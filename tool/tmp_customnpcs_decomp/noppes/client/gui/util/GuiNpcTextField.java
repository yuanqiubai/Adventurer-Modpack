/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  avf
 *  avi
 *  awe
 */
package noppes.client.gui.util;

import noppes.client.gui.util.ITextfieldListener;

public class GuiNpcTextField
extends avf {
    private static GuiNpcTextField activeTextfield = null;
    private final int[] allowedSpecialChars = new int[]{14, 211, 203, 205};
    public boolean enabled = true;
    public boolean inMenu = true;
    public boolean numbersOnly = false;
    public int id;
    public int min = 0;
    public int max = Integer.MAX_VALUE;
    public int def = 0;
    private ITextfieldListener listener;

    public GuiNpcTextField(int id, awe parent, avi fontRenderer, int i, int j, int k, int l, String s) {
        super(fontRenderer, i, j, k, l);
        this.f(500);
        this.a(s);
        this.id = id;
        if (parent instanceof ITextfieldListener) {
            this.listener = (ITextfieldListener)parent;
        }
    }

    public static void unfocus() {
        if (activeTextfield != null) {
            activeTextfield.unFocused();
        }
        activeTextfield = null;
    }

    private boolean charAllowed(char c, int i) {
        if (!(!this.numbersOnly || Character.isDigit(c) || this.isEmpty() && c == '-')) {
            for (int j : this.allowedSpecialChars) {
                if (j != i) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean a(char c, int i) {
        return this.charAllowed(c, i) && super.a(c, i);
    }

    public boolean isEmpty() {
        return this.b().trim().length() == 0;
    }

    public int getInteger() {
        return Integer.parseInt(this.b());
    }

    public boolean isInteger() {
        try {
            Integer.parseInt(this.b());
            return true;
        }
        catch (NumberFormatException var2) {
            return false;
        }
    }

    public void a(int i, int j, int k) {
        boolean wasFocused = this.l();
        super.a(i, j, k);
        if (wasFocused != this.l() && wasFocused) {
            this.unFocused();
        }
        if (this.l()) {
            activeTextfield = this;
        }
    }

    public void unFocused() {
        if (this.numbersOnly) {
            if (!this.isEmpty() && this.isInteger()) {
                if (this.getInteger() < this.min) {
                    this.a(String.valueOf(this.min));
                } else if (this.getInteger() > this.max) {
                    this.a(String.valueOf(this.max));
                }
            } else {
                this.a(String.valueOf(this.def));
            }
        }
        if (this.listener != null) {
            this.listener.unFocused(this);
        }
        if (this == activeTextfield) {
            activeTextfield = null;
        }
    }

    public void f() {
        if (this.enabled) {
            super.f();
        }
    }

    public void setMinMaxDefault(int i, int j, int k) {
        this.min = i;
        this.max = j;
        this.def = k;
    }
}

