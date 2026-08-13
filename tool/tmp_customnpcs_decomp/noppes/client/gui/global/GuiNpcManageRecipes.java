/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  bjo
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.global;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.IScrollData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.constants.EnumGuiType;
import noppes.common.containers.ContainerManageRecipes;
import noppes.common.controllers.RecipeCarpentry;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditRecipePackets;
import noppes.util.NoppesUtil;
import org.lwjgl.opengl.GL11;

public class GuiNpcManageRecipes
extends GuiContainerNPCInterface2
implements IScrollData,
IGuiData,
GuiCustomScrollActionListener,
ITextfieldListener {
    private GuiCustomScroll scroll;
    private HashMap<String, Integer> data = new HashMap();
    private final ContainerManageRecipes container;
    private RecipeCarpentry recipe = new RecipeCarpentry();
    private String selected = null;
    private final bjo slot;

    public GuiNpcManageRecipes(EntityNPCInterface npc, ContainerManageRecipes container) {
        super(npc, container);
        this.container = container;
        this.drawDefaultBackground = false;
        PacketDispatcher.sendPacketToServer((ey)new EditRecipePackets.RecipesGetPacket(container.width).makePacket("CNPCs Server"));
        this.setBackground("npctradersetup.png");
        this.slot = this.getResource("slot.png");
    }

    @Override
    public void A_() {
        super.A_();
        this.scroll = new GuiCustomScroll((awe)this, 0);
        this.scroll.a(this.f, 350, 250);
        this.scroll.setSize(130, 180);
        this.scroll.guiLeft = this.p + 172;
        this.scroll.guiTop = this.q + 8;
        this.addButton(new GuiNpcButton(0, this.p + 306, this.q + 10, 84, 20, "menu.global"));
        this.addButton(new GuiNpcButton(1, this.p + 306, this.q + 32, 84, 20, "tile.npcCarpentyBench.name"));
        this.getButton((int)0).h = this.container.width == 4;
        this.getButton((int)1).h = this.container.width == 3;
        this.addButton(new GuiNpcButton(3, this.p + 306, this.q + 60, 84, 20, "gui.add"));
        this.addButton(new GuiNpcButton(4, this.p + 306, this.q + 82, 84, 20, "gui.remove"));
        this.addLabel(new GuiNpcLabel(0, "gui.ignoreDamage", this.p + 86, this.q + 32, 0x404040));
        this.addButton(new GuiNpcButton(5, this.p + 114, this.q + 40, 30, 20, new String[]{"gui.no", "gui.yes"}, 0));
        this.addTextField(new GuiNpcTextField(0, (awe)this, this.o, this.p + 8, this.q + 8, 160, 20, this.recipe.name));
        this.getTextField((int)0).enabled = false;
        this.getButton((int)5).h = false;
    }

    @Override
    public void a(int x, int y, float f) {
        super.a(x, y, f);
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g == 0) {
            this.save();
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageRecipes, 3, 0, 0);
        }
        if (guibutton.g == 1) {
            this.save();
            NoppesUtil.requestOpenGUI(EnumGuiType.ManageRecipes, 4, 0, 0);
        }
        if (guibutton.g == 3) {
            this.save();
            this.scroll.clear();
            String name = "New";
            while (this.data.containsKey(name)) {
                name = name + "_";
            }
            RecipeCarpentry recipe = new RecipeCarpentry(-1, name);
            recipe.isGlobal = this.container.width == 3;
            PacketDispatcher.sendPacketToServer((ey)new EditRecipePackets.RecipeSavePacket(recipe.writeNBT()).makePacket("CNPCs Server"));
        }
        if (guibutton.g == 4 && this.data.containsKey(this.scroll.getSelected())) {
            PacketDispatcher.sendPacketToServer((ey)new EditRecipePackets.RecipeRemovePacket(this.data.get(this.scroll.getSelected())).makePacket("CNPCs Server"));
            this.scroll.clear();
        }
        if (guibutton.g == 5) {
            this.recipe.ignoreDamage = button.getValue() == 1;
        }
    }

    public void doubleClicked() {
    }

    @Override
    public void setGuiData(by compound) {
        RecipeCarpentry recipe = new RecipeCarpentry();
        recipe.readNBT(compound);
        this.recipe = recipe;
        this.getTextField(0).a(recipe.name);
        this.container.setRecipe(recipe);
        this.getTextField((int)0).enabled = true;
        this.getButton((int)5).h = true;
        this.getButton(5).setDisplay(recipe.ignoreDamage ? 1 : 0);
        this.setSelected(recipe.name);
    }

    @Override
    protected void a(float f, int x, int y) {
        super.a(f, x, y);
        this.scroll.a(x, y, f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.f.N.a(this.slot);
        for (int i = 0; i < this.container.width; ++i) {
            for (int j = 0; j < this.container.width; ++j) {
                this.b(this.p + i * 18 + 7, this.q + j * 18 + 34, 0, 0, 20, 20);
            }
        }
        this.b(this.p + 86, this.q + 60, 0, 0, 20, 20);
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        String name = this.scroll.getSelected();
        this.data = data;
        this.scroll.setList(list);
        this.getTextField((int)0).enabled = name != null;
        boolean bl = this.getButton((int)5).h = name != null;
        if (name != null) {
            this.scroll.setSelected(name);
        }
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        if (k == 0 && this.scroll != null) {
            this.scroll.a(i, j, k);
        }
    }

    @Override
    public void setSelected(String selected) {
        this.selected = selected;
        this.scroll.setSelected(selected);
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        this.save();
        this.selected = this.scroll.getSelected();
        PacketDispatcher.sendPacketToServer((ey)new EditRecipePackets.RecipeGetPacket(this.data.get(this.selected)).makePacket("CNPCs Server"));
    }

    @Override
    public void save() {
        GuiNpcTextField.unfocus();
        if (this.selected != null && this.data.containsKey(this.selected)) {
            this.container.saveRecipe();
            PacketDispatcher.sendPacketToServer((ey)new EditRecipePackets.RecipeSavePacket(this.recipe.writeNBT()).makePacket("CNPCs Server"));
        }
    }

    @Override
    public void unFocused(GuiNpcTextField guiNpcTextField) {
        String name = guiNpcTextField.b();
        if (!name.isEmpty() && !this.data.containsKey(name)) {
            String old = this.recipe.name;
            this.data.remove(this.recipe.name);
            this.recipe.name = name;
            this.data.put(this.recipe.name, this.recipe.id);
            this.selected = name;
            this.scroll.replace(old, this.recipe.name);
        }
    }
}

