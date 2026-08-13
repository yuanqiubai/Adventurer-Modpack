/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 */
package noppes.common.quests;

import java.util.Vector;

public abstract class QuestInterface {
    public int questId;

    public abstract void writeEntityToNBT(by var1);

    public abstract void readEntityFromNBT(by var1);

    public abstract boolean isCompleted(uf var1);

    public abstract void handleComplete(uf var1);

    public abstract Vector<String> getQuestLogStatus(uf var1);
}

