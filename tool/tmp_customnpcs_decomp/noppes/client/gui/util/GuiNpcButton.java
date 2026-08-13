/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  aut
 *  bu
 */
package noppes.client.gui.util;

public class GuiNpcButton
extends aut {
    public boolean shown = true;
    private String[] display;
    private int displayValue = 0;

    public GuiNpcButton(int i, int j, int k, String s) {
        super(i, j, k, bu.a((String)s));
    }

    public GuiNpcButton(int i, int j, int k, String[] display, int val) {
        super(i, j, k, display[val]);
        this.display = display;
        this.displayValue = val;
    }

    public GuiNpcButton(int i, int j, int k, int l, int m, String string) {
        super(i, j, k, l, m, bu.a((String)string));
    }

    public GuiNpcButton(int i, int j, int k, int l, int m, String[] display, int val) {
        this(i, j, k, l, m, display[val % display.length]);
        this.display = display;
        this.displayValue = val % display.length;
    }

    public void setDisplayText(String text) {
        this.f = bu.a((String)text);
    }

    public int getValue() {
        return this.displayValue;
    }

    public void a(atv minecraft, int i, int j) {
        if (this.shown) {
            super.a(minecraft, i, j);
        }
    }

    public boolean c(atv minecraft, int i, int j) {
        boolean bo = super.c(minecraft, i, j);
        if (bo && this.display != null) {
            this.displayValue = (this.displayValue + 1) % this.display.length;
            this.f = bu.a((String)this.display[this.displayValue]);
        }
        return bo;
    }

    public void setDisplay(int value) {
        this.displayValue = value;
        this.f = bu.a((String)this.display[value]);
    }
}

