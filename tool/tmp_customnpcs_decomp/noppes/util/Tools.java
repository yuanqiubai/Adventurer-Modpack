/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  cpw.mods.fml.common.registry.EntityRegistry
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.server.MinecraftServer
 *  nn
 *  ss
 *  uf
 *  ye
 */
package noppes.util;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import net.minecraft.server.MinecraftServer;
import noppes.common.config.CustomNPCConfig;

public class Tools {
    public static File getWorldSaveDirectory() {
        MinecraftServer server = MinecraftServer.F();
        File saves = new File(".");
        if (server != null && !server.V()) {
            saves = new File(atv.w().x, "saves");
        }
        if (server != null) {
            File savedir = new File(new File(saves, server.L()), "customnpcs");
            if (!savedir.exists()) {
                savedir.mkdir();
            }
            return savedir;
        }
        return null;
    }

    public static int getEntityId() {
        int n;
        if (CustomNPCConfig.UseUniqueEntities) {
            n = EntityRegistry.findGlobalUniqueEntityId();
        } else {
            int n2 = CustomNPCConfig.EntityStartId;
            n = n2;
            CustomNPCConfig.EntityStartId = n2 + 1;
        }
        return n;
    }

    public static void GivePlayerItem(nn entity, uf player, ye item) {
        if (!entity.q.I) {
            item = item.m();
            float f = 0.7f;
            double d = (double)(entity.q.s.nextFloat() * f) + (double)(1.0f - f);
            double d1 = (double)(entity.q.s.nextFloat() * f) + (double)(1.0f - f);
            double d2 = (double)(entity.q.s.nextFloat() * f) + (double)(1.0f - f);
            ss entityitem = new ss(entity.q, entity.u + d, entity.v + d1, entity.w + d2, item);
            entityitem.b = 2;
            entity.q.d((nn)entityitem);
            int i = item.b;
            if (player.bn.a(item)) {
                GameRegistry.onPickupNotification((uf)player, (ss)entityitem);
                entity.q.a((nn)entityitem, "random.pop", 0.2f, ((entity.q.s.nextFloat() - entity.q.s.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                player.a((nn)entityitem, i);
                if (item.b <= 0) {
                    entityitem.x();
                }
            }
        }
    }
}

