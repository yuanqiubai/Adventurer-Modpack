package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class RecipesFood {

   public void addRecipes(CraftingManager par1CraftingManager) {
      par1CraftingManager.addShapelessRecipe(new ItemStack(Item.bowlSoup), new Object[]{Block.mushroomBrown, Block.mushroomRed, Item.bowlEmpty});
      par1CraftingManager.addRecipe(new ItemStack(Item.cookie, 8), new Object[]{"#X#", Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 3), Character.valueOf('#'), Item.wheat});
      par1CraftingManager.addRecipe(new ItemStack(Block.melon), new Object[]{"MMM", "MMM", "MMM", Character.valueOf('M'), Item.melon});
      par1CraftingManager.addRecipe(new ItemStack(Item.melonSeeds), new Object[]{"M", Character.valueOf('M'), Item.melon});
      par1CraftingManager.addRecipe(new ItemStack(Item.pumpkinSeeds, 4), new Object[]{"M", Character.valueOf('M'), Block.pumpkin});
      par1CraftingManager.addShapelessRecipe(new ItemStack(Item.pumpkinPie), new Object[]{Block.pumpkin, Item.sugar, Item.egg});
      par1CraftingManager.addShapelessRecipe(new ItemStack(Item.fermentedSpiderEye), new Object[]{Item.spiderEye, Block.mushroomBrown, Item.sugar});
      par1CraftingManager.addShapelessRecipe(new ItemStack(Item.blazePowder, 2), new Object[]{Item.blazeRod});
      par1CraftingManager.addShapelessRecipe(new ItemStack(Item.magmaCream), new Object[]{Item.blazePowder, Item.slimeBall});
   }
}
