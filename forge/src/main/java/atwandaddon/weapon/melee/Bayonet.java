package atwandaddon.weapon.melee;

import com.adventurer_engine.common.items.blank.BasicItemSword;

import atwandaddon.weapon.nbt.SwordNbtBuilder;


/**
 * 刺刀 — 近战武器，使用 {@link SwordNbtBuilder} NBT 路径绕过引擎 JSON 路径的
 * {@code ice_damage=0.0f} 硬编码 bug，支持完整的冰穿甲伤害配置。
 */
public class Bayonet extends BasicItemSword
{
    public Bayonet(int _id)
    {
        super(SwordNbtBuilder.create(_id, "bayonet")
            .description("魂枪配件:刺刀系\\n通过合成台装备在枪身上,提升魂枪属性且赋予近战能力\\n与魂枪适配时,会有特殊效果\\n但一旦拆卸下来,就会消失")
            .icon("bayonet")
            .maxStack(1)
            .durability(60)      // 武器耐久
            .basicDamage(3.0)    // 基础伤害
            .iceDamage(6.0)      // 冰穿甲伤害
            .unbreakable(false)  // 可损坏
            .build());
    }
}
