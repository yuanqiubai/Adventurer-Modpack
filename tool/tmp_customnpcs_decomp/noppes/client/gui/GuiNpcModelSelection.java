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
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNPCStringSlot;
import noppes.client.gui.util.GuiNpcButton;
import noppes.common.constants.EnumModelType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.model_entity.EntityNPCDwarfFemale;
import noppes.common.entity.model_entity.EntityNPCDwarfMale;
import noppes.common.entity.model_entity.EntityNPCElfFemale;
import noppes.common.entity.model_entity.EntityNPCElfMale;
import noppes.common.entity.model_entity.EntityNPCEnderman;
import noppes.common.entity.model_entity.EntityNPCFurryFemale;
import noppes.common.entity.model_entity.EntityNPCFurryMale;
import noppes.common.entity.model_entity.EntityNPCGolem;
import noppes.common.entity.model_entity.EntityNPCHumanFemale;
import noppes.common.entity.model_entity.EntityNPCHumanMale;
import noppes.common.entity.model_entity.EntityNPCOrcFemale;
import noppes.common.entity.model_entity.EntityNPCOrcMale;
import noppes.common.entity.model_entity.EntityNPCPony;
import noppes.common.entity.model_entity.EntityNPCVillager;
import noppes.common.entity.model_entity.EntityNpcCrystal;
import noppes.common.entity.model_entity.EntityNpcDragon;
import noppes.common.entity.model_entity.EntityNpcEnderchibi;
import noppes.common.entity.model_entity.EntityNpcMonsterFemale;
import noppes.common.entity.model_entity.EntityNpcMonsterMale;
import noppes.common.entity.model_entity.EntityNpcNagaFemale;
import noppes.common.entity.model_entity.EntityNpcNagaMale;
import noppes.common.entity.model_entity.EntityNpcSkeleton;
import noppes.common.entity.model_entity.EntityNpcSlime;
import noppes.network.server.EditChangeModelPacket;
import noppes.util.NoppesUtil;

public class GuiNpcModelSelection
extends GuiNPCInterface {
    private GuiNPCStringSlot slot;
    private final GuiNPCInterface2 parent;
    private final HashMap<String, EnumModelType> data = new HashMap();

    public GuiNpcModelSelection(EntityNPCInterface npc, GuiNPCInterface2 parent) {
        super(npc);
        this.drawDefaultBackground = false;
        this.title = "Select Npc Model";
        this.parent = parent;
    }

    @Override
    public void A_() {
        super.A_();
        Vector<String> list = new Vector<String>();
        for (EnumModelType type : EnumModelType.values()) {
            list.add(type.name);
            this.data.put(type.name, type);
        }
        this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
        this.slot.d(4, 5);
        this.slot.selected = this.npc.display.modelType.name;
        this.addButton(2, new GuiNpcButton(2, this.g / 2 - 100, this.h - 41, 98, 20, "gui.back"));
        this.addButton(4, new GuiNpcButton(4, this.g / 2 + 2, this.h - 41, 98, 20, "mco.template.button.select"));
    }

    @Override
    public void a(int i, int j, float f) {
        this.slot.a(i, j, f);
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 2) {
            NoppesUtil.openGUI(this.player, (Object)this.parent);
        }
        if (guibutton.g == 4) {
            this.close();
        }
    }

    @Override
    public void doubleClicked() {
        this.close();
    }

    @Override
    public void save() {
        if (this.npc.display.modelType != this.data.get(this.slot.selected)) {
            this.npc.display.modelType = this.data.get(this.slot.selected);
            EntityNPCInterface newnpc = null;
            if (this.npc.display.modelType == EnumModelType.HumanMale) {
                newnpc = new EntityNPCHumanMale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.Villager) {
                newnpc = new EntityNPCVillager(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.Pony) {
                newnpc = new EntityNPCPony(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.HumanFemale) {
                newnpc = new EntityNPCHumanFemale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.DwarfMale) {
                newnpc = new EntityNPCDwarfMale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.FurryMale) {
                newnpc = new EntityNPCFurryMale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.MonsterMale) {
                newnpc = new EntityNpcMonsterMale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.MonsterFemale) {
                newnpc = new EntityNpcMonsterFemale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.Skeleton) {
                newnpc = new EntityNpcSkeleton(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.DwarfFemale) {
                newnpc = new EntityNPCDwarfFemale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.FurryFemale) {
                newnpc = new EntityNPCFurryFemale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.OrcMale) {
                newnpc = new EntityNPCOrcMale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.OrcFemale) {
                newnpc = new EntityNPCOrcFemale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.ElfMale) {
                newnpc = new EntityNPCElfMale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.ElfFemale) {
                newnpc = new EntityNPCElfFemale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.Crystal) {
                newnpc = new EntityNpcCrystal(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.EnderChibi) {
                newnpc = new EntityNpcEnderchibi(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.EnderMan) {
                newnpc = new EntityNPCEnderman(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.NagaFemale) {
                newnpc = new EntityNpcNagaFemale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.NagaMale) {
                newnpc = new EntityNpcNagaMale(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.Dragon) {
                newnpc = new EntityNpcDragon(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.Slime) {
                newnpc = new EntityNpcSlime(this.npc.q);
            }
            if (this.npc.display.modelType == EnumModelType.Golem) {
                newnpc = new EntityNPCGolem(this.npc.q);
            }
            String texture = newnpc.display.texture;
            by nbttagcompound = this.npc.copy();
            newnpc.f(nbttagcompound);
            String faction = this.npc.v().e(13);
            newnpc.v().b(13, (Object)faction);
            newnpc.display.texture = texture;
            EditChangeModelPacket packet = new EditChangeModelPacket(this.npc.startPos[0], this.npc.startPos[1], this.npc.startPos[2], newnpc.copy());
            PacketDispatcher.sendPacketToServer((ey)packet.makePacket("CNPCs Server"));
        }
    }
}

