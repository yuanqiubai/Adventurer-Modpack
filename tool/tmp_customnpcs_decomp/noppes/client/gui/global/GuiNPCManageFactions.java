/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.global;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import noppes.client.gui.SubGuiNpcFactionPoints;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.IScrollData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.controllers.Faction;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditFactionPackets;

public class GuiNPCManageFactions
extends GuiNPCInterface2
implements IScrollData,
GuiCustomScrollActionListener,
ITextfieldListener,
IGuiData {
    private GuiCustomScroll scrollFactions;
    private HashMap<String, Integer> data = new HashMap();
    private Faction faction = new Faction();
    private String selected = null;
    private static final ey FACTIONS_GET = new EditFactionPackets.FactionsGetPacket().makePacket("CNPCs Server");

    public GuiNPCManageFactions(EntityNPCInterface npc) {
        super(npc);
        PacketDispatcher.sendPacketToServer((ey)FACTIONS_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(0, this.guiLeft + 368, this.guiTop + 8, 45, 20, "gui.add"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 368, this.guiTop + 32, 45, 20, "gui.remove"));
        if (this.scrollFactions == null) {
            this.scrollFactions = new GuiCustomScroll(this, 0);
            this.scrollFactions.setSize(143, 208);
            this.scrollFactions.guiLeft = this.guiLeft + 220;
            this.scrollFactions.guiTop = this.guiTop + 4;
        }
        this.addScroll(this.scrollFactions);
        if (this.faction.id != -1) {
            this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 48, this.guiTop + 4, 165, 20, this.faction.name));
            this.getTextField(0).f(20);
            this.addLabel(new GuiNpcLabel(0, "gui.name", this.guiLeft + 8, this.guiTop + 9, 0x404040));
            String color = Integer.toHexString(this.faction.color);
            while (color.length() < 6) {
                color = "0" + color;
            }
            this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 48, this.guiTop + 26, 165, 20, color));
            this.addLabel(new GuiNpcLabel(1, "gui.color", this.guiLeft + 8, this.guiTop + 31, 0x404040));
            this.getTextField(1).f(6);
            this.getTextField(1).g(this.faction.color);
            this.addLabel(new GuiNpcLabel(2, "faction.points", this.guiLeft + 8, this.guiTop + 53, 0x404040));
            this.addButton(new GuiNpcButton(2, this.guiLeft + 80, this.guiTop + 48, 45, 20, "selectServer.edit"));
            this.addLabel(new GuiNpcLabel(3, "faction.hidden", this.guiLeft + 8, this.guiTop + 75, 0x404040));
            this.addButton(new GuiNpcButton(3, this.guiLeft + 80, this.guiTop + 70, 45, 20, new String[]{"gui.no", "gui.yes"}, this.faction.hideFaction ? 1 : 0));
            this.addLabel(new GuiNpcLabel(4, "faction.attacked", this.guiLeft + 8, this.guiTop + 97, 0x404040));
            this.addButton(new GuiNpcButton(4, this.guiLeft + 80, this.guiTop + 92, 45, 20, new String[]{"gui.no", "gui.yes"}, this.faction.getsAttacked ? 1 : 0));
            this.addLabel(new GuiNpcLabel(6, "faction.hostiles", this.guiLeft + 8, this.guiTop + 145, 0x404040));
            ArrayList<String> hostileList = new ArrayList<String>(this.scrollFactions.getList());
            hostileList.remove(this.faction.name);
            HashSet<String> set = new HashSet<String>();
            for (String s : this.data.keySet()) {
                if (s.equals(this.faction.name) || !this.faction.attackFactions.contains(this.data.get(s))) continue;
                set.add(s);
            }
            GuiCustomScroll scrollHostileFactions1 = new GuiCustomScroll(this, 1, true);
            scrollHostileFactions1.setSize(163, 58);
            scrollHostileFactions1.guiLeft = this.guiLeft + 4;
            scrollHostileFactions1.guiTop = this.guiTop + 154;
            scrollHostileFactions1.setList(hostileList);
            scrollHostileFactions1.setSelectedList(set);
            this.addScroll(scrollHostileFactions1);
        }
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            this.save();
            String name = "New";
            while (this.data.containsKey(name)) {
                name = name + "_";
            }
            Faction faction = new Faction(-1, name, 65280, 1000);
            by compound = new by();
            faction.writeNBT(compound);
            ey pkt = new EditFactionPackets.FactionSavePacket(compound).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
        if (guibutton.g == 1 && this.data.containsKey(this.scrollFactions.getSelected())) {
            ey pkt = new EditFactionPackets.FactionRemovePacket(this.data.get(this.selected)).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
            this.scrollFactions.clear();
            this.faction = new Faction();
            this.A_();
        }
        if (guibutton.g == 2) {
            this.setSubGui(new SubGuiNpcFactionPoints(this.faction));
        }
        if (guibutton.g == 3) {
            boolean bl = this.faction.hideFaction = button.getValue() == 1;
        }
        if (guibutton.g == 4) {
            this.faction.getsAttacked = button.getValue() == 1;
        }
    }

    @Override
    public void setGuiData(by compound) {
        this.faction = new Faction();
        this.faction.readNBT(compound);
        this.setSelected(this.faction.name);
        this.A_();
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        String name = this.scrollFactions.getSelected();
        this.data = data;
        this.scrollFactions.setList(list);
        if (name != null) {
            this.scrollFactions.setSelected(name);
        }
    }

    @Override
    public void setSelected(String selected) {
        this.selected = selected;
        this.scrollFactions.setSelected(selected);
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.save();
            this.selected = this.scrollFactions.getSelected();
            ey pkt = new EditFactionPackets.FactionGetPacket(this.data.get(this.selected)).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        } else if (guiCustomScroll.id == 1) {
            HashSet<Integer> set = new HashSet<Integer>();
            for (String s : guiCustomScroll.getSelectedList()) {
                if (!this.data.containsKey(s)) continue;
                set.add(this.data.get(s));
            }
            this.faction.attackFactions = set;
            this.save();
        }
    }

    @Override
    public void save() {
        if (this.selected != null && this.data.containsKey(this.selected) && this.faction != null) {
            by compound = new by();
            this.faction.writeNBT(compound);
            ey pkt = new EditFactionPackets.FactionSavePacket(compound).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
    }

    @Override
    public void unFocused(GuiNpcTextField guiNpcTextField) {
        if (this.faction.id != -1) {
            if (guiNpcTextField.id == 0) {
                String color = guiNpcTextField.b();
                if (!color.isEmpty() && !this.data.containsKey(color)) {
                    String e = this.faction.name;
                    this.data.remove(this.faction.name);
                    this.faction.name = color;
                    this.data.put(this.faction.name, this.faction.id);
                    this.selected = color;
                    this.scrollFactions.replace(e, this.faction.name);
                }
            } else if (guiNpcTextField.id == 1) {
                int color2;
                boolean color1 = false;
                try {
                    color2 = Integer.parseInt(guiNpcTextField.b(), 16);
                }
                catch (NumberFormatException var4) {
                    color2 = 0;
                }
                this.faction.color = color2;
                guiNpcTextField.g(this.faction.color);
            }
        }
    }
}

