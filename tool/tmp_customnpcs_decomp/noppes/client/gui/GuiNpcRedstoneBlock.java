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
import noppes.client.gui.SubGuiNpcAvailability;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.blocks.TileRedstoneBlock;
import noppes.network.server.SaveTileEntityPacket;
import noppes.util.NoppesUtil;

public class GuiNpcRedstoneBlock
extends GuiNPCInterface {
    private final TileRedstoneBlock tile;

    public GuiNpcRedstoneBlock(int x, int y, int z) {
        this.tile = (TileRedstoneBlock)this.player.q.r(x, y, z);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(4, this.guiLeft + 40, this.guiTop + 40, 120, 20, "Availability Options"));
        this.addLabel(new GuiNpcLabel(0, "On Range X:", this.guiLeft + 1, this.guiTop + 76, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 60, this.guiTop + 71, 20, 20, String.valueOf(this.tile.onRangeX)));
        this.getTextField((int)0).numbersOnly = true;
        this.getTextField(0).setMinMaxDefault(0, 50, 6);
        this.addLabel(new GuiNpcLabel(1, "Y:", this.guiLeft + 83, this.guiTop + 76, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 92, this.guiTop + 71, 20, 20, String.valueOf(this.tile.onRangeY)));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(0, 50, 6);
        this.addLabel(new GuiNpcLabel(2, "Z:", this.guiLeft + 115, this.guiTop + 76, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 124, this.guiTop + 71, 20, 20, String.valueOf(this.tile.onRangeZ)));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(0, 50, 6);
        this.addLabel(new GuiNpcLabel(3, "Off Range X:", this.guiLeft - 3, this.guiTop + 99, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 60, this.guiTop + 94, 20, 20, String.valueOf(this.tile.offRangeX)));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(0, 50, 10);
        this.addLabel(new GuiNpcLabel(4, "Y:", this.guiLeft + 83, this.guiTop + 99, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 92, this.guiTop + 94, 20, 20, String.valueOf(this.tile.offRangeY)));
        this.getTextField((int)4).numbersOnly = true;
        this.getTextField(4).setMinMaxDefault(0, 50, 10);
        this.addLabel(new GuiNpcLabel(5, "Z:", this.guiLeft + 115, this.guiTop + 99, 0xFFFFFF));
        this.addTextField(new GuiNpcTextField(5, this, this.o, this.guiLeft + 124, this.guiTop + 94, 20, 20, String.valueOf(this.tile.offRangeZ)));
        this.getTextField((int)5).numbersOnly = true;
        this.getTextField(5).setMinMaxDefault(0, 50, 10);
        this.addButton(new GuiNpcButton(0, this.guiLeft + 40, this.guiTop + 190, 120, 20, "Done"));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 0) {
            this.close();
        }
        if (guibutton.g == 4) {
            this.save();
            SubGuiNpcAvailability gui = new SubGuiNpcAvailability(this.tile.availability);
            gui.parent2 = this;
            gui.parent = gui;
            NoppesUtil.openGUI(this.player, gui);
        }
    }

    @Override
    public void save() {
        if (this.tile != null) {
            this.tile.onRangeX = this.getTextField(0).getInteger();
            this.tile.onRangeY = this.getTextField(1).getInteger();
            this.tile.onRangeZ = this.getTextField(2).getInteger();
            this.tile.offRangeX = this.getTextField(3).getInteger();
            this.tile.offRangeY = this.getTextField(4).getInteger();
            this.tile.offRangeZ = this.getTextField(5).getInteger();
            if (this.tile.onRangeX > this.tile.offRangeX) {
                this.tile.offRangeX = this.tile.onRangeX;
            }
            if (this.tile.onRangeY > this.tile.offRangeY) {
                this.tile.offRangeY = this.tile.onRangeY;
            }
            if (this.tile.onRangeZ > this.tile.offRangeZ) {
                this.tile.offRangeZ = this.tile.onRangeZ;
            }
            this.tile.isActivated = false;
            by compound = new by();
            this.tile.b(compound);
            ey pkt = new SaveTileEntityPacket(compound).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
    }
}

