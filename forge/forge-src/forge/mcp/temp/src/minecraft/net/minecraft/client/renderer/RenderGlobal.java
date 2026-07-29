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

   private static final ResourceLocation field_110927_h = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation field_110928_i = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation field_110925_j = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation field_110926_k = new ResourceLocation("textures/environment/end_sky.png");
   public List field_72762_a = new ArrayList();
   private WorldClient field_72769_h;
   public final TextureManager field_72770_i;
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
   public List field_72767_j;
   private WorldRenderer[] field_72768_k;
   private WorldRenderer[] field_72765_l;
   private int field_72766_m;
   private int field_72763_n;
   private int field_72764_o;
   private int field_72778_p;
   private Minecraft field_72777_q;
   private RenderBlocks field_72776_r;
   private IntBuffer field_72775_s;
   private boolean field_72774_t;
   private int field_72773_u;
   private int field_72772_v;
   private int field_72771_w;
   private int field_72781_x;
   private int field_72780_y;
   private int field_72779_z;
   private int field_72741_A;
   private int field_72742_B;
   private int field_72743_C;
   private int field_72737_D;
   private Map field_72738_E = new HashMap();
   private Icon[] field_94141_F;
   private int field_72739_F = -1;
   private int field_72740_G = 2;
   private int field_72748_H;
   private int field_72749_I;
   private int field_72750_J;
   IntBuffer field_72761_c = GLAllocation.func_74527_f(64);
   private int field_72751_K;
   private int field_72744_L;
   private int field_72745_M;
   private int field_72746_N;
   private int field_72747_O;
   private int field_72753_P;
   private int field_72752_Q;
   private List field_72755_R = new ArrayList();
   private RenderList[] field_72754_S = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
   double field_72758_d = -9999.0D;
   double field_72759_e = -9999.0D;
   double field_72756_f = -9999.0D;
   int field_72757_g;


   private boolean isBoundingBoxInFrustum(AxisAlignedBB par1AxisAlignedBB) {
      return this.isBoxInFrustumCheck(par1AxisAlignedBB.field_72340_a - this.xPosition, par1AxisAlignedBB.field_72338_b - this.yPosition, par1AxisAlignedBB.field_72339_c - this.zPosition, par1AxisAlignedBB.field_72336_d - this.xPosition, par1AxisAlignedBB.field_72337_e - this.yPosition, par1AxisAlignedBB.field_72334_f - this.zPosition);
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
      return this.rayTraceBlocks(par1, par2, par3, axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
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
      int int1 = MathHelper.func_76128_c(double1);
      int int2 = MathHelper.func_76128_c(double2);
      int int3 = MathHelper.func_76128_c(double3);
      int int4 = MathHelper.func_76128_c(par1);
      int int5 = MathHelper.func_76128_c(par2);
      int int6 = MathHelper.func_76128_c(par3);
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
            int4 = MathHelper.func_76128_c(par1) - 1;
         } else {
            int4 = MathHelper.func_76128_c(par1);
         }

         if(byte1 == 1) {
            int5 = MathHelper.func_76128_c(par2) - 1;
         } else {
            int5 = MathHelper.func_76128_c(par2);
         }

         if(byte1 == 3) {
            int6 = MathHelper.func_76128_c(par3) - 1;
         } else {
            int6 = MathHelper.func_76128_c(par3);
         }

         if(int4 >> 4 != int8 || int5 >> 4 != int9 || int6 >> 4 != int10) {
            if(int5 < 0 || int5 >= 256) {
               return true;
            }

            extendedblockstorage = this.field_72769_h.func_72964_e(int8 = int4 >> 4, int10 = int6 >> 4).func_76587_i()[int9 = int5 >> 4];
         }
      } while(extendedblockstorage == null || (block1 = Block.field_71973_m[extendedblockstorage.func_76656_a(int4 & 15, int5 & 15, int6 & 15)]) == null || !block1.func_71926_d());

      return false;
   }

   public void addWorldRenderersList(WorldRenderer worldRenderer) {
      if(!this.variableBooleanTemp[2] && !worldRenderer.func_78906_e() && !this.worldRenderersList.contains(worldRenderer)) {
         this.worldRenderersList.add(worldRenderer);
      }

   }

   private boolean updateRenderersMain(EntityLivingBase par1EntityLivingBase, boolean par2, int par3) {
      if(this.field_72767_j.size() <= 0) {
         return true;
      } else {
         WorldRenderer worldRenderer;
         int var3;
         if(this.lightRendererConfig[6] == 2 && par3 <= 0) {
            this.variableLongTemp[4] = System.currentTimeMillis() + 3L;
            var3 = this.variableIntTemp[8];

            for(int var4 = this.variableIntTemp[8] + 64 > this.field_72767_j.size()?this.field_72767_j.size():this.variableIntTemp[8] + 64; this.variableLongTemp[4] > System.currentTimeMillis() && var3 < var4; ++var3) {
               Chunk chunk;
               if((worldRenderer = (WorldRenderer)this.field_72767_j.get(var3)) != null && worldRenderer.field_78939_q && (!this.field_72769_h.func_72899_e(worldRenderer.field_78923_c, worldRenderer.field_78920_d, worldRenderer.field_78921_e) || !(chunk = this.field_72769_h.func_72938_d(worldRenderer.field_78923_c, worldRenderer.field_78921_e)).func_76621_g() && chunk.func_76587_i()[worldRenderer.field_78920_d >> 4] == null)) {
                  worldRenderer.func_78907_a();
                  if(var3 > 0 && var4 > 0) {
                     this.field_72767_j.remove(var3--);
                     --var4;
                  }
               }
            }

            if(this.variableIntTemp[8] > this.field_72767_j.size()) {
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
         for(var6 = this.field_72767_j.size(); var161 < var6; ++var161) {
            if((worldRenderer = (WorldRenderer)this.field_72767_j.get(var161)) != null) {
               if(!worldRenderer.field_78939_q) {
                  this.field_72767_j.set(var161, (Object)null);
               } else {
                  label90: {
                     if(!worldRenderer.field_78927_l) {
                        if((var12 = worldRenderer.func_78912_a(par1EntityLivingBase) * 4.0F) <= 1024.0F) {
                           break label90;
                        }
                     } else if((var12 = worldRenderer.func_78912_a(par1EntityLivingBase)) <= 256.0F) {
                        break label90;
                     }

                     if(var12 < var8) {
                        var7 = worldRenderer;
                        var8 = var12;
                        var9 = var161;
                     }
                     continue;
                  }

                  worldRenderer.func_78907_a();
                  worldRenderer.field_78939_q = false;
                  this.addWorldRenderersList(worldRenderer);
                  this.field_72767_j.set(var161, (Object)null);
                  ++var3;
                  if(var3 >= var15) {
                     break;
                  }
               }
            }
         }

         if(var7 != null && var3 < var15) {
            var7.func_78907_a();
            var7.field_78939_q = false;
            this.addWorldRenderersList(var7);
            this.field_72767_j.set(var9, (Object)null);
            float var16 = var8 / 5.0F;
            ++var3;
            if(var3 < var15) {
               var161 = 0;

               for(var6 = this.field_72767_j.size(); var161 < var6; ++var161) {
                  if((worldRenderer = (WorldRenderer)this.field_72767_j.get(var161)) != null && Math.abs((worldRenderer.field_78927_l?worldRenderer.func_78912_a(par1EntityLivingBase):worldRenderer.func_78912_a(par1EntityLivingBase) * 4.0F) - var8) < var16) {
                     worldRenderer.func_78907_a();
                     worldRenderer.field_78939_q = false;
                     this.addWorldRenderersList(worldRenderer);
                     this.field_72767_j.set(var161, (Object)null);
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
      this.variableBooleanTemp[4] = this.lightRendererConfig[7] == 2 && this.field_72739_F < 2;
      this.field_72752_Q = 0;
      this.frameSettings(this.field_72766_m * this.field_72763_n * this.field_72764_o);
      this.markRenderers();
      if(this.variableIntTemp[7] == 0) {
         this.variableIntTemp[7] = GLAllocation.func_74526_a(1);
      }

      if(this.lightRendererConfig[11] != this.variableIntTemp[17]) {
         this.variableIntTemp[17] = this.lightRendererConfig[11];
         GL11.glPushMatrix();
         GL11.glNewList(this.field_72772_v, 4864);
         if(this.lightRendererConfig[11] != 0) {
            this.renderFancyStars((this.lightRendererConfig[11] & 255) * 100);
         } else {
            this.func_72730_g();
         }

         GL11.glEndList();
         GL11.glPopMatrix();
      }

   }

   private void markRenderers() {
      this.field_72758_d = this.field_72759_e = this.field_72756_f = this.prevReposX = this.prevReposY = this.prevReposZ = -9999.0D;
      this.worldRenderersList.clear();
   }

   public int booleanNumber(boolean boolean1, int int1) {
      return (boolean1?1:0) << int1;
   }

   private void frameSettings(int int1) {
      this.variableIntTemp[13] = this.variableBooleanTemp[4]?(int)((double)int1 * 0.61685D):int1;
      this.variableBooleanTemp[5] = this.field_72774_t && this.field_72777_q.field_71474_y.field_74349_h && !this.field_72777_q.field_71474_y.field_74337_g;
      this.variableBooleanTemp[2] = this.variableBooleanTemp[3] || this.variableBooleanTemp[4] || this.variableBooleanTemp[5];
   }

   public byte getmemoryReserve(int int1) {
      Minecraft var10000 = this.field_72777_q;
      byte var2;
      if(Minecraft.field_71444_a != null) {
         var10000 = this.field_72777_q;
         if(Minecraft.field_71444_a.length >= int1) {
            var10000 = this.field_72777_q;
            var2 = Minecraft.field_71444_a[int1];
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
      Tessellator var2 = Tessellator.field_78398_a;
      var2.func_78382_b();
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
            var2.func_78369_a(float2, float3, float4, float1);
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
               var2.func_78377_a(var4 * 100.0D + var53, var6 * 100.0D + var49, var8 * 100.0D + var55);
            }
         }
      }

      var2.func_78381_a();
   }

   public RenderGlobal(Minecraft p_i1249_1_) {
      this.field_72777_q = p_i1249_1_;
      this.field_72770_i = p_i1249_1_.func_110434_K();
      byte var2 = 26;
      byte var3 = 16;
      this.variableIntTemp[11] = var2;
      this.variableIntTemp[12] = var3;
      if(this.field_72777_q != null) {
         Minecraft var10000 = this.field_72777_q;
         if(Minecraft.field_71444_a != null) {
            var10000 = this.field_72777_q;
            if(Minecraft.field_71444_a.length >= 65536) {
               var10000 = this.field_72777_q;
               Minecraft.field_71444_a['\ufff0'] = 1;
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
               lightRenderer.setLightRenderer(this.field_72777_q);
            }
         }
      } catch (Throwable var11) {
         var11.printStackTrace();
      }

      if(lightRenderer != null) {
         lightRenderer.lightRendererClassErrorCheck();
      }

      this.field_72767_j = new ArrayList();
      this.field_72778_p = GLAllocation.func_74526_a(var2 * var2 * var3 * 3);
      this.field_72774_t = OpenGlCapsChecker.func_74371_a();
      if(this.field_72774_t) {
         this.field_72761_c.clear();
         this.field_72775_s = GLAllocation.func_74527_f(var2 * var2 * var3);
         this.field_72775_s.clear();
         this.field_72775_s.position(0);
         this.field_72775_s.limit(var2 * var2 * var3);
         ARBOcclusionQuery.glGenQueriesARB(this.field_72775_s);
      }

      this.field_72772_v = GLAllocation.func_74526_a(3);
      GL11.glPushMatrix();
      GL11.glNewList(this.field_72772_v, 4864);
      this.func_72730_g();
      GL11.glEndList();
      GL11.glPopMatrix();
      Tessellator var12 = Tessellator.field_78398_a;
      this.field_72771_w = this.field_72772_v + 1;
      GL11.glNewList(this.field_72771_w, 4864);
      byte var13 = 64;
      var6 = 256 / var13 + 2;
      float var7 = 16.0F;

      int var8;
      int var9;
      for(var8 = -var13 * var6; var8 <= var13 * var6; var8 += var13) {
         for(var9 = -var13 * var6; var9 <= var13 * var6; var9 += var13) {
            var12.func_78382_b();
            var12.func_78377_a((double)(var8 + 0), (double)var7, (double)(var9 + 0));
            var12.func_78377_a((double)(var8 + var13), (double)var7, (double)(var9 + 0));
            var12.func_78377_a((double)(var8 + var13), (double)var7, (double)(var9 + var13));
            var12.func_78377_a((double)(var8 + 0), (double)var7, (double)(var9 + var13));
            var12.func_78381_a();
         }
      }

      GL11.glEndList();
      this.field_72781_x = this.field_72772_v + 2;
      GL11.glNewList(this.field_72781_x, 4864);
      var7 = -16.0F;
      var12.func_78382_b();

      for(var8 = -var13 * var6; var8 <= var13 * var6; var8 += var13) {
         for(var9 = -var13 * var6; var9 <= var13 * var6; var9 += var13) {
            var12.func_78377_a((double)(var8 + var13), (double)var7, (double)(var9 + 0));
            var12.func_78377_a((double)(var8 + 0), (double)var7, (double)(var9 + 0));
            var12.func_78377_a((double)(var8 + 0), (double)var7, (double)(var9 + var13));
            var12.func_78377_a((double)(var8 + var13), (double)var7, (double)(var9 + var13));
         }
      }

      var12.func_78381_a();
      GL11.glEndList();
   }

   private void func_72730_g() {
      Random var1 = new Random(10842L);
      Tessellator var2 = Tessellator.field_78398_a;
      var2.func_78382_b();

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
               var2.func_78377_a(var14 + var53, var16 + var49, var18 + var55);
            }
         }
      }

      var2.func_78381_a();
   }

   public void func_72732_a(WorldClient p_72732_1_) {
      if(this.field_72769_h != null) {
         this.field_72769_h.func_72848_b(this);
      }

      this.field_72758_d = -9999.0D;
      this.field_72759_e = -9999.0D;
      this.field_72756_f = -9999.0D;
      RenderManager.field_78727_a.func_78717_a(p_72732_1_);
      this.field_72769_h = p_72732_1_;
      this.field_72776_r = new RenderBlocks(p_72732_1_);
      if(p_72732_1_ != null) {
         p_72732_1_.func_72954_a(this);
         this.func_72712_a();
      }

   }

   public void func_72712_a() {
      if(this.field_72769_h != null) {
         Block.field_71952_K.func_72133_a(this.field_72777_q.field_71474_y.field_74347_j);
         this.field_72739_F = this.field_72777_q.field_71474_y.field_74339_e;
         if(this.field_72765_l != null) {
            for(int var1 = 0; var1 < this.field_72765_l.length; ++var1) {
               this.field_72765_l[var1].func_78911_c();
            }
         }

         if(this.field_72774_t) {
            this.field_72775_s.clear();
         }

         int[] var2 = null;
         if(lightRenderer != null) {
            lightRenderer.firstExecution();
            var2 = lightRenderer.getChunkDepiction(this.variableIntTemp[11], this.variableIntTemp[12], this.field_72739_F);
            this.lightRendererConfig = lightRenderer.lightRendererConfig1(this.lightRendererConfig, '\uffdc', '\uffff');
            lightRenderer.lightRendererClassErrorCheck();
         }

         if(var2 == null) {
            var2 = new int[]{4 << 3 - this.field_72739_F > 21?21:4 << 3 - this.field_72739_F, (3 - this.field_72739_F + 1) * (3 - this.field_72739_F) + 4, this.field_72739_F};
         }

         this.field_72766_m = var2[0];
         this.field_72763_n = var2[1];
         this.field_72764_o = var2[0];
         this.variableFloatTemp[6] = (float)this.lightRendererConfig[31 + var2[2]] <= 0.0F?64.0F:(float)this.lightRendererConfig[31 + var2[2]];
         this.startRenderers();
         this.field_72765_l = new WorldRenderer[this.field_72766_m * this.field_72763_n * this.field_72764_o];
         this.field_72768_k = new WorldRenderer[this.field_72766_m * this.field_72763_n * this.field_72764_o];
         int var3 = 0;
         int var4 = 0;
         this.field_72780_y = 0;
         this.field_72779_z = 0;
         this.field_72741_A = 0;
         this.field_72742_B = this.field_72766_m;
         this.field_72743_C = this.field_72763_n;
         this.field_72737_D = this.field_72764_o;

         int var5;
         for(var5 = 0; var5 < this.field_72767_j.size(); ++var5) {
            ((WorldRenderer)this.field_72767_j.get(var5)).field_78939_q = false;
         }

         this.field_72767_j.clear();
         this.field_72762_a.clear();

         for(var5 = 0; var5 < this.field_72766_m; ++var5) {
            for(int var6 = 0; var6 < this.field_72763_n; ++var6) {
               for(int var7 = 0; var7 < this.field_72764_o; ++var7) {
                  this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5] = new WorldRenderer(this.field_72769_h, this.field_72762_a, var5 * 16, var6 * 16, var7 * 16, this.field_72778_p + var3);
                  if(this.field_72774_t) {
                     this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5].field_78934_v = this.field_72775_s.get(var4);
                  }

                  this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5].field_78935_u = false;
                  this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5].field_78936_t = true;
                  this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5].field_78927_l = true;
                  this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5].field_78937_s = var4++;
                  this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5].func_78914_f();
                  this.field_72768_k[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5] = this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5];
                  this.field_72767_j.add(this.field_72765_l[(var7 * this.field_72763_n + var6) * this.field_72766_m + var5]);
                  var3 += 3;
               }
            }
         }

         if(this.field_72769_h != null) {
            EntityLivingBase var8 = this.field_72777_q.field_71451_h;
            if(var8 != null) {
               this.func_72722_c(MathHelper.func_76128_c(var8.field_70165_t), MathHelper.func_76128_c(var8.field_70163_u), MathHelper.func_76128_c(var8.field_70161_v));
               Arrays.sort(this.field_72768_k, new EntitySorter(var8));
            }
         }

         this.field_72740_G = 2;
      }

   }

   public void func_72713_a(Vec3 p_72713_1_, ICamera p_72713_2_, float p_72713_3_) {
      if(this.field_72740_G > 0) {
         --this.field_72740_G;
      } else {
         this.field_72769_h.field_72984_F.func_76320_a("prepare");
         TileEntityRenderer.field_76963_a.func_76953_a(this.field_72769_h, this.field_72777_q.func_110434_K(), this.field_72777_q.field_71466_p, this.field_72777_q.field_71451_h, p_72713_3_);
         RenderManager.field_78727_a.func_78718_a(this.field_72769_h, this.field_72777_q.func_110434_K(), this.field_72777_q.field_71466_p, this.field_72777_q.field_71451_h, this.field_72777_q.field_96291_i, this.field_72777_q.field_71474_y, p_72713_3_);
         this.field_72748_H = 0;
         this.field_72749_I = 0;
         this.field_72750_J = 0;
         EntityLivingBase var4 = this.field_72777_q.field_71451_h;
         RenderManager.field_78725_b = var4.field_70142_S + (var4.field_70165_t - var4.field_70142_S) * (double)p_72713_3_;
         RenderManager.field_78726_c = var4.field_70137_T + (var4.field_70163_u - var4.field_70137_T) * (double)p_72713_3_;
         RenderManager.field_78723_d = var4.field_70136_U + (var4.field_70161_v - var4.field_70136_U) * (double)p_72713_3_;
         TileEntityRenderer.field_76961_b = var4.field_70142_S + (var4.field_70165_t - var4.field_70142_S) * (double)p_72713_3_;
         TileEntityRenderer.field_76962_c = var4.field_70137_T + (var4.field_70163_u - var4.field_70137_T) * (double)p_72713_3_;
         TileEntityRenderer.field_76959_d = var4.field_70136_U + (var4.field_70161_v - var4.field_70136_U) * (double)p_72713_3_;
         this.field_72777_q.field_71460_t.func_78463_b((double)p_72713_3_);
         this.field_72769_h.field_72984_F.func_76318_c("global");
         List var5 = this.field_72769_h.func_72910_y();
         this.field_72748_H = var5.size();

         for(int var6 = 0; var6 < this.field_72769_h.field_73007_j.size(); ++var6) {
            Entity var7 = (Entity)this.field_72769_h.field_73007_j.get(var6);
            ++this.field_72749_I;
            if(var7.func_70102_a(p_72713_1_)) {
               RenderManager.field_78727_a.func_78720_a(var7, p_72713_3_);
            }
         }

         List var8 = this.field_72769_h.func_72910_y();
         List var9 = this.field_72762_a;
         Entity var10 = null;
         TileEntity var11 = null;
         EntityLiving var12 = null;
         if(this.field_72777_q.field_71474_y.field_82882_x && RenderManager.field_85095_o) {
            --this.field_72749_I;
            --this.field_72750_J;
            this.field_72777_q.field_71460_t.func_78483_a((double)p_72713_3_);
            this.field_72769_h.field_72984_F.func_76319_b();
            return;
         }

         if(this.lightRendererConfig[3] == 2) {
            Object var13 = null;
            RenderManager var14 = RenderManager.field_78727_a;
            TileEntityRenderer var15 = TileEntityRenderer.field_76963_a;
            boolean var16 = false;
            boolean var17 = false;
            boolean var18 = false;
            int var19 = var8 != null && 0 < var8.size()?var8.size():0;
            boolean var20 = false;
            double var21 = RenderManager.field_78725_b;
            double var23 = RenderManager.field_78726_c;
            double var25 = RenderManager.field_78723_d;
            double var27 = 0.0D;
            double var29 = this.lightRendererConfig[4] >= 3?24.0D:576.0D;
            double var31 = (double)this.variableFloatTemp[6];
            boolean var39 = this.lightRendererConfig[4] >= 2;
            boolean var40 = this.variableLongTemp[0] != (long)this.field_72773_u;
            boolean var41 = this.lightRendererConfig[9] == 2 && (this.field_72777_q.field_71474_y.field_74320_O != 0 || this.field_72777_q.field_71451_h.func_70608_bn());
            boolean var42 = false;
            this.field_72769_h.field_72984_F.func_76318_c("entities");
            int var45;
            int var46;
            int var47;
            int var48;
            if(!var40 && var19 == this.objectEntity.size()) {
               this.field_72749_I = this.variableIntTemp[0];
            } else {
               this.objectEntity.clear();
               this.variableLongTemp[0] = (long)this.field_72773_u;
               this.objectEntityLCX = new float[var19];
               this.objectEntityLCY = new float[var19];
               var45 = 0;
               var48 = 0;

               for(var46 = var8.size(); var45 < var46; ++var45) {
                  var42 = (var10 = (Entity)var8.get(var45)).func_70102_a(p_72713_1_) && (var10.field_70158_ak || this.isBoundingBoxInFrustum(var10.field_70121_D));
                  if(!var42 && var10 instanceof EntityLiving && (var12 = (EntityLiving)var10).func_110167_bD() && var12.func_110166_bE() != null) {
                     var42 = p_72713_2_.func_78546_a(var12.func_110166_bE().field_70121_D);
                  }

                  if(var42) {
                     var42 = (var10 != this.field_72777_q.field_71451_h || var41) && this.field_72769_h.func_72899_e(MathHelper.func_76128_c(var10.field_70165_t), 0, MathHelper.func_76128_c(var10.field_70161_v));
                  }

                  double var33;
                  double var35;
                  double var37;
                  if(var39 && var42 && (var33 = var10.field_70165_t - var21) * var33 + (var35 = var10.field_70163_u - var23) * var35 + (var37 = var10.field_70161_v - var25) * var37 > var29) {
                     var42 = this.cubeRayTraceBlocks(var21, var23, var25, var10.field_70121_D);
                  }

                  if(var42) {
                     this.objectEntity.add(var10);
                     var47 = var10.func_70027_ad()?15728880:var10.func_70070_b(p_72713_3_);
                     this.objectEntityLCX[var48] = (float)(var47 % 65536);
                     this.objectEntityLCY[var48] = (float)(var47 / 65536);
                     if(var10.field_70173_aa == 0) {
                        var10.field_70142_S = var10.field_70165_t;
                        var10.field_70137_T = var10.field_70163_u;
                        var10.field_70136_U = var10.field_70161_v;
                     }

                     ++var48;
                  }
               }

               this.field_72749_I = this.variableIntTemp[0] = this.objectEntity.size();
            }

            var45 = 0;

            for(var46 = this.objectEntity.size(); var45 < var46; ++var45) {
               if((var10 = (Entity)this.objectEntity.get(var45)) != null) {
                  OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, this.objectEntityLCX[var45], this.objectEntityLCY[var45]);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  var14.func_78719_a(var10, var10.field_70142_S + (var10.field_70165_t - var10.field_70142_S) * (double)p_72713_3_ - var21, var10.field_70137_T + (var10.field_70163_u - var10.field_70137_T) * (double)p_72713_3_ - var23, var10.field_70136_U + (var10.field_70161_v - var10.field_70136_U) * (double)p_72713_3_ - var25, var10.field_70126_B + (var10.field_70177_z - var10.field_70126_B) * p_72713_3_, p_72713_3_);
               }
            }

            this.field_72769_h.field_72984_F.func_76318_c("tileentities");
            var19 = var9 != null && 0 < var9.size()?var9.size():0;
            var29 = this.lightRendererConfig[4] >= 3?16.0D:256.0D;
            if(!var40 && var19 == this.objectTileEntity.size()) {
               this.field_72750_J = this.variableIntTemp[1];
            } else {
               this.objectTileEntity.clear();
               this.variableLongTemp[0] = (long)this.field_72773_u;
               this.objectTileEntityLCX = new float[var19];
               this.objectTileEntityLCY = new float[var19];
               var45 = 0;
               var48 = 0;

               for(var46 = var9.size(); var45 < var46; ++var45) {
                  var42 = (var27 = (var11 = (TileEntity)var9.get(var45)).func_70318_a(var21, var23, var25) * 1.778D) < (var11.func_82115_m() <= 4096.0D?var31:var11.func_82115_m()) && this.isBoxInFrustum((double)var11.field_70329_l - 1.0D, (double)var11.field_70330_m - 1.0D, (double)var11.field_70327_n - 1.0D, (double)var11.field_70329_l + 2.0D, (double)var11.field_70330_m + ((var41 = var11.func_82115_m() > 4096.0D)?128.0D:2.0D), (double)var11.field_70327_n + 2.0D);
                  if(var39 && var42 && !var41 && var27 > var29) {
                     var42 = this.rayTraceBlocks(var21, var23, var25, (double)var11.field_70329_l, (double)var11.field_70330_m, (double)var11.field_70327_n, (double)var11.field_70329_l + 1.0D, (double)var11.field_70330_m + 1.0D, (double)var11.field_70327_n + 1.0D);
                  }

                  if(var42) {
                     this.objectTileEntity.add(var11);
                     var47 = this.field_72769_h.func_72802_i(var11.field_70329_l, var11.field_70330_m, var11.field_70327_n, 0);
                     this.objectTileEntityLCX[var48] = (float)(var47 % 65536);
                     this.objectTileEntityLCY[var48] = (float)(var47 / 65536);
                     ++var48;
                  }
               }

               this.field_72750_J = this.variableIntTemp[1] = this.objectTileEntity.size();
            }

            var45 = 0;

            for(var46 = this.objectTileEntity.size(); var45 < var46; ++var45) {
               if((var11 = (TileEntity)this.objectTileEntity.get(var45)) != null) {
                  OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, this.objectTileEntityLCX[var45], this.objectTileEntityLCY[var45]);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  var15.func_76949_a(var11, (double)var11.field_70329_l - var21, (double)var11.field_70330_m - var23, (double)var11.field_70327_n - var25, p_72713_3_);
               }
            }
         } else {
            boolean var43 = false;
            this.field_72769_h.field_72984_F.func_76318_c("entities");

            int var44;
            for(var44 = 0; var44 < var8.size(); ++var44) {
               var43 = (var10 = (Entity)var8.get(var44)).func_70102_a(p_72713_1_) && (var10.field_70158_ak || p_72713_2_.func_78546_a(var10.field_70121_D) || var10.field_70153_n == this.field_72777_q.field_71439_g);
               if(!var43 && var10 instanceof EntityLiving && (var12 = (EntityLiving)var10).func_110167_bD() && var12.func_110166_bE() != null) {
                  var43 = p_72713_2_.func_78546_a(var12.func_110166_bE().field_70121_D);
               }

               if(var43 && (var10 != this.field_72777_q.field_71451_h || this.field_72777_q.field_71474_y.field_74320_O != 0 || this.field_72777_q.field_71451_h.func_70608_bn()) && this.field_72769_h.func_72899_e(MathHelper.func_76128_c(var10.field_70165_t), 0, MathHelper.func_76128_c(var10.field_70161_v))) {
                  ++this.field_72749_I;
                  RenderManager.field_78727_a.func_78720_a(var10, p_72713_3_);
               }
            }

            this.field_72769_h.field_72984_F.func_76318_c("tileentities");

            for(var44 = 0; var44 < var9.size(); ++var44) {
               TileEntityRenderer.field_76963_a.func_76950_a((TileEntity)var9.get(var44), p_72713_3_);
            }
         }

         this.field_72777_q.field_71460_t.func_78483_a((double)p_72713_3_);
         this.field_72769_h.field_72984_F.func_76319_b();
      }

   }

   public String func_72735_c() {
      return "C: " + this.field_72746_N + "/" + this.field_72751_K + ". F: " + this.field_72744_L + ", O: " + this.field_72745_M + ", E: " + this.field_72747_O;
   }

   public String func_72723_d() {
      return "E: " + this.field_72749_I + "/" + this.field_72748_H + ". B: " + this.field_72750_J + ", I: " + (this.field_72748_H - this.field_72750_J - this.field_72749_I);
   }

   private void func_72722_c(int p_72722_1_, int p_72722_2_, int p_72722_3_) {
      p_72722_1_ -= 8;
      p_72722_2_ -= 8;
      p_72722_3_ -= 8;
      short var4 = 256;
      int var5 = this.field_72763_n * 16;
      int var6 = var5 / 2 > var4 / 2?var4 / 2:var5 / 2;
      int var7 = var4 - var5 / 2 < var4 / 2?var4 / 2:var4 - var5 / 2;
      int var8 = p_72722_2_ < var6?var6:(p_72722_2_ > var7?var7:p_72722_2_);
      int var9 = this.field_72766_m * 16;
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

      for(int var21 = 0; var21 < this.field_72763_n; ++var21) {
         int var22;
         int var23;
         if((var22 = (var23 = var21 * 16) + var5 / 2 - var8) < 0) {
            var22 -= var5 - 1;
         }

         var23 -= var22 / var5 * var5;

         for(int var24 = 0; var24 < this.field_72766_m; ++var24) {
            int var25;
            int var26;
            if((var25 = (var26 = var24 * 16) + var10 - p_72722_1_) < 0) {
               var25 -= var9 - 1;
            }

            var26 -= var25 / var9 * var9;

            for(int var27 = 0; var27 < this.field_72764_o; ++var27) {
               int var28;
               int var29;
               if((var28 = (var29 = var27 * 16) + var10 - p_72722_3_) < 0) {
                  var28 -= var9 - 1;
               }

               var29 -= var28 / var9 * var9;
               var20 = !(var19 = this.field_72765_l[(var27 * this.field_72763_n + var21) * this.field_72766_m + var24]).field_78939_q;
               var19.func_78913_a(var26, var23, var29);
               if(var20 && var19.field_78939_q) {
                  this.field_72767_j.add(var19);
               }
            }
         }
      }

   }

   public int func_72719_a(EntityLivingBase p_72719_1_, int p_72719_2_, double p_72719_3_) {
      if(this.field_72777_q.field_71474_y.field_74339_e != this.field_72739_F) {
         this.func_72712_a();
      }

      Profiler var5 = this.field_72769_h.field_72984_F;
      var5.func_76320_a("sortchunks");
      WorldRenderer var6 = null;
      int var7 = 0;

      int var8;
      for(var8 = this.field_72767_j.size(); var7 < var8; ++var7) {
         if((WorldRenderer)this.field_72767_j.get(var7) == null) {
            this.field_72767_j.remove(var7--);
            --var8;
         }
      }

      boolean var40 = false;
      var8 = MathHelper.func_76128_c(Math.sqrt((double)(this.variableIntTemp[13] / 5)) + 1.0D);
      int var9 = this.variableIntTemp[13];

      for(var7 = 0; var7 < var8; ++var7) {
         var6 = this.field_72765_l[this.field_72752_Q];
         this.field_72752_Q = (this.field_72752_Q + 1) % this.field_72765_l.length;
         if(var6.field_78939_q && !this.field_72767_j.contains(var6)) {
            this.field_72767_j.add(var6);
         }
      }

      if(p_72719_2_ == 0) {
         this.field_72751_K = this.variableIntTemp[13];
         this.field_72753_P = this.field_72744_L = this.field_72745_M = this.field_72746_N = this.field_72747_O = 0;
      }

      double var10 = p_72719_1_.field_70165_t;
      double var12 = p_72719_1_.field_70163_u;
      double var14 = p_72719_1_.field_70161_v;
      double var16 = var10 - this.field_72758_d;
      double var18 = var12 - this.field_72759_e;
      double var20 = var14 - this.field_72756_f;
      double var22 = (double)((float)(p_72719_1_.field_70142_S + (var10 - p_72719_1_.field_70142_S) * p_72719_3_));
      double var24 = (double)((float)(p_72719_1_.field_70137_T + (var12 - p_72719_1_.field_70137_T) * p_72719_3_));
      double var26 = (double)((float)(p_72719_1_.field_70136_U + (var14 - p_72719_1_.field_70136_U) * p_72719_3_));
      double var10000 = var16 * var16 + var18 * var18 + var20 * var20;
      var40 = true;
      boolean var28;
      if(var10000 > (double)16) {
         var7 = (4 - this.field_72739_F) * 4;
         if(var7 < 4) {
            var7 = 4;
         }

         var28 = (var16 = (this.field_72758_d = var10) - this.prevReposX) * var16 + (var18 = (this.field_72759_e = var12) - this.prevReposY) * var18 + (var20 = (this.field_72756_f = var14) - this.prevReposZ) * var20 > (double)(var7 * var7);
         if(var28) {
            this.func_72722_c(MathHelper.func_76128_c(this.prevReposX = var10), MathHelper.func_76128_c(this.prevReposY = var12), MathHelper.func_76128_c(this.prevReposZ = var14));
         }

         ++this.variableIntTemp[9];
         Arrays.sort(this.field_72768_k, new EntitySorter(p_72719_1_));
         if(var28 && !this.variableBooleanTemp[2]) {
            this.worldRenderersList.clear();

            for(var7 = 0; var7 < var9; ++var7) {
               if(!this.field_72768_k[var7].func_78906_e()) {
                  this.worldRenderersList.add(this.field_72768_k[var7]);
               }
            }
         }
      }

      RenderHelper.func_74518_a();
      var28 = false;
      var5.func_76318_c("render");
      if(this.variableBooleanTemp[5] && p_72719_2_ == 0) {
         boolean var29 = false;
         byte var30 = 0;
         int var31 = 20;
         int var32 = 0;
         boolean var33 = false;

         for(var7 = var30; var7 < var31; ++var7) {
            this.field_72768_k[var7].field_78936_t = true;
         }

         int var42;
         for(var8 = this.func_72724_a(var30, var31, p_72719_2_, p_72719_3_); var31 < var9; var8 += this.func_72724_a(var42, var31, p_72719_2_, p_72719_3_)) {
            var5.func_76318_c("occ");
            var42 = var31;
            if(var32 < this.field_72766_m) {
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
            this.field_72755_R.clear();
            float var34 = 0.0F;
            float var35 = 0.0F;
            float var36 = 0.0F;
            float var37 = 0.0F;
            float var38 = 0.0F;
            float var39 = 0.0F;
            int var41 = var42;

            for(int var43 = var31; var41 < var43; ++var41) {
               if((var6 = this.field_72768_k[var41]).field_78935_u) {
                  if(ARBOcclusionQuery.glGetQueryObjectiARB(var6.field_78934_v, '\u8867') != 0) {
                     var6.field_78935_u = false;
                     --var41;
                     var6.field_78936_t = ARBOcclusionQuery.glGetQueryObjectiARB(var6.field_78934_v, '\u8866') > 0;
                  }
               } else if(var6.field_78927_l && this.field_72773_u % (var7 = (int)(1.0F + MathHelper.func_76129_c(var6.func_78912_a(p_72719_1_)) / 128.0F)) == var41 % var7) {
                  if((float)((double)var6.field_78919_g - var24) - var38 != 0.0F || (float)((double)var6.field_78918_f - var22) - var37 != 0.0F || (float)((double)var6.field_78931_h - var26) - var39 != 0.0F) {
                     GL11.glTranslatef(var34 = (float)((double)var6.field_78918_f - var22) - var37, var35 = (float)((double)var6.field_78919_g - var24) - var38, var36 = (float)((double)var6.field_78931_h - var26) - var39);
                     var37 += var34;
                     var38 += var35;
                     var39 += var36;
                  }

                  ARBOcclusionQuery.glBeginQueryARB('\u8914', var6.field_78934_v);
                  var6.func_78904_d();
                  ARBOcclusionQuery.glEndQueryARB('\u8914');
                  var6.field_78935_u = true;
               }
            }

            GL11.glPopMatrix();
            if(this.field_72777_q.field_71474_y.field_74337_g) {
               var28 = EntityRenderer.field_78515_b == 0;
               GL11.glColorMask(!var28, var28, var28, true);
            } else {
               GL11.glColorMask(true, true, true, true);
            }

            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glEnable(2912);
            var5.func_76318_c("render");
         }
      } else {
         var8 = this.func_72724_a(0, var9, p_72719_2_, p_72719_3_);
      }

      var5.func_76319_b();
      return var8;
   }

   private void checkOcclusionQueryResult(int startIndex, int endIndex, double double1, double double2, double double3) {}

   private int func_72724_a(int p_72724_1_, int p_72724_2_, int p_72724_3_, double p_72724_4_) {
      int var6 = this.variableIntTemp[13];
      int var7 = this.field_72746_N;
      boolean var8 = false;
      boolean var9 = false;
      if(p_72724_3_ >= 0 && p_72724_3_ <= 1 && p_72724_2_ - p_72724_1_ > 0) {
         if(p_72724_2_ > var6) {
            p_72724_2_ = var6;
         }

         if(p_72724_1_ < 0) {
            p_72724_1_ = 0;
         }

         this.field_72769_h.field_72984_F.func_76320_a("ChankBuilder");
         boolean var10 = p_72724_3_ == 0 && (this.variableBooleanTemp[5] || this.variableLongTemp[3] <= System.currentTimeMillis());
         boolean var11 = p_72724_3_ == 0 && (this.variableBooleanTemp[5] || var10 && this.field_72777_q.field_71474_y.field_74330_P);
         if(var10) {
            this.variableLongTemp[3] = System.currentTimeMillis() + (long)this.lightRendererConfig[5];
         }

         WorldRenderer var12 = null;
         if(p_72724_3_ < 1 || this.variableIntTemp[14] < 1) {
            this.field_72755_R.clear();
            if(!this.variableBooleanTemp[2]) {
               int var26;
               int var27;
               if(var10) {
                  var26 = 0;

                  for(var27 = this.worldRenderersList.size(); var26 < var27; ++var26) {
                     if(((var12 = (WorldRenderer)this.worldRenderersList.get(var26)).field_78927_l = this.isBoundingBoxInFrustum(var12.field_78938_r)) && !var12.field_78928_m[0]) {
                        this.field_72755_R.add(var12);
                     }
                  }
               } else {
                  var26 = 0;

                  for(var27 = this.worldRenderersList.size(); var26 < var27; ++var26) {
                     if((var12 = (WorldRenderer)this.worldRenderersList.get(var26)).field_78927_l && !var12.field_78928_m[p_72724_3_]) {
                        this.field_72755_R.add(var12);
                     }
                  }
               }
            } else if(var10) {
               for(var6 = p_72724_1_; var6 < p_72724_2_; ++var6) {
                  if(!this.field_72768_k[var6].func_78906_e()) {
                     if((this.field_72768_k[var6].field_78927_l = this.isBoundingBoxInFrustum(this.field_72768_k[var6].field_78938_r)) && !this.field_72768_k[var6].field_78928_m[0]) {
                        if(this.field_72768_k[var6].field_78936_t) {
                           this.field_72755_R.add(this.field_72768_k[var6]);
                        } else {
                           ++this.field_72745_M;
                        }
                     } else {
                        ++this.field_72744_L;
                     }
                  }
               }
            } else {
               for(var6 = p_72724_1_; var6 < p_72724_2_; ++var6) {
                  if(this.field_72768_k[var6].field_78927_l && !this.field_72768_k[var6].field_78928_m[p_72724_3_] && this.field_72768_k[var6].field_78936_t) {
                     this.field_72755_R.add(this.field_72768_k[var6]);
                  }
               }
            }

            this.variableIntTemp[15] = this.field_72755_R.size();
            if(var10) {
               this.field_72746_N += this.variableIntTemp[15];
               if(!this.variableBooleanTemp[2]) {
                  this.field_72744_L = this.worldRenderersList.size() - this.field_72746_N;
               }
            }
         }

         this.variableIntTemp[14] = p_72724_3_;
         int var13 = this.field_72746_N - var7 >= 0?this.field_72746_N - var7:0;
         if(var10) {
            this.variableIntTemp[6] = var13;
         } else {
            var13 = p_72724_3_ == 0 && !this.variableBooleanTemp[5]?this.variableIntTemp[6] - var7:1;
         }

         ++var13;
         if(var11) {
            this.variableIntTemp[2] = this.field_72747_O = this.field_72751_K - this.field_72744_L - this.field_72745_M - this.field_72746_N;
            this.variableIntTemp[3] = this.field_72744_L;
            this.variableIntTemp[4] = this.field_72745_M;
            this.variableIntTemp[5] = this.field_72746_N;
         } else {
            this.field_72747_O = this.variableIntTemp[2];
            this.field_72744_L = this.variableIntTemp[3];
            this.field_72745_M = this.variableIntTemp[4];
            this.field_72746_N = this.variableIntTemp[5];
         }

         this.field_72769_h.field_72984_F.func_76319_b();
         EntityLivingBase var14 = this.field_72777_q.field_71451_h;
         double var15 = var14.field_70142_S + (var14.field_70165_t - var14.field_70142_S) * p_72724_4_;
         double var17 = var14.field_70137_T + (var14.field_70163_u - var14.field_70137_T) * p_72724_4_;
         double var19 = var14.field_70136_U + (var14.field_70161_v - var14.field_70136_U) * p_72724_4_;
         int var21 = 0;

         int var22;
         for(var22 = 0; var22 < this.field_72754_S.length; ++var22) {
            this.field_72754_S[var22].func_78421_b();
         }

         for(var22 = 0; var22 < this.field_72755_R.size(); ++var22) {
            WorldRenderer var23 = (WorldRenderer)this.field_72755_R.get(var22);
            int var24 = -1;

            for(int var25 = 0; var25 < var21; ++var25) {
               if(this.field_72754_S[var25].func_78418_a(var23.field_78918_f, var23.field_78919_g, var23.field_78931_h)) {
                  var24 = var25;
               }
            }

            if(var24 < 0) {
               var24 = var21++;
               this.field_72754_S[var24].func_78422_a(var23.field_78918_f, var23.field_78919_g, var23.field_78931_h, var15, var17, var19);
            }

            this.field_72754_S[var24].func_78420_a(var23.func_78909_a(p_72724_3_));
         }

         this.func_72733_a(p_72724_3_, p_72724_4_);
         return var13;
      } else {
         return 0;
      }
   }

   public void func_72733_a(int p_72733_1_, double p_72733_2_) {
      this.field_72777_q.field_71460_t.func_78463_b(p_72733_2_);

      for(int var4 = 0; var4 < this.field_72754_S.length; ++var4) {
         this.field_72754_S[var4].func_78419_a();
      }

      this.field_72777_q.field_71460_t.func_78483_a(p_72733_2_);
   }

   public void func_72734_e() {
      ++this.field_72773_u;
      if(this.field_72773_u % 20 == 0) {
         Iterator var1 = this.field_72738_E.values().iterator();

         while(var1.hasNext()) {
            DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
            int var3 = var2.func_82743_f();
            if(this.field_72773_u - var3 > 400) {
               var1.remove();
            }
         }
      }

   }

   public void func_72714_a(float p_72714_1_) {
      if(this.field_72777_q.field_71441_e.field_73011_w.field_76574_g == 1) {
         GL11.glDisable(2912);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         RenderHelper.func_74518_a();
         GL11.glDepthMask(false);
         this.field_72770_i.func_110577_a(field_110926_k);
         Tessellator var2 = Tessellator.field_78398_a;

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

            var2.func_78382_b();
            var2.func_78378_d(2631720);
            var2.func_78374_a(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
            var2.func_78374_a(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
            var2.func_78374_a(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
            var2.func_78374_a(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
            var2.func_78381_a();
            GL11.glPopMatrix();
         }

         GL11.glDepthMask(true);
         GL11.glEnable(3553);
         GL11.glEnable(3008);
      } else if(this.field_72777_q.field_71441_e.field_73011_w.func_76569_d()) {
         GL11.glDisable(3553);
         Vec3 var23 = this.field_72769_h.func_72833_a(this.field_72777_q.field_71451_h, p_72714_1_);
         float var24 = (float)var23.field_72450_a;
         float var4 = (float)var23.field_72448_b;
         float var5 = (float)var23.field_72449_c;
         float var6;
         if(this.field_72777_q.field_71474_y.field_74337_g) {
            float var7 = (var24 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
            float var8 = (var24 * 30.0F + var4 * 70.0F) / 100.0F;
            var6 = (var24 * 30.0F + var5 * 70.0F) / 100.0F;
            var24 = var7;
            var4 = var8;
            var5 = var6;
         }

         GL11.glColor3f(var24, var4, var5);
         Tessellator var25 = Tessellator.field_78398_a;
         GL11.glDepthMask(false);
         GL11.glEnable(2912);
         GL11.glColor3f(var24, var4, var5);
         GL11.glCallList(this.field_72771_w);
         GL11.glDisable(2912);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         RenderHelper.func_74518_a();
         float[] var26 = this.field_72769_h.field_73011_w.func_76560_a(this.field_72769_h.func_72826_c(p_72714_1_), p_72714_1_);
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
            GL11.glRotatef(MathHelper.func_76126_a(this.field_72769_h.func_72929_e(p_72714_1_)) < 0.0F?180.0F:0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            var6 = var26[0];
            var9 = var26[1];
            var10 = var26[2];
            float var13;
            if(this.field_72777_q.field_71474_y.field_74337_g) {
               var11 = (var6 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
               var12 = (var6 * 30.0F + var9 * 70.0F) / 100.0F;
               var13 = (var6 * 30.0F + var10 * 70.0F) / 100.0F;
               var6 = var11;
               var9 = var12;
               var10 = var13;
            }

            var25.func_78371_b(6);
            var25.func_78369_a(var6, var9, var10, var26[3]);
            var25.func_78377_a(0.0D, 100.0D, 0.0D);
            byte var14 = 16;
            var25.func_78369_a(var26[0], var26[1], var26[2], 0.0F);

            for(var15 = 0; var15 <= var14; ++var15) {
               var13 = (float)var15 * 3.1415927F * 2.0F / (float)var14;
               var16 = MathHelper.func_76126_a(var13);
               var17 = MathHelper.func_76134_b(var13);
               var25.func_78377_a((double)(var16 * 120.0F), (double)(var17 * 120.0F), (double)(-var17 * 40.0F * var26[3]));
            }

            var25.func_78381_a();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
         }

         GL11.glEnable(3553);
         GL11.glBlendFunc(770, 1);
         GL11.glPushMatrix();
         var6 = 1.0F - this.field_72769_h.func_72867_j(p_72714_1_);
         var9 = 0.0F;
         var10 = 0.0F;
         var11 = 0.0F;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var6);
         GL11.glTranslatef(var9, var10, var11);
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.field_72769_h.func_72826_c(p_72714_1_) * 360.0F, 1.0F, 0.0F, 0.0F);
         var12 = 30.0F;
         this.field_72770_i.func_110577_a(field_110928_i);
         var25.func_78382_b();
         var25.func_78374_a((double)(-var12), 100.0D, (double)(-var12), 0.0D, 0.0D);
         var25.func_78374_a((double)var12, 100.0D, (double)(-var12), 1.0D, 0.0D);
         var25.func_78374_a((double)var12, 100.0D, (double)var12, 1.0D, 1.0D);
         var25.func_78374_a((double)(-var12), 100.0D, (double)var12, 0.0D, 1.0D);
         var25.func_78381_a();
         var12 = 20.0F;
         this.field_72770_i.func_110577_a(field_110927_h);
         int var27 = this.field_72769_h.func_72853_d();
         int var28 = var27 % 4;
         var15 = var27 / 4 % 2;
         var16 = (float)(var28 + 0) / 4.0F;
         var17 = (float)(var15 + 0) / 2.0F;
         float var18 = (float)(var28 + 1) / 4.0F;
         float var19 = (float)(var15 + 1) / 2.0F;
         var25.func_78382_b();
         var25.func_78374_a((double)(-var12), -100.0D, (double)var12, (double)var18, (double)var19);
         var25.func_78374_a((double)var12, -100.0D, (double)var12, (double)var16, (double)var19);
         var25.func_78374_a((double)var12, -100.0D, (double)(-var12), (double)var16, (double)var17);
         var25.func_78374_a((double)(-var12), -100.0D, (double)(-var12), (double)var18, (double)var17);
         var25.func_78381_a();
         GL11.glDisable(3553);
         float var20 = this.field_72769_h.func_72880_h(p_72714_1_) * var6;
         if(var20 > 0.0F) {
            GL11.glColor4f(var20, var20, var20, var20);
            GL11.glCallList(this.field_72772_v);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(3008);
         GL11.glEnable(2912);
         GL11.glPopMatrix();
         GL11.glDisable(3553);
         GL11.glColor3f(0.0F, 0.0F, 0.0F);
         double var21 = this.field_72777_q.field_71439_g.func_70666_h(p_72714_1_).field_72448_b - this.field_72769_h.func_72919_O();
         if(var21 < 0.0D) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.field_72781_x);
            GL11.glPopMatrix();
            var10 = 1.0F;
            var11 = -((float)(var21 + 65.0D));
            var12 = -var10;
            var25.func_78382_b();
            var25.func_78384_a(0, 255);
            var25.func_78377_a((double)(-var10), (double)var11, (double)var10);
            var25.func_78377_a((double)var10, (double)var11, (double)var10);
            var25.func_78377_a((double)var10, (double)var12, (double)var10);
            var25.func_78377_a((double)(-var10), (double)var12, (double)var10);
            var25.func_78377_a((double)(-var10), (double)var12, (double)(-var10));
            var25.func_78377_a((double)var10, (double)var12, (double)(-var10));
            var25.func_78377_a((double)var10, (double)var11, (double)(-var10));
            var25.func_78377_a((double)(-var10), (double)var11, (double)(-var10));
            var25.func_78377_a((double)var10, (double)var12, (double)(-var10));
            var25.func_78377_a((double)var10, (double)var12, (double)var10);
            var25.func_78377_a((double)var10, (double)var11, (double)var10);
            var25.func_78377_a((double)var10, (double)var11, (double)(-var10));
            var25.func_78377_a((double)(-var10), (double)var11, (double)(-var10));
            var25.func_78377_a((double)(-var10), (double)var11, (double)var10);
            var25.func_78377_a((double)(-var10), (double)var12, (double)var10);
            var25.func_78377_a((double)(-var10), (double)var12, (double)(-var10));
            var25.func_78377_a((double)(-var10), (double)var12, (double)(-var10));
            var25.func_78377_a((double)(-var10), (double)var12, (double)var10);
            var25.func_78377_a((double)var10, (double)var12, (double)var10);
            var25.func_78377_a((double)var10, (double)var12, (double)(-var10));
            var25.func_78381_a();
         }

         if(this.field_72769_h.field_73011_w.func_76561_g()) {
            GL11.glColor3f(var24 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
         } else {
            GL11.glColor3f(var24, var4, var5);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, -((float)(var21 - 16.0D)), 0.0F);
         GL11.glCallList(this.field_72781_x);
         GL11.glPopMatrix();
         GL11.glEnable(3553);
         GL11.glDepthMask(true);
      }

   }

   public void func_72718_b(float p_72718_1_) {
      if(this.field_72777_q.field_71441_e.field_73011_w.func_76569_d()) {
         float var2 = (float)(this.field_72777_q.field_71451_h.field_70137_T + (this.field_72777_q.field_71451_h.field_70163_u - this.field_72777_q.field_71451_h.field_70137_T) * (double)p_72718_1_);
         float var3 = this.field_72769_h.field_73011_w.func_76571_f() - var2 + 0.33F;
         double var4 = this.field_72777_q.field_71451_h.field_70169_q + (this.field_72777_q.field_71451_h.field_70165_t - this.field_72777_q.field_71451_h.field_70169_q) * (double)p_72718_1_ + (double)((float)this.field_72773_u + p_72718_1_) * 0.029999999329447746D;
         double var6 = this.field_72777_q.field_71451_h.field_70166_s + (this.field_72777_q.field_71451_h.field_70161_v - this.field_72777_q.field_71451_h.field_70166_s) * (double)p_72718_1_;
         int var8 = 12 << 3 - this.field_72777_q.field_71474_y.field_74339_e;
         boolean var9 = false;
         boolean var10 = false;
         boolean var11 = false;
         float var12 = 0.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = 0.0F;
         float var16 = 0.0F;
         float var17 = 0.0F;
         Tessellator var18 = Tessellator.field_78398_a;
         Vec3 var19 = this.field_72769_h.func_72824_f(p_72718_1_);
         float var20 = (float)var19.field_72450_a;
         float var21 = (float)var19.field_72448_b;
         float var22 = (float)var19.field_72449_c;
         if(this.field_72777_q.field_71474_y.field_74337_g) {
            var20 = (var20 * 30.0F + var21 * 59.0F + var22 * 11.0F) / 100.0F;
            var21 = (var20 * 30.0F + var21 * 70.0F) / 100.0F;
            var22 = (var20 * 30.0F + var22 * 70.0F) / 100.0F;
         }

         GL11.glDisable(2884);
         this.field_72770_i.func_110577_a(field_110925_j);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         float var23;
         float var24;
         float var25;
         float var26;
         if(!this.field_72777_q.field_71474_y.field_74347_j) {
            var4 -= (double)(MathHelper.func_76128_c(var4 / 2048.0D) * 2048);
            var6 -= (double)(MathHelper.func_76128_c(var6 / 2048.0D) * 2048);
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
               var18.func_78382_b();
               var18.func_78369_a(var20, var21, var22, 0.8F);
               var18.func_78374_a((double)var23, (double)var3, (double)(var26 + (float)var8), (double)(var23 * var12 + var13), (double)((var26 + (float)var8) * var12 + var14));
               var18.func_78374_a((double)(var25 + (float)var8), (double)var3, (double)(var26 + (float)var8), (double)((var25 + (float)var8) * var12 + var13), (double)((var26 + (float)var8) * var12 + var14));
               var18.func_78374_a((double)(var25 + (float)var8), (double)var3, (double)var24, (double)((var25 + (float)var8) * var12 + var13), (double)(var24 * var12 + var14));
               var18.func_78374_a((double)var23, (double)var3, (double)var24, (double)(var23 * var12 + var13), (double)(var24 * var12 + var14));
               var18.func_78381_a();
               GL11.glEndList();
            }
         } else {
            var12 = 12.0F;
            var4 /= (double)var12;
            var6 = var6 / (double)var12 + 0.33000001311302185D;
            var4 -= (double)(MathHelper.func_76128_c(var4 / 2048.0D) * 2048);
            var6 -= (double)(MathHelper.func_76128_c(var6 / 2048.0D) * 2048);
            GL11.glScalef(var12, 1.0F, var12);
            if(var20 - this.variableFloatTemp[3] <= 0.01F && var20 - this.variableFloatTemp[3] > -0.01F && var21 - this.variableFloatTemp[4] <= 0.01F && var21 - this.variableFloatTemp[4] > -0.01F && var22 - this.variableFloatTemp[5] <= 0.01F && var22 - this.variableFloatTemp[5] > -0.01F && (float)var4 - this.variableFloatTemp[0] <= 0.346F && (float)var4 - this.variableFloatTemp[0] > -0.346F && var3 - this.variableFloatTemp[1] <= 3.46F && var3 - this.variableFloatTemp[1] > -3.46F && (float)var6 - this.variableFloatTemp[2] <= 0.346F && (float)var6 - this.variableFloatTemp[2] > -0.346F) {
               GL11.glTranslatef(this.variableFloatTemp[0] - (float)var4, var3 - this.variableFloatTemp[1], this.variableFloatTemp[2] - (float)var6);
               GL11.glCallList(this.variableIntTemp[7]);
            } else {
               var13 = 4.0F;
               var14 = 0.00390625F;
               var15 = 9.765625E-4F;
               var16 = (float)MathHelper.func_76128_c(var4) * var14;
               var17 = (float)MathHelper.func_76128_c(var6) * var14;
               this.variableFloatTemp[0] = (float)var4;
               this.variableFloatTemp[1] = var3;
               this.variableFloatTemp[2] = (float)var6;
               this.variableFloatTemp[3] = var20;
               this.variableFloatTemp[4] = var21;
               this.variableFloatTemp[5] = var22;
               var23 = (float)var8 / -2.0F;
               var24 = (float)var8 / -2.0F;
               var25 = var23 - (float)(var4 - (double)MathHelper.func_76128_c(var4));
               var26 = var24 - (float)(var6 - (double)MathHelper.func_76128_c(var6));
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
                  } else if(this.field_72777_q.field_71474_y.field_74337_g) {
                     if(EntityRenderer.field_78515_b == 0) {
                        GL11.glColorMask(false, true, true, true);
                     } else {
                        GL11.glColorMask(true, false, false, true);
                     }
                  } else {
                     GL11.glColorMask(true, true, true, true);
                  }

                  var18.func_78382_b();
                  if(var3 > -var13 - 4.0F) {
                     var18.func_78369_a(var20 * 0.7F, var21 * 0.7F, var22 * 0.7F, 0.8F);
                     var18.func_78375_b(0.0F, -1.0F, 0.0F);
                     var18.func_78374_a((double)var25, (double)var3, var27, var31, var37);
                     var18.func_78374_a((double)(var25 + (float)var8), (double)var3, var27, var35, var37);
                     var18.func_78374_a((double)(var25 + (float)var8), (double)var3, (double)var26, var35, var33);
                     var18.func_78374_a((double)var25, (double)var3, (double)var26, var31, var33);
                  }

                  if(var3 <= var13 + 4.0F) {
                     var18.func_78369_a(var20, var21, var22, 0.8F);
                     var18.func_78375_b(0.0F, 1.0F, 0.0F);
                     var18.func_78374_a((double)var25, var29, var27, var31, var37);
                     var18.func_78374_a((double)(var25 + (float)var8), var29, var27, var35, var37);
                     var18.func_78374_a((double)(var25 + (float)var8), var29, (double)var26, var35, var33);
                     var18.func_78374_a((double)var25, var29, (double)var26, var31, var33);
                  }

                  var18.func_78369_a(var20 * 0.9F, var21 * 0.9F, var22 * 0.9F, 0.8F);
                  var18.func_78375_b(-1.0F, 0.0F, 0.0F);
                  int var41 = 0;

                  int var40;
                  for(var40 = (int)((double)var8 * 0.5D) + -1; var41 < var8; ++var41) {
                     if(var41 > var40) {
                        var18.func_78374_a((double)(var25 + (float)var41), (double)var3, var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.func_78374_a((double)(var25 + (float)var41), (double)(var3 + var13), var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.func_78374_a((double)(var25 + (float)var41), (double)(var3 + var13), (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                        var18.func_78374_a((double)(var25 + (float)var41), (double)var3, (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                     }
                  }

                  var18.func_78375_b(1.0F, 0.0F, 0.0F);
                  var41 = 0;

                  for(var40 = (int)((double)var8 * 0.5D) + 1; var41 < var8; ++var41) {
                     if(var41 <= var40) {
                        var18.func_78374_a((double)(var25 + (float)var41 + 1.0F - var15), (double)var3, var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.func_78374_a((double)(var25 + (float)var41 + 1.0F - var15), (double)(var3 + var13), var27, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var37);
                        var18.func_78374_a((double)(var25 + (float)var41 + 1.0F - var15), (double)(var3 + var13), (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                        var18.func_78374_a((double)(var25 + (float)var41 + 1.0F - var15), (double)var3, (double)var26, (double)((var23 + (float)var41 + 0.5F) * var14 + var16), var33);
                     }
                  }

                  var18.func_78369_a(var20 * 0.8F, var21 * 0.8F, var22 * 0.8F, 0.8F);
                  var18.func_78375_b(0.0F, 0.0F, -1.0F);
                  var41 = 0;

                  for(var40 = (int)((double)var8 * 0.5D) + -1; var41 < var8; ++var41) {
                     if(var41 > var40) {
                        var18.func_78374_a((double)var25, (double)(var3 + var13), (double)(var26 + (float)var41), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.func_78374_a((double)(var25 + (float)var8), (double)(var3 + var13), (double)(var26 + (float)var41), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.func_78374_a((double)(var25 + (float)var8), (double)var3, (double)(var26 + (float)var41), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.func_78374_a((double)var25, (double)var3, (double)(var26 + (float)var41), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                     }
                  }

                  var18.func_78375_b(0.0F, 0.0F, 1.0F);
                  var41 = 0;

                  for(var40 = (int)((double)var8 * 0.5D) + 1; var41 < var8; ++var41) {
                     if(var41 <= var40) {
                        var18.func_78374_a((double)var25, (double)(var3 + var13), (double)(var26 + (float)var41 + 1.0F - var15), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.func_78374_a((double)(var25 + (float)var8), (double)(var3 + var13), (double)(var26 + (float)var41 + 1.0F - var15), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.func_78374_a((double)(var25 + (float)var8), (double)var3, (double)(var26 + (float)var41 + 1.0F - var15), var35, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                        var18.func_78374_a((double)var25, (double)var3, (double)(var26 + (float)var41 + 1.0F - var15), var31, (double)((var24 + (float)var41 + 0.5F) * var14 + var17));
                     }
                  }

                  var18.func_78381_a();
               }

               GL11.glEndList();
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(2884);
      }
   }

   public boolean func_72721_a(double p_72721_1_, double p_72721_3_, double p_72721_5_, float p_72721_7_) {
      return false;
   }

   public void func_72736_c(float p_72736_1_) {}

   public boolean func_72716_a(EntityLivingBase p_72716_1_, boolean p_72716_2_) {
      if(this.variableIntTemp[9] != 0) {
         this.variableIntTemp[9] = 0;
         return true;
      } else {
         int var10006 = this.variableIntTemp[18];
         int var10003 = this.variableIntTemp[18];
         this.variableIntTemp[18] = var10006 + 1;
         return this.updateRenderersMain(p_72716_1_, p_72716_2_, var10003);
      }
   }

   public void func_72717_a(Tessellator p_72717_1_, EntityPlayer p_72717_2_, float p_72717_3_) {
      double var4 = p_72717_2_.field_70142_S + (p_72717_2_.field_70165_t - p_72717_2_.field_70142_S) * (double)p_72717_3_;
      double var6 = p_72717_2_.field_70137_T + (p_72717_2_.field_70163_u - p_72717_2_.field_70137_T) * (double)p_72717_3_;
      double var8 = p_72717_2_.field_70136_U + (p_72717_2_.field_70161_v - p_72717_2_.field_70136_U) * (double)p_72717_3_;
      if(!this.field_72738_E.isEmpty()) {
         GL11.glBlendFunc(774, 768);
         this.field_72770_i.func_110577_a(TextureMap.field_110575_b);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
         GL11.glPushMatrix();
         GL11.glDisable(3008);
         GL11.glPolygonOffset(-3.0F, -3.0F);
         GL11.glEnable('\u8037');
         GL11.glEnable(3008);
         p_72717_1_.func_78382_b();
         p_72717_1_.func_78373_b(-var4, -var6, -var8);
         p_72717_1_.func_78383_c();
         Iterator var10 = this.field_72738_E.values().iterator();

         while(var10.hasNext()) {
            DestroyBlockProgress var11 = (DestroyBlockProgress)var10.next();
            double var12 = (double)var11.func_73110_b() - var4;
            double var14 = (double)var11.func_73109_c() - var6;
            double var16 = (double)var11.func_73108_d() - var8;
            if(var12 * var12 + var14 * var14 + var16 * var16 > 1024.0D) {
               var10.remove();
            } else {
               int var18 = this.field_72769_h.func_72798_a(var11.func_73110_b(), var11.func_73109_c(), var11.func_73108_d());
               Block var19 = var18 > 0?Block.field_71973_m[var18]:null;
               if(var19 == null) {
                  var19 = Block.field_71981_t;
               }

               this.field_72776_r.func_78604_a(var19, var11.func_73110_b(), var11.func_73109_c(), var11.func_73108_d(), this.field_94141_F[var11.func_73106_e()]);
            }
         }

         p_72717_1_.func_78381_a();
         p_72717_1_.func_78373_b(0.0D, 0.0D, 0.0D);
         GL11.glDisable(3008);
         GL11.glPolygonOffset(0.0F, 0.0F);
         GL11.glDisable('\u8037');
         GL11.glEnable(3008);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }

   }

   public void func_72731_b(EntityPlayer p_72731_1_, MovingObjectPosition p_72731_2_, int p_72731_3_, float p_72731_4_) {
      if(p_72731_3_ == 0 && p_72731_2_.field_72313_a == EnumMovingObjectType.TILE) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDepthMask(false);
         float var5 = 0.002F;
         int var6 = this.field_72769_h.func_72798_a(p_72731_2_.field_72311_b, p_72731_2_.field_72312_c, p_72731_2_.field_72309_d);
         if(var6 > 0) {
            Block.field_71973_m[var6].func_71902_a(this.field_72769_h, p_72731_2_.field_72311_b, p_72731_2_.field_72312_c, p_72731_2_.field_72309_d);
            double var7 = p_72731_1_.field_70142_S + (p_72731_1_.field_70165_t - p_72731_1_.field_70142_S) * (double)p_72731_4_;
            double var9 = p_72731_1_.field_70137_T + (p_72731_1_.field_70163_u - p_72731_1_.field_70137_T) * (double)p_72731_4_;
            double var11 = p_72731_1_.field_70136_U + (p_72731_1_.field_70161_v - p_72731_1_.field_70136_U) * (double)p_72731_4_;
            this.func_72715_a(Block.field_71973_m[var6].func_71911_a_(this.field_72769_h, p_72731_2_.field_72311_b, p_72731_2_.field_72312_c, p_72731_2_.field_72309_d).func_72314_b((double)var5, (double)var5, (double)var5).func_72325_c(-var7, -var9, -var11));
         }

         GL11.glDepthMask(true);
         GL11.glEnable(3553);
         GL11.glDisable(3042);
      }

   }

   private void func_72715_a(AxisAlignedBB p_72715_1_) {
      Tessellator var2 = Tessellator.field_78398_a;
      var2.func_78371_b(3);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78381_a();
      var2.func_78371_b(3);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78381_a();
      var2.func_78371_b(1);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78381_a();
   }

   public void func_72725_b(int p_72725_1_, int p_72725_2_, int p_72725_3_, int p_72725_4_, int p_72725_5_, int p_72725_6_) {
      int var7 = MathHelper.func_76137_a(p_72725_1_, 16);
      int var8 = MathHelper.func_76137_a(p_72725_2_, 16);
      int var9 = MathHelper.func_76137_a(p_72725_3_, 16);
      int var10 = MathHelper.func_76137_a(p_72725_4_, 16);
      int var11 = MathHelper.func_76137_a(p_72725_5_, 16);
      int var12 = MathHelper.func_76137_a(p_72725_6_, 16);

      for(int var13 = var7; var13 <= var10; ++var13) {
         int var14 = var13 % this.field_72766_m;
         if(var14 < 0) {
            var14 += this.field_72766_m;
         }

         for(int var15 = var8; var15 <= var11; ++var15) {
            int var16 = var15 % this.field_72763_n;
            if(var16 < 0) {
               var16 += this.field_72763_n;
            }

            for(int var17 = var9; var17 <= var12; ++var17) {
               int var18 = var17 % this.field_72764_o;
               if(var18 < 0) {
                  var18 += this.field_72764_o;
               }

               int var19 = (var18 * this.field_72763_n + var16) * this.field_72766_m + var14;
               WorldRenderer var20 = this.field_72765_l[var19];
               if(var20 != null && !var20.field_78939_q) {
                  this.field_72767_j.add(var20);
                  var20.func_78914_f();
               }
            }
         }
      }

   }

   public void func_72710_a(int p_72710_1_, int p_72710_2_, int p_72710_3_) {
      this.func_72725_b(p_72710_1_ - 1, p_72710_2_ - 1, p_72710_3_ - 1, p_72710_1_ + 1, p_72710_2_ + 1, p_72710_3_ + 1);
   }

   public void func_72711_b(int p_72711_1_, int p_72711_2_, int p_72711_3_) {
      this.func_72725_b(p_72711_1_ - 1, p_72711_2_ - 1, p_72711_3_ - 1, p_72711_1_ + 1, p_72711_2_ + 1, p_72711_3_ + 1);
   }

   public void func_72707_a(int p_72707_1_, int p_72707_2_, int p_72707_3_, int p_72707_4_, int p_72707_5_, int p_72707_6_) {
      this.func_72725_b(p_72707_1_ - 1, p_72707_2_ - 1, p_72707_3_ - 1, p_72707_4_ + 1, p_72707_5_ + 1, p_72707_6_ + 1);
   }

   public void func_72729_a(ICamera p_72729_1_, float p_72729_2_) {
      EntityLivingBase var3 = this.field_72777_q.field_71451_h;
      this.xPosition = var3.field_70142_S + (var3.field_70165_t - var3.field_70142_S) * (double)p_72729_2_;
      this.yPosition = var3.field_70137_T + (var3.field_70163_u - var3.field_70137_T) * (double)p_72729_2_;
      this.zPosition = var3.field_70136_U + (var3.field_70161_v - var3.field_70136_U) * (double)p_72729_2_;
      ClippingHelper var4 = ClippingHelperImpl.func_78558_a();
      int var5 = 0;

      for(byte var6 = 6; var5 < var6; ++var5) {
         this.frustum1[var5] = var4.field_78557_a[var5][0];
         this.frustum2[var5] = var4.field_78557_a[var5][1];
         this.frustum3[var5] = var4.field_78557_a[var5][2];
         this.frustum4[var5] = var4.field_78557_a[var5][3];
      }

      this.frameSettings(this.field_72766_m * this.field_72763_n * this.field_72764_o);
      byte var7 = 0;
      boolean var10002 = this.variableBooleanTemp[2];
      var5 = var7 + 1;
      int var8 = 1 + this.booleanNumber(var10002, var7) + this.booleanNumber(this.variableBooleanTemp[3], var5++) + this.booleanNumber(this.variableBooleanTemp[4], var5++) + this.booleanNumber(this.variableBooleanTemp[5], var5++) + (this.lightRendererConfig[24] << var5++) + (this.lightRendererConfig[25] << var5++);
      if(var8 != this.variableIntTemp[16]) {
         this.variableIntTemp[16] = var8;
         this.markRenderers();
      }

      if(this.lightRendererConfig[8] > 0) {
         if(this.variableLongTemp[5] != (long)this.field_72773_u) {
            if(this.lightRendererConfig[8] <= this.variableIntTemp[10]) {
               ++this.variableIntTemp[9];
            }

            this.variableIntTemp[10] = 0;
            this.variableLongTemp[5] = (long)this.field_72773_u;
         } else {
            ++this.variableIntTemp[10];
         }
      }

      this.variableIntTemp[18] = 0;
      ++this.field_72757_g;
   }

   public void func_72702_a(String p_72702_1_, int p_72702_2_, int p_72702_3_, int p_72702_4_) {
      ItemRecord var5 = ItemRecord.func_90042_d(p_72702_1_);
      if(p_72702_1_ != null && var5 != null) {
         this.field_72777_q.field_71456_v.func_73833_a(var5.func_90043_g());
      }

      this.field_72777_q.field_71416_A.func_77368_a(p_72702_1_, (float)p_72702_2_, (float)p_72702_3_, (float)p_72702_4_);
   }

   public void func_72704_a(String p_72704_1_, double p_72704_2_, double p_72704_4_, double p_72704_6_, float p_72704_8_, float p_72704_9_) {}

   public void func_85102_a(EntityPlayer p_85102_1_, String p_85102_2_, double p_85102_3_, double p_85102_5_, double p_85102_7_, float p_85102_9_, float p_85102_10_) {}

   public void func_72708_a(String p_72708_1_, double p_72708_2_, double p_72708_4_, double p_72708_6_, double p_72708_8_, double p_72708_10_, double p_72708_12_) {
      try {
         this.func_72726_b(p_72708_1_, p_72708_2_, p_72708_4_, p_72708_6_, p_72708_8_, p_72708_10_, p_72708_12_);
      } catch (Throwable var17) {
         CrashReport var15 = CrashReport.func_85055_a(var17, "Exception while adding particle");
         CrashReportCategory var16 = var15.func_85058_a("Particle being added");
         var16.func_71507_a("Name", p_72708_1_);
         var16.func_71500_a("Position", new CallableParticlePositionInfo(this, p_72708_2_, p_72708_4_, p_72708_6_));
         throw new ReportedException(var15);
      }
   }

   public EntityFX func_72726_b(String p_72726_1_, double p_72726_2_, double p_72726_4_, double p_72726_6_, double p_72726_8_, double p_72726_10_, double p_72726_12_) {
      if(this.field_72777_q != null && this.field_72777_q.field_71451_h != null && this.field_72777_q.field_71452_i != null) {
         int var14 = this.field_72777_q.field_71474_y.field_74362_aa;
         if(var14 == 1 && this.field_72769_h.field_73012_v.nextInt(3) == 0) {
            var14 = 2;
         }

         double var15 = this.field_72777_q.field_71451_h.field_70165_t - p_72726_2_;
         double var17 = this.field_72777_q.field_71451_h.field_70163_u - p_72726_4_;
         double var19 = this.field_72777_q.field_71451_h.field_70161_v - p_72726_6_;
         Object var21 = null;
         if(p_72726_1_.equals("hugeexplosion")) {
            this.field_72777_q.field_71452_i.func_78873_a(var21 = new EntityHugeExplodeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
         } else if(p_72726_1_.equals("largeexplode")) {
            this.field_72777_q.field_71452_i.func_78873_a(var21 = new EntityLargeExplodeFX(this.field_72770_i, this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
         } else if(p_72726_1_.equals("fireworksSpark")) {
            this.field_72777_q.field_71452_i.func_78873_a(var21 = new EntityFireworkSparkFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, this.field_72777_q.field_71452_i));
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
               if(p_72726_1_.equals("bubble")) {
                  var21 = new EntityBubbleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("suspended")) {
                  var21 = new EntitySuspendFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("depthsuspend")) {
                  var21 = new EntityAuraFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("townaura")) {
                  var21 = new EntityAuraFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("crit")) {
                  var21 = new EntityCritFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("magicCrit")) {
                  var21 = new EntityCritFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntityFX)var21).func_70538_b(((EntityFX)var21).func_70534_d() * 0.3F, ((EntityFX)var21).func_70542_f() * 0.8F, ((EntityFX)var21).func_70535_g());
                  ((EntityFX)var21).func_94053_h();
               } else if(p_72726_1_.equals("smoke")) {
                  var21 = new EntitySmokeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("mobSpell")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, 0.0D, 0.0D, 0.0D);
                  ((EntityFX)var21).func_70538_b((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
               } else if(p_72726_1_.equals("mobSpellAmbient")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, 0.0D, 0.0D, 0.0D);
                  ((EntityFX)var21).func_82338_g(0.15F);
                  ((EntityFX)var21).func_70538_b((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
               } else if(p_72726_1_.equals("spell")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("instantSpell")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntitySpellParticleFX)var21).func_70589_b(144);
               } else if(p_72726_1_.equals("witchMagic")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntitySpellParticleFX)var21).func_70589_b(144);
                  float var24 = this.field_72769_h.field_73012_v.nextFloat() * 0.5F + 0.35F;
                  ((EntityFX)var21).func_70538_b(1.0F * var24, 0.0F * var24, 1.0F * var24);
               } else if(p_72726_1_.equals("note")) {
                  var21 = new EntityNoteFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("portal")) {
                  var21 = new EntityPortalFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("enchantmenttable")) {
                  var21 = new EntityEnchantmentTableParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("explode")) {
                  var21 = new EntityExplodeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("flame")) {
                  var21 = new EntityFlameFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("lava")) {
                  var21 = new EntityLavaFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_);
               } else if(p_72726_1_.equals("footstep")) {
                  var21 = new EntityFootStepFX(this.field_72770_i, this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_);
               } else if(p_72726_1_.equals("splash")) {
                  var21 = new EntitySplashFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("largesmoke")) {
                  var21 = new EntitySmokeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, 2.5F);
               } else if(p_72726_1_.equals("cloud")) {
                  var21 = new EntityCloudFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("reddust")) {
                  var21 = new EntityReddustFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, (float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
               } else if(p_72726_1_.equals("snowballpoof")) {
                  var21 = new EntityBreakingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Item.field_77768_aD);
               } else if(p_72726_1_.equals("dripWater")) {
                  var21 = new EntityDropParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Material.field_76244_g);
               } else if(p_72726_1_.equals("dripLava")) {
                  var21 = new EntityDropParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Material.field_76256_h);
               } else if(p_72726_1_.equals("snowshovel")) {
                  var21 = new EntitySnowShovelFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("slime")) {
                  var21 = new EntityBreakingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Item.field_77761_aM);
               } else if(p_72726_1_.equals("heart")) {
                  var21 = new EntityHeartFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("angryVillager")) {
                  var21 = new EntityHeartFX(this.field_72769_h, p_72726_2_, p_72726_4_ + 0.5D, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntityFX)var21).func_70536_a(81);
                  ((EntityFX)var21).func_70538_b(1.0F, 1.0F, 1.0F);
               } else if(p_72726_1_.equals("happyVillager")) {
                  var21 = new EntityAuraFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntityFX)var21).func_70536_a(82);
                  ((EntityFX)var21).func_70538_b(1.0F, 1.0F, 1.0F);
               } else {
                  String[] var25;
                  int var26;
                  int var27;
                  if(p_72726_1_.startsWith("iconcrack_")) {
                     var25 = p_72726_1_.split("_", 3);
                     var27 = Integer.parseInt(var25[1]);
                     if(var25.length > 2) {
                        var26 = Integer.parseInt(var25[2]);
                        var21 = new EntityBreakingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Item.field_77698_e[var27], var26);
                     } else {
                        var21 = new EntityBreakingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Item.field_77698_e[var27], 0);
                     }
                  } else if(p_72726_1_.startsWith("tilecrack_")) {
                     var25 = p_72726_1_.split("_", 3);
                     var27 = Integer.parseInt(var25[1]);
                     var26 = Integer.parseInt(var25[2]);
                     var21 = (new EntityDiggingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Block.field_71973_m[var27], var26)).func_90019_g(var26);
                  }
               }

               if(var21 != null) {
                  this.field_72777_q.field_71452_i.func_78873_a((EntityFX)var21);
               }

               return (EntityFX)var21;
            }
         }
      } else {
         return null;
      }
   }

   public void func_72703_a(Entity p_72703_1_) {}

   public void func_72709_b(Entity p_72709_1_) {}

   public void func_72728_f() {
      GLAllocation.func_74523_b(this.field_72778_p);
   }

   public void func_82746_a(int p_82746_1_, int p_82746_2_, int p_82746_3_, int p_82746_4_, int p_82746_5_) {
      Random var6 = this.field_72769_h.field_73012_v;
      switch(p_82746_1_) {
      case 1013:
      case 1018:
         if(this.field_72777_q.field_71451_h != null) {
            double var7 = (double)p_82746_2_ - this.field_72777_q.field_71451_h.field_70165_t;
            double var9 = (double)p_82746_3_ - this.field_72777_q.field_71451_h.field_70163_u;
            double var11 = (double)p_82746_4_ - this.field_72777_q.field_71451_h.field_70161_v;
            double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
            double var15 = this.field_72777_q.field_71451_h.field_70165_t;
            double var17 = this.field_72777_q.field_71451_h.field_70163_u;
            double var19 = this.field_72777_q.field_71451_h.field_70161_v;
            if(var13 > 0.0D) {
               var15 += var7 / var13 * 2.0D;
               var17 += var9 / var13 * 2.0D;
               var19 += var11 / var13 * 2.0D;
            }

            if(p_82746_1_ == 1013) {
               this.field_72769_h.func_72980_b(var15, var17, var19, "mob.wither.spawn", 1.0F, 1.0F, false);
            } else if(p_82746_1_ == 1018) {
               this.field_72769_h.func_72980_b(var15, var17, var19, "mob.enderdragon.end", 5.0F, 1.0F, false);
            }
         }
      default:
      }
   }

   public void func_72706_a(EntityPlayer p_72706_1_, int p_72706_2_, int p_72706_3_, int p_72706_4_, int p_72706_5_, int p_72706_6_) {
      Random var7 = this.field_72769_h.field_73012_v;
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
      switch(p_72706_2_) {
      case 1000:
         this.field_72769_h.func_72980_b((double)p_72706_3_, (double)p_72706_4_, (double)p_72706_5_, "random.click", 1.0F, 1.0F, false);
         break;
      case 1001:
         this.field_72769_h.func_72980_b((double)p_72706_3_, (double)p_72706_4_, (double)p_72706_5_, "random.click", 1.0F, 1.2F, false);
         break;
      case 1002:
         this.field_72769_h.func_72980_b((double)p_72706_3_, (double)p_72706_4_, (double)p_72706_5_, "random.bow", 1.0F, 1.2F, false);
         break;
      case 1003:
         if(Math.random() < 0.5D) {
            this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "random.door_open", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         } else {
            this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "random.door_close", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         }
         break;
      case 1004:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.fizz", 0.5F, 2.6F + (var7.nextFloat() - var7.nextFloat()) * 0.8F, false);
         break;
      case 1005:
         if(Item.field_77698_e[p_72706_6_] instanceof ItemRecord) {
            this.field_72769_h.func_72934_a(((ItemRecord)Item.field_77698_e[p_72706_6_]).field_77837_a, p_72706_3_, p_72706_4_, p_72706_5_);
         } else {
            this.field_72769_h.func_72934_a((String)null, p_72706_3_, p_72706_4_, p_72706_5_);
         }
         break;
      case 1007:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.ghast.charge", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1008:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.ghast.fireball", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1009:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.ghast.fireball", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1010:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.wood", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1011:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.metal", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1012:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.woodbreak", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1014:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.wither.shoot", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1015:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.bat.takeoff", 0.05F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1016:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.infect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1017:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.unfect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1020:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.anvil_break", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1021:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.anvil_use", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1022:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.anvil_land", 0.3F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2000:
         int var27 = p_72706_6_ % 3 - 1;
         int var28 = p_72706_6_ / 3 % 3 - 1;
         var10 = (double)p_72706_3_ + (double)var27 * 0.6D + 0.5D;
         var12 = (double)p_72706_4_ + 0.5D;
         double var29 = (double)p_72706_5_ + (double)var28 * 0.6D + 0.5D;

         for(int var43 = 0; var43 < 10; ++var43) {
            double var44 = var7.nextDouble() * 0.2D + 0.01D;
            double var45 = var10 + (double)var27 * 0.01D + (var7.nextDouble() - 0.5D) * (double)var28 * 0.5D;
            var25 = var12 + (var7.nextDouble() - 0.5D) * 0.5D;
            var17 = var29 + (double)var28 * 0.01D + (var7.nextDouble() - 0.5D) * (double)var27 * 0.5D;
            var19 = (double)var27 * var44 + var7.nextGaussian() * 0.01D;
            var21 = -0.03D + var7.nextGaussian() * 0.01D;
            var23 = (double)var28 * var44 + var7.nextGaussian() * 0.01D;
            this.func_72708_a("smoke", var45, var25, var17, var19, var21, var23);
         }

         return;
      case 2001:
         var16 = p_72706_6_ & 4095;
         if(var16 > 0) {
            Block var42 = Block.field_71973_m[var16];
            this.field_72777_q.field_71416_A.func_77364_b(var42.field_72020_cn.func_72676_a(), (float)p_72706_3_ + 0.5F, (float)p_72706_4_ + 0.5F, (float)p_72706_5_ + 0.5F, (var42.field_72020_cn.func_72677_b() + 1.0F) / 2.0F, var42.field_72020_cn.func_72678_c() * 0.8F);
         }

         this.field_72777_q.field_71452_i.func_78871_a(p_72706_3_, p_72706_4_, p_72706_5_, p_72706_6_ & 4095, p_72706_6_ >> 12 & 255);
         break;
      case 2002:
         var8 = (double)p_72706_3_;
         var10 = (double)p_72706_4_;
         var12 = (double)p_72706_5_;
         var14 = "iconcrack_" + Item.field_77726_bs.field_77779_bT + "_" + p_72706_6_;

         for(var15 = 0; var15 < 8; ++var15) {
            this.func_72708_a(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
         }

         var15 = Item.field_77726_bs.func_77620_a(p_72706_6_);
         float var31 = (float)(var15 >> 16 & 255) / 255.0F;
         float var32 = (float)(var15 >> 8 & 255) / 255.0F;
         float var33 = (float)(var15 >> 0 & 255) / 255.0F;
         String var34 = "spell";
         if(Item.field_77726_bs.func_77833_h(p_72706_6_)) {
            var34 = "instantSpell";
         }

         for(var16 = 0; var16 < 100; ++var16) {
            var25 = var7.nextDouble() * 4.0D;
            var17 = var7.nextDouble() * 3.141592653589793D * 2.0D;
            var19 = Math.cos(var17) * var25;
            var21 = 0.01D + var7.nextDouble() * 0.5D;
            var23 = Math.sin(var17) * var25;
            EntityFX var47 = this.func_72726_b(var34, var8 + var19 * 0.1D, var10 + 0.3D, var12 + var23 * 0.1D, var19, var21, var23);
            if(var47 != null) {
               float var48 = 0.75F + var7.nextFloat() * 0.25F;
               var47.func_70538_b(var31 * var48, var32 * var48, var33 * var48);
               var47.func_70543_e((float)var25);
            }
         }

         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "random.glass", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2003:
         var8 = (double)p_72706_3_ + 0.5D;
         var10 = (double)p_72706_4_;
         var12 = (double)p_72706_5_ + 0.5D;
         var14 = "iconcrack_" + Item.field_77748_bA.field_77779_bT;

         for(var15 = 0; var15 < 8; ++var15) {
            this.func_72708_a(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
         }

         for(double var46 = 0.0D; var46 < 6.283185307179586D; var46 += 0.15707963267948966D) {
            this.func_72708_a("portal", var8 + Math.cos(var46) * 5.0D, var10 - 0.4D, var12 + Math.sin(var46) * 5.0D, Math.cos(var46) * -5.0D, 0.0D, Math.sin(var46) * -5.0D);
            this.func_72708_a("portal", var8 + Math.cos(var46) * 5.0D, var10 - 0.4D, var12 + Math.sin(var46) * 5.0D, Math.cos(var46) * -7.0D, 0.0D, Math.sin(var46) * -7.0D);
         }

         return;
      case 2004:
         for(int var35 = 0; var35 < 20; ++var35) {
            double var36 = (double)p_72706_3_ + 0.5D + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5D) * 2.0D;
            double var38 = (double)p_72706_4_ + 0.5D + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5D) * 2.0D;
            double var40 = (double)p_72706_5_ + 0.5D + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5D) * 2.0D;
            this.field_72769_h.func_72869_a("smoke", var36, var38, var40, 0.0D, 0.0D, 0.0D);
            this.field_72769_h.func_72869_a("flame", var36, var38, var40, 0.0D, 0.0D, 0.0D);
         }

         return;
      case 2005:
         ItemDye.func_96603_a(this.field_72769_h, p_72706_3_, p_72706_4_, p_72706_5_, p_72706_6_);
      }

   }

   public void func_72705_a(int p_72705_1_, int p_72705_2_, int p_72705_3_, int p_72705_4_, int p_72705_5_) {
      if(p_72705_5_ >= 0 && p_72705_5_ < 10) {
         DestroyBlockProgress var6 = (DestroyBlockProgress)this.field_72738_E.get(Integer.valueOf(p_72705_1_));
         if(var6 == null || var6.func_73110_b() != p_72705_2_ || var6.func_73109_c() != p_72705_3_ || var6.func_73108_d() != p_72705_4_) {
            var6 = new DestroyBlockProgress(p_72705_1_, p_72705_2_, p_72705_3_, p_72705_4_);
            this.field_72738_E.put(Integer.valueOf(p_72705_1_), var6);
         }

         var6.func_73107_a(p_72705_5_);
         var6.func_82744_b(this.field_72773_u);
      } else {
         this.field_72738_E.remove(Integer.valueOf(p_72705_1_));
      }

   }

   public void func_94140_a(IconRegister p_94140_1_) {
      this.field_94141_F = new Icon[10];

      for(int var2 = 0; var2 < this.field_94141_F.length; ++var2) {
         this.field_94141_F[var2] = p_94140_1_.func_94245_a("destroy_stage_" + var2);
      }

   }

}
