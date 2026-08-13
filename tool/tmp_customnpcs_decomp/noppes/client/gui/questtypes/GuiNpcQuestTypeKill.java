/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  aut
 *  awe
 *  nt
 *  of
 */
package noppes.client.gui.questtypes;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.controllers.Quest;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.quests.QuestKill;
import noppes.util.NoppesUtil;

public class GuiNpcQuestTypeKill
extends GuiNPCInterface
implements ITextfieldListener,
GuiCustomScrollActionListener {
    private final awe parent;
    private GuiCustomScroll scroll;
    private final QuestKill quest;
    private GuiNpcTextField lastSelected;

    public GuiNpcQuestTypeKill(EntityNPCInterface npc, Quest q, awe parent) {
        super(npc);
        this.parent = parent;
        this.title = "Quest Kill Setup";
        this.quest = (QuestKill)q.questInterface;
    }

    @Override
    public void A_() {
        super.A_();
        int i = 0;
        this.addLabel(new GuiNpcLabel(0, "You can fill in npc or player names too", this.guiLeft - 100, this.guiTop + 50, 0xFFFFFF));
        for (String list : this.quest.targets.keySet()) {
            this.addTextField(new GuiNpcTextField(i, this, this.o, this.guiLeft - 100, this.guiTop + 70 + i * 22, 180, 20, list));
            this.addTextField(new GuiNpcTextField(i + 3, this, this.o, this.guiLeft + 84, this.guiTop + 70 + i * 22, 30, 20, String.valueOf(this.quest.targets.get(list))));
            this.getTextField((int)(i + 3)).numbersOnly = true;
            this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
            ++i;
        }
        while (i < 3) {
            this.addTextField(new GuiNpcTextField(i, this, this.o, this.guiLeft - 100, this.guiTop + 70 + i * 22, 180, 20, ""));
            this.addTextField(new GuiNpcTextField(i + 3, this, this.o, this.guiLeft + 84, this.guiTop + 70 + i * 22, 30, 20, "1"));
            this.getTextField((int)(i + 3)).numbersOnly = true;
            this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
            ++i;
        }
        Map var10 = nt.b;
        ArrayList<String> var11 = new ArrayList<String>();
        for (Object name : var10.keySet()) {
            Class c = (Class)var10.get(name);
            try {
                if (!of.class.isAssignableFrom(c) || EntityNPCInterface.class.isAssignableFrom(c) || c.getConstructor(abw.class) == null || Modifier.isAbstract(c.getModifiers())) continue;
                var11.add(name.toString());
            }
            catch (SecurityException var8) {
                var8.printStackTrace();
            }
            catch (NoSuchMethodException noSuchMethodException) {}
        }
        this.scroll = new GuiCustomScroll(this, 0);
        this.scroll.setList(var11);
        this.scroll.a(this.f, 350, 250);
        this.scroll.setSize(140, 190);
        this.scroll.guiLeft = this.guiLeft + 120;
        this.scroll.guiTop = this.guiTop + 14;
        this.addButton(new GuiNpcButton(0, this.guiLeft - 100, this.guiTop + 140, 98, 20, "gui.back"));
    }

    @Override
    public void a(int i, int j, float f) {
        super.a(i, j, f);
        this.scroll.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        super.a(guibutton);
        if (guibutton.g == 0) {
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        this.scroll.a(i, j, k);
    }

    @Override
    public void save() {
    }

    @Override
    public void unFocused(GuiNpcTextField guiNpcTextField) {
        if (guiNpcTextField.id < 3) {
            this.lastSelected = guiNpcTextField;
        }
        this.saveTargets();
    }

    private void saveTargets() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < 3; ++i) {
            String name = this.getTextField(i).b();
            if (name.isEmpty()) continue;
            map.put(name, this.getTextField(i + 3).getInteger());
        }
        this.quest.targets = map;
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        if (this.lastSelected != null) {
            this.lastSelected.a(guiCustomScroll.getSelected());
            this.saveTargets();
        }
    }
}

