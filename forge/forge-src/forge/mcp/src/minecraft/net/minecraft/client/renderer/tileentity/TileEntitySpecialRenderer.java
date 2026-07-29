package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class TileEntitySpecialRenderer {

   protected TileEntityRenderer tileEntityRenderer;

   public abstract void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8);

   protected void bindTexture(ResourceLocation par1ResourceLocation) {
      TextureManager var2 = this.tileEntityRenderer.renderEngine;
      if(var2 != null) {
         var2.bindTexture(par1ResourceLocation);
      }

   }

   public void setTileEntityRenderer(TileEntityRenderer par1TileEntityRenderer) {
      this.tileEntityRenderer = par1TileEntityRenderer;
   }

   public void onWorldChange(World par1World) {}

   public FontRenderer getFontRenderer() {
      return this.tileEntityRenderer.getFontRenderer();
   }
}
