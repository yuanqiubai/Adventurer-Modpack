/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  avi
 *  awe
 */
package noppes.client.gui.util;

import noppes.client.gui.util.GuiNpcTextField;

public class GuiNpcTextArea
extends GuiNpcTextField {
    public boolean inMenu = true;
    public boolean numbersOnly = false;
    private final int posX;
    private final int posY;
    private final int d;
    private final int e;
    private int h;
    private final avi fontrenderer;
    private int r = 0;

    public GuiNpcTextArea(int id, awe guiscreen, avi fontrenderer, int i, int j, int k, int l, String s) {
        super(id, guiscreen, fontrenderer, i, j, k, l, s);
        this.posX = i;
        this.posY = j;
        this.d = k;
        this.e = l;
        this.fontrenderer = fontrenderer;
        this.f(1500);
        this.a(s);
    }

    public void a() {
        ++this.h;
    }

    @Override
    public boolean a(char c, int i) {
        if (!this.l()) {
            return false;
        }
        String originalText = this.b();
        this.a(originalText);
        if (c == '\r' || c == '\n') {
            this.a(originalText + c);
        }
        boolean bo = super.a(c, i);
        String newText = this.b();
        if (originalText.length() > newText.length()) {
            --this.r;
        }
        if (originalText.length() < newText.length()) {
            ++this.r;
        }
        return bo;
    }

    @Override
    public void a(int i, int j, int k) {
        boolean wasFocused = this.l();
        super.a(i, j, k);
        if (!wasFocused && this.l()) {
            this.r = this.b().length();
        }
    }

    @Override
    public void f() {
        GuiNpcTextArea.a((int)(this.posX - 1), (int)(this.posY - 1), (int)(this.posX + this.d + 1), (int)(this.posY + this.e + 1), (int)-6250336);
        GuiNpcTextArea.a((int)this.posX, (int)this.posY, (int)(this.posX + this.d), (int)(this.posY + this.e), (int)-16777216);
        int x = 0;
        String line = "";
        int color = 0xE0E0E0;
        for (char len$ : this.b().toCharArray()) {
            if (len$ != '\r' && len$ != '\n') {
                if (this.fontrenderer.a(line + len$) > this.d - 8) {
                    this.b(this.fontrenderer, line, this.posX + 4, this.posY + 4 + x * this.fontrenderer.a, color);
                    line = "";
                    ++x;
                }
                line = line + len$;
                continue;
            }
            this.b(this.fontrenderer, line, this.posX + 4, this.posY + 4 + x * this.fontrenderer.a, color);
            line = "";
            ++x;
        }
        this.b(this.fontrenderer, line, this.posX + 4, this.posY + 4 + x * this.fontrenderer.a, color);
        boolean var10 = this.l() && this.h / 6 % 2 == 0;
        int i = 0;
        x = 0;
        line = "";
        if (var10 && 0 == this.r) {
            this.fontrenderer.b("_", this.posX + 3 + this.fontrenderer.a(line), this.posY + 4 + x * this.fontrenderer.a, color);
        }
        for (char c : this.b().toCharArray()) {
            ++i;
            if (c != '\r' && c != '\n') {
                if (this.fontrenderer.a(line + c) > this.d - 8) {
                    line = "";
                    ++x;
                    line = line + c;
                } else {
                    line = line + c;
                }
            } else {
                line = "";
                ++x;
            }
            if (!var10 || i != this.r) continue;
            this.fontrenderer.b("_", this.posX + 3 + this.fontrenderer.a(line), this.posY + 4 + x * this.fontrenderer.a, color);
        }
    }
}

