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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestCategory;
import noppes.util.Tools;

public class QuestController {
    public static QuestController instance;
    public HashMap<Integer, QuestCategory> categories = new HashMap();
    public HashMap<Integer, Quest> quests = new HashMap();
    private int lastUsedID = 0;

    public QuestController() {
        instance = this;
        this.loadCategories();
    }

    private void loadCategories() {
        File saveDir = Tools.getWorldSaveDirectory();
        try {
            File e = new File(saveDir, "quests.dat");
            if (e.exists()) {
                this.loadCategories(e);
            }
        }
        catch (Exception var5) {
            try {
                File ee = new File(saveDir, "quests.dat_old");
                if (ee.exists()) {
                    this.loadCategories(ee);
                }
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }

    private void loadCategories(File file) throws Exception {
        HashMap<Integer, QuestCategory> categories = new HashMap<Integer, QuestCategory>();
        HashMap<Integer, Quest> quests = new HashMap<Integer, Quest>();
        by nbttagcompound1 = ci.a((InputStream)new FileInputStream(file));
        this.lastUsedID = nbttagcompound1.e("lastID");
        cg list = nbttagcompound1.m("Data");
        if (list != null) {
            for (int i = 0; i < list.c(); ++i) {
                QuestCategory category = new QuestCategory();
                category.readNBT((by)list.b(i));
                categories.put(category.id, category);
                for (Quest quest : category.quests.values()) {
                    quests.put(quest.id, quest);
                }
            }
        }
        this.quests = quests;
        this.categories = categories;
    }

    public void saveCategories() {
        try {
            Object file1;
            File e = Tools.getWorldSaveDirectory();
            cg list = new cg();
            for (QuestCategory file : this.categories.values()) {
                file1 = new by();
                file.writeNBT((by)file1);
                list.a((cl)file1);
            }
            by nbttagcompound1 = new by();
            nbttagcompound1.a("lastID", this.lastUsedID);
            nbttagcompound1.a("Data", (cl)list);
            file1 = new File(e, "quests.dat_new");
            File file11 = new File(e, "quests.dat_old");
            File file2 = new File(e, "quests.dat");
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
        catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    public void removeCategory(int category) {
        QuestCategory cat = this.categories.get(category);
        if (cat != null) {
            for (Integer integer : cat.quests.keySet()) {
                int dia = integer;
                this.quests.remove(dia);
            }
            this.categories.remove(category);
            this.saveCategories();
        }
    }

    public void removeQuest(Quest quest) {
        for (QuestCategory category : this.categories.values()) {
            category.quests.remove(quest.id);
        }
        this.quests.remove(quest.id);
        quest.category.quests.remove(quest.id);
        this.saveCategories();
    }

    public void saveCategory(QuestCategory category) {
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
            QuestCategory currentcategory1 = this.categories.get(category.id);
            if (!currentcategory1.title.equals(category.title)) {
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

    private boolean containsCategoryName(String name) {
        QuestCategory cat;
        name = name.toLowerCase();
        Iterator<QuestCategory> i$ = this.categories.values().iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            cat = i$.next();
        } while (!cat.title.toLowerCase().equals(name));
        return true;
    }

    private boolean containsQuestName(QuestCategory category, String name) {
        Quest quest;
        name = name.toLowerCase();
        Iterator<Quest> i$ = category.quests.values().iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            quest = i$.next();
        } while (!quest.title.toLowerCase().equals(name));
        return true;
    }

    public Quest saveQuest(int categoryId, by nbt) {
        QuestCategory category = this.categories.get(categoryId);
        if (category == null) {
            return null;
        }
        Quest quest = new Quest();
        quest.readNBT(nbt);
        quest.category = category;
        if (quest.id < 0) {
            int id = 0;
            for (int key : this.quests.keySet()) {
                if (key <= id) continue;
                id = key;
            }
            quest.id = ++id;
            while (this.containsQuestName(quest.category, quest.title)) {
                quest.title = quest.title + "_";
            }
        }
        this.quests.put(quest.id, quest);
        quest.category.quests.put(quest.id, quest);
        this.saveCategories();
        return quest;
    }
}

