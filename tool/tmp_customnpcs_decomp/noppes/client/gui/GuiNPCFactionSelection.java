/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCStringSlot;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.IScrollData;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditFactionPackets;
import noppes.util.NoppesUtil;

public class GuiNPCFactionSelection
extends GuiNPCInterface
implements IScrollData {
    public GuiSelectionListener listener;
    private GuiNPCStringSlot slot;
    private final awe parent;
    private HashMap<String, Integer> data = new HashMap();
    private int dialog;
    private static final ey FACTIONS_GET = new EditFactionPackets.FactionsGetPacket().makePacket("CNPCs Server");

    public GuiNPCFactionSelection(EntityNPCInterface npc, awe parent, int dialog) {
        super(npc);
        this.drawDefaultBackground = false;
        this.title = "Select Dialog Category";
        this.parent = parent;
        this.dialog = dialog;
        PacketDispatcher.sendPacketToServer((ey)FACTIONS_GET);
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
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
        }
        if (guibutton.g == 4) {
            this.doubleClicked();
        }
    }

    @Override
    public void doubleClicked() {
        if (this.slot.selected != null && !this.slot.selected.isEmpty()) {
            this.dialog = this.data.get(this.slot.selected);
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    public void save() {
        if (this.dialog >= 0 && this.listener != null) {
            this.listener.selected(this.dialog);
        }
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.data = data;
        this.slot.setList(list);
        if (this.dialog >= 0) {
            for (String name : data.keySet()) {
                if (data.get(name) != this.dialog) continue;
                this.slot.selected = name;
            }
        }
    }

    @Override
    public void setSelected(String selected) {
    }
}

