/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.IScrollData;
import noppes.common.controllers.Bank;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleBank;
import noppes.network.server.EditBankPackets;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcBankSetup
extends GuiNPCInterface2
implements IScrollData,
GuiCustomScrollActionListener {
    private GuiCustomScroll scroll;
    private HashMap<String, Integer> data = new HashMap();
    private final RoleBank role;

    public GuiNpcBankSetup(EntityNPCInterface npc) {
        super(npc);
        PacketDispatcher.sendPacketToServer((ey)new EditBankPackets.BanksGetPacket().makePacket("CNPCs Server"));
        this.role = (RoleBank)npc.roleInterface;
    }

    @Override
    public void A_() {
        super.A_();
        this.scroll = new GuiCustomScroll(this, 0);
        this.scroll.a(this.f, 350, 250);
        this.scroll.setSize(200, 152);
        this.scroll.guiLeft = this.guiLeft + 85;
        this.scroll.guiTop = this.guiTop + 20;
    }

    @Override
    public void a(int x, int y, float f) {
        super.a(x, y, f);
        this.scroll.a(x, y, f);
    }

    @Override
    protected void a(aut guibutton) {
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        String name = null;
        Bank bank = this.role.getBank();
        if (bank != null) {
            name = bank.name;
        }
        this.data = data;
        this.scroll.setList(list);
        if (name != null) {
            this.setSelected(name);
        }
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        if (k == 0 && this.scroll != null) {
            this.scroll.a(i, j, k);
        }
    }

    @Override
    public void setSelected(String selected) {
        this.scroll.setSelected(selected);
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.role.bankId = this.data.get(this.scroll.getSelected());
            this.save();
        }
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }
}

