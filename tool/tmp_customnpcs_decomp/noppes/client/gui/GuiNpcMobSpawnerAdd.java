/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  nn
 */
package noppes.client.gui;

import noppes.client.controllers.CloneController;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;

public class GuiNpcMobSpawnerAdd
extends GuiNPCInterface {
    private final nn toClone = CloneController.toClone;

    public GuiNpcMobSpawnerAdd() {
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        CloneController.toClone = null;
    }

    @Override
    public void A_() {
        super.A_();
        String name = this.toClone.an();
        this.addLabel(new GuiNpcLabel(0, "\u5b58\u50a8\u4e3a", this.guiLeft + 4, this.guiTop + 6, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 4, this.guiTop + 18, 200, 20, name));
        this.addLabel(new GuiNpcLabel(1, "Tab", this.guiLeft + 10, this.guiTop + 50, 0x404040));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 40, this.guiTop + 45, 20, 20, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, 0));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 70, 80, 20, "gui.save"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 86, this.guiTop + 70, 80, 20, "gui.cancel"));
    }

    @Override
    public void buttonEvent(aut guibutton) {
        if (guibutton.g == 0) {
            String name = this.getTextField(0).b();
            if (name.isEmpty()) {
                return;
            }
            CloneController.addClone(this.toClone, name, this.getButton(2).getValue() + 1);
            this.close();
        }
        if (guibutton.g == 1) {
            this.close();
        }
    }

    @Override
    public void save() {
    }
}

