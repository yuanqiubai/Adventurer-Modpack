/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 */
package noppes.common.entity.data;

import java.util.ArrayList;
import java.util.List;
import noppes.common.constants.EnumAnimation;
import noppes.common.constants.EnumMovingType;
import noppes.common.constants.EnumNavType;
import noppes.common.constants.EnumStandingType;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NBTTags;

public class DataAI {
    public int onAttack = 0;
    public int doorInteract = 2;
    public int findShelter = 2;
    public int distanceToMelee = 4;
    public boolean canSwim = true;
    public boolean reactsToFire = false;
    public boolean avoidsWater = false;
    public boolean avoidsSun = false;
    public boolean returnToStart = true;
    public boolean directLOS = true;
    public boolean canSleep = false;
    public boolean canLeap = false;
    public boolean canFireIndirect = false;
    public boolean canSprint = false;
    public EnumNavType tacticalVariant = EnumNavType.Default;
    public int useRangeMelee = 0;
    public int tacticalRadius = 8;
    public EnumAnimation animationType = EnumAnimation.NONE;
    public EnumStandingType standingType = EnumStandingType.RotateBody;
    public EnumMovingType movingType = EnumMovingType.Standing;
    public int orientation = 0;
    public float bodyOffsetX = 5.0f;
    public float bodyOffsetY = 5.0f;
    public float bodyOffsetZ = 5.0f;
    public int walkingRange = 5;
    public int movingPattern = 0;
    public boolean movingPause = true;
    public int movingPos = 0;
    private final EntityNPCInterface npc;
    private List<int[]> movingPath = new ArrayList<int[]>();

    public DataAI(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public void readToNBT(by compound) {
        this.canSwim = compound.n("CanSwim");
        this.reactsToFire = compound.n("ReactsToFire");
        this.avoidsWater = compound.n("AvoidsWater");
        this.avoidsSun = compound.n("AvoidsSun");
        this.returnToStart = compound.n("ReturnToStart");
        this.onAttack = compound.e("OnAttack");
        this.doorInteract = compound.e("DoorInteract");
        this.findShelter = compound.e("FindShelter");
        this.directLOS = compound.n("DirectLOS");
        this.canSleep = compound.n("CanSleep");
        this.canLeap = compound.n("CanLeap");
        this.canSprint = compound.n("CanSprint");
        this.canFireIndirect = compound.n("CanFireIndirect");
        this.useRangeMelee = compound.e("RangeAndMelee");
        this.distanceToMelee = compound.e("DistanceToMelee");
        this.tacticalRadius = compound.e("TacticalRadius");
        this.movingPause = compound.n("MovingPause");
        this.animationType = EnumAnimation.values()[compound.e("MoveState") % EnumAnimation.values().length];
        this.standingType = EnumStandingType.values()[compound.e("StandingState") % EnumStandingType.values().length];
        this.movingType = EnumMovingType.values()[compound.e("MovingState") % EnumMovingType.values().length];
        this.tacticalVariant = EnumNavType.values()[compound.e("TacticalVariant") % EnumNavType.values().length];
        this.orientation = compound.e("Orientation");
        this.bodyOffsetY = compound.g("PositionOffsetY");
        this.bodyOffsetZ = compound.g("PositionOffsetZ");
        this.bodyOffsetX = compound.g("PositionOffsetX");
        this.walkingRange = compound.e("WalkingRange");
        this.setMovingPath(NBTTags.getIntegerArraySet(compound.m("MovingPath")));
        this.movingPos = compound.e("MovingPos");
        this.movingPattern = compound.e("MovingPatern");
        this.npc.updateTasks();
    }

    public by writeToNBT(by compound) {
        compound.a("CanSwim", this.canSwim);
        compound.a("ReactsToFire", this.reactsToFire);
        compound.a("AvoidsWater", this.avoidsWater);
        compound.a("AvoidsSun", this.avoidsSun);
        compound.a("ReturnToStart", this.returnToStart);
        compound.a("OnAttack", this.onAttack);
        compound.a("DoorInteract", this.doorInteract);
        compound.a("FindShelter", this.findShelter);
        compound.a("DirectLOS", this.directLOS);
        compound.a("CanSleep", this.canSleep);
        compound.a("CanLeap", this.canLeap);
        compound.a("CanSprint", this.canSprint);
        compound.a("CanFireIndirect", this.canFireIndirect);
        compound.a("RangeAndMelee", this.useRangeMelee);
        compound.a("DistanceToMelee", this.distanceToMelee);
        compound.a("TacticalRadius", this.tacticalRadius);
        compound.a("MovingPause", this.movingPause);
        compound.a("MoveState", this.animationType.ordinal());
        compound.a("StandingState", this.standingType.ordinal());
        compound.a("MovingState", this.movingType.ordinal());
        compound.a("TacticalVariant", this.tacticalVariant.ordinal());
        compound.a("Orientation", this.orientation);
        compound.a("PositionOffsetX", this.bodyOffsetX);
        compound.a("PositionOffsetY", this.bodyOffsetY);
        compound.a("PositionOffsetZ", this.bodyOffsetZ);
        compound.a("WalkingRange", this.walkingRange);
        compound.a("MovingPath", (cl)NBTTags.nbtIntegerArraySet(this.movingPath));
        compound.a("MovingPos", this.movingPos);
        compound.a("MovingPatern", this.movingPattern);
        this.npc.setAvoidWater(this.avoidsWater);
        return compound;
    }

    public List<int[]> getMovingPath() {
        if (this.movingPath.isEmpty()) {
            this.movingPath.add(this.npc.startPos);
        }
        return this.movingPath;
    }

    public void setMovingPath(List<int[]> list) {
        this.movingPath = list;
        if (!this.movingPath.isEmpty()) {
            this.npc.startPos = this.movingPath.get(0);
        }
    }

    public int[] getCurrentMovingPath() {
        List<int[]> list = this.getMovingPath();
        if (list.size() == 1) {
            this.movingPos = 0;
        } else if (this.movingPos >= list.size()) {
            if (this.movingPattern == 0) {
                this.movingPos = 0;
            } else {
                int size = list.size() * 2 - 2;
                if (this.movingPos >= size) {
                    this.movingPos = 0;
                } else if (this.movingPos >= list.size()) {
                    return list.get(list.size() - this.movingPos % list.size() - 2);
                }
            }
        }
        return list.get(this.movingPos);
    }

    public void incrementMovingPath() {
        List<int[]> list = this.getMovingPath();
        if (list.size() == 1) {
            this.movingPos = 0;
        } else if (this.movingPattern == 0) {
            ++this.movingPos;
            this.movingPos %= list.size();
        } else if (this.movingPattern == 1) {
            ++this.movingPos;
            int size = list.size() * 2 - 2;
            this.movingPos %= size;
        }
    }

    public void decreaseMovingPath() {
        List<int[]> list = this.getMovingPath();
        if (list.size() == 1) {
            this.movingPos = 0;
        } else if (this.movingPattern == 0) {
            --this.movingPos;
            if (this.movingPos < 0) {
                this.movingPos += list.size();
            }
        } else if (this.movingPattern == 1) {
            --this.movingPos;
            if (this.movingPos < 0) {
                int size = list.size() * 2 - 2;
                this.movingPos += size;
            }
        }
    }
}

