/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  awe
 */
package noppes.client.gui.util;

import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.ISubGuiListener;

public class SubGuiInterface
extends GuiNPCInterface {
    public awe parent;

    @Override
    public void save() {
    }

    @Override
    public void close() {
        if (this.parent instanceof GuiNPCInterface2) {
            ((GuiNPCInterface2)this.parent).closeSubGui(this);
        }
        if (this.parent instanceof GuiContainerNPCInterface2) {
            ((GuiContainerNPCInterface2)this.parent).closeSubGui(this);
        }
        if (this.parent instanceof ISubGuiListener) {
            ((ISubGuiListener)this.parent).subGuiClosed(this);
        }
    }

    protected void changeSubGui(SubGuiInterface gui) {
        if (this.parent instanceof GuiNPCInterface2) {
            ((GuiNPCInterface2)this.parent).setSubGui(gui);
        }
    }
}

