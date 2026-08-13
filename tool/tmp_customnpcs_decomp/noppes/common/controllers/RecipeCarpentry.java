/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aah
 *  abw
 *  aqz
 *  by
 *  cl
 *  vk
 *  yc
 *  ye
 */
package noppes.common.controllers;

import java.util.HashMap;
import noppes.common.controllers.Availability;
import noppes.util.NBTTags;
import noppes.util.NoppesUtilPlayer;

public class RecipeCarpentry
implements aah {
    public int id = -1;
    public String name = "";
    public int recipeWidth = 4;
    public int recipeHeight = 4;
    public ye recipeOutput;
    public Availability availability = new Availability();
    public boolean isGlobal = false;
    public boolean ignoreDamage = false;
    private ye[] recipeItems = new ye[16];

    public RecipeCarpentry() {
    }

    public RecipeCarpentry(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void readNBT(by compound) {
        this.id = compound.e("ID");
        this.recipeWidth = compound.e("Width");
        this.recipeHeight = compound.e("Height");
        this.recipeOutput = ye.a((by)compound.l("Item"));
        this.recipeItems = NBTTags.getItemStackArray(compound.m("Materials"));
        this.availability.readFromNBT(compound.l("Availability"));
        this.ignoreDamage = compound.n("IgnoreDamage");
        this.name = compound.i("Name");
        this.isGlobal = compound.n("Global");
    }

    public by writeNBT() {
        by compound = new by();
        compound.a("ID", this.id);
        compound.a("Width", this.recipeWidth);
        compound.a("Height", this.recipeHeight);
        if (this.recipeOutput != null) {
            compound.a("Item", this.recipeOutput.b(new by()));
        }
        compound.a("Materials", (cl)NBTTags.nbtItemStackArray(this.recipeItems));
        compound.a("Availability", this.availability.writeToNBT(new by()));
        compound.a("Name", this.name);
        compound.a("Global", this.isGlobal);
        compound.a("IgnoreDamage", this.ignoreDamage);
        return compound;
    }

    public boolean a(vk par1InventoryCrafting, abw world) {
        for (int var2 = 0; var2 <= 4 - this.recipeWidth; ++var2) {
            for (int var3 = 0; var3 <= 4 - this.recipeHeight; ++var3) {
                if (this.checkMatch(par1InventoryCrafting, var2, var3, true)) {
                    return true;
                }
                if (!this.checkMatch(par1InventoryCrafting, var2, var3, false)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean checkMatch(vk par1InventoryCrafting, int par2, int par3, boolean par4) {
        for (int var5 = 0; var5 < 4; ++var5) {
            for (int var6 = 0; var6 < 4; ++var6) {
                ye var10;
                int var7 = var5 - par2;
                int var8 = var6 - par3;
                ye var9 = null;
                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
                    var9 = par4 ? this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth] : this.recipeItems[var7 + var8 * this.recipeWidth];
                }
                if ((var10 = par1InventoryCrafting.b(var5, var6)) == null && var9 == null || NoppesUtilPlayer.compareItems(var9, var10, this.ignoreDamage)) continue;
                return false;
            }
        }
        return true;
    }

    public ye a(vk var1) {
        return this.recipeOutput == null ? null : this.recipeOutput.m();
    }

    public int a() {
        return 16;
    }

    public ye b() {
        return this.recipeOutput;
    }

    public void addRecipe(ye par1ItemStack, Object ... par2ArrayOfObj) {
        int var9;
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        if (par2ArrayOfObj[var4] instanceof String[]) {
            String[] var14;
            String[] var15 = var14 = (String[])par2ArrayOfObj[var4++];
            var9 = var14.length;
            for (int var18 = 0; var18 < var9; ++var18) {
                String var11 = var15[var18];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        } else {
            while (par2ArrayOfObj[var4] instanceof String) {
                String var13 = (String)par2ArrayOfObj[var4++];
                ++var6;
                var5 = var13.length();
                var3 = var3 + var13;
            }
        }
        HashMap<Character, ye> var12 = new HashMap<Character, ye>();
        while (var4 < par2ArrayOfObj.length) {
            Character var141 = (Character)par2ArrayOfObj[var4];
            ye var16 = null;
            if (par2ArrayOfObj[var4 + 1] instanceof yc) {
                var16 = new ye((yc)par2ArrayOfObj[var4 + 1]);
            } else if (par2ArrayOfObj[var4 + 1] instanceof aqz) {
                var16 = new ye((aqz)par2ArrayOfObj[var4 + 1], 1, -1);
            } else if (par2ArrayOfObj[var4 + 1] instanceof ye) {
                var16 = (ye)par2ArrayOfObj[var4 + 1];
            }
            var12.put(var141, var16);
            var4 += 2;
        }
        ye[] var151 = new ye[var5 * var6];
        for (var9 = 0; var9 < var5 * var6; ++var9) {
            char var17 = var3.charAt(var9);
            var151[var9] = var12.containsKey(Character.valueOf(var17)) ? ((ye)var12.get(Character.valueOf(var17))).m() : null;
        }
        this.recipeOutput = par1ItemStack;
        this.recipeItems = var151;
        this.recipeWidth = var5;
        this.recipeHeight = var6;
        if (var5 == 4 || var6 == 4) {
            this.isGlobal = false;
        }
    }

    public ye getCraftingItem(int i) {
        return this.recipeItems != null && i < this.recipeItems.length ? this.recipeItems[i] : null;
    }

    public void setCraftingItem(int i, ye item) {
        if (i < this.recipeItems.length) {
            this.recipeItems[i] = item;
        }
    }

    public void clear() {
        this.recipeOutput = null;
        this.recipeItems = new ye[16];
    }
}

