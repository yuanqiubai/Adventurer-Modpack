/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 *  mo
 *  nn
 *  oa
 *  uf
 *  ye
 */
package noppes.common.entity.data;

import java.util.HashMap;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NBTTags;

public class DataInventory
implements mo {
    public HashMap<Integer, ye> items = new HashMap();
    public HashMap<Integer, Integer> dropchance = new HashMap();
    public HashMap<Integer, ye> weapons = new HashMap();
    public HashMap<Integer, ye> armor = new HashMap();
    public int minExp = 0;
    public int maxExp = 0;
    private final EntityNPCInterface npc;

    public DataInventory(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public by writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("MinExp", this.minExp);
        nbttagcompound.a("MaxExp", this.maxExp);
        nbttagcompound.a("NpcInv", (cl)NBTTags.nbtItemStackList(this.items));
        nbttagcompound.a("Armor", (cl)NBTTags.nbtItemStackList(this.getArmor()));
        nbttagcompound.a("Weapons", (cl)NBTTags.nbtItemStackList(this.getWeapons()));
        nbttagcompound.a("DropChance", (cl)NBTTags.nbtIntegerIntegerMap(this.dropchance));
        return nbttagcompound;
    }

    public void readEntityFromNBT(by nbttagcompound) {
        this.minExp = nbttagcompound.e("MinExp");
        this.maxExp = nbttagcompound.e("MaxExp");
        this.items = NBTTags.getItemStackList(nbttagcompound.m("NpcInv"));
        this.setArmor(NBTTags.getItemStackList(nbttagcompound.m("Armor")));
        this.setWeapons(NBTTags.getItemStackList(nbttagcompound.m("Weapons")));
        this.dropchance = NBTTags.getIntegerIntegerMap(nbttagcompound.m("DropChance"));
        this.npc.updateTasks();
    }

    public HashMap<Integer, ye> getWeapons() {
        return this.weapons;
    }

    public void setWeapons(HashMap<Integer, ye> list) {
        this.weapons = list;
    }

    public HashMap<Integer, ye> getArmor() {
        return this.armor;
    }

    public void setArmor(HashMap<Integer, ye> list) {
        this.armor = list;
    }

    public ye getWeapon() {
        return this.getWeapons().get(0);
    }

    public ye getProjectile() {
        return this.getWeapons().get(1);
    }

    public ye getOffHand() {
        return this.getWeapons().get(2);
    }

    public void dropStuff(boolean dropXP) {
        if (!this.npc.q.I) {
            for (int var2 : this.items.keySet()) {
                int chance;
                ye item = this.items.get(var2);
                if (item == null) continue;
                int dchance = 100;
                if (this.dropchance.containsKey(var2)) {
                    dchance = this.dropchance.get(var2);
                }
                if ((chance = this.npc.q.s.nextInt(100) + dchance) < 100) continue;
                this.npc.dropPlayerItemWithRandomChoice(item.m(), true);
            }
            if (dropXP) {
                int var11 = this.minExp;
                if (this.maxExp - this.minExp > 0) {
                    var11 += this.npc.q.s.nextInt(this.maxExp - this.minExp);
                }
                while (var11 > 0) {
                    int var2;
                    var2 = oa.a((int)var11);
                    var11 -= var2;
                    this.npc.q.d((nn)new oa(this.npc.q, this.npc.u, this.npc.v, this.npc.w, var2));
                }
            }
        }
    }

    public ye armorItemInSlot(int i) {
        return this.getArmor().get(i);
    }

    public int j_() {
        return 15;
    }

    public ye a(int i) {
        return i < 4 ? this.armorItemInSlot(i) : (i < 7 ? this.getWeapons().get(i - 4) : this.items.get(i - 7));
    }

    public ye a(int par1, int par2) {
        HashMap<Integer, ye> var3;
        int i = 0;
        if (par1 >= 7) {
            var3 = this.items;
            par1 -= 7;
        } else if (par1 >= 4) {
            var3 = this.getWeapons();
            par1 -= 4;
            i = 1;
        } else {
            var3 = this.getArmor();
            i = 2;
        }
        ye var4 = null;
        if (var3.get(par1) != null) {
            if (var3.get((Object)Integer.valueOf((int)par1)).b <= par2) {
                var4 = var3.get(par1);
                var3.put(par1, null);
            } else {
                var4 = var3.get(par1).a(par2);
                if (var3.get((Object)Integer.valueOf((int)par1)).b == 0) {
                    var3.put(par1, null);
                }
            }
        }
        if (i == 1) {
            this.setWeapons(var3);
        }
        if (i == 2) {
            this.setArmor(var3);
        }
        return var4;
    }

    public ye a_(int par1) {
        HashMap<Integer, ye> var2;
        int i = 0;
        if (par1 >= 7) {
            var2 = this.items;
            par1 -= 7;
        } else if (par1 >= 4) {
            var2 = this.getWeapons();
            par1 -= 4;
            i = 1;
        } else {
            var2 = this.getArmor();
            i = 2;
        }
        if (var2.get(par1) != null) {
            ye var3 = var2.get(par1);
            var2.put(par1, null);
            if (i == 1) {
                this.setWeapons(var2);
            }
            if (i == 2) {
                this.setArmor(var2);
            }
            return var3;
        }
        return null;
    }

    public void a(int par1, ye par2ItemStack) {
        HashMap<Integer, ye> var3;
        int i = 0;
        if (par1 >= 7) {
            var3 = this.items;
            par1 -= 7;
        } else if (par1 >= 4) {
            var3 = this.getWeapons();
            par1 -= 4;
            i = 1;
        } else {
            var3 = this.getArmor();
            i = 2;
        }
        var3.put(par1, par2ItemStack);
        if (i == 1) {
            this.setWeapons(var3);
        }
        if (i == 2) {
            this.setArmor(var3);
        }
    }

    public String b() {
        return "NPC Inventory";
    }

    public int d() {
        return 64;
    }

    public void e() {
    }

    public boolean a(uf var1) {
        return true;
    }

    public void k_() {
    }

    public void g() {
    }

    public boolean c() {
        return true;
    }

    public boolean b(int i, ye itemstack) {
        return true;
    }
}

