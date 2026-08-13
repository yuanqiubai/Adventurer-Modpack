/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  uf
 */
package noppes.common.controllers;

import java.util.HashMap;
import java.util.Iterator;
import noppes.common.constants.EnumOptionType;
import noppes.common.controllers.Availability;
import noppes.common.controllers.DialogCategory;
import noppes.common.controllers.DialogOption;
import noppes.common.controllers.FactionOptions;
import noppes.common.controllers.PlayerMail;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestController;
import noppes.common.quests.ICompatibilty;
import noppes.common.quests.VersionCompatibility;

public class Dialog
implements ICompatibilty {
    public int version = VersionCompatibility.ModRev;
    public int id = -1;
    public String title = "";
    public String text = "";
    public int quest = -1;
    public DialogCategory category;
    public HashMap<Integer, DialogOption> options = new HashMap();
    public Availability availability = new Availability();
    public FactionOptions factionOptions = new FactionOptions();
    public String sound;
    public String command = "";
    public PlayerMail mail = new PlayerMail();

    public boolean hasDialogs(uf player) {
        DialogOption option;
        Iterator<DialogOption> i$ = this.options.values().iterator();
        do {
            if (i$.hasNext()) continue;
            return false;
        } while ((option = i$.next()) == null || option.optionType != EnumOptionType.DialogOption || !option.hasDialog() || !option.isAvailable(player));
        return true;
    }

    public void readNBT(by compound) {
        this.version = compound.e("ModRev");
        VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
        this.id = compound.e("DialogId");
        this.title = compound.i("DialogTitle");
        this.text = compound.i("DialogText");
        this.quest = compound.e("DialogQuest");
        this.sound = compound.i("DialogSound");
        this.command = compound.i("DialogCommand");
        this.mail.readNBT(compound.l("DialogMail"));
        cg options = compound.m("Options");
        HashMap<Integer, DialogOption> newoptions = new HashMap<Integer, DialogOption>();
        for (int iii = 0; iii < options.c(); ++iii) {
            by option = (by)options.b(iii);
            int opslot = option.e("OptionSlot");
            DialogOption dia = new DialogOption();
            dia.readNBT(option.l("Option"));
            newoptions.put(opslot, dia);
        }
        this.options = newoptions;
        this.availability.readFromNBT(compound);
        this.factionOptions.readFromNBT(compound);
    }

    @Override
    public by writeToNBT(by compound) {
        compound.a("ModRev", this.version);
        compound.a("DialogId", this.id);
        compound.a("DialogTitle", this.title);
        compound.a("DialogText", this.text);
        compound.a("DialogQuest", this.quest);
        compound.a("DialogCommand", this.command);
        compound.a("DialogMail", this.mail.writeNBT());
        if (this.sound != null && !this.sound.isEmpty()) {
            compound.a("DialogSound", this.sound);
        }
        cg options = new cg();
        for (int opslot : this.options.keySet()) {
            by listcompound = new by();
            listcompound.a("OptionSlot", opslot);
            listcompound.a("Option", (cl)this.options.get(opslot).writeNBT());
            options.a((cl)listcompound);
        }
        compound.a("Options", (cl)options);
        this.availability.writeToNBT(compound);
        this.factionOptions.writeToNBT(compound);
        return compound;
    }

    public boolean hasQuest() {
        return this.getQuest() != null;
    }

    public Quest getQuest() {
        return QuestController.instance.quests.get(this.quest);
    }

    public boolean hasOtherOptions() {
        DialogOption option;
        Iterator<DialogOption> i$ = this.options.values().iterator();
        do {
            if (i$.hasNext()) continue;
            return false;
        } while ((option = i$.next()) == null || option.optionType == EnumOptionType.Disabled);
        return true;
    }

    public Dialog copy(uf player) {
        Dialog dialog = new Dialog();
        dialog.id = this.id;
        dialog.text = this.text;
        dialog.title = this.title;
        dialog.category = this.category;
        dialog.quest = this.quest;
        dialog.sound = this.sound;
        dialog.mail = this.mail;
        dialog.command = this.command;
        for (int slot : this.options.keySet()) {
            DialogOption option = this.options.get(slot);
            if (option.optionType == EnumOptionType.DialogOption && (!option.hasDialog() || !option.isAvailable(player))) continue;
            dialog.options.put(slot, option);
        }
        return dialog;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }
}

