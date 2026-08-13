/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bjj
 *  bjr
 *  bjw
 *  net.minecraftforge.client.event.TextureStitchEvent$Post
 *  net.minecraftforge.event.ForgeSubscribe
 */
package noppes.client.events;

import java.io.File;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import noppes.CustomNpcs;

public class TextureLoadEvent {
    @ForgeSubscribe
    public void invoke(TextureStitchEvent.Post event) {
        File check;
        File file = new File(CustomNpcs.Dir, "assets/customnpcs");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!(check = new File(file, "sound")).exists()) {
            check.mkdir();
        }
        if (!(check = new File(file, "music")).exists()) {
            check.mkdir();
        }
        if (!(check = new File(file, "textures")).exists()) {
            check.mkdir();
        }
        bjw simplemanager = (bjw)atv.w().K();
        bjj pack = new bjj(CustomNpcs.Dir);
        simplemanager.a((bjr)pack);
    }
}

