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
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobSpawner;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcSpawner
extends GuiNPCInterface2
implements ITextfieldListener {
    private final JobSpawner job;
    private GuiNpcMusicSelection gui;
    private int slot = -1;

    public GuiNpcSpawner(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobSpawner)npc.jobInterface;
    }

    @Override
    public void A_() {
        super.A_();
        int y = this.guiTop + 6;
        this.addButton(new GuiNpcButton(20, this.guiLeft + 25, y, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(0, "1:", this.guiLeft + 4, y + 5, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 50, y, this.getTitle(this.job.compound1)));
        this.addButton(new GuiNpcButton(21, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(1, "2:", this.guiLeft + 4, y + 5, 0x404040));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 50, y, this.getTitle(this.job.compound2)));
        this.addButton(new GuiNpcButton(22, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(2, "3:", this.guiLeft + 4, y + 5, 0x404040));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 50, y, this.getTitle(this.job.compound3)));
        this.addButton(new GuiNpcButton(23, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(3, "4:", this.guiLeft + 4, y + 5, 0x404040));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 50, y, this.getTitle(this.job.compound4)));
        this.addButton(new GuiNpcButton(24, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(4, "5:", this.guiLeft + 4, y + 5, 0x404040));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 50, y, this.getTitle(this.job.compound5)));
        this.addButton(new GuiNpcButton(25, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(5, "6:", this.guiLeft + 4, y + 5, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 50, y, this.getTitle(this.job.compound6)));
        this.addLabel(new GuiNpcLabel(6, "Dies after spawning", this.guiLeft + 4, (y += 23) + 5, 0x404040));
        this.addButton(new GuiNpcButton(26, this.guiLeft + 75, y, 40, 20, new String[]{"gui.yes", "gui.no"}, this.job.doesntDie ? 1 : 0));
        this.addLabel(new GuiNpcLabel(7, "Position Offset X:", this.guiLeft + 4, (y += 23) + 5, 0x404040));
        this.addTextField(new GuiNpcTextField(7, this, this.o, this.guiLeft + 99, y, 24, 20, String.valueOf(this.job.xOffset)));
        this.getTextField((int)7).numbersOnly = true;
        this.getTextField(7).setMinMaxDefault(-9, 9, 0);
        this.addLabel(new GuiNpcLabel(8, "Y:", this.guiLeft + 125, y + 5, 0x404040));
        this.addTextField(new GuiNpcTextField(8, this, this.o, this.guiLeft + 135, y, 24, 20, String.valueOf(this.job.yOffset)));
        this.getTextField((int)8).numbersOnly = true;
        this.getTextField(8).setMinMaxDefault(-9, 9, 0);
        this.addLabel(new GuiNpcLabel(9, "Z:", this.guiLeft + 161, y + 5, 0x404040));
        this.addTextField(new GuiNpcTextField(9, this, this.o, this.guiLeft + 171, y, 24, 20, String.valueOf(this.job.zOffset)));
        this.getTextField((int)9).numbersOnly = true;
        this.getTextField(9).setMinMaxDefault(-9, 9, 0);
        this.addLabel(new GuiNpcLabel(10, "SpawnType", this.guiLeft + 4, (y += 23) + 5, 0x404040));
        this.addButton(new GuiNpcButton(10, this.guiLeft + 80, y, 100, 20, new String[]{"One by One", "All", "Random"}, this.job.spawnType));
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
        if (button.g >= 0 && button.g < 6) {
            this.slot = button.g + 1;
            this.setSubGui(new GuiNpcMobSpawnerSelector());
        }
        if (button.g >= 20 && button.g < 26) {
            this.job.setJobCompound(button.g - 19, null);
            this.A_();
        }
        if (button.g == 26) {
            boolean bl = this.job.doesntDie = button.getValue() == 1;
        }
        if (button.g == 10) {
            this.job.spawnType = button.getValue();
        }
    }

    @Override
    public void closeSubGui(SubGuiInterface gui) {
        super.closeSubGui(gui);
        by compound = ((GuiNpcMobSpawnerSelector)gui).getCompound();
        if (compound != null) {
            this.job.setJobCompound(this.slot, compound);
        }
        this.A_();
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 7) {
            this.job.xOffset = textfield.getInteger();
        }
        if (textfield.id == 8) {
            this.job.yOffset = textfield.getInteger();
        }
        if (textfield.id == 9) {
            this.job.zOffset = textfield.getInteger();
        }
    }
}

