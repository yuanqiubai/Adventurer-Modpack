/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bu
 *  by
 *  cl
 *  ls
 *  nn
 *  uf
 */
package noppes.common.roles;

import java.util.HashMap;
import noppes.common.constants.EnumGuiType;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleInterface;
import noppes.util.NBTTags;
import noppes.util.NoppesUtilServer;

public class RoleFollower
extends RoleInterface {
    public boolean isFollowing = true;
    public HashMap<Integer, Integer> rates = new HashMap();
    public NpcMiscInventory inventory = new NpcMiscInventory(3);
    public String dialogHire = bu.a((String)"follower.hireText") + " {days} " + bu.a((String)"follower.days");
    public String dialogFarewell = bu.a((String)"follower.farewellText") + " {player}";
    public int daysHired;
    public long hiredTime;
    public int updateTick = 0;
    private String owner;

    public RoleFollower(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("MercenaryDaysHired", this.daysHired);
        nbttagcompound.a("MercenaryHiredTime", this.hiredTime);
        nbttagcompound.a("MercenaryDialogHired", this.dialogHire);
        nbttagcompound.a("MercenaryDialogFarewell", this.dialogFarewell);
        if (this.owner != null && !this.owner.isEmpty()) {
            nbttagcompound.a("MercenaryOwner", this.owner);
        }
        nbttagcompound.a("MercenaryDayRates", (cl)NBTTags.nbtIntegerIntegerMap(this.rates));
        nbttagcompound.a("MercenaryInv", this.inventory.getToNBT());
        nbttagcompound.a("MercenaryIsFollowing", this.isFollowing);
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.owner = nbttagcompound.i("MercenaryOwner");
        this.daysHired = nbttagcompound.e("MercenaryDaysHired");
        this.hiredTime = nbttagcompound.f("MercenaryHiredTime");
        this.dialogHire = nbttagcompound.i("MercenaryDialogHired");
        this.dialogFarewell = nbttagcompound.i("MercenaryDialogFarewell");
        this.rates = NBTTags.getIntegerIntegerMap(nbttagcompound.m("MercenaryDayRates"));
        this.inventory.setFromNBT(nbttagcompound.l("MercenaryInv"));
        this.isFollowing = nbttagcompound.n("MercenaryIsFollowing");
    }

    @Override
    public boolean aiShouldExecute() {
        if (this.hasOwner() && this.isFollowing) {
            if (this.getDaysLeft() <= 0) {
                uf player = this.getOwner();
                if (player != null) {
                    player.a(this.dialogFarewell.replaceAll("\\{player\\}", player.bu));
                }
                this.killed();
                return false;
            }
            return this.npc.d((nn)this.getOwner()) >= 10.0f;
        }
        return false;
    }

    @Override
    public void aiUpdateTask() {
        ++this.updateTick;
        if (this.updateTick >= 10) {
            uf theOwner = this.getOwner();
            this.npc.h().a((nn)theOwner, 10.0f, (float)this.npc.bp());
            if (!this.npc.k().a((nn)theOwner, 1.0) && this.npc.e((nn)theOwner) >= 144.0) {
                int var1 = ls.c((double)theOwner.u) - 2;
                int var2 = ls.c((double)theOwner.w) - 2;
                int var3 = ls.c((double)theOwner.E.b);
                for (int var4 = 0; var4 <= 4; ++var4) {
                    for (int var5 = 0; var5 <= 4; ++var5) {
                        if (var4 >= 1 && var5 >= 1 && var4 <= 3 && var5 <= 3 || !this.npc.q.w(var1 + var4, var3 - 1, var2 + var5) || this.npc.q.u(var1 + var4, var3, var2 + var5) || this.npc.q.u(var1 + var4, var3 + 1, var2 + var5)) continue;
                        this.npc.b((float)(var1 + var4) + 0.5f, var3, (float)(var2 + var5) + 0.5f, this.npc.A, this.npc.B);
                        this.npc.k().h();
                        return;
                    }
                }
            }
            this.updateTick = 0;
        }
    }

    @Override
    public void aiStartExecuting() {
        this.updateTick = 10;
    }

    @Override
    public boolean aiContinueExecute() {
        uf owner = this.getOwner();
        return owner != null && !this.npc.k().g() && this.npc.d((nn)owner) > 2.0f && this.isFollowing;
    }

    public boolean isFollowing() {
        return this.isFollowing && this.getOwner() != null;
    }

    public uf getOwner() {
        return this.owner != null && !this.owner.isEmpty() ? this.npc.q.a(this.owner) : null;
    }

    public void setOwner(String username) {
        if (this.owner == null || !this.owner.equals(username)) {
            this.killed();
        }
        this.owner = username;
    }

    public boolean hasOwner() {
        return this.daysHired > 0 && this.getOwner() != null;
    }

    @Override
    public void killed() {
        this.owner = null;
        this.daysHired = 0;
        this.hiredTime = 0L;
    }

    public int getDaysLeft() {
        if (this.daysHired <= 0) {
            return 0;
        }
        int days = (int)((this.npc.q.J() - this.hiredTime) / 24000L);
        return this.daysHired - days;
    }

    public void addDays(int days) {
        this.daysHired = days + this.getDaysLeft();
        this.hiredTime = this.npc.q.J();
    }

    @Override
    public boolean interact(uf player) {
        if (this.owner != null && !this.owner.isEmpty()) {
            if (player == this.getOwner()) {
                NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollower, this.npc);
            }
        } else {
            this.npc.say(player, this.npc.advanced.getInteractLine());
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollowerHire, this.npc);
        }
        return false;
    }

    @Override
    public void delete() {
    }
}

