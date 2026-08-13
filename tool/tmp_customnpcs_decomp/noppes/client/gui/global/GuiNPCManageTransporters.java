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
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.GuiNPCTransportCategoryEdit;
import noppes.client.gui.mainmenu.GuiNPCGlobalMainMenu;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCStringSlot;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.IScrollData;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditTransportPackets;
import noppes.util.NoppesUtil;

public class GuiNPCManageTransporters
extends GuiNPCInterface
implements IScrollData {
    private GuiNPCStringSlot slot;
    private HashMap<String, Integer> data;
    private boolean selectCategory = true;
    private static final ey TRANSPORT_CATEGORIES_GET = new EditTransportPackets.TransportCategoriesGetPacket().makePacket("CNPCs Server");

    public GuiNPCManageTransporters(EntityNPCInterface npc) {
        super(npc);
        PacketDispatcher.sendPacketToServer((ey)TRANSPORT_CATEGORIES_GET);
        this.drawDefaultBackground = false;
        this.title = "Transport Categories";
        this.data = new HashMap();
    }

    @Override
    public void A_() {
        super.A_();
        Vector<String> list = new Vector<String>();
        this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
        this.slot.d(4, 5);
        this.addButton(0, new GuiNpcButton(0, this.g / 2 - 100, this.h - 52, 65, 20, "gui.add"));
        this.addButton(1, new GuiNpcButton(1, this.g / 2 - 33, this.h - 52, 65, 20, "selectServer.edit"));
        this.getButton((int)0).h = this.selectCategory;
        this.getButton((int)1).h = this.selectCategory;
        this.addButton(3, new GuiNpcButton(3, this.g / 2 + 33, this.h - 52, 65, 20, "gui.remove"));
        this.addButton(2, new GuiNpcButton(2, this.g / 2 - 100, this.h - 31, 98, 20, "gui.open"));
        this.getButton((int)2).h = this.selectCategory;
        this.addButton(4, new GuiNpcButton(4, this.g / 2 + 2, this.h - 31, 98, 20, "gui.back"));
    }

    @Override
    public void a(int i, int j, float f) {
        this.slot.a(i, j, f);
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 0 && this.selectCategory) {
            NoppesUtil.openGUI(this.player, (Object)new GuiNPCTransportCategoryEdit(this.npc, this, "", -1));
        }
        if (guibutton.g == 1) {
            if (this.slot.selected == null || this.slot.selected.isEmpty()) {
                return;
            }
            if (this.selectCategory) {
                NoppesUtil.openGUI(this.player, (Object)new GuiNPCTransportCategoryEdit(this.npc, this, this.slot.selected, this.data.get(this.slot.selected)));
            }
        }
        if (guibutton.g == 4) {
            if (this.selectCategory) {
                this.close();
                NoppesUtil.openGUI(this.player, (Object)new GuiNPCGlobalMainMenu(this.npc));
            } else {
                this.title = "Transport Categories";
                this.selectCategory = true;
                PacketDispatcher.sendPacketToServer((ey)TRANSPORT_CATEGORIES_GET);
                this.A_();
            }
        }
        if (guibutton.g == 3) {
            if (this.slot.selected == null || this.slot.selected.isEmpty()) {
                return;
            }
            this.save();
            if (this.selectCategory) {
                ey pkt = new EditTransportPackets.TransportCategoryRemovePacket(this.data.get(this.slot.selected)).makePacket("CNPCs Server");
                PacketDispatcher.sendPacketToServer((ey)pkt);
            } else {
                ey pkt = new EditTransportPackets.TransportRemovePacket(this.data.get(this.slot.selected)).makePacket("CNPCs Server");
                PacketDispatcher.sendPacketToServer((ey)pkt);
            }
            this.A_();
        }
        if (guibutton.g == 2) {
            this.doubleClicked();
        }
    }

    @Override
    public void doubleClicked() {
        if (this.slot.selected != null && !this.slot.selected.isEmpty() && this.selectCategory) {
            this.selectCategory = false;
            this.title = "TransportLocations";
            ey pkt = new EditTransportPackets.TransportsGetPacket(this.data.get(this.slot.selected)).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
            this.A_();
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.data = data;
        this.slot.setList(list);
    }

    @Override
    public void setSelected(String selected) {
    }
}

