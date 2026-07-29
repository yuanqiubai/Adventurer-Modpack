package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockComparator;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneLogic;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderBlocks {

   public IBlockAccess blockAccess;
   private Icon overrideBlockTexture;
   private boolean flipTexture;
   private boolean renderAllFaces;
   public static boolean fancyGrass = true;
   public boolean useInventoryTint = true;
   private double renderMinX;
   private double renderMaxX;
   private double renderMinY;
   private double renderMaxY;
   private double renderMinZ;
   private double renderMaxZ;
   private boolean lockBlockBounds;
   private boolean partialRenderBounds;
   private final Minecraft minecraftRB;
   private int uvRotateEast;
   private int uvRotateWest;
   private int uvRotateSouth;
   private int uvRotateNorth;
   private int uvRotateTop;
   private int uvRotateBottom;
   private boolean enableAO;
   private float aoLightValueScratchXYZNNN;
   private float aoLightValueScratchXYNN;
   private float aoLightValueScratchXYZNNP;
   private float aoLightValueScratchYZNN;
   private float aoLightValueScratchYZNP;
   private float aoLightValueScratchXYZPNN;
   private float aoLightValueScratchXYPN;
   private float aoLightValueScratchXYZPNP;
   private float aoLightValueScratchXYZNPN;
   private float aoLightValueScratchXYNP;
   private float aoLightValueScratchXYZNPP;
   private float aoLightValueScratchYZPN;
   private float aoLightValueScratchXYZPPN;
   private float aoLightValueScratchXYPP;
   private float aoLightValueScratchYZPP;
   private float aoLightValueScratchXYZPPP;
   private float aoLightValueScratchXZNN;
   private float aoLightValueScratchXZPN;
   private float aoLightValueScratchXZNP;
   private float aoLightValueScratchXZPP;
   private int aoBrightnessXYZNNN;
   private int aoBrightnessXYNN;
   private int aoBrightnessXYZNNP;
   private int aoBrightnessYZNN;
   private int aoBrightnessYZNP;
   private int aoBrightnessXYZPNN;
   private int aoBrightnessXYPN;
   private int aoBrightnessXYZPNP;
   private int aoBrightnessXYZNPN;
   private int aoBrightnessXYNP;
   private int aoBrightnessXYZNPP;
   private int aoBrightnessYZPN;
   private int aoBrightnessXYZPPN;
   private int aoBrightnessXYPP;
   private int aoBrightnessYZPP;
   private int aoBrightnessXYZPPP;
   private int aoBrightnessXZNN;
   private int aoBrightnessXZPN;
   private int aoBrightnessXZNP;
   private int aoBrightnessXZPP;
   private int brightnessTopLeft;
   private int brightnessBottomLeft;
   private int brightnessBottomRight;
   private int brightnessTopRight;
   private float colorRedTopLeft;
   private float colorRedBottomLeft;
   private float colorRedBottomRight;
   private float colorRedTopRight;
   private float colorGreenTopLeft;
   private float colorGreenBottomLeft;
   private float colorGreenBottomRight;
   private float colorGreenTopRight;
   private float colorBlueTopLeft;
   private float colorBlueBottomLeft;
   private float colorBlueBottomRight;
   private float colorBlueTopRight;
   public float aoLightValueOpaque = 0.2F;
   private static float[] aoRenderGLV;
   private int bSITemp0;
   private int bSITemp1;
   private int bSITemp2;
   private int bSITemp3;
   private float bSFTemp0;
   private float bSFTemp1;
   private float bSFTemp2;
   private float bSFTemp3;
   private float bSFTemp4;
   private float bSFTemp5;
   private float bSFTemp6;
   private float bSFTemp7;
   private float bSFTemp8;
   private float bSFTemp9;
   private float bSFTemp10;
   private float bSFTemp11;
   private float bSFTemp12;
   private float bSFTemp13;
   private boolean bSBTemp1;
   private boolean bSBTemp2;
   private boolean bSBTemp3;
   private boolean bSBTemp4;
   private boolean bSBTemp5;
   private boolean bSBTemp6;
   private boolean bSBTemp7;
   private boolean bSBTemp8;
   private final boolean LRBTEMP1;
   private final boolean LRBTEMP2;
   private final boolean LRBTEMP3;
   private final boolean LRBTEMP4;
   private final boolean LRBTEMP5;
   private final boolean LRBTEMP6;
   private final boolean LRBTEMP7;
   private final boolean LRBTEMP8;
   private final float[] blockShadow = new float[]{0.5F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F};
   private static final boolean[] fastSideList;
   private Icon stackIcon;

   public RenderBlocks(IBlockAccess par1IBlockAccess) {
      this.blockAccess = par1IBlockAccess;
      this.minecraftRB = Minecraft.getMinecraft();
      this.aoRenderGetLightValue(this.aoLightValueOpaque);
      this.LRBTEMP1 = fancyGrass;
      this.LRBTEMP2 = this.LRBTEMP5 = this.LRBTEMP6 = this.LRBTEMP7 = this.LRBTEMP8 = false;
      this.LRBTEMP3 = this.minecraftRB.gameSettings.ambientOcclusion >= 1;
      this.LRBTEMP4 = this.minecraftRB.gameSettings.ambientOcclusion >= 2;
      if(this.minecraftRB != null) {
         Minecraft var10000 = this.minecraftRB;
         if(Minecraft.memoryReserve != null) {
            var10000 = this.minecraftRB;
            if(Minecraft.memoryReserve.length >= 65536) {
               var10000 = this.minecraftRB;
               Minecraft.memoryReserve['\ufff2'] = 1;
            }
         }
      }

   }

   public RenderBlocks() {
      this.minecraftRB = Minecraft.getMinecraft();
      this.LRBTEMP1 = this.LRBTEMP2 = this.LRBTEMP3 = this.LRBTEMP4 = this.LRBTEMP5 = this.LRBTEMP6 = this.LRBTEMP7 = this.LRBTEMP8 = false;
   }

   public void setOverrideBlockTexture(Icon par1Icon) {
      this.overrideBlockTexture = par1Icon;
   }

   public void clearOverrideBlockTexture() {
      this.overrideBlockTexture = null;
   }

   public boolean hasOverrideBlockTexture() {
      return this.overrideBlockTexture != null;
   }

   public void setRenderBounds(double par1, double par3, double par5, double par7, double par9, double par11) {
      if(!this.lockBlockBounds) {
         this.renderMinX = par1;
         this.renderMaxX = par7;
         this.renderMinY = par3;
         this.renderMaxY = par9;
         this.renderMinZ = par5;
         this.renderMaxZ = par11;
      }

   }

   public void setRenderBoundsFromBlock(Block par1Block) {
      if(!this.lockBlockBounds) {
         this.renderMinX = par1Block.getBlockBoundsMinX();
         this.renderMaxX = par1Block.getBlockBoundsMaxX();
         this.renderMinY = par1Block.getBlockBoundsMinY();
         this.renderMaxY = par1Block.getBlockBoundsMaxY();
         this.renderMinZ = par1Block.getBlockBoundsMinZ();
         this.renderMaxZ = par1Block.getBlockBoundsMaxZ();
      }

   }

   public void overrideBlockBounds(double par1, double par3, double par5, double par7, double par9, double par11) {
      this.renderMinX = par1;
      this.renderMaxX = par7;
      this.renderMinY = par3;
      this.renderMaxY = par9;
      this.renderMinZ = par5;
      this.renderMaxZ = par11;
      this.lockBlockBounds = true;
   }

   public void unlockBlockBounds() {
      this.lockBlockBounds = false;
   }

   public void renderBlockUsingTexture(Block par1Block, int par2, int par3, int par4, Icon par5Icon) {
      this.setOverrideBlockTexture(par5Icon);
      this.renderBlockByRenderType(par1Block, par2, par3, par4);
      this.clearOverrideBlockTexture();
   }

   public void renderBlockAllFaces(Block par1Block, int par2, int par3, int par4) {
      this.renderAllFaces = true;
      this.renderBlockByRenderType(par1Block, par2, par3, par4);
      this.renderAllFaces = false;
   }

   public boolean renderBlockByRenderType(Block par1Block, int par2, int par3, int par4) {
      par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
      this.setRenderBoundsFromBlock(par1Block);
      switch(par1Block.getRenderType()) {
      case -1:
         return false;
      case 0:
         return this.renderStandardBlock(par1Block, par2, par3, par4);
      case 4:
         return this.renderBlockFluids(par1Block, par2, par3, par4);
      default:
         return this.renderBlockByRenderType2(par1Block, par2, par3, par4);
      }
   }

   private boolean renderBlockEndPortalFrame(BlockEndPortalFrame par1BlockEndPortalFrame, int par2, int par3, int par4) {
      int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var6 = var5 & 3;
      if(var6 == 0) {
         this.uvRotateTop = 3;
      } else if(var6 == 3) {
         this.uvRotateTop = 1;
      } else if(var6 == 1) {
         this.uvRotateTop = 2;
      }

      if(!BlockEndPortalFrame.isEnderEyeInserted(var5)) {
         this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
         this.renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
         this.uvRotateTop = 0;
         return true;
      } else {
         this.renderAllFaces = true;
         this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
         this.renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
         this.setOverrideBlockTexture(par1BlockEndPortalFrame.func_94398_p());
         this.setRenderBounds(0.25D, 0.8125D, 0.25D, 0.75D, 1.0D, 0.75D);
         this.renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
         this.renderAllFaces = false;
         this.clearOverrideBlockTexture();
         this.uvRotateTop = 0;
         return true;
      }
   }

   private boolean renderBlockBed(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var7 = BlockBed.getDirection(var6);
      boolean var8 = BlockBed.isBlockHeadOfBed(var6);
      float var9 = 0.5F;
      float var10 = 1.0F;
      float var11 = 0.8F;
      float var12 = 0.6F;
      int var13 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      var5.setBrightness(var13);
      var5.setColorOpaque_F(var9, var9, var9);
      Icon var14 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0);
      double var15 = (double)var14.getMinU();
      double var17 = (double)var14.getMaxU();
      double var19 = (double)var14.getMinV();
      double var21 = (double)var14.getMaxV();
      double var23 = (double)par2 + this.renderMinX;
      double var25 = (double)par2 + this.renderMaxX;
      double var27 = (double)par3 + this.renderMinY + 0.1875D;
      double var29 = (double)par4 + this.renderMinZ;
      double var31 = (double)par4 + this.renderMaxZ;
      var5.addVertexWithUV(var23, var27, var31, var15, var21);
      var5.addVertexWithUV(var23, var27, var29, var15, var19);
      var5.addVertexWithUV(var25, var27, var29, var17, var19);
      var5.addVertexWithUV(var25, var27, var31, var17, var21);
      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
      var5.setColorOpaque_F(var10, var10, var10);
      var14 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1);
      var15 = (double)var14.getMinU();
      var17 = (double)var14.getMaxU();
      var19 = (double)var14.getMinV();
      var21 = (double)var14.getMaxV();
      var23 = var15;
      var25 = var17;
      var27 = var19;
      var29 = var19;
      var31 = var15;
      double var33 = var17;
      double var35 = var21;
      double var37 = var21;
      if(var7 == 0) {
         var25 = var15;
         var27 = var21;
         var31 = var17;
         var37 = var19;
      } else if(var7 == 2) {
         var23 = var17;
         var29 = var21;
         var33 = var15;
         var35 = var19;
      } else if(var7 == 3) {
         var23 = var17;
         var29 = var21;
         var33 = var15;
         var35 = var19;
         var25 = var15;
         var27 = var21;
         var31 = var17;
         var37 = var19;
      }

      double var39 = (double)par2 + this.renderMinX;
      double var41 = (double)par2 + this.renderMaxX;
      double var43 = (double)par3 + this.renderMaxY;
      double var45 = (double)par4 + this.renderMinZ;
      double var47 = (double)par4 + this.renderMaxZ;
      var5.addVertexWithUV(var41, var43, var47, var31, var35);
      var5.addVertexWithUV(var41, var43, var45, var23, var27);
      var5.addVertexWithUV(var39, var43, var45, var25, var29);
      var5.addVertexWithUV(var39, var43, var47, var33, var37);
      int var49 = Direction.directionToFacing[var7];
      if(var8) {
         var49 = Direction.directionToFacing[Direction.rotateOpposite[var7]];
      }

      byte var50 = 4;
      switch(var7) {
      case 0:
         var50 = 5;
         break;
      case 1:
         var50 = 3;
      case 2:
      default:
         break;
      case 3:
         var50 = 2;
      }

      if(var49 != 2 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))) {
         var5.setBrightness(this.renderMinZ > 0.0D?var13:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
         var5.setColorOpaque_F(var11, var11, var11);
         this.flipTexture = var50 == 2;
         this.renderFaceZNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2));
      }

      if(var49 != 3 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))) {
         var5.setBrightness(this.renderMaxZ < 1.0D?var13:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
         var5.setColorOpaque_F(var11, var11, var11);
         this.flipTexture = var50 == 3;
         this.renderFaceZPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3));
      }

      if(var49 != 4 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))) {
         var5.setBrightness(this.renderMinZ > 0.0D?var13:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
         var5.setColorOpaque_F(var12, var12, var12);
         this.flipTexture = var50 == 4;
         this.renderFaceXNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4));
      }

      if(var49 != 5 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))) {
         var5.setBrightness(this.renderMaxZ < 1.0D?var13:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
         var5.setColorOpaque_F(var12, var12, var12);
         this.flipTexture = var50 == 5;
         this.renderFaceXPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5));
      }

      this.flipTexture = false;
      return true;
   }

   private boolean renderBlockBrewingStand(BlockBrewingStand par1BlockBrewingStand, int par2, int par3, int par4) {
      this.setRenderBounds(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);
      this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
      this.setOverrideBlockTexture(par1BlockBrewingStand.getBrewingStandIcon());
      this.renderAllFaces = true;
      this.setRenderBounds(0.5625D, 0.0D, 0.3125D, 0.9375D, 0.125D, 0.6875D);
      this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
      this.setRenderBounds(0.125D, 0.0D, 0.0625D, 0.5D, 0.125D, 0.4375D);
      this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
      this.setRenderBounds(0.125D, 0.0D, 0.5625D, 0.5D, 0.125D, 0.9375D);
      this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
      this.renderAllFaces = false;
      this.clearOverrideBlockTexture();
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1BlockBrewingStand.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var6 = 1.0F;
      int var7 = par1BlockBrewingStand.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var8 = (float)(var7 >> 16 & 255) / 255.0F;
      float var9 = (float)(var7 >> 8 & 255) / 255.0F;
      float var10 = (float)(var7 & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
         float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
         float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
         var8 = var11;
         var9 = var12;
         var10 = var13;
      }

      var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
      Icon var32 = this.getBlockIconFromSideAndMetadata(par1BlockBrewingStand, 0, 0);
      if(this.hasOverrideBlockTexture()) {
         var32 = this.overrideBlockTexture;
      }

      double var33 = (double)var32.getMinV();
      double var14 = (double)var32.getMaxV();
      int var16 = this.blockAccess.getBlockMetadata(par2, par3, par4);

      for(int var17 = 0; var17 < 3; ++var17) {
         double var18 = (double)var17 * 3.141592653589793D * 2.0D / 3.0D + 1.5707963267948966D;
         double var20 = (double)var32.getInterpolatedU(8.0D);
         double var22 = (double)var32.getMaxU();
         if((var16 & 1 << var17) != 0) {
            var22 = (double)var32.getMinU();
         }

         double var24 = (double)par2 + 0.5D;
         double var26 = (double)par2 + 0.5D + Math.sin(var18) * 8.0D / 16.0D;
         double var28 = (double)par4 + 0.5D;
         double var30 = (double)par4 + 0.5D + Math.cos(var18) * 8.0D / 16.0D;
         var5.addVertexWithUV(var24, (double)(par3 + 1), var28, var20, var33);
         var5.addVertexWithUV(var24, (double)(par3 + 0), var28, var20, var14);
         var5.addVertexWithUV(var26, (double)(par3 + 0), var30, var22, var14);
         var5.addVertexWithUV(var26, (double)(par3 + 1), var30, var22, var33);
         var5.addVertexWithUV(var26, (double)(par3 + 1), var30, var22, var33);
         var5.addVertexWithUV(var26, (double)(par3 + 0), var30, var22, var14);
         var5.addVertexWithUV(var24, (double)(par3 + 0), var28, var20, var14);
         var5.addVertexWithUV(var24, (double)(par3 + 1), var28, var20, var33);
      }

      par1BlockBrewingStand.setBlockBoundsForItemRender();
      return true;
   }

   private boolean renderBlockCauldron(BlockCauldron par1BlockCauldron, int par2, int par3, int par4) {
      this.renderStandardBlock(par1BlockCauldron, par2, par3, par4);
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1BlockCauldron.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var6 = 1.0F;
      int var7 = par1BlockCauldron.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var8 = (float)(var7 >> 16 & 255) / 255.0F;
      float var9 = (float)(var7 >> 8 & 255) / 255.0F;
      float var10 = (float)(var7 & 255) / 255.0F;
      float var11;
      if(EntityRenderer.anaglyphEnable) {
         float var12 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
         var11 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
         float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
         var8 = var12;
         var9 = var11;
         var10 = var13;
      }

      var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
      Icon var16 = par1BlockCauldron.getBlockTextureFromSide(2);
      var11 = 0.125F;
      this.renderFaceXPos(par1BlockCauldron, (double)((float)par2 - 1.0F + var11), (double)par3, (double)par4, var16);
      this.renderFaceXNeg(par1BlockCauldron, (double)((float)par2 + 1.0F - var11), (double)par3, (double)par4, var16);
      this.renderFaceZPos(par1BlockCauldron, (double)par2, (double)par3, (double)((float)par4 - 1.0F + var11), var16);
      this.renderFaceZNeg(par1BlockCauldron, (double)par2, (double)par3, (double)((float)par4 + 1.0F - var11), var16);
      Icon var17 = BlockCauldron.getCauldronIcon("inner");
      this.renderFaceYPos(par1BlockCauldron, (double)par2, (double)((float)par3 - 1.0F + 0.25F), (double)par4, var17);
      this.renderFaceYNeg(par1BlockCauldron, (double)par2, (double)((float)par3 + 1.0F - 0.75F), (double)par4, var17);
      int var14 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      if(var14 > 0) {
         Icon var15 = BlockFluid.getFluidIcon("water_still");
         if(var14 > 3) {
            var14 = 3;
         }

         this.renderFaceYPos(par1BlockCauldron, (double)par2, (double)((float)par3 - 1.0F + (6.0F + (float)var14 * 3.0F) / 16.0F), (double)par4, var15);
      }

      return true;
   }

   private boolean renderBlockFlowerpot(BlockFlowerPot par1BlockFlowerPot, int par2, int par3, int par4) {
      this.renderStandardBlock(par1BlockFlowerPot, par2, par3, par4);
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1BlockFlowerPot.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var6 = 1.0F;
      int var7 = par1BlockFlowerPot.colorMultiplier(this.blockAccess, par2, par3, par4);
      Icon var8 = this.getBlockIconFromSide(par1BlockFlowerPot, 0);
      float var9 = (float)(var7 >> 16 & 255) / 255.0F;
      float var10 = (float)(var7 >> 8 & 255) / 255.0F;
      float var11 = (float)(var7 & 255) / 255.0F;
      float var12;
      float var13;
      if(EntityRenderer.anaglyphEnable) {
         var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
         float var14 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
         var13 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
         var9 = var12;
         var10 = var14;
         var11 = var13;
      }

      var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
      var12 = 0.1865F;
      this.renderFaceXPos(par1BlockFlowerPot, (double)((float)par2 - 0.5F + var12), (double)par3, (double)par4, var8);
      this.renderFaceXNeg(par1BlockFlowerPot, (double)((float)par2 + 0.5F - var12), (double)par3, (double)par4, var8);
      this.renderFaceZPos(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 - 0.5F + var12), var8);
      this.renderFaceZNeg(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 + 0.5F - var12), var8);
      this.renderFaceYPos(par1BlockFlowerPot, (double)par2, (double)((float)par3 - 0.5F + var12 + 0.1875F), (double)par4, this.getBlockIcon(Block.dirt));
      int var19 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      if(var19 != 0) {
         var13 = 0.0F;
         float var15 = 4.0F;
         float var16 = 0.0F;
         BlockFlower var17 = null;
         switch(var19) {
         case 1:
            var17 = Block.plantRed;
            break;
         case 2:
            var17 = Block.plantYellow;
         case 3:
         case 4:
         case 5:
         case 6:
         default:
            break;
         case 7:
            var17 = Block.mushroomRed;
            break;
         case 8:
            var17 = Block.mushroomBrown;
         }

         var5.addTranslation(var13 / 16.0F, var15 / 16.0F, var16 / 16.0F);
         if(var17 != null) {
            this.renderBlockByRenderType(var17, par2, par3, par4);
         } else if(var19 == 9) {
            this.renderAllFaces = true;
            float var18 = 0.125F;
            this.setRenderBounds((double)(0.5F - var18), 0.0D, (double)(0.5F - var18), (double)(0.5F + var18), 0.25D, (double)(0.5F + var18));
            this.renderStandardBlock(Block.cactus, par2, par3, par4);
            this.setRenderBounds((double)(0.5F - var18), 0.25D, (double)(0.5F - var18), (double)(0.5F + var18), 0.5D, (double)(0.5F + var18));
            this.renderStandardBlock(Block.cactus, par2, par3, par4);
            this.setRenderBounds((double)(0.5F - var18), 0.5D, (double)(0.5F - var18), (double)(0.5F + var18), 0.75D, (double)(0.5F + var18));
            this.renderStandardBlock(Block.cactus, par2, par3, par4);
            this.renderAllFaces = false;
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         } else if(var19 == 3) {
            this.drawCrossedSquares(Block.sapling, 0, (double)par2, (double)par3, (double)par4, 0.75F);
         } else if(var19 == 5) {
            this.drawCrossedSquares(Block.sapling, 2, (double)par2, (double)par3, (double)par4, 0.75F);
         } else if(var19 == 4) {
            this.drawCrossedSquares(Block.sapling, 1, (double)par2, (double)par3, (double)par4, 0.75F);
         } else if(var19 == 6) {
            this.drawCrossedSquares(Block.sapling, 3, (double)par2, (double)par3, (double)par4, 0.75F);
         } else if(var19 == 11) {
            var7 = Block.tallGrass.colorMultiplier(this.blockAccess, par2, par3, par4);
            var9 = (float)(var7 >> 16 & 255) / 255.0F;
            var10 = (float)(var7 >> 8 & 255) / 255.0F;
            var11 = (float)(var7 & 255) / 255.0F;
            var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
            this.drawCrossedSquares(Block.tallGrass, 2, (double)par2, (double)par3, (double)par4, 0.75F);
         } else if(var19 == 10) {
            this.drawCrossedSquares(Block.deadBush, 2, (double)par2, (double)par3, (double)par4, 0.75F);
         }

         var5.addTranslation(-var13 / 16.0F, -var15 / 16.0F, -var16 / 16.0F);
      }

      return true;
   }

   private boolean renderBlockAnvil(BlockAnvil par1BlockAnvil, int par2, int par3, int par4) {
      return this.renderBlockAnvilMetadata(par1BlockAnvil, par2, par3, par4, this.blockAccess.getBlockMetadata(par2, par3, par4));
   }

   public boolean renderBlockAnvilMetadata(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5) {
      Tessellator var6 = Tessellator.instance;
      var6.setBrightness(par1BlockAnvil.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var7 = 1.0F;
      int var8 = par1BlockAnvil.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var9 = (float)(var8 >> 16 & 255) / 255.0F;
      float var10 = (float)(var8 >> 8 & 255) / 255.0F;
      float var11 = (float)(var8 & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
         float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
         float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
         var9 = var12;
         var10 = var13;
         var11 = var14;
      }

      var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
      return this.renderBlockAnvilOrient(par1BlockAnvil, par2, par3, par4, par5, false);
   }

   private boolean renderBlockAnvilOrient(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5, boolean par6) {
      int var7 = par6?0:par5 & 3;
      boolean var8 = false;
      float var9 = 0.0F;
      switch(var7) {
      case 0:
         this.uvRotateSouth = 2;
         this.uvRotateNorth = 1;
         this.uvRotateTop = 3;
         this.uvRotateBottom = 3;
         break;
      case 1:
         this.uvRotateEast = 1;
         this.uvRotateWest = 2;
         this.uvRotateTop = 2;
         this.uvRotateBottom = 1;
         var8 = true;
         break;
      case 2:
         this.uvRotateSouth = 1;
         this.uvRotateNorth = 2;
         break;
      case 3:
         this.uvRotateEast = 2;
         this.uvRotateWest = 1;
         this.uvRotateTop = 1;
         this.uvRotateBottom = 2;
         var8 = true;
      }

      var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 0, var9, 0.75F, 0.25F, 0.75F, var8, par6, par5);
      var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 1, var9, 0.5F, 0.0625F, 0.625F, var8, par6, par5);
      var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 2, var9, 0.25F, 0.3125F, 0.5F, var8, par6, par5);
      this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 3, var9, 0.625F, 0.375F, 1.0F, var8, par6, par5);
      this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      this.uvRotateEast = 0;
      this.uvRotateWest = 0;
      this.uvRotateSouth = 0;
      this.uvRotateNorth = 0;
      this.uvRotateTop = 0;
      this.uvRotateBottom = 0;
      return true;
   }

   private float renderBlockAnvilRotate(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5, float par6, float par7, float par8, float par9, boolean par10, boolean par11, int par12) {
      if(par10) {
         float var13 = par7;
         par7 = par9;
         par9 = var13;
      }

      par7 /= 2.0F;
      par9 /= 2.0F;
      par1BlockAnvil.field_82521_b = par5;
      this.setRenderBounds((double)(0.5F - par7), (double)par6, (double)(0.5F - par9), (double)(0.5F + par7), (double)(par6 + par8), (double)(0.5F + par9));
      if(par11) {
         Tessellator var14 = Tessellator.instance;
         var14.startDrawingQuads();
         var14.setNormal(0.0F, -1.0F, 0.0F);
         this.renderFaceYNeg(par1BlockAnvil, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 0, par12));
         var14.draw();
         var14.startDrawingQuads();
         var14.setNormal(0.0F, 1.0F, 0.0F);
         this.renderFaceYPos(par1BlockAnvil, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 1, par12));
         var14.draw();
         var14.startDrawingQuads();
         var14.setNormal(0.0F, 0.0F, -1.0F);
         this.renderFaceZNeg(par1BlockAnvil, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 2, par12));
         var14.draw();
         var14.startDrawingQuads();
         var14.setNormal(0.0F, 0.0F, 1.0F);
         this.renderFaceZPos(par1BlockAnvil, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 3, par12));
         var14.draw();
         var14.startDrawingQuads();
         var14.setNormal(-1.0F, 0.0F, 0.0F);
         this.renderFaceXNeg(par1BlockAnvil, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 4, par12));
         var14.draw();
         var14.startDrawingQuads();
         var14.setNormal(1.0F, 0.0F, 0.0F);
         this.renderFaceXPos(par1BlockAnvil, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 5, par12));
         var14.draw();
      } else {
         this.renderStandardBlock(par1BlockAnvil, par2, par3, par4);
      }

      return par6 + par8;
   }

   public boolean renderBlockTorch(Block par1Block, int par2, int par3, int par4) {
      int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      Tessellator var6 = Tessellator.instance;
      var6.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      double var7 = 0.4000000059604645D;
      double var9 = 0.5D - var7;
      double var11 = 0.20000000298023224D;
      if(var5 == 1) {
         this.renderTorchAtAngle(par1Block, (double)par2 - var9, (double)par3 + var11, (double)par4, -var7, 0.0D, 0);
      } else if(var5 == 2) {
         this.renderTorchAtAngle(par1Block, (double)par2 + var9, (double)par3 + var11, (double)par4, var7, 0.0D, 0);
      } else if(var5 == 3) {
         this.renderTorchAtAngle(par1Block, (double)par2, (double)par3 + var11, (double)par4 - var9, 0.0D, -var7, 0);
      } else if(var5 == 4) {
         this.renderTorchAtAngle(par1Block, (double)par2, (double)par3 + var11, (double)par4 + var9, 0.0D, var7, 0);
      } else {
         this.renderTorchAtAngle(par1Block, (double)par2, (double)par3, (double)par4, 0.0D, 0.0D, 0);
      }

      return true;
   }

   private boolean renderBlockRepeater(BlockRedstoneRepeater par1BlockRedstoneRepeater, int par2, int par3, int par4) {
      int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var6 = var5 & 3;
      int var7 = (var5 & 12) >> 2;
      Tessellator var8 = Tessellator.instance;
      var8.setBrightness(par1BlockRedstoneRepeater.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      double var9 = -0.1875D;
      boolean var11 = par1BlockRedstoneRepeater.func_94476_e(this.blockAccess, par2, par3, par4, var5);
      double var12 = 0.0D;
      double var14 = 0.0D;
      double var16 = 0.0D;
      double var18 = 0.0D;
      switch(var6) {
      case 0:
         var18 = -0.3125D;
         var14 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
         break;
      case 1:
         var16 = 0.3125D;
         var12 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
         break;
      case 2:
         var18 = 0.3125D;
         var14 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
         break;
      case 3:
         var16 = -0.3125D;
         var12 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
      }

      if(!var11) {
         this.renderTorchAtAngle(par1BlockRedstoneRepeater, (double)par2 + var12, (double)par3 + var9, (double)par4 + var14, 0.0D, 0.0D, 0);
      } else {
         Icon var20 = this.getBlockIcon(Block.bedrock);
         this.setOverrideBlockTexture(var20);
         float var21 = 2.0F;
         float var22 = 14.0F;
         float var23 = 7.0F;
         float var24 = 9.0F;
         switch(var6) {
         case 1:
         case 3:
            var21 = 7.0F;
            var22 = 9.0F;
            var23 = 2.0F;
            var24 = 14.0F;
         case 0:
         case 2:
         default:
            this.setRenderBounds((double)(var21 / 16.0F + (float)var12), 0.125D, (double)(var23 / 16.0F + (float)var14), (double)(var22 / 16.0F + (float)var12), 0.25D, (double)(var24 / 16.0F + (float)var14));
            double var25 = (double)var20.getInterpolatedU((double)var21);
            double var27 = (double)var20.getInterpolatedV((double)var23);
            double var29 = (double)var20.getInterpolatedU((double)var22);
            double var31 = (double)var20.getInterpolatedV((double)var24);
            var8.addVertexWithUV((double)((float)par2 + var21 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var23 / 16.0F) + var14, var25, var27);
            var8.addVertexWithUV((double)((float)par2 + var21 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var24 / 16.0F) + var14, var25, var31);
            var8.addVertexWithUV((double)((float)par2 + var22 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var24 / 16.0F) + var14, var29, var31);
            var8.addVertexWithUV((double)((float)par2 + var22 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var23 / 16.0F) + var14, var29, var27);
            this.renderStandardBlock(par1BlockRedstoneRepeater, par2, par3, par4);
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
            this.clearOverrideBlockTexture();
         }
      }

      var8.setBrightness(par1BlockRedstoneRepeater.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      this.renderTorchAtAngle(par1BlockRedstoneRepeater, (double)par2 + var16, (double)par3 + var9, (double)par4 + var18, 0.0D, 0.0D, 0);
      this.renderBlockRedstoneLogic(par1BlockRedstoneRepeater, par2, par3, par4);
      return true;
   }

   private boolean renderBlockComparator(BlockComparator par1BlockComparator, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1BlockComparator.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var7 = var6 & 3;
      double var8 = 0.0D;
      double var10 = -0.1875D;
      double var12 = 0.0D;
      double var14 = 0.0D;
      double var16 = 0.0D;
      Icon var18;
      if(par1BlockComparator.func_94490_c(var6)) {
         var18 = Block.torchRedstoneActive.getBlockTextureFromSide(0);
      } else {
         var10 -= 0.1875D;
         var18 = Block.torchRedstoneIdle.getBlockTextureFromSide(0);
      }

      switch(var7) {
      case 0:
         var12 = -0.3125D;
         var16 = 1.0D;
         break;
      case 1:
         var8 = 0.3125D;
         var14 = -1.0D;
         break;
      case 2:
         var12 = 0.3125D;
         var16 = -1.0D;
         break;
      case 3:
         var8 = -0.3125D;
         var14 = 1.0D;
      }

      this.renderTorchAtAngle(par1BlockComparator, (double)par2 + 0.25D * var14 + 0.1875D * var16, (double)((float)par3 - 0.1875F), (double)par4 + 0.25D * var16 + 0.1875D * var14, 0.0D, 0.0D, var6);
      this.renderTorchAtAngle(par1BlockComparator, (double)par2 + 0.25D * var14 + -0.1875D * var16, (double)((float)par3 - 0.1875F), (double)par4 + 0.25D * var16 + -0.1875D * var14, 0.0D, 0.0D, var6);
      this.setOverrideBlockTexture(var18);
      this.renderTorchAtAngle(par1BlockComparator, (double)par2 + var8, (double)par3 + var10, (double)par4 + var12, 0.0D, 0.0D, var6);
      this.clearOverrideBlockTexture();
      this.renderBlockRedstoneLogicMetadata(par1BlockComparator, par2, par3, par4, var7);
      return true;
   }

   private boolean renderBlockRedstoneLogic(BlockRedstoneLogic par1BlockRedstoneLogic, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      this.renderBlockRedstoneLogicMetadata(par1BlockRedstoneLogic, par2, par3, par4, this.blockAccess.getBlockMetadata(par2, par3, par4) & 3);
      return true;
   }

   private void renderBlockRedstoneLogicMetadata(BlockRedstoneLogic par1BlockRedstoneLogic, int par2, int par3, int par4, int par5) {
      this.renderStandardBlock(par1BlockRedstoneLogic, par2, par3, par4);
      Tessellator var6 = Tessellator.instance;
      var6.setBrightness(par1BlockRedstoneLogic.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      int var7 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      Icon var8 = this.getBlockIconFromSideAndMetadata(par1BlockRedstoneLogic, 1, var7);
      double var9 = (double)var8.getMinU();
      double var11 = (double)var8.getMaxU();
      double var13 = (double)var8.getMinV();
      double var15 = (double)var8.getMaxV();
      double var17 = 0.125D;
      double var19 = (double)(par2 + 1);
      double var21 = (double)(par2 + 1);
      double var23 = (double)(par2 + 0);
      double var25 = (double)(par2 + 0);
      double var27 = (double)(par4 + 0);
      double var29 = (double)(par4 + 1);
      double var31 = (double)(par4 + 1);
      double var33 = (double)(par4 + 0);
      double var35 = (double)par3 + var17;
      if(par5 == 2) {
         var19 = var21 = (double)(par2 + 0);
         var23 = var25 = (double)(par2 + 1);
         var27 = var33 = (double)(par4 + 1);
         var29 = var31 = (double)(par4 + 0);
      } else if(par5 == 3) {
         var19 = var25 = (double)(par2 + 0);
         var21 = var23 = (double)(par2 + 1);
         var27 = var29 = (double)(par4 + 0);
         var31 = var33 = (double)(par4 + 1);
      } else if(par5 == 1) {
         var19 = var25 = (double)(par2 + 1);
         var21 = var23 = (double)(par2 + 0);
         var27 = var29 = (double)(par4 + 1);
         var31 = var33 = (double)(par4 + 0);
      }

      var6.addVertexWithUV(var25, var35, var33, var9, var13);
      var6.addVertexWithUV(var23, var35, var31, var9, var15);
      var6.addVertexWithUV(var21, var35, var29, var11, var15);
      var6.addVertexWithUV(var19, var35, var27, var11, var13);
   }

   public void renderPistonBaseAllFaces(Block par1Block, int par2, int par3, int par4) {
      this.renderAllFaces = true;
      this.renderPistonBase(par1Block, par2, par3, par4, true);
      this.renderAllFaces = false;
   }

   private boolean renderPistonBase(Block par1Block, int par2, int par3, int par4, boolean par5) {
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      boolean var7 = par5 || (var6 & 8) != 0;
      int var8 = BlockPistonBase.getOrientation(var6);
      float var9 = 0.25F;
      if(var7) {
         switch(var8) {
         case 0:
            this.uvRotateEast = 3;
            this.uvRotateWest = 3;
            this.uvRotateSouth = 3;
            this.uvRotateNorth = 3;
            this.setRenderBounds(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
            break;
         case 1:
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
            break;
         case 2:
            this.uvRotateSouth = 1;
            this.uvRotateNorth = 2;
            this.setRenderBounds(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
            break;
         case 3:
            this.uvRotateSouth = 2;
            this.uvRotateNorth = 1;
            this.uvRotateTop = 3;
            this.uvRotateBottom = 3;
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
            break;
         case 4:
            this.uvRotateEast = 1;
            this.uvRotateWest = 2;
            this.uvRotateTop = 2;
            this.uvRotateBottom = 1;
            this.setRenderBounds(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            break;
         case 5:
            this.uvRotateEast = 2;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 2;
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
         }

         ((BlockPistonBase)par1Block).func_96479_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.uvRotateEast = 0;
         this.uvRotateWest = 0;
         this.uvRotateSouth = 0;
         this.uvRotateNorth = 0;
         this.uvRotateTop = 0;
         this.uvRotateBottom = 0;
         this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         ((BlockPistonBase)par1Block).func_96479_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
      } else {
         switch(var8) {
         case 0:
            this.uvRotateEast = 3;
            this.uvRotateWest = 3;
            this.uvRotateSouth = 3;
            this.uvRotateNorth = 3;
         case 1:
         default:
            break;
         case 2:
            this.uvRotateSouth = 1;
            this.uvRotateNorth = 2;
            break;
         case 3:
            this.uvRotateSouth = 2;
            this.uvRotateNorth = 1;
            this.uvRotateTop = 3;
            this.uvRotateBottom = 3;
            break;
         case 4:
            this.uvRotateEast = 1;
            this.uvRotateWest = 2;
            this.uvRotateTop = 2;
            this.uvRotateBottom = 1;
            break;
         case 5:
            this.uvRotateEast = 2;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 2;
         }

         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.uvRotateEast = 0;
         this.uvRotateWest = 0;
         this.uvRotateSouth = 0;
         this.uvRotateNorth = 0;
         this.uvRotateTop = 0;
         this.uvRotateBottom = 0;
      }

      return true;
   }

   private void renderPistonRodUD(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14) {
      Icon var16 = BlockPistonBase.getPistonBaseIcon("piston_side");
      if(this.hasOverrideBlockTexture()) {
         var16 = this.overrideBlockTexture;
      }

      Tessellator var17 = Tessellator.instance;
      double var18 = (double)var16.getMinU();
      double var20 = (double)var16.getMinV();
      double var22 = (double)var16.getInterpolatedU(par14);
      double var24 = (double)var16.getInterpolatedV(4.0D);
      var17.setColorOpaque_F(par13, par13, par13);
      var17.addVertexWithUV(par1, par7, par9, var22, var20);
      var17.addVertexWithUV(par1, par5, par9, var18, var20);
      var17.addVertexWithUV(par3, par5, par11, var18, var24);
      var17.addVertexWithUV(par3, par7, par11, var22, var24);
   }

   private void renderPistonRodSN(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14) {
      Icon var16 = BlockPistonBase.getPistonBaseIcon("piston_side");
      if(this.hasOverrideBlockTexture()) {
         var16 = this.overrideBlockTexture;
      }

      Tessellator var17 = Tessellator.instance;
      double var18 = (double)var16.getMinU();
      double var20 = (double)var16.getMinV();
      double var22 = (double)var16.getInterpolatedU(par14);
      double var24 = (double)var16.getInterpolatedV(4.0D);
      var17.setColorOpaque_F(par13, par13, par13);
      var17.addVertexWithUV(par1, par5, par11, var22, var20);
      var17.addVertexWithUV(par1, par5, par9, var18, var20);
      var17.addVertexWithUV(par3, par7, par9, var18, var24);
      var17.addVertexWithUV(par3, par7, par11, var22, var24);
   }

   private void renderPistonRodEW(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14) {
      Icon var16 = BlockPistonBase.getPistonBaseIcon("piston_side");
      if(this.hasOverrideBlockTexture()) {
         var16 = this.overrideBlockTexture;
      }

      Tessellator var17 = Tessellator.instance;
      double var18 = (double)var16.getMinU();
      double var20 = (double)var16.getMinV();
      double var22 = (double)var16.getInterpolatedU(par14);
      double var24 = (double)var16.getInterpolatedV(4.0D);
      var17.setColorOpaque_F(par13, par13, par13);
      var17.addVertexWithUV(par3, par5, par9, var22, var20);
      var17.addVertexWithUV(par1, par5, par9, var18, var20);
      var17.addVertexWithUV(par1, par7, par11, var18, var24);
      var17.addVertexWithUV(par3, par7, par11, var22, var24);
   }

   public void renderPistonExtensionAllFaces(Block par1Block, int par2, int par3, int par4, boolean par5) {
      this.renderAllFaces = true;
      this.renderPistonExtension(par1Block, par2, par3, par4, par5);
      this.renderAllFaces = false;
   }

   private boolean renderPistonExtension(Block par1Block, int par2, int par3, int par4, boolean par5) {
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var7 = BlockPistonExtension.getDirectionMeta(var6);
      float var8 = 0.25F;
      float var9 = 0.375F;
      float var10 = 0.625F;
      float var11 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4);
      float var12 = par5?1.0F:0.5F;
      double var13 = par5?16.0D:8.0D;
      switch(var7) {
      case 0:
         this.uvRotateEast = 3;
         this.uvRotateWest = 3;
         this.uvRotateSouth = 3;
         this.uvRotateNorth = 3;
         this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var12), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var11 * 0.8F, var13);
         this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var12), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var11 * 0.8F, var13);
         this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var12), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var11 * 0.6F, var13);
         this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var12), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var11 * 0.6F, var13);
         break;
      case 1:
         this.setRenderBounds(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 - 0.25F + 1.0F - var12), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var11 * 0.8F, var13);
         this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 - 0.25F + 1.0F - var12), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var11 * 0.8F, var13);
         this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 - 0.25F + 1.0F - var12), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var11 * 0.6F, var13);
         this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 - 0.25F + 1.0F - var12), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var11 * 0.6F, var13);
         break;
      case 2:
         this.uvRotateSouth = 1;
         this.uvRotateNorth = 2;
         this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var12), var11 * 0.6F, var13);
         this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var12), var11 * 0.6F, var13);
         this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var12), var11 * 0.5F, var13);
         this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var12), var11, var13);
         break;
      case 3:
         this.uvRotateSouth = 2;
         this.uvRotateNorth = 1;
         this.uvRotateTop = 3;
         this.uvRotateBottom = 3;
         this.setRenderBounds(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 - 0.25F + 1.0F - var12), (double)((float)par4 - 0.25F + 1.0F), var11 * 0.6F, var13);
         this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 - 0.25F + 1.0F - var12), (double)((float)par4 - 0.25F + 1.0F), var11 * 0.6F, var13);
         this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 - 0.25F + 1.0F - var12), (double)((float)par4 - 0.25F + 1.0F), var11 * 0.5F, var13);
         this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 - 0.25F + 1.0F - var12), (double)((float)par4 - 0.25F + 1.0F), var11, var13);
         break;
      case 4:
         this.uvRotateEast = 1;
         this.uvRotateWest = 2;
         this.uvRotateTop = 2;
         this.uvRotateBottom = 1;
         this.setRenderBounds(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var12), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var11 * 0.5F, var13);
         this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var12), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var11, var13);
         this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var12), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var11 * 0.6F, var13);
         this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var12), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var11 * 0.6F, var13);
         break;
      case 5:
         this.uvRotateEast = 2;
         this.uvRotateWest = 1;
         this.uvRotateTop = 1;
         this.uvRotateBottom = 2;
         this.setRenderBounds(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         this.renderStandardBlock(par1Block, par2, par3, par4);
         this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var12), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var11 * 0.5F, var13);
         this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var12), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var11, var13);
         this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var12), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var11 * 0.6F, var13);
         this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var12), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var11 * 0.6F, var13);
      }

      this.uvRotateEast = 0;
      this.uvRotateWest = 0;
      this.uvRotateSouth = 0;
      this.uvRotateNorth = 0;
      this.uvRotateTop = 0;
      this.uvRotateBottom = 0;
      this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      return true;
   }

   public boolean renderBlockLever(Block par1Block, int par2, int par3, int par4) {
      int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var6 = var5 & 7;
      boolean var7 = (var5 & 8) > 0;
      Tessellator var8 = Tessellator.instance;
      boolean var9 = this.hasOverrideBlockTexture();
      if(!var9) {
         this.setOverrideBlockTexture(this.getBlockIcon(Block.cobblestone));
      }

      float var10 = 0.25F;
      float var11 = 0.1875F;
      float var12 = 0.1875F;
      if(var6 == 5) {
         this.setRenderBounds((double)(0.5F - var11), 0.0D, (double)(0.5F - var10), (double)(0.5F + var11), (double)var12, (double)(0.5F + var10));
      } else if(var6 == 6) {
         this.setRenderBounds((double)(0.5F - var10), 0.0D, (double)(0.5F - var11), (double)(0.5F + var10), (double)var12, (double)(0.5F + var11));
      } else if(var6 == 4) {
         this.setRenderBounds((double)(0.5F - var11), (double)(0.5F - var10), (double)(1.0F - var12), (double)(0.5F + var11), (double)(0.5F + var10), 1.0D);
      } else if(var6 == 3) {
         this.setRenderBounds((double)(0.5F - var11), (double)(0.5F - var10), 0.0D, (double)(0.5F + var11), (double)(0.5F + var10), (double)var12);
      } else if(var6 == 2) {
         this.setRenderBounds((double)(1.0F - var12), (double)(0.5F - var10), (double)(0.5F - var11), 1.0D, (double)(0.5F + var10), (double)(0.5F + var11));
      } else if(var6 == 1) {
         this.setRenderBounds(0.0D, (double)(0.5F - var10), (double)(0.5F - var11), (double)var12, (double)(0.5F + var10), (double)(0.5F + var11));
      } else if(var6 == 0) {
         this.setRenderBounds((double)(0.5F - var10), (double)(1.0F - var12), (double)(0.5F - var11), (double)(0.5F + var10), 1.0D, (double)(0.5F + var11));
      } else if(var6 == 7) {
         this.setRenderBounds((double)(0.5F - var11), (double)(1.0F - var12), (double)(0.5F - var10), (double)(0.5F + var11), 1.0D, (double)(0.5F + var10));
      }

      this.renderStandardBlock(par1Block, par2, par3, par4);
      if(!var9) {
         this.clearOverrideBlockTexture();
      }

      var8.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var13 = 1.0F;
      if(Block.lightValue[par1Block.blockID] > 0) {
         var13 = 1.0F;
      }

      var8.setColorOpaque_F(var13, var13, var13);
      Icon var14 = this.getBlockIconFromSide(par1Block, 0);
      if(this.hasOverrideBlockTexture()) {
         var14 = this.overrideBlockTexture;
      }

      double var15 = (double)var14.getMinU();
      double var17 = (double)var14.getMinV();
      double var19 = (double)var14.getMaxU();
      double var21 = (double)var14.getMaxV();
      Vec3[] var23 = new Vec3[8];
      float var24 = 0.0625F;
      float var25 = 0.0625F;
      float var26 = 0.625F;
      var23[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var24), 0.0D, (double)(-var25));
      var23[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var24, 0.0D, (double)(-var25));
      var23[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var24, 0.0D, (double)var25);
      var23[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var24), 0.0D, (double)var25);
      var23[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var24), (double)var26, (double)(-var25));
      var23[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var24, (double)var26, (double)(-var25));
      var23[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var24, (double)var26, (double)var25);
      var23[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var24), (double)var26, (double)var25);

      for(int var27 = 0; var27 < 8; ++var27) {
         if(var7) {
            var23[var27].zCoord -= 0.0625D;
            var23[var27].rotateAroundX(0.69813174F);
         } else {
            var23[var27].zCoord += 0.0625D;
            var23[var27].rotateAroundX(-0.69813174F);
         }

         if(var6 == 0 || var6 == 7) {
            var23[var27].rotateAroundZ(3.1415927F);
         }

         if(var6 == 6 || var6 == 0) {
            var23[var27].rotateAroundY(1.5707964F);
         }

         if(var6 > 0 && var6 < 5) {
            var23[var27].yCoord -= 0.375D;
            var23[var27].rotateAroundX(1.5707964F);
            if(var6 == 4) {
               var23[var27].rotateAroundY(0.0F);
            }

            if(var6 == 3) {
               var23[var27].rotateAroundY(3.1415927F);
            }

            if(var6 == 2) {
               var23[var27].rotateAroundY(1.5707964F);
            }

            if(var6 == 1) {
               var23[var27].rotateAroundY(-1.5707964F);
            }

            var23[var27].xCoord += (double)par2 + 0.5D;
            var23[var27].yCoord += (double)((float)par3 + 0.5F);
            var23[var27].zCoord += (double)par4 + 0.5D;
         } else if(var6 != 0 && var6 != 7) {
            var23[var27].xCoord += (double)par2 + 0.5D;
            var23[var27].yCoord += (double)((float)par3 + 0.125F);
            var23[var27].zCoord += (double)par4 + 0.5D;
         } else {
            var23[var27].xCoord += (double)par2 + 0.5D;
            var23[var27].yCoord += (double)((float)par3 + 0.875F);
            var23[var27].zCoord += (double)par4 + 0.5D;
         }
      }

      Vec3 var32 = null;
      Vec3 var28 = null;
      Vec3 var29 = null;
      Vec3 var30 = null;

      for(int var31 = 0; var31 < 6; ++var31) {
         if(var31 == 0) {
            var15 = (double)var14.getInterpolatedU(7.0D);
            var17 = (double)var14.getInterpolatedV(6.0D);
            var19 = (double)var14.getInterpolatedU(9.0D);
            var21 = (double)var14.getInterpolatedV(8.0D);
         } else if(var31 == 2) {
            var15 = (double)var14.getInterpolatedU(7.0D);
            var17 = (double)var14.getInterpolatedV(6.0D);
            var19 = (double)var14.getInterpolatedU(9.0D);
            var21 = (double)var14.getMaxV();
         }

         if(var31 == 0) {
            var32 = var23[0];
            var28 = var23[1];
            var29 = var23[2];
            var30 = var23[3];
         } else if(var31 == 1) {
            var32 = var23[7];
            var28 = var23[6];
            var29 = var23[5];
            var30 = var23[4];
         } else if(var31 == 2) {
            var32 = var23[1];
            var28 = var23[0];
            var29 = var23[4];
            var30 = var23[5];
         } else if(var31 == 3) {
            var32 = var23[2];
            var28 = var23[1];
            var29 = var23[5];
            var30 = var23[6];
         } else if(var31 == 4) {
            var32 = var23[3];
            var28 = var23[2];
            var29 = var23[6];
            var30 = var23[7];
         } else if(var31 == 5) {
            var32 = var23[0];
            var28 = var23[3];
            var29 = var23[7];
            var30 = var23[4];
         }

         var8.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var15, var21);
         var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var19, var21);
         var8.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var19, var17);
         var8.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, var15, var17);
      }

      return true;
   }

   public boolean renderBlockTripWireSource(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var7 = var6 & 3;
      boolean var8 = (var6 & 4) == 4;
      boolean var9 = (var6 & 8) == 8;
      boolean var10 = !this.blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
      boolean var11 = this.hasOverrideBlockTexture();
      if(!var11) {
         this.setOverrideBlockTexture(this.getBlockIcon(Block.planks));
      }

      float var12 = 0.25F;
      float var13 = 0.125F;
      float var14 = 0.125F;
      float var15 = 0.3F - var12;
      float var16 = 0.3F + var12;
      if(var7 == 2) {
         this.setRenderBounds((double)(0.5F - var13), (double)var15, (double)(1.0F - var14), (double)(0.5F + var13), (double)var16, 1.0D);
      } else if(var7 == 0) {
         this.setRenderBounds((double)(0.5F - var13), (double)var15, 0.0D, (double)(0.5F + var13), (double)var16, (double)var14);
      } else if(var7 == 1) {
         this.setRenderBounds((double)(1.0F - var14), (double)var15, (double)(0.5F - var13), 1.0D, (double)var16, (double)(0.5F + var13));
      } else if(var7 == 3) {
         this.setRenderBounds(0.0D, (double)var15, (double)(0.5F - var13), (double)var14, (double)var16, (double)(0.5F + var13));
      }

      this.renderStandardBlock(par1Block, par2, par3, par4);
      if(!var11) {
         this.clearOverrideBlockTexture();
      }

      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var17 = 1.0F;
      if(Block.lightValue[par1Block.blockID] > 0) {
         var17 = 1.0F;
      }

      var5.setColorOpaque_F(var17, var17, var17);
      Icon var18 = this.getBlockIconFromSide(par1Block, 0);
      if(this.hasOverrideBlockTexture()) {
         var18 = this.overrideBlockTexture;
      }

      double var19 = (double)var18.getMinU();
      double var21 = (double)var18.getMinV();
      double var23 = (double)var18.getMaxU();
      double var25 = (double)var18.getMaxV();
      Vec3[] var27 = new Vec3[8];
      float var28 = 0.046875F;
      float var29 = 0.046875F;
      float var30 = 0.3125F;
      var27[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var28), 0.0D, (double)(-var29));
      var27[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var28, 0.0D, (double)(-var29));
      var27[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var28, 0.0D, (double)var29);
      var27[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var28), 0.0D, (double)var29);
      var27[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var28), (double)var30, (double)(-var29));
      var27[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var28, (double)var30, (double)(-var29));
      var27[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var28, (double)var30, (double)var29);
      var27[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var28), (double)var30, (double)var29);

      for(int var31 = 0; var31 < 8; ++var31) {
         var27[var31].zCoord += 0.0625D;
         if(var9) {
            var27[var31].rotateAroundX(0.5235988F);
            var27[var31].yCoord -= 0.4375D;
         } else if(var8) {
            var27[var31].rotateAroundX(0.08726647F);
            var27[var31].yCoord -= 0.4375D;
         } else {
            var27[var31].rotateAroundX(-0.69813174F);
            var27[var31].yCoord -= 0.375D;
         }

         var27[var31].rotateAroundX(1.5707964F);
         if(var7 == 2) {
            var27[var31].rotateAroundY(0.0F);
         }

         if(var7 == 0) {
            var27[var31].rotateAroundY(3.1415927F);
         }

         if(var7 == 1) {
            var27[var31].rotateAroundY(1.5707964F);
         }

         if(var7 == 3) {
            var27[var31].rotateAroundY(-1.5707964F);
         }

         var27[var31].xCoord += (double)par2 + 0.5D;
         var27[var31].yCoord += (double)((float)par3 + 0.3125F);
         var27[var31].zCoord += (double)par4 + 0.5D;
      }

      Vec3 var62 = null;
      Vec3 var32 = null;
      Vec3 var33 = null;
      Vec3 var34 = null;
      byte var35 = 7;
      byte var36 = 9;
      byte var37 = 9;
      byte var38 = 16;

      for(int var39 = 0; var39 < 6; ++var39) {
         if(var39 == 0) {
            var62 = var27[0];
            var32 = var27[1];
            var33 = var27[2];
            var34 = var27[3];
            var19 = (double)var18.getInterpolatedU((double)var35);
            var21 = (double)var18.getInterpolatedV((double)var37);
            var23 = (double)var18.getInterpolatedU((double)var36);
            var25 = (double)var18.getInterpolatedV((double)(var37 + 2));
         } else if(var39 == 1) {
            var62 = var27[7];
            var32 = var27[6];
            var33 = var27[5];
            var34 = var27[4];
         } else if(var39 == 2) {
            var62 = var27[1];
            var32 = var27[0];
            var33 = var27[4];
            var34 = var27[5];
            var19 = (double)var18.getInterpolatedU((double)var35);
            var21 = (double)var18.getInterpolatedV((double)var37);
            var23 = (double)var18.getInterpolatedU((double)var36);
            var25 = (double)var18.getInterpolatedV((double)var38);
         } else if(var39 == 3) {
            var62 = var27[2];
            var32 = var27[1];
            var33 = var27[5];
            var34 = var27[6];
         } else if(var39 == 4) {
            var62 = var27[3];
            var32 = var27[2];
            var33 = var27[6];
            var34 = var27[7];
         } else if(var39 == 5) {
            var62 = var27[0];
            var32 = var27[3];
            var33 = var27[7];
            var34 = var27[4];
         }

         var5.addVertexWithUV(var62.xCoord, var62.yCoord, var62.zCoord, var19, var25);
         var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var23, var25);
         var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var23, var21);
         var5.addVertexWithUV(var34.xCoord, var34.yCoord, var34.zCoord, var19, var21);
      }

      float var63 = 0.09375F;
      float var40 = 0.09375F;
      float var41 = 0.03125F;
      var27[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var63), 0.0D, (double)(-var40));
      var27[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var63, 0.0D, (double)(-var40));
      var27[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var63, 0.0D, (double)var40);
      var27[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var63), 0.0D, (double)var40);
      var27[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var63), (double)var41, (double)(-var40));
      var27[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var63, (double)var41, (double)(-var40));
      var27[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var63, (double)var41, (double)var40);
      var27[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var63), (double)var41, (double)var40);

      for(int var42 = 0; var42 < 8; ++var42) {
         var27[var42].zCoord += 0.21875D;
         if(var9) {
            var27[var42].yCoord -= 0.09375D;
            var27[var42].zCoord -= 0.1625D;
            var27[var42].rotateAroundX(0.0F);
         } else if(var8) {
            var27[var42].yCoord += 0.015625D;
            var27[var42].zCoord -= 0.171875D;
            var27[var42].rotateAroundX(0.17453294F);
         } else {
            var27[var42].rotateAroundX(0.87266463F);
         }

         if(var7 == 2) {
            var27[var42].rotateAroundY(0.0F);
         }

         if(var7 == 0) {
            var27[var42].rotateAroundY(3.1415927F);
         }

         if(var7 == 1) {
            var27[var42].rotateAroundY(1.5707964F);
         }

         if(var7 == 3) {
            var27[var42].rotateAroundY(-1.5707964F);
         }

         var27[var42].xCoord += (double)par2 + 0.5D;
         var27[var42].yCoord += (double)((float)par3 + 0.3125F);
         var27[var42].zCoord += (double)par4 + 0.5D;
      }

      byte var64 = 5;
      byte var43 = 11;
      byte var44 = 3;
      byte var45 = 9;

      for(int var46 = 0; var46 < 6; ++var46) {
         if(var46 == 0) {
            var62 = var27[0];
            var32 = var27[1];
            var33 = var27[2];
            var34 = var27[3];
            var19 = (double)var18.getInterpolatedU((double)var64);
            var21 = (double)var18.getInterpolatedV((double)var44);
            var23 = (double)var18.getInterpolatedU((double)var43);
            var25 = (double)var18.getInterpolatedV((double)var45);
         } else if(var46 == 1) {
            var62 = var27[7];
            var32 = var27[6];
            var33 = var27[5];
            var34 = var27[4];
         } else if(var46 == 2) {
            var62 = var27[1];
            var32 = var27[0];
            var33 = var27[4];
            var34 = var27[5];
            var19 = (double)var18.getInterpolatedU((double)var64);
            var21 = (double)var18.getInterpolatedV((double)var44);
            var23 = (double)var18.getInterpolatedU((double)var43);
            var25 = (double)var18.getInterpolatedV((double)(var44 + 2));
         } else if(var46 == 3) {
            var62 = var27[2];
            var32 = var27[1];
            var33 = var27[5];
            var34 = var27[6];
         } else if(var46 == 4) {
            var62 = var27[3];
            var32 = var27[2];
            var33 = var27[6];
            var34 = var27[7];
         } else if(var46 == 5) {
            var62 = var27[0];
            var32 = var27[3];
            var33 = var27[7];
            var34 = var27[4];
         }

         var5.addVertexWithUV(var62.xCoord, var62.yCoord, var62.zCoord, var19, var25);
         var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var23, var25);
         var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var23, var21);
         var5.addVertexWithUV(var34.xCoord, var34.yCoord, var34.zCoord, var19, var21);
      }

      if(var8) {
         double var65 = var27[0].yCoord;
         float var48 = 0.03125F;
         float var49 = 0.5F - var48 / 2.0F;
         float var50 = var49 + var48;
         Icon var51 = this.getBlockIcon(Block.tripWire);
         double var52 = (double)var18.getMinU();
         double var54 = (double)var18.getInterpolatedV(var8?2.0D:0.0D);
         double var56 = (double)var18.getMaxU();
         double var58 = (double)var18.getInterpolatedV(var8?4.0D:2.0D);
         double var60 = (double)(var10?3.5F:1.5F) / 16.0D;
         var17 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4) * 0.75F;
         var5.setColorOpaque_F(var17, var17, var17);
         if(var7 == 2) {
            var5.addVertexWithUV((double)((float)par2 + var49), (double)par3 + var60, (double)par4 + 0.25D, var52, var54);
            var5.addVertexWithUV((double)((float)par2 + var50), (double)par3 + var60, (double)par4 + 0.25D, var52, var58);
            var5.addVertexWithUV((double)((float)par2 + var50), (double)par3 + var60, (double)par4, var56, var58);
            var5.addVertexWithUV((double)((float)par2 + var49), (double)par3 + var60, (double)par4, var56, var54);
            var5.addVertexWithUV((double)((float)par2 + var49), var65, (double)par4 + 0.5D, var52, var54);
            var5.addVertexWithUV((double)((float)par2 + var50), var65, (double)par4 + 0.5D, var52, var58);
            var5.addVertexWithUV((double)((float)par2 + var50), (double)par3 + var60, (double)par4 + 0.25D, var56, var58);
            var5.addVertexWithUV((double)((float)par2 + var49), (double)par3 + var60, (double)par4 + 0.25D, var56, var54);
         } else if(var7 == 0) {
            var5.addVertexWithUV((double)((float)par2 + var49), (double)par3 + var60, (double)par4 + 0.75D, var52, var54);
            var5.addVertexWithUV((double)((float)par2 + var50), (double)par3 + var60, (double)par4 + 0.75D, var52, var58);
            var5.addVertexWithUV((double)((float)par2 + var50), var65, (double)par4 + 0.5D, var56, var58);
            var5.addVertexWithUV((double)((float)par2 + var49), var65, (double)par4 + 0.5D, var56, var54);
            var5.addVertexWithUV((double)((float)par2 + var49), (double)par3 + var60, (double)(par4 + 1), var52, var54);
            var5.addVertexWithUV((double)((float)par2 + var50), (double)par3 + var60, (double)(par4 + 1), var52, var58);
            var5.addVertexWithUV((double)((float)par2 + var50), (double)par3 + var60, (double)par4 + 0.75D, var56, var58);
            var5.addVertexWithUV((double)((float)par2 + var49), (double)par3 + var60, (double)par4 + 0.75D, var56, var54);
         } else if(var7 == 1) {
            var5.addVertexWithUV((double)par2, (double)par3 + var60, (double)((float)par4 + var50), var52, var58);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var60, (double)((float)par4 + var50), var56, var58);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var60, (double)((float)par4 + var49), var56, var54);
            var5.addVertexWithUV((double)par2, (double)par3 + var60, (double)((float)par4 + var49), var52, var54);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var60, (double)((float)par4 + var50), var52, var58);
            var5.addVertexWithUV((double)par2 + 0.5D, var65, (double)((float)par4 + var50), var56, var58);
            var5.addVertexWithUV((double)par2 + 0.5D, var65, (double)((float)par4 + var49), var56, var54);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var60, (double)((float)par4 + var49), var52, var54);
         } else {
            var5.addVertexWithUV((double)par2 + 0.5D, var65, (double)((float)par4 + var50), var52, var58);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var60, (double)((float)par4 + var50), var56, var58);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var60, (double)((float)par4 + var49), var56, var54);
            var5.addVertexWithUV((double)par2 + 0.5D, var65, (double)((float)par4 + var49), var52, var54);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var60, (double)((float)par4 + var50), var52, var58);
            var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var60, (double)((float)par4 + var50), var56, var58);
            var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var60, (double)((float)par4 + var49), var56, var54);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var60, (double)((float)par4 + var49), var52, var54);
         }
      }

      return true;
   }

   public boolean renderBlockTripWire(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      Icon var6 = this.getBlockIconFromSide(par1Block, 0);
      int var7 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      boolean var8 = (var7 & 4) == 4;
      boolean var9 = (var7 & 2) == 2;
      if(this.hasOverrideBlockTexture()) {
         var6 = this.overrideBlockTexture;
      }

      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var10 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4) * 0.75F;
      var5.setColorOpaque_F(var10, var10, var10);
      double var11 = (double)var6.getMinU();
      double var13 = (double)var6.getInterpolatedV(var8?2.0D:0.0D);
      double var15 = (double)var6.getMaxU();
      double var17 = (double)var6.getInterpolatedV(var8?4.0D:2.0D);
      double var19 = (double)(var9?3.5F:1.5F) / 16.0D;
      boolean var21 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 1);
      boolean var22 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 3);
      boolean var23 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 2);
      boolean var24 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 0);
      float var25 = 0.03125F;
      float var26 = 0.5F - var25 / 2.0F;
      float var27 = var26 + var25;
      if(!var23 && !var22 && !var24 && !var21) {
         var23 = true;
         var24 = true;
      }

      if(var23) {
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.25D, var11, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.25D, var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.25D, var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.25D, var11, var13);
      }

      if(var23 || var24 && !var22 && !var21) {
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.5D, var11, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.5D, var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.25D, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.25D, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.25D, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.25D, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.5D, var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.5D, var11, var13);
      }

      if(var24 || var23 && !var22 && !var21) {
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.75D, var11, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.75D, var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.5D, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.5D, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.5D, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.5D, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.75D, var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.75D, var11, var13);
      }

      if(var24) {
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)(par4 + 1), var11, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)(par4 + 1), var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.75D, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.75D, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)par4 + 0.75D, var15, var13);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)par4 + 0.75D, var15, var17);
         var5.addVertexWithUV((double)((float)par2 + var27), (double)par3 + var19, (double)(par4 + 1), var11, var17);
         var5.addVertexWithUV((double)((float)par2 + var26), (double)par3 + var19, (double)(par4 + 1), var11, var13);
      }

      if(var21) {
         var5.addVertexWithUV((double)par2, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)par2, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)par2, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)par2, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
      }

      if(var21 || var22 && !var23 && !var24) {
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
      }

      if(var22 || var21 && !var23 && !var24) {
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
      }

      if(var22) {
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
         var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var26), var11, var13);
         var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var19, (double)((float)par4 + var26), var15, var13);
         var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var19, (double)((float)par4 + var27), var15, var17);
         var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var19, (double)((float)par4 + var27), var11, var17);
      }

      return true;
   }

   public boolean renderBlockFire(BlockFire par1BlockFire, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      Icon var6 = par1BlockFire.getFireIcon(0);
      Icon var7 = par1BlockFire.getFireIcon(1);
      Icon var8 = var6;
      if(this.hasOverrideBlockTexture()) {
         var8 = this.overrideBlockTexture;
      }

      var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      var5.setBrightness(par1BlockFire.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      double var9 = (double)var8.getMinU();
      double var11 = (double)var8.getMinV();
      double var13 = (double)var8.getMaxU();
      double var15 = (double)var8.getMaxV();
      float var17 = 1.4F;
      double var18;
      double var20;
      double var22;
      double var24;
      double var26;
      double var28;
      double var30;
      if(!this.blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 - 1, par4)) {
         float var36 = 0.2F;
         float var33 = 0.0625F;
         if((par2 + par3 + par4 & 1) == 1) {
            var9 = (double)var7.getMinU();
            var11 = (double)var7.getMinV();
            var13 = (double)var7.getMaxU();
            var15 = (double)var7.getMaxV();
         }

         if((par2 / 2 + par3 / 2 + par4 / 2 & 1) == 1) {
            var20 = var13;
            var13 = var9;
            var9 = var20;
         }

         if(Block.fire.canBlockCatchFire(this.blockAccess, par2 - 1, par3, par4)) {
            var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var13, var11);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var13, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var11);
            var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var11);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var13, var15);
            var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var13, var11);
         }

         if(Block.fire.canBlockCatchFire(this.blockAccess, par2 + 1, par3, par4)) {
            var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var11);
            var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var13, var15);
            var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var13, var11);
            var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var13, var11);
            var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var13, var15);
            var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var11);
         }

         if(Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 - 1)) {
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var13, var11);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var13, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var9, var11);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var9, var11);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var13, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var13, var11);
         }

         if(Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 + 1)) {
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var9, var11);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var9, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var13, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var13, var11);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var13, var11);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var13, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var9, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var9, var11);
         }

         if(Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 + 1, par4)) {
            var20 = (double)par2 + 0.5D + 0.5D;
            var22 = (double)par2 + 0.5D - 0.5D;
            var24 = (double)par4 + 0.5D + 0.5D;
            var26 = (double)par4 + 0.5D - 0.5D;
            var28 = (double)par2 + 0.5D - 0.5D;
            var30 = (double)par2 + 0.5D + 0.5D;
            var18 = (double)par4 + 0.5D - 0.5D;
            double var34 = (double)par4 + 0.5D + 0.5D;
            var9 = (double)var6.getMinU();
            var11 = (double)var6.getMinV();
            var13 = (double)var6.getMaxU();
            var15 = (double)var6.getMaxV();
            ++par3;
            var17 = -0.2F;
            if((par2 + par3 + par4 & 1) == 0) {
               var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 0), var13, var11);
               var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 0), var13, var15);
               var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 1), var9, var15);
               var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 1), var9, var11);
               var9 = (double)var7.getMinU();
               var11 = (double)var7.getMinV();
               var13 = (double)var7.getMaxU();
               var15 = (double)var7.getMaxV();
               var5.addVertexWithUV(var30, (double)((float)par3 + var17), (double)(par4 + 1), var13, var11);
               var5.addVertexWithUV(var22, (double)(par3 + 0), (double)(par4 + 1), var13, var15);
               var5.addVertexWithUV(var22, (double)(par3 + 0), (double)(par4 + 0), var9, var15);
               var5.addVertexWithUV(var30, (double)((float)par3 + var17), (double)(par4 + 0), var9, var11);
            } else {
               var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var34, var13, var11);
               var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var26, var13, var15);
               var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var26, var9, var15);
               var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var34, var9, var11);
               var9 = (double)var7.getMinU();
               var11 = (double)var7.getMinV();
               var13 = (double)var7.getMaxU();
               var15 = (double)var7.getMaxV();
               var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var18, var13, var11);
               var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var24, var13, var15);
               var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var24, var9, var15);
               var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var18, var9, var11);
            }
         }
      } else {
         double var32 = (double)par2 + 0.5D + 0.2D;
         var20 = (double)par2 + 0.5D - 0.2D;
         var22 = (double)par4 + 0.5D + 0.2D;
         var24 = (double)par4 + 0.5D - 0.2D;
         var26 = (double)par2 + 0.5D - 0.3D;
         var28 = (double)par2 + 0.5D + 0.3D;
         var30 = (double)par4 + 0.5D - 0.3D;
         var18 = (double)par4 + 0.5D + 0.3D;
         var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 1), var13, var11);
         var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 1), var13, var15);
         var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 0), var9, var15);
         var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 0), var9, var11);
         var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 0), var13, var11);
         var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 0), var13, var15);
         var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 1), var9, var15);
         var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 1), var9, var11);
         var9 = (double)var7.getMinU();
         var11 = (double)var7.getMinV();
         var13 = (double)var7.getMaxU();
         var15 = (double)var7.getMaxV();
         var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var18, var13, var11);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var24, var13, var15);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var24, var9, var15);
         var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var18, var9, var11);
         var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var30, var13, var11);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var22, var13, var15);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var22, var9, var15);
         var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var30, var9, var11);
         var32 = (double)par2 + 0.5D - 0.5D;
         var20 = (double)par2 + 0.5D + 0.5D;
         var22 = (double)par4 + 0.5D - 0.5D;
         var24 = (double)par4 + 0.5D + 0.5D;
         var26 = (double)par2 + 0.5D - 0.4D;
         var28 = (double)par2 + 0.5D + 0.4D;
         var30 = (double)par4 + 0.5D - 0.4D;
         var18 = (double)par4 + 0.5D + 0.4D;
         var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 0), var9, var11);
         var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 0), var9, var15);
         var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 1), var13, var15);
         var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 1), var13, var11);
         var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 1), var9, var11);
         var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 1), var9, var15);
         var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 0), var13, var15);
         var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 0), var13, var11);
         var9 = (double)var6.getMinU();
         var11 = (double)var6.getMinV();
         var13 = (double)var6.getMaxU();
         var15 = (double)var6.getMaxV();
         var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var18, var9, var11);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var24, var9, var15);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var24, var13, var15);
         var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var18, var13, var11);
         var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var30, var9, var11);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var22, var9, var15);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var22, var13, var15);
         var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var30, var13, var11);
      }

      return true;
   }

   public boolean renderBlockRedstoneWire(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      Icon var7 = BlockRedstoneWire.getRedstoneWireIcon("cross");
      Icon var8 = BlockRedstoneWire.getRedstoneWireIcon("line");
      Icon var9 = BlockRedstoneWire.getRedstoneWireIcon("cross_overlay");
      Icon var10 = BlockRedstoneWire.getRedstoneWireIcon("line_overlay");
      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var11 = 1.0F;
      float var12 = (float)var6 / 15.0F;
      float var13 = var12 * 0.6F + 0.4F;
      if(var6 == 0) {
         var13 = 0.3F;
      }

      float var14 = var12 * var12 * 0.7F - 0.5F;
      float var15 = var12 * var12 * 0.6F - 0.7F;
      if(var14 < 0.0F) {
         var14 = 0.0F;
      }

      if(var15 < 0.0F) {
         var15 = 0.0F;
      }

      var5.setColorOpaque_F(var13, var14, var15);
      boolean var16 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3, par4, 1) || !this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 - 1, par4, -1);
      boolean var17 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3, par4, 3) || !this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 - 1, par4, -1);
      boolean var18 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 - 1, 2) || !this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 - 1, -1);
      boolean var19 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 + 1, 0) || !this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 + 1, -1);
      if(!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
         if(this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 + 1, par4, -1)) {
            var16 = true;
         }

         if(this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 + 1, par4, -1)) {
            var17 = true;
         }

         if(this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 - 1, -1)) {
            var18 = true;
         }

         if(this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 + 1, -1)) {
            var19 = true;
         }
      }

      float var20 = (float)(par2 + 0);
      float var21 = (float)(par2 + 1);
      float var22 = (float)(par4 + 0);
      float var23 = (float)(par4 + 1);
      if((var16 || var17) && !var18 && !var19) {
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var23, (double)var8.getMaxU(), (double)var8.getMaxV());
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var22, (double)var8.getMaxU(), (double)var8.getMinV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var22, (double)var8.getMinU(), (double)var8.getMinV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var23, (double)var8.getMinU(), (double)var8.getMaxV());
         var5.setColorOpaque_F(var11, var11, var11);
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var23, (double)var10.getMaxU(), (double)var10.getMaxV());
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var22, (double)var10.getMaxU(), (double)var10.getMinV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var22, (double)var10.getMinU(), (double)var10.getMinV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var23, (double)var10.getMinU(), (double)var10.getMaxV());
      } else if((var18 || var19) && !var17 && !var16) {
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var23, (double)var8.getMaxU(), (double)var8.getMaxV());
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var22, (double)var8.getMinU(), (double)var8.getMaxV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var22, (double)var8.getMinU(), (double)var8.getMinV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var23, (double)var8.getMaxU(), (double)var8.getMinV());
         var5.setColorOpaque_F(var11, var11, var11);
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var23, (double)var10.getMaxU(), (double)var10.getMaxV());
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var22, (double)var10.getMinU(), (double)var10.getMaxV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var22, (double)var10.getMinU(), (double)var10.getMinV());
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var23, (double)var10.getMaxU(), (double)var10.getMinV());
      } else {
         int var24 = 0;
         int var25 = 0;
         int var26 = 16;
         int var27 = 16;
         if(!var16) {
            var20 += 0.3125F;
            var24 += 5;
         }

         if(!var17) {
            var21 -= 0.3125F;
            var26 -= 5;
         }

         if(!var18) {
            var22 += 0.3125F;
            var25 += 5;
         }

         if(!var19) {
            var23 -= 0.3125F;
            var27 -= 5;
         }

         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var23, (double)var7.getInterpolatedU((double)var26), (double)var7.getInterpolatedV((double)var27));
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var22, (double)var7.getInterpolatedU((double)var26), (double)var7.getInterpolatedV((double)var25));
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var22, (double)var7.getInterpolatedU((double)var24), (double)var7.getInterpolatedV((double)var25));
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var23, (double)var7.getInterpolatedU((double)var24), (double)var7.getInterpolatedV((double)var27));
         var5.setColorOpaque_F(var11, var11, var11);
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var23, (double)var9.getInterpolatedU((double)var26), (double)var9.getInterpolatedV((double)var27));
         var5.addVertexWithUV((double)var21, (double)par3 + 0.015625D, (double)var22, (double)var9.getInterpolatedU((double)var26), (double)var9.getInterpolatedV((double)var25));
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var22, (double)var9.getInterpolatedU((double)var24), (double)var9.getInterpolatedV((double)var25));
         var5.addVertexWithUV((double)var20, (double)par3 + 0.015625D, (double)var23, (double)var9.getInterpolatedU((double)var24), (double)var9.getInterpolatedV((double)var27));
      }

      if(!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
         if(this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4) == Block.redstoneWire.blockID) {
            var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)var8.getMaxU(), (double)var8.getMinV());
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)var8.getMinU(), (double)var8.getMinV());
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)var8.getMinU(), (double)var8.getMaxV());
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)var8.getMaxU(), (double)var8.getMaxV());
            var5.setColorOpaque_F(var11, var11, var11);
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)var10.getMaxU(), (double)var10.getMinV());
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)var10.getMinU(), (double)var10.getMinV());
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)var10.getMinU(), (double)var10.getMaxV());
            var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)var10.getMaxU(), (double)var10.getMaxV());
         }

         if(this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4) == Block.redstoneWire.blockID) {
            var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)var8.getMinU(), (double)var8.getMaxV());
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)var8.getMaxU(), (double)var8.getMaxV());
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)var8.getMaxU(), (double)var8.getMinV());
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)var8.getMinU(), (double)var8.getMinV());
            var5.setColorOpaque_F(var11, var11, var11);
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)var10.getMinU(), (double)var10.getMaxV());
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)var10.getMaxU(), (double)var10.getMaxV());
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)var10.getMaxU(), (double)var10.getMinV());
            var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)var10.getMinU(), (double)var10.getMinV());
         }

         if(this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1) == Block.redstoneWire.blockID) {
            var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + 0.015625D, (double)var8.getMinU(), (double)var8.getMaxV());
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)var8.getMaxU(), (double)var8.getMaxV());
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)var8.getMaxU(), (double)var8.getMinV());
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + 0.015625D, (double)var8.getMinU(), (double)var8.getMinV());
            var5.setColorOpaque_F(var11, var11, var11);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + 0.015625D, (double)var10.getMinU(), (double)var10.getMaxV());
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)var10.getMaxU(), (double)var10.getMaxV());
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)var10.getMaxU(), (double)var10.getMinV());
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + 0.015625D, (double)var10.getMinU(), (double)var10.getMinV());
         }

         if(this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1) == Block.redstoneWire.blockID) {
            var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)var8.getMaxU(), (double)var8.getMinV());
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)var8.getMinU(), (double)var8.getMinV());
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)var8.getMinU(), (double)var8.getMaxV());
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)var8.getMaxU(), (double)var8.getMaxV());
            var5.setColorOpaque_F(var11, var11, var11);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)var10.getMaxU(), (double)var10.getMinV());
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)var10.getMinU(), (double)var10.getMinV());
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)var10.getMinU(), (double)var10.getMaxV());
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)var10.getMaxU(), (double)var10.getMaxV());
         }
      }

      return true;
   }

   public boolean renderBlockMinecartTrack(BlockRailBase par1BlockRailBase, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      Icon var7 = this.getBlockIconFromSideAndMetadata(par1BlockRailBase, 0, var6);
      if(this.hasOverrideBlockTexture()) {
         var7 = this.overrideBlockTexture;
      }

      if(par1BlockRailBase.isPowered()) {
         var6 &= 7;
      }

      var5.setBrightness(par1BlockRailBase.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      double var8 = (double)var7.getMinU();
      double var10 = (double)var7.getMinV();
      double var12 = (double)var7.getMaxU();
      double var14 = (double)var7.getMaxV();
      double var16 = 0.0625D;
      double var18 = (double)(par2 + 1);
      double var20 = (double)(par2 + 1);
      double var22 = (double)(par2 + 0);
      double var24 = (double)(par2 + 0);
      double var26 = (double)(par4 + 0);
      double var28 = (double)(par4 + 1);
      double var30 = (double)(par4 + 1);
      double var32 = (double)(par4 + 0);
      double var34 = (double)par3 + var16;
      double var36 = (double)par3 + var16;
      double var38 = (double)par3 + var16;
      double var40 = (double)par3 + var16;
      if(var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7) {
         if(var6 == 8) {
            var18 = var20 = (double)(par2 + 0);
            var22 = var24 = (double)(par2 + 1);
            var26 = var32 = (double)(par4 + 1);
            var28 = var30 = (double)(par4 + 0);
         } else if(var6 == 9) {
            var18 = var24 = (double)(par2 + 0);
            var20 = var22 = (double)(par2 + 1);
            var26 = var28 = (double)(par4 + 0);
            var30 = var32 = (double)(par4 + 1);
         }
      } else {
         var18 = var24 = (double)(par2 + 1);
         var20 = var22 = (double)(par2 + 0);
         var26 = var28 = (double)(par4 + 1);
         var30 = var32 = (double)(par4 + 0);
      }

      if(var6 != 2 && var6 != 4) {
         if(var6 == 3 || var6 == 5) {
            ++var36;
            ++var38;
         }
      } else {
         ++var34;
         ++var40;
      }

      var5.addVertexWithUV(var18, var34, var26, var12, var10);
      var5.addVertexWithUV(var20, var36, var28, var12, var14);
      var5.addVertexWithUV(var22, var38, var30, var8, var14);
      var5.addVertexWithUV(var24, var40, var32, var8, var10);
      var5.addVertexWithUV(var24, var40, var32, var8, var10);
      var5.addVertexWithUV(var22, var38, var30, var8, var14);
      var5.addVertexWithUV(var20, var36, var28, var12, var14);
      var5.addVertexWithUV(var18, var34, var26, var12, var10);
      return true;
   }

   public boolean renderBlockLadder(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      Icon var6 = this.getBlockIconFromSide(par1Block, 0);
      if(this.hasOverrideBlockTexture()) {
         var6 = this.overrideBlockTexture;
      }

      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var7 = 1.0F;
      var5.setColorOpaque_F(var7, var7, var7);
      double var8 = (double)var6.getMinU();
      double var10 = (double)var6.getMinV();
      double var12 = (double)var6.getMaxU();
      double var14 = (double)var6.getMaxV();
      int var16 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      double var17 = 0.0D;
      double var19 = 0.05000000074505806D;
      if(var16 == 5) {
         var5.addVertexWithUV((double)par2 + var19, (double)(par3 + 1) + var17, (double)(par4 + 1) + var17, var8, var10);
         var5.addVertexWithUV((double)par2 + var19, (double)(par3 + 0) - var17, (double)(par4 + 1) + var17, var8, var14);
         var5.addVertexWithUV((double)par2 + var19, (double)(par3 + 0) - var17, (double)(par4 + 0) - var17, var12, var14);
         var5.addVertexWithUV((double)par2 + var19, (double)(par3 + 1) + var17, (double)(par4 + 0) - var17, var12, var10);
      }

      if(var16 == 4) {
         var5.addVertexWithUV((double)(par2 + 1) - var19, (double)(par3 + 0) - var17, (double)(par4 + 1) + var17, var12, var14);
         var5.addVertexWithUV((double)(par2 + 1) - var19, (double)(par3 + 1) + var17, (double)(par4 + 1) + var17, var12, var10);
         var5.addVertexWithUV((double)(par2 + 1) - var19, (double)(par3 + 1) + var17, (double)(par4 + 0) - var17, var8, var10);
         var5.addVertexWithUV((double)(par2 + 1) - var19, (double)(par3 + 0) - var17, (double)(par4 + 0) - var17, var8, var14);
      }

      if(var16 == 3) {
         var5.addVertexWithUV((double)(par2 + 1) + var17, (double)(par3 + 0) - var17, (double)par4 + var19, var12, var14);
         var5.addVertexWithUV((double)(par2 + 1) + var17, (double)(par3 + 1) + var17, (double)par4 + var19, var12, var10);
         var5.addVertexWithUV((double)(par2 + 0) - var17, (double)(par3 + 1) + var17, (double)par4 + var19, var8, var10);
         var5.addVertexWithUV((double)(par2 + 0) - var17, (double)(par3 + 0) - var17, (double)par4 + var19, var8, var14);
      }

      if(var16 == 2) {
         var5.addVertexWithUV((double)(par2 + 1) + var17, (double)(par3 + 1) + var17, (double)(par4 + 1) - var19, var8, var10);
         var5.addVertexWithUV((double)(par2 + 1) + var17, (double)(par3 + 0) - var17, (double)(par4 + 1) - var19, var8, var14);
         var5.addVertexWithUV((double)(par2 + 0) - var17, (double)(par3 + 0) - var17, (double)(par4 + 1) - var19, var12, var14);
         var5.addVertexWithUV((double)(par2 + 0) - var17, (double)(par3 + 1) + var17, (double)(par4 + 1) - var19, var12, var10);
      }

      return true;
   }

   public boolean renderBlockVine(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      Icon var6 = this.getBlockIconFromSide(par1Block, 0);
      if(this.hasOverrideBlockTexture()) {
         var6 = this.overrideBlockTexture;
      }

      float var7 = 1.0F;
      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      int var8 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var9 = (float)(var8 >> 16 & 255) / 255.0F;
      float var10 = (float)(var8 >> 8 & 255) / 255.0F;
      float var11 = (float)(var8 & 255) / 255.0F;
      var5.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
      double var12 = (double)var6.getMinU();
      double var14 = (double)var6.getMinV();
      double var16 = (double)var6.getMaxU();
      double var18 = (double)var6.getMaxV();
      double var20 = 0.05000000074505806D;
      int var22 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      if((var22 & 2) != 0) {
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 1), (double)(par4 + 1), var12, var14);
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 0), (double)(par4 + 1), var12, var18);
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 0), (double)(par4 + 0), var16, var18);
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 1), (double)(par4 + 0), var16, var14);
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 1), (double)(par4 + 0), var16, var14);
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 0), (double)(par4 + 0), var16, var18);
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 0), (double)(par4 + 1), var12, var18);
         var5.addVertexWithUV((double)par2 + var20, (double)(par3 + 1), (double)(par4 + 1), var12, var14);
      }

      if((var22 & 8) != 0) {
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 0), (double)(par4 + 1), var16, var18);
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 1), (double)(par4 + 1), var16, var14);
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 1), (double)(par4 + 0), var12, var14);
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 0), (double)(par4 + 0), var12, var18);
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 0), (double)(par4 + 0), var12, var18);
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 1), (double)(par4 + 0), var12, var14);
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 1), (double)(par4 + 1), var16, var14);
         var5.addVertexWithUV((double)(par2 + 1) - var20, (double)(par3 + 0), (double)(par4 + 1), var16, var18);
      }

      if((var22 & 4) != 0) {
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + var20, var16, var18);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)par4 + var20, var16, var14);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)par4 + var20, var12, var14);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + var20, var12, var18);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + var20, var12, var18);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)par4 + var20, var12, var14);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)par4 + var20, var16, var14);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + var20, var16, var18);
      }

      if((var22 & 1) != 0) {
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)(par4 + 1) - var20, var12, var14);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - var20, var12, var18);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - var20, var16, var18);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)(par4 + 1) - var20, var16, var14);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)(par4 + 1) - var20, var16, var14);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - var20, var16, var18);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - var20, var12, var18);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)(par4 + 1) - var20, var12, var14);
      }

      if(this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1) - var20, (double)(par4 + 0), var12, var14);
         var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1) - var20, (double)(par4 + 1), var12, var18);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1) - var20, (double)(par4 + 1), var16, var18);
         var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1) - var20, (double)(par4 + 0), var16, var14);
      }

      return true;
   }

   public boolean renderBlockPane(BlockPane par1BlockPane, int par2, int par3, int par4) {
      int var5 = this.blockAccess.getHeight();
      Tessellator var6 = Tessellator.instance;
      var6.setBrightness(par1BlockPane.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var7 = 1.0F;
      int var8 = par1BlockPane.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var9 = (float)(var8 >> 16 & 255) / 255.0F;
      float var10 = (float)(var8 >> 8 & 255) / 255.0F;
      float var11 = (float)(var8 & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
         float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
         float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
         var9 = var12;
         var10 = var13;
         var11 = var14;
      }

      var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
      Icon var64;
      Icon var65;
      if(this.hasOverrideBlockTexture()) {
         var64 = this.overrideBlockTexture;
         var65 = this.overrideBlockTexture;
      } else {
         int var66 = this.blockAccess.getBlockMetadata(par2, par3, par4);
         var64 = this.getBlockIconFromSideAndMetadata(par1BlockPane, 0, var66);
         var65 = par1BlockPane.getSideTextureIndex();
      }

      double var67 = (double)var64.getMinU();
      double var16 = (double)var64.getInterpolatedU(8.0D);
      double var18 = (double)var64.getMaxU();
      double var20 = (double)var64.getMinV();
      double var22 = (double)var64.getMaxV();
      double var24 = (double)var65.getInterpolatedU(7.0D);
      double var26 = (double)var65.getInterpolatedU(9.0D);
      double var28 = (double)var65.getMinV();
      double var30 = (double)var65.getInterpolatedV(8.0D);
      double var32 = (double)var65.getMaxV();
      double var34 = (double)par2;
      double var36 = (double)par2 + 0.5D;
      double var38 = (double)(par2 + 1);
      double var40 = (double)par4;
      double var42 = (double)par4 + 0.5D;
      double var44 = (double)(par4 + 1);
      double var46 = (double)par2 + 0.5D - 0.0625D;
      double var48 = (double)par2 + 0.5D + 0.0625D;
      double var50 = (double)par4 + 0.5D - 0.0625D;
      double var52 = (double)par4 + 0.5D + 0.0625D;
      boolean var54 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 - 1));
      boolean var55 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 + 1));
      boolean var56 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 - 1, par3, par4));
      boolean var57 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 + 1, par3, par4));
      boolean var58 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1);
      boolean var59 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0);
      double var60 = 0.01D;
      double var62 = 0.005D;
      if((!var56 || !var57) && (var56 || var57 || var54 || var55)) {
         if(var56 && !var57) {
            var6.addVertexWithUV(var34, (double)(par3 + 1), var42, var67, var20);
            var6.addVertexWithUV(var34, (double)(par3 + 0), var42, var67, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var16, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var16, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var67, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var67, var22);
            var6.addVertexWithUV(var34, (double)(par3 + 0), var42, var16, var22);
            var6.addVertexWithUV(var34, (double)(par3 + 1), var42, var16, var20);
            if(!var55 && !var54) {
               var6.addVertexWithUV(var36, (double)(par3 + 1), var52, var24, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var52, var24, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var50, var26, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1), var50, var26, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 1), var50, var24, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var50, var24, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var52, var26, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1), var52, var26, var28);
            }

            if(var58 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4)) {
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var30);
            }

            if(var59 || par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4)) {
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var30);
            }
         } else if(!var56 && var57) {
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var16, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var16, var22);
            var6.addVertexWithUV(var38, (double)(par3 + 0), var42, var18, var22);
            var6.addVertexWithUV(var38, (double)(par3 + 1), var42, var18, var20);
            var6.addVertexWithUV(var38, (double)(par3 + 1), var42, var16, var20);
            var6.addVertexWithUV(var38, (double)(par3 + 0), var42, var16, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var18, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var18, var20);
            if(!var55 && !var54) {
               var6.addVertexWithUV(var36, (double)(par3 + 1), var50, var24, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var50, var24, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var52, var26, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1), var52, var26, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 1), var52, var24, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var52, var24, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 0), var50, var26, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1), var50, var26, var28);
            }

            if(var58 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4)) {
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var28);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var50, var24, var28);
            }

            if(var59 || par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4)) {
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var28);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var50, var24, var28);
            }
         }
      } else {
         var6.addVertexWithUV(var34, (double)(par3 + 1), var42, var67, var20);
         var6.addVertexWithUV(var34, (double)(par3 + 0), var42, var67, var22);
         var6.addVertexWithUV(var38, (double)(par3 + 0), var42, var18, var22);
         var6.addVertexWithUV(var38, (double)(par3 + 1), var42, var18, var20);
         var6.addVertexWithUV(var38, (double)(par3 + 1), var42, var67, var20);
         var6.addVertexWithUV(var38, (double)(par3 + 0), var42, var67, var22);
         var6.addVertexWithUV(var34, (double)(par3 + 0), var42, var18, var22);
         var6.addVertexWithUV(var34, (double)(par3 + 1), var42, var18, var20);
         if(var58) {
            var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var52, var26, var32);
            var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var52, var26, var28);
            var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var50, var24, var28);
            var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var50, var24, var32);
            var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var52, var26, var32);
            var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var52, var26, var28);
            var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var50, var24, var28);
            var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var50, var24, var32);
         } else {
            if(par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4)) {
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var34, (double)(par3 + 1) + 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var30);
            }

            if(par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4)) {
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var28);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)(par3 + 1) + 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var38, (double)(par3 + 1) + 0.01D, var50, var24, var28);
            }
         }

         if(var59) {
            var6.addVertexWithUV(var34, (double)par3 - 0.01D, var52, var26, var32);
            var6.addVertexWithUV(var38, (double)par3 - 0.01D, var52, var26, var28);
            var6.addVertexWithUV(var38, (double)par3 - 0.01D, var50, var24, var28);
            var6.addVertexWithUV(var34, (double)par3 - 0.01D, var50, var24, var32);
            var6.addVertexWithUV(var38, (double)par3 - 0.01D, var52, var26, var32);
            var6.addVertexWithUV(var34, (double)par3 - 0.01D, var52, var26, var28);
            var6.addVertexWithUV(var34, (double)par3 - 0.01D, var50, var24, var28);
            var6.addVertexWithUV(var38, (double)par3 - 0.01D, var50, var24, var32);
         } else {
            if(par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4)) {
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var52, var26, var32);
               var6.addVertexWithUV(var34, (double)par3 - 0.01D, var50, var24, var32);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var30);
            }

            if(par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4)) {
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var28);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var52, var26, var28);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var52, var26, var30);
               var6.addVertexWithUV(var36, (double)par3 - 0.01D, var50, var24, var30);
               var6.addVertexWithUV(var38, (double)par3 - 0.01D, var50, var24, var28);
            }
         }
      }

      if((!var54 || !var55) && (var56 || var57 || var54 || var55)) {
         if(var54 && !var55) {
            var6.addVertexWithUV(var36, (double)(par3 + 1), var40, var67, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var40, var67, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var16, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var16, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var67, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var67, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var40, var16, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var40, var16, var20);
            if(!var57 && !var56) {
               var6.addVertexWithUV(var46, (double)(par3 + 1), var42, var24, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 0), var42, var24, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 0), var42, var26, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1), var42, var26, var28);
               var6.addVertexWithUV(var48, (double)(par3 + 1), var42, var24, var28);
               var6.addVertexWithUV(var48, (double)(par3 + 0), var42, var24, var32);
               var6.addVertexWithUV(var46, (double)(par3 + 0), var42, var26, var32);
               var6.addVertexWithUV(var46, (double)(par3 + 1), var42, var26, var28);
            }

            if(var58 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1)) {
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var40, var26, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var40, var24, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var26, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var40, var26, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var40, var24, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var24, var28);
            }

            if(var59 || par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1)) {
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var40, var26, var28);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var40, var24, var28);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var26, var28);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var40, var26, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var40, var24, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var24, var28);
            }
         } else if(!var54 && var55) {
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var16, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var16, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var44, var18, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var44, var18, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var44, var16, var20);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var44, var16, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 0), var42, var18, var22);
            var6.addVertexWithUV(var36, (double)(par3 + 1), var42, var18, var20);
            if(!var57 && !var56) {
               var6.addVertexWithUV(var48, (double)(par3 + 1), var42, var24, var28);
               var6.addVertexWithUV(var48, (double)(par3 + 0), var42, var24, var32);
               var6.addVertexWithUV(var46, (double)(par3 + 0), var42, var26, var32);
               var6.addVertexWithUV(var46, (double)(par3 + 1), var42, var26, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 1), var42, var24, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 0), var42, var24, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 0), var42, var26, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1), var42, var26, var28);
            }

            if(var58 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1)) {
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var44, var24, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var44, var26, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var44, var24, var30);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var24, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var26, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var44, var26, var30);
            }

            if(var59 || par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1)) {
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var44, var24, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var44, var26, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var44, var24, var30);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var24, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var26, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var44, var26, var30);
            }
         }
      } else {
         var6.addVertexWithUV(var36, (double)(par3 + 1), var44, var67, var20);
         var6.addVertexWithUV(var36, (double)(par3 + 0), var44, var67, var22);
         var6.addVertexWithUV(var36, (double)(par3 + 0), var40, var18, var22);
         var6.addVertexWithUV(var36, (double)(par3 + 1), var40, var18, var20);
         var6.addVertexWithUV(var36, (double)(par3 + 1), var40, var67, var20);
         var6.addVertexWithUV(var36, (double)(par3 + 0), var40, var67, var22);
         var6.addVertexWithUV(var36, (double)(par3 + 0), var44, var18, var22);
         var6.addVertexWithUV(var36, (double)(par3 + 1), var44, var18, var20);
         if(var58) {
            var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var44, var26, var32);
            var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var40, var26, var28);
            var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var40, var24, var28);
            var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var44, var24, var32);
            var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var40, var26, var32);
            var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var44, var26, var28);
            var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var44, var24, var28);
            var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var40, var24, var32);
         } else {
            if(par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1)) {
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var40, var26, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var40, var24, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var26, var28);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var40, var26, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var40, var24, var30);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var24, var28);
            }

            if(par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1)) {
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var44, var24, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var44, var26, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var44, var24, var30);
               var6.addVertexWithUV(var46, (double)(par3 + 1) + 0.005D, var42, var24, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var42, var26, var32);
               var6.addVertexWithUV(var48, (double)(par3 + 1) + 0.005D, var44, var26, var30);
            }
         }

         if(var59) {
            var6.addVertexWithUV(var48, (double)par3 - 0.005D, var44, var26, var32);
            var6.addVertexWithUV(var48, (double)par3 - 0.005D, var40, var26, var28);
            var6.addVertexWithUV(var46, (double)par3 - 0.005D, var40, var24, var28);
            var6.addVertexWithUV(var46, (double)par3 - 0.005D, var44, var24, var32);
            var6.addVertexWithUV(var48, (double)par3 - 0.005D, var40, var26, var32);
            var6.addVertexWithUV(var48, (double)par3 - 0.005D, var44, var26, var28);
            var6.addVertexWithUV(var46, (double)par3 - 0.005D, var44, var24, var28);
            var6.addVertexWithUV(var46, (double)par3 - 0.005D, var40, var24, var32);
         } else {
            if(par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1)) {
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var40, var26, var28);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var40, var24, var28);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var26, var28);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var40, var26, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var40, var24, var30);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var24, var28);
            }

            if(par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1)) {
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var24, var30);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var44, var24, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var44, var26, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var26, var30);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var44, var24, var30);
               var6.addVertexWithUV(var46, (double)par3 - 0.005D, var42, var24, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var42, var26, var32);
               var6.addVertexWithUV(var48, (double)par3 - 0.005D, var44, var26, var30);
            }
         }
      }

      return true;
   }

   public boolean renderCrossedSquares(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var6 = 1.0F;
      int var7 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var8 = (float)(var7 >> 16 & 255) / 255.0F;
      float var9 = (float)(var7 >> 8 & 255) / 255.0F;
      float var10 = (float)(var7 & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
         float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
         float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
         var8 = var11;
         var9 = var12;
         var10 = var13;
      }

      var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
      double var19 = (double)par2;
      double var20 = (double)par3;
      double var15 = (double)par4;
      if(par1Block == Block.tallGrass) {
         long var17 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
         var17 = var17 * var17 * 42317861L + var17 * 11L;
         var19 += ((double)((float)(var17 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
         var20 += ((double)((float)(var17 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
         var15 += ((double)((float)(var17 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
      }

      this.drawCrossedSquares(par1Block, this.blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F);
      return true;
   }

   public boolean renderBlockStem(Block par1Block, int par2, int par3, int par4) {
      BlockStem var5 = (BlockStem)par1Block;
      Tessellator var6 = Tessellator.instance;
      var6.setBrightness(var5.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var7 = 1.0F;
      int var8 = var5.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var9 = (float)(var8 >> 16 & 255) / 255.0F;
      float var10 = (float)(var8 >> 8 & 255) / 255.0F;
      float var11 = (float)(var8 & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
         float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
         float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
         var9 = var12;
         var10 = var13;
         var11 = var14;
      }

      var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
      var5.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
      int var15 = var5.getState(this.blockAccess, par2, par3, par4);
      if(var15 < 0) {
         this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), this.renderMaxY, (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
      } else {
         this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), 0.5D, (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
         this.renderBlockStemBig(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), var15, this.renderMaxY, (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
      }

      return true;
   }

   public boolean renderBlockCrops(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      this.renderBlockCropsImpl(par1Block, this.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
      return true;
   }

   public void renderTorchAtAngle(Block par1Block, double par2, double par4, double par6, double par8, double par10, int par12) {
      Tessellator var13 = Tessellator.instance;
      Icon var14 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par12);
      if(this.hasOverrideBlockTexture()) {
         var14 = this.overrideBlockTexture;
      }

      double var15 = (double)var14.getMinU();
      double var17 = (double)var14.getMinV();
      double var19 = (double)var14.getMaxU();
      double var21 = (double)var14.getMaxV();
      double var23 = (double)var14.getInterpolatedU(7.0D);
      double var25 = (double)var14.getInterpolatedV(6.0D);
      double var27 = (double)var14.getInterpolatedU(9.0D);
      double var29 = (double)var14.getInterpolatedV(8.0D);
      double var31 = (double)var14.getInterpolatedU(7.0D);
      double var33 = (double)var14.getInterpolatedV(13.0D);
      double var35 = (double)var14.getInterpolatedU(9.0D);
      double var37 = (double)var14.getInterpolatedV(15.0D);
      par2 += 0.5D;
      par6 += 0.5D;
      double var39 = par2 - 0.5D;
      double var41 = par2 + 0.5D;
      double var43 = par6 - 0.5D;
      double var45 = par6 + 0.5D;
      double var47 = 0.0625D;
      double var49 = 0.625D;
      var13.addVertexWithUV(par2 + par8 * (1.0D - var49) - var47, par4 + var49, par6 + par10 * (1.0D - var49) - var47, var23, var25);
      var13.addVertexWithUV(par2 + par8 * (1.0D - var49) - var47, par4 + var49, par6 + par10 * (1.0D - var49) + var47, var23, var29);
      var13.addVertexWithUV(par2 + par8 * (1.0D - var49) + var47, par4 + var49, par6 + par10 * (1.0D - var49) + var47, var27, var29);
      var13.addVertexWithUV(par2 + par8 * (1.0D - var49) + var47, par4 + var49, par6 + par10 * (1.0D - var49) - var47, var27, var25);
      var13.addVertexWithUV(par2 + var47 + par8, par4, par6 - var47 + par10, var35, var33);
      var13.addVertexWithUV(par2 + var47 + par8, par4, par6 + var47 + par10, var35, var37);
      var13.addVertexWithUV(par2 - var47 + par8, par4, par6 + var47 + par10, var31, var37);
      var13.addVertexWithUV(par2 - var47 + par8, par4, par6 - var47 + par10, var31, var33);
      var13.addVertexWithUV(par2 - var47, par4 + 1.0D, var43, var15, var17);
      var13.addVertexWithUV(par2 - var47 + par8, par4 + 0.0D, var43 + par10, var15, var21);
      var13.addVertexWithUV(par2 - var47 + par8, par4 + 0.0D, var45 + par10, var19, var21);
      var13.addVertexWithUV(par2 - var47, par4 + 1.0D, var45, var19, var17);
      var13.addVertexWithUV(par2 + var47, par4 + 1.0D, var45, var15, var17);
      var13.addVertexWithUV(par2 + par8 + var47, par4 + 0.0D, var45 + par10, var15, var21);
      var13.addVertexWithUV(par2 + par8 + var47, par4 + 0.0D, var43 + par10, var19, var21);
      var13.addVertexWithUV(par2 + var47, par4 + 1.0D, var43, var19, var17);
      var13.addVertexWithUV(var39, par4 + 1.0D, par6 + var47, var15, var17);
      var13.addVertexWithUV(var39 + par8, par4 + 0.0D, par6 + var47 + par10, var15, var21);
      var13.addVertexWithUV(var41 + par8, par4 + 0.0D, par6 + var47 + par10, var19, var21);
      var13.addVertexWithUV(var41, par4 + 1.0D, par6 + var47, var19, var17);
      var13.addVertexWithUV(var41, par4 + 1.0D, par6 - var47, var15, var17);
      var13.addVertexWithUV(var41 + par8, par4 + 0.0D, par6 - var47 + par10, var15, var21);
      var13.addVertexWithUV(var39 + par8, par4 + 0.0D, par6 - var47 + par10, var19, var21);
      var13.addVertexWithUV(var39, par4 + 1.0D, par6 - var47, var19, var17);
   }

   public void drawCrossedSquares(Block par1Block, int par2, double par3, double par5, double par7, float par9) {
      Tessellator var10 = Tessellator.instance;
      Icon var11 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par2);
      if(this.hasOverrideBlockTexture()) {
         var11 = this.overrideBlockTexture;
      }

      double var12 = (double)var11.getMinU();
      double var14 = (double)var11.getMinV();
      double var16 = (double)var11.getMaxU();
      double var18 = (double)var11.getMaxV();
      double var20 = 0.45D * (double)par9;
      double var22 = par3 + 0.5D - var20;
      double var24 = par3 + 0.5D + var20;
      double var26 = par7 + 0.5D - var20;
      double var28 = par7 + 0.5D + var20;
      var10.addVertexWithUV(var22, par5 + (double)par9, var26, var12, var14);
      var10.addVertexWithUV(var22, par5 + 0.0D, var26, var12, var18);
      var10.addVertexWithUV(var24, par5 + 0.0D, var28, var16, var18);
      var10.addVertexWithUV(var24, par5 + (double)par9, var28, var16, var14);
      var10.addVertexWithUV(var24, par5 + (double)par9, var28, var12, var14);
      var10.addVertexWithUV(var24, par5 + 0.0D, var28, var12, var18);
      var10.addVertexWithUV(var22, par5 + 0.0D, var26, var16, var18);
      var10.addVertexWithUV(var22, par5 + (double)par9, var26, var16, var14);
      var10.addVertexWithUV(var22, par5 + (double)par9, var28, var12, var14);
      var10.addVertexWithUV(var22, par5 + 0.0D, var28, var12, var18);
      var10.addVertexWithUV(var24, par5 + 0.0D, var26, var16, var18);
      var10.addVertexWithUV(var24, par5 + (double)par9, var26, var16, var14);
      var10.addVertexWithUV(var24, par5 + (double)par9, var26, var12, var14);
      var10.addVertexWithUV(var24, par5 + 0.0D, var26, var12, var18);
      var10.addVertexWithUV(var22, par5 + 0.0D, var28, var16, var18);
      var10.addVertexWithUV(var22, par5 + (double)par9, var28, var16, var14);
   }

   public void renderBlockStemSmall(Block par1Block, int par2, double par3, double par5, double par7, double par9) {
      Tessellator var11 = Tessellator.instance;
      Icon var12 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par2);
      if(this.hasOverrideBlockTexture()) {
         var12 = this.overrideBlockTexture;
      }

      double var13 = (double)var12.getMinU();
      double var15 = (double)var12.getMinV();
      double var17 = (double)var12.getMaxU();
      double var19 = (double)var12.getInterpolatedV(par3 * 16.0D);
      double var21 = par5 + 0.5D - 0.44999998807907104D;
      double var23 = par5 + 0.5D + 0.44999998807907104D;
      double var25 = par9 + 0.5D - 0.44999998807907104D;
      double var27 = par9 + 0.5D + 0.44999998807907104D;
      var11.addVertexWithUV(var21, par7 + par3, var25, var13, var15);
      var11.addVertexWithUV(var21, par7 + 0.0D, var25, var13, var19);
      var11.addVertexWithUV(var23, par7 + 0.0D, var27, var17, var19);
      var11.addVertexWithUV(var23, par7 + par3, var27, var17, var15);
      var11.addVertexWithUV(var23, par7 + par3, var27, var13, var15);
      var11.addVertexWithUV(var23, par7 + 0.0D, var27, var13, var19);
      var11.addVertexWithUV(var21, par7 + 0.0D, var25, var17, var19);
      var11.addVertexWithUV(var21, par7 + par3, var25, var17, var15);
      var11.addVertexWithUV(var21, par7 + par3, var27, var13, var15);
      var11.addVertexWithUV(var21, par7 + 0.0D, var27, var13, var19);
      var11.addVertexWithUV(var23, par7 + 0.0D, var25, var17, var19);
      var11.addVertexWithUV(var23, par7 + par3, var25, var17, var15);
      var11.addVertexWithUV(var23, par7 + par3, var25, var13, var15);
      var11.addVertexWithUV(var23, par7 + 0.0D, var25, var13, var19);
      var11.addVertexWithUV(var21, par7 + 0.0D, var27, var17, var19);
      var11.addVertexWithUV(var21, par7 + par3, var27, var17, var15);
   }

   public boolean renderBlockLilyPad(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      Icon var6 = this.getBlockIconFromSide(par1Block, 1);
      if(this.hasOverrideBlockTexture()) {
         var6 = this.overrideBlockTexture;
      }

      float var7 = 0.015625F;
      double var8 = (double)var6.getMinU();
      double var10 = (double)var6.getMinV();
      double var12 = (double)var6.getMaxU();
      double var14 = (double)var6.getMaxV();
      long var16 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
      var16 = var16 * var16 * 42317861L + var16 * 11L;
      int var18 = (int)(var16 >> 16 & 3L);
      var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var19 = (float)par2 + 0.5F;
      float var20 = (float)par4 + 0.5F;
      float var21 = (float)(var18 & 1) * 0.5F * (float)(1 - var18 / 2 % 2 * 2);
      float var22 = (float)(var18 + 1 & 1) * 0.5F * (float)(1 - (var18 + 1) / 2 % 2 * 2);
      var5.setColorOpaque_I(par1Block.getBlockColor());
      var5.addVertexWithUV((double)(var19 + var21 - var22), (double)((float)par3 + var7), (double)(var20 + var21 + var22), var8, var10);
      var5.addVertexWithUV((double)(var19 + var21 + var22), (double)((float)par3 + var7), (double)(var20 - var21 + var22), var12, var10);
      var5.addVertexWithUV((double)(var19 - var21 + var22), (double)((float)par3 + var7), (double)(var20 - var21 - var22), var12, var14);
      var5.addVertexWithUV((double)(var19 - var21 - var22), (double)((float)par3 + var7), (double)(var20 + var21 - var22), var8, var14);
      var5.setColorOpaque_I((par1Block.getBlockColor() & 16711422) >> 1);
      var5.addVertexWithUV((double)(var19 - var21 - var22), (double)((float)par3 + var7), (double)(var20 + var21 - var22), var8, var14);
      var5.addVertexWithUV((double)(var19 - var21 + var22), (double)((float)par3 + var7), (double)(var20 - var21 - var22), var12, var14);
      var5.addVertexWithUV((double)(var19 + var21 + var22), (double)((float)par3 + var7), (double)(var20 - var21 + var22), var12, var10);
      var5.addVertexWithUV((double)(var19 + var21 - var22), (double)((float)par3 + var7), (double)(var20 + var21 + var22), var8, var10);
      return true;
   }

   public void renderBlockStemBig(BlockStem par1BlockStem, int par2, int par3, double par4, double par6, double par8, double par10) {
      Tessellator var12 = Tessellator.instance;
      Icon var13 = par1BlockStem.getStemIcon();
      if(this.hasOverrideBlockTexture()) {
         var13 = this.overrideBlockTexture;
      }

      double var14 = (double)var13.getMinU();
      double var16 = (double)var13.getMinV();
      double var18 = (double)var13.getMaxU();
      double var20 = (double)var13.getMaxV();
      double var22 = par6 + 0.5D - 0.5D;
      double var24 = par6 + 0.5D + 0.5D;
      double var26 = par10 + 0.5D - 0.5D;
      double var28 = par10 + 0.5D + 0.5D;
      double var30 = par6 + 0.5D;
      double var32 = par10 + 0.5D;
      if((par3 + 1) / 2 % 2 == 1) {
         double var34 = var18;
         var18 = var14;
         var14 = var34;
      }

      if(par3 < 2) {
         var12.addVertexWithUV(var22, par8 + par4, var32, var14, var16);
         var12.addVertexWithUV(var22, par8 + 0.0D, var32, var14, var20);
         var12.addVertexWithUV(var24, par8 + 0.0D, var32, var18, var20);
         var12.addVertexWithUV(var24, par8 + par4, var32, var18, var16);
         var12.addVertexWithUV(var24, par8 + par4, var32, var18, var16);
         var12.addVertexWithUV(var24, par8 + 0.0D, var32, var18, var20);
         var12.addVertexWithUV(var22, par8 + 0.0D, var32, var14, var20);
         var12.addVertexWithUV(var22, par8 + par4, var32, var14, var16);
      } else {
         var12.addVertexWithUV(var30, par8 + par4, var28, var14, var16);
         var12.addVertexWithUV(var30, par8 + 0.0D, var28, var14, var20);
         var12.addVertexWithUV(var30, par8 + 0.0D, var26, var18, var20);
         var12.addVertexWithUV(var30, par8 + par4, var26, var18, var16);
         var12.addVertexWithUV(var30, par8 + par4, var26, var18, var16);
         var12.addVertexWithUV(var30, par8 + 0.0D, var26, var18, var20);
         var12.addVertexWithUV(var30, par8 + 0.0D, var28, var14, var20);
         var12.addVertexWithUV(var30, par8 + par4, var28, var14, var16);
      }

   }

   public void renderBlockCropsImpl(Block par1Block, int par2, double par3, double par5, double par7) {
      Tessellator var9 = Tessellator.instance;
      Icon var10 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par2);
      if(this.hasOverrideBlockTexture()) {
         var10 = this.overrideBlockTexture;
      }

      double var11 = (double)var10.getMinU();
      double var13 = (double)var10.getMinV();
      double var15 = (double)var10.getMaxU();
      double var17 = (double)var10.getMaxV();
      double var19 = par3 + 0.5D - 0.25D;
      double var21 = par3 + 0.5D + 0.25D;
      double var23 = par7 + 0.5D - 0.5D;
      double var25 = par7 + 0.5D + 0.5D;
      var9.addVertexWithUV(var19, par5 + 1.0D, var23, var11, var13);
      var9.addVertexWithUV(var19, par5 + 0.0D, var23, var11, var17);
      var9.addVertexWithUV(var19, par5 + 0.0D, var25, var15, var17);
      var9.addVertexWithUV(var19, par5 + 1.0D, var25, var15, var13);
      var9.addVertexWithUV(var19, par5 + 1.0D, var25, var11, var13);
      var9.addVertexWithUV(var19, par5 + 0.0D, var25, var11, var17);
      var9.addVertexWithUV(var19, par5 + 0.0D, var23, var15, var17);
      var9.addVertexWithUV(var19, par5 + 1.0D, var23, var15, var13);
      var9.addVertexWithUV(var21, par5 + 1.0D, var25, var11, var13);
      var9.addVertexWithUV(var21, par5 + 0.0D, var25, var11, var17);
      var9.addVertexWithUV(var21, par5 + 0.0D, var23, var15, var17);
      var9.addVertexWithUV(var21, par5 + 1.0D, var23, var15, var13);
      var9.addVertexWithUV(var21, par5 + 1.0D, var23, var11, var13);
      var9.addVertexWithUV(var21, par5 + 0.0D, var23, var11, var17);
      var9.addVertexWithUV(var21, par5 + 0.0D, var25, var15, var17);
      var9.addVertexWithUV(var21, par5 + 1.0D, var25, var15, var13);
      var19 = par3 + 0.5D - 0.5D;
      var21 = par3 + 0.5D + 0.5D;
      var23 = par7 + 0.5D - 0.25D;
      var25 = par7 + 0.5D + 0.25D;
      var9.addVertexWithUV(var19, par5 + 1.0D, var23, var11, var13);
      var9.addVertexWithUV(var19, par5 + 0.0D, var23, var11, var17);
      var9.addVertexWithUV(var21, par5 + 0.0D, var23, var15, var17);
      var9.addVertexWithUV(var21, par5 + 1.0D, var23, var15, var13);
      var9.addVertexWithUV(var21, par5 + 1.0D, var23, var11, var13);
      var9.addVertexWithUV(var21, par5 + 0.0D, var23, var11, var17);
      var9.addVertexWithUV(var19, par5 + 0.0D, var23, var15, var17);
      var9.addVertexWithUV(var19, par5 + 1.0D, var23, var15, var13);
      var9.addVertexWithUV(var21, par5 + 1.0D, var25, var11, var13);
      var9.addVertexWithUV(var21, par5 + 0.0D, var25, var11, var17);
      var9.addVertexWithUV(var19, par5 + 0.0D, var25, var15, var17);
      var9.addVertexWithUV(var19, par5 + 1.0D, var25, var15, var13);
      var9.addVertexWithUV(var19, par5 + 1.0D, var25, var11, var13);
      var9.addVertexWithUV(var19, par5 + 0.0D, var25, var11, var17);
      var9.addVertexWithUV(var21, par5 + 0.0D, var25, var15, var17);
      var9.addVertexWithUV(var21, par5 + 1.0D, var25, var15, var13);
   }

   public boolean renderBlockFluidsMain(Block par1Block, int par2, int par3, int par4) {
      boolean var10 = this.bSBTemp2;
      boolean var11 = this.bSBTemp1;
      boolean[] var12 = new boolean[]{this.bSBTemp3, this.bSBTemp4, this.bSBTemp5, this.bSBTemp6};
      Tessellator var5 = Tessellator.instance;
      if((this.bSITemp0 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4)) != this.bSITemp1) {
         this.bSFTemp1 = (float)((this.bSITemp1 = this.bSITemp0) >> 16 & 255) / 255.0F;
         this.bSFTemp2 = (float)(this.bSITemp1 >> 8 & 255) / 255.0F;
         this.bSFTemp3 = (float)(this.bSITemp1 & 255) / 255.0F;
      }

      float var7 = this.bSFTemp1;
      float var8 = this.bSFTemp2;
      float var9 = this.bSFTemp3;
      boolean var13 = false;
      float var14 = 0.5F;
      float var15 = 1.0F;
      float var16 = 0.8F;
      float var17 = 0.6F;
      double var18 = 0.0D;
      double var20 = 1.0D;
      Material var22 = par1Block.blockMaterial;
      int var23 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      double var24 = (double)this.getFluidHeight(par2, par3, par4, var22);
      double var26 = (double)this.getFluidHeight(par2, par3, par4 + 1, var22);
      double var28 = (double)this.getFluidHeight(par2 + 1, par3, par4 + 1, var22);
      double var30 = (double)this.getFluidHeight(par2 + 1, par3, par4, var22);
      double var32 = 0.0010000000474974513D;
      float var54;
      float var53;
      float var52;
      double var39;
      double var43;
      double var41;
      double var47;
      double var45;
      double var49;
      if(this.renderAllFaces || var10) {
         var13 = true;
         Icon var57 = this.getBlockIconFromSideAndMetadata(par1Block, 1, var23);
         float var59 = (float)BlockFluid.getFlowDirection(this.blockAccess, par2, par3, par4, var22);
         if(var59 > -999.0F) {
            var57 = this.getBlockIconFromSideAndMetadata(par1Block, 2, var23);
         }

         var24 -= var32;
         var26 -= var32;
         var28 -= var32;
         var30 -= var32;
         double var37;
         double var51;
         if(var59 < -999.0F) {
            var39 = (double)var57.getInterpolatedU(0.0D);
            var45 = (double)var57.getInterpolatedV(0.0D);
            var37 = var39;
            var47 = (double)var57.getInterpolatedV(16.0D);
            var41 = (double)var57.getInterpolatedU(16.0D);
            var51 = var47;
            var43 = var41;
            var49 = var45;
         } else {
            var52 = MathHelper.sin(var59) * 0.25F;
            var53 = MathHelper.cos(var59) * 0.25F;
            var54 = 8.0F;
            var39 = (double)var57.getInterpolatedU((double)(8.0F + (-var53 - var52) * 16.0F));
            var45 = (double)var57.getInterpolatedV((double)(8.0F + (-var53 + var52) * 16.0F));
            var37 = (double)var57.getInterpolatedU((double)(8.0F + (-var53 + var52) * 16.0F));
            var47 = (double)var57.getInterpolatedV((double)(8.0F + (var53 + var52) * 16.0F));
            var41 = (double)var57.getInterpolatedU((double)(8.0F + (var53 + var52) * 16.0F));
            var51 = (double)var57.getInterpolatedV((double)(8.0F + (var53 - var52) * 16.0F));
            var43 = (double)var57.getInterpolatedU((double)(8.0F + (var53 - var52) * 16.0F));
            var49 = (double)var57.getInterpolatedV((double)(8.0F + (-var53 - var52) * 16.0F));
         }

         var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
         var52 = 1.0F;
         var5.setColorOpaque_F(var15 * var52 * var7, var15 * var52 * var8, var15 * var52 * var9);
         var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var24, (double)(par4 + 0), var39, var45);
         var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var26, (double)(par4 + 1), var37, var47);
         var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var28, (double)(par4 + 1), var41, var51);
         var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var30, (double)(par4 + 0), var43, var49);
      }

      if(this.renderAllFaces || var11) {
         var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
         float var551 = 1.0F;
         var5.setColorOpaque_F(var14 * var551, var14 * var551, var14 * var551);
         this.renderFaceYNeg(par1Block, (double)par2, (double)par3 + var32, (double)par4, this.getBlockIconFromSide(par1Block, 0));
         var13 = true;
      }

      for(int var571 = 0; var571 < 4; ++var571) {
         int var561 = par2;
         int var58 = par4;
         if(var571 == 0) {
            var58 = par4 - 1;
         }

         if(var571 == 1) {
            ++var58;
         }

         if(var571 == 2) {
            var561 = par2 - 1;
         }

         if(var571 == 3) {
            ++var561;
         }

         Icon var60 = this.getBlockIconFromSideAndMetadata(par1Block, var571 + 2, var23);
         if(this.renderAllFaces || var12[var571]) {
            if(var571 == 0) {
               var39 = var24;
               var41 = var30;
               var43 = (double)par2;
               var47 = (double)(par2 + 1);
               var45 = (double)par4 + var32;
               var49 = (double)par4 + var32;
            } else if(var571 == 1) {
               var39 = var28;
               var41 = var26;
               var43 = (double)(par2 + 1);
               var47 = (double)par2;
               var45 = (double)(par4 + 1) - var32;
               var49 = (double)(par4 + 1) - var32;
            } else if(var571 == 2) {
               var39 = var26;
               var41 = var24;
               var43 = (double)par2 + var32;
               var47 = (double)par2 + var32;
               var45 = (double)(par4 + 1);
               var49 = (double)par4;
            } else {
               var39 = var30;
               var41 = var28;
               var43 = (double)(par2 + 1) - var32;
               var47 = (double)(par2 + 1) - var32;
               var45 = (double)par4;
               var49 = (double)(par4 + 1);
            }

            var13 = true;
            float var591 = var60.getInterpolatedU(0.0D);
            var52 = var60.getInterpolatedU(8.0D);
            var53 = var60.getInterpolatedV((1.0D - var39) * 16.0D * 0.5D);
            var54 = var60.getInterpolatedV((1.0D - var41) * 16.0D * 0.5D);
            float var55 = var60.getInterpolatedV(8.0D);
            var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, var561, par3, var58));
            float var56 = 1.0F;
            if(var571 < 2) {
               var56 *= var16;
            } else {
               var56 *= var17;
            }

            var5.setColorOpaque_F(var15 * var56 * var7, var15 * var56 * var8, var15 * var56 * var9);
            var5.addVertexWithUV(var43, (double)par3 + var39, var45, (double)var591, (double)var53);
            var5.addVertexWithUV(var47, (double)par3 + var41, var49, (double)var52, (double)var54);
            var5.addVertexWithUV(var47, (double)(par3 + 0), var49, (double)var52, (double)var55);
            var5.addVertexWithUV(var43, (double)(par3 + 0), var45, (double)var591, (double)var55);
         }
      }

      this.renderMinY = var18;
      this.renderMaxY = var20;
      return var13;
   }

   private float getFluidHeight(int par1, int par2, int par3, Material par4Material) {
      int var5 = 0;
      float var6 = 0.0F;

      for(int var7 = 0; var7 < 4; ++var7) {
         int var8 = par1 - (var7 & 1);
         int var9 = par3 - (var7 >> 1 & 1);
         if(this.blockAccess.getBlockMaterial(var8, par2 + 1, var9) == par4Material) {
            return 1.0F;
         }

         Material var10 = this.blockAccess.getBlockMaterial(var8, par2, var9);
         if(var10 == par4Material) {
            int var11 = this.blockAccess.getBlockMetadata(var8, par2, var9);
            if(var11 >= 8 || var11 == 0) {
               var6 += BlockFluid.getFluidHeightPercent(var11) * 10.0F;
               var5 += 10;
            }

            var6 += BlockFluid.getFluidHeightPercent(var11);
            ++var5;
         } else if(!var10.isSolid()) {
            ++var6;
            ++var5;
         }
      }

      return 1.0F - var6 / (float)var5;
   }

   public void renderBlockSandFalling(Block par1Block, World par2World, int par3, int par4, int par5, int par6) {
      float var7 = 0.5F;
      float var8 = 1.0F;
      float var9 = 0.8F;
      float var10 = 0.6F;
      Tessellator var11 = Tessellator.instance;
      var11.startDrawingQuads();
      var11.setBrightness(par1Block.getMixedBrightnessForBlock(par2World, par3, par4, par5));
      float var12 = 1.0F;
      float var13 = 1.0F;
      if(var13 < var12) {
         var13 = var12;
      }

      var11.setColorOpaque_F(var7 * var13, var7 * var13, var7 * var13);
      this.renderFaceYNeg(par1Block, -0.5D, -0.5D, -0.5D, this.getBlockIconFromSideAndMetadata(par1Block, 0, par6));
      var13 = 1.0F;
      if(var13 < var12) {
         var13 = var12;
      }

      var11.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
      this.renderFaceYPos(par1Block, -0.5D, -0.5D, -0.5D, this.getBlockIconFromSideAndMetadata(par1Block, 1, par6));
      var13 = 1.0F;
      if(var13 < var12) {
         var13 = var12;
      }

      var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
      this.renderFaceZNeg(par1Block, -0.5D, -0.5D, -0.5D, this.getBlockIconFromSideAndMetadata(par1Block, 2, par6));
      var13 = 1.0F;
      if(var13 < var12) {
         var13 = var12;
      }

      var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
      this.renderFaceZPos(par1Block, -0.5D, -0.5D, -0.5D, this.getBlockIconFromSideAndMetadata(par1Block, 3, par6));
      var13 = 1.0F;
      if(var13 < var12) {
         var13 = var12;
      }

      var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
      this.renderFaceXNeg(par1Block, -0.5D, -0.5D, -0.5D, this.getBlockIconFromSideAndMetadata(par1Block, 4, par6));
      var13 = 1.0F;
      if(var13 < var12) {
         var13 = var12;
      }

      var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
      this.renderFaceXPos(par1Block, -0.5D, -0.5D, -0.5D, this.getBlockIconFromSideAndMetadata(par1Block, 5, par6));
      var11.draw();
   }

   public boolean renderBlockByRenderType2(Block par1Block, int par2, int par3, int par4) {
      switch(par1Block.getRenderType()) {
      case 1:
         return this.renderCrossedSquares(par1Block, par2, par3, par4);
      case 2:
         return this.renderBlockTorch(par1Block, par2, par3, par4);
      case 3:
         return this.renderBlockFire((BlockFire)par1Block, par2, par3, par4);
      case 4:
      case 22:
      default:
         return false;
      case 5:
         return this.renderBlockRedstoneWire(par1Block, par2, par3, par4);
      case 6:
         return this.renderBlockCrops(par1Block, par2, par3, par4);
      case 7:
         return this.renderBlockDoor(par1Block, par2, par3, par4);
      case 8:
         return this.renderBlockLadder(par1Block, par2, par3, par4);
      case 9:
         return this.renderBlockMinecartTrack((BlockRailBase)par1Block, par2, par3, par4);
      case 10:
         return this.renderBlockStairs((BlockStairs)par1Block, par2, par3, par4);
      case 11:
         return this.renderBlockFence((BlockFence)par1Block, par2, par3, par4);
      case 12:
         return this.renderBlockLever(par1Block, par2, par3, par4);
      case 13:
         return this.renderBlockCactus(par1Block, par2, par3, par4);
      case 14:
         return this.renderBlockBed(par1Block, par2, par3, par4);
      case 15:
         return this.renderBlockRepeater((BlockRedstoneRepeater)par1Block, par2, par3, par4);
      case 16:
         return this.renderPistonBase(par1Block, par2, par3, par4, false);
      case 17:
         return this.renderPistonExtension(par1Block, par2, par3, par4, true);
      case 18:
         return this.renderBlockPane((BlockPane)par1Block, par2, par3, par4);
      case 19:
         return this.renderBlockStem(par1Block, par2, par3, par4);
      case 20:
         return this.renderBlockVine(par1Block, par2, par3, par4);
      case 21:
         return this.renderBlockFenceGate((BlockFenceGate)par1Block, par2, par3, par4);
      case 23:
         return this.renderBlockLilyPad(par1Block, par2, par3, par4);
      case 24:
         return this.renderBlockCauldron((BlockCauldron)par1Block, par2, par3, par4);
      case 25:
         return this.renderBlockBrewingStand((BlockBrewingStand)par1Block, par2, par3, par4);
      case 26:
         return this.renderBlockEndPortalFrame((BlockEndPortalFrame)par1Block, par2, par3, par4);
      case 27:
         return this.renderBlockDragonEgg((BlockDragonEgg)par1Block, par2, par3, par4);
      case 28:
         return this.renderBlockCocoa((BlockCocoa)par1Block, par2, par3, par4);
      case 29:
         return this.renderBlockTripWireSource(par1Block, par2, par3, par4);
      case 30:
         return this.renderBlockTripWire(par1Block, par2, par3, par4);
      case 31:
         return this.renderBlockLog(par1Block, par2, par3, par4);
      case 32:
         return this.renderBlockWall((BlockWall)par1Block, par2, par3, par4);
      case 33:
         return this.renderBlockFlowerpot((BlockFlowerPot)par1Block, par2, par3, par4);
      case 34:
         return this.renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);
      case 35:
         return this.renderBlockAnvil((BlockAnvil)par1Block, par2, par3, par4);
      case 36:
         return this.renderBlockRedstoneLogic((BlockRedstoneLogic)par1Block, par2, par3, par4);
      case 37:
         return this.renderBlockComparator((BlockComparator)par1Block, par2, par3, par4);
      case 38:
         return this.renderBlockHopper((BlockHopper)par1Block, par2, par3, par4);
      case 39:
         return this.renderBlockQuartz(par1Block, par2, par3, par4);
      }
   }

   public void getblockColorMultiplier(Block par1Block, int par2, int par3, int par4) {
      if((this.bSITemp0 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4)) != this.bSITemp1) {
         this.bSFTemp1 = (float)((this.bSITemp1 = this.bSITemp0) >> 16 & 255) / 255.0F;
         this.bSFTemp2 = (float)(this.bSITemp1 >> 8 & 255) / 255.0F;
         this.bSFTemp3 = (float)(this.bSITemp1 & 255) / 255.0F;
         if(EntityRenderer.anaglyphEnable) {
            this.bSFTemp1 = (this.bSFTemp1 * 30.0F + this.bSFTemp2 * 59.0F + this.bSFTemp3 * 11.0F) / 100.0F;
            this.bSFTemp2 = (this.bSFTemp1 * 30.0F + this.bSFTemp2 * 70.0F) / 100.0F;
            this.bSFTemp3 = (this.bSFTemp1 * 30.0F + this.bSFTemp3 * 70.0F) / 100.0F;
         }
      }

   }

   public boolean renderBlockFluids(Block par1Block, int par2, int par3, int par4) {
      return this.sideBeRendered(par1Block, par2, par3, par4)?this.renderBlockFluidsMain(par1Block, par2, par3, par4):false;
   }

   public boolean sideBeRendered(Block par1Block, int par2, int par3, int par4) {
      if(this.renderAllFaces) {
         return this.bSBTemp1 = this.bSBTemp2 = this.bSBTemp3 = this.bSBTemp4 = this.bSBTemp5 = this.bSBTemp6 = true;
      } else if(!fastSideList[par1Block.blockID]) {
         return (this.bSBTemp1 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0)) | (this.bSBTemp2 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1)) | (this.bSBTemp3 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2)) | (this.bSBTemp4 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3)) | (this.bSBTemp5 = par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4)) | (this.bSBTemp6 = par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5));
      } else {
         Block block = Block.blocksList[this.blockAccess.getBlockId(par2, par3 - 1, par4)];
         return (this.bSBTemp1 = block == null || !block.isOpaqueCube()) | (this.bSBTemp2 = (block = Block.blocksList[this.blockAccess.getBlockId(par2, par3 + 1, par4)]) == null || !block.isOpaqueCube()) | (this.bSBTemp3 = (block = Block.blocksList[this.blockAccess.getBlockId(par2, par3, par4 - 1)]) == null || !block.isOpaqueCube()) | (this.bSBTemp4 = (block = Block.blocksList[this.blockAccess.getBlockId(par2, par3, par4 + 1)]) == null || !block.isOpaqueCube()) | (this.bSBTemp5 = (block = Block.blocksList[this.blockAccess.getBlockId(par2 - 1, par3, par4)]) == null || !block.isOpaqueCube()) | (this.bSBTemp6 = (block = Block.blocksList[this.blockAccess.getBlockId(par2 + 1, par3, par4)]) == null || !block.isOpaqueCube());
      }
   }

   public boolean renderStandardBlockWithAmbientOcclusion(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7) {
      return this.renderStandardBlock(par1Block, par2, par3, par4);
   }

   public boolean renderStandardBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7) {
      return this.renderStandardBlock(par1Block, par2, par3, par4);
   }

   public boolean renderStandardBlock(Block par1Block, int par2, int par3, int par4) {
      return !this.sideBeRendered(par1Block, par2, par3, par4)?false:(this.LRBTEMP3 && Block.lightValue[par1Block.blockID] == 0?this.renderStandardBlockWithAmbientOcclusion(par1Block, par2, par3, par4):this.renderStandardBlockWithColorMultiplier(par1Block, par2, par3, par4));
   }

   private void aoRenderGetLightValue(float par1) {
      if(aoRenderGLV == null || aoRenderGLV.length != Block.blocksList.length + 1) {
         aoRenderGLV = new float[Block.blocksList.length + 1];
      }

      if(aoRenderGLV[aoRenderGLV.length - 1] != this.aoLightValueOpaque) {
         Block block = null;

         for(int int1 = 0; int1 < Block.blocksList.length; ++int1) {
            aoRenderGLV[int1] = (block = Block.blocksList[int1]) != null && block.blockMaterial.blocksMovement() && block.renderAsNormalBlock()?par1:1.0F;
         }

         aoRenderGLV[aoRenderGLV.length - 1] = par1;
      }

   }

   private void aoRenderFancyGrass() {
      this.colorRedTopLeft *= this.bSFTemp1;
      this.colorRedBottomLeft *= this.bSFTemp1;
      this.colorRedBottomRight *= this.bSFTemp1;
      this.colorRedTopRight *= this.bSFTemp1;
      this.colorGreenTopLeft *= this.bSFTemp2;
      this.colorGreenBottomLeft *= this.bSFTemp2;
      this.colorGreenBottomRight *= this.bSFTemp2;
      this.colorGreenTopRight *= this.bSFTemp2;
      this.colorBlueTopLeft *= this.bSFTemp3;
      this.colorBlueBottomLeft *= this.bSFTemp3;
      this.colorBlueBottomRight *= this.bSFTemp3;
      this.colorBlueTopRight *= this.bSFTemp3;
   }

   private void aoRenderColor(float par1, float par2, float par3, float par4, int par5) {
      this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = this.bSFTemp4 * this.blockShadow[par5];
      this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = this.bSFTemp5 * this.blockShadow[par5];
      this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = this.bSFTemp6 * this.blockShadow[par5];
      this.colorRedTopLeft *= par1;
      this.colorGreenTopLeft *= par1;
      this.colorBlueTopLeft *= par1;
      this.colorRedBottomLeft *= par2;
      this.colorGreenBottomLeft *= par2;
      this.colorBlueBottomLeft *= par2;
      this.colorRedBottomRight *= par3;
      this.colorGreenBottomRight *= par3;
      this.colorBlueBottomRight *= par3;
      this.colorRedTopRight *= par4;
      this.colorGreenTopRight *= par4;
      this.colorBlueTopRight *= par4;
   }

   private void aoRenderBrightness1(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      int int1 = this.bSITemp3;
      this.bSFTemp7 = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4)];
      if(this.renderMinY <= 0.0D) {
         --par3;
         int1 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      } else if(this.bSITemp3 < 0) {
         int1 = this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      }

      this.aoLightValueScratchXYNN = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4)];
      this.aoLightValueScratchYZNN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 - 1)];
      this.aoLightValueScratchYZNP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 + 1)];
      this.aoLightValueScratchXYPN = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4)];
      this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
      this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
      this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
      this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
      this.aoLightValueScratchXYZNNN = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
      this.aoLightValueScratchXYZNNP = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
      this.aoLightValueScratchXYZPNN = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
      this.aoLightValueScratchXYZPNP = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
      this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
      this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
      this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
      this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
      if(this.LRBTEMP4) {
         this.aoRenderGrass1(tessellator, par1Block, par2, par3, par4);
      }

      this.aoRenderMinimum1(tessellator, par1Block, int1);
   }

   private void aoRenderBrightness2(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      int int1 = this.bSITemp3;
      this.bSFTemp7 = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4)];
      if(this.renderMaxY >= 1.0D) {
         ++par3;
         int1 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      } else if(this.bSITemp3 < 0) {
         int1 = this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      }

      this.aoLightValueScratchXYNP = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4)];
      this.aoLightValueScratchXYPP = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4)];
      this.aoLightValueScratchYZPN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 - 1)];
      this.aoLightValueScratchYZPP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 + 1)];
      this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
      this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
      this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
      this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
      this.aoLightValueScratchXYZNPN = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
      this.aoLightValueScratchXYZPPN = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
      this.aoLightValueScratchXYZNPP = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
      this.aoLightValueScratchXYZPPP = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
      this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
      this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
      this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
      this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
      if(this.LRBTEMP4) {
         this.aoRenderGrass2(tessellator, par1Block, par2, par3, par4);
      }

      this.aoRenderMinimum2(tessellator, par1Block, int1);
   }

   private void aoRenderBrightness3(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      int int1 = this.bSITemp3;
      this.bSFTemp7 = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 - 1)];
      if(this.renderMinZ <= 0.0D) {
         --par4;
         int1 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      } else if(this.bSITemp3 < 0) {
         int1 = this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      }

      this.aoLightValueScratchXZNN = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4)];
      this.aoLightValueScratchYZNN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4)];
      this.aoLightValueScratchYZPN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4)];
      this.aoLightValueScratchXZPN = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4)];
      this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
      this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
      this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
      this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
      this.aoLightValueScratchXYZNNN = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
      this.aoLightValueScratchXYZNPN = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
      this.aoLightValueScratchXYZPNN = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
      this.aoLightValueScratchXYZPPN = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
      this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
      this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
      this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
      this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
      if(this.LRBTEMP4) {
         this.aoRenderGrass3(tessellator, par1Block, par2, par3, par4);
         if(this.partialRenderBounds) {
            this.aoRenderMaximum3(tessellator, par1Block, int1);
         } else {
            this.aoRenderMinimum3(tessellator, par1Block, int1);
         }
      } else {
         this.aoRenderMinimum3(tessellator, par1Block, int1);
      }

   }

   private void aoRenderBrightness4(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      int int1 = this.bSITemp3;
      this.bSFTemp7 = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 + 1)];
      if(this.renderMaxZ >= 1.0D) {
         ++par4;
         int1 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      } else if(this.bSITemp3 < 0) {
         int1 = this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      }

      this.aoLightValueScratchXZNP = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4)];
      this.aoLightValueScratchXZPP = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4)];
      this.aoLightValueScratchYZNP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4)];
      this.aoLightValueScratchYZPP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4)];
      this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
      this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
      this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
      this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
      this.aoLightValueScratchXYZNNP = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
      this.aoLightValueScratchXYZNPP = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
      this.aoLightValueScratchXYZPNP = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
      this.aoLightValueScratchXYZPPP = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
      this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
      this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
      this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
      this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
      if(this.LRBTEMP4) {
         this.aoRenderGrass4(tessellator, par1Block, par2, par3, par4);
         if(this.partialRenderBounds) {
            this.aoRenderMaximum4(tessellator, par1Block, int1);
         } else {
            this.aoRenderMinimum4(tessellator, par1Block, int1);
         }
      } else {
         this.aoRenderMinimum4(tessellator, par1Block, int1);
      }

   }

   private void aoRenderBrightness5(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      int int1 = this.bSITemp3;
      this.bSFTemp7 = aoRenderGLV[this.blockAccess.getBlockId(par2 - 1, par3, par4)];
      if(this.renderMinX <= 0.0D) {
         --par2;
         int1 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      } else if(this.bSITemp3 < 0) {
         int1 = this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      }

      this.aoLightValueScratchXYNN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4)];
      this.aoLightValueScratchXZNN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 - 1)];
      this.aoLightValueScratchXZNP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 + 1)];
      this.aoLightValueScratchXYNP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4)];
      this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
      this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
      this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
      this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
      this.aoLightValueScratchXYZNNN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
      this.aoLightValueScratchXYZNNP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
      this.aoLightValueScratchXYZNPN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
      this.aoLightValueScratchXYZNPP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
      this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
      this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
      this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
      this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
      if(this.LRBTEMP4) {
         this.aoRenderGrass5(tessellator, par1Block, par2, par3, par4);
         if(this.partialRenderBounds) {
            this.aoRenderMaximum5(tessellator, par1Block, int1);
         } else {
            this.aoRenderMinimum5(tessellator, par1Block, int1);
         }
      } else {
         this.aoRenderMinimum5(tessellator, par1Block, int1);
      }

   }

   private void aoRenderBrightness6(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      int int1 = this.bSITemp3;
      this.bSFTemp7 = aoRenderGLV[this.blockAccess.getBlockId(par2 + 1, par3, par4)];
      if(this.renderMaxX >= 1.0D) {
         ++par2;
         int1 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      } else if(this.bSITemp3 < 0) {
         int1 = this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      }

      this.aoLightValueScratchXYPN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4)];
      this.aoLightValueScratchXZPN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 - 1)];
      this.aoLightValueScratchXZPP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3, par4 + 1)];
      this.aoLightValueScratchXYPP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4)];
      this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
      this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
      this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
      this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
      this.aoLightValueScratchXYZPNN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
      this.aoLightValueScratchXYZPNP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
      this.aoLightValueScratchXYZPPN = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
      this.aoLightValueScratchXYZPPP = aoRenderGLV[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
      this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
      this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
      this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
      this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
      if(this.LRBTEMP4) {
         this.aoRenderGrass6(tessellator, par1Block, par2, par3, par4);
         if(this.partialRenderBounds) {
            this.aoRenderMaximum6(tessellator, par1Block, int1);
         } else {
            this.aoRenderMinimum6(tessellator, par1Block, int1);
         }
      } else {
         this.aoRenderMinimum6(tessellator, par1Block, int1);
      }

   }

   private void aoRenderGrass1(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      boolean boolean1 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
      boolean boolean2 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
      boolean boolean3 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
      boolean boolean4 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
      if(boolean4 && boolean2) {
         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
         this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
      }

      if(boolean3 && boolean2) {
         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
         this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
      }

      if(boolean4 && boolean1) {
         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
         this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
      }

      if(boolean3 && boolean1) {
         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
         this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
      }

   }

   private void aoRenderGrass2(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      boolean boolean1 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
      boolean boolean2 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
      boolean boolean3 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
      boolean boolean4 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
      if(boolean4 && boolean2) {
         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
         this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
      }

      if(boolean4 && boolean1) {
         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
         this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
      }

      if(boolean3 && boolean2) {
         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
         this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
      }

      if(boolean3 && boolean1) {
         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
         this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
      }

   }

   private void aoRenderGrass3(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      boolean boolean1 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
      boolean boolean2 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
      boolean boolean3 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
      boolean boolean4 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
      if(boolean2 && boolean4) {
         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
         this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
      }

      if(boolean2 && boolean3) {
         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
         this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
      }

      if(boolean1 && boolean4) {
         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
         this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
      }

      if(boolean1 && boolean3) {
         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
         this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
      }

   }

   private void aoRenderGrass4(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      boolean boolean1 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
      boolean boolean2 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
      boolean boolean3 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
      boolean boolean4 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
      if(boolean2 && boolean4) {
         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
         this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
      }

      if(boolean2 && boolean3) {
         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
         this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
      }

      if(boolean1 && boolean4) {
         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
         this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
      }

      if(boolean1 && boolean3) {
         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
         this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
      }

   }

   private void aoRenderGrass5(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      boolean boolean1 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
      boolean boolean2 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
      boolean boolean3 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
      boolean boolean4 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
      if(boolean3 && boolean2) {
         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
         this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
      }

      if(boolean4 && boolean2) {
         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
         this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
      }

      if(boolean3 && boolean1) {
         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
         this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
      }

      if(boolean4 && boolean1) {
         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
         this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
      }

   }

   private void aoRenderGrass6(Tessellator tessellator, Block par1Block, int par2, int par3, int par4) {
      boolean boolean1 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
      boolean boolean2 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
      boolean boolean3 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
      boolean boolean4 = !Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
      if(boolean2 && boolean4) {
         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
         this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
      }

      if(boolean2 && boolean3) {
         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
         this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
      }

      if(boolean1 && boolean4) {
         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
         this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
      }

      if(boolean1 && boolean3) {
         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
         this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
      }

   }

   private void aoRenderMinimum1(Tessellator tessellator, Block par1Block, int par2) {
      this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, par2);
      this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, par2);
      this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, par2);
      this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, par2);
      this.aoRenderColor((this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + this.bSFTemp7) / 4.0F, (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + this.bSFTemp7 + this.aoLightValueScratchYZNN) / 4.0F, (this.bSFTemp7 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F, (this.aoLightValueScratchYZNP + this.bSFTemp7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F, 0);
   }

   private void aoRenderMinimum2(Tessellator tessellator, Block par1Block, int par2) {
      float var20 = (this.aoLightValueScratchYZPP + this.bSFTemp7 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
      float var21 = (this.bSFTemp7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
      float var22 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + this.bSFTemp7 + this.aoLightValueScratchYZPN) / 4.0F;
      float var23 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + this.bSFTemp7) / 4.0F;
      this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, par2);
      this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, par2);
      this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, par2);
      this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, par2);
      this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = this.bSFTemp1;
      this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = this.bSFTemp2;
      this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = this.bSFTemp3;
      this.colorRedTopLeft *= var20;
      this.colorGreenTopLeft *= var20;
      this.colorBlueTopLeft *= var20;
      this.colorRedBottomLeft *= var21;
      this.colorGreenBottomLeft *= var21;
      this.colorBlueBottomLeft *= var21;
      this.colorRedBottomRight *= var22;
      this.colorGreenBottomRight *= var22;
      this.colorBlueBottomRight *= var22;
      this.colorRedTopRight *= var23;
      this.colorGreenTopRight *= var23;
      this.colorBlueTopRight *= var23;
   }

   private void aoRenderMinimum3(Tessellator tessellator, Block par1Block, int par2) {
      this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, par2);
      this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, par2);
      this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, par2);
      this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, par2);
      this.aoRenderColor((this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + this.bSFTemp7 + this.aoLightValueScratchYZPN) / 4.0F, (this.bSFTemp7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F, (this.aoLightValueScratchYZNN + this.bSFTemp7 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F, (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + this.bSFTemp7) / 4.0F, 2);
   }

   private void aoRenderMinimum4(Tessellator tessellator, Block par1Block, int par2) {
      this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, par2);
      this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, par2);
      this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, par2);
      this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, par2);
      this.aoRenderColor((this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + this.bSFTemp7 + this.aoLightValueScratchYZPP) / 4.0F, (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + this.bSFTemp7) / 4.0F, (this.aoLightValueScratchYZNP + this.bSFTemp7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F, (this.bSFTemp7 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F, 3);
   }

   private void aoRenderMinimum5(Tessellator tessellator, Block par1Block, int par2) {
      this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, par2);
      this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, par2);
      this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, par2);
      this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, par2);
      this.aoRenderColor((this.bSFTemp7 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F, (this.aoLightValueScratchXZNN + this.bSFTemp7 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F, (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + this.bSFTemp7) / 4.0F, (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + this.bSFTemp7 + this.aoLightValueScratchXZNP) / 4.0F, 4);
   }

   private void aoRenderMinimum6(Tessellator tessellator, Block par1Block, int par2) {
      this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, par2);
      this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, par2);
      this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, par2);
      this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, par2);
      this.aoRenderColor((this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + this.bSFTemp7 + this.aoLightValueScratchXZPP) / 4.0F, (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + this.bSFTemp7) / 4.0F, (this.aoLightValueScratchXZPN + this.bSFTemp7 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F, (this.bSFTemp7 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F, 5);
   }

   private void aoRenderMaximum3(Tessellator tessellator, Block par1Block, int par2) {
      float var16 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + this.bSFTemp7 + this.aoLightValueScratchYZPN) / 4.0F;
      float var17 = (this.bSFTemp7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
      float var18 = (this.aoLightValueScratchYZNN + this.bSFTemp7 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
      float var19 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + this.bSFTemp7) / 4.0F;
      float var20 = (float)((double)var16 * this.renderMaxY * (1.0D - this.renderMinX) + (double)var17 * this.renderMinY * this.renderMinX + (double)var18 * (1.0D - this.renderMaxY) * this.renderMinX + (double)var19 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
      float var21 = (float)((double)var16 * this.renderMaxY * (1.0D - this.renderMaxX) + (double)var17 * this.renderMaxY * this.renderMaxX + (double)var18 * (1.0D - this.renderMaxY) * this.renderMaxX + (double)var19 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
      float var22 = (float)((double)var16 * this.renderMinY * (1.0D - this.renderMaxX) + (double)var17 * this.renderMinY * this.renderMaxX + (double)var18 * (1.0D - this.renderMinY) * this.renderMaxX + (double)var19 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
      float var23 = (float)((double)var16 * this.renderMinY * (1.0D - this.renderMinX) + (double)var17 * this.renderMinY * this.renderMinX + (double)var18 * (1.0D - this.renderMinY) * this.renderMinX + (double)var19 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
      int var24 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, par2);
      int var25 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, par2);
      int var26 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, par2);
      int var27 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, par2);
      this.brightnessTopLeft = this.mixAoBrightness(var24, var25, var26, var27, this.renderMaxY * (1.0D - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0D - this.renderMaxY) * this.renderMinX, (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
      this.brightnessBottomLeft = this.mixAoBrightness(var24, var25, var26, var27, this.renderMaxY * (1.0D - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0D - this.renderMaxY) * this.renderMaxX, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
      this.brightnessBottomRight = this.mixAoBrightness(var24, var25, var26, var27, this.renderMinY * (1.0D - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0D - this.renderMinY) * this.renderMaxX, (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
      this.brightnessTopRight = this.mixAoBrightness(var24, var25, var26, var27, this.renderMinY * (1.0D - this.renderMinX), this.renderMinY * this.renderMinX, (1.0D - this.renderMinY) * this.renderMinX, (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
      this.aoRenderColor(var20, var21, var22, var23, 2);
   }

   private void aoRenderMaximum4(Tessellator tessellator, Block par1Block, int par2) {
      float var16 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + this.bSFTemp7 + this.aoLightValueScratchYZPP) / 4.0F;
      float var17 = (this.bSFTemp7 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
      float var18 = (this.aoLightValueScratchYZNP + this.bSFTemp7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
      float var19 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + this.bSFTemp7) / 4.0F;
      float var20 = (float)((double)var16 * this.renderMaxY * (1.0D - this.renderMinX) + (double)var17 * this.renderMaxY * this.renderMinX + (double)var18 * (1.0D - this.renderMaxY) * this.renderMinX + (double)var19 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
      float var21 = (float)((double)var16 * this.renderMinY * (1.0D - this.renderMinX) + (double)var17 * this.renderMinY * this.renderMinX + (double)var18 * (1.0D - this.renderMinY) * this.renderMinX + (double)var19 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
      float var22 = (float)((double)var16 * this.renderMinY * (1.0D - this.renderMaxX) + (double)var17 * this.renderMinY * this.renderMaxX + (double)var18 * (1.0D - this.renderMinY) * this.renderMaxX + (double)var19 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
      float var23 = (float)((double)var16 * this.renderMaxY * (1.0D - this.renderMaxX) + (double)var17 * this.renderMaxY * this.renderMaxX + (double)var18 * (1.0D - this.renderMaxY) * this.renderMaxX + (double)var19 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
      int var24 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, par2);
      int var25 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, par2);
      int var26 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, par2);
      int var27 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, par2);
      this.brightnessTopLeft = this.mixAoBrightness(var24, var27, var26, var25, this.renderMaxY * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
      this.brightnessBottomLeft = this.mixAoBrightness(var24, var27, var26, var25, this.renderMinY * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
      this.brightnessBottomRight = this.mixAoBrightness(var24, var27, var26, var25, this.renderMinY * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
      this.brightnessTopRight = this.mixAoBrightness(var24, var27, var26, var25, this.renderMaxY * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);
      this.aoRenderColor(var20, var21, var22, var23, 3);
   }

   private void aoRenderMaximum5(Tessellator tessellator, Block par1Block, int par2) {
      float var16 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + this.bSFTemp7 + this.aoLightValueScratchXZNP) / 4.0F;
      float var17 = (this.bSFTemp7 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
      float var18 = (this.aoLightValueScratchXZNN + this.bSFTemp7 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
      float var19 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + this.bSFTemp7) / 4.0F;
      float var20 = (float)((double)var17 * this.renderMaxY * this.renderMaxZ + (double)var18 * this.renderMaxY * (1.0D - this.renderMaxZ) + (double)var19 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + (double)var16 * (1.0D - this.renderMaxY) * this.renderMaxZ);
      float var21 = (float)((double)var17 * this.renderMaxY * this.renderMinZ + (double)var18 * this.renderMaxY * (1.0D - this.renderMinZ) + (double)var19 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + (double)var16 * (1.0D - this.renderMaxY) * this.renderMinZ);
      float var22 = (float)((double)var17 * this.renderMinY * this.renderMinZ + (double)var18 * this.renderMinY * (1.0D - this.renderMinZ) + (double)var19 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + (double)var16 * (1.0D - this.renderMinY) * this.renderMinZ);
      float var23 = (float)((double)var17 * this.renderMinY * this.renderMaxZ + (double)var18 * this.renderMinY * (1.0D - this.renderMaxZ) + (double)var19 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + (double)var16 * (1.0D - this.renderMinY) * this.renderMaxZ);
      int var24 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, par2);
      int var25 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, par2);
      int var26 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, par2);
      int var27 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, par2);
      this.brightnessTopLeft = this.mixAoBrightness(var25, var26, var27, var24, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * this.renderMaxZ);
      this.brightnessBottomLeft = this.mixAoBrightness(var25, var26, var27, var24, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * this.renderMinZ);
      this.brightnessBottomRight = this.mixAoBrightness(var25, var26, var27, var24, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * this.renderMinZ);
      this.brightnessTopRight = this.mixAoBrightness(var25, var26, var27, var24, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * this.renderMaxZ);
      this.aoRenderColor(var20, var21, var22, var23, 4);
   }

   private void aoRenderMaximum6(Tessellator tessellator, Block par1Block, int par2) {
      float var16 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + this.bSFTemp7 + this.aoLightValueScratchXZPP) / 4.0F;
      float var17 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + this.bSFTemp7) / 4.0F;
      float var18 = (this.aoLightValueScratchXZPN + this.bSFTemp7 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
      float var19 = (this.bSFTemp7 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
      float var20 = (float)((double)var16 * (1.0D - this.renderMinY) * this.renderMaxZ + (double)var17 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + (double)var18 * this.renderMinY * (1.0D - this.renderMaxZ) + (double)var19 * this.renderMinY * this.renderMaxZ);
      float var21 = (float)((double)var16 * (1.0D - this.renderMinY) * this.renderMinZ + (double)var17 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + (double)var18 * this.renderMinY * (1.0D - this.renderMinZ) + (double)var19 * this.renderMinY * this.renderMinZ);
      float var22 = (float)((double)var16 * (1.0D - this.renderMaxY) * this.renderMinZ + (double)var17 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + (double)var18 * this.renderMaxY * (1.0D - this.renderMinZ) + (double)var19 * this.renderMaxY * this.renderMinZ);
      float var23 = (float)((double)var16 * (1.0D - this.renderMaxY) * this.renderMaxZ + (double)var17 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + (double)var18 * this.renderMaxY * (1.0D - this.renderMaxZ) + (double)var19 * this.renderMaxY * this.renderMaxZ);
      int var24 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, par2);
      int var25 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, par2);
      int var26 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, par2);
      int var27 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, par2);
      this.brightnessTopLeft = this.mixAoBrightness(var24, var27, var26, var25, (1.0D - this.renderMinY) * this.renderMaxZ, (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), this.renderMinY * (1.0D - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
      this.brightnessBottomLeft = this.mixAoBrightness(var24, var27, var26, var25, (1.0D - this.renderMinY) * this.renderMinZ, (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), this.renderMinY * (1.0D - this.renderMinZ), this.renderMinY * this.renderMinZ);
      this.brightnessBottomRight = this.mixAoBrightness(var24, var27, var26, var25, (1.0D - this.renderMaxY) * this.renderMinZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), this.renderMaxY * (1.0D - this.renderMinZ), this.renderMaxY * this.renderMinZ);
      this.brightnessTopRight = this.mixAoBrightness(var24, var27, var26, var25, (1.0D - this.renderMaxY) * this.renderMaxZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), this.renderMaxY * (1.0D - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);
      this.aoRenderColor(var20, var21, var22, var23, 5);
   }

   private double getInterpolatedU(double par2) {
      return (double)(this.bSFTemp8 + this.bSFTemp12 * (float)par2);
   }

   private double getInterpolatedV(double par2) {
      return (double)(this.bSFTemp10 + this.bSFTemp13 * (float)par2);
   }

   private void getIconInterpolated(Icon par1Icon) {
      this.stackIcon = par1Icon;
      this.bSFTemp12 = (this.bSFTemp9 = this.stackIcon.getMaxU()) - (this.bSFTemp8 = this.stackIcon.getMinU());
      this.bSFTemp13 = (this.bSFTemp11 = this.stackIcon.getMaxV()) - (this.bSFTemp10 = this.stackIcon.getMinV());
   }

   public boolean renderBlockLog(Block par1Block, int par2, int par3, int par4) {
      int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var6 = var5 & 12;
      if(var6 == 4) {
         this.uvRotateEast = 1;
         this.uvRotateWest = 1;
         this.uvRotateTop = 1;
         this.uvRotateBottom = 1;
      } else if(var6 == 8) {
         this.uvRotateSouth = 1;
         this.uvRotateNorth = 1;
      }

      boolean var7 = this.renderStandardBlock(par1Block, par2, par3, par4);
      this.uvRotateSouth = 0;
      this.uvRotateEast = 0;
      this.uvRotateWest = 0;
      this.uvRotateNorth = 0;
      this.uvRotateTop = 0;
      this.uvRotateBottom = 0;
      return var7;
   }

   public boolean renderBlockQuartz(Block par1Block, int par2, int par3, int par4) {
      int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      if(var5 == 3) {
         this.uvRotateEast = 1;
         this.uvRotateWest = 1;
         this.uvRotateTop = 1;
         this.uvRotateBottom = 1;
      } else if(var5 == 4) {
         this.uvRotateSouth = 1;
         this.uvRotateNorth = 1;
      }

      boolean var6 = this.renderStandardBlock(par1Block, par2, par3, par4);
      this.uvRotateSouth = 0;
      this.uvRotateEast = 0;
      this.uvRotateWest = 0;
      this.uvRotateNorth = 0;
      this.uvRotateTop = 0;
      this.uvRotateBottom = 0;
      return var6;
   }

   public boolean renderStandardBlockWithAmbientOcclusion(Block par1Block, int par2, int par3, int par4) {
      this.enableAO = true;
      Tessellator var18 = Tessellator.instance;
      var18.setBrightness(983055);
      this.bSBTemp7 = this.LRBTEMP1 && par1Block.blockID == 2 && !this.hasOverrideBlockTexture();
      this.bSITemp3 = -1;
      if(this.LRBTEMP4) {
         this.partialRenderBounds = this.renderMinY > 0.0D || this.renderMaxY < 1.0D;
      }

      this.getblockColorMultiplier(par1Block, par2, par3, par4);
      if(par1Block.blockID != 2) {
         this.bSFTemp4 = this.bSFTemp1;
         this.bSFTemp5 = this.bSFTemp2;
         this.bSFTemp6 = this.bSFTemp3;
      } else {
         this.bSFTemp4 = this.bSFTemp5 = this.bSFTemp6 = 1.0F;
      }

      if(this.bSBTemp1) {
         this.aoRenderBrightness1(var18, par1Block, par2, par3, par4);
         this.renderFaceYNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
      }

      if(this.bSBTemp2) {
         this.aoRenderBrightness2(var18, par1Block, par2, par3, par4);
         this.renderFaceYPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
      }

      Icon var24;
      if(this.bSBTemp3) {
         this.aoRenderBrightness3(var18, par1Block, par2, par3, par4);
         var24 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
         this.renderFaceZNeg(par1Block, (double)par2, (double)par3, (double)par4, var24);
         if(this.bSBTemp7 && var24.getIconName().equals("grass_side")) {
            this.aoRenderFancyGrass();
            this.renderFaceZNeg(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      if(this.bSBTemp4) {
         this.aoRenderBrightness4(var18, par1Block, par2, par3, par4);
         var24 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
         this.renderFaceZPos(par1Block, (double)par2, (double)par3, (double)par4, var24);
         if(this.bSBTemp7 && var24.getIconName().equals("grass_side")) {
            this.aoRenderFancyGrass();
            this.renderFaceZPos(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      if(this.bSBTemp5) {
         this.aoRenderBrightness5(var18, par1Block, par2, par3, par4);
         var24 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
         this.renderFaceXNeg(par1Block, (double)par2, (double)par3, (double)par4, var24);
         if(this.bSBTemp7 && var24.getIconName().equals("grass_side")) {
            this.aoRenderFancyGrass();
            this.renderFaceXNeg(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      if(this.bSBTemp6) {
         this.aoRenderBrightness6(var18, par1Block, par2, par3, par4);
         var24 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
         this.renderFaceXPos(par1Block, (double)par2, (double)par3, (double)par4, var24);
         if(this.bSBTemp7 && var24.getIconName().equals("grass_side")) {
            this.aoRenderFancyGrass();
            this.renderFaceXPos(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      this.enableAO = false;
      return true;
   }

   public int getAoBrightness(int par1, int par2, int par3, int par4) {
      return (par1 == 0?par4:par1) + (par2 == 0?par4:par2) + (par3 == 0?par4:par3) + par4 >> 2 & 16711935;
   }

   private int mixAoBrightness(int par1, int par2, int par3, int par4, double par5, double par7, double par9, double par11) {
      int var13 = (int)((double)(par1 >> 16 & 255) * par5 + (double)(par2 >> 16 & 255) * par7 + (double)(par3 >> 16 & 255) * par9 + (double)(par4 >> 16 & 255) * par11) & 255;
      int var14 = (int)((double)(par1 & 255) * par5 + (double)(par2 & 255) * par7 + (double)(par3 & 255) * par9 + (double)(par4 & 255) * par11) & 255;
      return var13 << 16 | var14;
   }

   public boolean renderStandardBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4) {
      Tessellator var10 = Tessellator.instance;
      this.bSBTemp7 = this.LRBTEMP1 && par1Block.blockID == 2 && !this.hasOverrideBlockTexture();
      this.bSITemp3 = -1;
      this.getblockColorMultiplier(par1Block, par2, par3, par4);
      if(par1Block.blockID != 2) {
         this.bSFTemp4 = this.bSFTemp1;
         this.bSFTemp5 = this.bSFTemp2;
         this.bSFTemp6 = this.bSFTemp3;
      } else {
         this.bSFTemp4 = this.bSFTemp5 = this.bSFTemp6 = 1.0F;
      }

      if(this.bSBTemp1) {
         var10.setBrightness(this.renderMinY <= 0.0D?par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4):(this.bSITemp3 < 0?(this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4)):this.bSITemp3));
         var10.setColorOpaque_F(this.bSFTemp4 * 0.5F, this.bSFTemp5 * 0.5F, this.bSFTemp6 * 0.5F);
         this.renderFaceYNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
      }

      if(this.bSBTemp2) {
         var10.setBrightness(this.renderMaxY >= 1.0D?par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4):(this.bSITemp3 < 0?(this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4)):this.bSITemp3));
         var10.setColorOpaque_F(this.bSFTemp1, this.bSFTemp2, this.bSFTemp3);
         this.renderFaceYPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
      }

      Icon var29;
      if(this.bSBTemp3) {
         var10.setBrightness(this.renderMinZ <= 0.0D?par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1):(this.bSITemp3 < 0?(this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4)):this.bSITemp3));
         var10.setColorOpaque_F(this.bSFTemp4 * 0.8F, this.bSFTemp5 * 0.8F, this.bSFTemp6 * 0.8F);
         var29 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
         this.renderFaceZNeg(par1Block, (double)par2, (double)par3, (double)par4, var29);
         if(this.bSBTemp7 && var29.getIconName().equals("grass_side")) {
            var10.setColorOpaque_F(this.bSFTemp4 * this.bSFTemp1 * 0.8F, this.bSFTemp5 * this.bSFTemp2 * 0.8F, this.bSFTemp6 * this.bSFTemp3 * 0.8F);
            this.renderFaceZNeg(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      if(this.bSBTemp4) {
         var10.setBrightness(this.renderMaxZ >= 1.0D?par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1):(this.bSITemp3 < 0?(this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4)):this.bSITemp3));
         var10.setColorOpaque_F(this.bSFTemp4 * 0.8F, this.bSFTemp5 * 0.8F, this.bSFTemp6 * 0.8F);
         var29 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
         this.renderFaceZPos(par1Block, (double)par2, (double)par3, (double)par4, var29);
         if(this.bSBTemp7 && var29.getIconName().equals("grass_side")) {
            var10.setColorOpaque_F(this.bSFTemp4 * this.bSFTemp1 * 0.8F, this.bSFTemp5 * this.bSFTemp2 * 0.8F, this.bSFTemp6 * this.bSFTemp3 * 0.8F);
            this.renderFaceZPos(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      if(this.bSBTemp5) {
         var10.setBrightness(this.renderMinX <= 0.0D?par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4):(this.bSITemp3 < 0?(this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4)):this.bSITemp3));
         var10.setColorOpaque_F(this.bSFTemp4 * 0.6F, this.bSFTemp5 * 0.6F, this.bSFTemp6 * 0.6F);
         var29 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
         this.renderFaceXNeg(par1Block, (double)par2, (double)par3, (double)par4, var29);
         if(this.bSBTemp7 && var29.getIconName().equals("grass_side")) {
            var10.setColorOpaque_F(this.bSFTemp4 * this.bSFTemp1 * 0.6F, this.bSFTemp5 * this.bSFTemp2 * 0.6F, this.bSFTemp6 * this.bSFTemp3 * 0.6F);
            this.renderFaceXNeg(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      if(this.bSBTemp6) {
         var10.setBrightness(this.renderMaxX >= 1.0D?par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4):(this.bSITemp3 < 0?(this.bSITemp3 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4)):this.bSITemp3));
         var10.setColorOpaque_F(this.bSFTemp4 * 0.6F, this.bSFTemp5 * 0.6F, this.bSFTemp6 * 0.6F);
         var29 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
         this.renderFaceXPos(par1Block, (double)par2, (double)par3, (double)par4, var29);
         if(this.bSBTemp7 && var29.getIconName().equals("grass_side")) {
            var10.setColorOpaque_F(this.bSFTemp4 * this.bSFTemp1 * 0.6F, this.bSFTemp5 * this.bSFTemp2 * 0.6F, this.bSFTemp6 * this.bSFTemp3 * 0.6F);
            this.renderFaceXPos(par1Block, (double)par2, (double)par3, (double)par4, BlockGrass.getIconSideOverlay());
         }
      }

      return true;
   }

   private boolean renderBlockCocoa(BlockCocoa par1BlockCocoa, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1BlockCocoa.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      int var7 = BlockDirectional.getDirection(var6);
      int var8 = BlockCocoa.func_72219_c(var6);
      Icon var9 = par1BlockCocoa.getCocoaIcon(var8);
      int var10 = 4 + var8 * 2;
      int var11 = 5 + var8 * 2;
      double var12 = 15.0D - (double)var10;
      double var14 = 15.0D;
      double var16 = 4.0D;
      double var18 = 4.0D + (double)var11;
      double var20 = (double)var9.getInterpolatedU(var12);
      double var22 = (double)var9.getInterpolatedU(var14);
      double var24 = (double)var9.getInterpolatedV(var16);
      double var26 = (double)var9.getInterpolatedV(var18);
      double var28 = 0.0D;
      double var30 = 0.0D;
      switch(var7) {
      case 0:
         var28 = 8.0D - (double)(var10 / 2);
         var30 = 15.0D - (double)var10;
         break;
      case 1:
         var28 = 1.0D;
         var30 = 8.0D - (double)(var10 / 2);
         break;
      case 2:
         var28 = 8.0D - (double)(var10 / 2);
         var30 = 1.0D;
         break;
      case 3:
         var28 = 15.0D - (double)var10;
         var30 = 8.0D - (double)(var10 / 2);
      }

      double var32 = (double)par2 + var28 / 16.0D;
      double var34 = (double)par2 + (var28 + (double)var10) / 16.0D;
      double var36 = (double)par3 + (12.0D - (double)var11) / 16.0D;
      double var38 = (double)par3 + 0.75D;
      double var40 = (double)par4 + var30 / 16.0D;
      double var42 = (double)par4 + (var30 + (double)var10) / 16.0D;
      var5.addVertexWithUV(var32, var36, var40, var20, var26);
      var5.addVertexWithUV(var32, var36, var42, var22, var26);
      var5.addVertexWithUV(var32, var38, var42, var22, var24);
      var5.addVertexWithUV(var32, var38, var40, var20, var24);
      var5.addVertexWithUV(var34, var36, var42, var20, var26);
      var5.addVertexWithUV(var34, var36, var40, var22, var26);
      var5.addVertexWithUV(var34, var38, var40, var22, var24);
      var5.addVertexWithUV(var34, var38, var42, var20, var24);
      var5.addVertexWithUV(var34, var36, var40, var20, var26);
      var5.addVertexWithUV(var32, var36, var40, var22, var26);
      var5.addVertexWithUV(var32, var38, var40, var22, var24);
      var5.addVertexWithUV(var34, var38, var40, var20, var24);
      var5.addVertexWithUV(var32, var36, var42, var20, var26);
      var5.addVertexWithUV(var34, var36, var42, var22, var26);
      var5.addVertexWithUV(var34, var38, var42, var22, var24);
      var5.addVertexWithUV(var32, var38, var42, var20, var24);
      int var44 = var10;
      if(var8 >= 2) {
         var44 = var10 - 1;
      }

      var20 = (double)var9.getMinU();
      var22 = (double)var9.getInterpolatedU((double)var44);
      var24 = (double)var9.getMinV();
      var26 = (double)var9.getInterpolatedV((double)var44);
      var5.addVertexWithUV(var32, var38, var42, var20, var26);
      var5.addVertexWithUV(var34, var38, var42, var22, var26);
      var5.addVertexWithUV(var34, var38, var40, var22, var24);
      var5.addVertexWithUV(var32, var38, var40, var20, var24);
      var5.addVertexWithUV(var32, var36, var40, var20, var24);
      var5.addVertexWithUV(var34, var36, var40, var22, var24);
      var5.addVertexWithUV(var34, var36, var42, var22, var26);
      var5.addVertexWithUV(var32, var36, var42, var20, var26);
      var20 = (double)var9.getInterpolatedU(12.0D);
      var22 = (double)var9.getMaxU();
      var24 = (double)var9.getMinV();
      var26 = (double)var9.getInterpolatedV(4.0D);
      var28 = 8.0D;
      var30 = 0.0D;
      double var45;
      switch(var7) {
      case 0:
         var28 = 8.0D;
         var30 = 12.0D;
         var45 = var20;
         var20 = var22;
         var22 = var45;
         break;
      case 1:
         var28 = 0.0D;
         var30 = 8.0D;
         break;
      case 2:
         var28 = 8.0D;
         var30 = 0.0D;
         break;
      case 3:
         var28 = 12.0D;
         var30 = 8.0D;
         var45 = var20;
         var20 = var22;
         var22 = var45;
      }

      var32 = (double)par2 + var28 / 16.0D;
      var34 = (double)par2 + (var28 + 4.0D) / 16.0D;
      var36 = (double)par3 + 0.75D;
      var38 = (double)par3 + 1.0D;
      var40 = (double)par4 + var30 / 16.0D;
      var42 = (double)par4 + (var30 + 4.0D) / 16.0D;
      if(var7 != 2 && var7 != 0) {
         if(var7 == 1 || var7 == 3) {
            var5.addVertexWithUV(var34, var36, var40, var20, var26);
            var5.addVertexWithUV(var32, var36, var40, var22, var26);
            var5.addVertexWithUV(var32, var38, var40, var22, var24);
            var5.addVertexWithUV(var34, var38, var40, var20, var24);
            var5.addVertexWithUV(var32, var36, var40, var22, var26);
            var5.addVertexWithUV(var34, var36, var40, var20, var26);
            var5.addVertexWithUV(var34, var38, var40, var20, var24);
            var5.addVertexWithUV(var32, var38, var40, var22, var24);
         }
      } else {
         var5.addVertexWithUV(var32, var36, var40, var22, var26);
         var5.addVertexWithUV(var32, var36, var42, var20, var26);
         var5.addVertexWithUV(var32, var38, var42, var20, var24);
         var5.addVertexWithUV(var32, var38, var40, var22, var24);
         var5.addVertexWithUV(var32, var36, var42, var20, var26);
         var5.addVertexWithUV(var32, var36, var40, var22, var26);
         var5.addVertexWithUV(var32, var38, var40, var22, var24);
         var5.addVertexWithUV(var32, var38, var42, var20, var24);
      }

      return true;
   }

   private boolean renderBlockBeacon(BlockBeacon par1BlockBeacon, int par2, int par3, int par4) {
      float var5 = 0.1875F;
      this.setOverrideBlockTexture(this.getBlockIcon(Block.glass));
      this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
      this.renderAllFaces = true;
      this.setOverrideBlockTexture(this.getBlockIcon(Block.obsidian));
      this.setRenderBounds(0.125D, 0.0062500000931322575D, 0.125D, 0.875D, (double)var5, 0.875D);
      this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
      this.setOverrideBlockTexture(this.getBlockIcon(Block.beacon));
      this.setRenderBounds(0.1875D, (double)var5, 0.1875D, 0.8125D, 0.875D, 0.8125D);
      this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
      this.renderAllFaces = false;
      this.clearOverrideBlockTexture();
      return true;
   }

   public boolean renderBlockCactus(Block par1Block, int par2, int par3, int par4) {
      int var5 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var6 = (float)(var5 >> 16 & 255) / 255.0F;
      float var7 = (float)(var5 >> 8 & 255) / 255.0F;
      float var8 = (float)(var5 & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
         float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
         float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
         var6 = var9;
         var7 = var10;
         var8 = var11;
      }

      return this.renderBlockCactusImpl(par1Block, par2, par3, par4, var6, var7, var8);
   }

   public boolean renderBlockCactusImpl(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7) {
      Tessellator var8 = Tessellator.instance;
      boolean var9 = false;
      float var10 = 0.5F;
      float var11 = 1.0F;
      float var12 = 0.8F;
      float var13 = 0.6F;
      float var14 = var10 * par5;
      float var15 = var11 * par5;
      float var16 = var12 * par5;
      float var17 = var13 * par5;
      float var18 = var10 * par6;
      float var19 = var11 * par6;
      float var20 = var12 * par6;
      float var21 = var13 * par6;
      float var22 = var10 * par7;
      float var23 = var11 * par7;
      float var24 = var12 * par7;
      float var25 = var13 * par7;
      float var26 = 0.0625F;
      int var27 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      if(this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0)) {
         var8.setBrightness(this.renderMinY > 0.0D?var27:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
         var8.setColorOpaque_F(var14, var18, var22);
         this.renderFaceYNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
      }

      if(this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1)) {
         var8.setBrightness(this.renderMaxY < 1.0D?var27:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
         var8.setColorOpaque_F(var15, var19, var23);
         this.renderFaceYPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
      }

      var8.setBrightness(var27);
      var8.setColorOpaque_F(var16, var20, var24);
      var8.addTranslation(0.0F, 0.0F, var26);
      this.renderFaceZNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2));
      var8.addTranslation(0.0F, 0.0F, -var26);
      var8.addTranslation(0.0F, 0.0F, -var26);
      this.renderFaceZPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3));
      var8.addTranslation(0.0F, 0.0F, var26);
      var8.setColorOpaque_F(var17, var21, var25);
      var8.addTranslation(var26, 0.0F, 0.0F);
      this.renderFaceXNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4));
      var8.addTranslation(-var26, 0.0F, 0.0F);
      var8.addTranslation(-var26, 0.0F, 0.0F);
      this.renderFaceXPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5));
      var8.addTranslation(var26, 0.0F, 0.0F);
      return true;
   }

   public boolean renderBlockFence(BlockFence par1BlockFence, int par2, int par3, int par4) {
      boolean var5 = false;
      float var6 = 0.375F;
      float var7 = 0.625F;
      this.setRenderBounds((double)var6, 0.0D, (double)var6, (double)var7, 1.0D, (double)var7);
      this.renderStandardBlock(par1BlockFence, par2, par3, par4);
      var5 = true;
      boolean var8 = false;
      boolean var9 = false;
      if(par1BlockFence.canConnectFenceTo(this.blockAccess, par2 - 1, par3, par4) || par1BlockFence.canConnectFenceTo(this.blockAccess, par2 + 1, par3, par4)) {
         var8 = true;
      }

      if(par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 - 1) || par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 + 1)) {
         var9 = true;
      }

      boolean var10 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2 - 1, par3, par4);
      boolean var11 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2 + 1, par3, par4);
      boolean var12 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 - 1);
      boolean var13 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 + 1);
      if(!var8 && !var9) {
         var8 = true;
      }

      var6 = 0.4375F;
      var7 = 0.5625F;
      float var14 = 0.75F;
      float var15 = 0.9375F;
      float var16 = var10?0.0F:var6;
      float var17 = var11?1.0F:var7;
      float var18 = var12?0.0F:var6;
      float var19 = var13?1.0F:var7;
      if(var8) {
         this.setRenderBounds((double)var16, (double)var14, (double)var6, (double)var17, (double)var15, (double)var7);
         this.renderStandardBlock(par1BlockFence, par2, par3, par4);
         var5 = true;
      }

      if(var9) {
         this.setRenderBounds((double)var6, (double)var14, (double)var18, (double)var7, (double)var15, (double)var19);
         this.renderStandardBlock(par1BlockFence, par2, par3, par4);
         var5 = true;
      }

      var14 = 0.375F;
      var15 = 0.5625F;
      if(var8) {
         this.setRenderBounds((double)var16, (double)var14, (double)var6, (double)var17, (double)var15, (double)var7);
         this.renderStandardBlock(par1BlockFence, par2, par3, par4);
         var5 = true;
      }

      if(var9) {
         this.setRenderBounds((double)var6, (double)var14, (double)var18, (double)var7, (double)var15, (double)var19);
         this.renderStandardBlock(par1BlockFence, par2, par3, par4);
         var5 = true;
      }

      par1BlockFence.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
      return var5;
   }

   public boolean renderBlockWall(BlockWall par1BlockWall, int par2, int par3, int par4) {
      boolean var5 = par1BlockWall.canConnectWallTo(this.blockAccess, par2 - 1, par3, par4);
      boolean var6 = par1BlockWall.canConnectWallTo(this.blockAccess, par2 + 1, par3, par4);
      boolean var7 = par1BlockWall.canConnectWallTo(this.blockAccess, par2, par3, par4 - 1);
      boolean var8 = par1BlockWall.canConnectWallTo(this.blockAccess, par2, par3, par4 + 1);
      boolean var9 = var7 && var8 && !var5 && !var6;
      boolean var10 = !var7 && !var8 && var5 && var6;
      boolean var11 = this.blockAccess.isAirBlock(par2, par3 + 1, par4);
      if((var9 || var10) && var11) {
         if(var9) {
            this.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 1.0D);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);
         } else {
            this.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);
         }
      } else {
         this.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
         this.renderStandardBlock(par1BlockWall, par2, par3, par4);
         if(var5) {
            this.setRenderBounds(0.0D, 0.0D, 0.3125D, 0.25D, 0.8125D, 0.6875D);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);
         }

         if(var6) {
            this.setRenderBounds(0.75D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);
         }

         if(var7) {
            this.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.25D);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);
         }

         if(var8) {
            this.setRenderBounds(0.3125D, 0.0D, 0.75D, 0.6875D, 0.8125D, 1.0D);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);
         }
      }

      par1BlockWall.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
      return true;
   }

   public boolean renderBlockDragonEgg(BlockDragonEgg par1BlockDragonEgg, int par2, int par3, int par4) {
      boolean var5 = false;
      int var6 = 0;

      for(int var7 = 0; var7 < 8; ++var7) {
         byte var8 = 0;
         byte var9 = 1;
         if(var7 == 0) {
            var8 = 2;
         }

         if(var7 == 1) {
            var8 = 3;
         }

         if(var7 == 2) {
            var8 = 4;
         }

         if(var7 == 3) {
            var8 = 5;
            var9 = 2;
         }

         if(var7 == 4) {
            var8 = 6;
            var9 = 3;
         }

         if(var7 == 5) {
            var8 = 7;
            var9 = 5;
         }

         if(var7 == 6) {
            var8 = 6;
            var9 = 2;
         }

         if(var7 == 7) {
            var8 = 3;
         }

         float var10 = (float)var8 / 16.0F;
         float var11 = 1.0F - (float)var6 / 16.0F;
         float var12 = 1.0F - (float)(var6 + var9) / 16.0F;
         var6 += var9;
         this.setRenderBounds((double)(0.5F - var10), (double)var12, (double)(0.5F - var10), (double)(0.5F + var10), (double)var11, (double)(0.5F + var10));
         this.renderStandardBlock(par1BlockDragonEgg, par2, par3, par4);
      }

      var5 = true;
      this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      return var5;
   }

   public boolean renderBlockFenceGate(BlockFenceGate par1BlockFenceGate, int par2, int par3, int par4) {
      boolean var5 = true;
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      boolean var7 = BlockFenceGate.isFenceGateOpen(var6);
      int var8 = BlockDirectional.getDirection(var6);
      float var9 = 0.375F;
      float var10 = 0.5625F;
      float var11 = 0.75F;
      float var12 = 0.9375F;
      float var13 = 0.3125F;
      float var14 = 1.0F;
      if((var8 == 2 || var8 == 0) && this.blockAccess.getBlockId(par2 - 1, par3, par4) == Block.cobblestoneWall.blockID && this.blockAccess.getBlockId(par2 + 1, par3, par4) == Block.cobblestoneWall.blockID || (var8 == 3 || var8 == 1) && this.blockAccess.getBlockId(par2, par3, par4 - 1) == Block.cobblestoneWall.blockID && this.blockAccess.getBlockId(par2, par3, par4 + 1) == Block.cobblestoneWall.blockID) {
         var9 -= 0.1875F;
         var10 -= 0.1875F;
         var11 -= 0.1875F;
         var12 -= 0.1875F;
         var13 -= 0.1875F;
         var14 -= 0.1875F;
      }

      this.renderAllFaces = true;
      float var15;
      float var16;
      float var17;
      float var18;
      if(var8 != 3 && var8 != 1) {
         var15 = 0.0F;
         var17 = 0.125F;
         var16 = 0.4375F;
         var18 = 0.5625F;
         this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var15 = 0.875F;
         var17 = 1.0F;
         this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
      } else {
         this.uvRotateTop = 1;
         var15 = 0.4375F;
         var17 = 0.5625F;
         var16 = 0.0F;
         var18 = 0.125F;
         this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var16 = 0.875F;
         var18 = 1.0F;
         this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         this.uvRotateTop = 0;
      }

      if(var7) {
         if(var8 == 2 || var8 == 0) {
            this.uvRotateTop = 1;
         }

         float var19;
         float var20;
         float var21;
         if(var8 == 3) {
            var15 = 0.0F;
            var17 = 0.125F;
            var16 = 0.875F;
            var18 = 1.0F;
            var19 = 0.5625F;
            var21 = 0.8125F;
            var20 = 0.9375F;
            this.setRenderBounds(0.8125D, (double)var9, 0.0D, 0.9375D, (double)var12, 0.125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.8125D, (double)var9, 0.875D, 0.9375D, (double)var12, 1.0D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.5625D, (double)var9, 0.0D, 0.8125D, (double)var10, 0.125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.5625D, (double)var9, 0.875D, 0.8125D, (double)var10, 1.0D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.5625D, (double)var11, 0.0D, 0.8125D, (double)var12, 0.125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.5625D, (double)var11, 0.875D, 0.8125D, (double)var12, 1.0D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         } else if(var8 == 1) {
            var15 = 0.0F;
            var17 = 0.125F;
            var16 = 0.875F;
            var18 = 1.0F;
            var19 = 0.0625F;
            var21 = 0.1875F;
            var20 = 0.4375F;
            this.setRenderBounds(0.0625D, (double)var9, 0.0D, 0.1875D, (double)var12, 0.125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.0625D, (double)var9, 0.875D, 0.1875D, (double)var12, 1.0D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.1875D, (double)var9, 0.0D, 0.4375D, (double)var10, 0.125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.1875D, (double)var9, 0.875D, 0.4375D, (double)var10, 1.0D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.1875D, (double)var11, 0.0D, 0.4375D, (double)var12, 0.125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.1875D, (double)var11, 0.875D, 0.4375D, (double)var12, 1.0D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         } else if(var8 == 0) {
            var15 = 0.0F;
            var17 = 0.125F;
            var16 = 0.875F;
            var18 = 1.0F;
            var19 = 0.5625F;
            var21 = 0.8125F;
            var20 = 0.9375F;
            this.setRenderBounds(0.0D, (double)var9, 0.8125D, 0.125D, (double)var12, 0.9375D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.875D, (double)var9, 0.8125D, 1.0D, (double)var12, 0.9375D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.0D, (double)var9, 0.5625D, 0.125D, (double)var10, 0.8125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.875D, (double)var9, 0.5625D, 1.0D, (double)var10, 0.8125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.0D, (double)var11, 0.5625D, 0.125D, (double)var12, 0.8125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.875D, (double)var11, 0.5625D, 1.0D, (double)var12, 0.8125D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         } else if(var8 == 2) {
            var15 = 0.0F;
            var17 = 0.125F;
            var16 = 0.875F;
            var18 = 1.0F;
            var19 = 0.0625F;
            var21 = 0.1875F;
            var20 = 0.4375F;
            this.setRenderBounds(0.0D, (double)var9, 0.0625D, 0.125D, (double)var12, 0.1875D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.875D, (double)var9, 0.0625D, 1.0D, (double)var12, 0.1875D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.0D, (double)var9, 0.1875D, 0.125D, (double)var10, 0.4375D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.875D, (double)var9, 0.1875D, 1.0D, (double)var10, 0.4375D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.0D, (double)var11, 0.1875D, 0.125D, (double)var12, 0.4375D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(0.875D, (double)var11, 0.1875D, 1.0D, (double)var12, 0.4375D);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         }
      } else if(var8 != 3 && var8 != 1) {
         var15 = 0.375F;
         var17 = 0.5F;
         var16 = 0.4375F;
         var18 = 0.5625F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var15 = 0.5F;
         var17 = 0.625F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var15 = 0.625F;
         var17 = 0.875F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var15 = 0.125F;
         var17 = 0.375F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
      } else {
         this.uvRotateTop = 1;
         var15 = 0.4375F;
         var17 = 0.5625F;
         var16 = 0.375F;
         var18 = 0.5F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var16 = 0.5F;
         var18 = 0.625F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var16 = 0.625F;
         var18 = 0.875F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         var16 = 0.125F;
         var18 = 0.375F;
         this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
         this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
         this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
      }

      this.renderAllFaces = false;
      this.uvRotateTop = 0;
      this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      return var5;
   }

   private boolean renderBlockHopper(BlockHopper par1BlockHopper, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      var5.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
      float var6 = 1.0F;
      int var7 = par1BlockHopper.colorMultiplier(this.blockAccess, par2, par3, par4);
      float var8 = (float)(var7 >> 16 & 255) / 255.0F;
      float var9 = (float)(var7 >> 8 & 255) / 255.0F;
      float var10 = (float)(var7 & 255) / 255.0F;
      if(EntityRenderer.anaglyphEnable) {
         float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
         float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
         float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
         var8 = var11;
         var9 = var12;
         var10 = var13;
      }

      var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
      return this.renderBlockHopperMetadata(par1BlockHopper, par2, par3, par4, this.blockAccess.getBlockMetadata(par2, par3, par4), false);
   }

   private boolean renderBlockHopperMetadata(BlockHopper par1BlockHopper, int par2, int par3, int par4, int par5, boolean par6) {
      Tessellator var7 = Tessellator.instance;
      int var8 = BlockHopper.getDirectionFromMetadata(par5);
      double var9 = 0.625D;
      this.setRenderBounds(0.0D, var9, 0.0D, 1.0D, 1.0D, 1.0D);
      if(par6) {
         var7.startDrawingQuads();
         var7.setNormal(0.0F, -1.0F, 0.0F);
         this.renderFaceYNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 0, par5));
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 1.0F, 0.0F);
         this.renderFaceYPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 1, par5));
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 0.0F, -1.0F);
         this.renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 2, par5));
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 0.0F, 1.0F);
         this.renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 3, par5));
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(-1.0F, 0.0F, 0.0F);
         this.renderFaceXNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 4, par5));
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(1.0F, 0.0F, 0.0F);
         this.renderFaceXPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 5, par5));
         var7.draw();
      } else {
         this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
      }

      float var11;
      if(!par6) {
         var7.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
         float var12 = 1.0F;
         int var13 = par1BlockHopper.colorMultiplier(this.blockAccess, par2, par3, par4);
         var11 = (float)(var13 >> 16 & 255) / 255.0F;
         float var14 = (float)(var13 >> 8 & 255) / 255.0F;
         float var15 = (float)(var13 & 255) / 255.0F;
         if(EntityRenderer.anaglyphEnable) {
            float var16 = (var11 * 30.0F + var14 * 59.0F + var15 * 11.0F) / 100.0F;
            float var17 = (var11 * 30.0F + var14 * 70.0F) / 100.0F;
            float var18 = (var11 * 30.0F + var15 * 70.0F) / 100.0F;
            var11 = var16;
            var14 = var17;
            var15 = var18;
         }

         var7.setColorOpaque_F(var12 * var11, var12 * var14, var12 * var15);
      }

      Icon var22 = BlockHopper.getHopperIcon("hopper_outside");
      Icon var23 = BlockHopper.getHopperIcon("hopper_inside");
      var11 = 0.125F;
      if(par6) {
         var7.startDrawingQuads();
         var7.setNormal(1.0F, 0.0F, 0.0F);
         this.renderFaceXPos(par1BlockHopper, (double)(-1.0F + var11), 0.0D, 0.0D, var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(-1.0F, 0.0F, 0.0F);
         this.renderFaceXNeg(par1BlockHopper, (double)(1.0F - var11), 0.0D, 0.0D, var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 0.0F, 1.0F);
         this.renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, (double)(-1.0F + var11), var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 0.0F, -1.0F);
         this.renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, (double)(1.0F - var11), var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 1.0F, 0.0F);
         this.renderFaceYPos(par1BlockHopper, 0.0D, -1.0D + var9, 0.0D, var23);
         var7.draw();
      } else {
         this.renderFaceXPos(par1BlockHopper, (double)((float)par2 - 1.0F + var11), (double)par3, (double)par4, var22);
         this.renderFaceXNeg(par1BlockHopper, (double)((float)par2 + 1.0F - var11), (double)par3, (double)par4, var22);
         this.renderFaceZPos(par1BlockHopper, (double)par2, (double)par3, (double)((float)par4 - 1.0F + var11), var22);
         this.renderFaceZNeg(par1BlockHopper, (double)par2, (double)par3, (double)((float)par4 + 1.0F - var11), var22);
         this.renderFaceYPos(par1BlockHopper, (double)par2, (double)((float)par3 - 1.0F) + var9, (double)par4, var23);
      }

      this.setOverrideBlockTexture(var22);
      double var24 = 0.25D;
      double var25 = 0.25D;
      this.setRenderBounds(var24, var25, var24, 1.0D - var24, var9 - 0.002D, 1.0D - var24);
      if(par6) {
         var7.startDrawingQuads();
         var7.setNormal(1.0F, 0.0F, 0.0F);
         this.renderFaceXPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(-1.0F, 0.0F, 0.0F);
         this.renderFaceXNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 0.0F, 1.0F);
         this.renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 0.0F, -1.0F);
         this.renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, 1.0F, 0.0F);
         this.renderFaceYPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, var22);
         var7.draw();
         var7.startDrawingQuads();
         var7.setNormal(0.0F, -1.0F, 0.0F);
         this.renderFaceYNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, var22);
         var7.draw();
      } else {
         this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
      }

      if(!par6) {
         double var26 = 0.375D;
         double var20 = 0.25D;
         this.setOverrideBlockTexture(var22);
         if(var8 == 0) {
            this.setRenderBounds(var26, 0.0D, var26, 1.0D - var26, 0.25D, 1.0D - var26);
            this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
         }

         if(var8 == 2) {
            this.setRenderBounds(var26, var25, 0.0D, 1.0D - var26, var25 + var20, var24);
            this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
         }

         if(var8 == 3) {
            this.setRenderBounds(var26, var25, 1.0D - var24, 1.0D - var26, var25 + var20, 1.0D);
            this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
         }

         if(var8 == 4) {
            this.setRenderBounds(0.0D, var25, var26, var24, var25 + var20, 1.0D - var26);
            this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
         }

         if(var8 == 5) {
            this.setRenderBounds(1.0D - var24, var25, var26, 1.0D, var25 + var20, 1.0D - var26);
            this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
         }
      }

      this.clearOverrideBlockTexture();
      return true;
   }

   public boolean renderBlockStairs(BlockStairs par1BlockStairs, int par2, int par3, int par4) {
      par1BlockStairs.func_82541_d(this.blockAccess, par2, par3, par4);
      this.setRenderBoundsFromBlock(par1BlockStairs);
      this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
      boolean var5 = par1BlockStairs.func_82542_g(this.blockAccess, par2, par3, par4);
      this.setRenderBoundsFromBlock(par1BlockStairs);
      this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
      if(var5 && par1BlockStairs.func_82544_h(this.blockAccess, par2, par3, par4)) {
         this.setRenderBoundsFromBlock(par1BlockStairs);
         this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
      }

      return true;
   }

   public boolean renderBlockDoor(Block par1Block, int par2, int par3, int par4) {
      Tessellator var5 = Tessellator.instance;
      int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
      if((var6 & 8) != 0) {
         if(this.blockAccess.getBlockId(par2, par3 - 1, par4) != par1Block.blockID) {
            return false;
         }
      } else if(this.blockAccess.getBlockId(par2, par3 + 1, par4) != par1Block.blockID) {
         return false;
      }

      boolean var7 = false;
      float var8 = 0.5F;
      float var9 = 1.0F;
      float var10 = 0.8F;
      float var11 = 0.6F;
      int var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
      var5.setBrightness(this.renderMinY > 0.0D?var12:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
      var5.setColorOpaque_F(var8, var8, var8);
      this.renderFaceYNeg(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
      var7 = true;
      var5.setBrightness(this.renderMaxY < 1.0D?var12:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
      var5.setColorOpaque_F(var9, var9, var9);
      this.renderFaceYPos(par1Block, (double)par2, (double)par3, (double)par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
      var7 = true;
      var5.setBrightness(this.renderMinZ > 0.0D?var12:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
      var5.setColorOpaque_F(var10, var10, var10);
      Icon var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
      this.renderFaceZNeg(par1Block, (double)par2, (double)par3, (double)par4, var13);
      var7 = true;
      this.flipTexture = false;
      var5.setBrightness(this.renderMaxZ < 1.0D?var12:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
      var5.setColorOpaque_F(var10, var10, var10);
      var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
      this.renderFaceZPos(par1Block, (double)par2, (double)par3, (double)par4, var13);
      var7 = true;
      this.flipTexture = false;
      var5.setBrightness(this.renderMinX > 0.0D?var12:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
      var5.setColorOpaque_F(var11, var11, var11);
      var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
      this.renderFaceXNeg(par1Block, (double)par2, (double)par3, (double)par4, var13);
      var7 = true;
      this.flipTexture = false;
      var5.setBrightness(this.renderMaxX < 1.0D?var12:par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
      var5.setColorOpaque_F(var11, var11, var11);
      var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
      this.renderFaceXPos(par1Block, (double)par2, (double)par3, (double)par4, var13);
      var7 = true;
      this.flipTexture = false;
      return var7;
   }

   public void renderFaceYNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
      Tessellator var9 = Tessellator.instance;
      if(this.overrideBlockTexture != null) {
         par8Icon = this.overrideBlockTexture;
      }

      if(par8Icon != this.stackIcon) {
         this.getIconInterpolated(par8Icon);
      }

      double var10 = this.getInterpolatedU(this.renderMinX);
      double var12 = this.getInterpolatedU(this.renderMaxX);
      double var14 = this.getInterpolatedV(this.renderMinZ);
      double var16 = this.getInterpolatedV(this.renderMaxZ);
      if(this.renderMinX < 0.0D || this.renderMaxX > 1.0D) {
         var10 = (double)this.bSFTemp8;
         var12 = (double)this.bSFTemp9;
      }

      if(this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D) {
         var14 = (double)this.bSFTemp10;
         var16 = (double)this.bSFTemp11;
      }

      double var18 = var12;
      double var20 = var10;
      double var22 = var14;
      double var24 = var16;
      if(this.uvRotateBottom != 0) {
         if(this.uvRotateBottom == 2) {
            var10 = this.getInterpolatedU(this.renderMinZ);
            var14 = this.getInterpolatedV(1.0D - this.renderMaxX);
            var12 = this.getInterpolatedU(this.renderMaxZ);
            var16 = this.getInterpolatedV(1.0D - this.renderMinX);
            var22 = var14;
            var24 = var16;
            var18 = var10;
            var20 = var12;
            var14 = var16;
            var16 = var22;
         } else if(this.uvRotateBottom == 1) {
            var10 = this.getInterpolatedU(1.0D - this.renderMaxZ);
            var14 = this.getInterpolatedV(this.renderMinX);
            var12 = this.getInterpolatedU(1.0D - this.renderMinZ);
            var16 = this.getInterpolatedV(this.renderMaxX);
            var18 = var12;
            var20 = var10;
            var10 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
         } else if(this.uvRotateBottom == 3) {
            var10 = this.getInterpolatedU(1.0D - this.renderMinX);
            var12 = this.getInterpolatedU(1.0D - this.renderMaxX);
            var14 = this.getInterpolatedV(1.0D - this.renderMinZ);
            var16 = this.getInterpolatedV(1.0D - this.renderMaxZ);
            var18 = var12;
            var20 = var10;
            var22 = var14;
            var24 = var16;
         }
      }

      double var26 = par4 + this.renderMinY;
      if(this.enableAO) {
         var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
         var9.setBrightness(this.brightnessTopLeft);
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMaxZ, var20, var24);
         var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
         var9.setBrightness(this.brightnessBottomLeft);
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMinZ, var10, var14);
         var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
         var9.setBrightness(this.brightnessBottomRight);
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMinZ, var18, var22);
         var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
         var9.setBrightness(this.brightnessTopRight);
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMaxZ, var12, var16);
      } else {
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMaxZ, var20, var24);
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMinZ, var10, var14);
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMinZ, var18, var22);
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMaxZ, var12, var16);
      }

   }

   public void renderFaceYPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
      Tessellator var9 = Tessellator.instance;
      if(this.overrideBlockTexture != null) {
         par8Icon = this.overrideBlockTexture;
      }

      if(par8Icon != this.stackIcon) {
         this.getIconInterpolated(par8Icon);
      }

      double var10 = this.getInterpolatedU(this.renderMinX);
      double var12 = this.getInterpolatedU(this.renderMaxX);
      double var14 = this.getInterpolatedV(this.renderMinZ);
      double var16 = this.getInterpolatedV(this.renderMaxZ);
      if(this.renderMinX < 0.0D || this.renderMaxX > 1.0D) {
         var10 = (double)this.bSFTemp8;
         var12 = (double)this.bSFTemp9;
      }

      if(this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D) {
         var14 = (double)this.bSFTemp10;
         var16 = (double)this.bSFTemp11;
      }

      double var18 = var12;
      double var20 = var10;
      double var22 = var14;
      double var24 = var16;
      if(this.uvRotateTop != 0) {
         if(this.uvRotateTop == 1) {
            var10 = this.getInterpolatedU(this.renderMinZ);
            var14 = this.getInterpolatedV(1.0D - this.renderMaxX);
            var12 = this.getInterpolatedU(this.renderMaxZ);
            var16 = this.getInterpolatedV(1.0D - this.renderMinX);
            var22 = var14;
            var24 = var16;
            var18 = var10;
            var20 = var12;
            var14 = var16;
            var16 = var22;
         } else if(this.uvRotateTop == 2) {
            var10 = this.getInterpolatedU(1.0D - this.renderMaxZ);
            var14 = this.getInterpolatedV(this.renderMinX);
            var12 = this.getInterpolatedU(1.0D - this.renderMinZ);
            var16 = this.getInterpolatedV(this.renderMaxX);
            var18 = var12;
            var20 = var10;
            var10 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
         } else if(this.uvRotateTop == 3) {
            var10 = this.getInterpolatedU(1.0D - this.renderMinX);
            var12 = this.getInterpolatedU(1.0D - this.renderMaxX);
            var14 = this.getInterpolatedV(1.0D - this.renderMinZ);
            var16 = this.getInterpolatedV(1.0D - this.renderMaxZ);
            var18 = var12;
            var20 = var10;
            var22 = var14;
            var24 = var16;
         }
      }

      double var26 = par4 + this.renderMaxY;
      if(this.enableAO) {
         var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
         var9.setBrightness(this.brightnessTopLeft);
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMaxZ, var12, var16);
         var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
         var9.setBrightness(this.brightnessBottomLeft);
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMinZ, var18, var22);
         var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
         var9.setBrightness(this.brightnessBottomRight);
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMinZ, var10, var14);
         var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
         var9.setBrightness(this.brightnessTopRight);
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMaxZ, var20, var24);
      } else {
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMaxZ, var12, var16);
         var9.addVertexWithUV(par2 + this.renderMaxX, var26, par6 + this.renderMinZ, var18, var22);
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMinZ, var10, var14);
         var9.addVertexWithUV(par2 + this.renderMinX, var26, par6 + this.renderMaxZ, var20, var24);
      }

   }

   public void renderFaceZNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
      Tessellator var9 = Tessellator.instance;
      if(this.overrideBlockTexture != null) {
         par8Icon = this.overrideBlockTexture;
      }

      if(par8Icon != this.stackIcon) {
         this.getIconInterpolated(par8Icon);
      }

      double var10 = this.getInterpolatedU(this.renderMinX);
      double var12 = this.getInterpolatedU(this.renderMaxX);
      double var14 = this.getInterpolatedV(1.0D - this.renderMaxY);
      double var16 = this.getInterpolatedV(1.0D - this.renderMinY);
      if(this.renderMinX < 0.0D || this.renderMaxX > 1.0D) {
         var10 = (double)this.bSFTemp8;
         var12 = (double)this.bSFTemp9;
      }

      if(this.renderMinY < 0.0D || this.renderMaxY > 1.0D) {
         var14 = (double)this.bSFTemp10;
         var16 = (double)this.bSFTemp11;
      }

      double var18 = var12;
      double var20 = var10;
      double var22 = var14;
      double var24 = var16;
      if(this.uvRotateEast != 0) {
         if(this.uvRotateEast == 2) {
            var10 = this.getInterpolatedU(this.renderMinY);
            var14 = this.getInterpolatedV(1.0D - this.renderMinX);
            var12 = this.getInterpolatedU(this.renderMaxY);
            var16 = this.getInterpolatedV(1.0D - this.renderMaxX);
            var22 = var14;
            var24 = var16;
            var18 = var10;
            var20 = var12;
            var14 = var16;
            var16 = var22;
         } else if(this.uvRotateEast == 1) {
            var10 = this.getInterpolatedU(1.0D - this.renderMaxY);
            var14 = this.getInterpolatedV(this.renderMaxX);
            var12 = this.getInterpolatedU(1.0D - this.renderMinY);
            var16 = this.getInterpolatedV(this.renderMinX);
            var18 = var12;
            var20 = var10;
            var10 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
         } else if(this.uvRotateEast == 3) {
            var10 = this.getInterpolatedU(1.0D - this.renderMinX);
            var12 = this.getInterpolatedU(1.0D - this.renderMaxX);
            var14 = this.getInterpolatedV(this.renderMaxY);
            var16 = this.getInterpolatedV(this.renderMinY);
            var18 = var12;
            var20 = var10;
            var22 = var14;
            var24 = var16;
         }
      }

      double var26 = par6 + this.renderMinZ;
      if(this.enableAO) {
         var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
         var9.setBrightness(this.brightnessTopLeft);
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMaxY, var26, var18, var22);
         var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
         var9.setBrightness(this.brightnessBottomLeft);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMaxY, var26, var10, var14);
         var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
         var9.setBrightness(this.brightnessBottomRight);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMinY, var26, var20, var24);
         var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
         var9.setBrightness(this.brightnessTopRight);
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMinY, var26, var12, var16);
      } else {
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMaxY, var26, var18, var22);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMaxY, var26, var10, var14);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMinY, var26, var20, var24);
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMinY, var26, var12, var16);
      }

   }

   public void renderFaceZPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
      Tessellator var9 = Tessellator.instance;
      if(this.overrideBlockTexture != null) {
         par8Icon = this.overrideBlockTexture;
      }

      if(par8Icon != this.stackIcon) {
         this.getIconInterpolated(par8Icon);
      }

      double var10 = this.getInterpolatedU(this.renderMinX);
      double var12 = this.getInterpolatedU(this.renderMaxX);
      double var14 = this.getInterpolatedV(1.0D - this.renderMaxY);
      double var16 = this.getInterpolatedV(1.0D - this.renderMinY);
      if(this.renderMinX < 0.0D || this.renderMaxX > 1.0D) {
         var10 = (double)this.bSFTemp8;
         var12 = (double)this.bSFTemp9;
      }

      if(this.renderMinY < 0.0D || this.renderMaxY > 1.0D) {
         var14 = (double)this.bSFTemp10;
         var16 = (double)this.bSFTemp11;
      }

      double var18 = var12;
      double var20 = var10;
      double var22 = var14;
      double var24 = var16;
      if(this.uvRotateWest != 0) {
         if(this.uvRotateWest == 1) {
            var10 = this.getInterpolatedU(this.renderMinY);
            var16 = this.getInterpolatedV(1.0D - this.renderMinX);
            var12 = this.getInterpolatedU(this.renderMaxY);
            var14 = this.getInterpolatedV(1.0D - this.renderMaxX);
            var22 = var14;
            var24 = var16;
            var18 = var10;
            var20 = var12;
            var14 = var16;
            var16 = var22;
         } else if(this.uvRotateWest == 2) {
            var10 = this.getInterpolatedU(1.0D - this.renderMaxY);
            var14 = this.getInterpolatedV(this.renderMinX);
            var12 = this.getInterpolatedU(1.0D - this.renderMinY);
            var16 = this.getInterpolatedV(this.renderMaxX);
            var18 = var12;
            var20 = var10;
            var10 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
         } else if(this.uvRotateWest == 3) {
            var10 = this.getInterpolatedU(1.0D - this.renderMinX);
            var12 = this.getInterpolatedU(1.0D - this.renderMaxX);
            var14 = this.getInterpolatedV(this.renderMaxY);
            var16 = this.getInterpolatedV(this.renderMinY);
            var18 = var12;
            var20 = var10;
            var22 = var14;
            var24 = var16;
         }
      }

      double var26 = par6 + this.renderMaxZ;
      if(this.enableAO) {
         var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
         var9.setBrightness(this.brightnessTopLeft);
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMaxY, var26, var10, var14);
         var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
         var9.setBrightness(this.brightnessBottomLeft);
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMinY, var26, var20, var24);
         var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
         var9.setBrightness(this.brightnessBottomRight);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMinY, var26, var12, var16);
         var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
         var9.setBrightness(this.brightnessTopRight);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMaxY, var26, var18, var22);
      } else {
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMaxY, var26, var10, var14);
         var9.addVertexWithUV(par2 + this.renderMinX, par4 + this.renderMinY, var26, var20, var24);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMinY, var26, var12, var16);
         var9.addVertexWithUV(par2 + this.renderMaxX, par4 + this.renderMaxY, var26, var18, var22);
      }

   }

   public void renderFaceXNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
      Tessellator var9 = Tessellator.instance;
      if(this.overrideBlockTexture != null) {
         par8Icon = this.overrideBlockTexture;
      }

      if(par8Icon != this.stackIcon) {
         this.getIconInterpolated(par8Icon);
      }

      double var10 = this.getInterpolatedU(this.renderMinZ);
      double var12 = this.getInterpolatedU(this.renderMaxZ);
      double var14 = this.getInterpolatedV(1.0D - this.renderMaxY);
      double var16 = this.getInterpolatedV(1.0D - this.renderMinY);
      if(this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D) {
         var10 = (double)this.bSFTemp8;
         var12 = (double)this.bSFTemp9;
      }

      if(this.renderMinY < 0.0D || this.renderMaxY > 1.0D) {
         var14 = (double)this.bSFTemp10;
         var16 = (double)this.bSFTemp11;
      }

      double var18 = var12;
      double var20 = var10;
      double var22 = var14;
      double var24 = var16;
      if(this.uvRotateNorth != 0) {
         if(this.uvRotateNorth == 1) {
            var10 = this.getInterpolatedU(this.renderMinY);
            var14 = this.getInterpolatedV(1.0D - this.renderMaxZ);
            var12 = this.getInterpolatedU(this.renderMaxY);
            var16 = this.getInterpolatedV(1.0D - this.renderMinZ);
            var22 = var14;
            var24 = var16;
            var18 = var10;
            var20 = var12;
            var14 = var16;
            var16 = var22;
         } else if(this.uvRotateNorth == 2) {
            var10 = this.getInterpolatedU(1.0D - this.renderMaxY);
            var14 = this.getInterpolatedV(this.renderMinZ);
            var12 = this.getInterpolatedU(1.0D - this.renderMinY);
            var16 = this.getInterpolatedV(this.renderMaxZ);
            var18 = var12;
            var20 = var10;
            var10 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
         } else if(this.uvRotateNorth == 3) {
            var10 = this.getInterpolatedU(1.0D - this.renderMinZ);
            var12 = this.getInterpolatedU(1.0D - this.renderMaxZ);
            var14 = this.getInterpolatedV(this.renderMaxY);
            var16 = this.getInterpolatedV(this.renderMinY);
            var18 = var12;
            var20 = var10;
            var22 = var14;
            var24 = var16;
         }
      }

      double var26 = par2 + this.renderMinX;
      if(this.enableAO) {
         var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
         var9.setBrightness(this.brightnessTopLeft);
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMaxZ, var18, var22);
         var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
         var9.setBrightness(this.brightnessBottomLeft);
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMinZ, var10, var14);
         var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
         var9.setBrightness(this.brightnessBottomRight);
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMinZ, var20, var24);
         var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
         var9.setBrightness(this.brightnessTopRight);
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMaxZ, var12, var16);
      } else {
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMaxZ, var18, var22);
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMinZ, var10, var14);
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMinZ, var20, var24);
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMaxZ, var12, var16);
      }

   }

   public void renderFaceXPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
      Tessellator var9 = Tessellator.instance;
      if(this.overrideBlockTexture != null) {
         par8Icon = this.overrideBlockTexture;
      }

      if(par8Icon != this.stackIcon) {
         this.getIconInterpolated(par8Icon);
      }

      double var10 = this.getInterpolatedU(this.renderMinZ);
      double var12 = this.getInterpolatedU(this.renderMaxZ);
      double var14 = this.getInterpolatedV(1.0D - this.renderMaxY);
      double var16 = this.getInterpolatedV(1.0D - this.renderMinY);
      if(this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D) {
         var10 = (double)this.bSFTemp8;
         var12 = (double)this.bSFTemp9;
      }

      if(this.renderMinY < 0.0D || this.renderMaxY > 1.0D) {
         var14 = (double)this.bSFTemp10;
         var16 = (double)this.bSFTemp11;
      }

      double var18 = var12;
      double var20 = var10;
      double var22 = var14;
      double var24 = var16;
      if(this.uvRotateSouth != 0) {
         if(this.uvRotateSouth == 2) {
            var10 = this.getInterpolatedU(this.renderMinY);
            var14 = this.getInterpolatedV(1.0D - this.renderMinZ);
            var12 = this.getInterpolatedU(this.renderMaxY);
            var16 = this.getInterpolatedV(1.0D - this.renderMaxZ);
            var22 = var14;
            var24 = var16;
            var18 = var10;
            var20 = var12;
            var14 = var16;
            var16 = var22;
         } else if(this.uvRotateSouth == 1) {
            var10 = this.getInterpolatedU(1.0D - this.renderMaxY);
            var14 = this.getInterpolatedV(this.renderMaxZ);
            var12 = this.getInterpolatedU(1.0D - this.renderMinY);
            var16 = this.getInterpolatedV(this.renderMinZ);
            var18 = var12;
            var20 = var10;
            var10 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
         } else if(this.uvRotateSouth == 3) {
            var10 = this.getInterpolatedU(1.0D - this.renderMinZ);
            var12 = this.getInterpolatedU(1.0D - this.renderMaxZ);
            var14 = this.getInterpolatedV(this.renderMaxY);
            var16 = this.getInterpolatedV(this.renderMinY);
            var18 = var12;
            var20 = var10;
            var22 = var14;
            var24 = var16;
         }
      }

      double var26 = par2 + this.renderMaxX;
      if(this.enableAO) {
         var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
         var9.setBrightness(this.brightnessTopLeft);
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMaxZ, var20, var24);
         var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
         var9.setBrightness(this.brightnessBottomLeft);
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMinZ, var12, var16);
         var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
         var9.setBrightness(this.brightnessBottomRight);
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMinZ, var18, var22);
         var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
         var9.setBrightness(this.brightnessTopRight);
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMaxZ, var10, var14);
      } else {
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMaxZ, var20, var24);
         var9.addVertexWithUV(var26, par4 + this.renderMinY, par6 + this.renderMinZ, var12, var16);
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMinZ, var18, var22);
         var9.addVertexWithUV(var26, par4 + this.renderMaxY, par6 + this.renderMaxZ, var10, var14);
      }

   }

   public void renderBlockAsItem(Block par1Block, int par2, float par3) {
      Tessellator var4 = Tessellator.instance;
      boolean var5 = par1Block.blockID == Block.grass.blockID;
      if(par1Block == Block.dispenser || par1Block == Block.dropper || par1Block == Block.furnaceIdle) {
         par2 = 3;
      }

      int var6;
      float var7;
      float var8;
      float var9;
      if(this.useInventoryTint) {
         var6 = par1Block.getRenderColor(par2);
         if(var5) {
            var6 = 16777215;
         }

         var7 = (float)(var6 >> 16 & 255) / 255.0F;
         var8 = (float)(var6 >> 8 & 255) / 255.0F;
         var9 = (float)(var6 & 255) / 255.0F;
         GL11.glColor4f(var7 * par3, var8 * par3, var9 * par3, 1.0F);
      }

      var6 = par1Block.getRenderType();
      this.setRenderBoundsFromBlock(par1Block);
      int var10;
      if(var6 != 0 && var6 != 31 && var6 != 39 && var6 != 16 && var6 != 26) {
         if(var6 == 1) {
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            this.drawCrossedSquares(par1Block, par2, -0.5D, -0.5D, -0.5D, 1.0F);
            var4.draw();
         } else if(var6 == 19) {
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            par1Block.setBlockBoundsForItemRender();
            this.renderBlockStemSmall(par1Block, par2, this.renderMaxY, -0.5D, -0.5D, -0.5D);
            var4.draw();
         } else if(var6 == 23) {
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            par1Block.setBlockBoundsForItemRender();
            var4.draw();
         } else if(var6 == 13) {
            par1Block.setBlockBoundsForItemRender();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            var7 = 0.0625F;
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 0));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 1.0F, 0.0F);
            this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 1));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, -1.0F);
            var4.addTranslation(0.0F, 0.0F, var7);
            this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 2));
            var4.addTranslation(0.0F, 0.0F, -var7);
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, 1.0F);
            var4.addTranslation(0.0F, 0.0F, -var7);
            this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 3));
            var4.addTranslation(0.0F, 0.0F, var7);
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0F, 0.0F, 0.0F);
            var4.addTranslation(var7, 0.0F, 0.0F);
            this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 4));
            var4.addTranslation(-var7, 0.0F, 0.0F);
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0F, 0.0F, 0.0F);
            var4.addTranslation(-var7, 0.0F, 0.0F);
            this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 5));
            var4.addTranslation(var7, 0.0F, 0.0F);
            var4.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
         } else if(var6 == 22) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            ChestItemRenderHelper.instance.renderChest(par1Block, par2, par3);
            GL11.glEnable('\u803a');
         } else if(var6 == 6) {
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            this.renderBlockCropsImpl(par1Block, par2, -0.5D, -0.5D, -0.5D);
            var4.draw();
         } else if(var6 == 2) {
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            this.renderTorchAtAngle(par1Block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D, 0);
            var4.draw();
         } else if(var6 == 10) {
            for(var10 = 0; var10 < 2; ++var10) {
               if(var10 == 0) {
                  this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
               }

               if(var10 == 1) {
                  this.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
               }

               GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
               var4.startDrawingQuads();
               var4.setNormal(0.0F, -1.0F, 0.0F);
               this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 0));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 1.0F, 0.0F);
               this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 1));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, -1.0F);
               this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, 1.0F);
               this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 3));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(-1.0F, 0.0F, 0.0F);
               this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 4));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(1.0F, 0.0F, 0.0F);
               this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 5));
               var4.draw();
               GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
         } else if(var6 == 27) {
            var10 = 0;
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            var4.startDrawingQuads();

            for(int var17 = 0; var17 < 8; ++var17) {
               byte var12 = 0;
               byte var13 = 1;
               if(var17 == 0) {
                  var12 = 2;
               }

               if(var17 == 1) {
                  var12 = 3;
               }

               if(var17 == 2) {
                  var12 = 4;
               }

               if(var17 == 3) {
                  var12 = 5;
                  var13 = 2;
               }

               if(var17 == 4) {
                  var12 = 6;
                  var13 = 3;
               }

               if(var17 == 5) {
                  var12 = 7;
                  var13 = 5;
               }

               if(var17 == 6) {
                  var12 = 6;
                  var13 = 2;
               }

               if(var17 == 7) {
                  var12 = 3;
               }

               float var14 = (float)var12 / 16.0F;
               float var15 = 1.0F - (float)var10 / 16.0F;
               float var16 = 1.0F - (float)(var10 + var13) / 16.0F;
               var10 += var13;
               this.setRenderBounds((double)(0.5F - var14), (double)var16, (double)(0.5F - var14), (double)(0.5F + var14), (double)var15, (double)(0.5F + var14));
               var4.setNormal(0.0F, -1.0F, 0.0F);
               this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 0));
               var4.setNormal(0.0F, 1.0F, 0.0F);
               this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 1));
               var4.setNormal(0.0F, 0.0F, -1.0F);
               this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 2));
               var4.setNormal(0.0F, 0.0F, 1.0F);
               this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 3));
               var4.setNormal(-1.0F, 0.0F, 0.0F);
               this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 4));
               var4.setNormal(1.0F, 0.0F, 0.0F);
               this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 5));
            }

            var4.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         } else if(var6 == 11) {
            for(var10 = 0; var10 < 4; ++var10) {
               var8 = 0.125F;
               if(var10 == 0) {
                  this.setRenderBounds((double)(0.5F - var8), 0.0D, 0.0D, (double)(0.5F + var8), 1.0D, (double)(var8 * 2.0F));
               }

               if(var10 == 1) {
                  this.setRenderBounds((double)(0.5F - var8), 0.0D, (double)(1.0F - var8 * 2.0F), (double)(0.5F + var8), 1.0D, 1.0D);
               }

               var8 = 0.0625F;
               if(var10 == 2) {
                  this.setRenderBounds((double)(0.5F - var8), (double)(1.0F - var8 * 3.0F), (double)(-var8 * 2.0F), (double)(0.5F + var8), (double)(1.0F - var8), (double)(1.0F + var8 * 2.0F));
               }

               if(var10 == 3) {
                  this.setRenderBounds((double)(0.5F - var8), (double)(0.5F - var8 * 3.0F), (double)(-var8 * 2.0F), (double)(0.5F + var8), (double)(0.5F - var8), (double)(1.0F + var8 * 2.0F));
               }

               GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
               var4.startDrawingQuads();
               var4.setNormal(0.0F, -1.0F, 0.0F);
               this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 0));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 1.0F, 0.0F);
               this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 1));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, -1.0F);
               this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, 1.0F);
               this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 3));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(-1.0F, 0.0F, 0.0F);
               this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 4));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(1.0F, 0.0F, 0.0F);
               this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 5));
               var4.draw();
               GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }

            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         } else if(var6 == 21) {
            for(var10 = 0; var10 < 3; ++var10) {
               var8 = 0.0625F;
               if(var10 == 0) {
                  this.setRenderBounds((double)(0.5F - var8), 0.30000001192092896D, 0.0D, (double)(0.5F + var8), 1.0D, (double)(var8 * 2.0F));
               }

               if(var10 == 1) {
                  this.setRenderBounds((double)(0.5F - var8), 0.30000001192092896D, (double)(1.0F - var8 * 2.0F), (double)(0.5F + var8), 1.0D, 1.0D);
               }

               var8 = 0.0625F;
               if(var10 == 2) {
                  this.setRenderBounds((double)(0.5F - var8), 0.5D, 0.0D, (double)(0.5F + var8), (double)(1.0F - var8), 1.0D);
               }

               GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
               var4.startDrawingQuads();
               var4.setNormal(0.0F, -1.0F, 0.0F);
               this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 0));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 1.0F, 0.0F);
               this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 1));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, -1.0F);
               this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, 1.0F);
               this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 3));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(-1.0F, 0.0F, 0.0F);
               this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 4));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(1.0F, 0.0F, 0.0F);
               this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSide(par1Block, 5));
               var4.draw();
               GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
         } else if(var6 == 32) {
            for(var10 = 0; var10 < 2; ++var10) {
               if(var10 == 0) {
                  this.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
               }

               if(var10 == 1) {
                  this.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
               }

               GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
               var4.startDrawingQuads();
               var4.setNormal(0.0F, -1.0F, 0.0F);
               this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 1.0F, 0.0F);
               this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, -1.0F);
               this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, 1.0F);
               this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(-1.0F, 0.0F, 0.0F);
               this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(1.0F, 0.0F, 0.0F);
               this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
               var4.draw();
               GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }

            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         } else if(var6 == 35) {
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            this.renderBlockAnvilOrient((BlockAnvil)par1Block, 0, 0, 0, par2 << 2, true);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
         } else if(var6 == 34) {
            for(var10 = 0; var10 < 3; ++var10) {
               if(var10 == 0) {
                  this.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
                  this.setOverrideBlockTexture(this.getBlockIcon(Block.obsidian));
               } else if(var10 == 1) {
                  this.setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
                  this.setOverrideBlockTexture(this.getBlockIcon(Block.beacon));
               } else if(var10 == 2) {
                  this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                  this.setOverrideBlockTexture(this.getBlockIcon(Block.glass));
               }

               GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
               var4.startDrawingQuads();
               var4.setNormal(0.0F, -1.0F, 0.0F);
               this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 1.0F, 0.0F);
               this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, -1.0F);
               this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(0.0F, 0.0F, 1.0F);
               this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(-1.0F, 0.0F, 0.0F);
               this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
               var4.draw();
               var4.startDrawingQuads();
               var4.setNormal(1.0F, 0.0F, 0.0F);
               this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
               var4.draw();
               GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }

            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            this.clearOverrideBlockTexture();
         } else if(var6 == 38) {
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            this.renderBlockHopperMetadata((BlockHopper)par1Block, 0, 0, 0, 0, true);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
         }
      } else {
         if(var6 == 16) {
            par2 = 1;
         }

         par1Block.setBlockBoundsForItemRender();
         this.setRenderBoundsFromBlock(par1Block);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
         var4.startDrawingQuads();
         var4.setNormal(0.0F, -1.0F, 0.0F);
         this.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
         var4.draw();
         if(var5 && this.useInventoryTint) {
            var10 = par1Block.getRenderColor(par2);
            var8 = (float)(var10 >> 16 & 255) / 255.0F;
            var9 = (float)(var10 >> 8 & 255) / 255.0F;
            float var11 = (float)(var10 & 255) / 255.0F;
            GL11.glColor4f(var8 * par3, var9 * par3, var11 * par3, 1.0F);
         }

         var4.startDrawingQuads();
         var4.setNormal(0.0F, 1.0F, 0.0F);
         this.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
         var4.draw();
         if(var5 && this.useInventoryTint) {
            GL11.glColor4f(par3, par3, par3, 1.0F);
         }

         var4.startDrawingQuads();
         var4.setNormal(0.0F, 0.0F, -1.0F);
         this.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
         var4.draw();
         var4.startDrawingQuads();
         var4.setNormal(0.0F, 0.0F, 1.0F);
         this.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
         var4.draw();
         var4.startDrawingQuads();
         var4.setNormal(-1.0F, 0.0F, 0.0F);
         this.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
         var4.draw();
         var4.startDrawingQuads();
         var4.setNormal(1.0F, 0.0F, 0.0F);
         this.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
         var4.draw();
         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
      }

   }

   public static boolean renderItemIn3d(int par0) {
      return par0 == 0?true:(par0 == 31?true:(par0 == 39?true:(par0 == 13?true:(par0 == 10?true:(par0 == 11?true:(par0 == 27?true:(par0 == 22?true:(par0 == 21?true:(par0 == 16?true:(par0 == 26?true:(par0 == 32?true:(par0 == 34?true:par0 == 35))))))))))));
   }

   public Icon getBlockIcon(Block par1Block, IBlockAccess par2IBlockAccess, int par3, int par4, int par5, int par6) {
      return this.getIconSafe(par1Block.getBlockTexture(par2IBlockAccess, par3, par4, par5, par6));
   }

   public Icon getBlockIconFromSideAndMetadata(Block par1Block, int par2, int par3) {
      return this.getIconSafe(par1Block.getIcon(par2, par3));
   }

   public Icon getBlockIconFromSide(Block par1Block, int par2) {
      return this.getIconSafe(par1Block.getBlockTextureFromSide(par2));
   }

   public Icon getBlockIcon(Block par1Block) {
      return this.getIconSafe(par1Block.getBlockTextureFromSide(1));
   }

   public Icon getIconSafe(Icon par1Icon) {
      if(par1Icon == null) {
         par1Icon = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
      }

      return (Icon)par1Icon;
   }

   static {
      int int3 = Block.blocksList.length;
      fastSideList = new boolean[int3];
      int[] int4 = new int[]{1, 2, 3, 4, 7, 12, 13, 14, 15, 16, 21, 24, 49, 56};

      for(int int1 = 0; int1 < int4.length; ++int1) {
         int int2;
         if(int3 >= (int2 = int4[int1]) && Block.blocksList[int2] != null) {
            Block block = Block.blocksList[int2];
            fastSideList[int2] = block.isOpaqueCube() && block.getBlockBoundsMinX() <= 0.0D && block.getBlockBoundsMaxX() >= 1.0D && block.getBlockBoundsMinY() <= 0.0D && block.getBlockBoundsMaxY() >= 1.0D && block.getBlockBoundsMinZ() <= 0.0D && block.getBlockBoundsMaxZ() >= 1.0D;
         }
      }

   }
}
