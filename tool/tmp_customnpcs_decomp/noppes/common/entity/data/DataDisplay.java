/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package noppes.common.entity.data;

import java.util.Random;
import noppes.common.constants.EnumModelType;
import noppes.common.entity.EntityNPCInterface;

public class DataDisplay {
    public String name;
    public boolean usingSkinUrl = false;
    public String skinUsername = "";
    public String texture = "customnpcs:textures/entity/humanmale/Steve.png";
    public String cloakTexture = "";
    public String glowTexture = "";
    public int visible = 0;
    public EnumModelType modelType = EnumModelType.HumanMale;
    public int modelSize = 5;
    public int showName = 0;
    public int skinColor = 0xFFFFFF;
    public boolean NoLivingAnimation = false;
    EntityNPCInterface npc;

    public DataDisplay(EntityNPCInterface npc) {
        this.npc = npc;
        String[] names = new String[]{"a", "a_craft", "a", "a_craft", "a", "a_craft", "AT", "RC_diamond_GH", "ssdsaad", "Orca", "otto", "Jackylove", "Ming", "Ning", "Dopa", "TheShy", "ruo", "XIye", "MLXG", "BSFC", "xiaohu"};
        this.name = names[new Random().nextInt(names.length)];
    }

    public by writeToNBT(by nbttagcompound) {
        nbttagcompound.a("Name", this.name);
        nbttagcompound.a("SkinUsername", this.skinUsername);
        nbttagcompound.a("Texture", this.texture);
        nbttagcompound.a("CloakTexture", this.cloakTexture);
        nbttagcompound.a("GlowTexture", this.glowTexture);
        nbttagcompound.a("UsingSkinUrl", this.usingSkinUrl);
        nbttagcompound.a("ModelType", this.modelType.ordinal());
        nbttagcompound.a("Size", this.modelSize);
        nbttagcompound.a("ShowName", this.showName);
        nbttagcompound.a("SkinColor", this.skinColor);
        nbttagcompound.a("NpcVisible", this.visible);
        nbttagcompound.a("NoLivingAnimation", this.NoLivingAnimation);
        return nbttagcompound;
    }

    public void readToNBT(by nbttagcompound) {
        this.name = nbttagcompound.i("Name");
        this.skinUsername = nbttagcompound.i("SkinUsername");
        this.texture = nbttagcompound.i("Texture");
        this.cloakTexture = nbttagcompound.i("CloakTexture");
        this.glowTexture = nbttagcompound.i("GlowTexture");
        this.usingSkinUrl = nbttagcompound.n("UsingSkinUrl");
        this.modelType = EnumModelType.values()[nbttagcompound.e("ModelType") % EnumModelType.values().length];
        this.modelSize = nbttagcompound.e("Size");
        this.showName = nbttagcompound.e("ShowName");
        this.skinColor = nbttagcompound.e("SkinColor");
        this.visible = nbttagcompound.e("NpcVisible");
        this.NoLivingAnimation = nbttagcompound.n("NoLivingAnimation");
    }

    public boolean showName() {
        return !this.npc.isKilled() && (this.showName == 0 || this.showName == 2 && this.npc.isAttacking());
    }
}

