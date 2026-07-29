package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityRainFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.CallableMouseLocation;
import net.minecraft.client.renderer.CallableScreenName;
import net.minecraft.client.renderer.CallableScreenSize;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class EntityRenderer {

   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
   public static boolean anaglyphEnable;
   public static int anaglyphField;
   private Minecraft mc;
   private float farPlaneDistance;
   public ItemRenderer itemRenderer;
   private int rendererUpdateCount;
   private Entity pointedEntity;
   private MouseFilter mouseFilterXAxis = new MouseFilter();
   private MouseFilter mouseFilterYAxis = new MouseFilter();
   private MouseFilter mouseFilterDummy1 = new MouseFilter();
   private MouseFilter mouseFilterDummy2 = new MouseFilter();
   private MouseFilter mouseFilterDummy3 = new MouseFilter();
   private MouseFilter mouseFilterDummy4 = new MouseFilter();
   private float thirdPersonDistance = 4.0F;
   private float thirdPersonDistanceTemp = 4.0F;
   private float debugCamYaw;
   private float prevDebugCamYaw;
   private float debugCamPitch;
   private float prevDebugCamPitch;
   private float smoothCamYaw;
   private float smoothCamPitch;
   private float smoothCamFilterX;
   private float smoothCamFilterY;
   private float smoothCamPartialTicks;
   private float debugCamFOV;
   private float prevDebugCamFOV;
   private float camRoll;
   private float prevCamRoll;
   private final DynamicTexture lightmapTexture;
   private final int[] lightmapColors;
   private final ResourceLocation locationLightMap;
   private float fovModifierHand;
   private float fovModifierHandPrev;
   private float fovMultiplierTemp;
   private float field_82831_U;
   private float field_82832_V;
   private boolean cloudFog;
   private double cameraZoom = 1.0D;
   private double cameraYaw;
   private double cameraPitch;
   private long prevFrameTime = Minecraft.getSystemTime();
   private long renderEndNanoTime;
   private boolean lightmapUpdateNeeded;
   float torchFlickerX;
   float torchFlickerDX;
   float torchFlickerY;
   float torchFlickerDY;
   private Random random = new Random();
   private int rainSoundCounter;
   float[] rainXCoords;
   float[] rainYCoords;
   FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
   float fogColorRed;
   float fogColorGreen;
   float fogColorBlue;
   private float fogColor2;
   private float fogColor1;
   public int debugViewDirection;
   private byte[] lightRendererConfig = new byte[136];
   private long[] variableLongTemp = new long[10];
   private int[] fpsCounter = new int[10];
   private boolean[] variableBooleanTemp = new boolean[10];
   private float farPlaneDistanceTemp;

   public EntityRenderer(Minecraft par1Minecraft) {
      this.mc = par1Minecraft;
      this.itemRenderer = new ItemRenderer(par1Minecraft);
      this.lightmapTexture = new DynamicTexture(16, 16);
      this.locationLightMap = par1Minecraft.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
      this.lightmapColors = this.lightmapTexture.getTextureData();
   }

   public void updateRenderer() {
      this.updateFovModifierHand();
      this.updateTorchFlicker();
      this.fogColor2 = this.fogColor1;
      this.thirdPersonDistanceTemp = this.thirdPersonDistance;
      this.prevDebugCamYaw = this.debugCamYaw;
      this.prevDebugCamPitch = this.debugCamPitch;
      this.prevDebugCamFOV = this.debugCamFOV;
      this.prevCamRoll = this.camRoll;
      float var1;
      float var2;
      if(this.mc.gameSettings.smoothCamera) {
         var1 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         var2 = var1 * var1 * var1 * 8.0F;
         this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * var2);
         this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * var2);
         this.smoothCamPartialTicks = 0.0F;
         this.smoothCamYaw = 0.0F;
         this.smoothCamPitch = 0.0F;
      }

      if(this.mc.renderViewEntity == null) {
         this.mc.renderViewEntity = this.mc.thePlayer;
      }

      var1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
      var2 = (float)(3 - this.mc.gameSettings.renderDistance) / 3.0F;
      float var3 = var1 * (1.0F - var2) + var2;
      this.fogColor1 += (var3 - this.fogColor1) * 0.1F;
      ++this.rendererUpdateCount;
      this.itemRenderer.updateEquippedItem();
      this.addRainParticles();
      this.field_82832_V = this.field_82831_U;
      if(BossStatus.field_82825_d) {
         this.field_82831_U += 0.05F;
         if(this.field_82831_U > 1.0F) {
            this.field_82831_U = 1.0F;
         }

         BossStatus.field_82825_d = false;
      } else if(this.field_82831_U > 0.0F) {
         this.field_82831_U -= 0.0125F;
      }

   }

   public void getMouseOver(float par1) {
      if(this.mc.renderViewEntity != null && this.mc.theWorld != null) {
         this.mc.pointedEntityLiving = null;
         double var2 = (double)this.mc.playerController.getBlockReachDistance();
         this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(var2, par1);
         double var4 = var2;
         Vec3 var6 = this.mc.renderViewEntity.getPosition(par1);
         if(this.mc.playerController.extendedReach()) {
            var2 = 6.0D;
            var4 = 6.0D;
         } else {
            if(var2 > 3.0D) {
               var4 = 3.0D;
            }

            var2 = var4;
         }

         if(this.mc.objectMouseOver != null) {
            var4 = this.mc.objectMouseOver.hitVec.distanceTo(var6);
         }

         Vec3 var7 = this.mc.renderViewEntity.getLook(par1);
         Vec3 var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
         this.pointedEntity = null;
         float var9 = 1.0F;
         List var10 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand((double)var9, (double)var9, (double)var9));
         double var11 = var4;

         for(int var13 = 0; var13 < var10.size(); ++var13) {
            Entity var14 = (Entity)var10.get(var13);
            if(var14.canBeCollidedWith()) {
               float var15 = var14.getCollisionBorderSize();
               AxisAlignedBB var16 = var14.boundingBox.expand((double)var15, (double)var15, (double)var15);
               MovingObjectPosition var17 = var16.calculateIntercept(var6, var8);
               if(var16.isVecInside(var6)) {
                  if(0.0D < var11 || var11 == 0.0D) {
                     this.pointedEntity = var14;
                     var11 = 0.0D;
                  }
               } else if(var17 != null) {
                  double var18 = var6.distanceTo(var17.hitVec);
                  if(var18 < var11 || var11 == 0.0D) {
                     if(var14 == this.mc.renderViewEntity.ridingEntity) {
                        if(var11 == 0.0D) {
                           this.pointedEntity = var14;
                        }
                     } else {
                        this.pointedEntity = var14;
                        var11 = var18;
                     }
                  }
               }
            }
         }

         if(this.pointedEntity != null && (var11 < var4 || this.mc.objectMouseOver == null)) {
            this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity);
            if(this.pointedEntity instanceof EntityLivingBase) {
               this.mc.pointedEntityLiving = (EntityLivingBase)this.pointedEntity;
            }
         }
      }

   }

   private void updateFovModifierHand() {
      EntityPlayerSP var1 = (EntityPlayerSP)this.mc.renderViewEntity;
      this.fovMultiplierTemp = var1.getFOVMultiplier();
      this.fovModifierHandPrev = this.fovModifierHand;
      this.fovModifierHand += (this.fovMultiplierTemp - this.fovModifierHand) * 0.5F;
      if(this.fovModifierHand > 1.5F) {
         this.fovModifierHand = 1.5F;
      }

      if(this.fovModifierHand < 0.1F) {
         this.fovModifierHand = 0.1F;
      }

   }

   private float getFOVModifier(float par1, boolean par2) {
      if(this.debugViewDirection > 0) {
         return 90.0F;
      } else {
         EntityPlayer var3 = (EntityPlayer)this.mc.renderViewEntity;
         float var4 = 70.0F;
         if(par2) {
            var4 += this.mc.gameSettings.fovSetting * 40.0F;
            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * par1;
         }

         if(var3.getHealth() <= 0.0F) {
            float var5 = (float)var3.deathTime + par1;
            var4 /= (1.0F - 500.0F / (var5 + 500.0F)) * 2.0F + 1.0F;
         }

         int var6 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(this.mc.theWorld, var3, par1);
         if(var6 != 0 && Block.blocksList[var6].blockMaterial == Material.water) {
            var4 = var4 * 60.0F / 70.0F;
         }

         return var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * par1;
      }
   }

   private void hurtCameraEffect(float par1) {
      EntityLivingBase var2 = this.mc.renderViewEntity;
      float var3 = (float)var2.hurtTime - par1;
      float var4;
      if(var2.getHealth() <= 0.0F) {
         var4 = (float)var2.deathTime + par1;
         GL11.glRotatef(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
      }

      if(var3 >= 0.0F) {
         var3 /= (float)var2.maxHurtTime;
         var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927F);
         var4 = var2.attackedAtYaw;
         GL11.glRotatef(-var4, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(var4, 0.0F, 1.0F, 0.0F);
      }

   }

   private void setupViewBobbing(float par1) {
      if(this.mc.renderViewEntity instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)this.mc.renderViewEntity;
         float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
         float var4 = -(var2.distanceWalkedModified + var3 * par1);
         float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * par1;
         float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * par1;
         GL11.glTranslatef(MathHelper.sin(var4 * 3.1415927F) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * 3.1415927F) * var5), 0.0F);
         GL11.glRotatef(MathHelper.sin(var4 * 3.1415927F) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(Math.abs(MathHelper.cos(var4 * 3.1415927F - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
      }

   }

   private void orientCamera(float par1) {
      EntityLivingBase var2 = this.mc.renderViewEntity;
      float var3 = var2.yOffset - 1.62F;
      double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)par1;
      double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)par1 - (double)var3;
      double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)par1;
      GL11.glRotatef(this.prevCamRoll + (this.camRoll - this.prevCamRoll) * par1, 0.0F, 0.0F, 1.0F);
      if(var2.isPlayerSleeping()) {
         var3 = (float)((double)var3 + 1.0D);
         GL11.glTranslatef(0.0F, 0.3F, 0.0F);
         if(!this.mc.gameSettings.debugCamEnable) {
            int var10 = this.mc.theWorld.getBlockId(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
            if(var10 == Block.bed.blockID) {
               int var11 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
               int var12 = var11 & 3;
               GL11.glRotatef((float)(var12 * 90), 0.0F, 1.0F, 0.0F);
            }

            GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0F, 0.0F, -1.0F, 0.0F);
            GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, -1.0F, 0.0F, 0.0F);
         }
      } else if(this.mc.gameSettings.thirdPersonView > 0) {
         double var27 = (double)(this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * par1);
         float var13;
         float var28;
         if(this.mc.gameSettings.debugCamEnable) {
            var13 = this.prevDebugCamYaw + (this.debugCamYaw - this.prevDebugCamYaw) * par1;
            var28 = this.prevDebugCamPitch + (this.debugCamPitch - this.prevDebugCamPitch) * par1;
            GL11.glTranslatef(0.0F, 0.0F, (float)(-var27));
            GL11.glRotatef(var28, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var13, 0.0F, 1.0F, 0.0F);
         } else {
            var13 = var2.rotationYaw;
            var28 = var2.rotationPitch;
            if(this.mc.gameSettings.thirdPersonView == 2) {
               var28 += 180.0F;
            }

            double var14 = (double)(-MathHelper.sin(var13 / 180.0F * 3.1415927F) * MathHelper.cos(var28 / 180.0F * 3.1415927F)) * var27;
            double var16 = (double)(MathHelper.cos(var13 / 180.0F * 3.1415927F) * MathHelper.cos(var28 / 180.0F * 3.1415927F)) * var27;
            double var18 = (double)(-MathHelper.sin(var28 / 180.0F * 3.1415927F)) * var27;

            for(int var20 = 0; var20 < 8; ++var20) {
               float var21 = (float)((var20 & 1) * 2 - 1);
               float var22 = (float)((var20 >> 1 & 1) * 2 - 1);
               float var23 = (float)((var20 >> 2 & 1) * 2 - 1);
               var21 *= 0.1F;
               var22 *= 0.1F;
               var23 *= 0.1F;
               MovingObjectPosition var24 = this.mc.theWorld.clip(this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 + (double)var21, var6 + (double)var22, var8 + (double)var23), this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 - var14 + (double)var21 + (double)var23, var6 - var18 + (double)var22, var8 - var16 + (double)var23));
               if(var24 != null) {
                  double var25 = var24.hitVec.distanceTo(this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4, var6, var8));
                  if(var25 < var27) {
                     var27 = var25;
                  }
               }
            }

            if(this.mc.gameSettings.thirdPersonView == 2) {
               GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GL11.glRotatef(var2.rotationPitch - var28, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var2.rotationYaw - var13, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.0F, (float)(-var27));
            GL11.glRotatef(var13 - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(var28 - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
         }
      } else {
         GL11.glTranslatef(0.0F, 0.0F, -0.1F);
      }

      if(!this.mc.gameSettings.debugCamEnable) {
         GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0F, 0.0F, 1.0F, 0.0F);
      }

      GL11.glTranslatef(0.0F, var3, 0.0F);
      var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)par1;
      var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)par1 - (double)var3;
      var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)par1;
      this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var6, var8, par1);
   }

   private void setupCameraTransform(float par1, int par2) {
      long var10003 = this.variableLongTemp[0];
      long var10000 = this.variableLongTemp[0];
      this.variableLongTemp[0] = var10003 + 1L;
      int var4;
      int var5;
      Minecraft var13;
      int var14;
      Minecraft var10002;
      if(var10000 == 0L || (long)this.mc.gameSettings.renderDistance != this.variableLongTemp[7]) {
         this.variableBooleanTemp[3] = GLContext.getCapabilities().GL_NV_fog_distance;
         this.variableLongTemp[7] = (long)this.mc.gameSettings.renderDistance;
         if(this.variableLongTemp[0] >= 2L) {
            this.variableLongTemp[0] = 0L;
         }

         if(this.mc != null) {
            var13 = this.mc;
            if(Minecraft.memoryReserve != null) {
               var13 = this.mc;
               if(Minecraft.memoryReserve.length >= 65536) {
                  var13 = this.mc;
                  Minecraft.memoryReserve['\ufff1'] = 1;
               }
            }
         }

         if(this.lightRendererConfig != null) {
            var13 = this.mc;
            if(Minecraft.memoryReserve != null) {
               var13 = this.mc;
               if(Minecraft.memoryReserve.length >= 65536) {
                  byte var15 = this.lightRendererConfig[100];
                  Minecraft var10001 = this.mc;
                  if(var15 != Minecraft.memoryReserve['\uffdc']) {
                     char var3 = '\uff78';

                     for(var4 = var3; var4 < var3 + this.lightRendererConfig.length; ++var4) {
                        if(var4 - var3 < this.lightRendererConfig.length) {
                           var10001 = this.mc;
                           if(var4 < Minecraft.memoryReserve.length) {
                              var14 = var4 - var3;
                              var10002 = this.mc;
                              this.lightRendererConfig[var14] = Minecraft.memoryReserve[var4];
                           }
                        }
                     }
                  }
               }
            }
         }

         int[] var7 = new int[]{25, 16, 8, 3, 3};
         var4 = this.mc.gameSettings.renderDistance < 0?0:(this.mc.gameSettings.renderDistance > 4?4:this.mc.gameSettings.renderDistance);
         if(this.lightRendererConfig[var4 + 0] > 0) {
            var7[var4] = this.lightRendererConfig[var4 + 0] - 1;
         }

         var5 = var7[var4] < 3?3:var7[var4];
         var5 += var7[var4] < 20?0:(var7[var4] <= 24?32 - var7[var4]:8);
         this.farPlaneDistanceTemp = (float)(var5 * 8);
         this.farPlaneDistance = this.farPlaneDistanceTemp;
      }

      this.farPlaneDistance = this.farPlaneDistanceTemp <= 0.0F?(float)(32 << 3 - this.mc.gameSettings.renderDistance):this.farPlaneDistanceTemp;
      ++this.fpsCounter[7];
      var13 = this.mc;
      if(Minecraft.getSystemTime() >= this.variableLongTemp[8]) {
         var10002 = this.mc;
         this.variableLongTemp[8] = Minecraft.getSystemTime() + 200L;
         if(!this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI) {
            int var10004 = this.fpsCounter[6];
            var14 = this.fpsCounter[6];
            this.fpsCounter[6] = var10004 + 1;
            this.fpsCounter[var14] = this.fpsCounter[7];
            if(this.fpsCounter[6] >= 5) {
               this.fpsCounter[6] = 0;
            }

            this.fpsCounter[7] = this.fpsCounter[5] = 0;

            for(int var8 = 0; var8 < 5; ++var8) {
               this.fpsCounter[5] += this.fpsCounter[var8];
            }

            String[] var9 = new String[1];
            if(this.lightRendererConfig[110] == 2 || this.mc.gameSettings.advancedItemTooltips) {
               var9[0] = this.fpsCounter[5] + " fps";
            }

            if(this.lightRendererConfig[112] != 1 && this.mc.currentScreen != null && this.mc.currentScreen.doesGuiPauseGame()) {
               var9[0] = this.lightRendererConfig[120] > 0?this.mc.renderGlobal.getModTitle():"Light Renderer";
            }

            if(this.lightRendererConfig[113] > 0 && this.lightRendererConfig[120] > 0) {
               var9 = this.mc.renderGlobal.getDebugMode();
            }

            this.variableBooleanTemp[2] = var9 != null && var9[0] != null && var9[0].length() > 0;
            if(this.variableBooleanTemp[2]) {
               if(this.fpsCounter[8] == 0) {
                  this.fpsCounter[8] = GLAllocation.generateDisplayLists(1);
               }

               GL11.glNewList(this.fpsCounter[8], 4865);

               for(var4 = 0; var4 < var9.length; ++var4) {
                  if(var9[var4] != null && var9[var4].length() > 0) {
                     this.mc.fontRenderer.drawStringWithShadow(var9[var4], 2, var4 * 10 + 2, 16777215);
                  }
               }

               GL11.glEndList();
            }
         } else {
            this.fpsCounter[7] = 0;
            this.variableBooleanTemp[2] = false;
         }
      } else if(this.variableBooleanTemp[2]) {
         GL11.glCallList(this.fpsCounter[8]);
      }

      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      float var10 = 0.07F;
      if(this.mc.gameSettings.anaglyph) {
         GL11.glTranslatef((float)(-(par2 * 2 - 1)) * var10, 0.0F, 0.0F);
      }

      if(this.cameraZoom != 1.0D) {
         GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
         GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0D);
      }

      Project.gluPerspective(this.getFOVModifier(par1, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
      float var11;
      if(this.mc.playerController.enableEverythingIsScrewedUpMode()) {
         var11 = 0.6666667F;
         GL11.glScalef(1.0F, var11, 1.0F);
      }

      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      if(this.mc.gameSettings.anaglyph) {
         GL11.glTranslatef((float)(par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
      }

      this.hurtCameraEffect(par1);
      if(this.mc.gameSettings.viewBobbing) {
         this.setupViewBobbing(par1);
      }

      var11 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;
      if(var11 > 0.0F) {
         byte var12 = 20;
         if(this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            var12 = 7;
         }

         float var6 = 5.0F / (var11 * var11 + 5.0F) - var11 * 0.04F;
         var6 *= var6;
         GL11.glRotatef(((float)this.rendererUpdateCount + par1) * (float)var12, 0.0F, 1.0F, 1.0F);
         GL11.glScalef(1.0F / var6, 1.0F, 1.0F);
         GL11.glRotatef(-((float)this.rendererUpdateCount + par1) * (float)var12, 0.0F, 1.0F, 1.0F);
      }

      this.orientCamera(par1);
      if(this.debugViewDirection > 0) {
         var5 = this.debugViewDirection - 1;
         if(var5 == 1) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         }

         if(var5 == 2) {
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         }

         if(var5 == 3) {
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         }

         if(var5 == 4) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         }

         if(var5 == 5) {
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
         }
      }

   }

   private void renderHand(float par1, int par2) {
      if(this.debugViewDirection <= 0) {
         GL11.glMatrixMode(5889);
         GL11.glLoadIdentity();
         float var3 = 0.07F;
         if(this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)(-(par2 * 2 - 1)) * var3, 0.0F, 0.0F);
         }

         if(this.cameraZoom != 1.0D) {
            GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
            GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0D);
         }

         Project.gluPerspective(this.getFOVModifier(par1, false), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
         if(this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            float var4 = 0.6666667F;
            GL11.glScalef(1.0F, var4, 1.0F);
         }

         GL11.glMatrixMode(5888);
         GL11.glLoadIdentity();
         if(this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)(par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
         }

         GL11.glPushMatrix();
         this.hurtCameraEffect(par1);
         if(this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(par1);
         }

         if(this.mc.gameSettings.thirdPersonView == 0 && !this.mc.renderViewEntity.isPlayerSleeping() && !this.mc.gameSettings.hideGUI && !this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            this.enableLightmap((double)par1);
            this.itemRenderer.renderItemInFirstPerson(par1);
            this.disableLightmap((double)par1);
         }

         GL11.glPopMatrix();
         if(this.mc.gameSettings.thirdPersonView == 0 && !this.mc.renderViewEntity.isPlayerSleeping()) {
            this.itemRenderer.renderOverlays(par1);
            this.hurtCameraEffect(par1);
         }

         if(this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(par1);
         }
      }

   }

   public void disableLightmap(double par1) {
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glDisable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   public void enableLightmap(double par1) {
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glMatrixMode(5890);
      GL11.glLoadIdentity();
      float var3 = 0.00390625F;
      GL11.glScalef(var3, var3, var3);
      GL11.glTranslatef(8.0F, 8.0F, 8.0F);
      GL11.glMatrixMode(5888);
      this.mc.getTextureManager().bindTexture(this.locationLightMap);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 10496);
      GL11.glTexParameteri(3553, 10243, 10496);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   private void updateTorchFlicker() {
      this.torchFlickerDX = (float)((double)this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
      this.torchFlickerDY = (float)((double)this.torchFlickerDY + (Math.random() - Math.random()) * Math.random() * Math.random());
      this.torchFlickerDX = (float)((double)this.torchFlickerDX * 0.9D);
      this.torchFlickerDY = (float)((double)this.torchFlickerDY * 0.9D);
      this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0F;
      this.torchFlickerY += (this.torchFlickerDY - this.torchFlickerY) * 1.0F;
      this.lightmapUpdateNeeded = true;
   }

   private void updateLightmap(float par1) {
      this.lightmapUpdateNeeded = false;
      WorldClient var2 = this.mc.theWorld;
      if(var2 != null && this.variableLongTemp[0] != this.variableLongTemp[1]) {
         this.variableLongTemp[1] = this.variableLongTemp[0];
         if(this.variableLongTemp[3] > System.currentTimeMillis()) {
            if(!this.variableBooleanTemp[0]) {
               if(this.variableLongTemp[2] != (long)this.rendererUpdateCount) {
                  this.variableLongTemp[2] = (long)this.rendererUpdateCount;
                  this.variableBooleanTemp[0] = true;
               }

               return;
            }

            if(this.variableLongTemp[2] != (long)this.rendererUpdateCount) {
               this.variableLongTemp[2] = (long)this.rendererUpdateCount;
            } else {
               this.variableBooleanTemp[0] = false;
            }
         }

         this.variableLongTemp[3] = System.currentTimeMillis() + 50L;
         float var3 = this.lightRendererConfig[20] != 0?(float)this.lightRendererConfig[20] / 127.0F:1.0F;
         float var4 = this.lightRendererConfig[21] != 0?(float)this.lightRendererConfig[21] / 127.0F:0.0F;
         float var5 = this.lightRendererConfig[22] != 0?(float)this.lightRendererConfig[22] / 127.0F:1.0F;
         float[] var6 = var2.provider.lightBrightnessTable;
         float var7 = var2.getSunBrightness(1.0F) * 0.95F + 0.05F;
         float var8 = var2.getSunBrightness(1.0F) * 0.65F + 0.35F;
         boolean var9 = this.mc.thePlayer.isPotionActive(Potion.nightVision);
         float var10 = var9?this.getNightVisionBrightness(this.mc.thePlayer, par1):0.0F;
         float var11 = 0.0F;
         float var12 = 0.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = 0.0F;
         float var16 = 0.0F;
         float var17 = 0.0F;

         for(int var18 = 0; var18 < 256; ++var18) {
            var11 = var2.lastLightningBolt > 0?var6[var18 / 16]:var6[var18 / 16] * var7;
            var12 = var6[var18 % 16] * (this.torchFlickerX * 0.1F + 1.5F);
            var13 = var12 * ((var12 * 0.6F + 0.4F) * 0.6F + 0.4F);
            var14 = var12 * (var12 * var12 * 0.6F + 0.4F);
            var15 = (var11 * var8 + var12) * 0.96F + 0.03F;
            var16 = (var11 * var8 + var13) * 0.96F + 0.03F;
            var17 = (var11 + var14) * 0.96F + 0.03F;
            if(this.field_82831_U > 0.0F) {
               var11 = this.field_82832_V + (this.field_82831_U - this.field_82832_V) * par1;
               var15 = var15 * (1.0F - var11) + var15 * 0.7F * var11;
               var16 = var16 * (1.0F - var11) + var16 * 0.6F * var11;
               var17 = var17 * (1.0F - var11) + var17 * 0.6F * var11;
            }

            if(var2.provider.dimensionId == 1) {
               var15 = 0.22F + var12 * 0.75F;
               var16 = 0.28F + var13 * 0.75F;
               var17 = 0.25F + var14 * 0.75F;
            }

            if(var9) {
               var12 = 1.0F / var15;
               if(var12 > 1.0F / var16) {
                  var12 = 1.0F / var16;
               }

               if(var12 > 1.0F / var17) {
                  var12 = 1.0F / var17;
               }

               var15 = var15 * (1.0F - var10) + var15 * var12 * var10;
               var16 = var16 * (1.0F - var10) + var16 * var12 * var10;
               var17 = var17 * (1.0F - var10) + var17 * var12 * var10;
            }

            if(var15 > 1.0F) {
               var15 = 1.0F;
            }

            if(var16 > 1.0F) {
               var16 = 1.0F;
            }

            if(var17 > 1.0F) {
               var17 = 1.0F;
            }

            var11 = this.mc.gameSettings.gammaSetting;
            var12 = 1.0F - var15;
            var13 = 1.0F - var16;
            var14 = 1.0F - var17;
            var12 = 1.0F - var12 * var12 * var12 * var12;
            var13 = 1.0F - var13 * var13 * var13 * var13;
            var14 = 1.0F - var14 * var14 * var14 * var14;
            var15 = (var15 * (1.0F - var11) + var12 * var11) * 0.96F + 0.03F;
            var16 = (var16 * (1.0F - var11) + var13 * var11) * 0.96F + 0.03F;
            var17 = (var17 * (1.0F - var11) + var14 * var11) * 0.96F + 0.03F;
            var15 = var15 * var3 * (var5 - var4) + var4;
            var16 = var16 * var3 * (var5 - var4) + var4;
            var17 = var17 * var3 * (var5 - var4) + var4;
            if(var15 > 1.0F) {
               var15 = 1.0F;
            } else if(var15 < 0.0F) {
               var15 = 0.0F;
            }

            if(var16 > 1.0F) {
               var16 = 1.0F;
            } else if(var16 < 0.0F) {
               var16 = 0.0F;
            }

            if(var17 > 1.0F) {
               var17 = 1.0F;
            } else if(var17 < 0.0F) {
               var17 = 0.0F;
            }

            this.lightmapColors[var18] = -16777216 | (int)(var15 * 255.0F) << 16 | (int)(var16 * 255.0F) << 8 | (int)(var17 * 255.0F);
         }

         this.lightmapTexture.updateDynamicTexture();
         this.lightmapUpdateNeeded = false;
      }
   }

   private float getNightVisionBrightness(EntityPlayer par1EntityPlayer, float par2) {
      int var3 = par1EntityPlayer.getActivePotionEffect(Potion.nightVision).getDuration();
      return var3 > 200?1.0F:0.7F + MathHelper.sin(((float)var3 - par2) * 3.1415927F * 0.2F) * 0.3F;
   }

   public void updateCameraAndRender(float par1) {
      this.mc.mcProfiler.startSection("lightTex");
      if(this.lightmapUpdateNeeded) {
         this.updateLightmap(par1);
      }

      this.mc.mcProfiler.endSection();
      boolean var2 = Display.isActive();
      if(!var2 && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
         if(Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
            this.mc.displayInGameMenu();
         }
      } else {
         this.prevFrameTime = Minecraft.getSystemTime();
      }

      this.mc.mcProfiler.startSection("mouse");
      if(this.mc.inGameHasFocus && var2) {
         this.mc.mouseHelper.mouseXYChange();
         float var3 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float var4 = var3 * var3 * var3 * 8.0F;
         float var5 = (float)this.mc.mouseHelper.deltaX * var4;
         float var6 = (float)this.mc.mouseHelper.deltaY * var4;
         byte var7 = 1;
         if(this.mc.gameSettings.invertMouse) {
            var7 = -1;
         }

         if(this.mc.gameSettings.smoothCamera) {
            this.smoothCamYaw += var5;
            this.smoothCamPitch += var6;
            float var8 = par1 - this.smoothCamPartialTicks;
            this.smoothCamPartialTicks = par1;
            var5 = this.smoothCamFilterX * var8;
            var6 = this.smoothCamFilterY * var8;
            this.mc.thePlayer.setAngles(var5, var6 * (float)var7);
         } else {
            this.mc.thePlayer.setAngles(var5, var6 * (float)var7);
         }
      }

      this.mc.mcProfiler.endSection();
      if(!this.mc.skipRenderWorld) {
         anaglyphEnable = this.mc.gameSettings.anaglyph;
         ScaledResolution var13 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
         int var14 = var13.getScaledWidth();
         int var15 = var13.getScaledHeight();
         int var16 = Mouse.getX() * var14 / this.mc.displayWidth;
         int var17 = var15 - Mouse.getY() * var15 / this.mc.displayHeight - 1;
         int var18 = performanceToFps(this.mc.gameSettings.limitFramerate);
         if(this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("level");
            if(this.mc.gameSettings.limitFramerate == 0) {
               this.renderWorld(par1, 0L);
            } else {
               this.renderWorld(par1, this.renderEndNanoTime + (long)(1000000000 / var18));
            }

            this.renderEndNanoTime = System.nanoTime();
            this.mc.mcProfiler.endStartSection("gui");
            if(!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
               this.mc.ingameGUI.renderGameOverlay(par1, this.mc.currentScreen != null, var16, var17);
            }

            this.mc.mcProfiler.endSection();
         } else {
            GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            this.setupOverlayRendering();
            this.renderEndNanoTime = System.nanoTime();
         }

         if(this.mc.currentScreen != null) {
            GL11.glClear(256);

            try {
               this.mc.currentScreen.drawScreen(var16, var17, par1);
            } catch (Throwable var12) {
               CrashReport var10 = CrashReport.makeCrashReport(var12, "Rendering screen");
               CrashReportCategory var11 = var10.makeCategory("Screen render details");
               var11.addCrashSectionCallable("Screen name", new CallableScreenName(this));
               var11.addCrashSectionCallable("Mouse location", new CallableMouseLocation(this, var16, var17));
               var11.addCrashSectionCallable("Screen size", new CallableScreenSize(this, var13));
               throw new ReportedException(var10);
            }
         }
      }

   }

   public void renderWorld(float par1, long par2) {
      this.mc.mcProfiler.startSection("lightTex");
      if(this.lightmapUpdateNeeded) {
         this.updateLightmap(par1);
      }

      GL11.glEnable(2884);
      GL11.glEnable(2929);
      if(this.mc.renderViewEntity == null) {
         this.mc.renderViewEntity = this.mc.thePlayer;
      }

      this.mc.mcProfiler.endStartSection("pick");
      this.getMouseOver(par1);
      EntityLivingBase var4 = this.mc.renderViewEntity;
      RenderGlobal var5 = this.mc.renderGlobal;
      EffectRenderer var6 = this.mc.effectRenderer;
      double var7 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)par1;
      double var9 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)par1;
      double var11 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)par1;
      this.mc.mcProfiler.endStartSection("center");

      for(int var13 = 0; var13 < 2; ++var13) {
         if(this.mc.gameSettings.anaglyph) {
            anaglyphField = var13;
            if(anaglyphField == 0) {
               GL11.glColorMask(false, true, true, false);
            } else {
               GL11.glColorMask(true, false, false, false);
            }
         }

         this.mc.mcProfiler.endStartSection("clear");
         GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
         this.updateFogColor(par1);
         GL11.glClear(16640);
         GL11.glEnable(2884);
         this.mc.mcProfiler.endStartSection("camera");
         this.setupCameraTransform(par1, var13);
         ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
         this.mc.mcProfiler.endStartSection("frustrum");
         ClippingHelperImpl.getInstance();
         if(this.mc.gameSettings.renderDistance < 2) {
            this.setupFog(-1, par1);
            this.mc.mcProfiler.endStartSection("sky");
            var5.renderSky(par1);
         }

         GL11.glEnable(2912);
         this.setupFog(1, par1);
         if(this.mc.gameSettings.ambientOcclusion != 0) {
            GL11.glShadeModel(7425);
         }

         this.mc.mcProfiler.endStartSection("culling");
         Frustrum var14 = new Frustrum();
         var14.setPosition(var7, var9, var11);
         this.mc.renderGlobal.clipRenderersByFrustum(var14, par1);
         if(var13 == 0) {
            this.mc.mcProfiler.endStartSection("updatechunks");

            while(!this.mc.renderGlobal.updateRenderers(var4, false) && par2 != 0L) {
               long var15 = par2 - System.nanoTime();
               if(var15 < 0L || var15 > 1000000000L) {
                  break;
               }
            }
         }

         if(var4.posY < 128.0D) {
            this.renderCloudsCheck(var5, par1);
         }

         this.mc.mcProfiler.endStartSection("prepareterrain");
         this.setupFog(0, par1);
         GL11.glEnable(2912);
         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         RenderHelper.disableStandardItemLighting();
         this.mc.mcProfiler.endStartSection("terrain");
         var5.sortAndRender(var4, 0, (double)par1);
         GL11.glShadeModel(7424);
         EntityPlayer var17;
         if(this.debugViewDirection == 0) {
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            var5.renderEntities(var4.getPosition(par1), var14, par1);
            this.enableLightmap((double)par1);
            this.mc.mcProfiler.endStartSection("litParticles");
            var6.renderLitParticles(var4, par1);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0, par1);
            this.mc.mcProfiler.endStartSection("particles");
            var6.renderParticles(var4, par1);
            this.disableLightmap((double)par1);
            if(this.mc.objectMouseOver != null && var4.isInsideOfMaterial(Material.water) && var4 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI) {
               var17 = (EntityPlayer)var4;
               GL11.glDisable(3008);
               this.mc.mcProfiler.endStartSection("outline");
               var5.drawSelectionBox(var17, this.mc.objectMouseOver, 0, par1);
               GL11.glEnable(3008);
            }
         }

         GL11.glDisable(3042);
         GL11.glEnable(2884);
         GL11.glBlendFunc(770, 771);
         GL11.glDepthMask(true);
         this.setupFog(0, par1);
         GL11.glEnable(3042);
         GL11.glDisable(2884);
         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         if(this.mc.gameSettings.fancyGraphics) {
            this.mc.mcProfiler.endStartSection("water");
            if(this.mc.gameSettings.ambientOcclusion != 0) {
               GL11.glShadeModel(7425);
            }

            GL11.glColorMask(false, false, false, false);
            int var16 = var5.sortAndRender(var4, 1, (double)par1);
            if(this.mc.gameSettings.anaglyph) {
               if(anaglyphField == 0) {
                  GL11.glColorMask(false, true, true, true);
               } else {
                  GL11.glColorMask(true, false, false, true);
               }
            } else {
               GL11.glColorMask(true, true, true, true);
            }

            if(var16 > 0) {
               var5.renderAllRenderLists(1, (double)par1);
            }

            GL11.glShadeModel(7424);
         } else {
            this.mc.mcProfiler.endStartSection("water");
            var5.sortAndRender(var4, 1, (double)par1);
         }

         GL11.glDepthMask(true);
         GL11.glEnable(2884);
         GL11.glDisable(3042);
         if(this.cameraZoom == 1.0D && var4 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI && this.mc.objectMouseOver != null && !var4.isInsideOfMaterial(Material.water)) {
            var17 = (EntityPlayer)var4;
            GL11.glDisable(3008);
            this.mc.mcProfiler.endStartSection("outline");
            var5.drawSelectionBox(var17, this.mc.objectMouseOver, 0, par1);
            GL11.glEnable(3008);
         }

         this.mc.mcProfiler.endStartSection("destroyProgress");
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 1);
         var5.drawBlockDamageTexture(Tessellator.instance, (EntityPlayer)var4, par1);
         GL11.glDisable(3042);
         this.mc.mcProfiler.endStartSection("weather");
         this.renderRainSnow(par1);
         GL11.glDisable(2912);
         if(var4.posY >= 128.0D) {
            this.renderCloudsCheck(var5, par1);
         }

         this.mc.mcProfiler.endStartSection("hand");
         if(this.cameraZoom == 1.0D) {
            GL11.glClear(256);
            this.renderHand(par1, var13);
         }

         if(!this.mc.gameSettings.anaglyph) {
            this.mc.mcProfiler.endSection();
            return;
         }
      }

      GL11.glColorMask(true, true, true, false);
      this.mc.mcProfiler.endSection();
   }

   private void renderCloudsCheck(RenderGlobal par1RenderGlobal, float par2) {
      if(this.mc.gameSettings.shouldRenderClouds()) {
         this.mc.mcProfiler.endStartSection("clouds");
         GL11.glPushMatrix();
         this.setupFog(0, par2);
         GL11.glEnable(2912);
         par1RenderGlobal.renderClouds(par2);
         GL11.glDisable(2912);
         this.setupFog(1, par2);
         GL11.glPopMatrix();
      }

   }

   private void addRainParticles() {
      float var1 = this.mc.theWorld.getRainStrength(1.0F);
      if(!this.mc.gameSettings.fancyGraphics) {
         var1 /= 2.0F;
      }

      if(var1 != 0.0F) {
         this.random.setSeed((long)this.rendererUpdateCount * 312987231L);
         EntityLivingBase var2 = this.mc.renderViewEntity;
         WorldClient var3 = this.mc.theWorld;
         int var4 = MathHelper.floor_double(var2.posX);
         int var5 = MathHelper.floor_double(var2.posY);
         int var6 = MathHelper.floor_double(var2.posZ);
         byte var7 = 10;
         double var8 = 0.0D;
         double var10 = 0.0D;
         double var12 = 0.0D;
         int var14 = 0;
         int var15 = (int)(100.0F * var1 * var1);
         if(this.mc.gameSettings.particleSetting == 1) {
            var15 >>= 1;
         } else if(this.mc.gameSettings.particleSetting == 2) {
            var15 = 0;
         }

         for(int var16 = 0; var16 < var15; ++var16) {
            int var17 = var4 + this.random.nextInt(var7) - this.random.nextInt(var7);
            int var18 = var6 + this.random.nextInt(var7) - this.random.nextInt(var7);
            int var19 = var3.getPrecipitationHeight(var17, var18);
            int var20 = var3.getBlockId(var17, var19 - 1, var18);
            BiomeGenBase var21 = var3.getBiomeGenForCoords(var17, var18);
            if(var19 <= var5 + var7 && var19 >= var5 - var7 && var21.canSpawnLightningBolt() && var21.getFloatTemperature() >= 0.2F) {
               float var22 = this.random.nextFloat();
               float var23 = this.random.nextFloat();
               if(var20 > 0) {
                  if(Block.blocksList[var20].blockMaterial == Material.lava) {
                     this.mc.effectRenderer.addEffect(new EntitySmokeFX(var3, (double)((float)var17 + var22), (double)((float)var19 + 0.1F) - Block.blocksList[var20].getBlockBoundsMinY(), (double)((float)var18 + var23), 0.0D, 0.0D, 0.0D));
                  } else {
                     ++var14;
                     if(this.random.nextInt(var14) == 0) {
                        var8 = (double)((float)var17 + var22);
                        var10 = (double)((float)var19 + 0.1F) - Block.blocksList[var20].getBlockBoundsMinY();
                        var12 = (double)((float)var18 + var23);
                     }

                     this.mc.effectRenderer.addEffect(new EntityRainFX(var3, (double)((float)var17 + var22), (double)((float)var19 + 0.1F) - Block.blocksList[var20].getBlockBoundsMinY(), (double)((float)var18 + var23)));
                  }
               }
            }
         }

         if(var14 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
            this.rainSoundCounter = 0;
            if(var10 > var2.posY + 1.0D && var3.getPrecipitationHeight(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posZ)) > MathHelper.floor_double(var2.posY)) {
               this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.1F, 0.5F, false);
            } else {
               this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.2F, 1.0F, false);
            }
         }
      }

   }

   protected void renderRainSnow(float par1) {
      float var2 = this.mc.theWorld.getRainStrength(par1);
      if(var2 > 0.0F) {
         this.enableLightmap((double)par1);
         if(this.rainXCoords == null) {
            this.rainXCoords = new float[1024];
            this.rainYCoords = new float[1024];

            for(int var3 = 0; var3 < 32; ++var3) {
               for(int var4 = 0; var4 < 32; ++var4) {
                  float var5 = (float)(var4 - 16);
                  float var6 = (float)(var3 - 16);
                  float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
                  this.rainXCoords[var3 << 5 | var4] = -var6 / var7;
                  this.rainYCoords[var3 << 5 | var4] = var5 / var7;
               }
            }
         }

         EntityLivingBase var41 = this.mc.renderViewEntity;
         WorldClient var42 = this.mc.theWorld;
         int var43 = MathHelper.floor_double(var41.posX);
         int var44 = MathHelper.floor_double(var41.posY);
         int var45 = MathHelper.floor_double(var41.posZ);
         Tessellator var8 = Tessellator.instance;
         GL11.glDisable(2884);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glAlphaFunc(516, 0.01F);
         this.mc.getTextureManager().bindTexture(locationSnowPng);
         double var9 = var41.lastTickPosX + (var41.posX - var41.lastTickPosX) * (double)par1;
         double var11 = var41.lastTickPosY + (var41.posY - var41.lastTickPosY) * (double)par1;
         double var13 = var41.lastTickPosZ + (var41.posZ - var41.lastTickPosZ) * (double)par1;
         int var15 = MathHelper.floor_double(var11);
         byte var16 = 5;
         if(this.mc.gameSettings.fancyGraphics) {
            var16 = 10;
         }

         boolean var17 = false;
         byte var18 = -1;
         float var19 = (float)this.rendererUpdateCount + par1;
         if(this.mc.gameSettings.fancyGraphics) {
            var16 = 10;
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         var17 = false;

         for(int var20 = var45 - var16; var20 <= var45 + var16; ++var20) {
            for(int var21 = var43 - var16; var21 <= var43 + var16; ++var21) {
               int var22 = (var20 - var45 + 16) * 32 + var21 - var43 + 16;
               float var23 = this.rainXCoords[var22] * 0.5F;
               float var24 = this.rainYCoords[var22] * 0.5F;
               BiomeGenBase var25 = var42.getBiomeGenForCoords(var21, var20);
               if(var25.canSpawnLightningBolt() || var25.getEnableSnow()) {
                  int var26 = var42.getPrecipitationHeight(var21, var20);
                  int var27 = var44 - var16;
                  int var28 = var44 + var16;
                  if(var27 < var26) {
                     var27 = var26;
                  }

                  if(var28 < var26) {
                     var28 = var26;
                  }

                  float var29 = 1.0F;
                  int var30 = var26;
                  if(var26 < var15) {
                     var30 = var15;
                  }

                  if(var27 != var28) {
                     this.random.setSeed((long)(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761));
                     float var31 = var25.getFloatTemperature();
                     double var32;
                     float var34;
                     if(var42.getWorldChunkManager().getTemperatureAtHeight(var31, var26) >= 0.15F) {
                        if(var18 != 0) {
                           if(var18 >= 0) {
                              var8.draw();
                           }

                           var18 = 0;
                           this.mc.getTextureManager().bindTexture(locationRainPng);
                           var8.startDrawingQuads();
                        }

                        var34 = ((float)(this.rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 31) + par1) / 32.0F * (3.0F + this.random.nextFloat());
                        double var35 = (double)((float)var21 + 0.5F) - var41.posX;
                        var32 = (double)((float)var20 + 0.5F) - var41.posZ;
                        float var37 = MathHelper.sqrt_double(var35 * var35 + var32 * var32) / (float)var16;
                        float var38 = 1.0F;
                        var8.setBrightness(var42.getLightBrightnessForSkyBlocks(var21, var30, var20, 0));
                        var8.setColorRGBA_F(var38, var38, var38, ((1.0F - var37 * var37) * 0.5F + 0.5F) * var2);
                        var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
                        var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var27, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29), (double)((float)var27 * var29 / 4.0F + var34 * var29));
                        var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var27, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29), (double)((float)var27 * var29 / 4.0F + var34 * var29));
                        var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var28, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29), (double)((float)var28 * var29 / 4.0F + var34 * var29));
                        var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var28, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29), (double)((float)var28 * var29 / 4.0F + var34 * var29));
                        var8.setTranslation(0.0D, 0.0D, 0.0D);
                     } else {
                        if(var18 != 1) {
                           if(var18 >= 0) {
                              var8.draw();
                           }

                           var18 = 1;
                           this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/environment/snow.png"));
                           var8.startDrawingQuads();
                        }

                        var34 = ((float)(this.rendererUpdateCount & 511) + par1) / 512.0F;
                        float var46 = this.random.nextFloat() + var19 * 0.01F * (float)this.random.nextGaussian();
                        float var36 = this.random.nextFloat() + var19 * (float)this.random.nextGaussian() * 0.001F;
                        var32 = (double)((float)var21 + 0.5F) - var41.posX;
                        double var47 = (double)((float)var20 + 0.5F) - var41.posZ;
                        float var39 = MathHelper.sqrt_double(var32 * var32 + var47 * var47) / (float)var16;
                        float var40 = 1.0F;
                        var8.setBrightness((var42.getLightBrightnessForSkyBlocks(var21, var30, var20, 0) * 3 + 15728880) / 4);
                        var8.setColorRGBA_F(var40, var40, var40, ((1.0F - var39 * var39) * 0.3F + 0.5F) * var2);
                        var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
                        var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var27, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29 + var46), (double)((float)var27 * var29 / 4.0F + var34 * var29 + var36));
                        var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var27, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29 + var46), (double)((float)var27 * var29 / 4.0F + var34 * var29 + var36));
                        var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var28, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29 + var46), (double)((float)var28 * var29 / 4.0F + var34 * var29 + var36));
                        var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var28, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29 + var46), (double)((float)var28 * var29 / 4.0F + var34 * var29 + var36));
                        var8.setTranslation(0.0D, 0.0D, 0.0D);
                     }
                  }
               }
            }
         }

         if(var18 >= 0) {
            var8.draw();
         }

         GL11.glEnable(2884);
         GL11.glDisable(3042);
         GL11.glAlphaFunc(516, 0.1F);
         this.disableLightmap((double)par1);
      }

   }

   public void setupOverlayRendering() {
      ScaledResolution var1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
      GL11.glClear(256);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
   }

   private void updateFogColor(float par1) {
      WorldClient var2 = this.mc.theWorld;
      if(var2 != null && this.variableLongTemp[0] != this.variableLongTemp[4]) {
         this.variableLongTemp[4] = this.variableLongTemp[0];
         if(this.variableLongTemp[6] > System.currentTimeMillis()) {
            if(!this.variableBooleanTemp[1]) {
               if(this.variableLongTemp[5] != (long)this.rendererUpdateCount) {
                  this.variableLongTemp[5] = (long)this.rendererUpdateCount;
                  this.variableBooleanTemp[1] = true;
               }

               return;
            }

            if(this.variableLongTemp[5] != (long)this.rendererUpdateCount) {
               this.variableLongTemp[5] = (long)this.rendererUpdateCount;
            } else {
               this.variableBooleanTemp[1] = false;
            }
         }

         this.variableLongTemp[6] = System.currentTimeMillis() + 200L;
         EntityLivingBase var3 = this.mc.renderViewEntity;
         float var4 = 1.0F / (float)(4 - this.mc.gameSettings.renderDistance);
         var4 = 1.0F - (float)Math.pow((double)var4, 0.25D);
         Vec3 var5 = var2.getSkyColor(this.mc.renderViewEntity, par1);
         float var6 = (float)var5.xCoord;
         float var7 = (float)var5.yCoord;
         float var8 = (float)var5.zCoord;
         Vec3 var9 = var2.getFogColor(par1);
         this.fogColorRed = (float)var9.xCoord;
         this.fogColorGreen = (float)var9.yCoord;
         this.fogColorBlue = (float)var9.zCoord;
         float var10;
         if(this.mc.gameSettings.renderDistance < 2) {
            Vec3 var11 = MathHelper.sin(var2.getCelestialAngleRadians(par1)) > 0.0F?var2.getWorldVec3Pool().getVecFromPool(-1.0D, 0.0D, 0.0D):var2.getWorldVec3Pool().getVecFromPool(1.0D, 0.0D, 0.0D);
            var10 = (float)var3.getLook(par1).dotProduct(var11);
            if(var10 < 0.0F) {
               var10 = 0.0F;
            }

            if(var10 > 0.0F) {
               float[] var12 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(par1), par1);
               if(var12 != null) {
                  var10 *= var12[3];
                  this.fogColorRed = this.fogColorRed * (1.0F - var10) + var12[0] * var10;
                  this.fogColorGreen = this.fogColorGreen * (1.0F - var10) + var12[1] * var10;
                  this.fogColorBlue = this.fogColorBlue * (1.0F - var10) + var12[2] * var10;
               }
            }
         }

         this.fogColorRed += (var6 - this.fogColorRed) * var4;
         this.fogColorGreen += (var7 - this.fogColorGreen) * var4;
         this.fogColorBlue += (var8 - this.fogColorBlue) * var4;
         float var22 = var2.getRainStrength(par1);
         float var23;
         if(var22 > 0.0F) {
            var10 = 1.0F - var22 * 0.5F;
            var23 = 1.0F - var22 * 0.4F;
            this.fogColorRed *= var10;
            this.fogColorGreen *= var10;
            this.fogColorBlue *= var23;
         }

         var10 = var2.getWeightedThunderStrength(par1);
         if(var10 > 0.0F) {
            var23 = 1.0F - var10 * 0.5F;
            this.fogColorRed *= var23;
            this.fogColorGreen *= var23;
            this.fogColorBlue *= var23;
         }

         int var13 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(this.mc.theWorld, var3, par1);
         float var14;
         if(this.cloudFog) {
            Vec3 var15 = var2.getCloudColour(par1);
            this.fogColorRed = (float)var15.xCoord;
            this.fogColorGreen = (float)var15.yCoord;
            this.fogColorBlue = (float)var15.zCoord;
         } else if(var13 != 0 && Block.blocksList[var13].blockMaterial == Material.water) {
            var14 = (float)EnchantmentHelper.getRespiration(var3) * 0.2F;
            this.fogColorRed = 0.02F + var14;
            this.fogColorGreen = 0.02F + var14;
            this.fogColorBlue = 0.2F + var14;
         } else if(var13 != 0 && Block.blocksList[var13].blockMaterial == Material.lava) {
            this.fogColorRed = 0.6F;
            this.fogColorGreen = 0.1F;
            this.fogColorBlue = 0.0F;
         }

         var14 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * par1;
         this.fogColorRed *= var14;
         this.fogColorGreen *= var14;
         this.fogColorBlue *= var14;
         double var24 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)par1) * var2.provider.getVoidFogYFactor();
         if(var3.isPotionActive(Potion.blindness)) {
            int var17 = var3.getActivePotionEffect(Potion.blindness).getDuration();
            if(var17 < 20) {
               var24 *= (double)(1.0F - (float)var17 / 20.0F);
            } else {
               var24 = 0.0D;
            }
         }

         if(var24 < 1.0D) {
            if(var24 < 0.0D) {
               var24 = 0.0D;
            }

            var24 *= var24;
            this.fogColorRed = (float)((double)this.fogColorRed * var24);
            this.fogColorGreen = (float)((double)this.fogColorGreen * var24);
            this.fogColorBlue = (float)((double)this.fogColorBlue * var24);
         }

         float var25;
         if(this.field_82831_U > 0.0F) {
            var25 = this.field_82832_V + (this.field_82831_U - this.field_82832_V) * par1;
            this.fogColorRed = this.fogColorRed * (1.0F - var25) + this.fogColorRed * 0.7F * var25;
            this.fogColorGreen = this.fogColorGreen * (1.0F - var25) + this.fogColorGreen * 0.6F * var25;
            this.fogColorBlue = this.fogColorBlue * (1.0F - var25) + this.fogColorBlue * 0.6F * var25;
         }

         float var18;
         if(var3.isPotionActive(Potion.nightVision)) {
            var25 = this.getNightVisionBrightness(this.mc.thePlayer, par1);
            var18 = 1.0F / this.fogColorRed;
            if(var18 > 1.0F / this.fogColorGreen) {
               var18 = 1.0F / this.fogColorGreen;
            }

            if(var18 > 1.0F / this.fogColorBlue) {
               var18 = 1.0F / this.fogColorBlue;
            }

            this.fogColorRed = this.fogColorRed * (1.0F - var25) + this.fogColorRed * var18 * var25;
            this.fogColorGreen = this.fogColorGreen * (1.0F - var25) + this.fogColorGreen * var18 * var25;
            this.fogColorBlue = this.fogColorBlue * (1.0F - var25) + this.fogColorBlue * var18 * var25;
         }

         float var19;
         if(this.mc.gameSettings.anaglyph) {
            var25 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
            var18 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
            var19 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
            this.fogColorRed = var25;
            this.fogColorGreen = var18;
            this.fogColorBlue = var19;
         }

         var19 = this.lightRendererConfig[23] != 0?(float)this.lightRendererConfig[23] * 10.0F / 127.0F:1.0F;
         float var20 = this.lightRendererConfig[24] != 0?(float)this.lightRendererConfig[24] * 10.0F / 127.0F:0.0F;
         float var21 = this.lightRendererConfig[25] != 0?(float)this.lightRendererConfig[25] * 10.0F / 127.0F:1.0F;
         this.fogColorRed = this.fogColorRed * var19 * (var21 - var20) + var20;
         this.fogColorGreen = this.fogColorGreen * var19 * (var21 - var20) + var20;
         this.fogColorBlue = this.fogColorBlue * var19 * (var21 - var20) + var20;
         GL11.glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
      }
   }

   private void setupFog(int par1, float par2) {
      EntityLivingBase var3 = this.mc.renderViewEntity;
      boolean var4 = false;
      if(var3 instanceof EntityPlayer) {
         var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
      }

      if(par1 == 999) {
         GL11.glFog(2918, this.setFogColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
         GL11.glFogi(2917, 9729);
         GL11.glFogf(2915, 0.0F);
         GL11.glFogf(2916, 8.0F);
         if(GLContext.getCapabilities().GL_NV_fog_distance) {
            GL11.glFogi('\u855a', '\u855b');
         }

         GL11.glFogf(2915, 0.0F);
      } else {
         GL11.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
         GL11.glNormal3f(0.0F, -1.0F, 0.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int var5 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(this.mc.theWorld, var3, par2);
         float var6;
         if(var3.isPotionActive(Potion.blindness)) {
            var6 = 5.0F;
            int var7 = var3.getActivePotionEffect(Potion.blindness).getDuration();
            if(var7 < 20) {
               var6 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - (float)var7 / 20.0F);
            }

            GL11.glFogi(2917, 9729);
            if(par1 < 0) {
               GL11.glFogf(2915, 0.0F);
               GL11.glFogf(2916, var6 * 0.8F);
            } else {
               GL11.glFogf(2915, var6 * 0.25F);
               GL11.glFogf(2916, var6);
            }

            if(GLContext.getCapabilities().GL_NV_fog_distance) {
               GL11.glFogi('\u855a', '\u855b');
            }
         } else if(this.cloudFog) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1F);
         } else if(var5 > 0 && Block.blocksList[var5].blockMaterial == Material.water) {
            GL11.glFogi(2917, 2048);
            if(var3.isPotionActive(Potion.waterBreathing)) {
               GL11.glFogf(2914, 0.05F);
            } else {
               GL11.glFogf(2914, 0.1F - (float)EnchantmentHelper.getRespiration(var3) * 0.03F);
            }
         } else if(var5 > 0 && Block.blocksList[var5].blockMaterial == Material.lava) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 2.0F);
         } else {
            var6 = this.farPlaneDistance;
            float var9;
            if(this.mc.theWorld.provider.getWorldHasVoidParticles() && !var4) {
               double var12 = (double)((var3.getBrightnessForRender(par2) & 15728640) >> 20) / 16.0D + (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)par2 + 4.0D) / 32.0D;
               if(var12 < 1.0D) {
                  if(var12 < 0.0D) {
                     var12 = 0.0D;
                  }

                  var12 *= var12;
                  var9 = 100.0F * (float)var12;
                  if(var9 < 5.0F) {
                     var9 = 5.0F;
                  }

                  if(var6 > var9) {
                     var6 = var9;
                  }
               }
            }

            GL11.glFogi(2917, 9729);
            float var13 = 0.25F;
            if(this.variableBooleanTemp[3]) {
               GL11.glFogi('\u855a', '\u855b');
            }

            float var8 = var6;
            var9 = par1 < 0?0.0F:var13;
            float var10 = par1 < 0?0.8F:1.0F;
            float var11 = this.lightRendererConfig[101] >= 1?(float)this.lightRendererConfig[101] - 1.0F:1.0F;
            if(this.mc.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
               var8 = this.farPlaneDistance;
               var9 = 0.05F;
               var10 = 1.0F;
            }

            if((float)(3 - this.mc.gameSettings.renderDistance) < var11) {
               var8 = this.farPlaneDistance;
               var9 = 2.0F;
               var10 = 4.0F;
            }

            GL11.glFogf(2915, var8 * var9);
            GL11.glFogf(2916, var8 * var10);
         }

         GL11.glEnable(2903);
         GL11.glColorMaterial(1028, 4608);
      }

   }

   private FloatBuffer setFogColorBuffer(float par1, float par2, float par3, float par4) {
      this.fogColorBuffer.clear();
      this.fogColorBuffer.put(par1).put(par2).put(par3).put(par4);
      this.fogColorBuffer.flip();
      return this.fogColorBuffer;
   }

   public static int performanceToFps(int par0) {
      short var1 = 200;
      if(par0 == 1) {
         var1 = 120;
      }

      if(par0 == 2) {
         var1 = 35;
      }

      return var1;
   }

   static Minecraft getRendererMinecraft(EntityRenderer par0EntityRenderer) {
      return par0EntityRenderer.mc;
   }

}
