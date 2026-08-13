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
import noppes.client.gui.SubGuiMailmanSendSetup;
import noppes.client.gui.SubGuiNpcFactionOptions;
import noppes.client.gui.SubGuiNpcTextArea;
import noppes.client.gui.global.GuiNPCQuestSelection;
import noppes.client.gui.questtypes.GuiNpcQuestTypeDialog;
import noppes.client.gui.questtypes.GuiNpcQuestTypeKill;
import noppes.client.gui.questtypes.GuiNpcQuestTypeLocation;
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
import noppes.common.constants.EnumGuiType;
import noppes.common.constants.EnumQuestCompletion;
import noppes.common.constants.EnumQuestRepeat;
import noppes.common.constants.EnumQuestType;
import noppes.common.controllers.PlayerMail;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestCategory;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditDialogPackets;
import noppes.network.server.EditQuestCategoriesPackets;
import noppes.network.server.EditQuestPackets;
import noppes.util.NoppesUtil;

public class GuiNPCManageQuest
extends GuiNPCInterface2
implements IScrollData,
ISubGuiListener,
GuiSelectionListener,
GuiCustomScrollActionListener,
ITextfieldListener,
IGuiData {
    public static Quest quest = new Quest();
    public static awe Instance;
    private GuiCustomScroll scroll;
    private HashMap<String, Integer> data = new HashMap();
    private QuestCategory category = new QuestCategory();
    private boolean categorySelection = true;
    private boolean questlogTA = false;
    private static final ey QUEST_CATEGORIES_GET;

    public GuiNPCManageQuest(EntityNPCInterface npc) {
        super(npc);
        Instance = this;
        PacketDispatcher.sendPacketToServer((ey)QUEST_CATEGORIES_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addButton(new GuiNpcButton(0, this.guiLeft + 358, this.guiTop + 8, 58, 20, this.categorySelection ? "quest.quests" : "gui.categories"));
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
        if (!this.categorySelection && GuiNPCManageQuest.quest.id >= 0) {
            this.dialogGuiInit();
        }
    }

    private void dialogGuiInit() {
        this.addLabel(new GuiNpcLabel(1, "gui.title", this.guiLeft + 4, this.guiTop + 8, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 60, this.guiTop + 3, 140, 20, GuiNPCManageQuest.quest.title));
        this.addLabel(new GuiNpcLabel(3, "quest.completedtext", this.guiLeft + 4, this.guiTop + 30, 0x404040));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 120, this.guiTop + 25, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(4, "quest.questlogtext", this.guiLeft + 4, this.guiTop + 51, 0x404040));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 120, this.guiTop + 46, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(5, "quest.reward", this.guiLeft + 4, this.guiTop + 72, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 120, this.guiTop + 67, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(6, "gui.type", this.guiLeft + 4, this.guiTop + 93, 0x404040));
        this.addButton(new GuiNpcButton(6, this.guiLeft + 90, this.guiTop + 88, 70, 20, new String[]{"quest.item", "quest.dialog", "quest.kill", "quest.location"}, GuiNPCManageQuest.quest.type.ordinal()));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 162, this.guiTop + 88, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiNpcLabel(8, "quest.repeatable", this.guiLeft + 4, this.guiTop + 114, 0x404040));
        this.addButton(new GuiNpcButton(8, this.guiLeft + 110, this.guiTop + 109, 70, 20, new String[]{"gui.no", "gui.yes", "quest.daily", "quest.weekly"}, GuiNPCManageQuest.quest.repeat.ordinal()));
        this.addButton(new GuiNpcButton(9, this.guiLeft + 4, this.guiTop + 131, 90, 20, new String[]{"quest.npc", "quest.instant"}, GuiNPCManageQuest.quest.completion.ordinal()));
        if (GuiNPCManageQuest.quest.completerNpc.isEmpty()) {
            GuiNPCManageQuest.quest.completerNpc = this.npc.display.name;
        }
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 96, this.guiTop + 131, 114, 20, GuiNPCManageQuest.quest.completerNpc));
        this.getTextField((int)2).enabled = GuiNPCManageQuest.quest.completion == EnumQuestCompletion.Npc;
        this.addLabel(new GuiNpcLabel(10, "faction.options", this.guiLeft + 4, this.guiTop + 157, 0x404040));
        this.addButton(new GuiNpcButton(10, this.guiLeft + 120, this.guiTop + 152, 50, 20, "selectServer.edit"));
        this.addButton(new GuiNpcButton(13, this.guiLeft + 4, this.guiTop + 173, 164, 20, "mailbox.setup"));
        this.addButton(new GuiNpcButton(14, this.guiLeft + 170, this.guiTop + 173, 20, 20, "X"));
        if (!GuiNPCManageQuest.quest.mail.subject.isEmpty()) {
            this.getButton((int)13).f = GuiNPCManageQuest.quest.mail.subject;
        }
        this.addButton(new GuiNpcButton(11, this.guiLeft + 4, this.guiTop + 194, 164, 20, "quest.next"));
        this.addButton(new GuiNpcButton(12, this.guiLeft + 170, this.guiTop + 194, 20, 20, "X"));
        if (!GuiNPCManageQuest.quest.nextQuestTitle.isEmpty()) {
            this.getButton((int)11).f = GuiNPCManageQuest.quest.nextQuestTitle;
        }
    }

    private void categoryGuiInit() {
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 8, this.guiTop + 8, 160, 16, this.category.title));
        this.getTextField(0).f(20);
    }

    @Override
    public void buttonEvent(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            this.save();
            if (this.categorySelection) {
                if (this.category.id < 0) {
                    return;
                }
                quest = new Quest();
                PacketDispatcher.sendPacketToServer((ey)new EditQuestPackets.QuestsGetPacket(this.category.id).makePacket("CNPCs Server"));
            } else if (!this.categorySelection) {
                quest = new Quest();
                this.category = new QuestCategory();
                PacketDispatcher.sendPacketToServer((ey)QUEST_CATEGORIES_GET);
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
                QuestCategory quest = new QuestCategory();
                quest.title = name;
                PacketDispatcher.sendPacketToServer((ey)new EditQuestCategoriesPackets.QuestCategorySavePacket(quest.writeNBT(new by())).makePacket("CNPCs Server"));
            } else {
                Quest quest1 = new Quest();
                quest1.title = name;
                ey pkt = new EditQuestPackets.QuestSavePacket(this.category.id, quest1.writeToNBT(new by())).makePacket("CNPCs Server");
                PacketDispatcher.sendPacketToServer((ey)pkt);
            }
        }
        if (guibutton.g == 2 && this.data.containsKey(this.scroll.getSelected())) {
            if (this.categorySelection) {
                PacketDispatcher.sendPacketToServer((ey)new EditQuestCategoriesPackets.QuestCategoryRemovePacket(this.category.id).makePacket("CNPCs Server"));
                this.category = new QuestCategory();
            } else {
                PacketDispatcher.sendPacketToServer((ey)new EditQuestPackets.QuestRemovePacket(GuiNPCManageQuest.quest.id).makePacket("CNPCs Server"));
                quest = new Quest();
            }
            this.scroll.clear();
        }
        if (guibutton.g == 3 && GuiNPCManageQuest.quest.id >= 0) {
            this.questlogTA = false;
            this.setSubGui(new SubGuiNpcTextArea(GuiNPCManageQuest.quest.completeText));
        }
        if (guibutton.g == 4 && GuiNPCManageQuest.quest.id >= 0) {
            this.questlogTA = true;
            this.setSubGui(new SubGuiNpcTextArea(GuiNPCManageQuest.quest.logText));
        }
        if (guibutton.g == 5 && GuiNPCManageQuest.quest.id >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.QuestOpenGuiPacket(EnumGuiType.QuestReward.ordinal(), quest.writeToNBT(new by())).makePacket("CNPCs Server"));
        }
        if (guibutton.g == 6 && GuiNPCManageQuest.quest.id >= 0) {
            quest.setType(EnumQuestType.values()[button.getValue()]);
        }
        if (guibutton.g == 7) {
            if (GuiNPCManageQuest.quest.type == EnumQuestType.Item) {
                PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.QuestOpenGuiPacket(EnumGuiType.QuestItem.ordinal(), quest.writeToNBT(new by())).makePacket("CNPCs Server"));
            }
            if (GuiNPCManageQuest.quest.type == EnumQuestType.Dialog) {
                NoppesUtil.openGUI(this.player, new GuiNpcQuestTypeDialog(this.npc, quest, this));
            }
            if (GuiNPCManageQuest.quest.type == EnumQuestType.Kill) {
                NoppesUtil.openGUI(this.player, new GuiNpcQuestTypeKill(this.npc, quest, this));
            }
            if (GuiNPCManageQuest.quest.type == EnumQuestType.Location) {
                NoppesUtil.openGUI(this.player, new GuiNpcQuestTypeLocation(this.npc, quest, this));
            }
        }
        if (guibutton.g == 8) {
            GuiNPCManageQuest.quest.repeat = EnumQuestRepeat.values()[button.getValue()];
        }
        if (guibutton.g == 9) {
            GuiNPCManageQuest.quest.completion = EnumQuestCompletion.values()[button.getValue()];
            boolean bl = this.getTextField((int)2).enabled = GuiNPCManageQuest.quest.completion == EnumQuestCompletion.Npc;
        }
        if (guibutton.g == 10) {
            this.setSubGui(new SubGuiNpcFactionOptions(GuiNPCManageQuest.quest.factionOptions));
        }
        if (guibutton.g == 11 && GuiNPCManageQuest.quest.id >= 0) {
            NoppesUtil.openGUI(this.player, new GuiNPCQuestSelection(this.npc, this, GuiNPCManageQuest.quest.nextQuestid));
        }
        if (guibutton.g == 12 && GuiNPCManageQuest.quest.id >= 0) {
            GuiNPCManageQuest.quest.nextQuestid = -1;
            this.A_();
        }
        if (guibutton.g == 13) {
            this.setSubGui(new SubGuiMailmanSendSetup(GuiNPCManageQuest.quest.mail, this));
        }
        if (guibutton.g == 14) {
            GuiNPCManageQuest.quest.mail = new PlayerMail();
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
            if (GuiNPCManageQuest.quest.id < 0) {
                guiNpcTextField.a("");
            } else {
                name = guiNpcTextField.b();
                if (!name.isEmpty() && !this.data.containsKey(name)) {
                    if (!this.categorySelection && GuiNPCManageQuest.quest.id >= 0) {
                        old = GuiNPCManageQuest.quest.title;
                        this.data.remove(GuiNPCManageQuest.quest.title);
                        GuiNPCManageQuest.quest.title = name;
                        this.data.put(GuiNPCManageQuest.quest.title, GuiNPCManageQuest.quest.id);
                        this.scroll.replace(old, GuiNPCManageQuest.quest.title);
                    }
                } else {
                    guiNpcTextField.a(GuiNPCManageQuest.quest.title);
                }
            }
        }
        if (guiNpcTextField.id == 2) {
            GuiNPCManageQuest.quest.completerNpc = guiNpcTextField.b();
        }
    }

    @Override
    public void setGuiData(by compound) {
        if (this.categorySelection) {
            this.category.readNBT(compound);
            this.setSelected(this.category.title);
            this.A_();
        } else {
            quest.readNBT(compound);
            this.setSelected(GuiNPCManageQuest.quest.title);
            this.A_();
        }
    }

    @Override
    public void subGuiClosed(SubGuiInterface subgui) {
        if (subgui instanceof SubGuiNpcTextArea) {
            SubGuiNpcTextArea gui = (SubGuiNpcTextArea)subgui;
            if (this.questlogTA) {
                GuiNPCManageQuest.quest.logText = gui.text;
            } else {
                GuiNPCManageQuest.quest.completeText = gui.text;
            }
        } else {
            this.A_();
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
        GuiNPCManageQuest.quest.nextQuestid = ob;
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.save();
            String selected = this.scroll.getSelected();
            if (this.categorySelection) {
                this.category = new QuestCategory();
                PacketDispatcher.sendPacketToServer((ey)new EditQuestCategoriesPackets.QuestCategoryGetPacket(this.data.get(selected)).makePacket("CNPCs Server"));
            } else {
                quest = new Quest();
                PacketDispatcher.sendPacketToServer((ey)new EditQuestPackets.QuestGetPacket(this.data.get(selected)).makePacket("CNPCs Server"));
            }
        }
    }

    @Override
    public void close() {
        super.close();
        quest = new Quest();
    }

    @Override
    public void save() {
        GuiNpcTextField.unfocus();
        if (!this.categorySelection && GuiNPCManageQuest.quest.id >= 0) {
            ey pkt = new EditQuestPackets.QuestSavePacket(this.category.id, quest.writeToNBT(new by())).makePacket("CNPCs Server");
            PacketDispatcher.sendPacketToServer((ey)pkt);
        } else if (this.categorySelection && this.category.id >= 0) {
            PacketDispatcher.sendPacketToServer((ey)new EditQuestCategoriesPackets.QuestCategorySavePacket(this.category.writeNBT(new by())).makePacket("CNPCs Server"));
        }
    }

    static {
        QUEST_CATEGORIES_GET = new EditQuestCategoriesPackets.QuestCategoriesGetPacket().makePacket("CNPCs Server");
    }
}

