/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atu
 *  bfq
 *  bma
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.ARBBufferObject
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 */
package com.adventurer_engine.client.model.tmt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import org.lwjgl.opengl.ARBBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

@SideOnly(value=Side.CLIENT)
public class TmtTessellator
extends bfq {
    private static int nativeBufferSize = 0x200000;
    private static int trivertsInBuffer = nativeBufferSize / 48 * 6;
    public static boolean renderingWorldRenderer = false;
    public boolean defaultTexture = false;
    private int rawBufferSize = 0;
    public int textureID = 0;
    private static boolean b = false;
    private static boolean c = false;
    private static ByteBuffer d = atu.c((int)(nativeBufferSize * 4));
    private static IntBuffer e = d.asIntBuffer();
    private static FloatBuffer f = d.asFloatBuffer();
    private static ShortBuffer g = d.asShortBuffer();
    private int[] h;
    private int i = 0;
    private double j;
    private double k;
    private double textureW;
    private int l;
    private int m;
    private boolean n = false;
    private boolean o = false;
    private boolean p = false;
    private boolean q = false;
    private int r = 0;
    private int s = 0;
    private boolean t = false;
    public int u;
    public double v;
    public double w;
    public double x;
    private int y;
    public static TmtTessellator a = new TmtTessellator(0x200000);
    public boolean z = false;
    private static boolean A = false;
    private static IntBuffer B;
    private int C = 0;
    private static int D;
    private int E;

    private TmtTessellator(int par1) {
    }

    public TmtTessellator() {
    }

    public int a() {
        if (!this.z) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.z = false;
        int offs = 0;
        while (offs < this.i) {
            int vtc = 0;
            vtc = this.u == 7 && b ? Math.min(this.i - offs, trivertsInBuffer) : Math.min(this.i - offs, nativeBufferSize >> 5);
            e.clear();
            e.put(this.h, offs * 10, vtc * 10);
            d.position(0);
            d.limit(vtc * 40);
            offs += vtc;
            if (A) {
                this.C = (this.C + 1) % D;
                ARBBufferObject.glBindBufferARB((int)34962, (int)B.get(this.C));
                ARBBufferObject.glBufferDataARB((int)34962, (ByteBuffer)d, (int)35040);
            }
            if (this.o) {
                if (A) {
                    GL11.glTexCoordPointer((int)4, (int)5126, (int)40, (long)12L);
                } else {
                    f.position(3);
                    GL11.glTexCoordPointer((int)4, (int)40, (FloatBuffer)f);
                }
                GL11.glEnableClientState((int)32888);
            }
            if (this.p) {
                bma.b((int)bma.b);
                if (A) {
                    GL11.glTexCoordPointer((int)2, (int)5122, (int)40, (long)36L);
                } else {
                    g.position(18);
                    GL11.glTexCoordPointer((int)2, (int)40, (ShortBuffer)g);
                }
                GL11.glEnableClientState((int)32888);
                bma.b((int)bma.a);
            }
            if (this.n) {
                if (A) {
                    GL11.glColorPointer((int)4, (int)5121, (int)40, (long)28L);
                } else {
                    d.position(28);
                    GL11.glColorPointer((int)4, (boolean)true, (int)40, (ByteBuffer)d);
                }
                GL11.glEnableClientState((int)32886);
            }
            if (this.q) {
                if (A) {
                    GL11.glNormalPointer((int)5121, (int)40, (long)32L);
                } else {
                    d.position(32);
                    GL11.glNormalPointer((int)40, (ByteBuffer)d);
                }
                GL11.glEnableClientState((int)32885);
            }
            if (A) {
                GL11.glVertexPointer((int)3, (int)5126, (int)40, (long)0L);
            } else {
                f.position(0);
                GL11.glVertexPointer((int)3, (int)40, (FloatBuffer)f);
            }
            GL11.glEnableClientState((int)32884);
            if (this.u == 7 && b) {
                GL11.glDrawArrays((int)4, (int)0, (int)vtc);
            } else {
                GL11.glDrawArrays((int)this.u, (int)0, (int)vtc);
            }
            GL11.glDisableClientState((int)32884);
            if (this.o) {
                GL11.glDisableClientState((int)32888);
            }
            if (this.p) {
                bma.b((int)bma.b);
                GL11.glDisableClientState((int)32888);
                bma.b((int)bma.a);
            }
            if (this.n) {
                GL11.glDisableClientState((int)32886);
            }
            if (!this.q) continue;
            GL11.glDisableClientState((int)32885);
        }
        if (this.rawBufferSize > 131072 && this.r < this.rawBufferSize << 3) {
            this.rawBufferSize = 0;
            this.h = null;
        }
        int var1 = this.r * 4;
        this.d();
        return var1;
    }

    private void d() {
        this.i = 0;
        d.clear();
        this.r = 0;
        this.s = 0;
    }

    public void b() {
        this.b(7);
    }

    public void b(int par1) {
        if (this.z) {
            throw new IllegalStateException("Already tesselating!");
        }
        this.z = true;
        this.d();
        this.u = par1;
        this.q = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.t = false;
    }

    public void a(double par1, double par3) {
        this.o = true;
        this.j = par1;
        this.k = par3;
        this.textureW = 1.0;
    }

    public void setTextureUVW(double par1, double par3, double par4) {
        this.o = true;
        this.j = par1;
        this.k = par3;
        this.textureW = par4;
    }

    public void c(int par1) {
        this.p = true;
        this.l = par1;
    }

    public void a(float par1, float par2, float par3) {
        this.a((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f));
    }

    public void a(float par1, float par2, float par3, float par4) {
        this.a((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f), (int)(par4 * 255.0f));
    }

    public void a(int par1, int par2, int par3) {
        this.a(par1, par2, par3, 255);
    }

    public void a(int par1, int par2, int par3, int par4) {
        if (!this.t) {
            if (par1 > 255) {
                par1 = 255;
            }
            if (par2 > 255) {
                par2 = 255;
            }
            if (par3 > 255) {
                par3 = 255;
            }
            if (par4 > 255) {
                par4 = 255;
            }
            if (par1 < 0) {
                par1 = 0;
            }
            if (par2 < 0) {
                par2 = 0;
            }
            if (par3 < 0) {
                par3 = 0;
            }
            if (par4 < 0) {
                par4 = 0;
            }
            this.n = true;
            this.m = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? par4 << 24 | par3 << 16 | par2 << 8 | par1 : par1 << 24 | par2 << 16 | par3 << 8 | par4;
        }
    }

    public void a(double par1, double par3, double par5, double par7, double par9) {
        this.a(par7, par9);
        this.a(par1, par3, par5);
    }

    public void addVertexWithUVW(double par1, double par3, double par5, double par7, double par9, double par10) {
        this.setTextureUVW(par7, par9, par10);
        this.a(par1, par3, par5);
    }

    public void a(double par1, double par3, double par5) {
        if (this.r >= this.rawBufferSize - 40) {
            if (this.rawBufferSize == 0) {
                this.rawBufferSize = 65536;
                this.h = new int[this.rawBufferSize];
            } else {
                this.rawBufferSize *= 2;
                this.h = Arrays.copyOf(this.h, this.rawBufferSize);
            }
        }
        ++this.s;
        if (this.u == 7 && b && this.s % 4 == 0) {
            for (int var7 = 0; var7 < 2; ++var7) {
                int var8 = 10 * (3 - var7);
                if (this.o) {
                    this.h[this.r + 3] = this.h[this.r - var8 + 3];
                    this.h[this.r + 4] = this.h[this.r - var8 + 4];
                    this.h[this.r + 5] = this.h[this.r - var8 + 5];
                    this.h[this.r + 6] = this.h[this.r - var8 + 6];
                }
                if (this.p) {
                    this.h[this.r + 9] = this.h[this.r - var8 + 9];
                }
                if (this.n) {
                    this.h[this.r + 7] = this.h[this.r - var8 + 7];
                }
                this.h[this.r + 0] = this.h[this.r - var8 + 0];
                this.h[this.r + 1] = this.h[this.r - var8 + 1];
                this.h[this.r + 2] = this.h[this.r - var8 + 2];
                ++this.i;
                this.r += 10;
            }
        }
        if (this.o) {
            this.h[this.r + 3] = Float.floatToRawIntBits((float)this.j);
            this.h[this.r + 4] = Float.floatToRawIntBits((float)this.k);
            this.h[this.r + 5] = Float.floatToRawIntBits(0.0f);
            this.h[this.r + 6] = Float.floatToRawIntBits((float)this.textureW);
        }
        if (this.p) {
            this.h[this.r + 9] = this.l;
        }
        if (this.n) {
            this.h[this.r + 7] = this.m;
        }
        if (this.q) {
            this.h[this.r + 8] = this.y;
        }
        this.h[this.r + 0] = Float.floatToRawIntBits((float)(par1 + this.v));
        this.h[this.r + 1] = Float.floatToRawIntBits((float)(par3 + this.w));
        this.h[this.r + 2] = Float.floatToRawIntBits((float)(par5 + this.x));
        this.r += 10;
        ++this.i;
    }

    public void d(int par1) {
        int j = par1 >> 16 & 0xFF;
        int k = par1 >> 8 & 0xFF;
        int l = par1 & 0xFF;
        this.a(j, k, l);
    }

    public void a(int par1, int par2) {
        int k = par1 >> 16 & 0xFF;
        int l = par1 >> 8 & 0xFF;
        int i1 = par1 & 0xFF;
        this.a(k, l, i1, par2);
    }

    public void c() {
        this.t = true;
    }

    public void b(float par1, float par2, float par3) {
        this.q = true;
        byte b0 = (byte)(par1 * 127.0f);
        byte b1 = (byte)(par2 * 127.0f);
        byte b2 = (byte)(par3 * 127.0f);
        this.y = b0 & 0xFF | (b1 & 0xFF) << 8 | (b2 & 0xFF) << 16;
    }

    public void b(double par1, double par3, double par5) {
        this.v = par1;
        this.w = par3;
        this.x = par5;
    }

    public void c(float par1, float par2, float par3) {
        this.v += (double)par1;
        this.w += (double)par2;
        this.x += (double)par3;
    }

    static {
        D = 10;
        TmtTessellator.a.defaultTexture = true;
        boolean bl = A = c && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
        if (A) {
            B = atu.f((int)D);
            ARBBufferObject.glGenBuffersARB((IntBuffer)B);
        }
    }
}

