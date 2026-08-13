/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  ad
 *  aqz
 *  ard
 *  asx
 *  by
 *  cg
 *  ci
 *  cl
 *  com.adventurer_engine.common.data.globaldata.IGlobalData
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
 *  cv
 *  ey
 *  ls
 *  ms
 *  nb
 *  ni
 *  nj
 *  nn
 *  of
 *  og
 *  oj
 *  on
 *  ps
 *  pt
 *  pu
 *  pw
 *  qa
 *  qi
 *  qp
 *  qx
 *  ro
 *  sg
 *  ss
 *  t
 *  tm
 *  to
 *  tp
 *  tw
 *  uf
 *  uh
 *  ye
 */
package noppes.common.entity;

import com.adventurer_engine.common.data.globaldata.IGlobalData;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import noppes.CustomNpcs;
import noppes.common.config.CustomNPCConfig;
import noppes.common.constants.EnumAnimation;
import noppes.common.constants.EnumJobType;
import noppes.common.constants.EnumModelType;
import noppes.common.constants.EnumMovingType;
import noppes.common.constants.EnumPotionType;
import noppes.common.constants.EnumRoleType;
import noppes.common.constants.EnumStandingType;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogOption;
import noppes.common.controllers.Faction;
import noppes.common.controllers.FactionController;
import noppes.common.controllers.Line;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.QuestData;
import noppes.common.entity.EntityProjectile;
import noppes.common.entity.NpcDamageSource;
import noppes.common.entity.ai.AttackSelectorNPC;
import noppes.common.entity.ai.EntityAIAmbushTarget;
import noppes.common.entity.ai.EntityAIAnimation;
import noppes.common.entity.ai.EntityAIAttackTarget;
import noppes.common.entity.ai.EntityAIAvoidTarget;
import noppes.common.entity.ai.EntityAIBustDoor;
import noppes.common.entity.ai.EntityAIClosestTarget;
import noppes.common.entity.ai.EntityAIDodgeShoot;
import noppes.common.entity.ai.EntityAIFindShade;
import noppes.common.entity.ai.EntityAIJob;
import noppes.common.entity.ai.EntityAILook;
import noppes.common.entity.ai.EntityAIMoveIndoors;
import noppes.common.entity.ai.EntityAIMovingPath;
import noppes.common.entity.ai.EntityAIOccupyBed;
import noppes.common.entity.ai.EntityAIOrbitTarget;
import noppes.common.entity.ai.EntityAIPanic;
import noppes.common.entity.ai.EntityAIRangedAttack;
import noppes.common.entity.ai.EntityAIReturn;
import noppes.common.entity.ai.EntityAIRole;
import noppes.common.entity.ai.EntityAISprintToTarget;
import noppes.common.entity.ai.EntityAIStalkTarget;
import noppes.common.entity.ai.EntityAIWander;
import noppes.common.entity.ai.EntityAIWatchClosest;
import noppes.common.entity.ai.EntityAIWaterNav;
import noppes.common.entity.ai.EntityAIWorldLines;
import noppes.common.entity.ai.EntityAIZigZagTarget;
import noppes.common.entity.data.DataAI;
import noppes.common.entity.data.DataAdvanced;
import noppes.common.entity.data.DataDisplay;
import noppes.common.entity.data.DataInventory;
import noppes.common.entity.data.DataStats;
import noppes.common.quests.VersionCompatibility;
import noppes.common.roles.JobBard;
import noppes.common.roles.JobBoss;
import noppes.common.roles.JobInterface;
import noppes.common.roles.RoleFollower;
import noppes.common.roles.RoleInterface;
import noppes.network.client.PlaySoundPacket;
import noppes.network.client.QuestCompletionPacket;
import noppes.util.NBTTags;
import noppes.util.NoppesStringUtils;
import noppes.util.NoppesUtilServer;

