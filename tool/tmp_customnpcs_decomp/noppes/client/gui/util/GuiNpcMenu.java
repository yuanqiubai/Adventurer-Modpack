/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  aux
 *  avi
 *  awe
 *  bu
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.input.Keyboard
 */
package noppes.client.gui.util;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import noppes.CustomNpcs;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiMenuTopButton;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcSlider;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.common.constants.EnumGuiType;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditRemoteControl;
import noppes.util.NoppesUtil;
import org.lwjgl.input.Keyboard;

public class GuiNpcMenu {
    private final awe parent;
    private final HashMap<Integer, GuiNpcButton> buttons = new HashMap();
    private final HashMap<Integer, GuiNpcSlider> sliders = new HashMap();
    private final HashMap<Integer, GuiNpcTextField> textfields = new HashMap();
    private final HashMap<Integer, GuiNpcLabel> labels = new HashMap();
    private final HashMap<Integer, GuiCustomScroll> scrolls = new HashMap();
    private GuiMenuTopButton[] topButtons;
    private int activeMenu;
    private final EntityNPCInterface npc;

    public GuiNpcMenu(awe parent, int activeMenu, EntityNPCInterface npc) {
        this.parent = parent;
        this.activeMenu = activeMenu;
        this.npc = npc;
    }

    public void initGui(int guiLeft, int guiTop, int width) {
        this.labels.clear();
        this.textfields.clear();
        this.buttons.clear();
        this.sliders.clear();
        this.scrolls.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        GuiMenuTopButton display = new GuiMenuTopButton(1, guiLeft + 4, guiTop - 17, "menu.display");
        GuiMenuTopButton stats = new GuiMenuTopButton(2, display.d + display.getWidth(), guiTop - 17, "menu.stats");
        GuiMenuTopButton ai = new GuiMenuTopButton(6, stats.d + stats.getWidth(), guiTop - 17, "menu.ai");
        GuiMenuTopButton inv = new GuiMenuTopButton(3, ai.d + ai.getWidth(), guiTop - 17, "menu.inventory");
        GuiMenuTopButton advanced = new GuiMenuTopButton(4, inv.d + inv.getWidth(), guiTop - 17, "menu.advanced");
        GuiMenuTopButton global = new GuiMenuTopButton(5, advanced.d + advanced.getWidth(), guiTop - 17, "menu.global");
        GuiMenuTopButton close = new GuiMenuTopButton(0, guiLeft + width - 22, guiTop - 17, "X");
        GuiMenuTopButton delete = new GuiMenuTopButton(66, guiLeft + width - 72, guiTop - 17, "selectWorld.deleteButton");
        delete.d = close.d - delete.getWidth();
        for (GuiMenuTopButton button : this.topButtons = new GuiMenuTopButton[]{display, stats, ai, inv, advanced, global, close, delete}) {
            button.active = button.g == this.activeMenu;
        }
    }

    private void topButtonPressed(GuiMenuTopButton button) {
        if (!button.f.equals(this.activeMenu)) {
            atv mc = atv.w();
            mc.v.a("random.click", 1.0f, 1.0f);
            if (button.g == 0) {
                this.close();
            } else if (button.g == 66) {
                aux guiyesno = new aux(this.parent, "Confirm", bu.a((String)"gui.delete"), 0);
                mc.a((awe)guiyesno);
            } else {
                this.save();
                if (button.g == 1) {
                    CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuDisplay);
                } else if (button.g == 2) {
                    CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuStats);
                } else if (button.g == 3) {
                    NoppesUtil.requestOpenGUI(EnumGuiType.MainMenuInv);
                } else if (button.g == 4) {
                    CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuAdvanced);
                } else if (button.g == 5) {
                    CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuGlobal);
                } else if (button.g == 6) {
                    CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuAI);
                }
                this.activeMenu = button.g;
            }
        }
    }

    private void save() {
        GuiNpcTextField.unfocus();
        if (this.parent instanceof GuiContainerNPCInterface2) {
            ((GuiContainerNPCInterface2)this.parent).save();
        }
        if (this.parent instanceof GuiNPCInterface2) {
            ((GuiNPCInterface2)this.parent).save();
        }
    }

    private void close() {
        if (this.parent instanceof GuiContainerNPCInterface2) {
            ((GuiContainerNPCInterface2)this.parent).close();
        }
        if (this.parent instanceof GuiNPCInterface2) {
            ((GuiNPCInterface2)this.parent).close();
        }
        if (this.npc != null) {
            this.npc.reset();
            PacketDispatcher.sendPacketToServer((ey)new EditRemoteControl.RemoteResetPacket(this.npc.k).makePacket("CNPCs Server"));
        }
    }

    public void updateScreen() {
        for (GuiNpcTextField tf : this.textfields.values()) {
            if (!tf.enabled) continue;
            tf.a();
        }
    }

    public void mouseClicked(int i, int j, int k) {
        for (GuiNpcTextField i$ : this.textfields.values()) {
            if (!i$.enabled) continue;
            i$.a(i, j, k);
        }
        if (k == 0) {
            atv var9 = atv.w();
            for (GuiMenuTopButton button : this.topButtons) {
                if (!button.c(var9, i, j)) continue;
                this.topButtonPressed(button);
            }
            for (GuiCustomScroll var12 : this.scrolls.values()) {
                var12.a(i, j, k);
            }
        }
    }

    public void keyTyped(char c, int i) {
        for (GuiNpcTextField tf : this.textfields.values()) {
            tf.a(c, i);
        }
        if (i == 1) {
            this.close();
        }
    }

    public void addButton(GuiNpcButton button) {
        this.buttons.put(button.g, button);
    }

    public GuiNpcButton getButton(int i) {
        return this.buttons.get(i);
    }

    public void addSlider(GuiNpcSlider slider) {
        this.sliders.put(slider.g, slider);
    }

    public GuiNpcSlider getSlider(int i) {
        return this.sliders.get(i);
    }

    public void addTextField(GuiNpcTextField tf) {
        this.textfields.put(tf.id, tf);
    }

    public GuiNpcTextField getTextField(int i) {
        return this.textfields.get(i);
    }

    public void addLabel(GuiNpcLabel label) {
        this.labels.put(label.id, label);
    }

    public GuiNpcLabel getLabel(int i) {
        return this.labels.get(i);
    }

    public void addScroll(GuiCustomScroll scroll, atv mc) {
        scroll.a(mc, 350, 250);
        this.scrolls.put(scroll.id, scroll);
    }

    public void drawElements(avi fontRenderer, int i, int j, atv mc, float f) {
        for (GuiNpcLabel scroll : this.labels.values()) {
            scroll.drawLabel(this.parent, fontRenderer);
        }
        for (GuiNpcTextField var11 : this.textfields.values()) {
            var11.f();
        }
        for (GuiMenuTopButton button : this.topButtons) {
            button.a(mc, i, j);
        }
        for (GuiCustomScroll var12 : this.scrolls.values()) {
            var12.a(i, j, f);
        }
    }

    public void clearAllTextFields() {
        for (GuiNpcTextField textfield : this.textfields.values()) {
            textfield.a("0");
        }
    }
}

