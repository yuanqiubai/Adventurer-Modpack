/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  nn
 *  uf
 *  ye
 */
package noppes.common.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.controllers.GlobalDataController;
import noppes.common.controllers.Line;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerItemGiverData;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobInterface;
import noppes.util.NBTTags;

public class JobItemGiver
extends JobInterface {
    public int cooldownType = 0;
    public int givingMethod = 0;
    public int cooldown = 10;
    public NpcMiscInventory inventory = new NpcMiscInventory(9);
    public int itemGiverId = 0;
    public List<String> lines = new ArrayList<String>();
    private int ticks = 10;
    private final List<uf> recentlyChecked = new ArrayList<uf>();
    private List<uf> toCheck;

    public JobItemGiver(EntityNPCInterface npc) {
        super(npc);
        this.lines.add("Have these items {player}");
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("igCooldownType", this.cooldownType);
        nbttagcompound.a("igGivingMethod", this.givingMethod);
        nbttagcompound.a("igCooldown", this.cooldown);
        nbttagcompound.a("ItemGiverId", this.itemGiverId);
        nbttagcompound.a("igLines", (cl)NBTTags.nbtStringList(this.lines));
        nbttagcompound.a("igJobInventory", this.inventory.getToNBT());
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.itemGiverId = nbttagcompound.e("ItemGiverId");
        this.cooldownType = nbttagcompound.e("igCooldownType");
        this.givingMethod = nbttagcompound.e("igGivingMethod");
        this.cooldown = nbttagcompound.e("igCooldown");
        this.lines = NBTTags.getStringList(nbttagcompound.m("igLines"));
        this.inventory.setFromNBT(nbttagcompound.l("igJobInventory"));
        if (this.itemGiverId == 0 && GlobalDataController.instance != null) {
            this.itemGiverId = GlobalDataController.instance.incrementItemGiverId();
        }
    }

    public cg newHashMapNBTList(HashMap<String, Long> lines) {
        cg nbttaglist = new cg();
        for (String s : lines.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Line", s);
            nbttagcompound.a("Time", lines.get(s).longValue());
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public HashMap<String, Long> getNBTLines(cg tagList) {
        HashMap<String, Long> map = new HashMap<String, Long>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            String line = nbttagcompound.i("Line");
            long time = nbttagcompound.f("Time");
            map.put(line, time);
        }
        return map;
    }

    private boolean giveItems(uf player) {
        PlayerItemGiverData data = PlayerDataController.instance.getPlayerData((uf)player).itemgiverData;
        if (!this.canPlayerInteract(data)) {
            return false;
        }
        System.out.println("test hmm");
        Vector<ye> items = new Vector<ye>();
        Vector<Object> toGive = new Vector<ye>();
        for (ye i : this.inventory.items.values()) {
            if (i == null) continue;
            items.add(i.m());
        }
        if (items.isEmpty()) {
            return false;
        }
        if (this.isAllGiver()) {
            toGive = items;
        } else if (this.isRemainingGiver()) {
            for (ye i : items) {
                if (this.playerHasItem(player, i.d)) continue;
                toGive.add(i);
            }
        } else if (this.isRandomGiver()) {
            toGive.add(((ye)items.get(this.npc.q.s.nextInt(items.size()))).m());
        } else if (this.isGiverWhenNotOwnedAny()) {
            boolean var9 = false;
            for (ye i$ : items) {
                if (!this.playerHasItem(player, i$.d)) continue;
                var9 = true;
                break;
            }
            if (var9) {
                return false;
            }
            toGive = items;
        } else if (this.isChainedGiver()) {
            int var11 = data.getItemIndex(this);
            int var13 = 0;
            for (ye item : this.inventory.items.values()) {
                if (var13 == var11) {
                    toGive.add(item.m());
                    break;
                }
                ++var13;
            }
        }
        if (this.givePlayerItems(player, toGive)) {
            if (!this.lines.isEmpty()) {
                this.npc.say(player, new Line(this.lines.get(this.npc.aD().nextInt(this.lines.size()))));
            }
            if (this.isDaily()) {
                data.setTime(this, this.getDay());
            } else {
                data.setTime(this, System.currentTimeMillis());
            }
            if (this.isChainedGiver()) {
                data.setItemIndex(this, (data.getItemIndex(this) + 1) % this.inventory.items.size());
            }
            return true;
        }
        return false;
    }

    private int getDay() {
        return (int)(this.npc.q.J() / 24000L);
    }

    private boolean canPlayerInteract(PlayerItemGiverData data) {
        return !this.inventory.items.isEmpty() && (this.isOnTimer() ? !data.hasInteractedBefore(this) || data.getTime(this) + (long)this.cooldown * 1000L < System.currentTimeMillis() : (this.isGiveOnce() ? !data.hasInteractedBefore(this) : this.isDaily() && (!data.hasInteractedBefore(this) || (long)this.getDay() > data.getTime(this))));
    }

    private boolean givePlayerItems(uf player, Vector<ye> toGive) {
        if (toGive.isEmpty()) {
            return false;
        }
        if (this.freeInventorySlots(player) < toGive.size()) {
            return false;
        }
        for (ye is : toGive) {
            this.npc.givePlayerItem(player, is);
        }
        return true;
    }

    private boolean playerHasItem(uf player, int id) {
        for (ye is : player.bn.a) {
            if (is == null || is.d != id) continue;
            return true;
        }
        for (ye is : player.bn.b) {
            if (is == null || is.d != id) continue;
            return true;
        }
        return false;
    }

    private int freeInventorySlots(uf player) {
        int i = 0;
        for (ye is : player.bn.a) {
            if (is != null) continue;
            ++i;
        }
        return i;
    }

    private boolean isRandomGiver() {
        return this.givingMethod == 0;
    }

    private boolean isAllGiver() {
        return this.givingMethod == 1;
    }

    private boolean isRemainingGiver() {
        return this.givingMethod == 2;
    }

    private boolean isGiverWhenNotOwnedAny() {
        return this.givingMethod == 3;
    }

    private boolean isChainedGiver() {
        return this.givingMethod == 4;
    }

    public boolean isOnTimer() {
        return this.cooldownType == 0;
    }

    private boolean isGiveOnce() {
        return this.cooldownType == 1;
    }

    private boolean isDaily() {
        return this.cooldownType == 2;
    }

    @Override
    public boolean aiShouldExecute() {
        --this.ticks;
        if (this.ticks <= 0 && !this.inventory.items.isEmpty()) {
            this.ticks = 20;
            this.toCheck = this.npc.q.a(uf.class, this.npc.E.b(3.0, 3.0, 3.0));
            this.toCheck.removeAll(this.recentlyChecked);
            List listMax = this.npc.q.a(uf.class, this.npc.E.b(10.0, 10.0, 10.0));
            this.recentlyChecked.retainAll(listMax);
            this.recentlyChecked.addAll(this.toCheck);
            return this.toCheck.size() > 0;
        }
        return false;
    }

    @Override
    public void aiStartExecuting() {
        for (uf player : this.toCheck) {
            if (!this.npc.o((nn)player)) continue;
            this.recentlyChecked.add(player);
            this.interact(player);
        }
    }

    @Override
    public void killed() {
    }

    private boolean interact(uf player) {
        if (!this.giveItems(player)) {
            this.npc.say(player, this.npc.advanced.getInteractLine());
        }
        return true;
    }

    @Override
    public void delete() {
    }
}

