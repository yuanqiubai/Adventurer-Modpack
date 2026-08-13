/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  bjo
 *  org.lwjgl.opengl.GL11
 *  uf
 */
package noppes.client.gui.global;

import noppes.client.gui.global.GuiNPCManageQuest;
import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.containers.ContainerNpcQuestReward;
import noppes.common.controllers.Quest;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;
import org.lwjgl.opengl.GL11;

public class GuiNpcQuestReward
extends GuiContainerNPCInterface
implements ITextfieldListener {
    private final Quest quest = GuiNPCManageQuest.quest;
    private final bjo resource = this.getResource("questreward.png");

    public GuiNpcQuestReward(EntityNPCInterface npc, ContainerNpcQuestReward container) {
        super(npc, container);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(5, this.p, this.q + this.d, 98, 20, "gui.back"));
        this.addLabel(new GuiNpcLabel(1, "Exp:", this.p + 5, this.q + 45, 0x404040));
        this.addTextField(new GuiNpcTextField(0, (awe)this, this.o, this.p + 30, this.q + 40, 60, 20, String.valueOf(this.quest.rewardExp)));
        this.getTextField((int)0).numbersOnly = true;
        this.getTextField(0).setMinMaxDefault(0, 99999, 0);
    }

    public void a(aut guibutton) {
        if (guibutton.g == 5) {
            NoppesUtil.openGUI((uf)this.player, GuiNPCManageQuest.Instance);
        }
    }

    public void b() {
    }

    @Override
    protected void a(float f, int i, int j) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        int l = (this.g - this.c) / 2;
        int i1 = (this.h - this.d) / 2;
        this.b(l, i1, 0, 0, this.c, this.d);
        super.a(f, i, j);
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
    }

    @Override
    public void save() {
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        this.quest.rewardExp = textfield.getInteger();
    }
}

