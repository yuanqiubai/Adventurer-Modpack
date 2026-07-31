package atwandaddon.buff;

import com.google.gson.internal.StringMap;
import com.adventurer_engine.util.JsonHelper;

/**
 * 自定义子弹 buff 工厂
 *
 * === 伤害层级 ===
 * 弹药层 (BasicAmmo JSON):
 *   basic_damage  — 普通物理伤害
 *   ice_damage    — 冰属性魔法伤害（独立计算护甲）
 *
 * DamagePacket 层 (onAttack):
 *   fluctuating   — 高斯分布伤害波动
 *   bomb          — 延迟爆炸 { damage, delay, sound }
 *   effect_wounded — 命中药水效果 [potionId, duration, amp, ...] x3
 *   effect_attacker — 攻击者药水效果
 *
 * 注意：DamagePacket 不提供 basic_damage/ice_damage，
 * 这两种基础伤害要写在弹药 JSON 的顶层。
 */
public class BuffFactory {

    /** 穿甲暴击: basic_damage 基础上加高斯波动 */
    public static StringMap armorPiercingCrit(double _baseDamage) {
        StringMap map = new StringMap();
        map.put("fluctuating", _baseDamage);
        return map;
    }
}
