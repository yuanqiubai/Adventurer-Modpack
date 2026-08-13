/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  cg
 *  cl
 *  nn
 *  of
 */
package noppes.common.entity;

import java.util.HashMap;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.model_data.ModelPartConfig;
import noppes.common.entity.model_data.ModelPartData;

public class EntityCustomNpc
extends EntityNPCInterface {
    public nn renderEntity;
    public String renderEntityName = "";
    public ModelPartConfig arms = new ModelPartConfig();
    public ModelPartConfig body = new ModelPartConfig();
    public ModelPartConfig legs = new ModelPartConfig();
    public ModelPartConfig head = new ModelPartConfig();
    public ModelPartData legParts = new ModelPartData();
    public byte breasts = 0;
    public int animationStart;
    private HashMap<String, ModelPartData> parts = new HashMap();

    public EntityCustomNpc(abw world) {
        super(world);
    }

    @Override
    public void a(by compound) {
        super.a(compound);
        this.setRenderEntityName(compound.i("RenderEntityName"));
        this.arms.readFromNBT(compound.l("ArmsConfig"));
        this.body.readFromNBT(compound.l("BodyConfig"));
        this.legs.readFromNBT(compound.l("LegsConfig"));
        this.head.readFromNBT(compound.l("HeadConfig"));
        this.legParts.readFromNBT(compound.l("LegParts"));
        HashMap<String, ModelPartData> parts = new HashMap<String, ModelPartData>();
        cg list = compound.m("Parts");
        for (int i = 0; i < list.c(); ++i) {
            by item = (by)list.b(i);
            ModelPartData part = new ModelPartData();
            part.readFromNBT(item);
            parts.put(item.i("PartName"), part);
        }
        this.parts = parts;
        this.breasts = compound.c("Breasts");
    }

    @Override
    public void b(by compound) {
        super.b(compound);
        compound.a("RenderEntityName", this.renderEntityName);
        compound.a("ArmsConfig", this.arms.writeToNBT());
        compound.a("BodyConfig", this.body.writeToNBT());
        compound.a("LegsConfig", this.legs.writeToNBT());
        compound.a("HeadConfig", this.head.writeToNBT());
        compound.a("LegParts", this.legParts.writeToNBT());
        cg list = new cg();
        for (String name : this.parts.keySet()) {
            by item = this.parts.get(name).writeToNBT();
            item.a("PartName", name);
            list.a((cl)item);
        }
        compound.a("Parts", (cl)list);
        compound.a("Breasts", this.breasts);
    }

    private void setRenderEntityName(String name) {
        this.renderEntity = null;
        try {
            Class<?> e = Class.forName(name);
            if (of.class.isAssignableFrom(e)) {
                this.renderEntity = (nn)e.getConstructor(abw.class).newInstance(this.q);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public ModelPartData getPartData(String part) {
        return this.parts.get(part);
    }

    public float getBodyY() {
        return this.legParts.type == 3 ? (0.9f - this.body.scaleY) * 0.75f + this.getLegsY() : (1.0f - this.body.scaleY) * 0.75f + this.getLegsY();
    }

    public float getLegsY() {
        return this.legParts.type == 3 ? 0.87f - this.legs.scaleY : (1.0f - this.legs.scaleY) * 0.75f;
    }
}

