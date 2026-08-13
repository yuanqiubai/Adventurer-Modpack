/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.global;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.constants.EnumPacketType;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditQuestCategoriesPackets;
import noppes.util.NoppesUtil;

public class GuiNPCQuestCategoryEdit
extends GuiNPCInterface {
    private final awe parent;
    private final String name;
    private final int id;
    private static final ey QUEST_CATEGORIES_GET = new EditQuestCategoriesPackets.QuestCategoriesGetPacket().makePacket("CNPCs Server");

    public GuiNPCQuestCategoryEdit(EntityNPCInterface npc, awe parent, String name, int id) {
        super(npc);
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.title = "Quest Category";
    }

    @Override
    public void A_() {
        super.A_();
        this.addTextField(1, new GuiNpcTextField(1, this, this.o, this.g / 2 - 40, 100, 140, 20, this.name));
        this.addLabel(new GuiNpcLabel(1, "Title:", this.g / 2 - 100 + 4, 105, 0xFFFFFF));
        this.addButton(2, new GuiNpcButton(2, this.g / 2 - 100, 210, 98, 20, "gui.back"));
        this.addButton(3, new GuiNpcButton(3, this.g / 2 + 2, 210, 98, 20, "Save"));
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 2) {
            NoppesUtil.openGUI(this.player, this.parent);
            PacketDispatcher.sendPacketToServer((ey)QUEST_CATEGORIES_GET);
        }
        if (guibutton.g == 3) {
            this.save();
            NoppesUtil.openGUI(this.player, this.parent);
            PacketDispatcher.sendPacketToServer((ey)QUEST_CATEGORIES_GET);
        }
    }

    @Override
    public void save() {
        String name = this.getTextField(1).b();
        if (!name.trim().isEmpty()) {
            NoppesUtil.sendData(EnumPacketType.QuestCategorySave, name, this.id);
        }
    }

    public void e() {
        this.c(0);
    }
}

