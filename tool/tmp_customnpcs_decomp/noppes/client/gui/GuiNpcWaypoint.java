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
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.blocks.TileWaypoint;
import noppes.network.server.SaveTileEntityPacket;

public class GuiNpcWaypoint
extends GuiNPCInterface {
    private final TileWaypoint tile;

    public GuiNpcWaypoint(int x, int y, int z) {
        this.tile = (TileWaypoint)this.player.q.r(x, y, z);
        this.xSize = 265;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "gui.name", this.guiLeft + 1, this.guiTop + 76, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 60, this.guiTop + 71, 200, 20, this.tile.name));
        this.addLabel(new GuiNpcLabel(1, "gui.range", this.guiLeft + 1, this.guiTop + 97, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 60, this.guiTop + 92, 200, 20, String.valueOf(this.tile.range)));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(2, 60, 10);
        this.addButton(new GuiNpcButton(0, this.guiLeft + 40, this.guiTop + 190, 120, 20, "Done"));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 0) {
            this.close();
        }
    }

    @Override
    public void save() {
        if (this.tile != null) {
            this.tile.name = this.getTextField(0).b();
            this.tile.range = this.getTextField(1).getInteger();
            by compound = new by();
            this.tile.b(compound);
            ey pkt = new SaveTileEntityPacket(compound).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
    }
}

