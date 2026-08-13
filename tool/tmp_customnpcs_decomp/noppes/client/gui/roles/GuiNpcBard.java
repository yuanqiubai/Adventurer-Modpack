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
import noppes.client.controllers.MusicController;
import noppes.client.gui.GuiNpcMusicSelection;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobBard;
import noppes.network.server.EditMainMenuPackets;
import noppes.util.NoppesUtil;

public class GuiNpcBard
extends GuiNPCInterface2 {
    private final JobBard job;
    private GuiNpcMusicSelection gui;

    public GuiNpcBard(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobBard)npc.jobInterface;
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(1, this.guiLeft + 55, this.guiTop + 15, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(0, this.job.song, this.guiLeft + 80, this.guiTop + 20, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 75, this.guiTop + 50, "Select Music"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 75, this.guiTop + 71, new String[]{"None", "Banjo", "Violin", "Guitar", "Harp"}, this.job.getInstrument().ordinal()));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 75, this.guiTop + 92, new String[]{"Play in background", "Play as jukebox"}, this.job.isStreamer ? 1 : 0));
        this.addLabel(new GuiNpcLabel(2, "On Distance:", this.guiLeft + 60, this.guiTop + 143, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 130, this.guiTop + 138, 40, 20, String.valueOf(this.job.minRange)));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(2, 64, 5);
        this.addLabel(new GuiNpcLabel(3, "Off Distance:", this.guiLeft + 60, this.guiTop + 166, 0x404040));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 130, this.guiTop + 161, 40, 20, String.valueOf(this.job.maxRange)));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(2, 64, 10);
        this.getLabel((int)3).enabled = this.job.isStreamer;
        this.getTextField((int)3).enabled = this.job.isStreamer;
    }

    @Override
    public void elementClicked() {
        this.job.song = this.gui.getSelected();
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            this.gui = new GuiNpcMusicSelection(this.npc, this, this.job.song);
            NoppesUtil.openGUI(this.player, (Object)this.gui);
            this.job.song = "";
            MusicController.Instance.stopMusic();
        }
        if (guibutton.g == 1) {
            this.job.song = "";
            this.getLabel((int)0).label = "";
            MusicController.Instance.stopMusic();
        }
        if (guibutton.g == 2) {
            this.job.setInstrument(button.getValue());
        }
        if (guibutton.g == 3) {
            this.getLabel((int)3).enabled = this.job.isStreamer = button.getValue() == 1;
            this.getTextField((int)3).enabled = this.job.isStreamer;
        }
    }

    @Override
    public void save() {
        this.job.minRange = this.getTextField(2).getInteger();
        this.job.maxRange = this.getTextField(3).getInteger();
        if (this.job.minRange > this.job.maxRange) {
            this.job.maxRange = this.job.minRange;
        }
        MusicController.Instance.stopMusic();
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}

