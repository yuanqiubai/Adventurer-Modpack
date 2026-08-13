/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  uf
 */
package noppes.common.containers;

import noppes.common.containers.ContainerNPCBankInterface;

public class ContainerNPCBankLarge
extends ContainerNPCBankInterface {
    public ContainerNPCBankLarge(uf player, int slot, int bankid) {
        super(player, slot, bankid);
    }

    @Override
    public boolean isUpgraded() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int getRowNumber() {
        return 6;
    }
}

