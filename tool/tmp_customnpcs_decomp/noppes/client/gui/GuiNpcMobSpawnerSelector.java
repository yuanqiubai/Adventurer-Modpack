/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cb
 *  cg
 *  cl
 */
package noppes.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import noppes.client.controllers.CloneController;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiMenuSideButton;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.SubGuiInterface;

public class GuiNpcMobSpawnerSelector
extends SubGuiInterface {
    private static String search = "";
    private GuiCustomScroll scroll;
    private final HashMap<String, by> cloneData = new HashMap();
    private ArrayList<String> list;
    private int activeTab = 1;

    public GuiNpcMobSpawnerSelector() {
        this.xSize = 256;
        this.closeOnEsc = true;
        this.setBackground("menubg.png");
    }

    @Override
    public void A_() {
        super.A_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScroll(this, 0);
            this.scroll.setSize(165, 188);
            this.scroll.guiLeft = this.guiLeft + 4;
            this.scroll.guiTop = this.guiTop + 26;
        } else {
            this.scroll.clear();
        }
        this.addScroll(this.scroll, this.f);
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 4, this.guiTop + 4, 165, 20, search));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 171, this.guiTop + 80, 80, 20, "gui.done"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 171, this.guiTop + 103, 80, 20, "gui.cancel"));
        this.addSideButton(new GuiMenuSideButton(21, this.guiLeft - 69, this.guiTop + 2, 70, 22, "Tab 1"));
        this.addSideButton(new GuiMenuSideButton(22, this.guiLeft - 69, this.guiTop + 23, 70, 22, "Tab 2"));
        this.addSideButton(new GuiMenuSideButton(23, this.guiLeft - 69, this.guiTop + 44, 70, 22, "Tab 3"));
        this.addSideButton(new GuiMenuSideButton(24, this.guiLeft - 69, this.guiTop + 65, 70, 22, "Tab 4"));
        this.addSideButton(new GuiMenuSideButton(25, this.guiLeft - 69, this.guiTop + 86, 70, 22, "Tab 5"));
        this.addSideButton(new GuiMenuSideButton(26, this.guiLeft - 69, this.guiTop + 107, 70, 22, "Tab 6"));
        this.addSideButton(new GuiMenuSideButton(27, this.guiLeft - 69, this.guiTop + 128, 70, 22, "Tab 7"));
        this.addSideButton(new GuiMenuSideButton(28, this.guiLeft - 69, this.guiTop + 149, 70, 22, "Tab 8"));
        this.addSideButton(new GuiMenuSideButton(29, this.guiLeft - 69, this.guiTop + 170, 70, 22, "Tab 9"));
        this.getSideButton((int)(20 + this.activeTab)).active = true;
        this.showClones();
    }

    private void showClones() {
        this.cloneData.clear();
        ArrayList<String> list = new ArrayList<String>();
        for (by comp : CloneController.getClones()) {
            String name = comp.i("ClonedName");
            int i = 1;
            while (list.contains(name)) {
                name = String.format("%s%s", comp.i("ClonedName"), ++i);
            }
            int tab = 1;
            if (comp.b("ClonedTab")) {
                tab = comp.e("ClonedTab");
            }
            if (this.activeTab != tab) continue;
            list.add(name);
            this.cloneData.put(name, comp);
        }
        this.list = list;
        this.scroll.setList(this.getSearchList());
    }

    @Override
    public void a(char c, int i) {
        super.a(c, i);
        if (!search.equals(this.getTextField(1).b())) {
            search = this.getTextField(1).b().toLowerCase();
            this.scroll.setList(this.getSearchList());
        }
    }

    private List<String> getSearchList() {
        if (search.isEmpty()) {
            return new ArrayList<String>(this.list);
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String name : this.list) {
            if (!name.toLowerCase().contains(search)) continue;
            list.add(name);
        }
        return list;
    }

    public by getCompound() {
        String sel = this.scroll.getSelected();
        if (sel == null) {
            return null;
        }
        by compound = this.cloneData.get(sel);
        compound.o("StartPos");
        return compound;
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 0) {
            this.close();
        }
        if (guibutton.g == 1) {
            this.scroll.clear();
            this.close();
        }
        if (guibutton.g > 20) {
            this.activeTab = guibutton.g - 20;
            this.A_();
        }
    }

    protected cg newDoubleNBTList(double ... par1ArrayOfDouble) {
        cg nbttaglist = new cg();
        double[] adouble = par1ArrayOfDouble;
        int i = par1ArrayOfDouble.length;
        for (int j = 0; j < i; ++j) {
            double d1 = adouble[j];
            nbttaglist.a((cl)new cb(null, d1));
        }
        return nbttaglist;
    }

    @Override
    public void save() {
    }
}

