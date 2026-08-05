package atwandaddon.weapon.melee;

import com.adventurer_engine.common.items.blank.BasicItemSword;
import atwandaddon.weapon.nbt.SwordNbtBuilder;

public class Sword_7 extends BasicItemSword
{
    public Sword_7(int _id)
    {
        super(SwordNbtBuilder.create(_id, "sword_7")
            .icon("sword_7")
            .maxStack(1)
            .durability(120)      // 武器耐久
            .basicDamage(10.0)    // 基础伤害
            .unbreakable(false)   // 可损坏
            .build());
    }
}
