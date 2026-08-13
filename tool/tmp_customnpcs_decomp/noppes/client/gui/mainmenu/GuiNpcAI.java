/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.mainmenu;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.SubGuiNpcMovement;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.constants.EnumNavType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.data.DataAI;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcAI
extends GuiNPCInterface2
implements ITextfieldListener,
IGuiData {
    private final DataAI ai;
    private static final ey AI_GET = new EditMainMenuPackets.MainmenuAIGet().makePacket("CNPCs Server");

    public GuiNpcAI(EntityNPCInterface npc) {
        super(npc, 6);
        this.ai = npc.ai;
        PacketDispatcher.sendPacketToServer((ey)AI_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "ai.enemyresponse", this.guiLeft + 5, this.guiTop + 17, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 86, this.guiTop + 10, 60, 20, new String[]{"gui.retaliate", "gui.panic", "gui.retreat", "gui.nothing"}, this.npc.ai.onAttack));
        this.addLabel(new GuiNpcLabel(1, "ai.door", this.guiLeft + 5, this.guiTop + 40, 0x404040));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 86, this.guiTop + 35, 60, 20, new String[]{"gui.break", "gui.open", "gui.disabled"}, this.npc.ai.doorInteract));
        this.addLabel(new GuiNpcLabel(12, "ai.swim", this.guiLeft + 5, this.guiTop + 65, 0x404040));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 86, this.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.canSwim ? 1 : 0));
        this.addLabel(new GuiNpcLabel(13, "ai.shelter", this.guiLeft + 5, this.guiTop + 90, 0x404040));
        this.addButton(new GuiNpcButton(9, this.guiLeft + 86, this.guiTop + 85, 60, 20, new String[]{"gui.darkness", "gui.sunlight", "gui.disabled"}, this.npc.ai.findShelter));
        this.addLabel(new GuiNpcLabel(14, "ai.clearlos", this.guiLeft + 5, this.guiTop + 115, 0x404040));
        this.addButton(new GuiNpcButton(10, this.guiLeft + 86, this.guiTop + 110, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.directLOS ? 1 : 0));
        this.addLabel(new GuiNpcLabel(18, "ai.sprint", this.guiLeft + 5, this.guiTop + 140, 0x404040));
        this.addButton(new GuiNpcButton(16, this.guiLeft + 86, this.guiTop + 135, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.canSprint ? 1 : 0));
        this.addLabel(new GuiNpcLabel(10, "ai.avoidwater", this.guiLeft + 150, this.guiTop + 17, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 230, this.guiTop + 10, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.k().a() ? 1 : 0));
        this.addLabel(new GuiNpcLabel(11, "ai.return", this.guiLeft + 150, this.guiTop + 40, 0x404040));
        this.addButton(new GuiNpcButton(6, this.guiLeft + 230, this.guiTop + 35, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.returnToStart ? 1 : 0));
        this.addLabel(new GuiNpcLabel(17, "ai.leapattarget", this.guiLeft + 150, this.guiTop + 65, 0x404040));
        this.addButton(new GuiNpcButton(15, this.guiLeft + 230, this.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.canLeap ? 1 : 0));
        this.addLabel(new GuiNpcLabel(15, "ai.indirect", this.guiLeft + 150, this.guiTop + 90, 0x404040));
        this.addButton(new GuiNpcButton(13, this.guiLeft + 230, this.guiTop + 85, 60, 20, new String[]{"gui.no", "gui.yes"}, this.ai.canFireIndirect ? 1 : 0));
        this.addLabel(new GuiNpcLabel(16, "ai.rangemelee", this.guiLeft + 150, this.guiTop + 115, 0x404040));
        this.addButton(new GuiNpcButton(14, this.guiLeft + 230, this.guiTop + 110, 60, 20, new String[]{"gui.always", "gui.untilclose", "gui.whenavailable"}, this.ai.useRangeMelee));
        if (this.ai.useRangeMelee == 1) {
            this.addLabel(new GuiNpcLabel(20, "gui.engagedistance", this.guiLeft + 300, this.guiTop + 115, 0x404040));
            this.addTextField(new GuiNpcTextField(6, this, this.o, this.guiLeft + 380, this.guiTop + 110, 30, 20, String.valueOf(this.ai.distanceToMelee)));
            this.getTextField((int)6).numbersOnly = true;
            this.getTextField(6).setMinMaxDefault(1, this.npc.stats.aggroRange, 5);
        }
        this.addLabel(new GuiNpcLabel(19, "ai.tacticalvariant", this.guiLeft + 150, this.guiTop + 140, 0x404040));
        this.addButton(new GuiNpcButton(17, this.guiLeft + 230, this.guiTop + 135, 60, 20, EnumNavType.names(), this.ai.tacticalVariant.ordinal()));
        if (this.ai.tacticalVariant != EnumNavType.Default) {
            this.addLabel(new GuiNpcLabel(21, this.ai.tacticalVariant == EnumNavType.Surround ? "gui.orbitdistance" : "gui.engagedistance", this.guiLeft + 300, this.guiTop + 140, 0x404040));
            this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 380, this.guiTop + 135, 30, 20, String.valueOf(this.ai.tacticalRadius)));
            this.getTextField((int)3).numbersOnly = true;
            this.getTextField(3).setMinMaxDefault(1, this.npc.stats.aggroRange, 5);
        }
        this.getButton((int)17).h = this.ai.onAttack == 0;
        this.getButton((int)15).h = this.ai.onAttack == 0;
        this.getButton((int)13).h = this.npc.inventory.getProjectile() != null;
        this.getButton((int)14).h = this.npc.inventory.getProjectile() != null;
        this.getButton((int)10).h = this.npc.ai.tacticalVariant != EnumNavType.Stalk;
        this.addLabel(new GuiNpcLabel(2, "Movement", this.guiLeft + 4, this.guiTop + 165, 0x404040));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 86, this.guiTop + 160, 60, 20, "selectServer.edit"));
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 3) {
            this.ai.tacticalRadius = textfield.getInteger();
        }
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g == 0) {
            this.npc.ai.onAttack = button.getValue();
            this.A_();
        } else if (button.g == 1) {
            this.npc.ai.doorInteract = button.getValue();
        } else if (button.g == 2) {
            this.setSubGui(new SubGuiNpcMovement(this.ai));
        } else if (button.g == 5) {
            this.npc.setAvoidWater(button.getValue() == 1);
        } else if (button.g == 6) {
            this.npc.ai.returnToStart = button.getValue() == 1;
        } else if (button.g == 7) {
            this.npc.ai.canSwim = button.getValue() == 1;
        } else if (button.g == 9) {
            this.npc.ai.findShelter = button.getValue();
        } else if (button.g == 10) {
            this.npc.ai.directLOS = button.getValue() == 1;
        } else if (button.g == 11) {
            this.npc.ai.canSleep = button.getValue() == 1;
        } else if (button.g == 13) {
            this.npc.ai.canFireIndirect = button.getValue() == 1;
        } else if (button.g == 14) {
            this.npc.ai.useRangeMelee = button.getValue();
            this.A_();
        } else if (button.g == 15) {
            this.npc.ai.canLeap = button.getValue() == 1;
        } else if (button.g == 16) {
            this.npc.ai.canSprint = button.getValue() == 1;
        } else if (button.g == 17) {
            this.npc.ai.tacticalVariant = EnumNavType.values()[button.getValue()];
            this.ai.directLOS = EnumNavType.values()[button.getValue()] != EnumNavType.Stalk;
            this.A_();
        }
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAISave(this.ai.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void setGuiData(by compound) {
        this.ai.readToNBT(compound);
        this.A_();
    }
}

