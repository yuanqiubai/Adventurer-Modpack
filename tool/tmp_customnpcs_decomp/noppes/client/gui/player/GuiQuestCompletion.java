/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  bjo
 *  bu
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.ITopButtonListener;
import noppes.common.controllers.Quest;
import noppes.network.player.QuestPackets;
import noppes.util.NoppesStringUtils;
import org.lwjgl.opengl.GL11;

public class GuiQuestCompletion
extends GuiNPCInterface
implements ITopButtonListener {
    private final bjo resource = new bjo("customnpcs", "textures/gui/smallbg.png");
    private final int xSize = 176;
    private final int ySize = 222;
    private int guiLeft;
    private int guiTop;
    private final Quest quest;

    public GuiQuestCompletion(Quest quest) {
        this.quest = quest;
        this.drawDefaultBackground = false;
        this.title = "";
    }

    @Override
    public void A_() {
        super.A_();
        this.guiLeft = (this.g - this.xSize) / 2;
        this.guiTop = (this.h - this.ySize) / 2;
        this.addButton(new GuiNpcButton(0, this.guiLeft + 38, this.guiTop + this.ySize - 24, 100, 20, bu.a((String)"quest.complete")));
    }

    @Override
    public void a(int i, int j, float f) {
        this.e();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        this.b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String questTitle = this.quest.title;
        int left = (this.xSize - this.o.a(questTitle)) / 2;
        this.addLabel(new GuiNpcLabel(0, questTitle, this.guiLeft + left, this.guiTop + 5, 0x404040));
        this.drawQuestText();
        super.a(i, j, f);
    }

    private void drawQuestText() {
        int x = 0;
        int yoffset = this.guiTop + 20;
        int xoffset = this.guiLeft + 4;
        String text = NoppesStringUtils.formatText(this.quest.completeText, this.player.bu);
        String line = "";
        for (char c : text.toCharArray()) {
            if (c != '\r' && c != '\n') {
                if (this.o.a(line + c) > 176) {
                    this.o.b(line, xoffset, yoffset + x * this.o.a, 0x404040);
                    line = "";
                    ++x;
                }
                line = line + c;
                continue;
            }
            this.o.b(line, xoffset, yoffset + x * this.o.a, 0x404040);
            line = "";
            ++x;
        }
        this.o.b(line, xoffset, yoffset + x * this.o.a, 0x404040);
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 0) {
            PacketDispatcher.sendPacketToServer((ey)new QuestPackets.QuestCompletionPacket(this.quest.id).makePacket("CNPCs Player"));
            this.close();
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
    }

    @Override
    public void a(char c, int i) {
        if (i == 1 || i == this.f.u.N.d) {
            this.close();
        }
    }

    @Override
    public void save() {
    }
}

