package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class RecipesCrafting {

   public void addRecipes(CraftingManager par1CraftingManager) {
      par1CraftingManager.addRecipe(new ItemStack(Block.chest), new Object[]{"###", "# #", "###", Character.valueOf('#'), Block.planks});
      par1CraftingManager.addRecipe(new ItemStack(Block.chestTrapped), new Object[]{"#-", Character.valueOf('#'), Block.chest, Character.valueOf('-'), Block.tripWireSource});
      par1CraftingManager.addRecipe(new ItemStack(Block.enderChest), new Object[]{"###", "#E#", "###", Character.valueOf('#'), Block.obsidian, Character.valueOf('E'), Item.eyeOfEnder});
      par1CraftingManager.addRecipe(new ItemStack(Block.furnaceIdle), new Object[]{"###", "# #", "###", Character.valueOf('#'), Block.cobblestone});
      par1CraftingManager.addRecipe(new ItemStack(Block.workbench), new Object[]{"##", "##", Character.valueOf('#'), Block.planks});
      par1CraftingManager.addRecipe(new ItemStack(Block.sandStone), new Object[]{"##", "##", Character.valueOf('#'), Block.sand});
      par1CraftingManager.addRecipe(new ItemStack(Block.sandStone, 4, 2), new Object[]{"##", "##", Character.valueOf('#'), Block.sandStone});
      par1CraftingManager.addRecipe(new ItemStack(Block.sandStone, 1, 1), new Object[]{"#", "#", Character.valueOf('#'), new ItemStack(Block.stoneSingleSlab, 1, 1)});
      par1CraftingManager.addRecipe(new ItemStack(Block.blockNetherQuartz, 1, 1), new Object[]{"#", "#", Character.valueOf('#'), new ItemStack(Block.stoneSingleSlab, 1, 7)});
      par1CraftingManager.addRecipe(new ItemStack(Block.blockNetherQuartz, 2, 2), new Object[]{"#", "#", Character.valueOf('#'), new ItemStack(Block.blockNetherQuartz, 1, 0)});
      par1CraftingManager.addRecipe(new ItemStack(Block.stoneBrick, 4), new Object[]{"##", "##", Character.valueOf('#'), Block.stone});
      par1CraftingManager.addRecipe(new ItemStack(Block.fenceIron, 16), new Object[]{"###", "###", Character.valueOf('#'), Item.ingotIron});
      par1CraftingManager.addRecipe(new ItemStack(Block.thinGlass, 16), new Object[]{"###", "###", Character.valueOf('#'), Block.glass});
      par1CraftingManager.addRecipe(new ItemStack(Block.redstoneLampIdle, 1), new Object[]{" R ", "RGR", " R ", Character.valueOf('R'), Item.redstone, Character.valueOf('G'), Block.glowStone});
      par1CraftingManager.addRecipe(new ItemStack(Block.beacon, 1), new Object[]{"GGG", "GSG", "OOO", Character.valueOf('G'), Block.glass, Character.valueOf('S'), Item.netherStar, Character.valueOf('O'), Block.obsidian});
      par1CraftingManager.addRecipe(new ItemStack(Block.netherBrick, 1), new Object[]{"NN", "NN", Character.valueOf('N'), Item.netherrackBrick});
   }
}
