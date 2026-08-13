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
import noppes.client.gui.GuiNpcMobSpawnerSelector;
import noppes.client.gui.GuiNpcMusicSelection;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobBoss;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcBoss
extends GuiNPCInterface2 {
    private final JobBoss job;
    private GuiNpcMusicSelection gui;
    private int slot = -1;

    public GuiNpcBoss(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobBoss)npc.jobInterface;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(40, "Show name", this.guiLeft + 300, this.guiTop + 25, 0x404040));
        this.addButton(new GuiNpcButton(40, this.guiLeft + 355, this.guiTop + 20, 40, 20, new String[]{"gui.no", "gui.yes"}, this.job.hideName ? 0 : 1));
        int y = 6;
        this.addButton(new GuiNpcButton(20, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(0, "90%", this.guiLeft + 4, this.guiTop + y + 5, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound9)));
        int y1 = y + 23;
        this.addButton(new GuiNpcButton(21, this.guiLeft + 55, this.guiTop + y1, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(1, "80%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound8)));
        this.addButton(new GuiNpcButton(22, this.guiLeft + 55, this.guiTop + (y1 += 23), 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(2, "70%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound7)));
        this.addButton(new GuiNpcButton(23, this.guiLeft + 55, this.guiTop + (y1 += 23), 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(3, "60%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound6)));
        this.addButton(new GuiNpcButton(24, this.guiLeft + 55, this.guiTop + (y1 += 23), 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(4, "50%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound5)));
        this.addButton(new GuiNpcButton(25, this.guiLeft + 55, this.guiTop + (y1 += 23), 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(5, "40%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound4)));
        this.addButton(new GuiNpcButton(26, this.guiLeft + 55, this.guiTop + (y1 += 23), 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(6, "30%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(6, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound3)));
        this.addButton(new GuiNpcButton(27, this.guiLeft + 55, this.guiTop + (y1 += 23), 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(7, "20%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound2)));
        this.addButton(new GuiNpcButton(28, this.guiLeft + 55, this.guiTop + (y1 += 23), 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(8, "10%", this.guiLeft + 4, this.guiTop + y1 + 5, 0x404040));
        this.addButton(new GuiNpcButton(8, this.guiLeft + 80, this.guiTop + y1, this.getTitle(this.job.compound1)));
    }

    private String getTitle(by compound) {
        return compound != null && compound.b("ClonedName") ? compound.i("ClonedName") : "gui.selectnpc";
    }

    @Override
    public void elementClicked() {
        this.gui.getSelected();
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g >= 0 && button.g < 9) {
            this.slot = 9 - button.g;
            this.setSubGui(new GuiNpcMobSpawnerSelector());
        }
        if (button.g >= 20 && button.g < 29) {
            this.job.setNBT(29 - button.g, null);
            this.A_();
        }
        if (button.g == 40) {
            this.job.hideName = button.getValue() == 0;
        }
    }

    @Override
    public void closeSubGui(SubGuiInterface gui) {
        super.closeSubGui(gui);
        by compound = ((GuiNpcMobSpawnerSelector)gui).getCompound();
        if (compound != null) {
            this.job.setNBT(this.slot, compound);
        }
        this.A_();
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}

