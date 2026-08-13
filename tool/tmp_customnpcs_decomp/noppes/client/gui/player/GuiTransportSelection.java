/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  bjo
 *  bu
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.IScrollData;
import noppes.client.gui.util.ITopButtonListener;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.player.TransportPacket;
import org.lwjgl.opengl.GL11;

public class GuiTransportSelection
extends GuiNPCInterface
implements ITopButtonListener,
IScrollData {
    private final bjo resource = new bjo("customnpcs", "textures/gui/smallbg.png");
    protected int xSize = 176;
    protected int guiLeft;
    protected int guiTop;
    private GuiCustomScroll scroll;

    public GuiTransportSelection(EntityNPCInterface npc) {
        super(npc);
        this.drawDefaultBackground = false;
        this.title = "";
    }

    @Override
    public void A_() {
        super.A_();
        this.guiLeft = (this.g - this.xSize) / 2;
        this.guiTop = (this.h - 222) / 2;
        String name = "";
        this.addLabel(new GuiNpcLabel(0, name, this.guiLeft + (this.xSize - this.o.a(name)) / 2, this.guiTop + 10, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 10, this.guiTop + 192, 156, 20, bu.a((String)"transporter.travel")));
        this.scroll = new GuiCustomScroll(this, 0);
        this.scroll.a(this.f, 350, 250);
        this.scroll.setSize(156, 165);
        this.scroll.guiLeft = this.guiLeft + 10;
        this.scroll.guiTop = this.guiTop + 20;
    }

    @Override
    public void a(int i, int j, float f) {
        this.e();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.resource);
        this.b(this.guiLeft, this.guiTop, 0, 0, 176, 222);
        super.a(i, j, f);
        this.scroll.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        String sel = this.scroll.getSelected();
        if (button.g == 0 && sel != null) {
            this.close();
            ey pkt = new TransportPacket(sel).makePacket("CNPCs Player");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        this.scroll.a(i, j, k);
    }

    @Override
    public void a(char c, int i) {
        if (i == 1 || i == this.f.u.N.d) {
            this.close();
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.scroll.setList(list);
    }

    @Override
    public void setSelected(String selected) {
    }
}

