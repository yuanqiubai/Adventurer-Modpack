/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  atv
 *  avi
 *  awf
 *  bex
 *  bgw
 *  bim
 *  bjo
 *  bkb
 *  org.lwjgl.opengl.GL11
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.client.hud;

import com.adventurer_engine.client.hud.IGuiHUDBase;
import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import org.lwjgl.opengl.GL11;

public class HUDAmmoSelect
implements IGuiHUDBase {
    public static final bjo texture = new bjo("adventurer_engine", "textures/gui/ammo_select.png");
    private int offsetX;
    private int offsetY;
    private final int textureWidth = 33;
    private final int textureHeight = 147;
    private static final float scale = 0.6f;
    private static final float re_scale = 1.6666666f;
    private bgw itemRenderer = new bgw();

    @Override
    public boolean canDisplay(bex player) {
        return player.aZ() != null && player.aZ().b() instanceof BasicRangedWeapon;
    }

    @Override
    public void display(bex player, awf s) {
        yc currentAmmo;
        yc tmp = player.aZ().b();
        this.offsetX = 4;
        this.offsetY = s.b() / 2 - 10;
        atv mc = atv.w();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        this.offsetX = (int)((float)this.offsetX * 1.6666666f);
        this.offsetY = (int)((float)this.offsetY * 1.6666666f);
        mc.r.b(this.offsetX, this.offsetY, 0, 0, 33, 147);
        BasicRangedWeapon weapon = (BasicRangedWeapon)tmp;
        ye item = player.aZ();
        int[] ammoAmount = PlayerTools.ammoAmountList((uf)player, weapon);
        int k = 3;
        int ammoListStart = 0;
        if (item.p() && item.q().b("current_ammo") && PlayerTools.hasItem((uf)player, currentAmmo = BasicAmmo.getAmmoByString(item.q().i("current_ammo")))) {
            for (int i = 0; i < weapon.allowed_ammo.size(); ++i) {
                if ((currentAmmo.cv != yc.n.cv || !"arrow".equals(weapon.allowed_ammo.get(i))) && !((BasicAmmo)currentAmmo).internal_name.equals(weapon.allowed_ammo.get(i))) continue;
                ammoListStart = i;
                k = 2;
                break;
            }
        }
        int size = weapon.allowed_ammo.size();
        for (int i = 0; i < size; ++i) {
            int c = (i + ammoListStart) % size;
            if (ammoAmount[c] <= 0) continue;
            this.renderAmmoIcon(weapon.allowed_ammo.get(c), ammoAmount[c], k, mc.l, mc.J());
            k = (k + 1) % 5;
            if (k == 2) break;
        }
        GL11.glScalef((float)1.6666666f, (float)1.6666666f, (float)1.6666666f);
    }

    @Override
    public bjo getTexture(bex player) {
        return texture;
    }

    @Override
    public String getHUDName() {
        return "ammo_select";
    }

    private void renderAmmoIcon(String ammoName, int amount, int idx, avi font, bim manager) {
        att.c();
        int ox = this.offsetX + 8;
        int oy = this.offsetY + 23 + 21 * idx;
        yc i = BasicAmmo.getAmmoByString(ammoName);
        this.itemRenderer.b(font, manager, new ye(i, amount), ox, oy);
        if (i instanceof BasicAmmo) {
            BasicAmmo am = (BasicAmmo)i;
            if (am.unbreakable) {
                font.b(font.a(bkb.a((String)"adv.gun.infinity"), 32), ox + 28, oy + 4, 0xFFFFFF);
                att.a();
                return;
            }
        }
        font.b(font.a(String.valueOf(amount), 32), ox + 28, oy + 4, 0xFFFFFF);
        att.a();
    }
}

