/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 */
package noppes.client.gui.questtypes;

import noppes.client.gui.GuiNPCDialogSelection;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.common.controllers.Quest;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.quests.QuestDialog;
import noppes.util.NoppesUtil;

public class GuiNpcQuestTypeDialog
extends GuiNPCInterface
implements GuiSelectionListener {
    private final awe parent;
    private final QuestDialog quest;
    private int selectedSlot;

    public GuiNpcQuestTypeDialog(EntityNPCInterface npc, Quest q, awe parent) {
        super(npc);
        this.parent = parent;
        this.title = "Quest Dialog Setup";
        this.quest = (QuestDialog)q.questInterface;
    }

    @Override
    public void A_() {
        super.A_();
        for (int i = 0; i < 3; ++i) {
            String title = "dialog.selectoption";
            this.addButton(i + 9, new GuiNpcButton(i + 9, this.g / 2 - 100, 55 + i * 22, 20, 20, "X"));
            this.addButton(i + 3, new GuiNpcButton(i + 3, this.g / 2 - 78, 55 + i * 22, 140, 20, title));
        }
        this.addButton(0, new GuiNpcButton(0, this.g / 2 - 100, 215, 98, 20, "gui.back"));
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        int slot;
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            NoppesUtil.openGUI(this.player, this.parent);
        }
        if (guibutton.g >= 3 && guibutton.g < 9) {
            this.close();
            this.selectedSlot = guibutton.g - 3;
            slot = -1;
            if (this.quest.dialogs.containsKey(this.selectedSlot)) {
                slot = this.quest.dialogs.get(this.selectedSlot);
            }
            NoppesUtil.openGUI(this.player, new GuiNPCDialogSelection(this.npc, this, slot));
        }
        if (guibutton.g >= 9 && guibutton.g < 15) {
            slot = guibutton.g - 9;
            this.quest.dialogs.remove(slot);
            this.save();
            this.A_();
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void selected(int id) {
        this.quest.dialogs.put(this.selectedSlot, id);
    }
}

