/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bjo
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.controllers.MusicController;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.common.constants.EnumOptionType;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogOption;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.player.PlayerDialogPacket;
import noppes.network.player.QuestPackets;
import noppes.util.NoppesStringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiDialogInteract
extends GuiNPCInterface {
    private final Dialog dialog;
    private final bjo wheel;
    private final bjo[] wheelparts;
    private final bjo indicator;
    private int selectedX = 0;
    private int selectedY = 0;
    private int selected = 0;

    public GuiDialogInteract(EntityNPCInterface npc, Dialog dialog) {
        super(npc);
        this.dialog = dialog;
        this.title = "";
        this.wheel = this.getResource("wheel.png");
        this.indicator = this.getResource("indicator.png");
        this.wheelparts = new bjo[]{this.getResource("wheel1.png"), this.getResource("wheel2.png"), this.getResource("wheel3.png"), this.getResource("wheel4.png"), this.getResource("wheel5.png"), this.getResource("wheel6.png")};
        if (dialog.sound != null && !dialog.sound.isEmpty()) {
            MusicController.Instance.playStreaming(dialog.sound, (float)npc.u, (float)npc.v, (float)npc.w);
        }
    }

    @Override
    public void A_() {
        super.A_();
        this.f.w.a();
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
        int yoffset = (this.h - 240) / 2;
        this.b(this.o, this.npc.an(), this.g / 2 - this.o.a(this.npc.an()) / 2, yoffset + 1, 0xE0E0E0);
        int x = 0;
        if (this.dialog == null) {
            return;
        }
        String text = NoppesStringUtils.formatText(this.dialog.text, this.player.bu);
        String line = "";
        for (char option : text.toCharArray()) {
            if (option != '\r' && option != '\n') {
                if (this.o.a(line + option) > 182) {
                    this.b(this.o, line, this.g / 2 + 4 - 95, yoffset + 11 + x * this.o.a, 0xE0E0E0);
                    line = "";
                    ++x;
                }
                line = line + option;
                continue;
            }
            this.b(this.o, line, this.g / 2 + 4 - 95, yoffset + 11 + x * this.o.a, 0xE0E0E0);
            line = "";
            ++x;
        }
        this.b(this.o, line, this.g / 2 + 4 - 95, yoffset + 11 + x * this.o.a, 0xE0E0E0);
        if (this.dialog.hasOtherOptions()) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.f.N.a(this.wheel);
            this.b(this.g / 2 - 31, yoffset + 198, 0, 0, 63, 40);
            this.selectedX += Mouse.getDX();
            this.selectedY += Mouse.getDY();
            int var13 = 80;
            if (this.selectedX > var13) {
                this.selectedX = var13;
            }
            if (this.selectedX < -var13) {
                this.selectedX = -var13;
            }
            if (this.selectedY > var13) {
                this.selectedY = var13;
            }
            if (this.selectedY < -var13) {
                this.selectedY = -var13;
            }
            this.selected = 2;
            if (this.selectedY < -20) {
                ++this.selected;
            }
            if (this.selectedY > 54) {
                --this.selected;
            }
            if (this.selectedX < 0) {
                this.selected += 3;
            }
            this.f.N.a(this.wheelparts[this.selected - 1]);
            this.b(this.g / 2 - 31, yoffset + 198, 0, 0, 85, 55);
            for (Integer integer : this.dialog.options.keySet()) {
                int slot = integer;
                DialogOption var15 = this.dialog.options.get(slot);
                if (var15 == null || var15.optionType == EnumOptionType.Disabled) continue;
                int color = var15.optionColor;
                if (slot == this.selected - 1) {
                    color = 8622040;
                }
                if (slot == 0) {
                    this.b(this.o, var15.title, this.g / 2 + 13, yoffset + 192, color);
                }
                if (slot == 1) {
                    this.b(this.o, var15.title, this.g / 2 + 33, yoffset + 210, color);
                }
                if (slot == 2) {
                    this.b(this.o, var15.title, this.g / 2 + 27, yoffset + 230, color);
                }
                if (slot == 3) {
                    this.b(this.o, var15.title, this.g / 2 - 13 - this.o.a(var15.title), yoffset + 192, color);
                }
                if (slot == 4) {
                    this.b(this.o, var15.title, this.g / 2 - 33 - this.o.a(var15.title), yoffset + 210, color);
                }
                if (slot != 5) continue;
                this.b(this.o, var15.title, this.g / 2 - 27 - this.o.a(var15.title), yoffset + 230, color);
            }
            this.f.N.a(this.indicator);
            this.b(this.g / 2 + this.selectedX / 4 - 2, yoffset + 214 - this.selectedY / 6, 0, 0, 8, 8);
        }
    }

    private void setSelectedXY() {
        if (this.selected == 1) {
            this.selectedX = 33;
            this.selectedY = 72;
        } else if (this.selected == 2) {
            this.selectedX = 80;
            this.selectedY = 31;
        } else if (this.selected == 3) {
            this.selectedX = 54;
            this.selectedY = -45;
        } else if (this.selected == 4) {
            this.selectedX = -33;
            this.selectedY = 72;
        } else if (this.selected == 5) {
            this.selectedX = -80;
            this.selectedY = 31;
        } else if (this.selected == 6) {
            this.selectedX = -54;
            this.selectedY = -45;
        }
    }

    @Override
    public void a(char c, int i) {
        if ((i == this.f.u.I.d || i == 200) && this.selected - 1 != 0 && this.selected - 1 != 3) {
            --this.selected;
            this.setSelectedXY();
        }
        if ((i == this.f.u.K.d || i == 208) && this.selected + 1 != 4 && this.selected + 1 != 7) {
            ++this.selected;
            this.setSelectedXY();
        }
        if ((i == this.f.u.L.d || i == 205) && this.selected - 3 > 0) {
            this.selected -= 3;
            this.setSelectedXY();
        }
        if ((i == this.f.u.J.d || i == 203) && this.selected + 3 < 7) {
            this.selected += 3;
            this.setSelectedXY();
        }
        if (i == 28) {
            this.handleDialogSelection();
        }
        if (i == 1 || i == this.f.u.N.d) {
            this.closed();
            this.close();
        }
        super.a(c, i);
    }

    @Override
    public void a(int i, int j, int k) {
        this.handleDialogSelection();
    }

    private void handleDialogSelection() {
        this.close();
        if (this.dialog != null && this.dialog.hasOtherOptions()) {
            DialogOption option = this.dialog.options.get(this.selected - 1);
            if (option != null && option.optionType != EnumOptionType.QuitOption && option.optionType != EnumOptionType.Disabled) {
                PacketDispatcher.sendPacketToServer((ey)new PlayerDialogPacket(this.dialog.id, this.selected - 1).makePacket("CNPCs Player"));
                this.f.v.a("random.click", 1.0f, 1.0f);
            } else {
                this.closed();
            }
        } else {
            this.closed();
        }
    }

    private void closed() {
        PacketDispatcher.sendPacketToServer((ey)new QuestPackets.CheckQuestCompletionPacket().makePacket("CNPCs Player"));
    }

    @Override
    public void save() {
    }
}

