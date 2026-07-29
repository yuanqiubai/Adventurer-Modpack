/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nb
 *  nc
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  nj
 *  nn
 *  of
 */
package com.adventurer_engine.common.gadgets;

import com.adventurer_engine.util.Tools;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.DirtyEntityAccessor;
import net.minecraftforge.event.entity.living.LivingEvent;

public class AdditionBombs {
    private float damage;
    private float percent;
    private int delay;
    private of attacker;
    private nj[] effects;
    private String sound;
    private static Map<of, LinkedBombs> bombs = new HashMap<of, LinkedBombs>();

    public AdditionBombs setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public AdditionBombs setPercent(float percent) {
        this.percent = percent;
        return this;
    }

    public AdditionBombs setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public AdditionBombs setAttacker(of attacker) {
        this.attacker = attacker;
        return this;
    }

    public AdditionBombs setEffects(nj[] effects) {
        this.effects = effects;
        return this;
    }

    public AdditionBombs setSound(String sound) {
        this.sound = sound;
        return this;
    }

    public static nb causeBombDamage(nn entity) {
        return new DamageSourceAdditionBomb("additionBomb", entity);
    }

    public void trigger(of entity) {
        nb source = AdditionBombs.causeBombDamage((nn)this.attacker);
        float ratio = 1.0f;
        ratio *= this.damage;
        if (entity.aN() - ratio > 0.0f) {
            DirtyEntityAccessor.damageEntity(entity, source, ratio);
        } else {
            entity.a(source, ratio);
        }
        if (this.effects != null && Tools.percent(this.percent)) {
            for (nj p : this.effects) {
                if (entity.i(p.a())) continue;
                entity.c(new nj(p));
            }
        }
        entity.af = 0;
        if (this.sound != null) {
            float volume = Tools.random.nextFloat() * 0.4f + 0.5f;
            float frequency = 1.0f / (Tools.random.nextFloat() * 0.4f + 0.5f);
            this.attacker.q.a((nn)entity, this.sound, volume, frequency);
        }
    }

    public AdditionBombs copy() {
        nj[] copyEffects = new nj[this.effects.length];
        for (int i = 0; i < copyEffects.length; ++i) {
            copyEffects[i] = new nj(this.effects[i].a(), this.effects[i].b, this.effects[i].c());
        }
        AdditionBombs toRet = new AdditionBombs();
        toRet.damage = this.damage;
        toRet.percent = this.percent;
        toRet.delay = this.delay;
        toRet.attacker = this.attacker;
        toRet.effects = copyEffects;
        toRet.sound = this.sound;
        return toRet;
    }

    public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
        of entity = event.entityLiving;
        if (entity == null) {
            return;
        }
        if (!bombs.containsKey(entity)) {
            return;
        }
        if (event.entityLiving.M) {
            bombs.remove(event.entityLiving);
            return;
        }
        LinkedBombs head = bombs.get(entity);
        --head.v.delay;
        LinkedBombs b = head.next;
        while (b != head) {
            --b.v.delay;
            b = b.next;
        }
        boolean firstTime = true;
        boolean headChanged = false;
        while (head.v.delay == 0) {
            head.v.trigger(entity);
            if (firstTime) {
                bombs.remove(entity);
                firstTime = false;
            }
            if (head.next == head) break;
            head.next.prev = head.prev;
            head.prev.next = head.next;
            head = head.next;
            headChanged = true;
        }
        if (head.v.delay != 0) {
            if (headChanged) {
                bombs.put(entity, head);
            }
            LinkedBombs b2 = head.next;
            while (b2 != head) {
                if (b2.v.delay == 0) {
                    b2.v.trigger(entity);
                    b2.next.prev = b2.prev;
                    b2.prev.next = b2.next;
                }
                b2 = b2.next;
            }
        }
    }

    public static void addBomb(of entity, AdditionBombs bomb) {
        if (bombs.containsKey(entity)) {
            LinkedBombs head;
            LinkedBombs l = new LinkedBombs();
            l.v = bomb;
            l.prev = head = bombs.get(entity);
            l.next = head.next;
            head.next.prev = l;
            head.next = l;
        } else {
            LinkedBombs l = new LinkedBombs();
            l.v = bomb;
            l.prev = l;
            l.next = l;
            bombs.put(entity, l);
        }
    }

    private static class LinkedBombs {
        public LinkedBombs prev;
        public AdditionBombs v;
        public LinkedBombs next;

        private LinkedBombs() {
        }
    }

    public static class DamageSourceAdditionBomb
    extends nc {
        public DamageSourceAdditionBomb(String name, nn entity) {
            super(name, entity);
            this.j();
        }
    }
}

