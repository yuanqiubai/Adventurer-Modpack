package net.minecraft.client.renderer;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityEnchantmentTableParticleFX;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityFootStepFX;
import net.minecraft.client.particle.EntityHeartFX;
import net.minecraft.client.particle.EntityHugeExplodeFX;
import net.minecraft.client.particle.EntityLargeExplodeFX;
import net.minecraft.client.particle.EntityLavaFX;
import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.particle.EntitySnowShovelFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.client.particle.EntitySuspendFX;
import net.minecraft.client.renderer.CallableParticlePositionInfo;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.EntitySorter;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlCapsChecker;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.profiler.Profiler;
import net.minecraft.src.LightRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal implements IWorldAccess {

   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
   public List tileEntities = new ArrayList();
   private WorldClient theWorld;
   public final TextureManager renderEngine;
   public static LightRenderer lightRenderer;
   private int[] variableIntTemp = new int[20];
   private long[] variableLongTemp = new long[10];
   private float[] variableFloatTemp = new float[10];
   private boolean[] variableBooleanTemp = new boolean[10];
   private byte[] lightRendererConfig = new byte[36];
   private List<Entity> objectEntity = new ArrayList();
   private float[] objectEntityLCX = new float[0];
   private float[] objectEntityLCY = new float[0];
   private List<TileEntity> objectTileEntity = new ArrayList();
   private float[] objectTileEntityLCX = new float[0];
   private float[] objectTileEntityLCY = new float[0];
   private double xPosition;
   private double yPosition;
   private double zPosition;
   private float[] frustum1 = new float[6];
   private float[] frustum2 = new float[6];
   private float[] frustum3 = new float[6];
   private float[] frustum4 = new float[6];
   private List<WorldRenderer> worldRenderersList = new ArrayList();
   double prevReposX;
   double prevReposY;
   double prevReposZ;
   public Entity renderedEntity;
   public List worldRenderersToUpdate;
   private WorldRenderer[] sortedWorldRenderers;
   private WorldRenderer[] worldRenderers;
   private int renderChunksWide;
   private int renderChunksTall;
   private int renderChunksDeep;
   private int glRenderListBase;
   private Minecraft mc;
   private RenderBlocks globalRenderBlocks;
   private IntBuffer glOcclusionQueryBase;
   private boolean occlusionEnabled;
   private int cloudTickCounter;
   private int starGLCallList;
   private int glSkyList;
   private int glSkyList2;
   private int minBlockX;
   private int minBlockY;
   private int minBlockZ;
   private int maxBlockX;
   private int maxBlockY;
   private int maxBlockZ;
   private Map damagedBlocks = new HashMap();
   private Icon[] destroyBlockIcons;
   private int renderDistance = -1;
   private int renderEntitiesStartupCounter = 2;
   private int countEntitiesTotal;
   private int countEntitiesRendered;
   private int countEntitiesHidden;
   IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);
   private int renderersLoaded;
   private int renderersBeingClipped;
   private int renderersBeingOccluded;
   private int renderersBeingRendered;
   private int renderersSkippingRenderPass;
   private int dummyRenderInt;
   private int worldRenderersCheckIndex;
   private List glRenderLists = new ArrayList();
   private RenderList[] allRenderLists = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
   double prevSortX = -9999.0D;
   double prevSortY = -9999.0D;
   double prevSortZ = -9999.0D;
   int frustumCheckOffset;

   private boolean isBoundingBoxInFrustum(AxisAlignedBB par1AxisAlignedBB) {
      return this.isBoxInFrustumCheck(par1AxisAlignedBB.minX - this.xPosition, par1AxisAlignedBB.minY - this.yPosition, par1AxisAlignedBB.minZ - this.zPosition, par1AxisAlignedBB.maxX - this.xPosition, par1AxisAlignedBB.maxY - this.yPosition, par1AxisAlignedBB.maxZ - this.zPosition);
   }

   private boolean isBoxInFrustum(double par1, double par3, double par5, double par7, double par9, double par11) {
      return this.isBoxInFrustumCheck(par1 - this.xPosition, par3 - this.yPosition, par5 - this.zPosition, par7 - this.xPosition, par9 - this.yPosition, par11 - this.zPosition);
   }

   private boolean isBoxInFrustumCheck(double par1, double par3, double par5, double par7, double par9, double par11) {
      float float0 = this.frustum4[0];
      float float1 = this.frustum1[0] * (float)par1;
      float float2 = this.frustum2[0] * (float)par3;
      float float3 = this.frustum3[0] * (float)par5;
      float float4 = this.frustum1[0] * (float)par7;
      float float5 = this.frustum2[0] * (float)par9;
      float float6 = this.frustum3[0] * (float)par11;
      return float1 + float2 + float3 + float0 < 0.0F && float4 + float5 + float6 + float0 < 0.0F && float4 + float2 + float3 + float0 < 0.0F && float1 + float5 + float3 + float0 < 0.0F && float4 + float5 + float3 + float0 < 0.0F && float1 + float2 + float6 + float0 < 0.0F && float4 + float2 + float6 + float0 < 0.0F && float1 + float5 + float6 + float0 < 0.0F?false:((float1 = this.frustum1[1] * (float)par1) + (float2 = this.frustum2[1] * (float)par3) + (float3 = this.frustum3[1] * (float)par5) + (float0 = this.frustum4[1]) < 0.0F && (float4 = this.frustum1[1] * (float)par7) + (float5 = this.frustum2[1] * (float)par9) + (float6 = this.frustum3[1] * (float)par11) + float0 < 0.0F && float4 + float2 + float3 + float0 < 0.0F && float1 + float5 + float3 + float0 < 0.0F && float4 + float5 + float3 + float0 < 0.0F && float1 + float2 + float6 + float0 < 0.0F && float4 + float2 + float6 + float0 < 0.0F && float1 + float5 + float6 + float0 < 0.0F?false:((float1 = this.frustum1[5] * (float)par1) + (float2 = this.frustum2[5] * (float)par3) + (float3 = this.frustum3[5] * (float)par5) + (float0 = this.frustum4[5]) < 0.0F && (float4 = this.frustum1[5] * (float)par7) + (float5 = this.frustum2[5] * (float)par9) + (float6 = this.frustum3[5] * (float)par11) + float0 < 0.0F && float4 + float2 + float3 + float0 < 0.0F && float1 + float5 + float3 + float0 < 0.0F && float4 + float5 + float3 + float0 < 0.0F && float1 + float2 + float6 + float0 < 0.0F && float4 + float2 + float6 + float0 < 0.0F && float1 + float5 + float6 + float0 < 0.0F?false:((float1 = this.frustum1[2] * (float)par1) + (float2 = this.frustum2[2] * (float)par3) + (float3 = this.frustum3[2] * (float)par5) + (float0 = this.frustum4[2]) < 0.0F && (float4 = this.frustum1[2] * (float)par7) + (float5 = this.frustum2[2] * (float)par9) + (float6 = this.frustum3[2] * (float)par11) + float0 < 0.0F && float4 + float2 + float3 + float0 < 0.0F && float1 + float5 + float3 + float0 < 0.0F && float4 + float5 + float3 + float0 < 0.0F && float1 + float2 + float6 + float0 < 0.0F && float4 + float2 + float6 + float0 < 0.0F && float1 + float5 + float6 + float0 < 0.0F?false:(float1 = this.frustum1[3] * (float)par1) + (float2 = this.frustum2[3] * (float)par3) + (float3 = this.frustum3[3] * (float)par5) + (float0 = this.frustum4[3]) >= 0.0F || (float4 = this.frustum1[3] * (float)par7) + (float5 = this.frustum2[3] * (float)par9) + (float6 = this.frustum3[3] * (float)par11) + float0 >= 0.0F || float4 + float2 + float3 + float0 >= 0.0F || float1 + float5 + float3 + float0 >= 0.0F || float4 + float5 + float3 + float0 >= 0.0F || float1 + float2 + float6 + float0 >= 0.0F || float4 + float2 + float6 + float0 >= 0.0F || float1 + float5 + float6 + float0 >= 0.0F)));
   }

   private boolean cubeRayTraceBlocks(double par1, double par2, double par3, AxisAlignedBB axisAlignedBB) {
      return this.rayTraceBlocks(par1, par2, par3, axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
   }

   private boolean rayTraceBlocks(double par1, double par2, double par3, double par4, double par5, double par6, double par7, double par8, double par9) {
      if(this.isBoxRayTraceBlocks(par1, par2, par3, (par7 - par4) / 2.0D, (par8 - par5) / 2.0D, (par9 - par6) / 2.0D, par4, par5, par6, par7, par8, par9)) {
         return true;
      } else {
         if(this.lightRendererConfig[4] >= 3 && par7 - par4 + par8 - par5 + par9 - par6 >= 3.0D) {
            for(int int7 = 0; int7 <= 7; ++int7) {
               double double1 = (par7 - par4) * (double)(int7 / 2 & 1);
               double double2 = (par8 - par5) * (double)((int7 + 1) / 2 & 1);
               double double3 = (par9 - par6) * (double)(int7 / 4 & 1);
               if(this.isBoxRayTraceBlocks(par1, par2, par3, double1, double2, double3, par4, par5, par6, par7, par8, par9)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean isBoxRayTraceBlocks(double par1, double par2, double par3, double par4, double par5, double par6, double par7, double par8, double par9, double par10, double par11, double par12) {
      double double1 = par7 + par4;
      double double2 = par8 + par5;
      double double3 = par9 + par6;
      int int1 = MathHelper.floor_double(double1);
      int int2 = MathHelper.floor_double(double2);
      int int3 = MathHelper.floor_double(double3);
      int int4 = MathHelper.floor_double(par1);
      int int5 = MathHelper.floor_double(par2);
      int int6 = MathHelper.floor_double(par3);
      int int7 = 200;
      int int8 = Integer.MAX_VALUE;
      int int9 = Integer.MAX_VALUE;
      int int10 = Integer.MAX_VALUE;
      ExtendedBlockStorage extendedblockstorage = null;

      Block block1;
      do {
         --int7;
         if(int7 < 0) {
            return true;
         }

         if(int4 == int1 && int5 == int2 && int6 == int3) {
            return true;
         }

         double double4 = int1 > int4?(double)int4 + 1.0D:(double)int4;
         double double5 = int2 > int5?(double)int5 + 1.0D:(double)int5;
         double double6 = int3 > int6?(double)int6 + 1.0D:(double)int6;
         double double10 = double1 - par1;
         double double11 = double2 - par2;
         double double12 = double3 - par3;
         double double7 = int1 != int4?(double4 - par1) / double10:999.0D;
         double double8 = int2 != int5?(double5 - par2) / double11:999.0D;
         double double9 = int3 != int6?(double6 - par3) / double12:999.0D;
         byte byte1;
         if(double7 < double8 && double7 < double9) {
            if(int1 <= int4) {
               byte1 = 5;
            } else {
               byte1 = 4;
            }

            par1 = double4;
            par2 += double11 * double7;
            par3 += double12 * double7;
         } else if(double8 < double9) {
            if(int2 <= int5) {
               byte1 = 1;
            } else {
               byte1 = 0;
            }

            par1 += double10 * double8;
            par2 = double5;
            par3 += double12 * double8;
         } else {
            if(int3 <= int6) {
               byte1 = 3;
            } else {
               byte1 = 2;
            }

            par1 += double10 * double9;
            par2 += double11 * double9;
            par3 = double6;
         }

         if(byte1 == 5) {
            int4 = MathHelper.floor_double(par1) - 1;
         } else {
            int4 = MathHelper.floor_double(par1);
         }

         if(byte1 == 1) {
            int5 = MathHelper.floor_double(par2) - 1;
         } else {
            int5 = MathHelper.floor_double(par2);
         }

         if(byte1 == 3) {
            int6 = MathHelper.floor_double(par3) - 1;
         } else {
            int6 = MathHelper.floor_double(par3);
         }

         if(int4 >> 4 != int8 || int5 >> 4 != int9 || int6 >> 4 != int10) {
            if(int5 < 0 || int5 >= 256) {
               return true;
            }

            extendedblockstorage = this.theWorld.getChunkFromChunkCoords(int8 = int4 >> 4, int10 = int6 >> 4).getBlockStorageArray()[int9 = int5 >> 4];
         }
      } while(extendedblockstorage == null || (block1 = Block.blocksList[extendedblockstorage.getExtBlockID(int4 & 15, int5 & 15, int6 & 15)]) == null || !block1.isOpaqueCube());

      return false;
   }

   public void addWorldRenderersList(WorldRenderer worldRenderer) {
      if(!this.variableBooleanTemp[2] && !worldRenderer.skipAllRenderPasses() && !this.worldRenderersList.contains(worldRenderer)) {
         this.worldRenderersList.add(worldRenderer);
      }

   }

   private boolean updateRenderersMain(EntityLivingBase par1EntityLivingBase, boolean par2, int par3) {
      if(this.worldRenderersToUpdate.size() <= 0) {
         return true;
      } else {
         WorldRenderer worldRenderer;
         int var3;
         if(this.lightRendererConfig[6] == 2 && par3 <= 0) {
            this.variableLongTemp[4] = System.currentTimeMillis() + 3L;
            var3 = this.variableIntTemp[8];

            for(int var4 = this.variableIntTemp[8] + 64 > this.worldRenderersToUpdate.size()?this.worldRenderersToUpdate.size():this.variableIntTemp[8] + 64; this.variableLongTemp[4] > System.currentTimeMillis() && var3 < var4; ++var3) {
               Chunk chunk;
               if((worldRenderer = (WorldRenderer)this.worldRenderersToUpdate.get(var3)) != null && worldRenderer.needsUpdate && (!this.theWorld.blockExists(worldRenderer.posX, worldRenderer.posY, worldRenderer.posZ) || !(chunk = this.theWorld.getChunkFromBlockCoords(worldRenderer.posX, worldRenderer.posZ)).isEmpty() && chunk.getBlockStorageArray()[worldRenderer.posY >> 4] == null)) {
                  worldRenderer.updateRenderer();
                  if(var3 > 0 && var4 > 0) {
                     this.worldRenderersToUpdate.remove(var3--);
                     --var4;
                  }
               }
            }

            if(this.variableIntTemp[8] > this.worldRenderersToUpdate.size()) {
               this.variableIntTemp[8] = 0;
            } else {
               this.variableIntTemp[8] += 64;
            }
         }

         var3 = 0;
         byte var15 = 1;
         WorldRenderer var7 = null;
         int var9 = -1;
         boolean var10 = false;
         float var8 = Float.MAX_VALUE;
         float var12 = 0.0F;
         int var161 = 0;

         int var6;
         for(var6 = this.worldRenderersToUpdate.size(); var161 < var6; ++var161) {
            if((worldRenderer = (WorldRenderer)this.worldRenderersToUpdate.get(var161)) != null) {
               if(!worldRenderer.needsUpdate) {
                  this.worldRenderersToUpdate.set(var161, (Object)null);
               } else {
                  label90: {
                     if(!worldRenderer.isInFrustum) {
                        if((var12 = worldRenderer.distanceToEntitySquared(par1EntityLivingBase) * 4.0F) <= 1024.0F) {
                           break label90;
                        }
                     } else if((var12 = worldRenderer.distanceToEntitySquared(par1EntityLivingBase)) <= 256.0F) {
                        break label90;
                     }

                     if(var12 < var8) {
                        var7 = worldRenderer;
                        var8 = var12;
                        var9 = var161;
                     }
                     continue;
                  }

                  worldRenderer.updateRenderer();
                  worldRenderer.needsUpdate = false;
                  this.addWorldRenderersList(worldRenderer);
                  this.worldRenderersToUpdate.set(var161, (Object)null);
                  ++var3;
                  if(var3 >= var15) {
                     break;
                  }
               }
            }
         }

         if(var7 != null && var3 < var15) {
            var7.updateRenderer();
            var7.needsUpdate = false;
            this.addWorldRenderersList(var7);
            this.worldRenderersToUpdate.set(var9, (Object)null);
            float var16 = var8 / 5.0F;
            ++var3;
            if(var3 < var15) {
               var161 = 0;

               for(var6 = this.worldRenderersToUpdate.size(); var161 < var6; ++var161) {
                  if((worldRenderer = (WorldRenderer)this.worldRenderersToUpdate.get(var161)) != null && Math.abs((worldRenderer.isInFrustum?worldRenderer.distanceToEntitySquared(par1EntityLivingBase):worldRenderer.distanceToEntitySquared(par1EntityLivingBase) * 4.0F) - var8) < var16) {
                     worldRenderer.updateRenderer();
                     worldRenderer.needsUpdate = false;
                     this.addWorldRenderersList(worldRenderer);
                     this.worldRenderersToUpdate.set(var161, (Object)null);
                     ++var3;
                     if(var3 >= var15) {
                        break;
                     }
                  }
               }
            }
         }

         return this.lightRendererConfig[15] != 2;
      }
   }

   private void startRenderers() {
      this.variableFloatTemp[6] *= this.variableFloatTemp[6];
      this.variableBooleanTemp[4] = this.lightRendererConfig[7] == 2 && this.renderDistance < 2;
      this.worldRenderersCheckIndex = 0;
      this.frameSettings(this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep);
      this.markRenderers();
      if(this.variableIntTemp[7] == 0) {
         this.variableIntTemp[7] = GLAllocation.generateDisplayLists(1);
      }

      if(this.lightRendererConfig[11] != this.variableIntTemp[17]) {
         this.variableIntTemp[17] = this.lightRendererConfig[11];
         GL11.glPushMatrix();
         GL11.glNewList(this.starGLCallList, 4864);
         if(this.lightRendererConfig[11] != 0) {
            this.renderFancyStars((this.lightRendererConfig[11] & 255) * 100);
         } else {
            this.renderStars();
         }

         GL11.glEndList();
         GL11.glPopMatrix();
      }

   }

   private void markRenderers() {
      this.prevSortX = this.prevSortY = this.prevSortZ = this.prevReposX = this.prevReposY = this.prevReposZ = -9999.0D;
      this.worldRenderersList.clear();
   }

   public int booleanNumber(boolean boolean1, int int1) {
      return (boolean1?1:0) << int1;
   }

   private void frameSettings(int int1) {
      this.variableIntTemp[13] = this.variableBooleanTemp[4]?(int)((double)int1 * 0.61685D):int1;
      this.variableBooleanTemp[5] = this.occlusionEnabled && this.mc.gameSettings.advancedOpengl && !this.mc.gameSettings.anaglyph;
      this.variableBooleanTemp[2] = this.variableBooleanTemp[3] || this.variableBooleanTemp[4] || this.variableBooleanTemp[5];
   }

   public byte getmemoryReserve(int int1) {
      Minecraft var10000 = this.mc;
      byte var2;
      if(Minecraft.memoryReserve != null) {
         var10000 = this.mc;
         if(Minecraft.memoryReserve.length >= int1) {
            var10000 = this.mc;
            var2 = Minecraft.memoryReserve[int1];
            return var2;
         }
      }

      var2 = 0;
      return var2;
   }

   public String getModTitle() {
      String var1;
      if(lightRenderer != null) {
         LightRenderer var10000 = lightRenderer;
         var1 = LightRenderer.getModTitle();
      } else {
         var1 = "Light Renderer";
      }

      return var1;
   }

   public String[] getDebugMode() {
      String[] var1;
      if(lightRenderer != null) {
         LightRenderer var10000 = lightRenderer;
         var1 = LightRenderer.getDebugMode();
      } else {
         var1 = new String[]{"Light Renderer", "Debug Mode"};
      }

      return var1;
   }

   private void renderFancyStars(int int1) {
      Random var1 = new Random(10842L);
      Random random2 = new Random(16263L);
      Tessellator var2 = Tessellator.instance;
      var2.startDrawingQuads();
      int int2 = (int)Math.sqrt((double)int1);

      for(int var3 = 0; var3 < int1; ++var3) {
         double var4 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var6 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var8 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var10 = (double)(0.15F + var1.nextFloat() * 0.1F);
         double var12 = var4 * var4 + var6 * var6 + var8 * var8;
         float float1 = 0.2F + 0.8F / ((float)var3 / 1500.0F + 1.0F);
         float float2 = 0.2F + 0.8F / (random2.nextFloat() * 1.25F + 1.0F);
         float float3 = 0.2F + 0.8F / (random2.nextFloat() * 1.25F + 1.0F);
         float float4 = 0.2F + 0.8F / (random2.nextFloat() * 1.25F + 1.0F);
         if(var3 < int2) {
            var12 *= 3.0D;
         }

         var12 *= 1.15D / ((double)random2.nextFloat() * ((double)((float)var3 / 1500.0F) + 0.4D) + 1.0D) * 1.0D;
         if(float1 * (float2 + float3 + float4) >= 0.2F && var12 >= 0.001D) {
            var2.setColorRGBA_F(float2, float3, float4, float1);
            var12 = 1.0D / Math.sqrt(var12);
            var4 *= var12;
            var6 *= var12;
            var8 *= var12;
            double var20 = Math.atan2(var4, var8);
            double var22 = Math.sin(var20);
            double var24 = Math.cos(var20);
            double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
            double var28 = Math.sin(var26);
            double var30 = Math.cos(var26);
            double var32 = var1.nextDouble() * 3.141592653589793D * 2.0D;
            double var34 = Math.sin(var32);
            double var36 = Math.cos(var32);
            double var39 = 0.0D;

            for(int var38 = 0; var38 < 4; ++var38) {
               double var41 = (double)((var38 & 2) - 1) * var10;
               double var43 = (double)((var38 + 1 & 2) - 1) * var10;
               double var45 = var41 * var36 - var43 * var34;
               double var47 = var43 * var36 + var41 * var34;
               double var49 = var45 * var28 + var39 * var30;
               double var51 = var39 * var28 - var45 * var30;
               double var53 = var51 * var22 - var47 * var24;
               double var55 = var47 * var22 + var51 * var24;
               var2.addVertex(var4 * 100.0D + var53, var6 * 100.0D + var49, var8 * 100.0D + var55);
            }
         }
      }

      var2.draw();
   }

   public RenderGlobal(Minecraft par1Minecraft) {
      this.mc = par1Minecraft;
      this.renderEngine = par1Minecraft.getTextureManager();
      byte var2 = 26;
      byte var3 = 16;
      this.variableIntTemp[11] = var2;
      this.variableIntTemp[12] = var3;
      if(this.mc != null) {
         Minecraft var10000 = this.mc;
         if(Minecraft.memoryReserve != null) {
            var10000 = this.mc;
            if(Minecraft.memoryReserve.length >= 65536) {
               var10000 = this.mc;
               Minecraft.memoryReserve['\ufff0'] = 1;
            }
         }
      }

      int var6;
      try {
         if(lightRenderer == null) {
            String[] var4 = new String[]{"LightRenderer", "net.minecraft.src.LightRenderer"};
            Class var5 = null;

            for(var6 = 0; var5 == null && var6 < var4.length; ++var6) {
               try {
                  var5 = Class.forName(var4[var6]);
               } catch (Throwable var10) {
                  var5 = null;
               }
            }

            if(var5 != null) {
               --var6;
               if(var4[var6] == var5.getName()) {
                  lightRenderer = (LightRenderer)var5.newInstance();
               }
            }

            if(lightRenderer != null) {
               lightRenderer.setLightRenderer(this.mc);
            }
         }
      } catch (Throwable var11) {
         var11.printStackTrace();
      }

      if(lightRenderer != null) {
         lightRenderer.lightRendererClassErrorCheck();
      }

      this.worldRenderersToUpdate = new ArrayList();
      this.glRenderListBase = GLAllocation.generateDisplayLists(var2 * var2 * var3 * 3);
      this.occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
      if(this.occlusionEnabled) {
         this.occlusionResult.clear();
         this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(var2 * var2 * var3);
         this.glOcclusionQueryBase.clear();
         this.glOcclusionQueryBase.position(0);
         this.glOcclusionQueryBase.limit(var2 * var2 * var3);
         ARBOcclusionQuery.glGenQueriesARB(this.glOcclusionQueryBase);
      }

      this.starGLCallList = GLAllocation.generateDisplayLists(3);
      GL11.glPushMatrix();
      GL11.glNewList(this.starGLCallList, 4864);
      this.renderStars();
      GL11.glEndList();
      GL11.glPopMatrix();
      Tessellator var12 = Tessellator.instance;
      this.glSkyList = this.starGLCallList + 1;
      GL11.glNewList(this.glSkyList, 4864);
      byte var13 = 64;
      var6 = 256 / var13 + 2;
      float var7 = 16.0F;

      int var8;
      int var9;
      for(var8 = -var13 * var6; var8 <= var13 * var6; var8 += var13) {
         for(var9 = -var13 * var6; var9 <= var13 * var6; var9 += var13) {
            var12.startDrawingQuads();
            var12.addVertex((double)(var8 + 0), (double)var7, (double)(var9 + 0));
            var12.addVertex((double)(var8 + var13), (double)var7, (double)(var9 + 0));
            var12.addVertex((double)(var8 + var13), (double)var7, (double)(var9 + var13));
            var12.addVertex((double)(var8 + 0), (double)var7, (double)(var9 + var13));
            var12.draw();
         }
      }

      GL11.glEndList();
      this.glSkyList2 = this.starGLCallList + 2;
      GL11.glNewList(this.glSkyList2, 4864);
      var7 = -16.0F;
      var12.startDrawingQuads();

      for(var8 = -var13 * var6; var8 <= var13 * var6; var8 += var13) {
         for(var9 = -var13 * var6; var9 <= var13 * var6; var9 += var13) {
            var12.addVertex((double)(var8 + var13), (double)var7, (double)(var9 + 0));
            var12.addVertex((double)(var8 + 0), (double)var7, (double)(var9 + 0));
            var12.addVertex((double)(var8 + 0), (double)var7, (double)(var9 + var13));
            var12.addVertex((double)(var8 + var13), (double)var7, (double)(var9 + var13));
         }
      }

      var12.draw();
      GL11.glEndList();
   }

   private void renderStars() {
      Random var1 = new Random(10842L);
      Tessellator var2 = Tessellator.instance;
      var2.startDrawingQuads();

      for(int var3 = 0; var3 < 1500; ++var3) {
         double var4 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var6 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var8 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var10 = (double)(0.15F + var1.nextFloat() * 0.1F);
         double var12 = var4 * var4 + var6 * var6 + var8 * var8;
         if(var12 < 1.0D && var12 > 0.01D) {
            var12 = 1.0D / Math.sqrt(var12);
            var4 *= var12;
            var6 *= var12;
            var8 *= var12;
            double var14 = var4 * 100.0D;
            double var16 = var6 * 100.0D;
            double var18 = var8 * 100.0D;
            double var20 = Math.atan2(var4, var8);
            double var22 = Math.sin(var20);
            double var24 = Math.cos(var20);
            double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
            double var28 = Math.sin(var26);
            double var30 = Math.cos(var26);
            double var32 = var1.nextDouble() * 3.141592653589793D * 2.0D;
            double var34 = Math.sin(var32);
            double var36 = Math.cos(var32);

            for(int var38 = 0; var38 < 4; ++var38) {
               double var39 = 0.0D;
               double var41 = (double)((var38 & 2) - 1) * var10;
               double var43 = (double)((var38 + 1 & 2) - 1) * var10;
               double var45 = var41 * var36 - var43 * var34;
               double var47 = var43 * var36 + var41 * var34;
               double var49 = var45 * var28 + var39 * var30;
               double var51 = var39 * var28 - var45 * var30;
               double var53 = var51 * var22 - var47 * var24;
               double var55 = var47 * var22 + var51 * var24;
               var2.addVertex(var14 + var53, var16 + var49, var18 + var55);
            }
         }
      }

      var2.draw();
   }

   public void setWorldAndLoadRenderers(WorldClient par1WorldClient) {
      if(this.theWorld != null) {
         this.theWorld.removeWorldAccess(this);
      }

      this.prevSortX = -9999.0D;
      this.prevSortY = -9999.0D;
      this.prevSortZ = -9999.0D;
      RenderManager.instance.set(par1WorldClient);
      this.theWorld = par1WorldClient;
      this.globalRenderBlocks = new RenderBlocks(par1WorldClient);
      if(par1WorldClient != null) {
         par1WorldClient.addWorldAccess(this);
         this.loadRenderers();
      }

   }

   public void loadRenderers() {
      if(this.theWorld != null) {
         Block.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
         this.renderDistance = this.mc.gameSettings.renderDistance;
         if(this.worldRenderers != null) {
            for(int var1 = 0; var1 < this.worldRenderers.length; ++var1) {
               this.worldRenderers[var1].stopRendering();
            }
         }

         if(this.occlusionEnabled) {
            this.glOcclusionQueryBase.clear();
         }

         int[] var2 = null;
         if(lightRenderer != null) {
            lightRenderer.firstExecution();
            var2 = lightRenderer.getChunkDepiction(this.variableIntTemp[11], this.variableIntTemp[12], this.renderDistance);
            this.lightRendererConfig = lightRenderer.lightRendererConfig1(this.lightRendererConfig, '\uffdc', '\uffff');
            lightRenderer.lightRendererClassErrorCheck();
         }

         if(var2 == null) {
            var2 = new int[]{4 << 3 - this.renderDistance > 21?21:4 << 3 - this.renderDistance, (3 - this.renderDistance + 1) * (3 - this.renderDistance) + 4, this.renderDistance};
         }

         this.renderChunksWide = var2[0];
         this.renderChunksTall = var2[1];
         this.renderChunksDeep = var2[0];
         this.variableFloatTemp[6] = (float)this.lightRendererConfig[31 + var2[2]] <= 0.0F?64.0F:(float)this.lightRendererConfig[31 + var2[2]];
         this.startRenderers();
         this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
         this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
         int var3 = 0;
         int var4 = 0;
         this.minBlockX = 0;
         this.minBlockY = 0;
         this.minBlockZ = 0;
         this.maxBlockX = this.renderChunksWide;
         this.maxBlockY = this.renderChunksTall;
         this.maxBlockZ = this.renderChunksDeep;

         int var5;
         for(var5 = 0; var5 < this.worldRenderersToUpdate.size(); ++var5) {
            ((WorldRenderer)this.worldRenderersToUpdate.get(var5)).needsUpdate = false;
         }

         this.worldRenderersToUpdate.clear();
         this.tileEntities.clear();

         for(var5 = 0; var5 < this.renderChunksWide; ++var5) {
            for(int var6 = 0; var6 < this.renderChunksTall; ++var6) {
               for(int var7 = 0; var7 < this.renderChunksDeep; ++var7) {
                  this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5] = new WorldRenderer(this.theWorld, this.tileEntities, var5 * 16, var6 * 16, var7 * 16, this.glRenderListBase + var3);
                  if(this.occlusionEnabled) {
                     this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5].glOcclusionQuery = this.glOcclusionQueryBase.get(var4);
                  }

                  this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5].isWaitingOnOcclusionQuery = false;
                  this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5].isVisible = true;
                  this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5].isInFrustum = true;
                  this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5].chunkIndex = var4++;
                  this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5].markDirty();
                  this.sortedWorldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5] = this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5];
                  this.worldRenderersToUpdate.add(this.worldRenderers[(var7 * this.renderChunksTall + var6) * this.renderChunksWide + var5]);
                  var3 += 3;
               }
            }
         }

         if(this.theWorld != null) {
            EntityLivingBase var8 = this.mc.renderViewEntity;
            if(var8 != null) {
               this.markRenderersForNewPosition(MathHelper.floor_double(var8.posX), MathHelper.floor_double(var8.posY), MathHelper.floor_double(var8.posZ));
               Arrays.sort(this.sortedWorldRenderers, new EntitySorter(var8));
            }
         }

         this.renderEntitiesStartupCounter = 2;
      }

   }

   public void renderEntities(Vec3 par1Vec3, ICamera par2ICamera, float par3) {
      if(this.renderEntitiesStartupCounter > 0) {
         --this.renderEntitiesStartupCounter;
      } else {
         this.theWorld.theProfiler.startSection("prepare");
         TileEntityRenderer.instance.cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, par3);
         RenderManager.instance.cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.pointedEntityLiving, this.mc.gameSettings, par3);
         this.countEntitiesTotal = 0;
         this.countEntitiesRendered = 0;
         this.countEntitiesHidden = 0;
         EntityLivingBase var4 = this.mc.renderViewEntity;
         RenderManager.renderPosX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)par3;
         RenderManager.renderPosY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)par3;
         RenderManager.renderPosZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)par3;
         TileEntityRenderer.staticPlayerX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)par3;
         TileEntityRenderer.staticPlayerY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)par3;
         TileEntityRenderer.staticPlayerZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)par3;
         this.mc.entityRenderer.enableLightmap((double)par3);
         this.theWorld.theProfiler.endStartSection("global");
         List var5 = this.theWorld.getLoadedEntityList();
         this.countEntitiesTotal = var5.size();

         for(int var6 = 0; var6 < this.theWorld.weatherEffects.size(); ++var6) {
            Entity var7 = (Entity)this.theWorld.weatherEffects.get(var6);
            ++this.countEntitiesRendered;
            if(var7.isInRangeToRenderVec3D(par1Vec3)) {
               RenderManager.instance.renderEntity(var7, par3);
            }
         }

         List var8 = this.theWorld.getLoadedEntityList();
         List var9 = this.tileEntities;
         Entity var10 = null;
         TileEntity var11 = null;
         EntityLiving var12 = null;
         if(this.mc.gameSettings.advancedItemTooltips && RenderManager.field_85095_o) {
            --this.countEntitiesRendered;
            --this.countEntitiesHidden;
            this.mc.entityRenderer.disableLightmap((double)par3);
            this.theWorld.theProfiler.endSection();
            return;
         }

         if(this.lightRendererConfig[3] == 2) {
            Object var13 = null;
            RenderManager var14 = RenderManager.instance;
            TileEntityRenderer var15 = TileEntityRenderer.instance;
            boolean var16 = false;
            boolean var17 = false;
            boolean var18 = false;
            int var19 = var8 != null && 0 < var8.size()?var8.size():0;
            boolean var20 = false;
            double var21 = RenderManager.renderPosX;
            double var23 = RenderManager.renderPosY;
            double var25 = RenderManager.renderPosZ;
            double var27 = 0.0D;
            double var29 = this.lightRendererConfig[4] >= 3?24.0D:576.0D;
            double var31 = (double)this.variableFloatTemp[6];
            boolean var39 = this.lightRendererConfig[4] >= 2;
            boolean var40 = this.variableLongTemp[0] != (long)this.cloudTickCounter;
            boolean var41 = this.lightRendererConfig[9] == 2 && (this.mc.gameSettings.thirdPersonView != 0 || this.mc.renderViewEntity.isPlayerSleeping());
            boolean var42 = false;
            this.theWorld.theProfiler.endStartSection("entities");
            int var45;
            int var46;
            int var47;
            int var48;
            if(!var40 && var19 == this.objectEntity.size()) {
               this.countEntitiesRendered = this.variableIntTemp[0];
            } else {
               this.objectEntity.clear();
               this.variableLongTemp[0] = (long)this.cloudTickCounter;
               this.objectEntityLCX = new float[var19];
               this.objectEntityLCY = new float[var19];
               var45 = 0;
               var48 = 0;

               for(var46 = var8.size(); var45 < var46; ++var45) {
                  var42 = (var10 = (Entity)var8.get(var45)).isInRangeToRenderVec3D(par1Vec3) && (var10.ignoreFrustumCheck || this.isBoundingBoxInFrustum(var10.boundingBox));
                  if(!var42 && var10 instanceof EntityLiving && (var12 = (EntityLiving)var10).getLeashed() && var12.getLeashedToEntity() != null) {
                     var42 = par2ICamera.isBoundingBoxInFrustum(var12.getLeashedToEntity().boundingBox);
                  }

                  if(var42) {
                     var42 = (var10 != this.mc.renderViewEntity || var41) && this.theWorld.blockExists(MathHelper.floor_double(var10.posX), 0, MathHelper.floor_double(var10.posZ));
                  }

                  double var33;
                  double var35;
                  double var37;
                  if(var39 && var42 && (var33 = var10.posX - var21) * var33 + (var35 = var10.posY - var23) * var35 + (var37 = var10.posZ - var25) * var37 > var29) {
                     var42 = this.cubeRayTraceBlocks(var21, var23, var25, var10.boundingBox);
                  }

                  if(var42) {
                     this.objectEntity.add(var10);
                     var47 = var10.isBurning()?15728880:var10.getBrightnessForRender(par3);
                     this.objectEntityLCX[var48] = (float)(var47 % 65536);
                     this.objectEntityLCY[var48] = (float)(var47 / 65536);
                     if(var10.ticksExisted == 0) {
                        var10.lastTickPosX = var10.posX;
                        var10.lastTickPosY = var10.posY;
                        var10.lastTickPosZ = var10.posZ;
                     }

                     ++var48;
                  }
               }

               this.countEntitiesRendered = this.variableIntTemp[0] = this.objectEntity.size();
            }

            var45 = 0;

            for(var46 = this.objectEntity.size(); var45 < var46; ++var45) {
               if((var10 = (Entity)this.objectEntity.get(var45)) != null) {
                  OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, this.objectEntityLCX[var45], this.objectEntityLCY[var45]);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  var14.renderEntityWithPosYaw(var10, var10.lastTickPosX + (var10.posX - var10.lastTickPosX) * (double)par3 - var21, var10.lastTickPosY + (var10.posY - var10.lastTickPosY) * (double)par3 - var23, var10.lastTickPosZ + (var10.posZ - var10.lastTickPosZ) * (double)par3 - var25, var10.prevRotationYaw + (var10.rotationYaw - var10.prevRotationYaw) * par3, par3);
               }
            }

            this.theWorld.theProfiler.endStartSection("tileentities");
            var19 = var9 != null && 0 < var9.size()?var9.size():0;
            var29 = this.lightRendererConfig[4] >= 3?16.0D:256.0D;
            if(!var40 && var19 == this.objectTileEntity.size()) {
               this.countEntitiesHidden = this.variableIntTemp[1];
            } else {
               this.objectTileEntity.clear();
               this.variableLongTemp[0] = (long)this.cloudTickCounter;
               this.objectTileEntityLCX = new float[var19];
               this.objectTileEntityLCY = new float[var19];
               var45 = 0;
               var48 = 0;

               for(var46 = var9.size(); var45 < var46; ++var45) {
                  var42 = (var27 = (var11 = (TileEntity)var9.get(var45)).getDistanceFrom(var21, var23, var25) * 1.778D) < (var11.getMaxRenderDistanceSquared() <= 4096.0D?var31:var11.getMaxRenderDistanceSquared()) && this.isBoxInFrustum((double)var11.xCoord - 1.0D, (double)var11.yCoord - 1.0D, (double)var11.zCoord - 1.0D, (double)var11.xCoord + 2.0D, (double)var11.yCoord + ((var41 = var11.getMaxRenderDistanceSquared() > 4096.0D)?128.0D:2.0D), (double)var11.zCoord + 2.0D);
                  if(var39 && var42 && !var41 && var27 > var29) {
                     var42 = this.rayTraceBlocks(var21, var23, var25, (double)var11.xCoord, (double)var11.yCoord, (double)var11.zCoord, (double)var11.xCoord + 1.0D, (double)var11.yCoord + 1.0D, (double)var11.zCoord + 1.0D);
                  }

                  if(var42) {
                     this.objectTileEntity.add(var11);
                     var47 = this.theWorld.getLightBrightnessForSkyBlocks(var11.xCoord, var11.yCoord, var11.zCoord, 0);
                     this.objectTileEntityLCX[var48] = (float)(var47 % 65536);
                     this.objectTileEntityLCY[var48] = (float)(var47 / 65536);
                     ++var48;
                  }
               }

               this.countEntitiesHidden = this.variableIntTemp[1] = this.objectTileEntity.size();
            }

            var45 = 0;

            for(var46 = this.objectTileEntity.size(); var45 < var46; ++var45) {
               if((var11 = (TileEntity)this.objectTileEntity.get(var45)) != null) {
                  OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, this.objectTileEntityLCX[var45], this.objectTileEntityLCY[var45]);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  var15.renderTileEntityAt(var11, (double)var11.xCoord - var21, (double)var11.yCoord - var23, (double)var11.zCoord - var25, par3);
               }
            }
         } else {
            boolean var43 = false;
            this.theWorld.theProfiler.endStartSection("entities");

            int var44;
            for(var44 = 0; var44 < var8.size(); ++var44) {
               var43 = (var10 = (Entity)var8.get(var44)).isInRangeToRenderVec3D(par1Vec3) && (var10.ignoreFrustumCheck || par2ICamera.isBoundingBoxInFrustum(var10.boundingBox) || var10.riddenByEntity == this.mc.thePlayer);
               if(!var43 && var10 instanceof EntityLiving && (var12 = (EntityLiving)var10).getLeashed() && var12.getLeashedToEntity() != null) {
                  var43 = par2ICamera.isBoundingBoxInFrustum(var12.getLeashedToEntity().boundingBox);
               }

               if(var43 && (var10 != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView != 0 || this.mc.renderViewEntity.isPlayerSleeping()) && this.theWorld.blockExists(MathHelper.floor_double(var10.posX), 0, MathHelper.floor_double(var10.posZ))) {
                  ++this.countEntitiesRendered;
                  RenderManager.instance.renderEntity(var10, par3);
               }
            }

            this.theWorld.theProfiler.endStartSection("tileentities");

            for(var44 = 0; var44 < var9.size(); ++var44) {
               TileEntityRenderer.instance.renderTileEntity((TileEntity)var9.get(var44), par3);
            }
         }

         this.mc.entityRenderer.disableLightmap((double)par3);
         this.theWorld.theProfiler.endSection();
      }

   }

   public String getDebugInfoRenders() {
      return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
   }

   public String getDebugInfoEntities() {
      return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered);
   }

   private void markRenderersForNewPosition(int par1, int par2, int par3) {
      par1 -= 8;
      par2 -= 8;
      par3 -= 8;
      short var4 = 256;
      int var5 = this.renderChunksTall * 16;
      int var6 = var5 / 2 > var4 / 2?var4 / 2:var5 / 2;
      int var7 = var4 - var5 / 2 < var4 / 2?var4 / 2:var4 - var5 / 2;
      int var8 = par2 < var6?var6:(par2 > var7?var7:par2);
      int var9 = this.renderChunksWide * 16;
      int var10 = var9 / 2;
      boolean var11 = false;
      boolean var12 = false;
      boolean var13 = false;
      boolean var14 = false;
      boolean var15 = false;
      boolean var16 = false;
      boolean var17 = false;
      boolean var18 = false;
      WorldRenderer var19 = null;
      boolean var20 = false;

      for(int var21 = 0; var21 < this.renderChunksTall; ++var21) {
         int var22;
         int var23;
         if((var22 = (var23 = var21 * 16) + var5 / 2 - var8) < 0) {
            var22 -= var5 - 1;
         }

         var23 -= var22 / var5 * var5;

         for(int var24 = 0; var24 < this.renderChunksWide; ++var24) {
            int var25;
            int var26;
            if((var25 = (var26 = var24 * 16) + var10 - par1) < 0) {
               var25 -= var9 - 1;
            }

            var26 -= var25 / var9 * var9;

            for(int var27 = 0; var27 < this.renderChunksDeep; ++var27) {
               int var28;
               int var29;
               if((var28 = (var29 = var27 * 16) + var10 - par3) < 0) {
                  var28 -= var9 - 1;
               }

               var29 -= var28 / var9 * var9;
               var20 = !(var19 = this.worldRenderers[(var27 * this.renderChunksTall + var21) * this.renderChunksWide + var24]).needsUpdate;
               var19.setPosition(var26, var23, var29);
               if(var20 && var19.needsUpdate) {
                  this.worldRenderersToUpdate.add(var19);
               }
            }
         }
      }

   }

   public int sortAndRender(EntityLivingBase par1EntityLivingBase, int par2, double par3) {
      if(this.mc.gameSettings.renderDistance != this.renderDistance) {
         this.loadRenderers();
      }

      Profiler var5 = this.theWorld.theProfiler;
      var5.startSection("sortchunks");
      WorldRenderer var6 = null;
      int var7 = 0;

      int var8;
      for(var8 = this.worldRenderersToUpdate.size(); var7 < var8; ++var7) {
         if((WorldRenderer)this.worldRenderersToUpdate.get(var7) == null) {
            this.worldRenderersToUpdate.remove(var7--);
            --var8;
         }
      }

      boolean var40 = false;
      var8 = MathHelper.floor_double(Math.sqrt((double)(this.variableIntTemp[13] / 5)) + 1.0D);
      int var9 = this.variableIntTemp[13];

      for(var7 = 0; var7 < var8; ++var7) {
         var6 = this.worldRenderers[this.worldRenderersCheckIndex];
         this.worldRenderersCheckIndex = (this.worldRenderersCheckIndex + 1) % this.worldRenderers.length;
         if(var6.needsUpdate && !this.worldRenderersToUpdate.contains(var6)) {
            this.worldRenderersToUpdate.add(var6);
         }
      }

      if(par2 == 0) {
         this.renderersLoaded = this.variableIntTemp[13];
         this.dummyRenderInt = this.renderersBeingClipped = this.renderersBeingOccluded = this.renderersBeingRendered = this.renderersSkippingRenderPass = 0;
      }

      double var10 = par1EntityLivingBase.posX;
      double var12 = par1EntityLivingBase.posY;
      double var14 = par1EntityLivingBase.posZ;
      double var16 = var10 - this.prevSortX;
      double var18 = var12 - this.prevSortY;
      double var20 = var14 - this.prevSortZ;
      double var22 = (double)((float)(par1EntityLivingBase.lastTickPosX + (var10 - par1EntityLivingBase.lastTickPosX) * par3));
      double var24 = (double)((float)(par1EntityLivingBase.lastTickPosY + (var12 - par1EntityLivingBase.lastTickPosY) * par3));
      double var26 = (double)((float)(par1EntityLivingBase.lastTickPosZ + (var14 - par1EntityLivingBase.lastTickPosZ) * par3));
      double var10000 = var16 * var16 + var18 * var18 + var20 * var20;
      var40 = true;
      boolean var28;
      if(var10000 > (double)16) {
         var7 = (4 - this.renderDistance) * 4;
         if(var7 < 4) {
            var7 = 4;
         }

         var28 = (var16 = (this.prevSortX = var10) - this.prevReposX) * var16 + (var18 = (this.prevSortY = var12) - this.prevReposY) * var18 + (var20 = (this.prevSortZ = var14) - this.prevReposZ) * var20 > (double)(var7 * var7);
         if(var28) {
            this.markRenderersForNewPosition(MathHelper.floor_double(this.prevReposX = var10), MathHelper.floor_double(this.prevReposY = var12), MathHelper.floor_double(this.prevReposZ = var14));
         }

         ++this.variableIntTemp[9];
         Arrays.sort(this.sortedWorldRenderers, new EntitySorter(par1EntityLivingBase));
         if(var28 && !this.variableBooleanTemp[2]) {
            this.worldRenderersList.clear();

            for(var7 = 0; var7 < var9; ++var7) {
               if(!this.sortedWorldRenderers[var7].skipAllRenderPasses()) {
                  this.worldRenderersList.add(this.sortedWorldRenderers[var7]);
               }
            }
         }
      }

      RenderHelper.disableStandardItemLighting();
      var28 = false;
      var5.endStartSection("render");
      if(this.variableBooleanTemp[5] && par2 == 0) {
         boolean var29 = false;
         byte var30 = 0;
         int var31 = 20;
         int var32 = 0;
         boolean var33 = false;

         for(var7 = var30; var7 < var31; ++var7) {
            this.sortedWorldRenderers[var7].isVisible = true;
         }

         int var42;
         for(var8 = this.renderSortedRenderers(var30, var31, par2, par3); var31 < var9; var8 += this.renderSortedRenderers(var42, var31, par2, par3)) {
            var5.endStartSection("occ");
            var42 = var31;
            if(var32 < this.renderChunksWide) {
               ++var32;
            } else {
               --var32;
            }

            var31 += var32 * 40;
            if(var31 <= var42) {
               var31 = var42 + 10;
            }

            if(var31 > var9) {
               var31 = var9;
            }

            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(3008);
            GL11.glDisable(2912);
            GL11.glColorMask(false, false, false, false);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            this.glRenderLists.clear();
            float var34 = 0.0F;
            float var35 = 0.0F;
            float var36 = 0.0F;
            float var37 = 0.0F;
            float var38 = 0.0F;
            float var39 = 0.0F;
            int var41 = var42;

            for(int var43 = var31; var41 < var43; ++var41) {
               if((var6 = this.sortedWorldRenderers[var41]).isWaitingOnOcclusionQuery) {
                  if(ARBOcclusionQuery.glGetQueryObjectiARB(var6.glOcclusionQuery, '\u8867') != 0) {
                     var6.isWaitingOnOcclusionQuery = false;
                     --var41;
                     var6.isVisible = ARBOcclusionQuery.glGetQueryObjectiARB(var6.glOcclusionQuery, '\u8866') > 0;
                  }
               } else if(var6.isInFrustum && this.cloudTickCounter % (var7 = (int)(1.0F + MathHelper.sqrt_float(var6.distanceToEntitySquared(par1EntityLivingBase)) / 128.0F)) == var41 % var7) {
                  if((float)((double)var6.posYMinus - var24) - var38 != 0.0F || (float)((double)var6.posXMinus - var22) - var37 != 0.0F || (float)((double)var6.posZMinus - var26) - var39 != 0.0F) {
                     GL11.glTranslatef(var34 = (float)((double)var6.posXMinus - var22) - var37, var35 = (float)((double)var6.posYMinus - var24) - var38, var36 = (float)((double)var6.posZMinus - var26) - var39);
                     var37 += var34;
                     var38 += var35;
                     var39 += var36;
                  }

                  ARBOcclusionQuery.glBeginQueryARB('\u8914', var6.glOcclusionQuery);
                  var6.callOcclusionQueryList();
                  ARBOcclusionQuery.glEndQueryARB('\u8914');
                  var6.isWaitingOnOcclusionQuery = true;
               }
            }

            GL11.glPopMatrix();
            if(this.mc.gameSettings.anaglyph) {
               var28 = EntityRenderer.anaglyphField == 0;
               GL11.glColorMask(!var28, var28, var28, true);
            } else {
               GL11.glColorMask(true, true, true, true);
            }

            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glEnable(2912);
            var5.endStartSection("render");
         }
      } else {
         var8 = this.renderSortedRenderers(0, var9, par2, par3);
      }

      var5.endSection();
      return var8;
   }

   private void checkOcclusionQueryResult(int startIndex, int endIndex, double double1, double double2, double double3) {}

   private int renderSortedRenderers(int par1, int par2, int par3, double par4) {
      int var6 = this.variableIntTemp[13];
      int var7 = this.renderersBeingRendered;
      boolean var8 = false;
      boolean var9 = false;
      if(par3 >= 0 && par3 <= 1 && par2 - par1 > 0) {
         if(par2 > var6) {
            par2 = var6;
         }

         if(par1 < 0) {
            par1 = 0;
         }

         this.theWorld.theProfiler.startSection("ChankBuilder");
         boolean var10 = par3 == 0 && (this.variableBooleanTemp[5] || this.variableLongTemp[3] <= System.currentTimeMillis());
         boolean var11 = par3 == 0 && (this.variableBooleanTemp[5] || var10 && this.mc.gameSettings.showDebugInfo);
         if(var10) {
            this.variableLongTemp[3] = System.currentTimeMillis() + (long)this.lightRendererConfig[5];
         }

         WorldRenderer var12 = null;
         if(par3 < 1 || this.variableIntTemp[14] < 1) {
            this.glRenderLists.clear();
            if(!this.variableBooleanTemp[2]) {
               int var26;
               int var27;
               if(var10) {
                  var26 = 0;

                  for(var27 = this.worldRenderersList.size(); var26 < var27; ++var26) {
                     if(((var12 = (WorldRenderer)this.worldRenderersList.get(var26)).isInFrustum = this.isBoundingBoxInFrustum(var12.rendererBoundingBox)) && !var12.skipRenderPass[0]) {
                        this.glRenderLists.add(var12);
                     }
                  }
               } else {
                  var26 = 0;

                  for(var27 = this.worldRenderersList.size(); var26 < var27; ++var26) {
                     if((var12 = (WorldRenderer)this.worldRenderersList.get(var26)).isInFrustum && !var12.skipRenderPass[par3]) {
                        this.glRenderLists.add(var12);
                     }
                  }
               }
            } else if(var10) {
               for(var6 = par1; var6 < par2; ++var6) {
                  if(!this.sortedWorldRenderers[var6].skipAllRenderPasses()) {
                     if((this.sortedWorldRenderers[var6].isInFrustum = this.isBoundingBoxInFrustum(this.sortedWorldRenderers[var6].rendererBoundingBox)) && !this.sortedWorldRenderers[var6].skipRenderPass[0]) {
                        if(this.sortedWorldRenderers[var6].isVisible) {
                           this.glRenderLists.add(this.sortedWorldRenderers[var6]);
                        } else {
                           ++this.renderersBeingOccluded;
                        }
                     } else {
                        ++this.renderersBeingClipped;
                     }
                  }
               }
            } else {
               for(var6 = par1; var6 < par2; ++var6) {
                  if(this.sortedWorldRenderers[var6].isInFrustum && !this.sortedWorldRenderers[var6].skipRenderPass[par3] && this.sortedWorldRenderers[var6].isVisible) {
                     this.glRenderLists.add(this.sortedWorldRenderers[var6]);
                  }
               }
            }

            this.variableIntTemp[15] = this.glRenderLists.size();
            if(var10) {
               this.renderersBeingRendered += this.variableIntTemp[15];
               if(!this.variableBooleanTemp[2]) {
                  this.renderersBeingClipped = this.worldRenderersList.size() - this.renderersBeingRendered;
               }
            }
         }

         this.variableIntTemp[14] = par3;
         int var13 = this.renderersBeingRendered - var7 >= 0?this.renderersBeingRendered - var7:0;
         if(var10) {
            this.variableIntTemp[6] = var13;
         } else {
            var13 = par3 == 0 && !this.variableBooleanTemp[5]?this.variableIntTemp[6] - var7:1;
         }

         ++var13;
         if(var11) {
            this.variableIntTemp[2] = this.renderersSkippingRenderPass = this.renderersLoaded - this.renderersBeingClipped - this.renderersBeingOccluded - this.renderersBeingRendered;
            this.variableIntTemp[3] = this.renderersBeingClipped;
            this.variableIntTemp[4] = this.renderersBeingOccluded;
            this.variableIntTemp[5] = this.renderersBeingRendered;
         } else {
            this.renderersSkippingRenderPass = this.variableIntTemp[2];
            this.renderersBeingClipped = this.variableIntTemp[3];
            this.renderersBeingOccluded = this.variableIntTemp[4];
            this.renderersBeingRendered = this.variableIntTemp[5];
         }

         this.theWorld.theProfiler.endSection();
         EntityLivingBase var14 = this.mc.renderViewEntity;
         double var15 = var14.lastTickPosX + (var14.posX - var14.lastTickPosX) * par4;
         double var17 = var14.lastTickPosY + (var14.posY - var14.lastTickPosY) * par4;
         double var19 = var14.lastTickPosZ + (var14.posZ - var14.lastTickPosZ) * par4;
         int var21 = 0;

         int var22;
         for(var22 = 0; var22 < this.allRenderLists.length; ++var22) {
            this.allRenderLists[var22].func_78421_b();
         }

         for(var22 = 0; var22 < this.glRenderLists.size(); ++var22) {
            WorldRenderer var23 = (WorldRenderer)this.glRenderLists.get(var22);
            int var24 = -1;

            for(int var25 = 0; var25 < var21; ++var25) {
               if(this.allRenderLists[var25].func_78418_a(var23.posXMinus, var23.posYMinus, var23.posZMinus)) {
                  var24 = var25;
               }
            }

            if(var24 < 0) {
               var24 = var21++;
               this.allRenderLists[var24].func_78422_a(var23.posXMinus, var23.posYMinus, var23.posZMinus, var15, var17, var19);
            }

            this.allRenderLists[var24].func_78420_a(var23.getGLCallListForPass(par3));
         }

         this.renderAllRenderLists(par3, par4);
         return var13;
      } else {
         return 0;
      }
   }

   public void renderAllRenderLists(int par1, double par2) {
      this.mc.entityRenderer.enableLightmap(par2);

      for(int var4 = 0; var4 < this.allRenderLists.length; ++var4) {
         this.allRenderLists[var4].func_78419_a();
      }

      this.mc.entityRenderer.disableLightmap(par2);
   }

   public void updateClouds() {
      ++this.cloudTickCounter;
      if(this.cloudTickCounter % 20 == 0) {
         Iterator var1 = this.damagedBlocks.values().iterator();

         while(var1.hasNext()) {
            DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
            int var3 = var2.getCreationCloudUpdateTick();
            if(this.cloudTickCounter - var3 > 400) {
               var1.remove();
            }
         }
      }

   }

   public void renderSky(float par1) {
      if(this.mc.theWorld.provider.dimensionId == 1) {
         GL11.glDisable(2912);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         RenderHelper.disableStandardItemLighting();
         GL11.glDepthMask(false);
         this.renderEngine.bindTexture(locationEndSkyPng);
         Tessellator var2 = Tessellator.instance;

         for(int var3 = 0; var3 < 6; ++var3) {
            GL11.glPushMatrix();
            if(var3 == 1) {
               GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var3 == 2) {
               GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var3 == 3) {
               GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var3 == 4) {
               GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if(var3 == 5) {
               GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            var2.startDrawingQuads();
            var2.setColorOpaque_I(2631720);
            var2.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
            var2.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
            var2.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
            var2.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
            var2.draw();
            GL11.glPopMatrix();
         }

         GL11.glDepthMask(true);
         GL11.glEnable(3553);
         GL11.glEnable(3008);
      } else if(this.mc.theWorld.provider.isSurfaceWorld()) {
         GL11.glDisable(3553);
         Vec3 var23 = this.theWorld.getSkyColor(this.mc.renderViewEntity, par1);
         float var24 = (float)var23.xCoord;
         float var4 = (float)var23.yCoord;
         float var5 = (float)var23.zCoord;
         float var6;
         if(this.mc.gameSettings.anaglyph) {
            float var7 = (var24 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
            float var8 = (var24 * 30.0F + var4 * 70.0F) / 100.0F;
            var6 = (var24 * 30.0F + var5 * 70.0F) / 100.0F;
            var24 = var7;
            var4 = var8;
            var5 = var6;
         }

         GL11.glColor3f(var24, var4, var5);
         Tessellator var25 = Tessellator.instance;
         GL11.glDepthMask(false);
         GL11.glEnable(2912);
         GL11.glColor3f(var24, var4, var5);
         GL11.glCallList(this.glSkyList);
         GL11.glDisable(2912);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         RenderHelper.disableStandardItemLighting();
         float[] var26 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(par1), par1);
         float var9;
         float var10;
         float var11;
         float var12;
         int var15;
         float var16;
         float var17;
         if(var26 != null) {
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(MathHelper.sin(this.theWorld.getCelestialAngleRadians(par1)) < 0.0F?180.0F:0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            var6 = var26[0];
            var9 = var26[1];
            var10 = var26[2];
            float var13;
            if(this.mc.gameSettings.anaglyph) {
               var11 = (var6 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
               var12 = (var6 * 30.0F + var9 * 70.0F) / 100.0F;
               var13 = (var6 * 30.0F + var10 * 70.0F) / 100.0F;
               var6 = var11;
               var9 = var12;
               var10 = var13;
            }

            var25.startDrawing(6);
            var25.setColorRGBA_F(var6, var9, var10, var26[3]);
            var25.addVertex(0.0D, 100.0D, 0.0D);
            byte var14 = 16;
            var25.setColorRGBA_F(var26[0], var26[1], var26[2], 0.0F);

            for(var15 = 0; var15 <= var14; ++var15) {
               var13 = (float)var15 * 3.1415927F * 2.0F / (float)var14;
               var16 = MathHelper.sin(var13);
               var17 = MathHelper.cos(var13);
               var25.addVertex((double)(var16 * 120.0F), (double)(var17 * 120.0F), (double)(-var17 * 40.0F * var26[3]));
            }

            var25.draw();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
         }

         GL11.glEnable(3553);
         GL11.glBlendFunc(770, 1);
         GL11.glPushMatrix();
         var6 = 1.0F - this.theWorld.getRainStrength(par1);
         var9 = 0.0F;
         var10 = 0.0F;
         var11 = 0.0F;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var6);
         GL11.glTranslatef(var9, var10, var11);
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.theWorld.getCelestialAngle(par1) * 360.0F, 1.0F, 0.0F, 0.0F);
         var12 = 30.0F;
         this.renderEngine.bindTexture(locationSunPng);
         var25.startDrawingQuads();
         var25.addVertexWithUV((double)(-var12), 100.0D, (double)(-var12), 0.0D, 0.0D);
         var25.addVertexWithUV((double)var12, 100.0D, (double)(-var12), 1.0D, 0.0D);
         var25.addVertexWithUV((double)var12, 100.0D, (double)var12, 1.0D, 1.0D);
         var25.addVertexWithUV((double)(-var12), 100.0D, (double)var12, 0.0D, 1.0D);
         var25.draw();
         var12 = 20.0F;
         this.renderEngine.bindTexture(locationMoonPhasesPng);
         int var27 = this.theWorld.getMoonPhase();
         int var28 = var27 % 4;
         var15 = var27 / 4 % 2;
         var16 = (float)(var28 + 0) / 4.0F;
         var17 = (float)(var15 + 0) / 2.0F;
         float var18 = (float)(var28 + 1) / 4.0F;
         float var19 = (float)(var15 + 1) / 2.0F;
         var25.startDrawingQuads();
         var25.addVertexWithUV((double)(-var12), -100.0D, (double)var12, (double)var18, (double)var19);
         var25.addVertexWithUV((double)var12, -100.0D, (double)var12, (double)var16, (double)var19);
         var25.addVertexWithUV((double)var12, -100.0D, (double)(-var12), (double)var16, (double)var17);
         var25.addVertexWithUV((double)(-var12), -100.0D, (double)(-var12), (double)var18, (double)var17);
         var25.draw();
         GL11.glDisable(3553);
         float var20 = this.theWorld.getStarBrightness(par1) * var6;
         if(var20 > 0.0F) {
            GL11.glColor4f(var20, var20, var20, var20);
            GL11.glCallList(this.starGLCallList);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(3008);
         GL11.glEnable(2912);
         GL11.glPopMatrix();
         GL11.glDisable(3553);
         GL11.glColor3f(0.0F, 0.0F, 0.0F);
         double var21 = this.mc.thePlayer.getPosition(par1).yCoord - this.theWorld.getHorizon();
         if(var21 < 0.0D) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            var10 = 1.0F;
            var11 = -((float)(var21 + 65.0D));
            var12 = -var10;
            var25.startDrawingQuads();
            var25.setColorRGBA_I(0, 255);
            var25.addVertex((double)(-var10), (double)var11, (double)var10);
            var25.addVertex((double)var10, (double)var11, (double)var10);
            var25.addVertex((double)var10, (double)var12, (double)var10);
            var25.addVertex((double)(-var10), (double)var12, (double)var10);
            var25.addVertex((double)(-var10), (double)var12, (double)(-var10));
            var25.addVertex((double)var10, (double)var12, (double)(-var10));
            var25.addVertex((double)var10, (double)var11, (double)(-var10));
            var25.addVertex((double)(-var10), (double)var11, (double)(-var10));
            var25.addVertex((double)var10, (double)var12, (double)(-var10));
            var25.addVertex((double)var10, (double)var12, (double)var10);
            var25.addVertex((double)var10, (double)var11, (double)var10);
            var25.addVertex((double)var10, (double)var11, (double)(-var10));
            var25.addVertex((double)(-var10), (double)var11, (double)(-var10));
            var25.addVertex((double)(-var10), (double)var11, (double)var10);
            var25.addVertex((double)(-var10), (double)var12, (double)var10);
            var25.addVertex((double)(-var10), (double)var12, (double)(-var10));
            var25.addVertex((double)(-var10), (double)var12, (double)(-var10));
            var25.addVertex((double)(-var10), (double)var12, (double)var10);
            var25.addVertex((double)var10, (double)var12, (double)var10);
            var25.addVertex((double)var10, (double)var12, (double)(-var10));
            var25.draw();
         }

         if(this.theWorld.provider.isSkyColored()) {
            GL11.glColor3f(var24 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
         } else {
            GL11.glColor3f(var24, var4, var5);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, -((float)(var21 - 16.0D)), 0.0F);
         GL11.glCallList(this.glSkyList2);
         GL11.glPopMatrix();
         GL11.glEnable(3553);
         GL11.glDepthMask(true);
      }

   }

   public void renderClouds(float par1) {
      if(this.mc.theWorld.provider.isSurfaceWorld()) {
         float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)par1);
         float var3 = this.theWorld.provider.getCloudHeight() - var2 + 0.33F;
         double var4 = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)par1 + (double)((float)this.cloudTickCounter + par1) * 0.029999999329447746D;
         double var6 = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)par1;
         int var8 = 12 << 3 - this.mc.gameSettings.renderDistance;
         boolean var9 = false;
         boolean var10 = false;
         boolean var11 = false;
         float var12 = 0.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = 0.0F;
         float var16 = 0.0F;
         float var17 = 0.0F;
         Tessellator var18 = Tessellator.instance;
         Vec3 var19 = this.theWorld.getCloudColour(par1);
         float var20 = (float)var19.xCoord;
         float var21 = (float)var19.yCoord;
         float var22 = (float)var19.zCoord;
         if(this.mc.gameSettings.anaglyph) {
            var20 = (var20 * 30.0F + var21 * 59.0F + var22 * 11.0F) / 100.0F;
            var21 = (var20 * 30.0F + var21 * 70.0F) / 100.0F;
            var22 = (var20 * 30.0F + var22 * 70.0F) / 100.0F;
         }

         GL11.glDisable(2884);
         this.renderEngine.bindTexture(locationCloudsPng);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         float var23;
         float var24;
         float var25;
         float var26;
         if(!this.mc.gameSettings.fancyGraphics) {
            var4 -= (double)(MathHelper.floor_double(var4 / 2048.0D) * 2048);
            var6 -= (double)(MathHelper.floor_double(var6 / 2048.0D) * 2048);
            if(var20 - this.variableFloatTemp[3] <= 0.01F && var20 - this.variableFloatTemp[3] > -0.01F && var21 - this.variableFloatTemp[4] <= 0.01F && var21 - this.variableFloatTemp[4] > -0.01F && var22 - this.variableFloatTemp[5] <= 0.01F && var22 - this.variableFloatTemp[5] > -0.01F && (float)var4 - this.variableFloatTemp[0] <= 4.152F && (float)var4 - this.variableFloatTemp[0] > -4.152F && var3 - this.variableFloatTemp[1] <= 3.46F && var3 - this.variableFloatTemp[1] > -3.46F && (float)var6 - this.variableFloatTemp[2] <= 4.152F && (float)var6 - this.variableFloatTemp[2] > -4.152F) {
               GL11.glTranslatef(this.variableFloatTemp[0] - (float)var4, var3 - this.variableFloatTemp[1], this.variableFloatTemp[2] - (float)var6);
               GL11.glCallList(this.variableIntTemp[7]);
            } else {
               var8 *= 12;
               var12 = 4.8828125E-4F;
               var13 = (float)(var4 * (double)var12);
               var14 = (float)(var6 * (double)var12);
               this.variableFloatTemp[0] = (float)var4;
               this.variableFloatTemp[1] = var3;
               this.variableFloatTemp[2] = (float)var6;
               this.variableFloatTemp[3] = var20;
               this.variableFloatTemp[4] = var21;
               this.variableFloatTemp[5] = var22;
               var23 = (float)var8 / -2.0F;
               var24 = (float)var8 / -2.0F;
               var25 = -var23;
               var26 = -var24;
               GL11.glNewList(this.variableIntTemp[7], 4865);
               var18.startDrawingQuads();
               var18.setColorRGBA_F(var20, var21, var22, 0.8F);
               var18.addVertexWithUV((double)var23, (double)var3, (double)(var26 + (float)var8), (double)(var23 * var12 + var13), (double)((var26 + (float)var8) * var12 + var14));
               var18.addVertexWithUV((double)(var25 + (float)var8), (double)var3, (double)(var26 + (float)var8), (double)((var25 + (float)var8) * var12 + var13), (double)((var26 + (float)var8) * var12 + var14));
               var18.addVertexWithUV((double)(var25 + (float)var8), (double)var3, (double)var24, (double)((var25 + (float)var8) * var12 + var13), (double)(var24 * var12 + var14));
               var18.addVertexWithUV((double)var23, (double)var3, (double)var24, (double)(var23 * var12 + var13), (double)(var24 * var12 + var14));
               var18.draw();
               GL11.glEndList();
            }
         } else {
            var12 = 12.0F;
            var4 /= (double)var12;
            var6 = var6 / (double)var12 + 0.33000001311302185D;
            var4 -= (double)(MathHelper.floor_double(var4 / 2048.0D) * 2048);
            var6 -= (double)(MathHelper.floor_double(var6 / 2048.0D) * 2048);
            GL11.glScalef(var12, 1.0F, var12);
            if(var20 - this.variableFloatTemp[3] <= 0.01F && var20 - this.variableFloatTemp[3] > -0.01F && var21 - this.variableFloatTemp[4] <= 0.01F && var21 - this.variableFloatTemp[4] > -0.01F && var22 - this.variableFloatTemp[5] <= 0.01F && var22 - this.variableFloatTemp[5] > -0.01F && (float)var4 - this.variableFloatTemp[0] <= 0.346F && (float)var4 - this.variableFloatTemp[0] > -0.346F && var3 - this.variableFloatTemp[1] <= 3.46F && var3 - this.variableFloatTemp[1] > -3.46F && (float)var6 - this.variableFloatTemp[2] <= 0.346F && (float)var6 - this.variableFloatTemp[2] > -0.346F) {
               GL11.glTranslatef(this.variableFloatTemp[0] - (float)var4, var3 - this.variableFloatTemp[1], this.variableFloatTemp[2] - (float)var6);
               GL11.glCallList(this.variableIntTemp[7]);
            } else {
               var13 = 4.0F;
               var14 = 0.00390625F;
               var15 = 9.765625E-4F;
               var16 = (float)MathHelper.floor_double(var4) * var14;
               var17 = (float)MathHelper.floor_double(var6) * var14;
               this.variableFloatTemp[0] = (float)var4;
               this.variableFloatTemp[1] = var3;
               this.variableFloatTemp[2] = (float)var6;
               this.variableFloatTemp[3] = var20;
               this.variableFloatTemp[4] = var21;
               this.variableFloatTemp[5] = var22;
               var23 = (float)var8 / -2.0F;
               var24 = (float)var8 / -2.0F;
               var25 = var23 - (float)(var4 - (double)MathHelper.floor_double(var4));
               var26 = var24 - (float)(var6 - (double)MathHelper.floor_double(var6));
               double var27 = (double)(var26 + (float)var8);
               double var29 = (double)(var3 + var13 - var15);
               double var31 = (double)(var23 * var14 + var16);
               double var33 = (double)(var24 * var14 + var17);
               double var35 = (double)((var23 + (float)var8) * var14 + var16);
               double var37 = (double)((var24 + (float)var8) * var14 + var17);
               GL11.glNewList(this.variableIntTemp[7], 4865);

               for(int var39 = 0; var39 < 2; ++var39) {
                  if(var39 == 0) {
                     GL11.glColorMask(false, false, false, false);
                  } else if(this.mc.gameSettings.anaglyph) {
                     if(EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask(false, true, true, true);
                     } else {
                        GL11.glColorMask(true, false, false, true);
                     }
                  } else {
                     GL11.glColorMask(true, true, true, true);
                  }

                  var18.startDrawingQuads();
                  if(var3 > -var13 - 4.0F) {
                     var18.setColorRGBA_F(var20 * 0.7F, var21 * 0.7F, var22 * 0.7F, 0.8F);
                     var18.setNormal(0.0F, -1.0F, 0.0F);
                     var18.addVertexWithUV((double)var25, (double)var3, var27, var31, var37);
                     var18.addVertexWithUV((double)(var25 + (float)var8), (double)var3, var27, var35, var37);
                     var18.addVertexWithUV((double)(var25 + (float)var8), (double)var3, (double)var26, var35, var33);
                     var18.addVertexWithUV((double)var25, (double)var3, (double)var26, var31, var33);
                  }

                  if(var3 <= var13 + 4.0F) {
                     var18.setColorRGBA_F(var20, var21, var22, 0.8F);
                     var18.setNormal(0.0F, 1.0F, 0.0F);
                     var18.addVertexWithUV((double)var25, var29, var27, var31, var37);
                     var18.addVertexWithUV((double)(var25 + (float)var8), var29, var27, var35, var37);
                     var18.addVertexWithUV((double)(var25 + (float)var8), var29, (double)var26, var35, var33);
                     var18.addVertexWithUV((double)var25, var29, (double)var26, var31, var33);
                  }

                  var18.setColorRGBA_F(var20 * 0.9F, var21 * 0.9F, var22 * 0.9F, 0.8F);
                  var18.setNormal(-1.0F, 0.0F, 0.0F);
                  int var41 = 0;

                  int var40;
                  for(var40 = (int)((double)var8 * 0.5D) + -1; var41 < var8; ++var41) {
                     if(var41 > var40) {
                        var18.addVertexWithUV((double)(var25 + (float)var41), (double)var3, var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.addVertexWithUV((double)(var25 + (float)var41), (double)(var3 + var13), var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.addVertexWithUV((double)(var25 + (float)var41), (double)(var3 + var13), (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                        var18.addVertexWithUV((double)(var25 + (float)var41), (double)var3, (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                     }
                  }

                  var18.setNormal(1.0F, 0.0F, 0.0F);
                  var41 = 0;

                  for(var40 = (int)((double)var8 * 0.5D) + 1; var41 < var8; ++var41) {
                     if(var41 <= var40) {
                        var18.addVertexWithUV((double)(var25 + (float)var41 + 1.0F - var15), (double)var3, var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.addVertexWithUV((double)(var25 + (float)var41 + 1.0F - var15), (double)(var3 + var13), var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.addVertexWithUV((double)(var25 + (float)var41 + 1.0F - var15), (double)(var3 + var13), (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                        var18.addVertexWithUV((double)(var25 + (float)var41 + 1.0F - var15), (double)var3, (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                     }
                  }

                  var18.setColorRGBA_F(var20 * 0.8F, var21 * 0.8F, var22 * 0.8F, 0.8F);
                  var18.setNormal(0.0F, 0.0F, -1.0F);
                  var41 = 0;

                  for(var40 = (int)((double)var8 * 0.5D) + -1; var41 < var8; ++var41) {
                     if(var41 > var40) {
                        var18.addVertexWithUV((double)var25, (double)(var3 + var13), (double)(var26 + (float)var41), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.addVertexWithUV((double)(var25 + (float)var8), (double)(var3 + var13), (double)(var26 + (float)var41), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.addVertexWithUV((double)(var25 + (float)var8), (double)var3, (double)(var26 + (float)var41), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.addVertexWithUV((double)var25, (double)var3, (double)(var26 + (float)var41), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                     }
                  }

                  var18.setNormal(0.0F, 0.0F, 1.0F);
                  var41 = 0;

                  for(var40 = (int)((double)var8 * 0.5D) + 1; var41 < var8; ++var41) {
                     if(var41 <= var40) {
                        var18.addVertexWithUV((double)var25, (double)(var3 + var13), (double)(var26 + (float)var41 + 1.0F - var15), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.addVertexWithUV((double)(var25 + (float)var8), (double)(var3 + var13), (double)(var26 + (float)var41 + 1.0F - var15), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.addVertexWithUV((double)(var25 + (float)var8), (double)var3, (double)(var26 + (float)var41 + 1.0F - var15), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.addVertexWithUV((double)var25, (double)var3, (double)(var26 + (float)var41 + 1.0F - var15), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                     }
                  }

                  var18.draw();
               }

               GL11.glEndList();
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(2884);
      }
   }

   public boolean hasCloudFog(double par1, double par3, double par5, float par7) {
      return false;
   }

   public void renderCloudsFancy(float par1) {}

   public boolean updateRenderers(EntityLivingBase par1EntityLivingBase, boolean par2) {
      if(this.variableIntTemp[9] != 0) {
         this.variableIntTemp[9] = 0;
         return true;
      } else {
         int var10006 = this.variableIntTemp[18];
         int var10003 = this.variableIntTemp[18];
         this.variableIntTemp[18] = var10006 + 1;
         return this.updateRenderersMain(par1EntityLivingBase, par2, var10003);
      }
   }

   public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityPlayer par2EntityPlayer, float par3) {
      double var4 = par2EntityPlayer.lastTickPosX + (par2EntityPlayer.posX - par2EntityPlayer.lastTickPosX) * (double)par3;
      double var6 = par2EntityPlayer.lastTickPosY + (par2EntityPlayer.posY - par2EntityPlayer.lastTickPosY) * (double)par3;
      double var8 = par2EntityPlayer.lastTickPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.lastTickPosZ) * (double)par3;
      if(!this.damagedBlocks.isEmpty()) {
         GL11.glBlendFunc(774, 768);
         this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
         GL11.glPushMatrix();
         GL11.glDisable(3008);
         GL11.glPolygonOffset(-3.0F, -3.0F);
         GL11.glEnable('\u8037');
         GL11.glEnable(3008);
         par1Tessellator.startDrawingQuads();
         par1Tessellator.setTranslation(-var4, -var6, -var8);
         par1Tessellator.disableColor();
         Iterator var10 = this.damagedBlocks.values().iterator();

         while(var10.hasNext()) {
            DestroyBlockProgress var11 = (DestroyBlockProgress)var10.next();
            double var12 = (double)var11.getPartialBlockX() - var4;
            double var14 = (double)var11.getPartialBlockY() - var6;
            double var16 = (double)var11.getPartialBlockZ() - var8;
            if(var12 * var12 + var14 * var14 + var16 * var16 > 1024.0D) {
               var10.remove();
            } else {
               int var18 = this.theWorld.getBlockId(var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ());
               Block var19 = var18 > 0?Block.blocksList[var18]:null;
               if(var19 == null) {
                  var19 = Block.stone;
               }

               this.globalRenderBlocks.renderBlockUsingTexture(var19, var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ(), this.destroyBlockIcons[var11.getPartialBlockDamage()]);
            }
         }

         par1Tessellator.draw();
         par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
         GL11.glDisable(3008);
         GL11.glPolygonOffset(0.0F, 0.0F);
         GL11.glDisable('\u8037');
         GL11.glEnable(3008);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }

   }

   public void drawSelectionBox(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, float par4) {
      if(par3 == 0 && par2MovingObjectPosition.typeOfHit == EnumMovingObjectType.TILE) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDepthMask(false);
         float var5 = 0.002F;
         int var6 = this.theWorld.getBlockId(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
         if(var6 > 0) {
            Block.blocksList[var6].setBlockBoundsBasedOnState(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
            double var7 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * (double)par4;
            double var9 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * (double)par4;
            double var11 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * (double)par4;
            this.drawOutlinedBoundingBox(Block.blocksList[var6].getSelectedBoundingBoxFromPool(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand((double)var5, (double)var5, (double)var5).getOffsetBoundingBox(-var7, -var9, -var11));
         }

         GL11.glDepthMask(true);
         GL11.glEnable(3553);
         GL11.glDisable(3042);
      }

   }

   private void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB) {
      Tessellator var2 = Tessellator.instance;
      var2.startDrawing(3);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      var2.draw();
      var2.startDrawing(3);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      var2.draw();
      var2.startDrawing(1);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      var2.draw();
   }

   public void markBlocksForUpdate(int par1, int par2, int par3, int par4, int par5, int par6) {
      int var7 = MathHelper.bucketInt(par1, 16);
      int var8 = MathHelper.bucketInt(par2, 16);
      int var9 = MathHelper.bucketInt(par3, 16);
      int var10 = MathHelper.bucketInt(par4, 16);
      int var11 = MathHelper.bucketInt(par5, 16);
      int var12 = MathHelper.bucketInt(par6, 16);

      for(int var13 = var7; var13 <= var10; ++var13) {
         int var14 = var13 % this.renderChunksWide;
         if(var14 < 0) {
            var14 += this.renderChunksWide;
         }

         for(int var15 = var8; var15 <= var11; ++var15) {
            int var16 = var15 % this.renderChunksTall;
            if(var16 < 0) {
               var16 += this.renderChunksTall;
            }

            for(int var17 = var9; var17 <= var12; ++var17) {
               int var18 = var17 % this.renderChunksDeep;
               if(var18 < 0) {
                  var18 += this.renderChunksDeep;
               }

               int var19 = (var18 * this.renderChunksTall + var16) * this.renderChunksWide + var14;
               WorldRenderer var20 = this.worldRenderers[var19];
               if(var20 != null && !var20.needsUpdate) {
                  this.worldRenderersToUpdate.add(var20);
                  var20.markDirty();
               }
            }
         }
      }

   }

   public void markBlockForUpdate(int par1, int par2, int par3) {
      this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
   }

   public void markBlockForRenderUpdate(int par1, int par2, int par3) {
      this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
   }

   public void markBlockRangeForRenderUpdate(int par1, int par2, int par3, int par4, int par5, int par6) {
      this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par4 + 1, par5 + 1, par6 + 1);
   }

   public void clipRenderersByFrustum(ICamera par1ICamera, float par2) {
      EntityLivingBase var3 = this.mc.renderViewEntity;
      this.xPosition = var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * (double)par2;
      this.yPosition = var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)par2;
      this.zPosition = var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * (double)par2;
      ClippingHelper var4 = ClippingHelperImpl.getInstance();
      int var5 = 0;

      for(byte var6 = 6; var5 < var6; ++var5) {
         this.frustum1[var5] = var4.frustum[var5][0];
         this.frustum2[var5] = var4.frustum[var5][1];
         this.frustum3[var5] = var4.frustum[var5][2];
         this.frustum4[var5] = var4.frustum[var5][3];
      }

      this.frameSettings(this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep);
      byte var7 = 0;
      boolean var10002 = this.variableBooleanTemp[2];
      var5 = var7 + 1;
      int var8 = 1 + this.booleanNumber(var10002, var7) + this.booleanNumber(this.variableBooleanTemp[3], var5++) + this.booleanNumber(this.variableBooleanTemp[4], var5++) + this.booleanNumber(this.variableBooleanTemp[5], var5++) + (this.lightRendererConfig[24] << var5++) + (this.lightRendererConfig[25] << var5++);
      if(var8 != this.variableIntTemp[16]) {
         this.variableIntTemp[16] = var8;
         this.markRenderers();
      }

      if(this.lightRendererConfig[8] > 0) {
         if(this.variableLongTemp[5] != (long)this.cloudTickCounter) {
            if(this.lightRendererConfig[8] <= this.variableIntTemp[10]) {
               ++this.variableIntTemp[9];
            }

            this.variableIntTemp[10] = 0;
            this.variableLongTemp[5] = (long)this.cloudTickCounter;
         } else {
            ++this.variableIntTemp[10];
         }
      }

      this.variableIntTemp[18] = 0;
      ++this.frustumCheckOffset;
   }

   public void playRecord(String par1Str, int par2, int par3, int par4) {
      ItemRecord var5 = ItemRecord.getRecord(par1Str);
      if(par1Str != null && var5 != null) {
         this.mc.ingameGUI.setRecordPlayingMessage(var5.getRecordTitle());
      }

      this.mc.sndManager.playStreaming(par1Str, (float)par2, (float)par3, (float)par4);
   }

   public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9) {}

   public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10) {}

   public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
      try {
         this.doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
      } catch (Throwable var17) {
         CrashReport var15 = CrashReport.makeCrashReport(var17, "Exception while adding particle");
         CrashReportCategory var16 = var15.makeCategory("Particle being added");
         var16.addCrashSection("Name", par1Str);
         var16.addCrashSectionCallable("Position", new CallableParticlePositionInfo(this, par2, par4, par6));
         throw new ReportedException(var15);
      }
   }

   public EntityFX doSpawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
      if(this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null) {
         int var14 = this.mc.gameSettings.particleSetting;
         if(var14 == 1 && this.theWorld.rand.nextInt(3) == 0) {
            var14 = 2;
         }

         double var15 = this.mc.renderViewEntity.posX - par2;
         double var17 = this.mc.renderViewEntity.posY - par4;
         double var19 = this.mc.renderViewEntity.posZ - par6;
         EntityFX var21 = null;
         if(par1Str.equals("hugeexplosion")) {
            this.mc.effectRenderer.addEffect(var21 = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12));
         } else if(par1Str.equals("largeexplode")) {
            this.mc.effectRenderer.addEffect(var21 = new EntityLargeExplodeFX(this.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12));
         } else if(par1Str.equals("fireworksSpark")) {
            this.mc.effectRenderer.addEffect(var21 = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer));
         }

         if(var21 != null) {
            return (EntityFX)var21;
         } else {
            double var22 = 16.0D;
            if(var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22) {
               return null;
            } else if(var14 > 1) {
               return null;
            } else {
               if(par1Str.equals("bubble")) {
                  var21 = new EntityBubbleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("suspended")) {
                  var21 = new EntitySuspendFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("depthsuspend")) {
                  var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("townaura")) {
                  var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("crit")) {
                  var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("magicCrit")) {
                  var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                  ((EntityFX)var21).setRBGColorF(((EntityFX)var21).getRedColorF() * 0.3F, ((EntityFX)var21).getGreenColorF() * 0.8F, ((EntityFX)var21).getBlueColorF());
                  ((EntityFX)var21).nextTextureIndexX();
               } else if(par1Str.equals("smoke")) {
                  var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("mobSpell")) {
                  var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
                  ((EntityFX)var21).setRBGColorF((float)par8, (float)par10, (float)par12);
               } else if(par1Str.equals("mobSpellAmbient")) {
                  var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
                  ((EntityFX)var21).setAlphaF(0.15F);
                  ((EntityFX)var21).setRBGColorF((float)par8, (float)par10, (float)par12);
               } else if(par1Str.equals("spell")) {
                  var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("instantSpell")) {
                  var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                  ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
               } else if(par1Str.equals("witchMagic")) {
                  var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                  ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
                  float var24 = this.theWorld.rand.nextFloat() * 0.5F + 0.35F;
                  ((EntityFX)var21).setRBGColorF(1.0F * var24, 0.0F * var24, 1.0F * var24);
               } else if(par1Str.equals("note")) {
                  var21 = new EntityNoteFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("portal")) {
                  var21 = new EntityPortalFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("enchantmenttable")) {
                  var21 = new EntityEnchantmentTableParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("explode")) {
                  var21 = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("flame")) {
                  var21 = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("lava")) {
                  var21 = new EntityLavaFX(this.theWorld, par2, par4, par6);
               } else if(par1Str.equals("footstep")) {
                  var21 = new EntityFootStepFX(this.renderEngine, this.theWorld, par2, par4, par6);
               } else if(par1Str.equals("splash")) {
                  var21 = new EntitySplashFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("largesmoke")) {
                  var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5F);
               } else if(par1Str.equals("cloud")) {
                  var21 = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("reddust")) {
                  var21 = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
               } else if(par1Str.equals("snowballpoof")) {
                  var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.snowball);
               } else if(par1Str.equals("dripWater")) {
                  var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.water);
               } else if(par1Str.equals("dripLava")) {
                  var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.lava);
               } else if(par1Str.equals("snowshovel")) {
                  var21 = new EntitySnowShovelFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("slime")) {
                  var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.slimeBall);
               } else if(par1Str.equals("heart")) {
                  var21 = new EntityHeartFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if(par1Str.equals("angryVillager")) {
                  var21 = new EntityHeartFX(this.theWorld, par2, par4 + 0.5D, par6, par8, par10, par12);
                  ((EntityFX)var21).setParticleTextureIndex(81);
                  ((EntityFX)var21).setRBGColorF(1.0F, 1.0F, 1.0F);
               } else if(par1Str.equals("happyVillager")) {
                  var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                  ((EntityFX)var21).setParticleTextureIndex(82);
                  ((EntityFX)var21).setRBGColorF(1.0F, 1.0F, 1.0F);
               } else {
                  String[] var25;
                  int var26;
                  int var27;
                  if(par1Str.startsWith("iconcrack_")) {
                     var25 = par1Str.split("_", 3);
                     var27 = Integer.parseInt(var25[1]);
                     if(var25.length > 2) {
                        var26 = Integer.parseInt(var25[2]);
                        var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[var27], var26);
                     } else {
                        var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[var27], 0);
                     }
                  } else if(par1Str.startsWith("tilecrack_")) {
                     var25 = par1Str.split("_", 3);
                     var27 = Integer.parseInt(var25[1]);
                     var26 = Integer.parseInt(var25[2]);
                     var21 = (new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Block.blocksList[var27], var26)).applyRenderColor(var26);
                  }
               }

               if(var21 != null) {
                  this.mc.effectRenderer.addEffect((EntityFX)var21);
               }

               return (EntityFX)var21;
            }
         }
      } else {
         return null;
      }
   }

   public void onEntityCreate(Entity par1Entity) {}

   public void onEntityDestroy(Entity par1Entity) {}

   public void deleteAllDisplayLists() {
      GLAllocation.deleteDisplayLists(this.glRenderListBase);
   }

   public void broadcastSound(int par1, int par2, int par3, int par4, int par5) {
      Random var6 = this.theWorld.rand;
      switch(par1) {
      case 1013:
      case 1018:
         if(this.mc.renderViewEntity != null) {
            double var7 = (double)par2 - this.mc.renderViewEntity.posX;
            double var9 = (double)par3 - this.mc.renderViewEntity.posY;
            double var11 = (double)par4 - this.mc.renderViewEntity.posZ;
            double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
            double var15 = this.mc.renderViewEntity.posX;
            double var17 = this.mc.renderViewEntity.posY;
            double var19 = this.mc.renderViewEntity.posZ;
            if(var13 > 0.0D) {
               var15 += var7 / var13 * 2.0D;
               var17 += var9 / var13 * 2.0D;
               var19 += var11 / var13 * 2.0D;
            }

            if(par1 == 1013) {
               this.theWorld.playSound(var15, var17, var19, "mob.wither.spawn", 1.0F, 1.0F, false);
            } else if(par1 == 1018) {
               this.theWorld.playSound(var15, var17, var19, "mob.enderdragon.end", 5.0F, 1.0F, false);
            }
         }
      default:
      }
   }

   public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6) {
      Random var7 = this.theWorld.rand;
      double var8;
      double var10;
      double var12;
      String var14;
      int var15;
      int var16;
      double var17;
      double var19;
      double var21;
      double var23;
      double var25;
      switch(par2) {
      case 1000:
         this.theWorld.playSound((double)par3, (double)par4, (double)par5, "random.click", 1.0F, 1.0F, false);
         break;
      case 1001:
         this.theWorld.playSound((double)par3, (double)par4, (double)par5, "random.click", 1.0F, 1.2F, false);
         break;
      case 1002:
         this.theWorld.playSound((double)par3, (double)par4, (double)par5, "random.bow", 1.0F, 1.2F, false);
         break;
      case 1003:
         if(Math.random() < 0.5D) {
            this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         } else {
            this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         }
         break;
      case 1004:
         this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.fizz", 0.5F, 2.6F + (var7.nextFloat() - var7.nextFloat()) * 0.8F, false);
         break;
      case 1005:
         if(Item.itemsList[par6] instanceof ItemRecord) {
            this.theWorld.playRecord(((ItemRecord)Item.itemsList[par6]).recordName, par3, par4, par5);
         } else {
            this.theWorld.playRecord((String)null, par3, par4, par5);
         }
         break;
      case 1007:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.charge", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1008:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.fireball", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1009:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.fireball", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1010:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.wood", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1011:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.metal", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1012:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.woodbreak", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1014:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.wither.shoot", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1015:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.bat.takeoff", 0.05F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1016:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.infect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1017:
         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.unfect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1020:
         this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1021:
         this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1022:
         this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2000:
         int var27 = par6 % 3 - 1;
         int var28 = par6 / 3 % 3 - 1;
         var10 = (double)par3 + (double)var27 * 0.6D + 0.5D;
         var12 = (double)par4 + 0.5D;
         double var29 = (double)par5 + (double)var28 * 0.6D + 0.5D;

         for(int var43 = 0; var43 < 10; ++var43) {
            double var44 = var7.nextDouble() * 0.2D + 0.01D;
            double var45 = var10 + (double)var27 * 0.01D + (var7.nextDouble() - 0.5D) * (double)var28 * 0.5D;
            var25 = var12 + (var7.nextDouble() - 0.5D) * 0.5D;
            var17 = var29 + (double)var28 * 0.01D + (var7.nextDouble() - 0.5D) * (double)var27 * 0.5D;
            var19 = (double)var27 * var44 + var7.nextGaussian() * 0.01D;
            var21 = -0.03D + var7.nextGaussian() * 0.01D;
            var23 = (double)var28 * var44 + var7.nextGaussian() * 0.01D;
            this.spawnParticle("smoke", var45, var25, var17, var19, var21, var23);
         }

         return;
      case 2001:
         var16 = par6 & 4095;
         if(var16 > 0) {
            Block var42 = Block.blocksList[var16];
            this.mc.sndManager.playSound(var42.stepSound.getBreakSound(), (float)par3 + 0.5F, (float)par4 + 0.5F, (float)par5 + 0.5F, (var42.stepSound.getVolume() + 1.0F) / 2.0F, var42.stepSound.getPitch() * 0.8F);
         }

         this.mc.effectRenderer.addBlockDestroyEffects(par3, par4, par5, par6 & 4095, par6 >> 12 & 255);
         break;
      case 2002:
         var8 = (double)par3;
         var10 = (double)par4;
         var12 = (double)par5;
         var14 = "iconcrack_" + Item.potion.itemID + "_" + par6;

         for(var15 = 0; var15 < 8; ++var15) {
            this.spawnParticle(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
         }

         var15 = Item.potion.getColorFromDamage(par6);
         float var31 = (float)(var15 >> 16 & 255) / 255.0F;
         float var32 = (float)(var15 >> 8 & 255) / 255.0F;
         float var33 = (float)(var15 >> 0 & 255) / 255.0F;
         String var34 = "spell";
         if(Item.potion.isEffectInstant(par6)) {
            var34 = "instantSpell";
         }

         for(var16 = 0; var16 < 100; ++var16) {
            var25 = var7.nextDouble() * 4.0D;
            var17 = var7.nextDouble() * 3.141592653589793D * 2.0D;
            var19 = Math.cos(var17) * var25;
            var21 = 0.01D + var7.nextDouble() * 0.5D;
            var23 = Math.sin(var17) * var25;
            EntityFX var47 = this.doSpawnParticle(var34, var8 + var19 * 0.1D, var10 + 0.3D, var12 + var23 * 0.1D, var19, var21, var23);
            if(var47 != null) {
               float var48 = 0.75F + var7.nextFloat() * 0.25F;
               var47.setRBGColorF(var31 * var48, var32 * var48, var33 * var48);
               var47.multiplyVelocity((float)var25);
            }
         }

         this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.glass", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2003:
         var8 = (double)par3 + 0.5D;
         var10 = (double)par4;
         var12 = (double)par5 + 0.5D;
         var14 = "iconcrack_" + Item.eyeOfEnder.itemID;

         for(var15 = 0; var15 < 8; ++var15) {
            this.spawnParticle(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
         }

         for(double var46 = 0.0D; var46 < 6.283185307179586D; var46 += 0.15707963267948966D) {
            this.spawnParticle("portal", var8 + Math.cos(var46) * 5.0D, var10 - 0.4D, var12 + Math.sin(var46) * 5.0D, Math.cos(var46) * -5.0D, 0.0D, Math.sin(var46) * -5.0D);
            this.spawnParticle("portal", var8 + Math.cos(var46) * 5.0D, var10 - 0.4D, var12 + Math.sin(var46) * 5.0D, Math.cos(var46) * -7.0D, 0.0D, Math.sin(var46) * -7.0D);
         }

         return;
      case 2004:
         for(int var35 = 0; var35 < 20; ++var35) {
            double var36 = (double)par3 + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            double var38 = (double)par4 + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            double var40 = (double)par5 + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            this.theWorld.spawnParticle("smoke", var36, var38, var40, 0.0D, 0.0D, 0.0D);
            this.theWorld.spawnParticle("flame", var36, var38, var40, 0.0D, 0.0D, 0.0D);
         }

         return;
      case 2005:
         ItemDye.func_96603_a(this.theWorld, par3, par4, par5, par6);
      }

   }

   public void destroyBlockPartially(int par1, int par2, int par3, int par4, int par5) {
      if(par5 >= 0 && par5 < 10) {
         DestroyBlockProgress var6 = (DestroyBlockProgress)this.damagedBlocks.get(Integer.valueOf(par1));
         if(var6 == null || var6.getPartialBlockX() != par2 || var6.getPartialBlockY() != par3 || var6.getPartialBlockZ() != par4) {
            var6 = new DestroyBlockProgress(par1, par2, par3, par4);
            this.damagedBlocks.put(Integer.valueOf(par1), var6);
         }

         var6.setPartialBlockDamage(par5);
         var6.setCloudUpdateTick(this.cloudTickCounter);
      } else {
         this.damagedBlocks.remove(Integer.valueOf(par1));
      }

   }

   public void registerDestroyBlockIcons(IconRegister par1IconRegister) {
      this.destroyBlockIcons = new Icon[10];

      for(int var2 = 0; var2 < this.destroyBlockIcons.length; ++var2) {
         this.destroyBlockIcons[var2] = par1IconRegister.registerIcon("destroy_stage_" + var2);
      }

   }

}
