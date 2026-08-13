/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import java.util.HashMap;
import noppes.common.controllers.RecipeCarpentry;
import noppes.common.controllers.RecipeController;

public class SyncRecipesPacket
extends RCsPacket {
    private by nbt;

    public SyncRecipesPacket(by nbt) {
        this.nbt = nbt;
    }

    public SyncRecipesPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.nbt = Tools.readNBT((ByteArrayDataInput)in);
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        cg ls2 = this.nbt.m("recipes");
        HashMap<Integer, RecipeCarpentry> recipes = new HashMap<Integer, RecipeCarpentry>();
        if (ls2 == null) {
            return;
        }
        for (int i = 0; i < ls2.c(); ++i) {
            RecipeCarpentry mc = new RecipeCarpentry();
            mc.readNBT((by)ls2.b(i));
            recipes.put(mc.id, mc);
        }
        RecipeController.reloadGlobalRecipes(recipes);
    }
}

