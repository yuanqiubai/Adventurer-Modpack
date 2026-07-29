package net.minecraft.client.renderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {

   public World worldObj;
   private int glRenderList = -1;
   private static Tessellator tessellator = Tessellator.instance;
   public static int chunksUpdated;
   public int posX;
   public int posY;
   public int posZ;
   public int posXMinus;
   public int posYMinus;
   public int posZMinus;
   public int posXClip;
   public int posYClip;
   public int posZClip;
   public boolean isInFrustum;
   public boolean[] skipRenderPass = new boolean[2];
   public int posXPlus;
   public int posYPlus;
   public int posZPlus;
   public boolean needsUpdate;
   public AxisAlignedBB rendererBoundingBox;
   public int chunkIndex;
   public boolean isVisible = true;
   public boolean isWaitingOnOcclusionQuery;
   public int glOcclusionQuery;
   public boolean isChunkLit;
   private boolean isInitialized;
   public List tileEntityRenderers = new ArrayList();
   private List tileEntities;
   private int bytesDrawn;
   protected boolean needsBoxUpdate = false;
   private final boolean skipAllRenderAccessFirst = false;
   private boolean skipAllRenderAccess = false;
   private static final boolean[] blockExist;
   private static final boolean[] blockTransparent;
   private boolean[] renderWorkList = new boolean[2];
   private static Minecraft minecraft;

   public WorldRenderer(World par1World, List par2List, int par3, int par4, int par5, int par6) {
      this.worldObj = par1World;
      this.tileEntities = par2List;
      this.glRenderList = par6;
      this.posX = -999;
      this.setPosition(par3, par4, par5);
      this.needsUpdate = false;
   }

   public void setPosition(int par1, int par2, int par3) {
      if(par1 != this.posX || par2 != this.posY || par3 != this.posZ) {
         this.setDontDraw();
         this.posX = par1;
         this.posY = par2;
         this.posZ = par3;
         this.posXPlus = par1 + 8;
         this.posYPlus = par2 + 8;
         this.posZPlus = par3 + 8;
         this.posXClip = par1 & 1023;
         this.posYClip = par2;
         this.posZClip = par3 & 1023;
         this.posXMinus = par1 - this.posXClip;
         this.posYMinus = par2 - this.posYClip;
         this.posZMinus = par3 - this.posZClip;
         if(this.rendererBoundingBox != null) {
            this.rendererBoundingBox.setBounds((double)par1, (double)par2, (double)par3, (double)par1 + 16.0D, (double)par2 + 16.0D, (double)par3 + 16.0D);
         } else {
            this.rendererBoundingBox = AxisAlignedBB.getBoundingBox((double)par1, (double)par2, (double)par3, (double)par1 + 16.0D, (double)par2 + 16.0D, (double)par3 + 16.0D);
         }

         this.needsBoxUpdate = true;
         this.skipAllRenderAccess = false;
         this.markDirty();
      }

   }

   private void setupGLTranslation() {
      GL11.glTranslatef((float)this.posXClip, (float)this.posYClip, (float)this.posZClip);
   }

   public void updateRenderer() {
      if(this.needsUpdate) {
         this.needsUpdate = false;
         int var1 = this.posX;
         int var2 = this.posY;
         int var3 = this.posZ;
         int var4 = this.posX + 16;
         int var5 = this.posY + 16;
         int var6 = this.posZ + 16;
         Chunk.isLit = false;
         HashSet var7 = new HashSet();
         var7.addAll(this.tileEntityRenderers);
         this.tileEntityRenderers.clear();

         for(int var8 = 0; var8 < this.skipRenderPass.length; ++var8) {
            this.skipRenderPass[var8] = true;
         }

         Chunk var24 = null;
         ChunkCache var9 = null;
         ExtendedBlockStorage var10 = null;
         boolean var11 = true;
         boolean var12 = false;
         boolean var13 = false;
         boolean var14 = false;
         boolean var15 = false;
         boolean var16 = false;
         int var26;
         int var27;
         int var28;
         int var29;
         if(var2 >= 0 && var2 < 256 && !(var24 = this.worldObj.getChunkFromChunkCoords(var1 >> 4, var3 >> 4)).isEmpty() && (var10 = var24.getBlockStorageArray()[var2 >> 4]) != null && !(var9 = new ChunkCache(this.worldObj, var1 - 1, var2 - 1, var3 - 1, var4 + 1, var5 + 1, var6 + 1, 1)).extendedLevelsInChunkCache()) {
            if(var11 && var2 - 1 >= 0) {
               var26 = var1;

               for(var27 = var1 + 16; var26 < var27; ++var26) {
                  var28 = var3;

                  for(var29 = var3 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.getBlockId(var26, var2 - 1, var28)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && var5 < 256) {
               var26 = var1;

               for(var27 = var1 + 16; var26 < var27; ++var26) {
                  var28 = var3;

                  for(var29 = var3 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.getBlockId(var26, var5, var28)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.worldObj.getChunkFromChunkCoords(var1 - 1 >> 4, var3 >> 4).isEmpty()) {
               var26 = var2;

               for(var27 = var2 + 16; var26 < var27; ++var26) {
                  var28 = var3;

                  for(var29 = var3 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.getBlockId(var1 - 1, var26, var28)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.worldObj.getChunkFromChunkCoords(var4 >> 4, var3 >> 4).isEmpty()) {
               var26 = var2;

               for(var27 = var2 + 16; var26 < var27; ++var26) {
                  var28 = var3;

                  for(var29 = var3 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.getBlockId(var4, var26, var28)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.worldObj.getChunkFromChunkCoords(var1 >> 4, var3 - 1 >> 4).isEmpty()) {
               var26 = var1;

               for(var27 = var1 + 16; var26 < var27; ++var26) {
                  var28 = var2;

                  for(var29 = var2 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.getBlockId(var26, var28, var3 - 1)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.worldObj.getChunkFromChunkCoords(var1 >> 4, var6 >> 4).isEmpty()) {
               var26 = var1;

               for(var27 = var1 + 16; var26 < var27; ++var26) {
                  var28 = var2;

                  for(var29 = var2 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.getBlockId(var26, var28, var6)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && minecraft.renderViewEntity != null && var1 >> 4 == MathHelper.floor_double(minecraft.renderViewEntity.lastTickPosX / 16.0D) && var2 >> 4 == MathHelper.floor_double(minecraft.renderViewEntity.lastTickPosY / 16.0D) && var3 >> 4 == MathHelper.floor_double(minecraft.renderViewEntity.lastTickPosZ / 16.0D)) {
               var11 = false;
            }
         }

         if(!var11) {
            ++chunksUpdated;
            RenderBlocks var17 = new RenderBlocks(var9);
            this.bytesDrawn = 0;
            Tessellator var18 = Tessellator.instance;

            for(int var25 = 0; var25 < 2; ++var25) {
               boolean var19 = true;
               boolean var20 = false;
               boolean var21 = var25 == 0;
               boolean var22 = false;
               GL11.glPushMatrix();
               var18.startDrawingQuads();
               var18.setTranslation((double)(-this.posXMinus), (double)(-this.posYMinus), (double)(-this.posZMinus));
               GL11.glNewList(this.glRenderList + var25, 4864);
               if(var21) {
                  for(var26 = var2; var26 < var5; ++var26) {
                     for(var27 = var3; var27 < var6; ++var27) {
                        for(var28 = var1; var28 < var4; ++var28) {
                           if(blockExist[var29 = var10.getExtBlockID(var28 - var1, var26 - var2, var27 - var3)]) {
                              if(Block.blocksList[var29].hasTileEntity()) {
                                 TileEntity var23 = var9.getBlockTileEntity(var28, var26, var27);
                                 if(TileEntityRenderer.instance.hasSpecialRenderer(var23)) {
                                    this.tileEntityRenderers.add(var23);
                                 }
                              }

                              if(Block.blocksList[var29].getRenderBlockPass() == var25) {
                                 var20 |= var17.renderBlockByRenderType(Block.blocksList[var29], var28, var26, var27);
                              } else if(var19) {
                                 var19 = false;
                              }
                           }
                        }
                     }
                  }
               } else {
                  for(var26 = var2; var26 < var5; ++var26) {
                     for(var27 = var3; var27 < var6; ++var27) {
                        for(var28 = var1; var28 < var4; ++var28) {
                           if(blockExist[var29 = var10.getExtBlockID(var28 - var1, var26 - var2, var27 - var3)] && Block.blocksList[var29].getRenderBlockPass() == var25) {
                              var20 |= var17.renderBlockByRenderType(Block.blocksList[var29], var28, var26, var27);
                           }
                        }
                     }
                  }
               }

               this.bytesDrawn += var18.draw();
               GL11.glEndList();
               this.renderWorkList[var25] = var20;
               var18.setTranslation(0.0D, 0.0D, 0.0D);
               GL11.glPopMatrix();
               this.skipRenderPass[var25] = !var20;
               if(var19) {
                  break;
               }
            }
         }

         HashSet var30 = new HashSet();
         var30.addAll(this.tileEntityRenderers);
         var30.removeAll(var7);
         this.tileEntities.addAll(var30);
         var7.removeAll(this.tileEntityRenderers);
         this.tileEntities.removeAll(var7);
         this.isChunkLit = Chunk.isLit;
         this.skipAllRenderAccess = this.skipRenderPass[0] && this.skipRenderPass[1];
         if(this.needsBoxUpdate && !this.skipAllRenderPasses()) {
            GL11.glNewList(this.glRenderList + 2, 4864);
            RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB((double)((float)this.posXClip), (double)((float)this.posYClip), (double)((float)this.posZClip), (double)((float)this.posXClip + 16.0F), (double)((float)this.posYClip + 16.0F), (double)((float)this.posZClip + 16.0F)));
            GL11.glEndList();
            this.needsBoxUpdate = false;
         }

      }
   }

   public float distanceToEntitySquared(Entity par1Entity) {
      float var2 = (float)(par1Entity.posX - (double)this.posXPlus);
      float var3 = (float)(par1Entity.posY - (double)this.posYPlus);
      float var4 = (float)(par1Entity.posZ - (double)this.posZPlus);
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   public void setDontDraw() {
      if(!this.skipAllRenderPasses()) {
         GL11.glNewList(this.glRenderList + 2, 4864);
         GL11.glEndList();
      }

      for(int var1 = 0; var1 < 2; ++var1) {
         if(this.renderWorkList == null || this.renderWorkList[var1]) {
            GL11.glNewList(this.glRenderList + var1, 4864);
            GL11.glEndList();
            if(this.renderWorkList != null) {
               this.renderWorkList[var1] = false;
            }
         }

         this.skipRenderPass[var1] = true;
      }

      this.isInFrustum = false;
      this.isInitialized = false;
      this.skipAllRenderAccess = false;
   }

   public void stopRendering() {
      this.setDontDraw();
      this.worldObj = null;
      this.tileEntities.clear();
      this.tileEntityRenderers.clear();
   }

   public int getGLCallListForPass(int par1) {
      return this.glRenderList + par1;
   }

   public void updateInFrustum(ICamera par1ICamera) {
      this.isInFrustum = par1ICamera.isBoundingBoxInFrustum(this.rendererBoundingBox);
   }

   public void callOcclusionQueryList() {
      GL11.glCallList(this.glRenderList + 2);
   }

   public boolean skipAllRenderPasses() {
      return this.skipAllRenderAccess;
   }

   public void markDirty() {
      this.needsUpdate = true;
   }

   static {
      if(minecraft == null) {
         minecraft = Minecraft.getMinecraft();
      }

      Minecraft var10000 = minecraft;
      if(Minecraft.memoryReserve != null) {
         var10000 = minecraft;
         if(Minecraft.memoryReserve.length >= 65536) {
            var10000 = minecraft;
            Minecraft.memoryReserve['\ufff3'] = 1;
         }
      }

      blockExist = new boolean[Block.blocksList.length];
      blockTransparent = new boolean[Block.blocksList.length];

      for(int int1 = 0; int1 < blockExist.length; ++int1) {
         blockExist[int1] = Block.blocksList[int1] != null;
         blockTransparent[int1] = Block.blocksList[int1] != null && Block.blocksList[int1].getRenderType() == 0 && Block.blocksList[int1].isOpaqueCube();
      }

   }
}
