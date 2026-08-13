/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 *  ye
 */
package noppes.common.items;

import noppes.common.items.MoneyBagContents$CoinType;

public class MoneyBagContents {
    private final uf player;
    private int[] coinData = new int[]{0, 0, 0, 0, 0, 0, 0};

    public MoneyBagContents(uf player) {
        this.player = player;
    }

    public void readNBT(by compound) {
        this.coinData = compound.k("coins");
    }

    public by writeNBT() {
        by compound = new by();
        compound.a("coins", this.coinData);
        return compound;
    }

    public void AddCurrency(MoneyBagContents$CoinType coinType, byte stackSize, ye theBag) {
        int n = coinType.ordinal();
        this.coinData[n] = this.coinData[n] + stackSize;
        theBag.e.a("contents", this.writeNBT());
    }

    public void WithdrawCurrencyByVal(MoneyBagContents$CoinType coinType, short amount, ye theBag) {
        int amtAdded = 0;
        int n = coinType.ordinal();
        this.coinData[n] = this.coinData[n] - amtAdded;
        theBag.e.a("contents", this.writeNBT());
    }

    public void WithdrawCurrencyByStack(MoneyBagContents$CoinType coinType, byte numStacks, ye theBag) {
        int amtAdded = 0;
        int n = coinType.ordinal();
        this.coinData[n] = this.coinData[n] - amtAdded;
        theBag.e.a("contents", this.writeNBT());
    }
}

