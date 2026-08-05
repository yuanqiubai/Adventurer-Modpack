package atwandaddon.weapon.melee;

import com.adventurer_engine.common.items.blank.BasicItemSword;
import atwandaddon.weapon.nbt.SwordNbtBuilder;

public class Sword_10 extends BasicItemSword
{
    public Sword_10(int _id)
    {
        super(SwordNbtBuilder.create(_id, "sword_10")
            .icon("sword_10")
            .maxStack(1)
            .durability(120)      // 武器耐久
            .basicDamage(10.0)    // 基础伤害
            .unbreakable(false)   // 可损坏
            .build());
    }
}