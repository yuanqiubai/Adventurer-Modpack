package atwandaddon.weapon.melee;

import com.adventurer_engine.common.items.blank.BasicItemSword;
import atwandaddon.weapon.nbt.SwordNbtBuilder;

/**
 * 刺刀·改 — 魂枪配件，可通过合成台装备在枪身上提供近战能力。
 * 与魂枪适配时有特殊效果。
 */
public class Bayonet_2 extends BasicItemSword
{
    public Bayonet_2(int _id)
    {
        super(SwordNbtBuilder.create(_id, "bayonet_2")
            .description("魂枪配件:刺刀系\\n通过合成台装备在枪身上,提升魂枪属性且赋予近战能力\\n与魂枪适配时,会有特殊效果\\n但一旦拆卸下来,就会消失")
            .icon("bayonet_2")
            .maxStack(1)
            .durability(60)      // 武器耐久
            .basicDamage(6.0)    // 基础伤害
            .iceDamage(3.0)      // 冰穿甲伤害
            .unbreakable(false)  // 可损坏
            .build());
    }
}
