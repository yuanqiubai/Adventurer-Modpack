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
package noppes.client.gui.global;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.IScrollData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.containers.ContainerManageBanks;
import noppes.common.controllers.Bank;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditBankPackets;

public class GuiNPCManageBanks
extends GuiContainerNPCInterface2
implements IScrollData,
GuiCustomScrollActionListener,
ITextfieldListener,
IGuiData {
    private GuiCustomScroll scroll;
    private HashMap<String, Integer> data = new HashMap();
    private final ContainerManageBanks container;
    private Bank bank = new Bank();
    private String selected = null;

    public GuiNPCManageBanks(EntityNPCInterface npc, ContainerManageBanks container) {
        super(npc, container);
        this.container = container;
        this.drawDefaultBackground = false;
        PacketDispatcher.sendPacketToServer((ey)new EditBankPackets.BanksGetPacket().makePacket("CNPCs Server"));
        this.setBackground("npcbanksetup.png");
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(6, this.p + 340, this.q + 10, 45, 20, "gui.add"));
        this.addButton(new GuiNpcButton(7, this.p + 340, this.q + 32, 45, 20, "gui.remove"));
        this.scroll = new GuiCustomScroll((awe)this, 0);
        this.scroll.a(this.f, 350, 250);
        this.scroll.setSize(160, 180);
        this.scroll.guiLeft = this.p + 174;
        this.scroll.guiTop = this.q + 8;
        for (int i = 0; i < 6; ++i) {
            int x = this.p + 6;
            int y = this.q + 36 + i * 22;
            this.addButton(new GuiNpcButton(i, x + 50, y, 80, 20, new String[]{"Can Upgrade", "Can't Upgrade", "Upgraded"}, 0));
            this.getButton((int)i).h = false;
        }
        this.addTextField(new GuiNpcTextField(0, (awe)this, this.o, this.p + 8, this.q + 8, 160, 16, ""));
        this.getTextField(0).f(20);
        this.addTextField(new GuiNpcTextField(1, (awe)this, this.o, this.p + 10, this.q + 80, 16, 16, ""));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).f(1);
        this.addTextField(new GuiNpcTextField(2, (awe)this, this.o, this.p + 10, this.q + 110, 16, 16, ""));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).f(1);
    }

    @Override
    public void a(int x, int y, float f) {
        super.a(x, y, f);
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 6) {
            this.save();
            this.scroll.clear();
            String name = "New";
            while (this.data.containsKey(name)) {
                name = name + "_";
            }
            Bank bank = new Bank();
            bank.name = name;
            by compound = new by();
            bank.writeEntityToNBT(compound);
            PacketDispatcher.sendPacketToServer((ey)new EditBankPackets.BankSavePacket(compound).makePacket("CNPCs Server"));
        } else if (guibutton.g == 7) {
            if (this.data.containsKey(this.scroll.getSelected())) {
                PacketDispatcher.sendPacketToServer((ey)new EditBankPackets.BankRemovePacket(this.data.get(this.selected)).makePacket("CNPCs Server"));
            }
        } else if (guibutton.g >= 0 && guibutton.g < 6) {
            this.bank.slotTypes.put(guibutton.g, button.getValue());
        }
    }

    protected void b(int par1, int par2) {
        this.o.b("Tab Cost", 23, 10, 0x404040);
        this.o.b("Upg. Cost", 123, 10, 0x404040);
        this.o.b("Start", 6, 52, 0x404040);
        this.o.b("Max", 9, 82, 0x404040);
    }

    @Override
    protected void a(float f, int i, int j) {
        super.a(f, i, j);
        this.scroll.a(i, j, f);
    }

    @Override
    public void setGuiData(by compound) {
        Bank bank = new Bank();
        bank.readEntityFromNBT(compound);
        this.bank = bank;
        if (bank.id == -1) {
            this.getTextField(0).a("");
            this.getTextField(1).a("");
            this.getTextField(2).a("");
            for (int i = 0; i < 6; ++i) {
                this.getButton(i).setDisplay(0);
                this.getButton((int)i).h = false;
            }
        } else {
            this.getTextField(0).a(bank.name);
            this.getTextField(1).a(Integer.toString(bank.startSlots));
            this.getTextField(2).a(Integer.toString(bank.maxSlots));
            for (int i = 0; i < 6; ++i) {
                int type = 0;
                if (bank.slotTypes.containsKey(i)) {
                    type = bank.slotTypes.get(i);
                }
                this.getButton(i).setDisplay(type);
                this.getButton((int)i).h = true;
            }
        }
        this.setSelected(bank.name);
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        String name = this.scroll.getSelected();
        this.data = data;
        this.scroll.setList(list);
        if (name != null) {
            this.scroll.setSelected(name);
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
        this.selected = selected;
        this.scroll.setSelected(selected);
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.save();
            this.selected = this.scroll.getSelected();
            PacketDispatcher.sendPacketToServer((ey)new EditBankPackets.BankGetPacket(this.data.get(this.selected)).makePacket("CNPCs Server"));
        }
    }

    @Override
    public void save() {
        if (this.selected != null && this.data.containsKey(this.selected) && this.bank != null) {
            by compound = new by();
            this.bank.currencyInventory = this.container.bank.currencyInventory;
            this.bank.upgradeInventory = this.container.bank.upgradeInventory;
            this.bank.writeEntityToNBT(compound);
            PacketDispatcher.sendPacketToServer((ey)new EditBankPackets.BankSavePacket(compound).makePacket("CNPCs Server"));
        }
    }

    @Override
    public void unFocused(GuiNpcTextField guiNpcTextField) {
        if (this.bank.id != -1) {
            if (guiNpcTextField.id == 0) {
                String num = guiNpcTextField.b();
                if (!num.isEmpty() && !this.data.containsKey(num)) {
                    String old = this.bank.name;
                    this.data.remove(this.bank.name);
                    this.bank.name = num;
                    this.data.put(this.bank.name, this.bank.id);
                    this.selected = num;
                    this.scroll.replace(old, this.bank.name);
                }
            } else if (guiNpcTextField.id == 1 || guiNpcTextField.id == 2) {
                int num1 = 1;
                if (!guiNpcTextField.isEmpty()) {
                    num1 = guiNpcTextField.getInteger();
                }
                if (num1 > 6) {
                    num1 = 6;
                }
                if (num1 < 0) {
                    num1 = 0;
                }
                if (guiNpcTextField.id == 1) {
                    this.bank.startSlots = num1;
                } else if (guiNpcTextField.id == 2) {
                    this.bank.maxSlots = num1;
                }
                if (this.bank.startSlots > this.bank.maxSlots) {
                    this.bank.maxSlots = this.bank.startSlots;
                }
                guiNpcTextField.a(Integer.toString(num1));
            }
        }
    }
}

