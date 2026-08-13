/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abk
 *  abl
 *  abm
 *  abw
 *  att
 *  atv
 *  aut
 *  awy
 *  axx
 *  bjo
 *  bkb
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ea
 *  ey
 *  org.lwjgl.opengl.GL11
 *  uf
 *  uy
 *  ye
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import noppes.client.gui.util.GuiNpcButton;
import noppes.common.containers.ContainerMerchantAdd;
import noppes.common.events.EventLoader;
import noppes.network.server.EditMerchantUpdatePacket;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class GuiMerchantAdd
extends awy {
    private static final bjo merchantGuiTextures = new bjo("textures/gui/container/villager.png");
    private final abk theIMerchant = EventLoader.Merchant;
    private axx nextRecipeButtonIndex;
    private axx previousRecipeButtonIndex;
    private int currentRecipeIndex;
    private final String field_94082_v = bkb.a((String)"entity.Villager.name");

    public GuiMerchantAdd() {
        super((uy)new ContainerMerchantAdd(atv.w().h.bn, (abk)EventLoader.Merchant, (abw)atv.w().f));
    }

    static bjo func_110417_h() {
        return merchantGuiTextures;
    }

    public void A_() {
        super.A_();
        int i = (this.g - this.c) / 2;
        int j = (this.h - this.d) / 2;
        this.nextRecipeButtonIndex = new axx(1, i + 120 + 27, j + 24 - 1, true);
        this.i.add(this.nextRecipeButtonIndex);
        this.previousRecipeButtonIndex = new axx(2, i + 36 - 19, j + 24 - 1, false);
        this.i.add(this.previousRecipeButtonIndex);
        this.i.add(new GuiNpcButton(4, i + this.c, j + 20, 60, 20, "gui.remove"));
        this.i.add(new GuiNpcButton(5, i + this.c, j + 50, 60, 20, "gui.add"));
        this.nextRecipeButtonIndex.h = false;
        this.previousRecipeButtonIndex.h = false;
    }

    protected void b(int par1, int par2) {
        this.o.b(this.field_94082_v, this.c / 2 - this.o.a(this.field_94082_v) / 2, 6, 0x404040);
        this.o.b(bkb.a((String)"container.inventory"), 8, this.d - 96 + 2, 0x404040);
    }

    public void c() {
        super.c();
        abm merchantrecipelist = this.theIMerchant.b((uf)this.f.h);
        if (merchantrecipelist != null) {
            this.nextRecipeButtonIndex.h = this.currentRecipeIndex < merchantrecipelist.size() - 1;
            this.previousRecipeButtonIndex.h = this.currentRecipeIndex > 0;
        }
    }

    protected void a(aut par1GuiButton) {
        abm merchantRecipes;
        boolean flag = false;
        if (par1GuiButton == this.nextRecipeButtonIndex) {
            ++this.currentRecipeIndex;
            flag = true;
        } else if (par1GuiButton == this.previousRecipeButtonIndex) {
            --this.currentRecipeIndex;
            flag = true;
        }
        if (par1GuiButton.g == 4 && this.currentRecipeIndex < (merchantRecipes = this.theIMerchant.b((uf)this.f.h)).size()) {
            merchantRecipes.remove(this.currentRecipeIndex);
            if (this.currentRecipeIndex > 0) {
                --this.currentRecipeIndex;
            }
            ey pkt = new EditMerchantUpdatePacket(EventLoader.Merchant.k, merchantRecipes).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
        if (par1GuiButton.g == 5) {
            ye var9 = this.e.a(0).d();
            ye dataoutputstream = this.e.a(1).d();
            ye exception = this.e.a(2).d();
            if (var9 == null && dataoutputstream != null) {
                var9 = dataoutputstream;
                dataoutputstream = null;
            }
            if (var9 != null && exception != null) {
                var9 = var9.m();
                exception = exception.m();
                if (dataoutputstream != null) {
                    dataoutputstream = dataoutputstream.m();
                }
                abl recipe = new abl(var9, dataoutputstream, exception);
                recipe.a(0x7FFFFFF7);
                abm merchantrecipelist = this.theIMerchant.b((uf)this.f.h);
                merchantrecipelist.add((Object)recipe);
                ey pkt = new EditMerchantUpdatePacket(EventLoader.Merchant.k, merchantrecipelist).makePacket("CNPCs Server");
                PacketDispatcher.sendPacketToServer((ey)pkt);
            }
        }
        if (flag) {
            ((ContainerMerchantAdd)this.e).setCurrentRecipeIndex(this.currentRecipeIndex);
            ByteArrayOutputStream var10 = new ByteArrayOutputStream();
            DataOutputStream var11 = new DataOutputStream(var10);
            try {
                var11.writeInt(this.currentRecipeIndex);
                this.f.q().c((ey)new ea("MC|TrSel", var10.toByteArray()));
            }
            catch (Exception var8) {
                var8.printStackTrace();
            }
        }
    }

    protected void a(float par1, int par2, int par3) {
        int i1;
        abl merchantrecipe;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.J().a(merchantGuiTextures);
        int k = (this.g - this.c) / 2;
        int l = (this.h - this.d) / 2;
        this.b(k, l, 0, 0, this.c, this.d);
        abm merchantrecipelist = this.theIMerchant.b((uf)this.f.h);
        if (merchantrecipelist != null && !merchantrecipelist.isEmpty() && (merchantrecipe = (abl)merchantrecipelist.get(i1 = this.currentRecipeIndex)).g()) {
            this.f.J().a(merchantGuiTextures);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)2896);
            this.b(this.p + 83, this.q + 21, 212, 0, 28, 21);
            this.b(this.p + 83, this.q + 51, 212, 0, 28, 21);
        }
    }

    public void a(int par1, int par2, float par3) {
        super.a(par1, par2, par3);
        abm merchantrecipelist = this.theIMerchant.b((uf)this.f.h);
        if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
            int k = (this.g - this.c) / 2;
            int l = (this.h - this.d) / 2;
            int i1 = this.currentRecipeIndex;
            abl merchantrecipe = (abl)merchantrecipelist.get(i1);
            GL11.glPushMatrix();
            ye itemstack = merchantrecipe.a();
            ye itemstack1 = merchantrecipe.b();
            ye itemstack2 = merchantrecipe.d();
            att.c();
            GL11.glDisable((int)2896);
            GL11.glEnable((int)32826);
            GL11.glEnable((int)2903);
            GL11.glEnable((int)2896);
            GuiMerchantAdd.b.f = 100.0f;
            b.b(this.o, this.f.J(), itemstack, k + 36, l + 24);
            b.c(this.o, this.f.J(), itemstack, k + 36, l + 24);
            if (itemstack1 != null) {
                b.b(this.o, this.f.J(), itemstack1, k + 62, l + 24);
                b.c(this.o, this.f.J(), itemstack1, k + 62, l + 24);
            }
            b.b(this.o, this.f.J(), itemstack2, k + 120, l + 24);
            b.c(this.o, this.f.J(), itemstack2, k + 120, l + 24);
            GuiMerchantAdd.b.f = 0.0f;
            GL11.glDisable((int)2896);
            if (this.c(36, 24, 16, 16, par1, par2)) {
                this.a(itemstack, par1, par2);
            } else if (itemstack1 != null && this.c(62, 24, 16, 16, par1, par2)) {
                this.a(itemstack1, par1, par2);
            } else if (this.c(120, 24, 16, 16, par1, par2)) {
                this.a(itemstack2, par1, par2);
            }
            GL11.glPopMatrix();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
            att.b();
        }
    }

    public abk getIMerchant() {
        return this.theIMerchant;
    }
}

