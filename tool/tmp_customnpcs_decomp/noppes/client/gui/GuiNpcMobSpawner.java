/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  atv
 *  aut
 *  by
 *  cb
 *  cg
 *  cl
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  nn
 *  nt
 *  og
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import noppes.client.controllers.CloneController;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiMenuSideButton;
import noppes.client.gui.util.GuiMenuTopButton;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.network.server.EditSpawnPackets;

public class GuiNpcMobSpawner
extends GuiNPCInterface {
    private static boolean showingClones = true;
    private static String search = "";
    private GuiCustomScroll scroll;
    private final int posX;
    private final int posY;
    private final int posZ;
    private final HashMap<String, by> cloneData = new HashMap();
    private List<by> data = new ArrayList<by>();
    private ArrayList<String> list;
    private int activeTab = 1;

    public GuiNpcMobSpawner(int i, int j, int k) {
        this.xSize = 256;
        this.posX = i;
        this.posY = j;
        this.posZ = k;
        this.closeOnEsc = true;
        this.setBackground("menubg.png");
    }

    @Override
    public void A_() {
        super.A_();
        this.guiTop += 10;
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
        GuiMenuTopButton button = new GuiMenuTopButton(3, this.guiLeft + 4, this.guiTop - 17, "spawner.clones");
        this.addTopButton(button);
        button.active = showingClones;
        button = new GuiMenuTopButton(4, button, "spawner.entities");
        this.addTopButton(button);
        button.active = !showingClones;
        this.addButton(new GuiNpcButton(1, this.guiLeft + 170, this.guiTop + 6, 82, 20, "item.monsterPlacer.name"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 170, this.guiTop + 100, 82, 20, "spawner.mobspawner"));
        if (showingClones) {
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
        } else {
            this.showEntities();
        }
    }

    private void showEntities() {
        Map data = nt.b;
        ArrayList<String> list = new ArrayList<String>();
        for (Object name : data.keySet()) {
            Class c = (Class)data.get(name);
            try {
                if (!og.class.isAssignableFrom(c) || c.getConstructor(abw.class) == null || Modifier.isAbstract(c.getModifiers())) continue;
                list.add(name.toString());
            }
            catch (SecurityException var7) {
                var7.printStackTrace();
            }
            catch (NoSuchMethodException noSuchMethodException) {}
        }
        this.list = list;
        this.scroll.setList(this.getSearchList());
    }

    private void showClones() {
        this.addButton(new GuiNpcButton(5, this.guiLeft + 170, this.guiTop + 30, 82, 20, "gui.remove"));
        this.cloneData.clear();
        ArrayList<String> list = new ArrayList<String>();
        this.data = CloneController.getClones();
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

    private by getCompound() {
        String sel = this.scroll.getSelected();
        if (sel == null) {
            return null;
        }
        if (showingClones) {
            by nbt = this.cloneData.get(sel);
            nbt.o("StartPos");
            nbt.o("Pos");
            if (!nbt.b("ModRev")) {
                nbt.a("ModRev", 1);
            }
            return nbt;
        }
        nn entity = nt.a((String)sel, (abw)atv.w().f);
        by compound = new by();
        entity.d(compound);
        return compound;
    }

    @Override
    protected void a(aut guibutton) {
        by toCheck;
        if (guibutton.g == 0) {
            this.close();
        }
        if (guibutton.g == 1 && (toCheck = this.getCompound()) != null) {
            toCheck.a("Pos", (cl)this.newDoubleNBTList((double)this.posX + 0.5, this.posY + 1, (double)this.posZ + 0.5));
            toCheck.a("ItemGiverId", 0);
            toCheck.a("TransporterId", -1);
            PacketDispatcher.sendPacketToServer((ey)new EditSpawnPackets.SpawnMobPacket(toCheck).makePacket("CNPCs Server"));
            this.close();
        }
        if (guibutton.g == 2 && (toCheck = this.getCompound()) != null) {
            EditSpawnPackets.MobSpawnerPacket pkt = new EditSpawnPackets.MobSpawnerPacket(this.posX, this.posY, this.posZ, toCheck);
            PacketDispatcher.sendPacketToServer((ey)pkt.makePacket("CNPCs Server"));
            this.close();
        }
        if (guibutton.g == 3) {
            showingClones = true;
            this.A_();
        }
        if (guibutton.g == 4) {
            showingClones = false;
            this.A_();
        }
        if (guibutton.g == 5 && this.scroll.getSelected() != null) {
            toCheck = this.cloneData.get(this.scroll.getSelected());
            this.data.remove(toCheck);
            CloneController.saveClones(this.data);
            this.scroll.selected = -1;
            this.A_();
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

