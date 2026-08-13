/*
 * Decompiled with CFR 0.152.
 */
package noppes.client.gui.mainmenu;

import noppes.common.constants.EnumJobType;
import noppes.common.constants.EnumRoleType;

class GuiNpcAdvanced$1 {
    static final int[] $SwitchMap$noppes$npcs$constants$EnumRoleType;
    static final int[] $SwitchMap$noppes$npcs$constants$EnumJobType;

    GuiNpcAdvanced$1() {
    }

    static {
        $SwitchMap$noppes$npcs$constants$EnumJobType = new int[EnumJobType.values().length];
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumJobType[EnumJobType.Bard.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumJobType[EnumJobType.Healer.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumJobType[EnumJobType.Guard.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumJobType[EnumJobType.ItemGiver.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumJobType[EnumJobType.Boss.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumJobType[EnumJobType.Spawner.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumJobType[EnumJobType.Conversation.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$noppes$npcs$constants$EnumRoleType = new int[EnumRoleType.values().length];
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumRoleType[EnumRoleType.Trader.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumRoleType[EnumRoleType.Follower.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumRoleType[EnumRoleType.Bank.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GuiNpcAdvanced$1.$SwitchMap$noppes$npcs$constants$EnumRoleType[EnumRoleType.Transporter.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