public class EntityNPCInterface
extends on
implements IEntityAdditionalSpawnData,
ad,
to,
sg {
    public DataDisplay display;
    public DataStats stats;
    public DataAI ai;
    public DataAdvanced advanced;
    public DataInventory inventory;
    public int[] startPos;
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    public float labelOffset;
    public RoleInterface roleInterface;
    public JobInterface jobInterface;
    public HashMap<Integer, DialogOption> dialogs;
    public boolean hasDied = false;
    public long killedtime = 0L;
    public Object textureLocation = null;
    public Object textureGlowLocation = null;
    public Object textureCloakLocation = null;
    public EnumAnimation currentAnimation = EnumAnimation.NONE;
    public int npcVersion = VersionCompatibility.ModRev;
    public double cloakX;
    public double cloakY;
    public double cloakZ;
    public double prev_cloakX;
    public double prev_cloakY;
    public double prev_cloakZ;
    private boolean wasKilled = false;
    private int taskCount = 1;
    private boolean isSleeping = false;
    private EntityAIRangedAttack aiRange;
    private int lastKnockback = 0;

    public EntityNPCInterface(abw world) {
        super(world);
        this.display = new DataDisplay(this);
        this.stats = new DataStats(this);
        this.ai = new DataAI(this);
        this.advanced = new DataAdvanced(this);
        this.inventory = new DataInventory(this);
        this.dialogs = new HashMap();
        this.advanced.interactLines.lines.put(0, new Line("Hello {player}"));
        this.b = 0;
        this.labelOffset = 0.0f;
        this.scaleZ = 0.9375f;
        this.scaleY = 0.9375f;
        this.scaleX = 0.9375f;
        this.updateHitbox();
        this.setFaction(this.getFaction().id);
        this.updateTasks();
    }

    protected void a() {
        super.a();
        this.ah.a(13, (Object)"");
        this.ah.a(14, (Object)0);
        this.ah.a(15, (Object)0);
        this.ah.a(23, (Object)0);
        this.ah.a(24, (Object)0);
    }

    protected boolean bf() {
        return true;
    }

    public boolean bH() {
        return false;
    }

    public boolean m(nn par1Entity) {
        boolean var4;
        float f = (float)this.a(tp.e).e();
        if (this.stats.attackSpeed < 10) {
            par1Entity.af = 0;
        }
        if ((var4 = par1Entity.a((nb)new NpcDamageSource("mob", (nn)this), f)) && this.stats.knockback > 0) {
            par1Entity.g((double)(-ls.a((float)(this.A * (float)Math.PI / 180.0f)) * (float)this.stats.knockback * 0.5f), 0.1, (double)(ls.b((float)(this.A * (float)Math.PI / 180.0f)) * (float)this.stats.knockback * 0.5f));
            this.x *= 0.6;
            this.z *= 0.6;
        }
        if (this.stats.potionType != EnumPotionType.None) {
            if (this.stats.potionType != EnumPotionType.Fire) {
                ((of)par1Entity).c(new nj(this.getPotionEffect(this.stats.potionType), this.stats.potionDuration * 20, this.stats.potionAmp));
            } else {
                par1Entity.d(this.stats.potionDuration);
            }
        }
        return var4;
    }

    public void c() {
        float f;
        if (CustomNpcs.FreezeNPCs) {
            return;
        }
        this.aW();
        if (this.lastKnockback != 0) {
            --this.lastKnockback;
        }
        if (!this.q.I) {
            if (!this.isKilled() && !this.isAttacking() && this.ac % 20 == 0) {
                if (this.stats.healthRegen && this.aN() < this.aT()) {
                    f = this.aT() / 20.0f;
                    this.f(f > 0.0f ? f : 1.0f);
                }
                if (this.getFaction().getsAttacked) {
                    List f1 = this.q.a(tm.class, this.E.b(16.0, 16.0, 16.0));
                    for (tm mob : f1) {
                        if (mob.m() != null || !this.o((nn)mob)) continue;
                        if (mob instanceof tw && !mob.getEntityData().b("AttackNpcs")) {
                            mob.c.a(2, (ps)new qa((on)mob, of.class, 1.0, false));
                            mob.getEntityData().a("AttackNpcs", true);
                        }
                        mob.d((of)this);
                        break;
                    }
                }
            }
            if (this.aN() <= 0.0f) {
                this.aK();
                this.ah.b(24, (Object)1);
            }
            this.ah.b(23, (Object)((byte)(this.m() != null ? 1 : 0)));
            this.ah.b(15, (Object)(this.k().g() ? 0 : 1));
        }
        if (this.wasKilled != this.isKilled() && this.wasKilled) {
            this.reset();
        }
        this.wasKilled = this.isKilled();
        if (this.q.v() && !this.q.I && this.stats.burnInSun && (f = this.d(1.0f)) > 0.5f && this.ab.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && this.q.l(ls.c((double)this.u), ls.c((double)this.v), ls.c((double)this.w))) {
            this.d(8);
        }
        super.c();
        if (this.q.I) {
            if (!this.display.cloakTexture.isEmpty()) {
                this.cloakUpdate();
            }
            if (this.currentAnimation.ordinal() != this.ah.c(14)) {
                this.currentAnimation = EnumAnimation.values()[this.ah.c(14)];
                this.updateHitbox();
            }
            if (this.advanced.job == EnumJobType.Bard) {
                ((JobBard)this.jobInterface).onLivingUpdate();
            }
        }
    }

    public boolean a(uf player) {
        if (this.q.I) {
            return false;
        }
        ye currentItem = player.bn.h();
        if (currentItem != null) {
            if (CustomNpcs.activeCloner(currentItem) || CustomNpcs.activeWand(currentItem)) {
                this.d((of)null);
                this.b(null);
                return true;
            }
            if (CustomNpcs.activeMoving(currentItem)) {
                this.d((of)null);
                if (currentItem.e == null) {
                    currentItem.e = new by();
                }
                currentItem.e.a("NPCID", this.k);
                player.a(cv.d((String)("Registered " + this.an() + " to your NPC Pather")));
                return true;
            }
        }
        if (!(this.isAttacking() || this.isKilled() || this.getFaction().isAggressiveToPlayer(player))) {
            Dialog dialog = this.getDialog(player);
            PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((uf)player).questData;
            QuestData data = playerdata.getQuestCompletion(player, this);
            if (data != null) {
                by quest_nbt = data.quest.writeToNBT(new by());
                PacketDispatcher.sendPacketToPlayer((ey)new QuestCompletionPacket(quest_nbt).makePacket("CNPCs Client"), (Player)((Player)player));
            } else if (dialog != null) {
                NoppesUtilServer.openDialog(player, this, dialog);
            } else if (this.roleInterface != null) {
                this.roleInterface.interact(player);
            } else {
                this.say(player, this.advanced.getInteractLine());
            }
            return true;
        }
        return false;
    }

    private Dialog getDialog(uf player) {
        for (DialogOption option : this.dialogs.values()) {
            if (option == null || !option.hasDialog()) continue;
            Dialog dialog = option.getDialog();
            if (!dialog.availability.isAvailable(player)) continue;
            return dialog;
        }
        return null;
    }

    public boolean a(nb damagesource, float i) {
        if (!(this.q.I || this.isKilled() || CustomNpcs.FreezeNPCs || damagesource.o.equals("inWall"))) {
            i = this.stats.resistances.applyResistance(damagesource, i);
            if (this.advanced.job == EnumJobType.Boss && ((JobBoss)this.jobInterface).applyDamage(i)) {
                return false;
            }
            nn entity = damagesource.i();
            of attackingEntity = null;
            if (entity instanceof of) {
                attackingEntity = (of)entity;
            }
            if (attackingEntity instanceof uf && this.getFaction().isFriendlyToPlayer((uf)attackingEntity)) {
                return false;
            }
            if (entity instanceof uh && ((uh)entity).c != null) {
                attackingEntity = (of)((uh)entity).c;
            }
            int faction = this.getFaction().id;
            if (attackingEntity instanceof EntityNPCInterface && ((EntityNPCInterface)attackingEntity).getFaction().id == faction) {
                return false;
            }
            if (attackingEntity == null) {
                return super.a(damagesource, i);
            }
            if (this.isAttacking()) {
                if (this.m() != null && this.e((nn)this.m()) > this.e((nn)attackingEntity)) {
                    this.d(attackingEntity);
                }
                return super.a(damagesource, i);
            }
            if (i > 0.0f) {
                List inRange = this.q.a(EntityNPCInterface.class, this.E.b(32.0, 16.0, 32.0));
                for (EntityNPCInterface npc : inRange) {
                    if (npc.isKilled() || !npc.advanced.defendFaction || npc.getFaction().id != faction || !npc.o((nn)this) && !npc.o((nn)attackingEntity)) continue;
                    npc.onAttack(attackingEntity);
                }
                this.d(attackingEntity);
            }
            return super.a(damagesource, i);
        }
        return false;
    }

    public void onAttack(of entity) {
        if (entity != null && entity != this && !this.isAttacking() && this.ai.onAttack != 3) {
            super.d(entity);
        }
    }

    public void d(of entity) {
        if (!(entity instanceof uf) || !((uf)entity).bG.a) {
            if (entity != null && entity != this && this.ai.onAttack != 3 && !this.isAttacking() && !this.isRemote()) {
                this.saySurrounding(this.advanced.getAttackLine());
            }
            super.d(entity);
        }
    }

    public void a(of entitylivingbase, float f) {
        ye proj = this.inventory.getProjectile();
        if (proj == null) {
            this.updateTasks();
        } else {
            EntityProjectile projectile = new EntityProjectile(this.q, (of)this, proj.m(), true);
            if (this.display.modelType == EnumModelType.Golem) {
                projectile.b(projectile.u, this.v + (double)1.45f, projectile.w, projectile.A, projectile.B);
            }
            double varX = entitylivingbase.u - this.u;
            double varY = entitylivingbase.E.b + (double)(entitylivingbase.P / 2.0f) - (this.v + (double)this.f());
            double varZ = entitylivingbase.w - this.w;
            float varF = projectile.hasGravity() ? ls.a((double)(varX * varX + varZ * varZ)) : 0.0f;
            float angle = projectile.getAngleForXYZ(varX, varY, varZ, varF, f == 1.0f && this.ai.canFireIndirect);
            float accuracy = 20.0f - (float)ls.d((float)((float)this.stats.accuracy / 5.0f));
            projectile.c(varX, varY, varZ, angle, accuracy);
            this.a(this.stats.fireSound, 2.0f, 1.0f);
            this.q.d((nn)projectile);
        }
    }

    private void clearTasks(pt tasks) {
        for (pu entry : tasks.a) {
            ps ai = entry.a;
            if (!tasks.b.contains(entry)) continue;
            ai.d();
        }
        tasks.a = new ArrayList();
        tasks.b = new ArrayList();
    }

    public void updateTasks() {
        if (this.q != null && !this.q.I) {
            this.clearTasks(this.c);
            this.clearTasks(this.d);
            AttackSelectorNPC attackEntitySelector = new AttackSelectorNPC(this);
            this.d.a(1, (ps)new qx((on)this, false));
            this.d.a(2, (ps)new EntityAIClosestTarget(this, of.class, 0, this.ai.directLOS, false, attackEntitySelector));
            this.c.a(0, (ps)new EntityAIWaterNav(this));
            this.taskCount = 1;
            this.doorInteractType();
            this.seekShelter();
            this.setResponse();
            this.setCanSleep();
            this.setMoveType();
            this.addRegularEntries();
        }
    }

    public void setResponse() {
        this.aiRange = null;
        if (this.ai.onAttack == 1) {
            this.c.a(this.taskCount++, (ps)new EntityAIPanic(this, 1.4f));
        } else if (this.ai.onAttack == 2) {
            this.c.a(this.taskCount++, (ps)new EntityAIAvoidTarget(this));
            this.setCanSprint();
        } else if (this.ai.onAttack == 0) {
            this.setCanLeap();
            this.setCanSprint();
            if (this.inventory.getProjectile() != null && this.ai.useRangeMelee != 2) {
                switch (this.ai.tacticalVariant) {
                    case Dodge: {
                        this.c.a(this.taskCount++, (ps)new EntityAIDodgeShoot(this));
                        break;
                    }
                    case Surround: {
                        this.c.a(this.taskCount++, (ps)new EntityAIOrbitTarget(this, 1.0, this.stats.rangedRange, false));
                        break;
                    }
                    case Ambush: {
                        this.c.a(this.taskCount++, (ps)new EntityAIAmbushTarget(this, 1.2, this.ai.tacticalRadius, false));
                        break;
                    }
                    case Stalk: {
                        this.c.a(this.taskCount++, (ps)new EntityAIStalkTarget(this, this.ai.tacticalRadius));
                    }
                }
            } else {
                switch (this.ai.tacticalVariant) {
                    case Dodge: {
                        this.c.a(this.taskCount++, (ps)new EntityAIZigZagTarget(this, 1.0, this.ai.tacticalRadius));
                        break;
                    }
                    case Surround: {
                        this.c.a(this.taskCount++, (ps)new EntityAIOrbitTarget(this, 1.0, this.ai.tacticalRadius, true));
                        break;
                    }
                    case Ambush: {
                        this.c.a(this.taskCount++, (ps)new EntityAIAmbushTarget(this, 1.2, this.ai.tacticalRadius, false));
                        break;
                    }
                    case Stalk: {
                        this.c.a(this.taskCount++, (ps)new EntityAIStalkTarget(this, this.ai.tacticalRadius));
                    }
                }
            }
            this.c.a(this.taskCount, (ps)new EntityAIAttackTarget(this, true));
            if (this.inventory.getProjectile() != null) {
                this.aiRange = new EntityAIRangedAttack(this);
                this.c.a(this.taskCount++, (ps)this.aiRange);
            }
        }
    }

    public void setMoveType() {
        if (this.ai.movingType == EnumMovingType.Wandering) {
            this.c.a(this.taskCount++, (ps)new EntityAIWander(this));
        }
        if (this.ai.movingType == EnumMovingType.MovingPath) {
            this.c.a(this.taskCount++, (ps)new EntityAIMovingPath(this));
        }
    }

    public void doorInteractType() {
        Object aiDoor = null;
        if (this.ai.doorInteract == 1) {
            aiDoor = new qi((og)this, true);
            this.c.a(this.taskCount++, (ps)aiDoor);
        } else if (this.ai.doorInteract == 0) {
            aiDoor = new EntityAIBustDoor((og)this);
            this.c.a(this.taskCount++, (ps)aiDoor);
        }
        this.k().b(aiDoor != null);
    }

    public void seekShelter() {
        if (this.ai.findShelter == 0) {
            this.c.a(this.taskCount++, (ps)new EntityAIMoveIndoors(this));
        } else if (this.ai.findShelter == 1) {
            this.c.a(this.taskCount++, (ps)new qp((on)this));
            this.c.a(this.taskCount++, (ps)new EntityAIFindShade(this));
        }
    }

    public void setCanSleep() {
        if (this.ai.canSleep) {
            this.c.a(this.taskCount++, (ps)new EntityAIOccupyBed(this));
        }
    }

    public void setCanLeap() {
        if (this.ai.canLeap) {
            this.c.a(this.taskCount++, (ps)new pw((og)this, 0.4f));
        }
    }

    public void setCanSprint() {
        if (this.ai.canSprint) {
            this.c.a(this.taskCount++, (ps)new EntityAISprintToTarget(this));
        }
    }

    public void addRegularEntries() {
        this.c.a(this.taskCount++, (ps)new EntityAIReturn(this));
        this.c.a(this.taskCount++, (ps)new EntityAILook(this));
        if (this.ai.standingType != EnumStandingType.NoRotation && this.ai.standingType != EnumStandingType.HeadRotation) {
            this.c.a(this.taskCount++, (ps)new EntityAIWatchClosest((og)this, og.class, 5.0f));
        }
        this.c.a(this.taskCount++, (ps)new EntityAIWorldLines(this));
        this.c.a(this.taskCount++, (ps)new EntityAIJob(this));
        this.c.a(this.taskCount++, (ps)new EntityAIRole(this));
        this.c.a(this.taskCount++, (ps)new EntityAIAnimation(this));
    }

    public float getSpeed() {
        return (float)this.stats.moveSpeed / 20.0f;
    }

    public float a(int par1, int par2, int par3) {
        return this.q.q(par1, par2, par3) - 0.5f;
    }

    private int getPotionEffect(EnumPotionType p) {
        switch (p) {
            case Poison: {
                return ni.u.H;
            }
            case Hunger: {
                return ni.s.H;
            }
            case Weakness: {
                return ni.t.H;
            }
            case Slowness: {
                return ni.d.H;
            }
            case Nausea: {
                return ni.k.H;
            }
            case Blindness: {
                return ni.q.H;
            }
            case Wither: {
                return ni.v.H;
            }
        }
        return 0;
    }

    protected int h(int par1) {
        return !this.stats.canDrown ? par1 : super.h(par1);
    }

    public oj aY() {
        return this.stats.creatureType;
    }

    public String an() {
        return this.display.name;
    }

    protected String r() {
        return this.T() ? (this.m() != null && !this.advanced.angrySound.equals("") ? this.advanced.angrySound : this.advanced.idleSound) : null;
    }

    protected String aO() {
        return this.advanced.hurtSound;
    }

    protected String aP() {
        return this.advanced.deathSound;
    }

    protected void a(int par1, int par2, int par3, int par4) {
        if (!this.advanced.stepSound.equals("")) {
            this.a(this.advanced.stepSound, 0.15f, 1.0f);
        } else {
            ard stepsound = aqz.s[par4].cS;
            if (this.q.a(par1, par2 + 1, par3) == aqz.aX.cF) {
                stepsound = aqz.aX.cS;
                this.a(stepsound.e(), stepsound.c() * 0.15f, stepsound.d());
            } else if (!aqz.s[par4].cU.d()) {
                this.a(stepsound.e(), stepsound.c() * 0.15f, stepsound.d());
            }
        }
    }

    public void saySurrounding(Line line) {
        if (line != null) {
            List inRange = this.q.a(uf.class, this.E.b(20.0, 20.0, 20.0));
            for (uf player : inRange) {
                this.say(player, line);
            }
        }
    }

    public void say(uf player, Line line) {
        if (line != null && this.o((nn)player)) {
            if (!line.sound.isEmpty()) {
                PacketDispatcher.sendPacketToPlayer((ey)new PlaySoundPacket(line.sound, (float)this.u, (float)this.v, (float)this.w).makePacket("CNPCs Client"), (Player)((Player)player));
            }
            String text = NoppesStringUtils.formatText(line.text, player.bu);
            player.a(this.an() + ": " + text);
        }
    }

    public void g(double d, double d1, double d2) {
        if (this.isWalking() && !this.isKilled()) {
            super.g(d, d1, d2);
        }
    }

    public void a(by compound) {
        super.a(compound);
        this.npcVersion = compound.e("ModRev");
        VersionCompatibility.CheckNpcCompatibility(this, compound);
        this.display.readToNBT(compound);
        this.stats.readToNBT(compound);
        this.ai.readToNBT(compound);
        this.advanced.readToNBT(compound);
        this.inventory.readEntityFromNBT(compound);
        this.killedtime = compound.f("KilledTime");
        this.startPos = NBTTags.getIntArray(compound.m("StartPos"));
        if (this.startPos == null || this.startPos.length != 3) {
            this.startPos = new int[]{ls.c((double)this.u), ls.c((double)this.v), ls.c((double)this.w)};
        }
        this.dialogs = this.getDialogs(compound.m("NPCDialogOptions"));
        this.textureLocation = null;
        this.textureGlowLocation = null;
        this.textureCloakLocation = null;
        this.updateTasks();
    }

    private HashMap<Integer, DialogOption> getDialogs(cg tagList) {
        HashMap<Integer, DialogOption> map = new HashMap<Integer, DialogOption>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            int slot = nbttagcompound.e("DialogSlot");
            DialogOption option = new DialogOption();
            option.readNBT(nbttagcompound.l("NPCDialog"));
            map.put(slot, option);
        }
        return map;
    }

    public void b(by compound) {
        super.b(compound);
        this.display.writeToNBT(compound);
        this.stats.writeToNBT(compound);
        this.ai.writeToNBT(compound);
        this.advanced.writeToNBT(compound);
        this.inventory.writeEntityToNBT(compound);
        compound.a("KilledTime", this.killedtime);
        if (this.startPos == null) {
            this.startPos = new int[]{ls.c((double)this.u), ls.c((double)this.v), ls.c((double)this.w)};
        }
        compound.a("StartPos", (cl)NBTTags.nbtIntArray(this.startPos));
        compound.a("NPCDialogOptions", (cl)this.nbtDialogs(this.dialogs));
        compound.a("ModRev", this.npcVersion);
        this.a(tp.a).a((double)this.stats.maxHealth);
        this.a(tp.b).a((double)CustomNPCConfig.NpcNavRange);
        this.a(tp.d).a((double)this.getSpeed());
        this.a(tp.e).a((double)this.stats.attackStrength);
    }

    private cg nbtDialogs(HashMap<Integer, DialogOption> dialogs2) {
        cg nbttaglist = new cg();
        for (Integer integer : dialogs2.keySet()) {
            int slot = integer;
            by nbttagcompound = new by();
            nbttagcompound.a("DialogSlot", slot);
            nbttagcompound.a("NPCDialog", dialogs2.get(slot).writeNBT());
            nbttaglist.a((cl)nbttagcompound);
        }
        return nbttaglist;
    }

    public void updateHitbox() {
        if (this.currentAnimation == EnumAnimation.LYING) {
            this.P = 0.2f;
            this.O = 0.2f;
        } else if (this.currentAnimation == EnumAnimation.SITTING) {
            this.O = 0.6f;
            this.P = 1.3f;
        } else {
            this.O = 0.6f;
            this.P = 1.8f;
        }
        this.O = this.O / 5.0f * (float)this.display.modelSize;
        this.P = this.P / 5.0f * (float)this.display.modelSize;
    }

    public void dropPlayerItemWithRandomChoice(ye itemstack, boolean flag) {
        if (itemstack != null) {
            ss entityitem = new ss(this.q, this.u, this.v - (double)0.3f + (double)this.f(), this.w, itemstack);
            entityitem.b = 40;
            if (flag) {
                entityitem.x = 0.0;
                entityitem.z = 0.0;
                entityitem.y = 0.2f;
            } else {
                float f1 = 0.3f;
                entityitem.x = -ls.a((float)(this.A / 180.0f * (float)Math.PI)) * ls.b((float)(this.B / 180.0f * (float)Math.PI)) * f1;
                entityitem.z = ls.b((float)(this.A / 180.0f * (float)Math.PI)) * ls.b((float)(this.B / 180.0f * (float)Math.PI)) * f1;
                entityitem.y = -ls.a((float)(this.B / 180.0f * (float)Math.PI)) * f1 + 0.1f;
                f1 = 0.02f;
                float f3 = this.ab.nextFloat() * (float)Math.PI * 2.0f;
                entityitem.x += Math.cos(f3) * (double)(f1 *= this.ab.nextFloat());
                entityitem.y += (double)((this.ab.nextFloat() - this.ab.nextFloat()) * 0.1f);
                entityitem.z += Math.sin(f3) * (double)f1;
            }
            this.q.d((nn)entityitem);
        }
    }

    public void aB() {
        if (this.stats.spawnCycle == 3) {
            super.aB();
        } else {
            ++this.aB;
            if (!this.q.I) {
                if (!this.hasDied) {
                    this.x();
                }
                boolean condition = this.stats.spawnCycle == 0 || this.q.v() && this.stats.spawnCycle == 1 || !this.q.v() && this.stats.spawnCycle == 2;
                long currentCalendar = IGlobalData.CALENDAR_DATA.getCalendarValue();
                if (currentCalendar - this.killedtime >= (long)this.stats.respawnTime * 20L && condition) {
                    this.reset();
                }
            }
        }
    }

    public void reset() {
        this.hasDied = false;
        this.g(this.aT());
        this.ah.b(24, (Object)0);
        this.ah.b(14, (Object)0);
        this.ah.b(15, (Object)0);
        this.d((of)null);
        this.b(null);
        this.aB = 0;
        if (this.startPos != null) {
            this.b(this.getStartXPos(), this.getStartYPos() + 1.0, this.getStartZPos(), this.A, this.B);
        }
        this.killedtime = 0L;
        this.B();
        this.aK();
        this.e(0.0f, 0.0f);
        this.R = 0.0f;
        this.k().h();
        this.currentAnimation = EnumAnimation.NONE;
        this.updateHitbox();
        this.updateTasks();
        this.ai.movingPos = 0;
        if (this.jobInterface != null) {
            this.jobInterface.reset();
        }
    }

    public void cloakUpdate() {
        this.cloakX = this.prev_cloakX;
        this.cloakY = this.prev_cloakY;
        this.cloakZ = this.prev_cloakZ;
        double deltaX = this.u - this.prev_cloakX;
        double deltaY = this.v - this.prev_cloakY;
        double deltaZ = this.w - this.prev_cloakZ;
        double d3 = 10.0;
        if (deltaX > d3) {
            this.cloakX = this.prev_cloakX = this.u;
        }
        if (deltaZ > d3) {
            this.cloakZ = this.prev_cloakZ = this.w;
        }
        if (deltaY > d3) {
            this.cloakY = this.prev_cloakY = this.v;
        }
        if (deltaX < -d3) {
            this.cloakX = this.prev_cloakX = this.u;
        }
        if (deltaZ < -d3) {
            this.cloakZ = this.prev_cloakZ = this.w;
        }
        if (deltaY < -d3) {
            this.cloakY = this.prev_cloakY = this.v;
        }
        this.prev_cloakX += deltaX * 0.25;
        this.prev_cloakZ += deltaZ * 0.25;
        this.prev_cloakY += deltaY * 0.25;
    }

    protected boolean t() {
        return false;
    }

    public ye aZ() {
        return this.isAttacking() ? this.inventory.getWeapon() : (this.jobInterface != null && this.jobInterface.overrideMainHand ? this.jobInterface.mainhand : this.inventory.getWeapon());
    }

    public ye getOffHand() {
        return this.isAttacking() ? this.inventory.getOffHand() : (this.jobInterface != null && this.jobInterface.overrideOffHand ? this.jobInterface.offhand : this.inventory.getOffHand());
    }

    public void a(nb par1DamageSource) {
        this.inventory.dropStuff(this.aT > 0);
        if (!this.isRemote()) {
            this.saySurrounding(this.advanced.getKilledLine());
        }
        super.a(par1DamageSource);
    }

    public void x() {
        this.hasDied = true;
        if (!this.q.I && this.stats.spawnCycle != 3) {
            this.g(-1.0f);
            long l = this.killedtime = this.killedtime == 0L ? IGlobalData.CALENDAR_DATA.getCalendarValue() : this.killedtime;
            if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
                this.roleInterface.killed();
            }
            if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
                this.jobInterface.killed();
            }
        } else {
            this.q();
            this.delete();
        }
    }

    public void delete() {
        if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
            this.roleInterface.delete();
        }
        if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
            this.jobInterface.delete();
        }
        super.x();
    }

    public float getStartXPos() {
        return (float)this.startPos[0] + this.ai.bodyOffsetX / 10.0f;
    }

    public float getStartZPos() {
        return (float)this.startPos[2] + this.ai.bodyOffsetZ / 10.0f;
    }

    public boolean isVeryNearAssignedPlace() {
        double xx = this.u - (double)this.getStartXPos();
        double zz = this.w - (double)this.getStartZPos();
        return xx >= -0.2 && xx <= 0.2 && zz >= -0.2 && zz <= 0.2;
    }

    public ms b(ye par1ItemStack, int par2) {
        uf player = CustomNpcs.proxy.getPlayer();
        return player == null ? super.b(par1ItemStack, par2) : player.b(par1ItemStack, par2);
    }

    public double getStartYPos() {
        int i = this.startPos[0];
        int j = this.startPos[1];
        int k = this.startPos[2];
        double yy = 0.0;
        for (int ii = j; ii >= 0; --ii) {
            aqz block;
            asx bb;
            int id = this.q.a(i, ii, k);
            if (id == 0 || (bb = (block = aqz.s[id]).b(this.q, i, ii, k)) == null) continue;
            yy = bb.e;
            break;
        }
        if (yy == 0.0) {
            this.x();
        }
        return yy += 0.5;
    }

    public void givePlayerItem(uf player, ye item) {
        if (!this.q.I) {
            item = item.m();
            float f = 0.7f;
            double d = (double)(this.q.s.nextFloat() * f) + (double)(1.0f - f);
            double d1 = (double)(this.q.s.nextFloat() * f) + (double)(1.0f - f);
            double d2 = (double)(this.q.s.nextFloat() * f) + (double)(1.0f - f);
            ss entityitem = new ss(this.q, this.u + d, this.v + d1, this.w + d2, item);
            entityitem.b = 2;
            this.q.d((nn)entityitem);
            int i = item.b;
            if (player.bn.a(item)) {
                this.q.a((nn)entityitem, "random.pop", 0.2f, ((this.ab.nextFloat() - this.ab.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                player.a((nn)entityitem, i);
                if (item.b <= 0) {
                    entityitem.x();
                }
            }
        }
    }

    public boolean bh() {
        return this.currentAnimation == EnumAnimation.LYING;
    }

    public boolean ag() {
        return this.currentAnimation == EnumAnimation.SITTING;
    }

    public boolean isWalking() {
        return this.ai.movingType != EnumMovingType.Standing || this.isAttacking() || this.isFollowerWithOwner() || this.ah.c(15) == 1;
    }

    public boolean ah() {
        return this.currentAnimation == EnumAnimation.SNEAKING;
    }

    public void a(nn entity, float power, double x, double z2) {
        if (this.lastKnockback > 0) {
            this.lastKnockback = 10;
            return;
        }
        this.lastKnockback = 10;
        this.an = true;
        float f1 = ls.a((double)(x * x + z2 * z2));
        float dk = 0.5f * (2.0f - this.stats.resistances.knockback);
        this.x /= 2.0;
        this.y /= 2.0;
        this.z /= 2.0;
        this.x -= x / (double)f1 * (double)dk;
        this.y += 0.2 + (double)(dk / 2.0f);
        this.z -= z2 / (double)f1 * (double)dk;
        if (this.y > (double)0.4f) {
            this.y = 0.4f;
        }
    }

    public Faction getFaction() {
        String[] split = this.ah.e(13).split(":");
        int faction = 0;
        if (!(this.q == null || split.length <= 1 && this.q.I)) {
            if (split.length > 1) {
                faction = Integer.parseInt(split[0]);
            }
            if (this.q.I) {
                Faction fac = new Faction();
                fac.id = faction;
                fac.color = Integer.parseInt(split[1]);
                fac.name = split[2];
                return fac;
            }
            Faction fac = FactionController.getInstance().getFaction(faction);
            if (fac == null) {
                faction = FactionController.getInstance().getFirstFactionId();
                fac = FactionController.getInstance().getFaction(faction);
            }
            return fac;
        }
        return new Faction();
    }

    public void setFaction(int integer) {
        Faction faction;
        if (integer >= 0 && !this.isRemote() && (faction = FactionController.getInstance().getFaction(integer)) != null) {
            String str = faction.id + ":" + faction.color + ":" + faction.name;
            if (str.length() > 64) {
                str = str.substring(0, 64);
            }
            this.ah.b(13, (Object)str);
        }
    }

    public boolean isRemote() {
        return this.q == null || this.q.I;
    }

    public boolean isFollowerWithOwner() {
        return this.advanced.role == EnumRoleType.Follower && ((RoleFollower)this.roleInterface).getDaysLeft() > 0;
    }

    public boolean d(nj par1PotionEffect) {
        return (this.aY() != oj.c || par1PotionEffect.a() != ni.u.H) && super.d(par1PotionEffect);
    }

    public by copy() {
        by nbttagcompound = new by();
        this.d(nbttagcompound);
        nbttagcompound.a("EntityId", this.k);
        return nbttagcompound;
    }

    public boolean inNormalState() {
        return !this.isAttacking() && !this.isFollowerWithOwner();
    }

    public boolean isAttacking() {
        return this.ah.a(23) == 1;
    }

    public boolean isKilled() {
        return this.ah.c(24) == 1;
    }

    public void writeSpawnData(ByteArrayDataOutput data) {
        try {
            ci.a((by)this.writeSpawnData(), (DataOutput)data);
        }
        catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public by writeSpawnData() {
        by compound = new by();
        this.display.writeToNBT(compound);
        compound.a("MaxHealth", this.stats.maxHealth);
        compound.a("Armor", (cl)NBTTags.nbtItemStackList(this.inventory.getArmor()));
        compound.a("Weapons", (cl)NBTTags.nbtItemStackList(this.inventory.getWeapons()));
        compound.a("Speed", this.stats.moveSpeed);
        compound.a("DeadBody", this.stats.hideKilledBody);
        compound.a("StandingState", this.ai.standingType.ordinal());
        compound.a("MovingState", this.ai.movingType.ordinal());
        compound.a("Orientation", this.ai.orientation);
        compound.a("Role", this.advanced.role.ordinal());
        compound.a("Job", this.advanced.job.ordinal());
        compound.a("bodyOffsetY", this.ai.bodyOffsetY);
        if (this.advanced.job == EnumJobType.Bard) {
            by bard = new by();
            this.jobInterface.writeEntityToNBT(bard);
            compound.a("Bard", (cl)bard);
        }
        if (this.advanced.job == EnumJobType.Boss) {
            compound.a("Boss", ((JobBoss)this.jobInterface).hideName);
        }
        return compound;
    }

    public void readSpawnData(ByteArrayDataInput data) {
        try {
            by e = ci.a((DataInput)data);
            this.readSpawnData(e);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void readSpawnData(by compound) {
        this.stats.maxHealth = compound.e("MaxHealth");
        this.stats.moveSpeed = compound.e("Speed");
        this.stats.hideKilledBody = compound.n("DeadBody");
        this.ai.standingType = EnumStandingType.values()[compound.e("StandingState") % EnumStandingType.values().length];
        this.ai.movingType = EnumMovingType.values()[compound.e("MovingState") % EnumMovingType.values().length];
        this.ai.orientation = compound.e("Orientation");
        this.ai.bodyOffsetY = compound.g("bodyOffsetY");
        this.a(tp.a).a((double)this.stats.maxHealth);
        this.inventory.setArmor(NBTTags.getItemStackList(compound.m("Armor")));
        this.inventory.setWeapons(NBTTags.getItemStackList(compound.m("Weapons")));
        this.advanced.setRole(compound.e("Role"));
        this.advanced.setJob(compound.e("Job"));
        if (this.advanced.job == EnumJobType.Bard) {
            by bard = compound.l("Bard");
            this.jobInterface.readEntityFromNBT(bard);
        }
        if (this.advanced.job == EnumJobType.Boss) {
            ((JobBoss)this.jobInterface).hideName = compound.n("Boss");
        }
        this.display.readToNBT(compound);
    }

    public String c_() {
        return this.display.name;
    }

    public boolean a(int var1, String var2) {
        return CustomNPCConfig.NpcUseOpCommands || var1 <= 2;
    }

    public t b() {
        return new t(ls.c((double)this.u), ls.c((double)this.v), ls.c((double)this.w));
    }

    public boolean a(Class par1Class) {
        return ro.class != par1Class;
    }

    public void setImmuneToFire(boolean immuneToFire) {
        this.ag = immuneToFire;
        this.stats.immuneToFire = immuneToFire;
    }

    public void setAvoidWater(boolean avoidWater) {
        this.k().a(avoidWater);
        this.ai.avoidsWater = avoidWater;
    }

    public boolean isSleeping() {
        return this.isSleeping;
    }

    public void setSleeping(boolean b) {
        this.isSleeping = b;
    }

    protected void b(float par1) {
        if (!this.stats.noFallDamage) {
            super.b(par1);
        }
    }

    public boolean L() {
        return !this.isKilled();
    }

    protected void az() {
        super.az();
        this.aX().b(tp.e);
    }

    public EntityAIRangedAttack getRangedTask() {
        return this.aiRange;
    }

    public void a(cv chatmessagecomponent) {
    }

    public ye o(int i) {
        return this.inventory.armorItemInSlot(i);
    }

    public void ab() {
    }

    public abw f_() {
        return this.q;
    }

    public boolean d(uf player) {
        return this.display.visible == 1 && !CustomNpcs.activeWand(player.aZ());
    }

    public boolean aj() {
        return this.display.visible != 0;
    }
}

