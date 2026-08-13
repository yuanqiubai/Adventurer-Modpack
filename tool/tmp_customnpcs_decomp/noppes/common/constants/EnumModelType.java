/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.constants;

import noppes.util.Tools;

public enum EnumModelType {
    HumanMale("Human Male", Tools.getEntityId(), "npchumanmale"),
    Villager("Villager", Tools.getEntityId(), "npcvillager"),
    Pony("Pony", Tools.getEntityId(), "npcpony"),
    HumanFemale("Human Female", Tools.getEntityId(), "npchumanfemale"),
    DwarfMale("Dwarf Male", Tools.getEntityId(), "npcdwarfmale"),
    FurryMale("Furry Male", Tools.getEntityId(), "npcfurrymale"),
    MonsterMale("Monster Male", Tools.getEntityId(), "npczombiemale"),
    MonsterFemale("Monster Female", Tools.getEntityId(), "npczombiefemale"),
    Skeleton("Skeleton", Tools.getEntityId(), "npcskeleton"),
    DwarfFemale("Dwarf Female", Tools.getEntityId(), "npcdwarffemale"),
    FurryFemale("Furry Female", Tools.getEntityId(), "npcfurryfemale"),
    OrcMale("Orc Male", Tools.getEntityId(), "npcorcfmale"),
    OrcFemale("Orc Female", Tools.getEntityId(), "npcorcfemale"),
    ElfMale("Elf Male", Tools.getEntityId(), "npcelfmale"),
    ElfFemale("Elf Female", Tools.getEntityId(), "npcelffemale"),
    Crystal("Crystal", Tools.getEntityId(), "npccrystal"),
    Golem("Golem", Tools.getEntityId(), "npcGolem"),
    EnderChibi("EnderChibi", Tools.getEntityId(), "npcenderchibi"),
    EnderMan("EnderMan", Tools.getEntityId(), "npcEnderman"),
    NagaMale("Naga Male", Tools.getEntityId(), "npcnagamale"),
    NagaFemale("Naga Female", Tools.getEntityId(), "npcnagafemale"),
    Slime("Slime", Tools.getEntityId(), "NpcSlime"),
    Dragon("Dragon", Tools.getEntityId(), "NpcDragon");

    public String name;
    public String entityName;
    public int id;

    private EnumModelType(String name, int id, String entityName) {
        this.name = name;
        this.entityName = entityName;
        this.id = id;
    }
}

