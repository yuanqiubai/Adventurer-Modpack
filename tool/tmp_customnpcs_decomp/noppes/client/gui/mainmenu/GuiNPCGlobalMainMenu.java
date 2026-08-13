/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui.mainmenu;

import noppes.client.gui.global.GuiNpcManagePlayerData;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.common.constants.EnumGuiType;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;

public class GuiNPCGlobalMainMenu
extends GuiNPCInterface2 {
    public GuiNPCGlobalMainMenu(EntityNPCInterface npc) {
        super(npc, 5);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(2, this.guiLeft + 85, this.guiTop + 20, "global.banks"));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 85, this.guiTop + 42, "menu.factions"));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 85, this.guiTop + 64, "dialog.dialogs"));
        this.addButton(new GuiNpcButton(11, this.guiLeft + 85, this.guiTop + 86, "quest.quests"));
        this.addButton(new GuiNpcButton(12, this.guiLeft + 85, this.guiTop + 108, "global.transport"));
        this.addButton(new GuiNpcButton(13, this.guiLeft + 85, this.guiTop + 130, "global.playerdata"));
        this.addButton(new GuiNpcButton(14, this.guiLeft + 85, this.guiTop + 152, "global.recipes"));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 11) {
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageQuests);
        }
        if (guibutton.g == 2) {
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageBanks);
        }
        if (guibutton.g == 3) {
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageFactions);
        }
        if (guibutton.g == 4) {
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageDialogs);
        }
        if (guibutton.g == 12) {
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageTransport);
        }
        if (guibutton.g == 13) {
            NoppesUtil.openGUI(this.player, new GuiNpcManagePlayerData(this.npc, this));
        }
        if (guibutton.g == 14) {
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageRecipes, 4, 0, 0);
        }
    }

    @Override
    public void save() {
    }
}

