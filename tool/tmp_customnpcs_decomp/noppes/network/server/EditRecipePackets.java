/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
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
import java.io.IOException;
import noppes.common.controllers.RecipeCarpentry;
import noppes.common.controllers.RecipeController;
import noppes.util.NoppesUtilServer;

public class EditRecipePackets {

    public static class RecipeSavePacket
    extends RCsPacket {
        private by nbt;

        public RecipeSavePacket(by nbt) {
            this.nbt = nbt;
        }

        public RecipeSavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            try {
                RecipeCarpentry recipe = RecipeController.instance.saveRecipe(this.nbt);
                NoppesUtilServer.sendRecipeData((jv)player, recipe.isGlobal ? 3 : 4);
                NoppesUtilServer.setRecipeGui((jv)player, recipe);
            }
            catch (IOException e) {
                DebugGadgets.debugInfo((String)e.getMessage());
            }
        }
    }

    public static class RecipeRemovePacket
    extends RCsPacket {
        private int recipeId;

        public RecipeRemovePacket(int recipeId) {
            this.recipeId = recipeId;
        }

        public RecipeRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.recipeId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.recipeId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            RecipeCarpentry recipe = RecipeController.instance.removeRecipe(this.recipeId);
            NoppesUtilServer.sendRecipeData((jv)player, recipe.isGlobal ? 3 : 4);
            NoppesUtilServer.setRecipeGui((jv)player, new RecipeCarpentry());
        }
    }

    public static class RecipeGetPacket
    extends RCsPacket {
        private int selected;

        public RecipeGetPacket(int selected) {
            this.selected = selected;
        }

        public RecipeGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.selected);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.selected = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            RecipeCarpentry recipe = RecipeController.instance.getRecipe(this.selected);
            NoppesUtilServer.setRecipeGui((jv)player, recipe);
        }
    }

    public static class RecipesGetPacket
    extends RCsPacket {
        private int width;

        public RecipesGetPacket(int width) {
            this.width = width;
        }

        public RecipesGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.width);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.width = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendRecipeData((jv)player, this.width);
        }
    }
}

