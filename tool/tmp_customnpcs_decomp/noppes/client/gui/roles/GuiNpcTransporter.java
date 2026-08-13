/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.mainmenu.GuiNpcAdvanced;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCStringSlot;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.IScrollData;
import noppes.common.controllers.TransportLocation;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditTransportPackets;
import noppes.util.NoppesUtil;

public class GuiNpcTransporter
extends GuiNPCInterface
implements IScrollData,
IGuiData {
    public TransportLocation location = new TransportLocation();
    private GuiNPCStringSlot slot;
    private HashMap<String, Integer> data = new HashMap();
    private static final ey TRANSPORT_CATEGORIES_GET = new EditTransportPackets.TransportCategoriesGetPacket().makePacket("CNPCs Server");
    private static final ey TRANSPORT_GET_LOCATION = new EditTransportPackets.TransportGetLocationPacket().makePacket("CNPCs Server");

    public GuiNpcTransporter(EntityNPCInterface npc) {
        super(npc);
        this.title = "Npc Transporter";
        PacketDispatcher.sendPacketToServer((ey)TRANSPORT_CATEGORIES_GET);
        PacketDispatcher.sendPacketToServer((ey)TRANSPORT_GET_LOCATION);
    }

    @Override
    public void A_() {
        super.A_();
        Vector<String> list = new Vector<String>();
        list.addAll(this.data.keySet());
        this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
        this.slot.d(4, 5);
        this.addLabel(new GuiNpcLabel(0, "Name:", this.guiLeft - 30, this.h - 49, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 1, this.h - 54, 198, 20, this.location.name));
        this.addButton(new GuiNpcButton(0, this.guiLeft, this.h - 31, new String[]{"Available when discovered", "Available from the start", "Available after interaction"}, this.location.type));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 210, this.h - 42, 98, 20, "Back"));
    }

    @Override
    public void a(int i, int j, float f) {
        this.slot.a(i, j, f);
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g == 0) {
            this.location.type = button.getValue();
        }
        if (button.g == 4) {
            this.close();
            NoppesUtil.openGUI(this.player, new GuiNpcAdvanced(this.npc));
        }
    }

    @Override
    public void save() {
        if (this.slot.selected != null && !this.slot.selected.isEmpty()) {
            String name = this.getTextField(0).b();
            if (!name.isEmpty()) {
                this.location.name = name;
            }
            this.location.npcX = this.npc.startPos[0];
            this.location.npcY = this.npc.startPos[1];
            this.location.npcZ = this.npc.startPos[2];
            this.location.posX = this.player.u;
            this.location.posY = this.player.v;
            this.location.posZ = this.player.w;
            this.location.dimension = this.player.ar;
            int cat = this.data.get(this.slot.selected);
            ey pkt = new EditTransportPackets.TransportSavePacket(cat, this.location.writeNBT()).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
    }

    public void e() {
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.data = data;
        this.slot.setList(list);
    }

    @Override
    public void setSelected(String selected) {
        this.slot.selected = selected;
    }

    @Override
    public void setGuiData(by compound) {
        TransportLocation loc = new TransportLocation();
        loc.readNBT(compound);
        this.location = loc;
        System.out.println(this.location.name);
        this.A_();
    }
}

