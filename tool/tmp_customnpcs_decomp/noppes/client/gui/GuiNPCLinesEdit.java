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
import noppes.client.gui.GuiNpcMusicSelection;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.common.controllers.Line;
import noppes.common.controllers.Lines;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditMainMenuPackets;
import noppes.util.NoppesUtil;

public class GuiNPCLinesEdit
extends GuiNPCInterface2
implements IGuiData {
    private final Lines lines;
    private GuiNpcTextField field;
    private GuiNpcMusicSelection gui;
    private static final ey ADVANCE_GET = new EditMainMenuPackets.MainmenuAdvancedGet().makePacket("CNPCs Server");

    public GuiNPCLinesEdit(EntityNPCInterface npc, Lines lines) {
        super(npc);
        this.lines = lines;
        PacketDispatcher.sendPacketToServer((ey)ADVANCE_GET);
    }

    @Override
    public void A_() {
        super.A_();
        for (int i = 0; i < 8; ++i) {
            String text = "";
            String sound = "";
            if (this.lines.lines.containsKey(i)) {
                Line line = this.lines.lines.get(i);
                text = line.text;
                sound = line.sound;
            }
            this.addTextField(new GuiNpcTextField(i, this, this.o, this.guiLeft + 4, this.guiTop + 4 + i * 24, 200, 20, text));
            this.addTextField(new GuiNpcTextField(i + 8, this, this.o, this.guiLeft + 208, this.guiTop + 4 + i * 24, 146, 20, sound));
            this.addButton(new GuiNpcButton(i, this.guiLeft + 358, this.guiTop + 4 + i * 24, 60, 20, "mco.template.button.select"));
        }
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        this.field = this.getTextField(button.g + 8);
        this.gui = new GuiNpcMusicSelection(this.npc, this, this.field.b());
        NoppesUtil.openGUI(this.player, (Object)this.gui);
    }

    @Override
    public void elementClicked() {
        this.field.a(this.gui.getSelected());
        this.saveLines();
    }

    @Override
    public void setGuiData(by compound) {
        this.npc.advanced.readToNBT(compound);
        this.A_();
    }

    private void saveLines() {
        HashMap<Integer, Line> lines = new HashMap<Integer, Line>();
        for (int i = 0; i < 8; ++i) {
            GuiNpcTextField tf = this.getTextField(i);
            GuiNpcTextField tf2 = this.getTextField(i + 8);
            if (tf.isEmpty()) continue;
            Line line = new Line();
            line.text = tf.b();
            line.sound = tf2.b();
            lines.put(i, line);
        }
        this.lines.lines = lines;
    }

    @Override
    public void save() {
        this.saveLines();
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}

