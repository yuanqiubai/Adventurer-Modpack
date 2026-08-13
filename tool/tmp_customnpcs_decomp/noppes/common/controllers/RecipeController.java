/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aaf
 *  by
 *  cg
 *  ci
 *  cl
 *  vk
 */
package noppes.common.controllers;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import noppes.client.controllers.CloneController;
import noppes.common.controllers.RecipeCarpentry;
import noppes.common.controllers.RecipesDefault;
import noppes.util.Tools;

public class RecipeController {
    public static RecipeController instance;
    private static Collection<RecipeCarpentry> prevRecipes;
    public HashMap<Integer, RecipeCarpentry> globalRecipes = new HashMap();
    public HashMap<Integer, RecipeCarpentry> anvilRecipes = new HashMap();

    public RecipeController() {
        instance = this;
        this.loadCategories();
        RecipeController.reloadGlobalRecipes(this.globalRecipes);
    }

    public static void reloadGlobalRecipes(HashMap<Integer, RecipeCarpentry> globalRecipes) {
        List list = aaf.a().b();
        if (prevRecipes != null) {
            list.removeAll(prevRecipes);
        }
        prevRecipes = new HashSet<RecipeCarpentry>(globalRecipes.values());
        list.addAll(prevRecipes);
    }

    private void loadCategories() {
        File saveDir = Tools.getWorldSaveDirectory();
        try {
            File e = new File(saveDir, "recipes.dat");
            if (e.exists()) {
                this.loadCategories(e);
            } else {
                this.loadDefaultRecipes();
            }
        }
        catch (Exception var5) {
            var5.printStackTrace();
            try {
                File ee = new File(saveDir, "recipes.dat_old");
                if (ee.exists()) {
                    this.loadCategories(ee);
                }
            }
            catch (Exception var4) {
                var5.printStackTrace();
            }
        }
    }

    private void loadDefaultRecipes() {
        new RecipesDefault(this);
        this.saveCategories();
    }

    private void loadCategories(File file) throws Exception {
        by nbttagcompound1 = ci.a((InputStream)new FileInputStream(file));
        cg list = nbttagcompound1.m("Data");
        HashMap<Integer, RecipeCarpentry> globalRecipes = new HashMap<Integer, RecipeCarpentry>();
        HashMap<Integer, RecipeCarpentry> anvilRecipes = new HashMap<Integer, RecipeCarpentry>();
        if (list != null) {
            for (int i = 0; i < list.c(); ++i) {
                RecipeCarpentry recipe = new RecipeCarpentry();
                recipe.readNBT((by)list.b(i));
                if (recipe.isGlobal) {
                    globalRecipes.put(recipe.id, recipe);
                    continue;
                }
                anvilRecipes.put(recipe.id, recipe);
            }
        }
        this.anvilRecipes = anvilRecipes;
        this.globalRecipes = globalRecipes;
    }

    private void saveCategories() {
        File e = Tools.getWorldSaveDirectory();
        cg list = new cg();
        for (RecipeCarpentry file : this.globalRecipes.values()) {
            list.a((cl)file.writeNBT());
        }
        for (RecipeCarpentry file : this.anvilRecipes.values()) {
            list.a((cl)file.writeNBT());
        }
        by nbttagcompound1 = new by();
        nbttagcompound1.a("Data", (cl)list);
        File theFile = new File(e, "recipes.dat_new");
        File file1 = new File(e, "recipes.dat_old");
        File file2 = new File(e, "recipes.dat");
        CloneController.saveInFile(nbttagcompound1, theFile, file1, file2);
    }

    public RecipeCarpentry findMatchingRecipe(vk par1InventoryCrafting) {
        RecipeCarpentry recipe;
        Iterator<RecipeCarpentry> i$ = this.anvilRecipes.values().iterator();
        do {
            if (i$.hasNext()) continue;
            return null;
        } while (!(recipe = i$.next()).a(par1InventoryCrafting, null));
        return recipe;
    }

    public RecipeCarpentry getRecipe(int id) {
        return this.globalRecipes.containsKey(id) ? this.globalRecipes.get(id) : (this.anvilRecipes.containsKey(id) ? this.anvilRecipes.get(id) : null);
    }

    public RecipeCarpentry saveRecipe(DataInputStream dis) throws IOException {
        RecipeCarpentry recipe = new RecipeCarpentry();
        recipe.readNBT(ci.a((DataInput)dis));
        return this.handleRecipeData(recipe);
    }

    public RecipeCarpentry saveRecipe(by nbt) throws IOException {
        RecipeCarpentry recipe = new RecipeCarpentry();
        recipe.readNBT(nbt);
        return this.handleRecipeData(recipe);
    }

    private RecipeCarpentry handleRecipeData(RecipeCarpentry recipe) {
        RecipeCarpentry current = this.getRecipe(recipe.id);
        if (current != null && !current.name.equals(recipe.name)) {
            while (this.containsRecipeName(recipe.name)) {
                recipe.name = recipe.name + "_";
            }
        }
        if (recipe.id == -1) {
            recipe.id = this.getUniqueId();
            while (this.containsRecipeName(recipe.name)) {
                recipe.name = recipe.name + "_";
            }
        }
        if (recipe.isGlobal) {
            this.anvilRecipes.remove(recipe.id);
            this.globalRecipes.put(recipe.id, recipe);
        } else {
            this.globalRecipes.remove(recipe.id);
            this.anvilRecipes.put(recipe.id, recipe);
        }
        this.saveCategories();
        RecipeController.reloadGlobalRecipes(this.globalRecipes);
        return recipe;
    }

    private int getUniqueId() {
        int id = 0;
        for (Integer k : this.globalRecipes.keySet()) {
            if (k <= id) continue;
            id = k;
        }
        for (Integer k : this.anvilRecipes.keySet()) {
            if (k <= id) continue;
            id = k;
        }
        return ++id;
    }

    private boolean containsRecipeName(String name) {
        RecipeCarpentry recipe;
        name = name.toLowerCase();
        Iterator<RecipeCarpentry> i$ = this.globalRecipes.values().iterator();
        do {
            if (!i$.hasNext()) {
                i$ = this.anvilRecipes.values().iterator();
                do {
                    if (!i$.hasNext()) {
                        return false;
                    }
                    recipe = i$.next();
                } while (!recipe.name.toLowerCase().equals(name));
                return true;
            }
            recipe = i$.next();
        } while (!recipe.name.toLowerCase().equals(name));
        return true;
    }

    public RecipeCarpentry removeRecipe(int id) {
        RecipeCarpentry recipe = this.getRecipe(id);
        this.globalRecipes.remove(recipe.id);
        this.anvilRecipes.remove(recipe.id);
        this.saveCategories();
        RecipeController.reloadGlobalRecipes(this.globalRecipes);
        return recipe;
    }
}

