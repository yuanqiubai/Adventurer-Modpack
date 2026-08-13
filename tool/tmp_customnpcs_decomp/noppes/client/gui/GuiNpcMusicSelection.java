/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  bu
 */
package noppes.client.gui;

import java.util.ArrayList;
import java.util.List;
import noppes.client.AssetsBrowser;
import noppes.client.controllers.MusicController;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNPCStringSlot;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;

public class GuiNpcMusicSelection
extends GuiNPCInterface {
    public awe listener;
    int depth = 0;
    private GuiNPCStringSlot slot;
    private final awe parent;
    private String location = "";
    private String selectedLocation = "";
    private String selectedFile = "";
    private final List<String> folders = new ArrayList<String>();
    private final List<String> files = new ArrayList<String>();
    private final String up = "..<" + bu.a((String)"gui.up") + ">..";

    public GuiNpcMusicSelection(EntityNPCInterface npc, awe parent, String sound) {
        super(npc);
        sound = sound.replaceAll("\\.", "/");
        if (sound.startsWith("customnpcs:")) {
            int index = sound.lastIndexOf("/");
            if (index < 0) {
                index = 11;
            }
            this.selectedFile = sound.substring(index + 1);
            sound = sound.substring(0, index);
            this.location = "/" + sound.substring(11);
            this.depth = this.location.split("/").length;
            this.selectedLocation = this.location;
        }
        this.drawDefaultBackground = false;
        this.title = "";
        this.listener = this.parent = parent;
    }

    @Override
    public void A_() {
        super.A_();
        String ss = bu.a((String)"gui.currentFolder") + ": " + this.location;
        this.addLabel(new GuiNpcLabel(0, ss, this.g / 2 - this.o.a(ss) / 2, 20, 0xFFFFFF));
        this.slot = new GuiNPCStringSlot(this.getList(), this, this.npc, false, 18);
        this.slot.d(4, 5);
        if (this.depth > 0 && this.selectedLocation.equals(this.location)) {
            this.slot.selected = this.selectedFile;
        }
        this.addButton(new GuiNpcButton(2, this.g / 2 - 100, this.h - 44, 98, 20, "gui.back"));
        this.addButton(new GuiNpcButton(1, this.g / 2 + 2, this.h - 44, 98, 20, "gui.play"));
    }

    private List<String> getList() {
        this.folders.clear();
        this.files.clear();
        ArrayList<String> list = new ArrayList<String>();
        if (this.depth > 0) {
            list.add(this.up);
        }
        AssetsBrowser browser = new AssetsBrowser("/customnpcs/music" + this.location, new String[]{"ogg", "wav"});
        for (String file : browser.folders) {
            this.folders.add("/" + file);
        }
        for (String file : browser.files) {
            if (this.files.contains(file = file.substring(0, file.lastIndexOf(".")))) continue;
            this.files.add(file);
        }
        list.addAll(this.folders);
        list.addAll(this.files);
        return list;
    }

    @Override
    public void a(int i, int j, float f) {
        this.slot.a(i, j, f);
        super.a(i, j, f);
    }

    @Override
    public void elementClicked() {
        if (this.slot.selected != null && this.files.contains(this.slot.selected)) {
            this.selectedLocation = this.location;
            this.selectedFile = this.slot.selected;
        }
    }

    public String getSelected() {
        if (this.selectedFile != null && !this.selectedFile.isEmpty()) {
            String file = this.selectedFile;
            if (!this.selectedLocation.isEmpty()) {
                file = this.selectedLocation.replaceAll("/", ".").substring(1) + "." + file;
            }
            return "customnpcs:" + file;
        }
        return "";
    }

    @Override
    public void doubleClicked() {
        if (this.slot.selected.equals(this.up)) {
            --this.depth;
            this.location = this.depth == 0 ? "" : this.location.substring(0, this.location.lastIndexOf("/"));
            this.A_();
        } else if (this.folders.contains(this.slot.selected)) {
            ++this.depth;
            this.location = this.location.endsWith("/") && this.slot.selected.startsWith("/") ? this.location + this.slot.selected.substring(1) : this.location + this.slot.selected;
            this.A_();
        } else {
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 1) {
            MusicController.Instance.playMusic(this.getSelected());
        }
        if (guibutton.g == 2) {
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    public void save() {
        if (this.listener instanceof GuiNPCInterface) {
            ((GuiNPCInterface)this.listener).elementClicked();
        } else if (this.listener instanceof GuiNPCInterface2) {
            ((GuiNPCInterface2)this.listener).elementClicked();
        }
    }
}

