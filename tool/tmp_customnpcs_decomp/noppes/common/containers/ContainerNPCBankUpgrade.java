/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  uf
 */
package noppes.common.containers;

import noppes.common.containers.ContainerNPCBankInterface;

public class ContainerNPCBankUpgrade
extends ContainerNPCBankInterface {
    public ContainerNPCBankUpgrade(uf player, int slot, int bankid) {
        super(player, slot, bankid);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean canBeUpgraded() {
        return true;
    }

    @Override
    public int xOffset() {
        return 54;
    }

    @Override
    public int getRowNumber() {
        return 3;
    }
}

