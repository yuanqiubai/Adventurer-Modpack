/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.SubGuiNpcFactionOptions;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.IScrollData;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditFactionPackets;
import noppes.network.server.EditMainMenuPackets;

public class GuiNPCFactionSetup
extends GuiNPCInterface2
implements IScrollData,
GuiCustomScrollActionListener {
    private GuiCustomScroll scrollFactions;
    private HashMap<String, Integer> data = new HashMap();
    private static final ey FACTIONS_GET = new EditFactionPackets.FactionsGetPacket().makePacket("CNPCs Server");

    public GuiNPCFactionSetup(EntityNPCInterface npc) {
        super(npc);
        PacketDispatcher.sendPacketToServer((ey)FACTIONS_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "faction.attackHostile", this.guiLeft + 4, this.guiTop + 25, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 144, this.guiTop + 20, 40, 20, new String[]{"gui.no", "gui.yes"}, this.npc.advanced.attackOtherFactions ? 1 : 0));
        this.addLabel(new GuiNpcLabel(1, "faction.defend", this.guiLeft + 4, this.guiTop + 47, 0x404040));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 144, this.guiTop + 42, 40, 20, new String[]{"gui.no", "gui.yes"}, this.npc.advanced.defendFaction ? 1 : 0));
        this.addLabel(new GuiNpcLabel(12, "faction.ondeath", this.guiLeft + 4, this.guiTop + 69, 0x404040));
        this.addButton(new GuiNpcButton(12, this.guiLeft + 90, this.guiTop + 64, 80, 20, "faction.points"));
        this.scrollFactions = new GuiCustomScroll(this, 0);
        this.scrollFactions.a(this.f, 350, 250);
        this.scrollFactions.setSize(180, 200);
        this.scrollFactions.guiLeft = this.guiLeft + 200;
        this.scrollFactions.guiTop = this.guiTop + 4;
    }

    @Override
    public void a(int x, int y, float f) {
        super.a(x, y, f);
        this.scrollFactions.a(x, y, f);
    }

    @Override
    public void buttonEvent(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            boolean bl = this.npc.advanced.attackOtherFactions = button.getValue() == 1;
        }
        if (guibutton.g == 1) {
            boolean bl = this.npc.advanced.defendFaction = button.getValue() == 1;
        }
        if (button.g == 12) {
            this.setSubGui(new SubGuiNpcFactionOptions(this.npc.advanced.factions));
        }
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        String name = this.npc.getFaction().name;
        this.data = data;
        this.scrollFactions.setList(list);
        if (name != null) {
            this.setSelected(name);
        }
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        if (k == 0 && this.scrollFactions != null) {
            this.scrollFactions.a(i, j, k);
        }
    }

    @Override
    public void setSelected(String selected) {
        this.scrollFactions.setSelected(selected);
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            ey pkt = new EditFactionPackets.FactionSetPacket(this.data.get(this.scrollFactions.getSelected())).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}

