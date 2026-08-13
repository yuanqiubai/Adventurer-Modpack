/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 */
package noppes.client.gui.questtypes;

import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.controllers.Quest;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.quests.QuestLocation;
import noppes.util.NoppesUtil;

public class GuiNpcQuestTypeLocation
extends GuiNPCInterface
implements ITextfieldListener {
    private final awe parent;
    private final QuestLocation quest;

    public GuiNpcQuestTypeLocation(EntityNPCInterface npc, Quest q, awe parent) {
        super(npc);
        this.parent = parent;
        this.title = "Quest Location Setup";
        this.quest = (QuestLocation)q.questInterface;
    }

    @Override
    public void A_() {
        super.A_();
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft - 100, this.guiTop + 70, 180, 20, this.quest.location));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft - 100, this.guiTop + 92, 180, 20, this.quest.location2));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft - 100, this.guiTop + 114, 180, 20, this.quest.location3));
        this.addButton(new GuiNpcButton(0, this.guiLeft - 100, this.guiTop + 140, 98, 20, "gui.back"));
    }

    @Override
    protected void a(aut guibutton) {
        super.a(guibutton);
        if (guibutton.g == 0) {
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 0) {
            this.quest.location = textfield.b();
        }
        if (textfield.id == 1) {
            this.quest.location2 = textfield.b();
        }
        if (textfield.id == 2) {
            this.quest.location3 = textfield.b();
        }
    }
}

