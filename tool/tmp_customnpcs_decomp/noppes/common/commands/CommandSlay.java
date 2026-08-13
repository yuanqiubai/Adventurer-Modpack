/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ad
 *  asx
 *  cv
 *  nn
 *  nt
 *  oa
 *  of
 *  oq
 *  rp
 *  rs
 *  sk
 *  ss
 *  tj
 *  tm
 *  uf
 *  z
 */
package noppes.common.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import noppes.common.entity.EntityNPCInterface;

public class CommandSlay
extends z {
    private final Map<String, Class<?>> map = new HashMap();

    public CommandSlay() {
        HashMap list = new HashMap(nt.b);
        for (String name : list.keySet()) {
            Class cls = (Class)list.get(name);
            if (EntityNPCInterface.class.isAssignableFrom(cls) || !of.class.isAssignableFrom(cls)) continue;
            this.map.put(name.toLowerCase(), (Class)list.get(name));
        }
        this.map.remove("monster");
        this.map.remove("mob");
        this.map.put("all", of.class);
        this.map.put("mobs", tm.class);
        this.map.put("animals", rp.class);
        this.map.put("items", ss.class);
        this.map.put("xporbs", oa.class);
    }

    public String c() {
        return "slay";
    }

    public String c(ad icommandsender) {
        return "/slay all, /slay mobs, /slay animals, /slay <entityname>";
    }

    public void b(ad icommandsender, String[] vars) {
        if (!(icommandsender instanceof uf)) {
            icommandsender.a(cv.d((String)"Can only be used by players"));
        } else if (vars.length == 0) {
            icommandsender.a(cv.d((String)"No entities given"));
        } else {
            uf player = (uf)icommandsender;
            ArrayList toDelete = new ArrayList();
            String[] count = vars;
            int range = vars.length;
            for (int list = 0; list < range; ++list) {
                String i$ = count[list];
                Class<?> entity = this.map.get(i$.toLowerCase());
                if (entity != null) {
                    toDelete.add(entity);
                }
                if (!i$.equals("mobs")) continue;
                toDelete.add(tj.class);
                toDelete.add(sk.class);
            }
            int var10 = 0;
            asx var11 = player.E.b(120.0, 120.0, 120.0);
            List var12 = player.q.a(of.class, var11);
            for (nn var14 : var12) {
                if (var14 instanceof uf || var14 instanceof oq && ((oq)var14).bT() || var14 instanceof EntityNPCInterface || !this.delete(var14, toDelete)) continue;
                ++var10;
            }
            if (toDelete.contains(oa.class)) {
                var12 = player.q.a(oa.class, var11);
                for (nn var14 : var12) {
                    var14.M = true;
                    ++var10;
                }
            }
            if (toDelete.contains(ss.class)) {
                var12 = player.q.a(ss.class, var11);
                for (nn var14 : var12) {
                    var14.M = true;
                    ++var10;
                }
            }
            icommandsender.a(cv.d((String)(var10 + " entities deleted")));
        }
    }

    private boolean delete(nn entity, ArrayList<Class<?>> toDelete) {
        Class<?> delete;
        Iterator<Class<?>> i$ = toDelete.iterator();
        do {
            if (i$.hasNext()) continue;
            return false;
        } while ((delete = i$.next()) == rp.class && entity instanceof rs || !delete.isAssignableFrom(entity.getClass()));
        entity.M = true;
        return true;
    }

    public List a(ad par1ICommandSender, String[] vars) {
        ArrayList<String> list = new ArrayList<String>(this.map.keySet());
        Collections.sort(list);
        return CommandSlay.a((String[])vars, list);
    }
}

