package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.util.Icon;

public class BlockButtonWood extends BlockButton {

   protected BlockButtonWood(int par1) {
      super(par1, true);
   }

   public Icon getIcon(int par1, int par2) {
      return Block.planks.getBlockTextureFromSide(1);
   }
}
