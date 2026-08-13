/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 */
package noppes.common.quests;

import java.util.Collection;
import java.util.List;
import noppes.common.controllers.Line;
import noppes.common.controllers.Lines;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.quests.ICompatibilty;
import noppes.util.NBTTags;

public class VersionCompatibility {
    public static int ModRev = 12;

    public static void CheckNpcCompatibility(EntityNPCInterface npc, by compound) {
        if (npc.npcVersion != ModRev) {
            VersionCompatibility.CompatabilityFix(compound, npc.advanced.writeToNBT(new by()));
            VersionCompatibility.CompatabilityFix(compound, npc.ai.writeToNBT(new by()));
            VersionCompatibility.CompatabilityFix(compound, npc.stats.writeToNBT(new by()));
            VersionCompatibility.CompatabilityFix(compound, npc.display.writeToNBT(new by()));
            VersionCompatibility.CompatabilityFix(compound, npc.inventory.writeEntityToNBT(new by()));
            if (npc.npcVersion < 5) {
                String interactLines = compound.i("Texture");
                interactLines = interactLines.replace("/mob/customnpcs/", "customnpcs:textures/entity/");
                interactLines = interactLines.replace("/mob/", "customnpcs:textures/entity/");
                compound.a("Texture", interactLines);
            }
            if (npc.npcVersion < 6 && compound.a("NpcInteractLines") instanceof cg) {
                List<String> var9 = NBTTags.getStringList(compound.m("NpcInteractLines"));
                Lines lines = new Lines();
                for (int worldLines = 0; worldLines < var9.size(); ++worldLines) {
                    Line attackLines = new Line();
                    attackLines.text = (String)var9.toArray()[worldLines];
                    lines.lines.put(worldLines, attackLines);
                }
                compound.a("NpcInteractLines", lines.writeToNBT());
                List<String> var10 = NBTTags.getStringList(compound.m("NpcLines"));
                lines = new Lines();
                for (int var11 = 0; var11 < var10.size(); ++var11) {
                    Line killedLines = new Line();
                    killedLines.text = (String)var10.toArray()[var11];
                    lines.lines.put(var11, killedLines);
                }
                compound.a("NpcLines", lines.writeToNBT());
                List<String> var12 = NBTTags.getStringList(compound.m("NpcAttackLines"));
                lines = new Lines();
                for (int var13 = 0; var13 < var12.size(); ++var13) {
                    Line i = new Line();
                    i.text = (String)var12.toArray()[var13];
                    lines.lines.put(var13, i);
                }
                compound.a("NpcAttackLines", lines.writeToNBT());
                List<String> var15 = NBTTags.getStringList(compound.m("NpcKilledLines"));
                lines = new Lines();
                for (int var14 = 0; var14 < var15.size(); ++var14) {
                    Line line = new Line();
                    line.text = (String)var15.toArray()[var14];
                    lines.lines.put(var14, line);
                }
                compound.a("NpcKilledLines", lines.writeToNBT());
            }
            npc.npcVersion = ModRev;
        }
    }

    public static void CheckAvailabilityCompatibility(ICompatibilty compatibilty, by compound) {
        if (compatibilty.getVersion() != ModRev) {
            VersionCompatibility.CompatabilityFix(compound, compatibilty.writeToNBT(new by()));
            compatibilty.setVersion(ModRev);
        }
    }

    public static void CompatabilityFix(by compound, by check) {
        Collection tags = check.c();
        for (cl nbt : tags) {
            if (!compound.b(nbt.e())) {
                compound.a(nbt.e(), nbt);
                continue;
            }
            if (!(nbt instanceof by) || !(compound.a(nbt.e()) instanceof by)) continue;
            VersionCompatibility.CompatabilityFix(compound.l(nbt.e()), (by)nbt);
        }
    }
}

