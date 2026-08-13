/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ci
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.DebugGadgets
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  jv
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import noppes.common.controllers.Bank;
import noppes.common.controllers.BankController;
import noppes.util.NoppesUtilServer;

public class EditBankPackets {

    public static class BankRemovePacket
    extends RCsPacket {
        private int bankId;

        public BankRemovePacket(int bankId) {
            this.bankId = bankId;
        }

        public BankRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.bankId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.bankId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            BankController.getInstance().removeBank(this.bankId);
            NoppesUtilServer.sendBankDataAll((jv)player);
            NoppesUtilServer.sendBank((jv)player, new Bank());
        }
    }

    public static class BankSavePacket
    extends RCsPacket {
        private by nbt;

        public BankSavePacket(by nbt) {
            this.nbt = nbt;
        }

        public BankSavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Bank bank = new Bank();
            bank.readEntityFromNBT(this.nbt);
            BankController.getInstance().saveBank(bank);
            NoppesUtilServer.sendBankDataAll((jv)player);
            NoppesUtilServer.sendBank((jv)player, bank);
        }
    }

    public static class BankGetPacket
    extends RCsPacket {
        private int bankId;

        public BankGetPacket(int bankId) {
            this.bankId = bankId;
        }

        public BankGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.bankId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.bankId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Bank bank = BankController.getInstance().getBank(this.bankId);
            NoppesUtilServer.sendBank((jv)player, bank);
        }
    }

    public static class BanksGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendBankDataAll((jv)player);
        }
    }

    public static class BankPacket
    extends RCsPacket {
        private by nbt;

        public BankPacket(by nbt) {
            this.nbt = nbt;
        }

        public BankPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            try {
                byte[] data = ci.a((by)this.nbt);
                BankController.getInstance().loadBanks(new DataInputStream(new ByteArrayInputStream(data)));
                BankController.getInstance().saveBanks();
            }
            catch (IOException e) {
                DebugGadgets.debugInfo((String)e.getMessage());
            }
        }
    }
}

