package colonies.hostile;
// 
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Calendar;

import colonies.src.ColoniesMain;
import colonies.src.citizens.EntityAlchemist;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityEnchanter;
import colonies.src.citizens.EntityFisherman;
import colonies.src.citizens.EntityHunter;
import colonies.src.citizens.EntityLumberjack;
import colonies.src.citizens.EntityMiner;
import colonies.src.citizens.EntityPriestess;
import colonies.src.citizens.EntityWife;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBarbarian extends EntityMob
{

    public EntityBarbarian(World par1World)
    {
        super(par1World);
        this.texture = "/colonies/hostile/gfx/Barbarian.png";
        this.moveSpeed = 0.25F;
        this.setSize(0.9f, 1.8f);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityAlchemist.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityCitizen.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityEnchanter.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityFisherman.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityHunter.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLumberjack.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityMiner.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPriestess.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityWife.class, this.moveSpeed, true));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 20.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityAlchemist.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCitizen.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityEnchanter.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityFisherman.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityHunter.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLumberjack.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMiner.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPriestess.class, 15.0F, 0, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWife.class, 15.0F, 0, false));
    }
        

    /**
     * This method returns a value to be applied directly to entity speed, this factor is less than 1 when a slowdown
     * potion effect is applied, more than 1 when a haste potion effect is applied and 2 for fleeing entities.
     */
    public float getSpeedModifier()
    {
        return super.getSpeedModifier() * (this.isChild() ? 1.5F : 1.0F);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataWatcher().addObject(12, Byte.valueOf((byte)0));
        this.getDataWatcher().addObject(13, Byte.valueOf((byte)0));
        this.getDataWatcher().addObject(14, Byte.valueOf((byte)0));
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the texture's file path as a String.
     */

    public int getMaxHealth()
    {
        return 25;
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        int var1 = super.getTotalArmorValue() + 2;

        if (var1 > 20)
        {
            var1 = 20;
        }

        return var1;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isChild()
    {
        return this.getDataWatcher().getWatchableObjectByte(12) == 1;
    }

    /**
     * Set whether this zombie is a child.
     */
    public void setChild(boolean par1)
    {
        this.getDataWatcher().updateObject(12, Byte.valueOf((byte)1));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild())
        {
            float var1 = this.getBrightness(1.0F);

            if (var1 > 0.5F && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
            {
                boolean var2 = true;
                ItemStack var3 = this.getCurrentItemOrArmor(4);

                if (var3 != null)
                {
                    if (var3.isItemStackDamageable())
                    {
                        var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));

                        if (var3.getItemDamageForDisplay() >= var3.getMaxDamage())
                        {
                            this.renderBrokenItemStack(var3);
                            this.setCurrentItemOrArmor(4, (ItemStack)null);
                        }
                    }

                    var2 = false;
                }

                if (var2)
                {
                    this.setFire(0);
                }
            }
        }

        super.onLivingUpdate();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
         super.onUpdate();
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity par1Entity)
    {
        ItemStack var2 = this.getHeldItem();
        int var3 = 4;

        if (var2 != null)
        {
            var3 += var2.getDamageVsEntity(this);
        }

        return var3;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.zombie.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.zombie.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.zombie.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.func_85030_a("mob.zombie.step", 0.15F, 1.0F);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.goldNugget.itemID;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEFINED;
    }

    protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(4))
        {
            case 0:
                this.dropItem(Item.ingotIron.itemID, 1);
                break;
            case 1:
                this.dropItem(Item.potato.itemID, 1);
                break;
            case 2:
                this.dropItem(Item.emerald.itemID, 1);
            case 3:
                this.dropItem(ColoniesMain.ancientTome.itemID, 1);
        }
    }

    protected void func_82164_bB()
    {
        super.func_82164_bB();

        if (this.rand.nextFloat() < (this.worldObj.difficultySetting == 3 ? 0.05F : 0.01F))
        {
            int var1 = this.rand.nextInt(3);

            if (var1 == 0)
            {
                this.setCurrentItemOrArmor(0, new ItemStack(Item.swordSteel));
            }
            else
            {
                this.setCurrentItemOrArmor(0, new ItemStack(Item.shovelSteel));
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);

        if (this.isChild())
        {
            par1NBTTagCompound.setBoolean("IsBaby", true);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.getBoolean("IsBaby"))
        {
            this.setChild(true);
        }
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLiving par1EntityLiving)
    {
        super.onKillEntity(par1EntityLiving);

        if (this.worldObj.difficultySetting >= 2 && par1EntityLiving instanceof EntityVillager)
        {
            if (this.worldObj.difficultySetting == 2 && this.rand.nextBoolean())
            {
                return;
            }

            EntityBarbarian var2 = new EntityBarbarian(this.worldObj);
            var2.func_82149_j(par1EntityLiving);
            this.worldObj.setEntityDead(par1EntityLiving);
            var2.initCreature();
            
            if (par1EntityLiving.isChild())
            {
                var2.setChild(true);
            }

            this.worldObj.spawnEntityInWorld(var2);
            this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        }
    }

    /**
     * Initialize this creature.
     */
    public void initCreature()
    {
        this.canPickUpLoot = this.rand.nextFloat() < field_82181_as[this.worldObj.difficultySetting];

        this.func_82164_bB();
        this.func_82162_bC();

        if (this.getCurrentItemOrArmor(4) == null)
        {
            Calendar var1 = this.worldObj.getCurrentDate();

            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }
    }


    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 16)
        {
            this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    public boolean func_82230_o()
    {
        return this.getDataWatcher().getWatchableObjectByte(14) == 1;
    }

  
}
