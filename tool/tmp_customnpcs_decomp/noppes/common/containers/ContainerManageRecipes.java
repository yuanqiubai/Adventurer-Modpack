/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  mu
 *  uf
 *  uy
 *  we
 *  ye
 */
package noppes.common.containers;

import java.util.ArrayList;
import java.util.HashMap;
import noppes.common.controllers.RecipeCarpentry;
import noppes.util.NoppesUtilPlayer;

public class ContainerManageRecipes
extends uy {
    public int size;
    public int width;
    private final mu craftingMatrix;
    private RecipeCarpentry recipe;
    private final boolean init = false;

    public ContainerManageRecipes(uf player, int size) {
        int l1;
        int j1;
        this.size = size * size;
        this.width = size;
        this.craftingMatrix = new mu("crafting", false, this.size + 1);
        this.recipe = new RecipeCarpentry();
        this.a(new we((mo)this.craftingMatrix, 0, 87, 44));
        for (j1 = 0; j1 < size; ++j1) {
            for (l1 = 0; l1 < size; ++l1) {
                this.a(new we((mo)this.craftingMatrix, j1 * this.width + l1 + 1, l1 * 18 + 8, j1 * 18 + 18));
            }
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (l1 = 0; l1 < 9; ++l1) {
                this.a(new we((mo)player.bn, l1 + j1 * 9 + 9, 8 + l1 * 18, 96 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 8 + j1 * 18, 154));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }

    public void setRecipe(RecipeCarpentry recipe) {
        this.craftingMatrix.a(0, recipe.recipeOutput);
        for (int i = 0; i < this.width; ++i) {
            for (int j = 0; j < this.width; ++j) {
                if (j >= recipe.recipeWidth) {
                    this.craftingMatrix.a(i * this.width + j + 1, null);
                    continue;
                }
                this.craftingMatrix.a(i * this.width + j + 1, recipe.getCraftingItem(i * recipe.recipeWidth + j));
            }
        }
        this.recipe = recipe;
    }

    public void saveRecipe() {
        int nextChar = 0;
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
        HashMap<ye, Character> nameMapping = new HashMap<ye, Character>();
        int firstRow = this.width;
        int lastRow = 0;
        int firstColumn = this.width;
        int lastColumn = 0;
        boolean seenRow = false;
        for (int recipe = 0; recipe < this.width; ++recipe) {
            boolean i$ = false;
            for (int mapped = 0; mapped < this.width; ++mapped) {
                ye letter = this.craftingMatrix.a(recipe * this.width + mapped + 1);
                if (letter == null) continue;
                if (!i$ && mapped < firstColumn) {
                    firstColumn = mapped;
                }
                if (mapped > lastColumn) {
                    lastColumn = mapped;
                }
                i$ = true;
                Character item = null;
                for (ye mapped1 : nameMapping.keySet()) {
                    if (!NoppesUtilPlayer.compareItems(mapped1, letter, false)) continue;
                    item = (Character)nameMapping.get(mapped1);
                }
                if (item != null) continue;
                item = Character.valueOf(chars[nextChar]);
                ++nextChar;
                nameMapping.put(letter, item);
            }
            if (!i$) continue;
            if (!seenRow) {
                firstRow = recipe;
                lastRow = recipe;
                seenRow = true;
                continue;
            }
            lastRow = recipe;
        }
        ArrayList<Object> var16 = new ArrayList<Object>();
        for (int var18 = 0; var18 < this.width; ++var18) {
            if (var18 < firstRow || var18 > lastRow) continue;
            String var21 = "";
            for (int var19 = 0; var19 < this.width; ++var19) {
                if (var19 < firstColumn || var19 > lastColumn) continue;
                ye var22 = this.craftingMatrix.a(var18 * this.width + var19 + 1);
                if (var22 == null) {
                    var21 = var21 + " ";
                    continue;
                }
                for (ye mapped1 : nameMapping.keySet()) {
                    if (!NoppesUtilPlayer.compareItems(mapped1, var22, false)) continue;
                    var21 = var21 + nameMapping.get(mapped1);
                }
            }
            var16.add(var21);
        }
        if (nameMapping.isEmpty()) {
            this.recipe.clear();
        } else {
            for (ye var20 : nameMapping.keySet()) {
                Character var23 = (Character)nameMapping.get(var20);
                var16.add(var23);
                var16.add(var20);
            }
            this.recipe.addRecipe(this.craftingMatrix.a(0), var16.toArray());
        }
    }
}

