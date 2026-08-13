/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  bjo
 *  bu
 *  by
 *  cg
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import noppes.client.gui.util.GuiButtonNextPage;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiPlayerTopMenu;
import noppes.client.gui.util.IGuiData;
import noppes.common.controllers.Faction;
import noppes.common.controllers.PlayerFactionData;
import noppes.network.player.FactionsGetPacket;
import org.lwjgl.opengl.GL11;

public class GuiFaction
extends GuiNPCInterface
implements IGuiData {
    private final int xSize = 200;
    private final int ySize = 216;
    private int guiLeft;
    private int guiTop;
    private ArrayList<Faction> playerFactions = new ArrayList();
    private GuiPlayerTopMenu topMenu;
    private int page = 0;
    private int pages = 1;
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private final bjo indicator;

    public GuiFaction() {
        this.drawDefaultBackground = false;
        this.title = "";
        PacketDispatcher.sendPacketToServer((ey)new FactionsGetPacket().makePacket("CNPCs Player"));
        this.indicator = this.getResource("menubg.png");
    }

    @Override
    public void A_() {
        super.A_();
        this.guiLeft = (this.g - this.xSize) / 2;
        this.guiTop = (this.h - this.ySize) / 2;
        this.topMenu = new GuiPlayerTopMenu(this.guiLeft + 4, this.guiTop - 9, 3, this.player);
        this.buttonNextPage = new GuiButtonNextPage(1, this.guiLeft + this.xSize - 43, this.guiTop + 198, true);
        this.i.add(this.buttonNextPage);
        this.buttonPreviousPage = new GuiButtonNextPage(2, this.guiLeft + 20, this.guiTop + 198, false);
        this.i.add(this.buttonPreviousPage);
    }

    @Override
    public void a(int i, int j, float f) {
        this.e();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.indicator);
        this.b(this.guiLeft, this.guiTop + 8, 0, 0, this.xSize, this.ySize);
        this.b(this.guiLeft + 4, this.guiTop + 8, 56, 0, 200, this.ySize);
        if (this.playerFactions.isEmpty()) {
            String noFaction = bu.a((String)"faction.nostanding");
            this.o.b(noFaction, this.guiLeft + (this.xSize - this.o.a(noFaction)) / 2, this.guiTop + 80, 0x404040);
        } else {
            this.renderScreen();
        }
        super.a(i, j, f);
        this.topMenu.a(i, j, f);
    }

    private void renderScreen() {
        int size = 6;
        if (this.pages == 1) {
            size = this.playerFactions.size();
        }
        if (this.page == this.pages) {
            size = this.playerFactions.size() % 6;
        }
        for (int s = 0; s < size; ++s) {
            this.a(this.guiLeft + 2, this.guiLeft + this.xSize, this.guiTop + 14 + s * 30, -12566464);
            Faction faction = this.playerFactions.get((this.page - 1) * 6 + s);
            String name = faction.name;
            String points = " : " + faction.defaultPoints;
            String standing = bu.a((String)"faction.friendly");
            int color = 65280;
            if (faction.defaultPoints < faction.neutralPoints) {
                standing = bu.a((String)"faction.unfriendly");
                color = 0xFF0000;
                points = points + "/" + faction.neutralPoints;
            } else if (faction.defaultPoints < faction.friendlyPoints) {
                standing = bu.a((String)"faction.neutral");
                color = 0xF2FF00;
                points = points + "/" + faction.friendlyPoints;
            } else {
                points = points + "/-";
            }
            this.o.b(name, this.guiLeft + (this.xSize - this.o.a(name)) / 2, this.guiTop + 19 + s * 30, faction.color);
            this.o.b(standing, this.g / 2 - this.o.a(standing) - 1, this.guiTop + 31 + s * 30, color);
            this.o.b(points, this.g / 2, this.guiTop + 35 + s * 30, 0x404040);
        }
        this.a(this.guiLeft + 2, this.guiLeft + this.xSize, this.guiTop + 14 + size * 30, -12566464);
        if (this.pages > 1) {
            String var8 = this.page + "/" + this.pages;
            this.o.b(var8, this.guiLeft + (this.xSize - this.o.a(var8)) / 2, this.guiTop + 203, 0x404040);
        }
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 1) {
            ++this.page;
        }
        if (guibutton.g == 2) {
            --this.page;
        }
        this.updateButtons();
    }

    private void updateButtons() {
        this.buttonNextPage.i = this.page < this.pages;
        this.buttonPreviousPage.i = this.page > 1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        this.topMenu.a(i, j, k);
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
    public void setGuiData(by compound) {
        this.playerFactions = new ArrayList();
        cg list = compound.m("FactionList");
        for (int data = 0; data < list.c(); ++data) {
            Faction i$ = new Faction();
            i$.readNBT((by)list.b(data));
            this.playerFactions.add(i$);
        }
        PlayerFactionData var9 = new PlayerFactionData();
        var9.readNBT(compound);
        for (int id : var9.factionData.keySet()) {
            int points = var9.factionData.get(id);
            for (Faction faction : this.playerFactions) {
                if (faction.id != id) continue;
                faction.defaultPoints = points;
            }
        }
        this.pages = (this.playerFactions.size() - 1) / 6;
        ++this.pages;
        this.page = 1;
        this.updateButtons();
    }
}

