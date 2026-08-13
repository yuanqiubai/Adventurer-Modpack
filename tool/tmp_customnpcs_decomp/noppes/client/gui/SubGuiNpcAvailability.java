/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.GuiNPCDialogSelection;
import noppes.client.gui.GuiNPCFactionSelection;
import noppes.client.gui.global.GuiNPCQuestSelection;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.constants.EnumAvailabilityDialog;
import noppes.common.constants.EnumAvailabilityFactionType;
import noppes.common.constants.EnumAvailabilityQuest;
import noppes.common.constants.EnumDayTime;
import noppes.common.controllers.Availability;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.Faction;
import noppes.common.controllers.Quest;
import noppes.network.server.EditDialogPackets;
import noppes.network.server.EditFactionPackets;
import noppes.network.server.EditQuestPackets;
import noppes.util.NoppesUtil;

public class SubGuiNpcAvailability
extends SubGuiInterface
implements ITextfieldListener,
GuiSelectionListener,
IGuiData {
    public awe parent2;
    private final Availability availabitily;
    private boolean selectDialog = false;
    private boolean selectFaction = false;
    private int slot = 0;

    public SubGuiNpcAvailability(Availability availabitily) {
        this.availabitily = availabitily;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(1, "availability.available", this.guiLeft, this.guiTop + 4, 0x404040));
        this.getLabel(1).center(this.xSize);
        this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 12, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialogAvailable.ordinal()));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 56, this.guiTop + 12, 172, 20, "availability.selectdialog"));
        this.getButton((int)1).h = this.availabitily.dialogAvailable != EnumAvailabilityDialog.Always;
        this.addButton(new GuiNpcButton(4, this.guiLeft + 230, this.guiTop + 12, 20, 20, "X"));
        this.addButton(new GuiNpcButton(10, this.guiLeft + 4, this.guiTop + 34, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialog2Available.ordinal()));
        this.addButton(new GuiNpcButton(11, this.guiLeft + 56, this.guiTop + 34, 172, 20, "availability.selectdialog"));
        this.getButton((int)11).h = this.availabitily.dialog2Available != EnumAvailabilityDialog.Always;
        this.addButton(new GuiNpcButton(14, this.guiLeft + 230, this.guiTop + 34, 20, 20, "X"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 4, this.guiTop + 58, 50, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.questAvailable.ordinal()));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 56, this.guiTop + 58, 172, 20, "availability.selectquest"));
        this.getButton((int)3).h = this.availabitily.questAvailable != EnumAvailabilityQuest.Always;
        this.addButton(new GuiNpcButton(5, this.guiLeft + 230, this.guiTop + 58, 20, 20, "X"));
        this.addButton(new GuiNpcButton(12, this.guiLeft + 4, this.guiTop + 80, 50, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.quest2Available.ordinal()));
        this.addButton(new GuiNpcButton(13, this.guiLeft + 56, this.guiTop + 80, 172, 20, "availability.selectquest"));
        this.getButton((int)13).h = this.availabitily.quest2Available != EnumAvailabilityQuest.Always;
        this.addButton(new GuiNpcButton(15, this.guiLeft + 230, this.guiTop + 80, 20, 20, "X"));
        this.addButton(new GuiNpcButton(20, this.guiLeft + 4, this.guiTop + 104, 50, 20, new String[]{"availability.always", "availability.is", "availability.isnot"}, this.availabitily.factionAvailable.ordinal()));
        this.addButton(new GuiNpcButton(22, this.guiLeft + 56, this.guiTop + 104, 60, 20, new String[]{"faction.friendly", "faction.neutral", "faction.unfriendly"}, this.availabitily.factionStance.ordinal()));
        this.addButton(new GuiNpcButton(21, this.guiLeft + 118, this.guiTop + 104, 110, 20, "availability.selectfaction"));
        this.getButton((int)21).h = this.availabitily.factionAvailable != EnumAvailabilityFactionType.Always;
        this.getButton((int)22).h = this.availabitily.factionAvailable != EnumAvailabilityFactionType.Always;
        this.addButton(new GuiNpcButton(23, this.guiLeft + 230, this.guiTop + 104, 20, 20, "X"));
        this.addButton(new GuiNpcButton(24, this.guiLeft + 4, this.guiTop + 126, 50, 20, new String[]{"availability.always", "availability.is", "availability.isnot"}, this.availabitily.faction2Available.ordinal()));
        this.addButton(new GuiNpcButton(27, this.guiLeft + 56, this.guiTop + 126, 60, 20, new String[]{"faction.friendly", "faction.neutral", "faction.unfriendly"}, this.availabitily.faction2Stance.ordinal()));
        this.addButton(new GuiNpcButton(25, this.guiLeft + 118, this.guiTop + 126, 110, 20, "availability.selectfaction"));
        this.getButton((int)25).h = this.availabitily.faction2Available != EnumAvailabilityFactionType.Always;
        this.getButton((int)27).h = this.availabitily.faction2Available != EnumAvailabilityFactionType.Always;
        this.addButton(new GuiNpcButton(26, this.guiLeft + 230, this.guiTop + 126, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(50, "availability.daytime", this.guiLeft + 4, this.guiTop + 153, 0x404040));
        this.addButton(new GuiNpcButton(50, this.guiLeft + 50, this.guiTop + 148, 150, 20, new String[]{"availability.wholeday", "availability.night", "availability.day"}, this.availabitily.daytime.ordinal()));
        this.addLabel(new GuiNpcLabel(51, "availability.minlevel", this.guiLeft + 4, this.guiTop + 175, 0x404040));
        this.addTextField(new GuiNpcTextField(51, this, this.o, this.guiLeft + 50, this.guiTop + 170, 90, 20, String.valueOf(this.availabitily.minPlayerLevel)));
        this.getTextField((int)51).numbersOnly = true;
        this.getTextField(51).setMinMaxDefault(0, 400, 0);
        this.addButton(new GuiNpcButton(66, this.guiLeft + 82, this.guiTop + 192, 98, 20, "gui.done"));
        this.updateGuiButtons();
    }

    private void updateGuiButtons() {
        ey pkt;
        this.getButton(1).setDisplayText("availability.selectdialog");
        this.getButton(11).setDisplayText("availability.selectdialog");
        this.getButton(3).setDisplayText("availability.selectquest");
        this.getButton(13).setDisplayText("availability.selectquest");
        if (this.availabitily.dialogId >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogGetPacket(this.availabitily.dialogId).makePacket("CNPCs Server"));
        }
        if (this.availabitily.dialog2Id >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogGetPacket(this.availabitily.dialog2Id).makePacket("CNPCs Server"));
        }
        if (this.availabitily.questId >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditQuestPackets.QuestGetPacket(this.availabitily.questId).makePacket("CNPCs Server"));
        }
        if (this.availabitily.quest2Id >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditQuestPackets.QuestGetPacket(this.availabitily.quest2Id).makePacket("CNPCs Server"));
        }
        if (this.availabitily.factionId >= 0) {
            pkt = new EditFactionPackets.FactionGetPacket(this.availabitily.factionId).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
        if (this.availabitily.faction2Id >= 0) {
            pkt = new EditFactionPackets.FactionGetPacket(this.availabitily.faction2Id).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
    }

    @Override
    protected void a(aut guibutton) {
        GuiNPCFactionSelection gui2;
        GuiNPCQuestSelection gui1;
        GuiNPCDialogSelection gui;
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            this.availabitily.setDialogAvailability(button.getValue());
            if (this.availabitily.dialogAvailable == EnumAvailabilityDialog.Always) {
                this.availabitily.dialogId = -1;
            }
            this.A_();
        }
        if (guibutton.g == 1) {
            this.slot = 1;
            this.selectDialog = true;
            gui = new GuiNPCDialogSelection(this.npc, this.parent, this.availabitily.dialogId);
            gui.listener = this;
            NoppesUtil.openGUI(this.player, gui);
        }
        if (guibutton.g == 2) {
            this.availabitily.setQuestAvailability(button.getValue());
            if (this.availabitily.questAvailable == EnumAvailabilityQuest.Always) {
                this.availabitily.questId = -1;
            }
            this.A_();
        }
        if (guibutton.g == 3) {
            this.slot = 1;
            gui1 = new GuiNPCQuestSelection(this.npc, this.parent, this.availabitily.questId);
            gui1.listener = this;
            NoppesUtil.openGUI(this.player, gui1);
        }
        if (guibutton.g == 4) {
            this.availabitily.dialogId = -1;
            this.getButton(1).setDisplayText("availability.selectdialog");
        }
        if (guibutton.g == 5) {
            this.availabitily.questId = -1;
            this.getButton(3).setDisplayText("availability.selectquest");
        }
        if (guibutton.g == 10) {
            this.availabitily.setDialog2Availability(button.getValue());
            if (this.availabitily.dialog2Available == EnumAvailabilityDialog.Always) {
                this.availabitily.dialog2Id = -1;
            }
            this.A_();
        }
        if (guibutton.g == 11) {
            this.slot = 2;
            this.selectDialog = true;
            gui = new GuiNPCDialogSelection(this.npc, this.parent, this.availabitily.dialog2Id);
            gui.listener = this;
            NoppesUtil.openGUI(this.player, gui);
        }
        if (guibutton.g == 12) {
            this.availabitily.setQuest2Availability(button.getValue());
            if (this.availabitily.quest2Available == EnumAvailabilityQuest.Always) {
                this.availabitily.quest2Id = -1;
            }
            this.A_();
        }
        if (guibutton.g == 13) {
            this.slot = 2;
            gui1 = new GuiNPCQuestSelection(this.npc, this.parent, this.availabitily.quest2Id);
            gui1.listener = this;
            NoppesUtil.openGUI(this.player, gui1);
        }
        if (guibutton.g == 14) {
            this.availabitily.dialog2Id = -1;
            this.getButton(11).setDisplayText("availability.selectdialog");
        }
        if (guibutton.g == 15) {
            this.availabitily.quest2Id = -1;
            this.getButton(13).setDisplayText("availability.selectquest");
        }
        if (guibutton.g == 20) {
            this.availabitily.setFactionAvailability(button.getValue());
            if (this.availabitily.factionAvailable == EnumAvailabilityFactionType.Always) {
                this.availabitily.factionId = -1;
            }
            this.A_();
        }
        if (guibutton.g == 24) {
            this.availabitily.setFaction2Availability(button.getValue());
            if (this.availabitily.faction2Available == EnumAvailabilityFactionType.Always) {
                this.availabitily.faction2Id = -1;
            }
            this.A_();
        }
        if (guibutton.g == 21) {
            this.selectFaction = true;
            this.slot = 1;
            gui2 = new GuiNPCFactionSelection(this.npc, this.parent, this.availabitily.factionId);
            gui2.listener = this;
            NoppesUtil.openGUI(this.player, gui2);
        }
        if (guibutton.g == 25) {
            this.selectFaction = true;
            this.slot = 2;
            gui2 = new GuiNPCFactionSelection(this.npc, this.parent, this.availabitily.faction2Id);
            gui2.listener = this;
            NoppesUtil.openGUI(this.player, gui2);
        }
        if (guibutton.g == 22) {
            this.availabitily.setFactionAvailabilityStance(button.getValue());
        }
        if (guibutton.g == 27) {
            this.availabitily.setFaction2AvailabilityStance(button.getValue());
        }
        if (guibutton.g == 23) {
            this.availabitily.factionId = -1;
            this.getButton(21).setDisplayText("availability.selectfaction");
        }
        if (guibutton.g == 26) {
            this.availabitily.faction2Id = -1;
            this.getButton(25).setDisplayText("availability.selectfaction");
        }
        if (guibutton.g == 50) {
            this.availabitily.daytime = EnumDayTime.values()[button.getValue()];
        }
        if (guibutton.g == 66) {
            this.close();
            if (this.parent2 != null) {
                NoppesUtil.openGUI(this.player, this.parent2);
            }
        }
    }

    @Override
    public void selected(int id) {
        if (this.selectDialog) {
            if (this.slot == 1) {
                this.availabitily.dialogId = id;
            }
            if (this.slot == 2) {
                this.availabitily.dialog2Id = id;
            }
        } else if (this.selectFaction) {
            if (this.slot == 1) {
                this.availabitily.factionId = id;
            }
            if (this.slot == 2) {
                this.availabitily.faction2Id = id;
            }
        } else {
            if (this.slot == 1) {
                this.availabitily.questId = id;
            }
            if (this.slot == 2) {
                this.availabitily.quest2Id = id;
            }
        }
        this.selectDialog = false;
        this.selectFaction = false;
    }

    @Override
    public void setGuiData(by compound) {
        if (compound.b("DialogId")) {
            Dialog quest = new Dialog();
            quest.readNBT(compound);
            if (this.availabitily.dialogId == quest.id) {
                this.getButton((int)1).f = quest.title;
            }
            if (this.availabitily.dialog2Id == quest.id) {
                this.getButton((int)11).f = quest.title;
            }
        } else if (compound.b("Slot")) {
            Faction quest1 = new Faction();
            quest1.readNBT(compound);
            if (this.availabitily.factionId == quest1.id) {
                this.getButton((int)21).f = quest1.name;
            }
            if (this.availabitily.faction2Id == quest1.id) {
                this.getButton((int)25).f = quest1.name;
            }
        } else {
            Quest quest2 = new Quest();
            quest2.readNBT(compound);
            if (this.availabitily.questId == quest2.id) {
                this.getButton((int)3).f = quest2.title;
            }
            if (this.availabitily.quest2Id == quest2.id) {
                this.getButton((int)13).f = quest2.title;
            }
        }
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 51) {
            this.availabitily.minPlayerLevel = textfield.getInteger();
        }
    }
}

