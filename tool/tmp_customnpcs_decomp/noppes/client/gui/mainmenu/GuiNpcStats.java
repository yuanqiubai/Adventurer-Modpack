/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  oj
 */
package noppes.client.gui.mainmenu;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.SubGuiNpcMeleeProperties;
import noppes.client.gui.SubGuiNpcProjectiles;
import noppes.client.gui.SubGuiNpcRangeProperties;
import noppes.client.gui.SubGuiNpcResistanceProperties;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.data.DataStats;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcStats
extends GuiNPCInterface2
implements ITextfieldListener,
IGuiData {
    DataStats stats;
    private static final ey MAINMENU_STATS_GET = new EditMainMenuPackets.MainmenuStatsGet().makePacket("CNPCs Server");

    public GuiNpcStats(EntityNPCInterface npc) {
        super(npc, 2);
        this.stats = npc.stats;
        PacketDispatcher.sendPacketToServer((ey)MAINMENU_STATS_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "stats.health", this.guiLeft + 5, this.guiTop + 15, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 85, this.guiTop + 10, 50, 18, String.valueOf(this.stats.maxHealth)));
        this.getTextField((int)0).numbersOnly = true;
        this.getTextField(0).setMinMaxDefault(1, Short.MAX_VALUE, 20);
        this.addLabel(new GuiNpcLabel(1, "stats.aggro", this.guiLeft + 140, this.guiTop + 15, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 220, this.guiTop + 10, 50, 18, String.valueOf(this.stats.aggroRange)));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(1, 64, 2);
        this.addLabel(new GuiNpcLabel(34, "stats.creaturetype", this.guiLeft + 275, this.guiTop + 15, 0x404040));
        this.addButton(new GuiNpcButton(8, this.guiLeft + 355, this.guiTop + 10, 56, 20, new String[]{"stats.normal", "stats.undead", "stats.arthropod"}, this.stats.creatureType.ordinal()));
        this.addLabel(new GuiNpcLabel(2, "stats.respawn", this.guiLeft + 5, this.guiTop + 35, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 82, this.guiTop + 30, 56, 20, new String[]{"gui.yes", "gui.day", "gui.night", "gui.no"}, this.stats.spawnCycle));
        if (this.stats.respawnTime > 0) {
            this.addLabel(new GuiNpcLabel(3, "gui.time", this.guiLeft + 140, this.guiTop + 35, 0x404040));
            this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 220, this.guiTop + 30, 50, 18, String.valueOf(this.stats.respawnTime)));
            this.getTextField((int)2).numbersOnly = true;
            this.getTextField(2).setMinMaxDefault(1, 99999, 20);
            this.addLabel(new GuiNpcLabel(4, "stats.deadbody", this.guiLeft + 275, this.guiTop + 35, 0x404040));
            this.addButton(new GuiNpcButton(1, this.guiLeft + 355, this.guiTop + 30, 51, 20, new String[]{"gui.no", "gui.yes"}, this.stats.hideKilledBody ? 1 : 0));
        }
        this.addLabel(new GuiNpcLabel(5, "stats.meleeproperties", this.guiLeft + 5, this.guiTop + 65, 0x404040));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 82, this.guiTop + 60, 56, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(6, "stats.rangedproperties", this.guiLeft + 5, this.guiTop + 89, 0x404040));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 82, this.guiTop + 84, 56, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(7, "stats.projectileproperties", this.guiLeft + 140, this.guiTop + 89, 0x404040));
        this.addButton(new GuiNpcButton(9, this.guiLeft + 217, this.guiTop + 84, 56, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(15, "potion.resistance", this.guiLeft + 5, this.guiTop + 113, 0x404040));
        this.addButton(new GuiNpcButton(15, this.guiLeft + 82, this.guiTop + 108, 56, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(8, "stats.walkspeed", this.guiLeft + 5, this.guiTop + 141, 0x404040));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 85, this.guiTop + 136, 50, 18, String.valueOf(this.stats.moveSpeed)));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(0, 10, 4);
        this.addLabel(new GuiNpcLabel(10, "stats.fireimmune", this.guiLeft + 5, this.guiTop + 163, 0x404040));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 82, this.guiTop + 158, 56, 20, new String[]{"gui.no", "gui.yes"}, this.npc.F() ? 1 : 0));
        this.addLabel(new GuiNpcLabel(11, "stats.candrown", this.guiLeft + 140, this.guiTop + 163, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 217, this.guiTop + 158, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.canDrown ? 1 : 0));
        this.addLabel(new GuiNpcLabel(12, "stats.burninsun", this.guiLeft + 5, this.guiTop + 185, 0x404040));
        this.addButton(new GuiNpcButton(14, this.guiLeft + 355, this.guiTop + 158, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.healthRegen ? 1 : 0));
        this.addLabel(new GuiNpcLabel(14, "stats.regenhealth", this.guiLeft + 275, this.guiTop + 163, 0x404040));
        this.addButton(new GuiNpcButton(6, this.guiLeft + 82, this.guiTop + 180, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.burnInSun ? 1 : 0));
        this.addLabel(new GuiNpcLabel(13, "stats.nofalldamage", this.guiLeft + 140, this.guiTop + 185, 0x404040));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 217, this.guiTop + 180, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.noFallDamage ? 1 : 0));
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 0) {
            this.stats.maxHealth = textfield.getInteger();
            this.npc.f(this.stats.maxHealth);
        } else if (textfield.id == 1) {
            this.stats.aggroRange = textfield.getInteger();
        } else if (textfield.id == 2) {
            this.stats.respawnTime = textfield.getInteger();
        } else if (textfield.id == 3) {
            this.stats.moveSpeed = textfield.getInteger();
        }
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g == 0) {
            this.stats.spawnCycle = button.getValue();
            this.stats.respawnTime = this.stats.spawnCycle == 3 ? 0 : 20;
            this.A_();
        } else if (button.g == 1) {
            this.stats.hideKilledBody = button.getValue() == 1;
        } else if (button.g == 2) {
            this.setSubGui(new SubGuiNpcMeleeProperties(this.stats));
        } else if (button.g == 3) {
            this.setSubGui(new SubGuiNpcRangeProperties(this.stats));
        } else if (button.g == 4) {
            this.npc.setImmuneToFire(button.getValue() == 1);
        } else if (button.g == 5) {
            this.stats.canDrown = button.getValue() == 1;
        } else if (button.g == 6) {
            this.stats.burnInSun = button.getValue() == 1;
        } else if (button.g == 7) {
            this.stats.noFallDamage = button.getValue() == 1;
        } else if (button.g == 8) {
            this.stats.creatureType = oj.values()[button.getValue()];
        } else if (button.g == 9) {
            this.setSubGui(new SubGuiNpcProjectiles(this.stats));
        } else if (button.g == 14) {
            this.stats.healthRegen = button.getValue() == 1;
        } else if (button.g == 15) {
            this.setSubGui(new SubGuiNpcResistanceProperties(this.stats.resistances));
        }
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuStatsSave(this.stats.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void setGuiData(by compound) {
        this.stats.readToNBT(compound);
        this.A_();
    }
}

