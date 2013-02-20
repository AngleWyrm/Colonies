package colonies.src;

//import net.minecraft.entity.item.EntityItem;
//Additional note to force re-commit
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class AncientTomeDropsEvent {

public static double rand;
@ForgeSubscribe

public void onEntityDrop(LivingDropsEvent event) {
	rand = Math.random();
	if (event.entityLiving instanceof EntityZombie) {
		 if (rand < 0.05d);{
         	 event.entityLiving.dropItem(ColoniesMain.ancientTome.shiftedIndex, 1);
		 }
													}
														}
}