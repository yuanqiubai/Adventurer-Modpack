/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  avi
 *  awe
 *  bu
 */
package noppes.client.gui.util;

public class GuiNpcLabel {
    public String label;
    public boolean enabled = true;
    public int id;
    private int x;
    private final int y;
    private int color = 0x404040;

    public GuiNpcLabel(int id, String label, int x, int y, int color) {
        this.id = id;
        this.label = bu.a((String)label);
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void drawLabel(awe gui, avi fontRenderer) {
        if (this.enabled) {
            fontRenderer.b(this.label, this.x, this.y, this.color);
        }
    }

    public void center(int width) {
        int size = atv.w().l.a(this.label);
        this.x += (width - size) / 2;
    }
}

