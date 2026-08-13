/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.global;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCStringSlot;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.IScrollData;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditQuestCategoriesPackets;
import noppes.network.server.EditQuestPackets;
import noppes.util.NoppesUtil;

public class GuiNPCQuestSelection
extends GuiNPCInterface
implements IScrollData {
    public GuiSelectionListener listener;
    private GuiNPCStringSlot slot;
    private final awe parent;
    private HashMap<String, Integer> data;
    private boolean selectCategory = true;
    private int quest;
    private static final ey QUEST_CATEGORIES_GET = new EditQuestCategoriesPackets.QuestCategoriesGetPacket().makePacket("CNPCs Server");

    public GuiNPCQuestSelection(EntityNPCInterface npc, awe parent, int quest) {
        super(npc);
        this.drawDefaultBackground = false;
        this.title = "Select Quest Category";
        this.parent = parent;
        this.data = new HashMap();
        this.quest = quest;
        if (quest >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditQuestPackets.QuestsGetFromQuestPacket(quest).makePacket("CNPCs Server"));
            this.selectCategory = false;
            this.title = "Select Dialog";
        } else {
            PacketDispatcher.sendPacketToServer((ey)QUEST_CATEGORIES_GET);
        }
        if (parent instanceof GuiSelectionListener) {
            this.listener = (GuiSelectionListener)parent;
        }
    }

    @Override
    public void A_() {
        super.A_();
        Vector<String> list = new Vector<String>();
        this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
        this.slot.d(4, 5);
        this.addButton(2, new GuiNpcButton(2, this.g / 2 - 100, this.h - 41, 98, 20, "gui.back"));
        this.addButton(4, new GuiNpcButton(4, this.g / 2 + 2, this.h - 41, 98, 20, "mco.template.button.select"));
    }

    @Override
    public void a(int i, int j, float f) {
        this.slot.a(i, j, f);
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 2) {
            if (this.selectCategory) {
                this.close();
                NoppesUtil.openGUI(this.player, this.parent);
            } else {
                this.title = "Select Dialog Category";
                this.selectCategory = true;
                PacketDispatcher.sendPacketToServer((ey)QUEST_CATEGORIES_GET);
            }
        }
        if (guibutton.g == 4) {
            if (this.slot.selected == null || this.slot.selected.isEmpty()) {
                return;
            }
            this.doubleClicked();
        }
    }

    public String getSelected() {
        return this.slot.selected;
    }

    @Override
    public void setSelected(String selected) {
    }

    @Override
    public void doubleClicked() {
        if (this.slot.selected != null && !this.slot.selected.isEmpty()) {
            if (this.selectCategory) {
                this.selectCategory = false;
                this.title = "Select Quest";
                PacketDispatcher.sendPacketToServer((ey)new EditQuestPackets.QuestsGetPacket(this.data.get(this.slot.selected)).makePacket("CNPCs Server"));
            } else {
                this.quest = this.data.get(this.slot.selected);
                this.close();
                NoppesUtil.openGUI(this.player, this.parent);
            }
        }
    }

    @Override
    public void save() {
        if (this.quest >= 0 && this.listener != null) {
            this.listener.selected(this.quest);
        }
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.data = data;
        this.slot.setList(list);
        if (this.quest >= 0) {
            for (String name : data.keySet()) {
                if (data.get(name) != this.quest) continue;
                this.slot.selected = name;
            }
        }
    }
}

