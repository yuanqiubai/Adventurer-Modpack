/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  bu
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IScrollData;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.controllers.FactionOptions;
import noppes.network.server.EditFactionPackets;

public class SubGuiNpcFactionOptions
extends SubGuiInterface
implements IScrollData,
GuiCustomScrollActionListener {
    private final FactionOptions options;
    private HashMap<String, Integer> data = new HashMap();
    private GuiCustomScroll scrollFactions;
    private int selected = -1;
    private static final ey FACTIONS_GET = new EditFactionPackets.FactionsGetPacket().makePacket("CNPCs Server");

    public SubGuiNpcFactionOptions(FactionOptions options) {
        this.options = options;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
        PacketDispatcher.sendPacketToServer((ey)FACTIONS_GET);
    }

    @Override
    public void A_() {
        String label;
        super.A_();
        if (this.scrollFactions == null) {
            this.scrollFactions = new GuiCustomScroll(this, 0);
            this.scrollFactions.setSize(120, 208);
            this.scrollFactions.guiLeft = this.guiLeft + 130;
            this.scrollFactions.guiTop = this.guiTop + 4;
        }
        this.addScroll(this.scrollFactions, this.f);
        this.addLabel(new GuiNpcLabel(0, "1: ", this.guiLeft + 4, this.guiTop + 12, 0x404040));
        if (this.data.containsValue(this.options.factionId)) {
            this.addLabel(new GuiNpcLabel(1, this.getFactionName(this.options.factionId), this.guiLeft + 12, this.guiTop + 8, 0x404040));
            label = "";
            label = this.options.decreaseFactionPoints ? label + bu.a((String)"gui.decrease") : label + bu.a((String)"gui.increase");
            label = label + " " + this.options.factionPoints + " " + bu.a((String)"faction.points");
            this.addLabel(new GuiNpcLabel(3, label, this.guiLeft + 12, this.guiTop + 16, 0x404040));
            this.addButton(new GuiNpcButton(0, this.guiLeft + 110, this.guiTop + 7, 20, 20, "X"));
        }
        this.addLabel(new GuiNpcLabel(4, "2: ", this.guiLeft + 4, this.guiTop + 40, 0x404040));
        if (this.data.containsValue(this.options.faction2Id)) {
            this.addLabel(new GuiNpcLabel(5, this.getFactionName(this.options.faction2Id), this.guiLeft + 12, this.guiTop + 36, 0x404040));
            label = "";
            label = this.options.decreaseFaction2Points ? label + bu.a((String)"gui.decrease") : label + bu.a((String)"gui.increase");
            label = label + " " + this.options.faction2Points + " " + bu.a((String)"faction.points");
            this.addLabel(new GuiNpcLabel(6, label, this.guiLeft + 12, this.guiTop + 44, 0x404040));
            this.addButton(new GuiNpcButton(1, this.guiLeft + 110, this.guiTop + 35, 20, 20, "X"));
        }
        if (!(this.selected < 0 || this.data.containsValue(this.options.faction2Id) && this.data.containsValue(this.options.factionId) || this.options.hasFaction(this.selected))) {
            this.addButton(new GuiNpcButton(2, this.guiLeft + 4, this.guiTop + 60, 90, 20, new String[]{"gui.increase", "gui.decrease"}, 0));
            this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 4, this.guiTop + 82, 110, 20, "10"));
            this.getTextField((int)1).numbersOnly = true;
            this.getTextField(1).setMinMaxDefault(1, 100000, 10);
            this.addButton(new GuiNpcButton(3, this.guiLeft + 4, this.guiTop + 104, 60, 20, "gui.add"));
        }
        this.addButton(new GuiNpcButton(66, this.guiLeft + 20, this.guiTop + 192, 90, 20, "gui.done"));
    }

    private String getFactionName(int faction) {
        String s;
        Iterator<String> i$ = this.data.keySet().iterator();
        do {
            if (i$.hasNext()) continue;
            return null;
        } while (this.data.get(s = i$.next()) != faction);
        return s;
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 0) {
            this.options.factionId = -1;
            this.A_();
        }
        if (guibutton.g == 1) {
            this.options.faction2Id = -1;
            this.A_();
        }
        if (guibutton.g == 3) {
            if (!this.data.containsValue(this.options.factionId)) {
                this.options.factionId = this.selected;
                this.options.decreaseFactionPoints = this.getButton(2).getValue() == 1;
                this.options.factionPoints = this.getTextField(1).getInteger();
            } else if (!this.data.containsValue(this.options.faction2Id)) {
                this.options.faction2Id = this.selected;
                this.options.decreaseFaction2Points = this.getButton(2).getValue() == 1;
                this.options.faction2Points = this.getTextField(1).getInteger();
            }
            this.A_();
        }
        if (guibutton.g == 66) {
            this.close();
        }
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        this.selected = this.data.get(guiCustomScroll.getSelected());
        this.A_();
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        GuiCustomScroll scroll = this.getScroll(0);
        String name = scroll.getSelected();
        this.data = data;
        scroll.setList(list);
        if (name != null) {
            scroll.setSelected(name);
        }
        this.A_();
    }

    @Override
    public void setSelected(String selected) {
    }
}

