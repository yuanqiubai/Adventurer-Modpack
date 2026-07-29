package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces2;
import net.minecraft.world.gen.structure.StructurePieceBlockSelector;

class StructureScatteredFeatureStones extends StructurePieceBlockSelector {

   private StructureScatteredFeatureStones() {}

   public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5) {
      if(par1Random.nextFloat() < 0.4F) {
         this.selectedBlockId = Block.cobblestone.blockID;
      } else {
         this.selectedBlockId = Block.cobblestoneMossy.blockID;
      }

   }

   // $FF: synthetic method
   StructureScatteredFeatureStones(ComponentScatteredFeaturePieces2 par1ComponentScatteredFeaturePieces2) {
      this();
   }
}
