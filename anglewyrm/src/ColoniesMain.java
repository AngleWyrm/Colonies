package colonies.anglewyrm.src;

import colonies.lohikaarme.src.ItemMeasuringTape;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Block;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import colonies.anglewyrm.src.ConfigFile;

@Mod(modid = "Colonies", name = "Colonies, a MineColony Reboot", version = "r1")
@NetworkMod(
        channels = { "Colonies" },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class )

public class ColoniesMain {
	public static TestBlock test; // initalize AFTER ConfigFile loads (init phase)	
	public static Item MeasuringTape;

	@Instance
	public static ColoniesMain instance;

	@SidedProxy(clientSide = "colonies.anglewyrm.src.ClientProxy", serverSide = "colonies.anglewyrm.src.ServerProxy")
	public static ServerProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Initializing Colonies"); 
		ConfigFile.load();
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{
		proxy.registerRenderInformation();
		registerColoniesStuff(); // at bottom of this file for legibility
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
	    // TODO: Add Post-Initialization code such as mod hooks
	}
	
	public String Version(){
		return "Colonies r2";
	}
	
	
	// Register Colonies stuff with Minecraft Forge
	private void registerColoniesStuff()
	{
		MeasuringTape=new ItemMeasuringTape(ConfigFile.parseInt("MeasuringTape")).setItemName("Measuring Tape");
		LanguageRegistry.addName(MeasuringTape,"Measuring Tape");
		GameRegistry.addRecipe(new ItemStack(MeasuringTape),"  ","II",Character.valueOf('I'),Item.ingotIron);
		
		test = (TestBlock) new TestBlock(ConfigFile.parseInt("TestBlockID"), 3, Material.ground)
			.setBlockName("test").setHardness(0.75f).setCreativeTab(CreativeTabs.tabDecorations);
		MinecraftForge.setBlockHarvestLevel(test, "shovel", 0);
		LanguageRegistry.addName(test, "Test Block");
		GameRegistry.registerBlock(test);

		// This generates an error from double-registering the ID,
		// But I have yet to find out how to get an egg and a 'this'
		ModLoader.registerEntityID(EntityCitizen.class, "Citizen", ConfigFile.parseInt("CitizenID"), 0x4444aa, 0xccccff);
		EntityRegistry.registerModEntity(EntityCitizen.class, "Citizen", ConfigFile.parseInt("CitizenID"), this, 40, 3, true);
		EntityRegistry.addSpawn(EntityCitizen.class, 10, 2, 4,
				EnumCreatureType.monster, BiomeGenBase.beach, BiomeGenBase.extremeHills,
				BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills,
				BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore,
				BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland);
	}
}
