/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import noppes.client.gui.SubGuiNpcTextArea$1;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcTextArea;
import noppes.client.gui.util.SubGuiInterface;

public class SubGuiNpcTextArea
extends SubGuiInterface {
    public String text;

    public SubGuiNpcTextArea(String text) {
        this.text = text;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addTextField(new GuiNpcTextArea(2, this, this.o, this.guiLeft + 4, this.guiTop + 4, 176, 182, this.text));
        this.i.add(new GuiNpcButton(0, this.guiLeft + 196, this.guiTop + 160, 56, 20, "Close"));
        this.i.add(new GuiNpcButton(102, this.guiLeft + 196, this.guiTop + 20, 56, 20, "Clear"));
        this.i.add(new GuiNpcButton(101, this.guiLeft + 196, this.guiTop + 43, 56, 20, "Paste"));
        this.i.add(new GuiNpcButton(100, this.guiLeft + 196, this.guiTop + 66, 56, 20, "Copy"));
    }

    public String getClipboardContents() {
        boolean hasTransferableText;
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean bl = hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException var6) {
                System.err.println(var6);
                var6.printStackTrace();
            }
            catch (IOException var7) {
                System.err.println(var7);
                var7.printStackTrace();
            }
        }
        return result;
    }

    public void setClipboardContents(String aString) {
        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, new SubGuiNpcTextArea$1(this));
    }

    @Override
    public void close() {
        this.text = this.getTextField(2).b();
        super.close();
    }

    @Override
    public void buttonEvent(aut guibutton) {
        if (guibutton.g == 100) {
            this.setClipboardContents(this.getTextField(2).b());
        }
        if (guibutton.g == 101) {
            this.getTextField(2).a(this.getClipboardContents());
        }
        if (guibutton.g == 102) {
            this.getTextField(2).a("");
        }
        if (guibutton.g == 0) {
            this.close();
        }
    }
}

