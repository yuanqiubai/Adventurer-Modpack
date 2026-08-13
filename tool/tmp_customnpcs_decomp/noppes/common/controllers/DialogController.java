/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  ci
 *  cl
 */
package noppes.common.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import noppes.common.constants.EnumOptionType;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogCategory;
import noppes.common.controllers.DialogOption;
import noppes.util.Tools;

public class DialogController {
    private static final String lock = "lock";
    public static DialogController instance;
    public HashMap<Integer, DialogCategory> categories = new HashMap();
    public HashMap<Integer, Dialog> dialogs = new HashMap();
    private int lastUsedID = 0;

    public DialogController() {
        instance = this;
        this.loadCategories();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadCategories() {
        boolean loadDefault = false;
        String var2 = lock;
        String string = lock;
        synchronized (lock) {
            File saveDir = Tools.getWorldSaveDirectory();
            try {
                File e = new File(saveDir, "dialog.dat");
                if (e.exists()) {
                    this.loadCategories(e);
                } else {
                    loadDefault = true;
                }
            }
            catch (Exception var8) {
                try {
                    File ee = new File(saveDir, "dialog.dat_old");
                    if (ee.exists()) {
                        this.loadCategories(ee);
                    }
                }
                catch (Exception var7) {
                    var8.printStackTrace();
                }
            }
            if (loadDefault) {
                this.loadDefaultDialogs();
            }
            return;
        }
    }

    private void loadCategories(File file) throws Exception {
        by nbttagcompound1 = ci.a((InputStream)new FileInputStream(file));
        this.lastUsedID = nbttagcompound1.e("lastID");
        cg list = nbttagcompound1.m("Data");
        HashMap<Integer, DialogCategory> categories = new HashMap<Integer, DialogCategory>();
        HashMap<Integer, Dialog> dialogs = new HashMap<Integer, Dialog>();
        if (list != null) {
            for (int i = 0; i < list.c(); ++i) {
                DialogCategory category = new DialogCategory();
                category.readNBT((by)list.b(i));
                categories.put(category.id, category);
                for (Dialog dialog : category.dialogs.values()) {
                    dialogs.put(dialog.id, dialog);
                }
            }
        }
        this.categories = categories;
        this.dialogs = dialogs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveCategories() {
        String var1 = lock;
        String string = lock;
        synchronized (lock) {
            try {
                Object file1;
                File e = Tools.getWorldSaveDirectory();
                cg list = new cg();
                for (DialogCategory file : this.categories.values()) {
                    file1 = new by();
                    file.writeNBT((by)file1);
                    list.a((cl)file1);
                }
                by nbttagcompound1 = new by();
                nbttagcompound1.a("lastID", this.lastUsedID);
                nbttagcompound1.a("Data", (cl)list);
                file1 = new File(e, "dialog.dat_new");
                File file11 = new File(e, "dialog.dat_old");
                File file2 = new File(e, "dialog.dat");
                ci.a((by)nbttagcompound1, (OutputStream)new FileOutputStream((File)file1));
                if (file11.exists()) {
                    file11.delete();
                }
                file2.renameTo(file11);
                if (file2.exists()) {
                    file2.delete();
                }
                ((File)file1).renameTo(file2);
                if (((File)file1).exists()) {
                    ((File)file1).delete();
                }
            }
            catch (Exception var9) {
                var9.printStackTrace();
            }
            return;
        }
    }

    private void loadDefaultDialogs() {
        if (this.categories.isEmpty() && this.categories.isEmpty()) {
            DialogCategory cat = new DialogCategory();
            cat.id = 1;
            cat.title = "Villager";
            Dialog dia1 = new Dialog();
            dia1.id = 1;
            dia1.category = cat;
            dia1.title = "Start";
            dia1.text = "Hello {player}, \n\nWelcome to our village. I hope you enjoy your stay";
            Dialog dia2 = new Dialog();
            dia2.id = 2;
            dia2.category = cat;
            dia2.title = "Ask about village";
            dia2.text = "This village has been around for ages. Enjoy your stay here.";
            Dialog dia3 = new Dialog();
            dia3.id = 3;
            dia3.category = cat;
            dia3.title = "Who are you";
            dia3.text = "I'm a villager here. I have lived in this village my whole life.";
            cat.dialogs.put(dia1.id, dia1);
            cat.dialogs.put(dia2.id, dia2);
            cat.dialogs.put(dia3.id, dia3);
            DialogOption option = new DialogOption();
            option.title = "Tell me something about this village";
            option.dialogId = 2;
            option.optionType = EnumOptionType.DialogOption;
            DialogOption option2 = new DialogOption();
            option2.title = "Who are you?";
            option2.dialogId = 3;
            option2.optionType = EnumOptionType.DialogOption;
            DialogOption option3 = new DialogOption();
            option3.title = "Goodbye";
            option3.optionType = EnumOptionType.QuitOption;
            dia1.options.put(0, option2);
            dia1.options.put(1, option);
            dia1.options.put(2, option3);
            DialogOption option4 = new DialogOption();
            option4.title = "Back";
            option4.dialogId = 1;
            dia2.options.put(1, option4);
            dia3.options.put(1, option4);
            this.categories.put(cat.id, cat);
            this.dialogs.put(dia1.id, dia1);
            this.dialogs.put(dia2.id, dia2);
            this.dialogs.put(dia3.id, dia3);
            this.lastUsedID = 3;
            this.saveCategories();
        }
    }

    public void removeDialog(Dialog dialog) {
        DialogCategory category = dialog.category;
        category.dialogs.remove(dialog.id);
        this.dialogs.remove(dialog.id);
        this.saveCategories();
    }

    public void saveCategory(DialogCategory category) throws IOException {
        if (category.id < 0) {
            if (this.lastUsedID == 0) {
                for (int catid : this.categories.keySet()) {
                    if (catid <= this.lastUsedID) continue;
                    this.lastUsedID = catid;
                }
            }
            ++this.lastUsedID;
            category.id = this.lastUsedID;
        }
        if (this.categories.containsKey(category.id)) {
            DialogCategory currentCategory1 = this.categories.get(category.id);
            if (!currentCategory1.title.equals(category.title)) {
                while (this.containsCategoryName(category.title)) {
                    category.title = category.title + "_";
                }
            }
        } else {
            while (this.containsCategoryName(category.title)) {
                category.title = category.title + "_";
            }
        }
        this.categories.put(category.id, category);
        this.saveCategories();
    }

    public void removeCategory(int category) {
        DialogCategory cat = this.categories.get(category);
        if (cat != null) {
            for (Integer integer : cat.dialogs.keySet()) {
                int dia = integer;
                this.dialogs.remove(dia);
            }
            this.categories.remove(category);
            this.saveCategories();
        }
    }

    private boolean containsCategoryName(String name) {
        DialogCategory cat;
        name = name.toLowerCase();
        Iterator<DialogCategory> i$ = this.categories.values().iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            cat = i$.next();
        } while (!cat.title.toLowerCase().equals(name));
        return true;
    }

    private boolean containsDialogName(DialogCategory category, String name) {
        Dialog dia;
        name = name.toLowerCase();
        Iterator<Dialog> i$ = category.dialogs.values().iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            dia = i$.next();
        } while (!dia.title.toLowerCase().equals(name));
        return true;
    }

    public void saveDialog(int categoryId, Dialog dialog) throws IOException {
        DialogCategory category = this.categories.get(categoryId);
        if (category != null) {
            dialog.category = category;
            if (dialog.id < 0) {
                int id = 0;
                for (int key : this.dialogs.keySet()) {
                    if (key <= id) continue;
                    id = key;
                }
                dialog.id = ++id;
                while (this.containsDialogName(dialog.category, dialog.title)) {
                    dialog.title = dialog.title + "_";
                }
            }
            this.dialogs.put(dialog.id, dialog);
            dialog.category.dialogs.put(dialog.id, dialog);
            this.saveCategories();
        }
    }

    public boolean hasDialog(int dialogId) {
        return this.dialogs.containsKey(dialogId);
    }
}

