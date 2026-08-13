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
import noppes.client.gui.GuiNpcSoundSelection;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditMainMenuPackets;
import noppes.util.NoppesUtil;

public class GuiNPCSoundsMenu
extends GuiNPCInterface2
implements ITextfieldListener {
    private GuiNpcSoundSelection gui;
    private GuiNpcTextField selectedField;

    public GuiNPCSoundsMenu(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "advanced.idlesound", this.guiLeft + 5, this.guiTop + 20, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 80, this.guiTop + 15, 200, 20, this.npc.advanced.idleSound));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 290, this.guiTop + 15, 80, 20, "gui.selectSound"));
        this.addLabel(new GuiNpcLabel(2, "advanced.angersound", this.guiLeft + 5, this.guiTop + 45, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 80, this.guiTop + 40, 200, 20, this.npc.advanced.angrySound));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 290, this.guiTop + 40, 80, 20, "gui.selectSound"));
        this.addLabel(new GuiNpcLabel(3, "advanced.hurtsound", this.guiLeft + 5, this.guiTop + 70, 0x404040));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 80, this.guiTop + 65, 200, 20, this.npc.advanced.hurtSound));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 290, this.guiTop + 65, 80, 20, "gui.selectSound"));
        this.addLabel(new GuiNpcLabel(4, "advanced.deathsound", this.guiLeft + 5, this.guiTop + 95, 0x404040));
        this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 80, this.guiTop + 90, 200, 20, this.npc.advanced.deathSound));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 290, this.guiTop + 90, 80, 20, "gui.selectSound"));
        this.addLabel(new GuiNpcLabel(5, "advanced.stepsound", this.guiLeft + 5, this.guiTop + 120, 0x404040));
        this.addTextField(new GuiNpcTextField(5, this, this.o, this.guiLeft + 80, this.guiTop + 115, 200, 20, this.npc.advanced.stepSound));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 290, this.guiTop + 115, 80, 20, "gui.selectSound"));
    }

    @Override
    public void buttonEvent(aut button) {
        this.selectedField = this.getTextField(button.g);
        this.gui = new GuiNpcSoundSelection(this.npc, this, this.selectedField.b());
        NoppesUtil.openGUI(this.player, (Object)this.gui);
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 0) {
            this.npc.advanced.idleSound = textfield.b();
        }
        if (textfield.id == 2) {
            this.npc.advanced.angrySound = textfield.b();
        }
        if (textfield.id == 3) {
            this.npc.advanced.hurtSound = textfield.b();
        }
        if (textfield.id == 4) {
            this.npc.advanced.deathSound = textfield.b();
        }
        if (textfield.id == 5) {
            this.npc.advanced.stepSound = textfield.b();
        }
    }

    @Override
    public void elementClicked() {
        this.selectedField.a(this.gui.getSelected());
        this.unFocused(this.selectedField);
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}

