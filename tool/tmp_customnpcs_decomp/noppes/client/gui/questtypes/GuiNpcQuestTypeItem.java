/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  bjo
 *  org.lwjgl.opengl.GL11
 *  uf
 */
package noppes.client.gui.questtypes;

import noppes.client.gui.global.GuiNPCManageQuest;
import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.containers.ContainerNpcQuestTypeItem;
import noppes.common.controllers.Quest;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;
import org.lwjgl.opengl.GL11;

public class GuiNpcQuestTypeItem
extends GuiContainerNPCInterface
implements ITextfieldListener {
    private static final bjo craftingTableGuiTextures = new bjo("customnpcs", "textures/gui/followersetup.png");
    private final Quest quest = GuiNPCManageQuest.quest;

    public GuiNpcQuestTypeItem(EntityNPCInterface npc, ContainerNpcQuestTypeItem container) {
        super(npc, container);
        this.title = "Quest Item Setup";
        this.d = 202;
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(5, this.p, this.q + this.d, 98, 20, "gui.back"));
    }

    public void a(aut guibutton) {
        if (guibutton.g == 5) {
            NoppesUtil.openGUI((uf)this.player, GuiNPCManageQuest.Instance);
        }
    }

    public void b() {
    }

    @Override
    protected void a(char c, int i) {
    }

    @Override
    protected void a(float f, int i, int j) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(craftingTableGuiTextures);
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

