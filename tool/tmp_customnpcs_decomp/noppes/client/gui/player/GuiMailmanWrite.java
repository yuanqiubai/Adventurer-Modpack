/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  a
 *  aut
 *  awe
 *  bjo
 *  bkb
 *  by
 *  cg
 *  ck
 *  cl
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 *  v
 */
package noppes.client.gui.player;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.client.gui.util.GuiButtonNextPage;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class GuiMailmanWrite
extends awe {
    private static final bjo bookGuiTextures = new bjo("textures/gui/book.png");
    private final by itemstackBook;
    private int updateCount;
    private final int bookImageWidth = 192;
    private final int bookImageHeight = 192;
    private int bookTotalPages = 1;
    private int currPage;
    private cg bookPages;
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private final boolean canEdit;
    private final awe parent;

    public GuiMailmanWrite(awe parent, by compound, boolean canEdit) {
        this.parent = parent;
        this.itemstackBook = compound;
        this.canEdit = canEdit;
        if (this.itemstackBook.b("pages")) {
            this.bookPages = this.itemstackBook.m("pages");
        }
        if (this.bookPages != null) {
            this.bookPages = (cg)this.bookPages.b();
            this.bookTotalPages = this.bookPages.c();
            if (this.bookTotalPages < 1) {
                this.bookTotalPages = 1;
            }
        } else {
            this.bookPages = new cg("pages");
            this.bookPages.a((cl)new ck("1", ""));
            this.bookTotalPages = 1;
        }
    }

    static bjo func_110404_g() {
        return bookGuiTextures;
    }

    public void c() {
        super.c();
        ++this.updateCount;
    }

    public void A_() {
        this.i.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        int n = this.g / 2 - 100;
        ((Object)((Object)this)).getClass();
        this.i.add(new aut(0, n, 4 + 192, 98, 20, bkb.a((String)"gui.done")));
        ((Object)((Object)this)).getClass();
        int i = (this.g - 192) / 2;
        int b0 = 2;
        this.buttonNextPage = new GuiButtonNextPage(1, i + 120, b0 + 154, true);
        this.i.add(this.buttonNextPage);
        this.buttonPreviousPage = new GuiButtonNextPage(2, i + 38, b0 + 154, false);
        this.i.add(this.buttonPreviousPage);
        this.updateButtons();
    }

    public void b() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    private void updateButtons() {
        this.buttonNextPage.i = this.currPage < this.bookTotalPages - 1 || this.canEdit;
        this.buttonPreviousPage.i = this.currPage > 0;
    }

    protected void a(aut par1GuiButton) {
        if (par1GuiButton.h) {
            if (par1GuiButton.g == 0) {
                this.close();
            } else if (par1GuiButton.g == 1) {
                if (this.currPage < this.bookTotalPages - 1) {
                    ++this.currPage;
                } else if (this.canEdit) {
                    this.addNewPage();
                    if (this.currPage < this.bookTotalPages - 1) {
                        ++this.currPage;
                    }
                }
            } else if (par1GuiButton.g == 2 && this.currPage > 0) {
                --this.currPage;
            }
            this.updateButtons();
        }
    }

    private void addNewPage() {
        if (this.bookPages != null && this.bookPages.c() < 50) {
            this.bookPages.a((cl)new ck(String.valueOf(this.bookTotalPages + 1), ""));
            ++this.bookTotalPages;
        }
    }

    protected void a(char par1, int par2) {
        if (par2 == 1) {
            this.close();
        }
        if (this.canEdit) {
            this.keyTypedInBook(par1, par2);
        }
    }

    private void keyTypedInBook(char par1, int par2) {
        if (par1 == '\u0016') {
            this.func_74160_b(awe.l());
            return;
        }
        switch (par2) {
            case 14: {
                String s = this.func_74158_i();
                if (s.length() > 0) {
                    this.func_74159_a(s.substring(0, s.length() - 1));
                }
                return;
            }
            case 28: 
            case 156: {
                this.func_74160_b("\n");
                return;
            }
        }
        if (v.a((char)par1)) {
            this.func_74160_b(Character.toString(par1));
        }
    }

    private String func_74158_i() {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.c()) {
            ck nbttagstring = (ck)this.bookPages.b(this.currPage);
            return nbttagstring.toString();
        }
        return "";
    }

    private void func_74159_a(String par1Str) {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.c()) {
            ck nbttagstring = (ck)this.bookPages.b(this.currPage);
            nbttagstring.a = par1Str;
        }
    }

    private void func_74160_b(String par1Str) {
        String s1 = this.func_74158_i();
        String s2 = s1 + par1Str;
        int i = this.o.b(s2 + a.a + "_", 118);
        if (i <= 118 && s2.length() < 256) {
            this.func_74159_a(s2);
        }
    }

    public void a(int par1, int par2, float par3) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.J().a(bookGuiTextures);
        ((Object)((Object)this)).getClass();
        int k = (this.g - 192) / 2;
        int b0 = 2;
        ((Object)((Object)this)).getClass();
        ((Object)((Object)this)).getClass();
        this.b(k, b0, 0, 0, 192, 192);
        String s = String.format(bkb.a((String)"book.pageIndicator"), this.currPage + 1, this.bookTotalPages);
        String s1 = "";
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.c()) {
            ck nbttagstring = (ck)this.bookPages.b(this.currPage);
            s1 = nbttagstring.toString();
        }
        if (this.canEdit) {
            s1 = this.o.b() ? s1 + "_" : (this.updateCount / 6 % 2 == 0 ? s1 + a.a + "_" : s1 + a.h + "_");
        }
        int l = this.o.a(s);
        ((Object)((Object)this)).getClass();
        this.o.b(s, k - l + 192 - 44, b0 + 16, 0);
        this.o.a(s1, k + 36, b0 + 16 + 16, 116, 0);
        super.a(par1, par2, par3);
    }

    public void close() {
        this.itemstackBook.a("pages", (cl)this.bookPages);
        this.f.a(this.parent);
    }
}

