/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  uf
 *  uy
 *  we
 *  ye
 */
package com.adventurer_engine.common.gui_handler;

import com.adventurer_engine.common.data.globaldata.IGlobalData;
import com.adventurer_engine.common.gui_handler.BackpackInventory;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.network.server2client.SendBackpackData;

public class BackpackContainer
extends uy {
    private BackpackInventory inv;
    ye item;
    public String backpackName;

    public BackpackContainer(uf player, ye item) {
        int j;
        int i;
        this.item = item;
        this.backpackName = item.q().i("backpackName");
        this.inv = player.q.I ? SendBackpackData.clientInv : IGlobalData.BACKPACK_INVENTORY_DATA.getBackpackInventory(this.backpackName, player);
        assert (this.inv != null);
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.a(new we((mo)player.bn, j + i * 9 + 9, 45 + j * 18, 158 + i * 18){

                    public boolean a(uf player) {
                        return this.d() == null || this.d().d != ItemLoader.itemBackpack.cv;
                    }
                });
            }
        }
        for (i = 0; i < 9; ++i) {
            this.a(new we((mo)player.bn, i, 45 + i * 18, 216){

                public boolean a(uf player) {
                    return this.d() == null || this.d().d != ItemLoader.itemBackpack.cv;
                }
            });
        }
        for (i = 7; i >= 0; --i) {
            for (j = 12; j >= 0; --j) {
                this.a(new we((mo)this.inv, j + i * 13, 8 + j * 18, 9 + i * 18));
            }
        }
    }

    private BackpackContainer() {
    }

    public boolean a(uf entityplayer) {
        return true;
    }

    public ye b(uf player, int idx) {
        ye transItem = null;
        we slot = (we)this.c.get(idx);
        if (slot != null && slot.e()) {
            ye orderedItem = slot.d();
            transItem = orderedItem.m();
            int numRows = 4;
            int numCols = 9;
            if (idx < numRows * numCols ? !this.a(orderedItem, numRows * numCols, this.c.size(), true) : !this.a(orderedItem, 0, numRows * numCols, false)) {
                return null;
            }
            if (orderedItem.b == 0) {
                slot.c(null);
            } else {
                slot.f();
            }
        }
        return transItem;
    }

    public void b(uf player) {
        super.b(player);
    }

    public void collate() {
        this.inv.collate();
        this.b();
    }
}

