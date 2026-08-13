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
package noppes.common.containers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import noppes.common.containers.InventoryNPC;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleTrader;
import noppes.util.NoppesUtilPlayer;

public class ContainerNPCTrader
extends uy {
    public InventoryNPC currencyMatrix;
    public RoleTrader role;

    public ContainerNPCTrader(EntityNPCInterface npc, uf player) {
        int var6;
        int i;
        this.role = (RoleTrader)npc.roleInterface;
        this.currencyMatrix = new InventoryNPC("currency", 1, this);
        for (i = 0; i < 18; ++i) {
            int l1 = 62;
            var6 = l1 + i % 3 * 45;
            int y = 9;
            int var7 = y + i / 3 * 22;
            this.a(new we((mo)this.role.inventorySold, i, var6, var7));
        }
        for (i = 0; i < 3; ++i) {
            for (var6 = 0; var6 < 9; ++var6) {
                this.a(new we((mo)player.bn, var6 + i * 9 + 9, 8 + var6 * 18, 144 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.a(new we((mo)player.bn, i, 8 + i * 18, 202));
        }
    }

    public ye b(uf entityplayer, int idx) {
        if (idx > 17) {
            return null;
        }
        we slot = (we)this.c.get(idx);
        if (slot == null || slot.d() == null) {
            return null;
        }
        ye money = this.role.inventoryCurrency.items.get(idx);
        LinkedList<Integer> matchID = new LinkedList<Integer>();
        int price = money.b;
        int totalMoney = 0;
        ye[] playerItem = entityplayer.bn.a;
        for (int i = 0; i < playerItem.length; ++i) {
            if (playerItem[i] == null || !NoppesUtilPlayer.compareItems(playerItem[i], money, false)) continue;
            matchID.add(i);
            totalMoney += playerItem[i].b;
        }
        ye product = slot.d();
        while (totalMoney >= price && this.a(product.m(), 18, 54, false)) {
            totalMoney -= price;
            this.payForIt(matchID, price, playerItem);
        }
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }

    public void b(uf entityplayer) {
        super.b(entityplayer);
        ye itemstack = this.currencyMatrix.a(0);
        if (itemstack != null && !entityplayer.q.I) {
            this.currencyMatrix.a(0, null);
            entityplayer.a(itemstack, 0.0f);
        }
    }

    public ye a(int i, int j, int par3, uf entityplayer) {
        if (par3 == 6) {
            par3 = 0;
        }
        if (par3 == 1) {
            return this.b(entityplayer, i);
        }
        if (i >= 0 && i < 18) {
            if (j == 1) {
                return null;
            }
            we slot = (we)this.c.get(i);
            if (slot != null && slot.d() != null) {
                ye item = slot.d();
                if (!this.canGivePlayer(item, entityplayer)) {
                    return null;
                }
                if (!this.canBuy(this.role.inventoryCurrency.items.get(i), entityplayer)) {
                    return null;
                }
                ye soldItem = item.m();
                this.givePlayer(soldItem, entityplayer);
                return soldItem;
            }
            return null;
        }
        return super.a(i, j, par3, entityplayer);
    }

    private boolean canBuy(ye money, uf entityplayer) {
        if (money == null) {
            return false;
        }
        LinkedList<Integer> matchID = new LinkedList<Integer>();
        int price = money.b;
        int totalMoney = 0;
        ye[] playerItem = entityplayer.bn.a;
        for (int i = 0; i < playerItem.length; ++i) {
            if (playerItem[i] == null || !NoppesUtilPlayer.compareItems(playerItem[i], money, false)) continue;
            matchID.add(i);
            totalMoney += playerItem[i].b;
        }
        if (totalMoney >= price) {
            assert (matchID.size() > 0);
            this.payForIt(matchID, price, playerItem);
            return true;
        }
        return false;
    }

    private void payForIt(List<Integer> matchID, int price, ye[] playerItem) {
        Iterator<Integer> it = matchID.iterator();
        while (it.hasNext()) {
            int delta;
            int id = it.next();
            if (price == 0) break;
            if (playerItem[id] == null) {
                it.remove();
                continue;
            }
            int itemAmount = playerItem[id].b;
            if ((itemAmount -= (delta = Math.min(price, itemAmount))) == 0) {
                playerItem[id] = null;
                it.remove();
                price -= delta;
                continue;
            }
            playerItem[id].a(delta);
            price -= delta;
        }
    }

    private boolean canGivePlayer(ye item, uf entityplayer) {
        ye itemstack3 = entityplayer.bn.o();
        if (itemstack3 == null) {
            return true;
        }
        if (NoppesUtilPlayer.compareItems(itemstack3, item, false)) {
            int k1 = item.b;
            return k1 > 0 && k1 + itemstack3.b <= itemstack3.e();
        }
        return false;
    }

    private void givePlayer(ye item, uf entityplayer) {
        int k1;
        ye itemstack3 = entityplayer.bn.o();
        if (itemstack3 == null) {
            entityplayer.bn.b(item);
        } else if (NoppesUtilPlayer.compareItems(itemstack3, item, false) && (k1 = item.b) > 0 && k1 + itemstack3.b <= itemstack3.e()) {
            itemstack3.b += k1;
        }
    }
}

