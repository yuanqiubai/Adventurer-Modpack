/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  awg
 *  bfq
 */
package noppes.client.gui.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.common.entity.EntityNPCInterface;

public class GuiNPCStringSlot
extends awg {
    public String selected;
    public HashSet<String> selectedList = new HashSet();
    public int size;
    private List<String> list;
    private final boolean multiSelect;
    private final GuiNPCInterface parent;
    private long prevTime = 0L;

    public GuiNPCStringSlot(List<String> list, GuiNPCInterface parent, EntityNPCInterface npc, boolean multiSelect, int size) {
        super(atv.w(), parent.g, parent.h, 32, parent.h - 64, size);
        this.parent = parent;
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        this.list = list;
        this.multiSelect = multiSelect;
        this.size = size;
    }

    public void setList(List<String> list) {
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        this.list = list;
        this.selected = "";
    }

    protected int a() {
        return this.list.size();
    }

    protected void a(int i, boolean flag) {
        long time = System.currentTimeMillis();
        if (this.selected != null && this.selected.equals(this.list.get(i)) && time - this.prevTime < 400L) {
            this.parent.doubleClicked();
        }
        this.selected = this.list.get(i);
        if (this.selectedList.contains(this.selected)) {
            this.selectedList.remove(this.selected);
        } else {
            this.selectedList.add(this.selected);
        }
        this.parent.elementClicked();
        this.prevTime = time;
    }

    protected boolean a(int i) {
        return !this.multiSelect ? this.selected != null && this.selected.equals(this.list.get(i)) : this.selectedList.contains(this.list.get(i));
    }

    protected int d() {
        return this.list.size() * this.size;
    }

    protected void b() {
        this.parent.e();
    }

    protected void a(int i, int j, int k, int l, bfq tessellator) {
        String s = this.list.get(i);
        if (!this.parent.drawSlot(i, j, k, l, tessellator, s)) {
            this.parent.b(this.parent.getFontRenderer(), s, j + 50, k + 3, 0xFFFFFF);
        }
    }

    public void clear() {
        this.list.clear();
    }
}

