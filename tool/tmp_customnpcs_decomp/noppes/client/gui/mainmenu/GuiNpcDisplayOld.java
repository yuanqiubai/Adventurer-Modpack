/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  nn
 */
package noppes.client.gui.mainmenu;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.GuiNPCTextures;
import noppes.client.gui.GuiNpcModelSelection;
import noppes.client.gui.GuiNpcTextureCloaks;
import noppes.client.gui.GuiNpcTextureOverlays;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.data.DataDisplay;
import noppes.network.server.EditMainMenuPackets;
import noppes.util.NoppesUtil;

public class GuiNpcDisplayOld
extends GuiNPCInterface2
implements ITextfieldListener,
IGuiData {
    private final DataDisplay display;
    private static final ey MAINMENU_DISPLAY_GET = new EditMainMenuPackets.MainmenuDisplayGet().makePacket("CNPCs Server");

    public GuiNpcDisplayOld(EntityNPCInterface npc) {
        super(npc, 1);
        this.display = npc.display;
        PacketDispatcher.sendPacketToServer((ey)MAINMENU_DISPLAY_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "gui.name", this.guiLeft + 5, this.guiTop + 15, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 50, this.guiTop + 10, 200, 20, this.display.name));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 253, this.guiTop + 10, 110, 20, new String[]{"display.show", "display.hide", "display.showAttacking"}, this.display.showName));
        this.addLabel(new GuiNpcLabel(1, "display.model", this.guiLeft + 5, this.guiTop + 38, 0x404040));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 50, this.guiTop + 33, 110, 20, this.display.modelType.name));
        this.addLabel(new GuiNpcLabel(2, "display.size", this.guiLeft + 175, this.guiTop + 38, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 203, this.guiTop + 33, 40, 20, String.valueOf(this.display.modelSize)));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(1, 30, 5);
        this.addLabel(new GuiNpcLabel(3, "(1-30)", this.guiLeft + 246, this.guiTop + 38, 0x404040));
        this.addLabel(new GuiNpcLabel(4, "display.texture", this.guiLeft + 5, this.guiTop + 61, 0x404040));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 80, this.guiTop + 56, 200, 20, this.display.usingSkinUrl ? this.display.skinUsername : this.display.texture));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 325, this.guiTop + 56, 38, 20, "mco.template.button.select"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 283, this.guiTop + 56, 40, 20, new String[]{"display.texture", "display.player"}, this.display.usingSkinUrl ? 1 : 0));
        this.getButton((int)3).h = !this.display.usingSkinUrl;
        this.addLabel(new GuiNpcLabel(8, "display.textureCape", this.guiLeft + 5, this.guiTop + 84, 0x404040));
        this.addTextField(new GuiNpcTextField(8, this, this.o, this.guiLeft + 80, this.guiTop + 79, 200, 20, this.display.cloakTexture));
        this.addButton(new GuiNpcButton(8, this.guiLeft + 283, this.guiTop + 79, 80, 20, "display.selectTexture"));
        this.addLabel(new GuiNpcLabel(9, "display.textureOverlay", this.guiLeft + 5, this.guiTop + 108, 0x404040));
        this.addTextField(new GuiNpcTextField(9, this, this.o, this.guiLeft + 80, this.guiTop + 103, 200, 20, this.display.glowTexture));
        this.addButton(new GuiNpcButton(9, this.guiLeft + 283, this.guiTop + 103, 80, 20, "display.selectTexture"));
        this.addLabel(new GuiNpcLabel(5, "display.livingAnimation", this.guiLeft + 5, this.guiTop + 132, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 120, this.guiTop + 127, 50, 20, new String[]{"gui.yes", "gui.no"}, this.display.NoLivingAnimation ? 1 : 0));
        this.addLabel(new GuiNpcLabel(6, "display.tint", this.guiLeft + 5, this.guiTop + 156, 0x404040));
        String color = Integer.toHexString(this.display.skinColor);
        while (color.length() < 6) {
            color = "0" + color;
        }
        this.addTextField(new GuiNpcTextField(6, this, this.o, this.guiLeft + 120, this.guiTop + 151, 40, 20, color));
        this.getTextField(6).g(this.display.skinColor);
        this.addLabel(new GuiNpcLabel(7, "display.visible", this.guiLeft + 5, this.guiTop + 180, 0x404040));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 120, this.guiTop + 175, 50, 20, new String[]{"gui.yes", "gui.no", "gui.partly"}, this.display.visible));
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 0) {
            if (!textfield.isEmpty()) {
                this.display.name = textfield.b();
            } else {
                textfield.a(this.display.name);
            }
        } else if (textfield.id == 2) {
            this.display.modelSize = textfield.getInteger();
        } else if (textfield.id == 3) {
            if (this.display.usingSkinUrl) {
                this.display.skinUsername = textfield.b();
            } else {
                this.display.texture = textfield.b();
            }
        } else if (textfield.id == 6) {
            int color1;
            boolean color = false;
            try {
                color1 = Integer.parseInt(textfield.b(), 16);
            }
            catch (NumberFormatException var4) {
                color1 = 0xFFFFFF;
            }
            this.display.skinColor = color1;
            textfield.g(this.display.skinColor);
        } else if (textfield.id == 8) {
            this.display.cloakTexture = textfield.b();
        } else if (textfield.id == 9) {
            this.display.glowTexture = textfield.b();
        }
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g == 0) {
            this.display.showName = button.getValue();
        }
        if (button.g == 1) {
            NoppesUtil.openGUI(this.player, (Object)new GuiNpcModelSelection(this.npc, this));
        }
        if (button.g == 2) {
            this.display.usingSkinUrl = button.getValue() == 1;
            boolean bl = this.getButton((int)3).h = !this.display.usingSkinUrl;
            if (this.display.usingSkinUrl) {
                this.getTextField(3).a(this.display.skinUsername);
            } else {
                this.getTextField(3).a(this.npc.display.texture);
            }
        } else if (button.g == 3) {
            NoppesUtil.openGUI(this.player, new GuiNPCTextures(this.npc, this));
        } else if (button.g == 5) {
            this.display.NoLivingAnimation = button.getValue() == 1;
        } else if (button.g == 7) {
            this.display.visible = button.getValue();
        } else if (button.g == 8) {
            NoppesUtil.openGUI(this.player, new GuiNpcTextureCloaks(this.npc, this));
        } else if (button.g == 9) {
            NoppesUtil.openGUI(this.player, new GuiNpcTextureOverlays(this.npc, this));
        }
    }

    @Override
    public void save() {
        this.f.g.b((nn)this.npc);
        this.f.g.a((nn)this.npc);
        ey pkt = new EditMainMenuPackets.MainmenuDisplaySave(this.display.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void setGuiData(by compound) {
        this.display.readToNBT(compound);
        this.A_();
    }
}

