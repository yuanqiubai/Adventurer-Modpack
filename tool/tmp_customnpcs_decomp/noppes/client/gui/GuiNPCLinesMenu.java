/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui;

import noppes.client.gui.GuiNPCLinesEdit;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;

public class GuiNPCLinesMenu
extends GuiNPCInterface2 {
    public GuiNPCLinesMenu(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(0, this.guiLeft + 85, this.guiTop + 20, "World Lines"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 85, this.guiTop + 43, "Attack Lines"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 85, this.guiTop + 66, "Interact Lines"));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 85, this.guiTop + 89, "Killed Lines"));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 0) {
            NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.worldLines));
        }
        if (guibutton.g == 1) {
            NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.attackLines));
        }
        if (guibutton.g == 2) {
            NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.interactLines));
        }
        if (guibutton.g == 5) {
            NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.killedLines));
        }
    }

    @Override
    public void save() {
    }
}

