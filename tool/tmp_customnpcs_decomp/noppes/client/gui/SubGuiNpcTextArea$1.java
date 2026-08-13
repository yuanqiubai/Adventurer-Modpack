/*
 * Decompiled with CFR 0.152.
 */
package noppes.client.gui;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import noppes.client.gui.SubGuiNpcTextArea;

class SubGuiNpcTextArea$1
implements ClipboardOwner {
    final SubGuiNpcTextArea this$0;

    SubGuiNpcTextArea$1(SubGuiNpcTextArea var1) {
        this.this$0 = var1;
    }

    @Override
    public void lostOwnership(Clipboard arg0, Transferable arg1) {
    }
}

