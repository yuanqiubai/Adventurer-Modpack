/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  awe
 *  bjo
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 *  uf
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiMenuSideButton;
import noppes.client.gui.util.GuiPlayerTopMenu;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.ITopButtonListener;
import noppes.common.quests.QuestLogData;
import noppes.network.player.QuestPackets;
import noppes.util.NoppesStringUtils;
import org.lwjgl.opengl.GL11;

public class GuiQuestLog
extends awe
implements ITopButtonListener,
GuiCustomScrollActionListener,
IGuiData {
    private final bjo resource = new bjo("customnpcs", "textures/gui/standardbg.png");
    protected int xSize;
    protected int guiLeft;
    protected int guiTop;
    private final uf player;
    private GuiCustomScroll scroll;
    private final HashMap<Integer, GuiMenuSideButton> sideButtons = new HashMap();
    private QuestLogData data = new QuestLogData();
    private boolean noQuests = false;
    private GuiPlayerTopMenu topMenu;
    private int yoffset;

    public GuiQuestLog(uf player) {
        this.player = player;
        this.xSize = 240;
        PacketDispatcher.sendPacketToServer((ey)new QuestPackets.QuestLogPacket().makePacket("CNPCs Player"));
    }

    public void A_() {
        super.A_();
        this.sideButtons.clear();
        this.guiLeft = (this.g - this.xSize) / 2;
        this.guiTop = (this.h - 176) / 2;
        this.topMenu = new GuiPlayerTopMenu(this.guiLeft + 35, this.guiTop - 17, 2, this.player);
        this.noQuests = false;
        if (this.data.categories.isEmpty()) {
            this.noQuests = true;
        } else {
            ArrayList<String> categories = new ArrayList<String>();
            categories.addAll(this.data.categories.keySet());
            Collections.sort(categories, String.CASE_INSENSITIVE_ORDER);
            int i = 0;
            for (String category : categories) {
                if (this.data.selectedCategory.isEmpty()) {
                    this.data.selectedCategory = category;
                }
                this.sideButtons.put(i, new GuiMenuSideButton(i, this.guiLeft - 89, this.guiTop + 2 + i * 21, 90, 22, category));
                ++i;
            }
            this.sideButtons.get((Object)Integer.valueOf((int)categories.indexOf((Object)this.data.selectedCategory))).active = true;
            this.scroll = new GuiCustomScroll(this, 0);
            this.scroll.setList((List<String>)this.data.categories.get(this.data.selectedCategory));
            this.scroll.a(this.f, 350, 250);
            this.scroll.setSize(144, 183);
            this.scroll.guiLeft = this.guiLeft + 6;
            this.scroll.guiTop = this.guiTop + 6;
        }
    }

    public void a(int i, int j, float f) {
        this.e();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        this.b(this.guiLeft, this.guiTop, 0, 0, 252, 195);
        this.b(this.guiLeft + 252, this.guiTop, 188, 0, 67, 195);
        super.a(i, j, f);
        this.topMenu.a(i, j, f);
        if (this.noQuests) {
            this.o.b("You have no active quests", this.guiLeft + 84, this.guiTop + 80, 0x404040);
        } else {
            for (GuiMenuSideButton button : this.sideButtons.values().toArray(new GuiMenuSideButton[this.sideButtons.size()])) {
                button.a(this.f, i, j);
            }
            this.scroll.a(i, j, f);
            if (this.data.hasSelectedQuest()) {
                this.drawQuestText();
            }
        }
    }

    private void drawQuestText() {
        this.yoffset = this.guiTop + 5;
        int x = 0;
        this.drawProgress();
        this.yoffset += 4;
        int xoffset = this.guiLeft + 150;
        String text = NoppesStringUtils.formatText(this.data.getQuestText(), this.player.bu);
        String line = "";
        for (char c : text.toCharArray()) {
            if (c != '\r' && c != '\n') {
                if (this.o.a(line + c) > 176) {
                    this.o.b(line, xoffset, this.yoffset + x * this.o.a, 0x404040);
                    line = "";
                    ++x;
                }
                line = line + c;
                continue;
            }
            this.o.b(line, xoffset, this.yoffset + x * this.o.a, 0x404040);
            line = "";
            ++x;
        }
        this.o.b(line, xoffset, this.yoffset + x * this.o.a, 0x404040);
    }

    private void drawProgress() {
        int xoffset = this.guiLeft + 152;
        for (String process : this.data.getQuestStatus()) {
            this.o.b(process, xoffset, this.yoffset, 0x404040);
            this.yoffset += 10;
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    }

    public void a(int i, int j, int k) {
        super.a(i, j, k);
        this.topMenu.a(i, j, k);
        if (k == 0) {
            if (this.scroll != null) {
                this.scroll.a(i, j, k);
            }
            for (GuiMenuSideButton button : this.sideButtons.values().toArray(new GuiMenuSideButton[this.sideButtons.size()])) {
                if (!button.c(this.f, i, j)) continue;
                this.sideButtonPressed(button);
            }
        }
    }

    private void sideButtonPressed(GuiMenuSideButton button) {
        if (!button.active) {
            this.f.v.a("random.click", 1.0f, 1.0f);
            this.data.selectedCategory = button.f;
            this.data.selectedQuest = "";
            this.A_();
        }
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll scroll) {
        if (scroll.hasSelected()) {
            this.data.selectedQuest = scroll.getSelected();
        }
    }

    protected void a(char c, int i) {
        if (i == 1 || i == this.f.u.N.d) {
            this.f.a(null);
            this.f.g();
        }
    }

    public boolean f() {
        return false;
    }

    @Override
    public void setGuiData(by compound) {
        QuestLogData data = new QuestLogData();
        data.readNBT(compound);
        this.data = data;
        this.A_();
    }
}

