/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.global;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.GuiNpcMusicSelection;
import noppes.client.gui.SubGuiMailmanSendSetup;
import noppes.client.gui.SubGuiNpcAvailability;
import noppes.client.gui.SubGuiNpcCommand;
import noppes.client.gui.SubGuiNpcDialogOption;
import noppes.client.gui.SubGuiNpcDialogOptions;
import noppes.client.gui.SubGuiNpcFactionOptions;
import noppes.client.gui.SubGuiNpcTextArea;
import noppes.client.gui.global.GuiNPCQuestSelection;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.IScrollData;
import noppes.client.gui.util.ISubGuiListener;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogCategory;
import noppes.common.controllers.PlayerMail;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditDialogCategoriesPackets;
import noppes.network.server.EditDialogPackets;
import noppes.util.NoppesUtil;

public class GuiNPCManageDialogs
extends GuiNPCInterface2
implements IScrollData,
ISubGuiListener,
GuiSelectionListener,
GuiCustomScrollActionListener,
ITextfieldListener,
IGuiData {
    private GuiCustomScroll scroll;
    private HashMap<String, Integer> data = new HashMap();
    private Dialog dialog = new Dialog();
    private DialogCategory category = new DialogCategory();
    private boolean categorySelection = true;
    private GuiNpcMusicSelection gui;
    private static final ey DIALOG_CATEGORIES_GET = new EditDialogCategoriesPackets.DialogCategoriesGetPacket().makePacket("CNPCs Server");

    public GuiNPCManageDialogs(EntityNPCInterface npc) {
        super(npc);
        PacketDispatcher.sendPacketToServer((ey)DIALOG_CATEGORIES_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(0, this.guiLeft + 358, this.guiTop + 8, 58, 20, this.categorySelection ? "dialog.dialogs" : "gui.categories"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 358, this.guiTop + 38, 58, 20, "gui.add"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 358, this.guiTop + 61, 58, 20, "gui.remove"));
        if (this.scroll == null) {
            this.scroll = new GuiCustomScroll(this, 0);
            this.scroll.setSize(143, 208);
            this.scroll.guiLeft = this.guiLeft + 214;
            this.scroll.guiTop = this.guiTop + 4;
        }
        this.addScroll(this.scroll);
        if (this.categorySelection && this.category.id >= 0) {
            this.categoryGuiInit();
        }
        if (!this.categorySelection && this.dialog.id >= 0) {
            this.dialogGuiInit();
        }
    }

    private void dialogGuiInit() {
        this.addLabel(new GuiNpcLabel(1, "gui.title", this.guiLeft + 4, this.guiTop + 8, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 60, this.guiTop + 3, 140, 20, this.dialog.title));
        this.addLabel(new GuiNpcLabel(3, "dialog.dialogtext", this.guiLeft + 4, this.guiTop + 30, 0x404040));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 120, this.guiTop + 25, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(4, "availability.options", this.guiLeft + 4, this.guiTop + 51, 0x404040));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 120, this.guiTop + 46, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(5, "faction.options", this.guiLeft + 4, this.guiTop + 72, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 120, this.guiTop + 67, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(6, "dialog.options", this.guiLeft + 4, this.guiTop + 93, 0x404040));
        this.addButton(new GuiNpcButton(6, this.guiLeft + 120, this.guiTop + 89, 50, 20, "selectServer.edit"));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 4, this.guiTop + 114, 144, 20, "availability.selectquest"));
        this.addButton(new GuiNpcButton(8, this.guiLeft + 150, this.guiTop + 114, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(9, "gui.selectSound", this.guiLeft + 4, this.guiTop + 138, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 4, this.guiTop + 148, 144, 20, this.dialog.sound));
        this.addButton(new GuiNpcButton(9, this.guiLeft + 150, this.guiTop + 148, 60, 20, "mco.template.button.select"));
        this.addButton(new GuiNpcButton(13, this.guiLeft + 4, this.guiTop + 169, 164, 20, "mailbox.setup"));
        this.addButton(new GuiNpcButton(14, this.guiLeft + 170, this.guiTop + 169, 20, 20, "X"));
        if (!this.dialog.mail.subject.isEmpty()) {
            this.getButton((int)13).f = this.dialog.mail.subject;
        }
        this.addLabel(new GuiNpcLabel(10, "advMode.command", this.guiLeft + 4, this.guiTop + 195, 0x404040));
        this.addButton(new GuiNpcButton(10, this.guiLeft + 120, this.guiTop + 190, 50, 20, "selectServer.edit"));
    }

    private void categoryGuiInit() {
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 8, this.guiTop + 8, 160, 16, this.category.title));
        this.getTextField(0).f(20);
    }

    @Override
    public void elementClicked() {
        this.getTextField(2).a(this.gui.getSelected());
        this.unFocused(this.getTextField(2));
    }

    @Override
    public void buttonEvent(aut guibutton) {
        if (guibutton.g == 0) {
            this.save();
            if (this.categorySelection) {
                if (this.category.id < 0) {
                    return;
                }
                PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogsGetPacket(this.category.id).makePacket("CNPCs Server"));
            } else {
                PacketDispatcher.sendPacketToServer((ey)DIALOG_CATEGORIES_GET);
                this.dialog = new Dialog();
                this.category = new DialogCategory();
            }
            this.categorySelection = !this.categorySelection;
            this.getButton((int)0).h = false;
            this.scroll.clear();
            this.data.clear();
        }
        if (guibutton.g == 1) {
            this.save();
            String name = "New";
            while (this.data.containsKey(name)) {
                name = name + "_";
            }
            if (this.categorySelection) {
                DialogCategory dialog = new DialogCategory();
                dialog.title = name;
                EditDialogCategoriesPackets.DialogCategorySavePacket pkt = new EditDialogCategoriesPackets.DialogCategorySavePacket(dialog.writeNBT(new by()));
                PacketDispatcher.sendPacketToServer((ey)pkt.makePacket("CNPCs Server"));
            } else {
                Dialog dialog1 = new Dialog();
                dialog1.title = name;
                PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogSavePacket(this.category.id, dialog1.writeToNBT(new by())).makePacket("CNPCs Server"));
            }
        }
        if (guibutton.g == 2 && this.data.containsKey(this.scroll.getSelected())) {
            if (this.categorySelection) {
                PacketDispatcher.sendPacketToServer((ey)new EditDialogCategoriesPackets.DialogCategoryRemovePacket(this.category.id).makePacket("CNPCs Server"));
                this.category = new DialogCategory();
            } else {
                PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogRemovePacket(this.dialog.id).makePacket("CNPCs Server"));
                this.dialog = new Dialog();
            }
            this.scroll.clear();
        }
        if (guibutton.g == 3 && this.dialog.id >= 0) {
            this.setSubGui(new SubGuiNpcTextArea(this.dialog.text));
        }
        if (guibutton.g == 4 && this.dialog.id >= 0) {
            this.setSubGui(new SubGuiNpcAvailability(this.dialog.availability));
        }
        if (guibutton.g == 5 && this.dialog.id >= 0) {
            this.setSubGui(new SubGuiNpcFactionOptions(this.dialog.factionOptions));
        }
        if (guibutton.g == 6 && this.dialog.id >= 0) {
            this.setSubGui(new SubGuiNpcDialogOptions(this.dialog));
        }
        if (guibutton.g == 7 && this.dialog.id >= 0) {
            NoppesUtil.openGUI(this.player, new GuiNPCQuestSelection(this.npc, this, this.dialog.quest));
        }
        if (guibutton.g == 8 && this.dialog.id >= 0) {
            this.dialog.quest = -1;
            this.A_();
        }
        if (guibutton.g == 9 && this.dialog.id >= 0) {
            this.gui = new GuiNpcMusicSelection(this.npc, this, this.getTextField(2).b());
            NoppesUtil.openGUI(this.player, (Object)this.gui);
        }
        if (guibutton.g == 10) {
            this.setSubGui(new SubGuiNpcCommand(this.dialog.command));
        }
        if (guibutton.g == 13) {
            this.setSubGui(new SubGuiMailmanSendSetup(this.dialog.mail, this));
        }
        if (guibutton.g == 14) {
            this.dialog.mail = new PlayerMail();
            this.A_();
        }
    }

    @Override
    public void unFocused(GuiNpcTextField guiNpcTextField) {
        String old;
        String name;
        if (guiNpcTextField.id == 0) {
            if (this.category.id < 0) {
                guiNpcTextField.a("");
            } else {
                name = guiNpcTextField.b();
                if (!name.isEmpty() && !this.data.containsKey(name)) {
                    if (this.categorySelection && this.category.id >= 0) {
                        old = this.category.title;
                        this.data.remove(this.category.title);
                        this.category.title = name;
                        this.data.put(this.category.title, this.category.id);
                        this.scroll.replace(old, this.category.title);
                    }
                } else {
                    guiNpcTextField.a(this.category.title);
                }
            }
        }
        if (guiNpcTextField.id == 1) {
            if (this.dialog.id < 0) {
                guiNpcTextField.a("");
            } else {
                name = guiNpcTextField.b();
                if (!name.isEmpty() && !this.data.containsKey(name)) {
                    if (!this.categorySelection && this.dialog.id >= 0) {
                        old = this.dialog.title;
                        this.data.remove(this.dialog.title);
                        this.dialog.title = name;
                        this.data.put(this.dialog.title, this.dialog.id);
                        this.scroll.replace(old, this.dialog.title);
                    }
                } else {
                    guiNpcTextField.a(this.dialog.title);
                }
            }
        }
        if (guiNpcTextField.id == 2) {
            this.dialog.sound = guiNpcTextField.b();
        }
    }

    @Override
    public void setGuiData(by compound) {
        if (this.categorySelection) {
            this.category.readNBT(compound);
            this.setSelected(this.category.title);
            this.A_();
        } else {
            this.dialog.readNBT(compound);
            this.setSelected(this.dialog.title);
            this.A_();
            if (compound.b("DialogQuestName")) {
                this.getButton((int)7).f = compound.i("DialogQuestName");
            }
        }
    }

    @Override
    public void subGuiClosed(SubGuiInterface subgui) {
        if (subgui instanceof SubGuiNpcTextArea) {
            SubGuiNpcTextArea gui = (SubGuiNpcTextArea)subgui;
            this.dialog.text = gui.text;
        }
        if (subgui instanceof SubGuiNpcDialogOption) {
            this.setSubGui(new SubGuiNpcDialogOptions(this.dialog));
        }
        if (subgui instanceof SubGuiNpcCommand) {
            this.dialog.command = ((SubGuiNpcCommand)subgui).command;
        }
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.getButton((int)0).h = true;
        String name = this.scroll.getSelected();
        this.data = data;
        this.scroll.setList(list);
        if (name != null) {
            this.scroll.setSelected(name);
        }
        this.A_();
    }

    @Override
    public void setSelected(String selected) {
    }

    @Override
    public void selected(int ob) {
        this.dialog.quest = ob;
        PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogSavePacket(this.category.id, this.dialog.writeToNBT(new by())).makePacket("CNPCs Server"));
        PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogGetPacket(this.dialog.id).makePacket("CNPCs Server"));
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.save();
            String selected = this.scroll.getSelected();
            if (this.categorySelection) {
                this.category = new DialogCategory();
                PacketDispatcher.sendPacketToServer((ey)new EditDialogCategoriesPackets.DialogCategoryGetPacket(this.data.get(selected)).makePacket("CNPCs Server"));
            } else {
                this.dialog = new Dialog();
                PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogGetPacket(this.data.get(selected)).makePacket("CNPCs Server"));
            }
        }
    }

    @Override
    public void save() {
        GuiNpcTextField.unfocus();
        if (!this.categorySelection && this.dialog.id >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogSavePacket(this.category.id, this.dialog.writeToNBT(new by())).makePacket("CNPCs Server"));
        } else if (this.categorySelection && this.category.id >= 0) {
            EditDialogCategoriesPackets.DialogCategorySavePacket pkt = new EditDialogCategoriesPackets.DialogCategorySavePacket(this.category.writeNBT(new by()));
            PacketDispatcher.sendPacketToServer((ey)pkt.makePacket("CNPCs Server"));
        }
    }
}

