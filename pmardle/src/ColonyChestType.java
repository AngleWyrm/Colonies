package Colonies.pmardle.src;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public enum ColonyChestType {
  TOWNHALL(54, 9, true, "Townhall Chest", "Townhallchest.png", 0, Arrays.asList("ingotIron","ingotRefinedIron"), TileEntityColonyChest.class, "BIBICIBIB", "mGmG3GmGm"),
  MINER(54, 9, true, "Miner Chest", "Minerchest.png", 1, Arrays.asList("ingotGold"), TileEntityMinerChest.class, "KTKTCTSTS", "mGmG4GmGm"),
  LUMBERJACK(54, 9, true, "Lumberjack Chest", "Lumberjackchest.png", 2, Arrays.asList("gemDiamond"), TileEntityLumberjackChest.class, "EJEJCJEJE", "GGGG4Gmmm"),
  BUILDER(54, 9, false, "Builder Chest", "Builderchest.png", 3, Arrays.asList("ingotCopper"), TileEntityBuilderChest.class, "ADADCDADA"),
  BLACKSMITH(54, 9, false, "Blacksmith Chest", "Blacksmithchest.png", 4, Arrays.asList("ingotSilver"), TileEntityBlacksmithChest.class, "LKLXCXWKW", "mGmG0GmGm"),
  HOUSE(54, 9, true, "House Chest", "Housechest.png", 5, Arrays.asList("blockGlass"), TileEntityHouseChest.class, "ZIZICIZIZ"),
  WOOD(0,0,false,"","",-1,Arrays.asList("blockPlanks"),null);
  int size;
  private int rowLength;
  public String friendlyName;
  private boolean tieredChest;
  private String modelTexture;
  private int textureRow;
  public Class<? extends TileEntityColonyChest> clazz;
  private String[] recipes;
  private ArrayList<String> matList;

  ColonyChestType(int size, int rowLength, boolean tieredChest, String friendlyName, String modelTexture, int textureRow, List<String> mats,
      Class<? extends TileEntityColonyChest> clazz, String... recipes) {
    this.size = size;
    this.rowLength = rowLength;
    this.tieredChest = tieredChest;
    this.friendlyName = friendlyName;
    this.modelTexture = "/pmardle/gfx/" + modelTexture;
    this.textureRow = textureRow;
    this.clazz = clazz;
    this.recipes = recipes;
    this.matList = new ArrayList<String>();
    matList.addAll(mats);
  }

  public String getModelTexture() {
    return modelTexture;
  }

  public int getTextureRow() {
    return textureRow;
  }

  public static TileEntityColonyChest makeEntity(int metadata) {
    // Compatibility
    int chesttype = validateMeta(metadata);
    if (chesttype == metadata) {
      try {
        TileEntityColonyChest te = values()[chesttype].clazz.newInstance();
        return te;
      } catch (InstantiationException e) {
        // unpossible
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        // unpossible
        e.printStackTrace();
      }
    }
    return null;
  }

  public static void registerTranslations() {
  }

  public static void generateTieredRecipes(BlockColonyChest blockResult) {
    ItemStack previous = new ItemStack(Block.chest);
    for (ColonyChestType typ : values()) {
      generateRecipesForType(blockResult, previous, typ);
      if (typ.tieredChest)
        previous = new ItemStack(blockResult, 1, typ.ordinal());
    }
  }

  public static void generateRecipesForType(BlockColonyChest blockResult, Object previousTier, ColonyChestType type) {
    for (String recipe : type.recipes) {
      String[] recipeSplit = new String[] { recipe.substring(0, 3), recipe.substring(3, 6), recipe.substring(6, 9) };
      Object mainMaterial = null;
      for (String mat : type.matList) {
        mainMaterial = translateOreName(mat);
        addRecipe(new ItemStack(blockResult, 1, type.ordinal()), recipeSplit,
            'm', mainMaterial,
            'P', previousTier, /* previous tier of chest */
            'G', Block.glass,
            'C', Block.chest,
            'B', Item.book,
            'I', Item.ingotIron,
            'Z', Item.bed,
            'K', Item.pickaxeSteel,
            'S', Item.shovelSteel,
            'T', Block.torchWood,
            'L', Item.bucketLava,
            'W', Item.bucketWater,
            'X', Block.blockSteel,
            'A', Block.brick,
            'D', Item.doorWood,
            'E', Block.sapling,
            'J', Item.axeStone,
            '0', new ItemStack(blockResult, 1, 0), /* Townhall Chest*/
            '1', new ItemStack(blockResult, 1, 1), /* Miner Chest*/
            '2', new ItemStack(blockResult, 1, 1), /* lumberjack Chest*/
            '3', new ItemStack(blockResult, 1, 3), /* Builder Chest */
            '4', new ItemStack(blockResult, 1, 4)/* Blacksmith Chest */
        );
      }
    }
  }

  public static Object translateOreName(String mat) {
    if (mat == "ingotIron" ) {
      return Item.ingotIron;
    } else if (mat == "ingotGold") {
      return Item.ingotGold;
    } else if (mat == "gemDiamond") {
      return Item.diamond;
    } else if (mat == "blockGlass") {
      return Block.glass;
    } else if (mat == "blockPlanks") {
      return Block.planks;
    }
    return mat;
  }

  @SuppressWarnings("unchecked")
  public static void addRecipe(ItemStack is, Object... parts) {
    ShapedOreRecipe oreRecipe = new ShapedOreRecipe(is, parts);
    CraftingManager.getInstance().getRecipeList().add(oreRecipe);
  }

  public int getRowCount() {
    return size / rowLength;
  }

  public int getRowLength() {
    return rowLength;
  }

  public boolean isTransparent() {
    return this == HOUSE;
  }

  public List<String> getMatList() {
    return matList;
  }

  public static int validateMeta(int i) {
    if (i < values().length && values()[i].size>0) {
      return i;
    } else {
      return 0;
    }
  }

  public boolean isValidForCreativeMode() {
    return validateMeta(ordinal())==ordinal();
  }

}