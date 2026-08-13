/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package noppes.common.entity.data;

import noppes.common.constants.EnumJobType;
import noppes.common.constants.EnumRoleType;
import noppes.common.controllers.FactionOptions;
import noppes.common.controllers.Line;
import noppes.common.controllers.Lines;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobBard;
import noppes.common.roles.JobBoss;
import noppes.common.roles.JobConversation;
import noppes.common.roles.JobGuard;
import noppes.common.roles.JobHealer;
import noppes.common.roles.JobItemGiver;
import noppes.common.roles.JobSpawner;
import noppes.common.roles.RoleBank;
import noppes.common.roles.RoleFollower;
import noppes.common.roles.RolePostman;
import noppes.common.roles.RoleTrader;
import noppes.common.roles.RoleTransporter;

public class DataAdvanced {
    public Lines interactLines = new Lines();
    public Lines worldLines = new Lines();
    public Lines attackLines = new Lines();
    public Lines killedLines = new Lines();
    public String idleSound = "";
    public String angrySound = "";
    public String hurtSound = "damage.hit";
    public String deathSound = "damage.hit";
    public String stepSound = "";
    public FactionOptions factions = new FactionOptions();
    public EnumRoleType role = EnumRoleType.None;
    public EnumJobType job = EnumJobType.None;
    public boolean attackOtherFactions = false;
    public boolean defendFaction = false;
    private final EntityNPCInterface npc;

    public DataAdvanced(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public by writeToNBT(by compound) {
        compound.a("NpcLines", this.worldLines.writeToNBT());
        compound.a("NpcKilledLines", this.killedLines.writeToNBT());
        compound.a("NpcInteractLines", this.interactLines.writeToNBT());
        compound.a("NpcAttackLines", this.attackLines.writeToNBT());
        compound.a("NpcIdleSound", this.idleSound);
        compound.a("NpcAngrySound", this.angrySound);
        compound.a("NpcHurtSound", this.hurtSound);
        compound.a("NpcDeathSound", this.deathSound);
        compound.a("NpcStepSound", this.stepSound);
        compound.a("FactionID", this.npc.getFaction().id);
        compound.a("AttackOtherFactions", this.attackOtherFactions);
        compound.a("DefendFaction", this.defendFaction);
        compound.a("Role", this.role.ordinal());
        compound.a("NpcJob", this.job.ordinal());
        compound.a("FactionPoints", this.factions.writeToNBT(new by()));
        if (this.role != EnumRoleType.None && this.npc.roleInterface != null) {
            this.npc.roleInterface.writeEntityToNBT(compound);
        }
        if (this.job != EnumJobType.None && this.npc.jobInterface != null) {
            this.npc.jobInterface.writeEntityToNBT(compound);
        }
        return compound;
    }

    public void readToNBT(by compound) {
        this.interactLines.readNBT(compound.l("NpcInteractLines"));
        this.worldLines.readNBT(compound.l("NpcLines"));
        this.attackLines.readNBT(compound.l("NpcAttackLines"));
        this.killedLines.readNBT(compound.l("NpcKilledLines"));
        this.idleSound = compound.i("NpcIdleSound");
        this.angrySound = compound.i("NpcAngrySound");
        this.hurtSound = compound.i("NpcHurtSound");
        this.deathSound = compound.i("NpcDeathSound");
        this.stepSound = compound.i("NpcStepSound");
        this.npc.setFaction(compound.e("FactionID"));
        this.attackOtherFactions = compound.n("AttackOtherFactions");
        this.defendFaction = compound.n("DefendFaction");
        this.setRole(compound.e("Role"));
        this.setJob(compound.e("NpcJob"));
        this.factions.readFromNBT(compound.l("FactionPoints"));
        if (this.role != EnumRoleType.None && this.npc.roleInterface != null) {
            this.npc.roleInterface.readEntityFromNBT(compound);
        }
        if (this.job != EnumJobType.None && this.npc.jobInterface != null) {
            this.npc.jobInterface.readEntityFromNBT(compound);
        }
    }

    public Line getInteractLine() {
        return this.interactLines.getLine();
    }

    public Line getAttackLine() {
        return this.attackLines.getLine();
    }

    public Line getKilledLine() {
        return this.killedLines.getLine();
    }

    public Line getWorldLine() {
        return this.worldLines.getLine();
    }

    public void setRole(int i) {
        if (EnumRoleType.values().length <= i) {
            i -= 2;
        }
        this.role = EnumRoleType.values()[i];
        if (this.role == EnumRoleType.None) {
            this.npc.roleInterface = null;
        } else if (this.role == EnumRoleType.Trader && !(this.npc.roleInterface instanceof RoleTrader)) {
            this.npc.roleInterface = new RoleTrader(this.npc);
        } else if (this.role == EnumRoleType.Follower && !(this.npc.roleInterface instanceof RoleFollower)) {
            this.npc.roleInterface = new RoleFollower(this.npc);
        } else if (this.role == EnumRoleType.Bank && !(this.npc.roleInterface instanceof RoleBank)) {
            this.npc.roleInterface = new RoleBank(this.npc);
        } else if (this.role == EnumRoleType.Transporter && !(this.npc.roleInterface instanceof RoleTransporter)) {
            this.npc.roleInterface = new RoleTransporter(this.npc);
        } else if (this.role == EnumRoleType.Postman && !(this.npc.roleInterface instanceof RolePostman)) {
            this.npc.roleInterface = new RolePostman(this.npc);
        }
    }

    public void setJob(int i) {
        this.job = EnumJobType.values()[i % EnumJobType.values().length];
        if (this.job == EnumJobType.None) {
            this.npc.jobInterface = null;
        } else if (this.job == EnumJobType.Bard && !(this.npc.jobInterface instanceof JobBard)) {
            this.npc.jobInterface = new JobBard(this.npc);
        } else if (this.job == EnumJobType.Healer && !(this.npc.jobInterface instanceof JobHealer)) {
            this.npc.jobInterface = new JobHealer(this.npc);
        } else if (this.job == EnumJobType.Guard && !(this.npc.jobInterface instanceof JobGuard)) {
            this.npc.jobInterface = new JobGuard(this.npc);
        } else if (this.job == EnumJobType.ItemGiver && !(this.npc.jobInterface instanceof JobItemGiver)) {
            this.npc.jobInterface = new JobItemGiver(this.npc);
        } else if (this.job == EnumJobType.Boss && !(this.npc.jobInterface instanceof JobBoss)) {
            this.npc.jobInterface = new JobBoss(this.npc);
        } else if (this.job == EnumJobType.Spawner && !(this.npc.jobInterface instanceof JobSpawner)) {
            this.npc.jobInterface = new JobSpawner(this.npc);
        } else if (this.job == EnumJobType.Conversation && !(this.npc.jobInterface instanceof JobConversation)) {
            this.npc.jobInterface = new JobConversation(this.npc);
        }
    }

    public boolean hasWorldLines() {
        return !this.worldLines.isEmpty();
    }
}

