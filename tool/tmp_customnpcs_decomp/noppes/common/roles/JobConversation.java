/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  uf
 */
package noppes.common.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import noppes.common.controllers.Availability;
import noppes.common.controllers.Line;
import noppes.common.controllers.PlayerQuestController;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestController;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobInterface;

public class JobConversation
extends JobInterface {
    public Availability availability = new Availability();
    public HashMap<Integer, ConversationLine> lines = new HashMap();
    public int quest = -1;
    public String questTitle = "";
    public int generalDelay = 400;
    public int ticks = 100;
    public int range = 20;
    private final ArrayList<String> names = new ArrayList();
    private final HashMap<String, EntityNPCInterface> npcs = new HashMap();
    private ConversationLine nextLine;

    public JobConversation(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by compound) {
        compound.a("ConversationAvailability", this.availability.writeToNBT(new by()));
        compound.a("ConversationQuest", this.quest);
        compound.a("ConversationDelay", this.generalDelay);
        compound.a("ConversationRange", this.range);
        cg nbttaglist = new cg();
        for (Integer integer : this.lines.keySet()) {
            int slot = integer;
            ConversationLine line = this.lines.get(slot);
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot);
            line.writeEntityToNBT(nbttagcompound);
            nbttaglist.a((cl)nbttagcompound);
        }
        compound.a("ConversationLines", (cl)nbttaglist);
        if (this.hasQuest()) {
            compound.a("ConversationQuestTitle", this.getQuest().title);
        }
    }

    @Override
    public void readEntityFromNBT(by compound) {
        this.names.clear();
        this.availability.readFromNBT(compound.l("ConversationAvailability"));
        this.quest = compound.e("ConversationQuest");
        this.generalDelay = compound.e("ConversationDelay");
        this.questTitle = compound.i("ConversationQuestTitle");
        this.range = compound.e("ConversationRange");
        cg nbttaglist = compound.m("ConversationLines");
        HashMap<Integer, ConversationLine> map = new HashMap<Integer, ConversationLine>();
        for (int i = 0; i < nbttaglist.c(); ++i) {
            by nbttagcompound = (by)nbttaglist.b(i);
            ConversationLine line = new ConversationLine(this);
            line.readEntityFromNBT(nbttagcompound);
            if (!line.npc.isEmpty() && !this.names.contains(line.npc.toLowerCase())) {
                this.names.add(line.npc.toLowerCase());
            }
            map.put(nbttagcompound.e("Slot"), line);
        }
        this.lines = map;
        this.ticks = this.generalDelay;
    }

    public boolean hasQuest() {
        return this.getQuest() != null;
    }

    public Quest getQuest() {
        return this.npc.q.I ? null : QuestController.instance.quests.get(this.quest);
    }

    @Override
    public void aiUpdateTask() {
        --this.ticks;
        if (this.ticks <= 0 && this.nextLine != null) {
            this.say(this.nextLine);
            boolean seenNext = false;
            ConversationLine compare = this.nextLine;
            this.nextLine = null;
            for (ConversationLine line : this.lines.values()) {
                if (line.isEmpty()) continue;
                if (seenNext) {
                    this.nextLine = line;
                    break;
                }
                if (line != compare) continue;
                seenNext = true;
            }
            if (this.nextLine != null) {
                this.ticks = this.nextLine.delay;
            } else if (this.hasQuest()) {
                List playerList = this.npc.q.a(uf.class, this.npc.E.b((double)this.range, (double)this.range, (double)this.range));
                for (uf player : playerList) {
                    if (!this.availability.isAvailable(player)) continue;
                    PlayerQuestController.addActiveQuest(this.getQuest(), player);
                }
            }
        }
    }

    @Override
    public boolean aiShouldExecute() {
        if (!this.lines.isEmpty() && !this.npc.isKilled() && !this.npc.isAttacking() && this.shouldRun()) {
            for (ConversationLine line : this.lines.values()) {
                if (line == null || line.isEmpty()) continue;
                this.nextLine = line;
                break;
            }
            return this.nextLine != null;
        }
        return false;
    }

    private boolean shouldRun() {
        --this.ticks;
        if (this.ticks > 0) {
            return false;
        }
        this.npcs.clear();
        List list = this.npc.q.a(EntityNPCInterface.class, this.npc.E.b(10.0, 20.0, 10.0));
        for (EntityNPCInterface npc : list) {
            if (npc.isKilled() || npc.isAttacking() || !this.names.contains(npc.an().toLowerCase())) continue;
            this.npcs.put(npc.an().toLowerCase(), npc);
        }
        return this.names.size() == this.npcs.size();
    }

    @Override
    public boolean aiContinueExecute() {
        EntityNPCInterface npc;
        Iterator<EntityNPCInterface> npcIterator = this.npcs.values().iterator();
        do {
            if (npcIterator.hasNext()) continue;
            return this.nextLine != null;
        } while (!(npc = npcIterator.next()).isKilled() && !npc.isAttacking());
        return false;
    }

    @Override
    public void resetTask() {
        this.nextLine = null;
        this.ticks = this.generalDelay;
    }

    private void say(ConversationLine line) {
        List inRange = this.npc.q.a(uf.class, this.npc.E.b(20.0, 20.0, 20.0));
        EntityNPCInterface npc = this.npcs.get(line.npc.toLowerCase());
        if (npc != null) {
            for (uf o : inRange) {
                if (!this.availability.isAvailable(o)) continue;
                npc.say(o, line);
            }
        }
    }

    @Override
    public void reset() {
        this.resetTask();
    }

    @Override
    public void killed() {
        this.reset();
    }

    public ConversationLine getLine(int slot) {
        if (this.lines.containsKey(slot)) {
            return this.lines.get(slot);
        }
        ConversationLine line = new ConversationLine(this);
        this.lines.put(slot, line);
        return line;
    }

    public static class ConversationLine
    extends Line {
        final JobConversation this$0;
        public String npc;
        public int delay;

        public ConversationLine(JobConversation var1) {
            this.this$0 = var1;
            this.npc = "";
            this.delay = 40;
        }

        public void writeEntityToNBT(by compound) {
            compound.a("Line", this.text);
            compound.a("Npc", this.npc);
            compound.a("Sound", this.sound);
            compound.a("Delay", this.delay);
        }

        public void readEntityFromNBT(by compound) {
            this.text = compound.i("Line");
            this.npc = compound.i("Npc");
            this.sound = compound.i("Sound");
            this.delay = compound.e("Delay");
        }

        public boolean isEmpty() {
            return this.npc.isEmpty() || this.text.isEmpty();
        }
    }
}

