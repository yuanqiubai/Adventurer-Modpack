/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  awe
 *  axv
 *  uf
 */
package noppes.client.gui.util;

import java.util.HashMap;
import noppes.client.gui.player.GuiFaction;
import noppes.client.gui.player.GuiQuestLog;
import noppes.client.gui.util.GuiMenuTopButton;
import noppes.client.gui.util.ITopButtonListener;
import noppes.util.NoppesUtil;

public class GuiPlayerTopMenu
extends awe
implements ITopButtonListener {
    private final int x;
    private final int y;
    private final int active;
    private final uf player;
    private final HashMap<Integer, GuiMenuTopButton> topButtons = new HashMap();

    public GuiPlayerTopMenu(int x, int y, int active, uf player) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.active = active;
        this.A_();
        this.f = atv.w();
    }

    public void A_() {
        super.A_();
        this.topButtons.clear();
        GuiMenuTopButton inv = new GuiMenuTopButton(1, this.x, this.y, "menu.inventory");
        GuiMenuTopButton quests = new GuiMenuTopButton(2, inv, "quest.quests");
        GuiMenuTopButton factions = new GuiMenuTopButton(3, quests, "menu.factions");
        this.topButtons.put(1, inv);
        this.topButtons.put(2, quests);
        this.topButtons.put(3, factions);
        this.topButtons.get((Object)Integer.valueOf((int)this.active)).active = true;
    }

    public void a(int i, int j, float f) {
        for (GuiMenuTopButton button : this.topButtons.values()) {
            button.a(this.f, i, j);
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    }

    public void a(int i, int j, int k) {
        super.a(i, j, k);
        if (k == 0) {
            for (GuiMenuTopButton button : this.topButtons.values()) {
                if (!button.c(this.f, i, j)) continue;
                this.topButtonPressed(button);
            }
        }
    }

    private void topButtonPressed(GuiMenuTopButton button) {
        if (!button.active) {
            this.f.v.a("random.click", 1.0f, 1.0f);
            if (button.g == 1) {
                NoppesUtil.openGUI(this.player, new axv(this.player));
            }
            if (button.g == 2) {
                NoppesUtil.openGUI(this.player, new GuiQuestLog((uf)this.f.h));
            }
            if (button.g == 3) {
                NoppesUtil.openGUI(this.player, new GuiFaction());
            }
        }
    }
}

