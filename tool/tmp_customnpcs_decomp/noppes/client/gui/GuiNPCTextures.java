/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  att
 *  aut
 *  awe
 *  bgl
 *  bma
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;
import noppes.client.AssetsBrowser;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNPCStringSlot;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcSkinPreviewInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;
import org.lwjgl.opengl.GL11;

public class GuiNPCTextures
extends GuiNPCInterface
implements GuiNpcSkinPreviewInterface {
    private GuiNPCStringSlot slot;
    private final awe parent;
    private String root = "";
    private AssetsBrowser assets;
    private final HashSet<String> dataFolder = new HashSet();
    private final HashSet<String> dataTextures = new HashSet();

    public GuiNPCTextures(EntityNPCInterface npc, awe parent) {
        super(npc);
        this.root = AssetsBrowser.getRoot(npc.display.texture);
        this.assets = new AssetsBrowser(this.root, new String[]{"png"});
        this.drawDefaultBackground = false;
        this.title = "Select Texture";
        this.parent = parent;
    }

    @Override
    public void A_() {
        String asset2;
        super.A_();
        this.dataFolder.clear();
        String ss = "Current Folder: /assets" + this.root;
        this.addLabel(new GuiNpcLabel(0, ss, this.g / 2 - this.o.a(ss) / 2, 20, 0xFFFFFF));
        Vector<String> list = new Vector<String>();
        if (!this.assets.isRoot) {
            list.add("..<UP>..");
        }
        for (String asset2 : this.assets.folders) {
            list.add("/" + asset2);
            this.dataFolder.add("/" + asset2);
        }
        for (String asset2 : this.assets.files) {
            list.add(asset2);
            this.dataTextures.add(asset2);
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
        int index1 = this.npc.display.texture.lastIndexOf("/");
        if (index1 > 0 && this.npc.display.texture.equals(this.assets.getAsset(asset2 = this.npc.display.texture.substring(index1 + 1)))) {
            this.slot.selected = asset2;
        }
        this.slot.d(4, 5);
        this.addButton(2, new GuiNpcButton(2, this.g / 2 - 100, this.h - 44, 98, 20, "gui.back"));
    }

    @Override
    public void a(int i, int j, float f) {
        int l = this.g / 2 - 180;
        int i1 = this.h / 2 - 90;
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), (float)50.0f);
        float f1 = 250.0f / (float)this.npc.display.modelSize;
        GL11.glScalef((float)(-f1), (float)f1, (float)f1);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float f2 = this.npc.aN;
        float f3 = this.npc.A;
        float f4 = this.npc.B;
        float f5 = (float)(l + 33) - (float)i;
        float f6 = (float)(i1 + 131 - 50) - (float)j;
        GL11.glRotatef((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        att.b();
        GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-((float)Math.atan(f6 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.npc.aN = (float)Math.atan(f5 / 40.0f) * 20.0f;
        this.npc.A = (float)Math.atan(f5 / 40.0f) * 40.0f;
        this.npc.B = -((float)Math.atan(f6 / 40.0f)) * 20.0f;
        this.npc.aP = this.npc.A;
        this.npc.cloakUpdate();
        GL11.glTranslatef((float)0.0f, (float)this.npc.N, (float)0.0f);
        bgl.a.j = 180.0f;
        bgl.a.a((nn)this.npc, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        this.npc.aN = f2;
        this.npc.A = f3;
        this.npc.B = f4;
        GL11.glPopMatrix();
        att.a();
        GL11.glDisable((int)32826);
        bma.a((int)bma.b);
        GL11.glDisable((int)3553);
        bma.a((int)bma.a);
        this.slot.a(i, j, f);
        super.a(i, j, f);
    }

    @Override
    public void elementClicked() {
        if (this.dataTextures.contains(this.slot.selected) && this.slot.selected != null) {
            this.npc.display.texture = this.assets.getAsset(this.slot.selected);
            this.npc.textureLocation = null;
        }
    }

    @Override
    public void doubleClicked() {
        String selected = this.slot.selected;
        if (selected.equals("..<UP>..")) {
            this.root = this.root.substring(0, this.root.lastIndexOf("/"));
            this.assets = new AssetsBrowser(this.root, new String[]{"png"});
            this.A_();
        } else if (this.dataFolder.contains(selected)) {
            this.root = this.root + selected;
            this.assets = new AssetsBrowser(this.root, new String[]{"png"});
            this.A_();
        } else {
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 2) {
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    public void save() {
    }
}

