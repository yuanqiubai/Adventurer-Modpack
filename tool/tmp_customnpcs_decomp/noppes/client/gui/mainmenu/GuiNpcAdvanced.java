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
import noppes.client.gui.GuiNPCDialogNpcOptions;
import noppes.client.gui.GuiNPCFactionSetup;
import noppes.client.gui.GuiNPCLinesMenu;
import noppes.client.gui.GuiNPCSoundsMenu;
import noppes.client.gui.roles.GuiNpcBard;
import noppes.client.gui.roles.GuiNpcBoss;
import noppes.client.gui.roles.GuiNpcConversation;
import noppes.client.gui.roles.GuiNpcGuard;
import noppes.client.gui.roles.GuiNpcHealer;
import noppes.client.gui.roles.GuiNpcSpawner;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.IGuiData;
import noppes.common.constants.EnumGuiType;
import noppes.common.constants.EnumJobType;
import noppes.common.constants.EnumRoleType;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditMainMenuPackets;
import noppes.util.NoppesUtil;

public class GuiNpcAdvanced
extends GuiNPCInterface2
implements IGuiData {
    private boolean hasChanges = false;
    private static final ey ADVANCE_GET = new EditMainMenuPackets.MainmenuAdvancedGet().makePacket("CNPCs Server");

    public GuiNpcAdvanced(EntityNPCInterface npc) {
        super(npc, 4);
        PacketDispatcher.sendPacketToServer((ey)ADVANCE_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(3, this.guiLeft + 85 + 160, this.guiTop + 20, 52, 20, "selectServer.edit"));
        this.addButton(new GuiNpcButton(8, this.guiLeft + 85, this.guiTop + 20, 155, 20, new String[]{"role.none", "role.trader", "role.follower", "role.bank", "role.transporter", "role.mailman"}, this.npc.advanced.role.ordinal()));
        this.getButton((int)3).h = this.npc.advanced.role != EnumRoleType.None && this.npc.advanced.role != EnumRoleType.Postman;
        this.addButton(new GuiNpcButton(4, this.guiLeft + 85 + 160, this.guiTop + 43, 52, 20, "selectServer.edit"));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 85, this.guiTop + 43, 155, 20, new String[]{"job.none", "job.bard", "job.healer", "job.guard", "job.itemgiver", "Boss(WIP)", "job.spawner", "job.conversation"}, this.npc.advanced.job.ordinal()));
        this.getButton((int)4).h = this.npc.advanced.job != EnumJobType.None;
        this.addButton(new GuiNpcButton(7, this.guiLeft + 85, this.guiTop + 66, 214, 20, "advanced.lines"));
        this.addButton(new GuiNpcButton(9, this.guiLeft + 85, this.guiTop + 89, 214, 20, "menu.factions"));
        this.addButton(new GuiNpcButton(10, this.guiLeft + 85, this.guiTop + 112, 214, 20, "dialog.dialogs"));
        this.addButton(new GuiNpcButton(11, this.guiLeft + 85, this.guiTop + 135, 214, 20, "advanced.sounds"));
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 3) {
            switch (this.npc.advanced.role) {
                case Trader: {
                    this.save();
                    NoppesUtil.requestOpenGUI(EnumGuiType.SetupTrader);
                    break;
                }
                case Follower: {
                    this.save();
                    NoppesUtil.requestOpenGUI(EnumGuiType.SetupFollower);
                    break;
                }
                case Bank: {
                    this.save();
                    NoppesUtil.requestOpenGUI(EnumGuiType.SetupBank);
                    break;
                }
                case Transporter: {
                    this.save();
                    NoppesUtil.requestOpenGUI(EnumGuiType.SetupTransporter);
                }
            }
        }
        if (guibutton.g == 8) {
            this.hasChanges = true;
            this.npc.advanced.setRole(button.getValue());
            boolean bl = this.getButton((int)3).h = this.npc.advanced.role != EnumRoleType.None && this.npc.advanced.role != EnumRoleType.Postman;
        }
        if (guibutton.g == 4) {
            switch (this.npc.advanced.job) {
                case Bard: {
                    NoppesUtil.openGUI(this.player, (Object)new GuiNpcBard(this.npc));
                    break;
                }
                case Healer: {
                    this.save();
                    NoppesUtil.openGUI(this.player, (Object)new GuiNpcHealer(this.npc));
                    break;
                }
                case Guard: {
                    this.save();
                    NoppesUtil.openGUI(this.player, (Object)new GuiNpcGuard(this.npc));
                    break;
                }
                case ItemGiver: {
                    this.close();
                    NoppesUtil.requestOpenGUI(EnumGuiType.SetupItemGiver);
                    break;
                }
                case Boss: {
                    this.close();
                    NoppesUtil.openGUI(this.player, (Object)new GuiNpcBoss(this.npc));
                    break;
                }
                case Spawner: {
                    this.close();
                    NoppesUtil.openGUI(this.player, new GuiNpcSpawner(this.npc));
                    break;
                }
                case Conversation: {
                    this.close();
                    NoppesUtil.openGUI(this.player, new GuiNpcConversation(this.npc));
                }
            }
        }
        if (guibutton.g == 5) {
            this.hasChanges = true;
            this.npc.advanced.setJob(button.getValue());
            boolean bl = this.getButton((int)4).h = this.npc.advanced.job != EnumJobType.None;
        }
        if (guibutton.g == 9) {
            NoppesUtil.openGUI(this.player, new GuiNPCFactionSetup(this.npc));
        }
        if (guibutton.g == 10) {
            NoppesUtil.openGUI(this.player, new GuiNPCDialogNpcOptions(this.npc, this));
        }
        if (guibutton.g == 11) {
            NoppesUtil.openGUI(this.player, new GuiNPCSoundsMenu(this.npc));
        }
        if (guibutton.g == 7) {
            NoppesUtil.openGUI(this.player, (Object)new GuiNPCLinesMenu(this.npc));
        }
    }

    @Override
    public void setGuiData(by compound) {
        this.npc.advanced.readToNBT(compound);
        this.A_();
    }

    @Override
    public void save() {
        if (this.hasChanges) {
            ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
            this.hasChanges = false;
        }
    }
}

