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

   public World field_78924_a;
   private int field_78942_y = -1;
   private static Tessellator field_78941_z = Tessellator.field_78398_a;
   public static int field_78922_b;
   public int field_78923_c;
   public int field_78920_d;
   public int field_78921_e;
   public int field_78918_f;
   public int field_78919_g;
   public int field_78931_h;
   public int field_78932_i;
   public int field_78929_j;
   public int field_78930_k;
   public boolean field_78927_l;
   public boolean[] field_78928_m = new boolean[2];
   public int field_78925_n;
   public int field_78926_o;
   public int field_78940_p;
   public boolean field_78939_q;
   public AxisAlignedBB field_78938_r;
   public int field_78937_s;
   public boolean field_78936_t = true;
   public boolean field_78935_u;
   public int field_78934_v;
   public boolean field_78933_w;
   private boolean field_78915_A;
   public List field_78943_x = new ArrayList();
   private List field_78916_B;
   private int field_78917_C;
   protected boolean needsBoxUpdate = false;
   private final boolean skipAllRenderAccessFirst = false;
   private boolean skipAllRenderAccess = false;
   private static final boolean[] blockExist;
   private static final boolean[] blockTransparent;
   private boolean[] renderWorkList = new boolean[2];
   private static Minecraft minecraft;


   public WorldRenderer(World p_i1240_1_, List p_i1240_2_, int p_i1240_3_, int p_i1240_4_, int p_i1240_5_, int p_i1240_6_) {
      this.field_78924_a = p_i1240_1_;
      this.field_78916_B = p_i1240_2_;
      this.field_78942_y = p_i1240_6_;
      this.field_78923_c = -999;
      this.func_78913_a(p_i1240_3_, p_i1240_4_, p_i1240_5_);
      this.field_78939_q = false;
   }

   public void func_78913_a(int p_78913_1_, int p_78913_2_, int p_78913_3_) {
      if(p_78913_1_ != this.field_78923_c || p_78913_2_ != this.field_78920_d || p_78913_3_ != this.field_78921_e) {
         this.func_78910_b();
         this.field_78923_c = p_78913_1_;
         this.field_78920_d = p_78913_2_;
         this.field_78921_e = p_78913_3_;
         this.field_78925_n = p_78913_1_ + 8;
         this.field_78926_o = p_78913_2_ + 8;
         this.field_78940_p = p_78913_3_ + 8;
         this.field_78932_i = p_78913_1_ & 1023;
         this.field_78929_j = p_78913_2_;
         this.field_78930_k = p_78913_3_ & 1023;
         this.field_78918_f = p_78913_1_ - this.field_78932_i;
         this.field_78919_g = p_78913_2_ - this.field_78929_j;
         this.field_78931_h = p_78913_3_ - this.field_78930_k;
         if(this.field_78938_r != null) {
            this.field_78938_r.func_72324_b((double)p_78913_1_, (double)p_78913_2_, (double)p_78913_3_, (double)p_78913_1_ + 16.0D, (double)p_78913_2_ + 16.0D, (double)p_78913_3_ + 16.0D);
         } else {
            this.field_78938_r = AxisAlignedBB.func_72330_a((double)p_78913_1_, (double)p_78913_2_, (double)p_78913_3_, (double)p_78913_1_ + 16.0D, (double)p_78913_2_ + 16.0D, (double)p_78913_3_ + 16.0D);
         }

         this.needsBoxUpdate = true;
         this.skipAllRenderAccess = false;
         this.func_78914_f();
      }

   }

   private void func_78905_g() {
      GL11.glTranslatef((float)this.field_78932_i, (float)this.field_78929_j, (float)this.field_78930_k);
   }

   public void func_78907_a() {
      if(this.field_78939_q) {
         this.field_78939_q = false;
         int var1 = this.field_78923_c;
         int var2 = this.field_78920_d;
         int var3 = this.field_78921_e;
         int var4 = this.field_78923_c + 16;
         int var5 = this.field_78920_d + 16;
         int var6 = this.field_78921_e + 16;
         Chunk.field_76640_a = false;
         HashSet var7 = new HashSet();
         var7.addAll(this.field_78943_x);
         this.field_78943_x.clear();

         for(int var8 = 0; var8 < this.field_78928_m.length; ++var8) {
            this.field_78928_m[var8] = true;
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
         if(var2 >= 0 && var2 < 256 && !(var24 = this.field_78924_a.func_72964_e(var1 >> 4, var3 >> 4)).func_76621_g() && (var10 = var24.func_76587_i()[var2 >> 4]) != null && !(var9 = new ChunkCache(this.field_78924_a, var1 - 1, var2 - 1, var3 - 1, var4 + 1, var5 + 1, var6 + 1, 1)).func_72806_N()) {
            if(var11 && var2 - 1 >= 0) {
               var26 = var1;

               for(var27 = var1 + 16; var26 < var27; ++var26) {
                  var28 = var3;

                  for(var29 = var3 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.func_72798_a(var26, var2 - 1, var28)]) {
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
                     if(!blockTransparent[var9.func_72798_a(var26, var5, var28)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.field_78924_a.func_72964_e(var1 - 1 >> 4, var3 >> 4).func_76621_g()) {
               var26 = var2;

               for(var27 = var2 + 16; var26 < var27; ++var26) {
                  var28 = var3;

                  for(var29 = var3 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.func_72798_a(var1 - 1, var26, var28)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.field_78924_a.func_72964_e(var4 >> 4, var3 >> 4).func_76621_g()) {
               var26 = var2;

               for(var27 = var2 + 16; var26 < var27; ++var26) {
                  var28 = var3;

                  for(var29 = var3 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.func_72798_a(var4, var26, var28)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.field_78924_a.func_72964_e(var1 >> 4, var3 - 1 >> 4).func_76621_g()) {
               var26 = var1;

               for(var27 = var1 + 16; var26 < var27; ++var26) {
                  var28 = var2;

                  for(var29 = var2 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.func_72798_a(var26, var28, var3 - 1)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && !this.field_78924_a.func_72964_e(var1 >> 4, var6 >> 4).func_76621_g()) {
               var26 = var1;

               for(var27 = var1 + 16; var26 < var27; ++var26) {
                  var28 = var2;

                  for(var29 = var2 + 16; var28 < var29; ++var28) {
                     if(!blockTransparent[var9.func_72798_a(var26, var28, var6)]) {
                        var11 = false;
                        var26 = var27;
                        var28 = var29;
                     }
                  }
               }
            }

            if(var11 && minecraft.field_71451_h != null && var1 >> 4 == MathHelper.func_76128_c(minecraft.field_71451_h.field_70142_S / 16.0D) && var2 >> 4 == MathHelper.func_76128_c(minecraft.field_71451_h.field_70137_T / 16.0D) && var3 >> 4 == MathHelper.func_76128_c(minecraft.field_71451_h.field_70136_U / 16.0D)) {
               var11 = false;
            }
         }

         if(!var11) {
            ++field_78922_b;
            RenderBlocks var17 = new RenderBlocks(var9);
            this.field_78917_C = 0;
            Tessellator var18 = Tessellator.field_78398_a;

            for(int var25 = 0; var25 < 2; ++var25) {
               boolean var19 = true;
               boolean var20 = false;
               boolean var21 = var25 == 0;
               boolean var22 = false;
               GL11.glPushMatrix();
               var18.func_78382_b();
               var18.func_78373_b((double)(-this.field_78918_f), (double)(-this.field_78919_g), (double)(-this.field_78931_h));
               GL11.glNewList(this.field_78942_y + var25, 4864);
               if(var21) {
                  for(var26 = var2; var26 < var5; ++var26) {
                     for(var27 = var3; var27 < var6; ++var27) {
                        for(var28 = var1; var28 < var4; ++var28) {
                           if(blockExist[var29 = var10.func_76656_a(var28 - var1, var26 - var2, var27 - var3)]) {
                              if(Block.field_71973_m[var29].func_71887_s()) {
                                 TileEntity var23 = var9.func_72796_p(var28, var26, var27);
                                 if(TileEntityRenderer.field_76963_a.func_76952_a(var23)) {
                                    this.field_78943_x.add(var23);
                                 }
                              }

                              if(Block.field_71973_m[var29].func_71856_s_() == var25) {
                                 var20 |= var17.func_78612_b(Block.field_71973_m[var29], var28, var26, var27);
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
                           if(blockExist[var29 = var10.func_76656_a(var28 - var1, var26 - var2, var27 - var3)] && Block.field_71973_m[var29].func_71856_s_() == var25) {
                              var20 |= var17.func_78612_b(Block.field_71973_m[var29], var28, var26, var27);
                           }
                        }
                     }
                  }
               }

               this.field_78917_C += var18.func_78381_a();
               GL11.glEndList();
               this.renderWorkList[var25] = var20;
               var18.func_78373_b(0.0D, 0.0D, 0.0D);
               GL11.glPopMatrix();
               this.field_78928_m[var25] = !var20;
               if(var19) {
                  break;
               }
            }
         }

         HashSet var30 = new HashSet();
         var30.addAll(this.field_78943_x);
         var30.removeAll(var7);
         this.field_78916_B.addAll(var30);
         var7.removeAll(this.field_78943_x);
         this.field_78916_B.removeAll(var7);
         this.field_78933_w = Chunk.field_76640_a;
         this.skipAllRenderAccess = this.field_78928_m[0] && this.field_78928_m[1];
         if(this.needsBoxUpdate && !this.func_78906_e()) {
            GL11.glNewList(this.field_78942_y + 2, 4864);
            RenderItem.func_76980_a(AxisAlignedBB.func_72332_a().func_72299_a((double)((float)this.field_78932_i), (double)((float)this.field_78929_j), (double)((float)this.field_78930_k), (double)((float)this.field_78932_i + 16.0F), (double)((float)this.field_78929_j + 16.0F), (double)((float)this.field_78930_k + 16.0F)));
            GL11.glEndList();
            this.needsBoxUpdate = false;
         }

      }
   }

   public float func_78912_a(Entity p_78912_1_) {
      float var2 = (float)(p_78912_1_.field_70165_t - (double)this.field_78925_n);
      float var3 = (float)(p_78912_1_.field_70163_u - (double)this.field_78926_o);
      float var4 = (float)(p_78912_1_.field_70161_v - (double)this.field_78940_p);
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   public void func_78910_b() {
      if(!this.func_78906_e()) {
         GL11.glNewList(this.field_78942_y + 2, 4864);
         GL11.glEndList();
      }

      for(int var1 = 0; var1 < 2; ++var1) {
         if(this.renderWorkList == null || this.renderWorkList[var1]) {
            GL11.glNewList(this.field_78942_y + var1, 4864);
            GL11.glEndList();
            if(this.renderWorkList != null) {
               this.renderWorkList[var1] = false;
            }
         }

         this.field_78928_m[var1] = true;
      }

      this.field_78927_l = false;
      this.field_78915_A = false;
      this.skipAllRenderAccess = false;
   }

   public void func_78911_c() {
      this.func_78910_b();
      this.field_78924_a = null;
      this.field_78916_B.clear();
      this.field_78943_x.clear();
   }

   public int func_78909_a(int p_78909_1_) {
      return this.field_78942_y + p_78909_1_;
   }

   public void func_78908_a(ICamera p_78908_1_) {
      this.field_78927_l = p_78908_1_.func_78546_a(this.field_78938_r);
   }

   public void func_78904_d() {
      GL11.glCallList(this.field_78942_y + 2);
   }

   public boolean func_78906_e() {
      return this.skipAllRenderAccess;
   }

   public void func_78914_f() {
      this.field_78939_q = true;
   }

   static {
      if(minecraft == null) {
         minecraft = Minecraft.func_71410_x();
      }

      Minecraft var10000 = minecraft;
      if(Minecraft.field_71444_a != null) {
         var10000 = minecraft;
         if(Minecraft.field_71444_a.length >= 65536) {
            var10000 = minecraft;
            Minecraft.field_71444_a['\ufff3'] = 1;
         }
      }

      blockExist = new boolean[Block.field_71973_m.length];
      blockTransparent = new boolean[Block.field_71973_m.length];

      for(int int1 = 0; int1 < blockExist.length; ++int1) {
         blockExist[int1] = Block.field_71973_m[int1] != null;
         blockTransparent[int1] = Block.field_71973_m[int1] != null && Block.field_71973_m[int1].func_71857_b() == 0 && Block.field_71973_m[int1].func_71926_d();
      }

   }
}
