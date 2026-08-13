/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.global;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IScrollData;
import noppes.common.constants.EnumPlayerData;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditPlayerPackets;

public class GuiNpcManagePlayerData
extends GuiNPCInterface2
implements IScrollData,
GuiCustomScrollActionListener {
    private GuiCustomScroll scroll;
    private String selectedPlayer = null;
    private String selected = null;
    private final HashMap<String, Integer> data = new HashMap();
    private EnumPlayerData selection = EnumPlayerData.Players;
    private String search = "";

    public GuiNpcManagePlayerData(EntityNPCInterface npc, GuiNPCInterface2 parent) {
        super(npc);
        ey pkt = new EditPlayerPackets.PlayerDataGet(0, null).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void A_() {
        super.A_();
        this.scroll = new GuiCustomScroll(this, 0);
        this.scroll.a(this.f, 350, 250);
        this.scroll.setSize(190, 175);
        this.scroll.guiLeft = this.guiLeft + 4;
        this.scroll.guiTop = this.guiTop + 16;
        this.addLabel(new GuiNpcLabel(0, "All Players", this.guiLeft + 10, this.guiTop + 6, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 200, this.guiTop + 10, 98, 20, "selectWorld.deleteButton"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 200, this.guiTop + 32, 98, 20, "Players"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 200, this.guiTop + 54, 98, 20, "Quest Data"));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 200, this.guiTop + 76, 98, 20, "Dialog Data"));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 200, this.guiTop + 98, 98, 20, "Transport Data"));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 200, this.guiTop + 120, 98, 20, "Bank Data"));
        this.addButton(new GuiNpcButton(6, this.guiLeft + 200, this.guiTop + 142, 98, 20, "Faction Data"));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 4, this.guiTop + 193, 190, 20, this.search));
        this.getTextField((int)0).enabled = this.selection == EnumPlayerData.Players;
        this.initButtons();
    }

    public void initButtons() {
        this.getButton((int)1).h = this.selection != EnumPlayerData.Players;
        this.getButton((int)2).h = this.selection != EnumPlayerData.Quest;
        this.getButton((int)3).h = this.selection != EnumPlayerData.Dialog;
        this.getButton((int)4).h = this.selection != EnumPlayerData.Transport;
        this.getButton((int)5).h = this.selection != EnumPlayerData.Bank;
        this.getButton((int)6).h = this.selection != EnumPlayerData.Factions;
        this.getLabel((int)0).label = this.selection == EnumPlayerData.Players ? "All Players" : "Selected player: " + this.selectedPlayer;
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
        this.scroll.a(i, j, f);
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        if (k == 0 && this.scroll != null) {
            this.scroll.a(i, j, k);
        }
    }

    @Override
    public void a(char c, int i) {
        super.a(c, i);
        if (this.selection == EnumPlayerData.Players && !this.search.equals(this.getTextField(0).b())) {
            this.search = this.getTextField(0).b().toLowerCase();
            this.scroll.setList(this.getSearchList());
        }
    }

    private List<String> getSearchList() {
        if (!this.search.isEmpty() && this.selection == EnumPlayerData.Players) {
            ArrayList<String> list = new ArrayList<String>();
            for (String name : this.data.keySet()) {
                if (!name.toLowerCase().contains(this.search)) continue;
                list.add(name);
            }
            return list;
        }
        return new ArrayList<String>(this.data.keySet());
    }

    @Override
    protected void a(aut guibutton) {
        ey pkt;
        if (guibutton.g == 0) {
            if (this.selected != null) {
                pkt = this.selection == EnumPlayerData.Players ? new EditPlayerPackets.PlayerDataRemove(0, this.selectedPlayer, 0).makePacket("CNPCs Server") : new EditPlayerPackets.PlayerDataRemove(this.selection.ordinal(), this.selectedPlayer, this.data.get(this.selected)).makePacket("CNPCs Server");
                PacketDispatcher.sendPacketToServer((ey)pkt);
                this.data.clear();
            }
            this.selected = null;
        }
        if (guibutton.g >= 1 && guibutton.g <= 6) {
            if (this.selectedPlayer == null && guibutton.g != 1) {
                return;
            }
            this.selection = EnumPlayerData.values()[guibutton.g - 1];
            this.initButtons();
            this.scroll.clear();
            this.data.clear();
            pkt = new EditPlayerPackets.PlayerDataGet(this.selection.ordinal(), this.selectedPlayer).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
            this.selected = null;
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.data.putAll(data);
        this.scroll.setList(this.getSearchList());
        if (this.selection == EnumPlayerData.Players && this.selectedPlayer != null) {
            this.scroll.setSelected(this.selectedPlayer);
            this.selected = this.selectedPlayer;
        }
    }

    @Override
    public void setSelected(String selected) {
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        this.selected = guiCustomScroll.getSelected();
        if (this.selection == EnumPlayerData.Players) {
            this.selectedPlayer = this.selected;
        }
    }
}

