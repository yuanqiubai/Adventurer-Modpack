/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aqz
 *  asp
 *  asx
 *  by
 *  uf
 */
package noppes.common.blocks;

import java.util.List;
import noppes.CustomNpcs;
import noppes.common.blocks.BlockNpcRedstone;
import noppes.common.controllers.Availability;

public class TileRedstoneBlock
extends asp {
    public int onRangeX = 6;
    public int onRangeY = 6;
    public int onRangeZ = 6;
    public int offRangeX = 10;
    public int offRangeY = 10;
    public int offRangeZ = 10;
    public Availability availability = new Availability();
    public boolean isActivated = false;
    private int ticks = 10;

    public void h() {
        if (!this.k.I) {
            --this.ticks;
            if (this.ticks <= 0) {
                this.ticks = 20;
                aqz block = aqz.s[this.k.a(this.l, this.m, this.n)];
                if (block != null && block instanceof BlockNpcRedstone) {
                    if (CustomNpcs.FreezeNPCs) {
                        if (this.isActivated) {
                            this.setActive(block, false);
                        }
                    } else if (!this.isActivated) {
                        List<uf> list = this.getPlayerList(this.onRangeX, this.onRangeY, this.onRangeZ);
                        if (list.isEmpty()) {
                            return;
                        }
                        for (uf player : list) {
                            if (!this.availability.isAvailable(player)) continue;
                            this.setActive(block, true);
                            return;
                        }
                    } else {
                        List<uf> list = this.getPlayerList(this.offRangeX, this.offRangeY, this.offRangeZ);
                        for (uf player : list) {
                            if (!this.availability.isAvailable(player)) continue;
                            return;
                        }
                        this.setActive(block, false);
                    }
                } else {
                    this.k.s(this.l, this.m, this.n);
                }
            }
        }
    }

    private void setActive(aqz block, boolean bo) {
        this.isActivated = bo;
        this.k.b(this.l, this.m, this.n, this.isActivated ? 1 : 0, 2);
        this.k.j(this.l, this.m, this.n);
        if (this.o()) {
            block.a(this.k, this.l, this.m, this.n);
        }
    }

    private List<uf> getPlayerList(int x, int y, int z) {
        return this.k.a(uf.class, asx.a((double)this.l, (double)this.m, (double)this.n, (double)(this.l + 1), (double)(this.m + 1), (double)(this.n + 1)).b((double)x, (double)y, (double)z));
    }

    public void a(by par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        this.onRangeX = par1NBTTagCompound.e("BlockOnRangeX");
        this.onRangeY = par1NBTTagCompound.e("BlockOnRangeY");
        this.onRangeZ = par1NBTTagCompound.e("BlockOnRangeZ");
        this.offRangeX = par1NBTTagCompound.e("BlockOffRangeX");
        this.offRangeY = par1NBTTagCompound.e("BlockOffRangeY");
        this.offRangeZ = par1NBTTagCompound.e("BlockOffRangeZ");
        this.isActivated = par1NBTTagCompound.n("BlockActivated");
        this.availability.readFromNBT(par1NBTTagCompound);
        if (this.o()) {
            this.setActive(this.q(), this.isActivated);
        }
    }

    public void b(by par1NBTTagCompound) {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.a("BlockOnRangeX", this.onRangeX);
        par1NBTTagCompound.a("BlockOnRangeY", this.onRangeY);
        par1NBTTagCompound.a("BlockOnRangeZ", this.onRangeZ);
        par1NBTTagCompound.a("BlockOffRangeX", this.offRangeX);
        par1NBTTagCompound.a("BlockOffRangeY", this.offRangeY);
        par1NBTTagCompound.a("BlockOffRangeZ", this.offRangeZ);
        par1NBTTagCompound.a("BlockActivated", this.isActivated);
        this.availability.writeToNBT(par1NBTTagCompound);
    }
}

