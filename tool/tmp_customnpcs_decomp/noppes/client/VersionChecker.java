/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bdi
 */
package noppes.client;

public class VersionChecker
extends Thread {
    private final int revision = 4;

    @Override
    public void run() {
        bdi player;
        String text = "Welcome to Adventurer`s Legend\nThere will be no more CustomNpcs update!";
        try {
            player = atv.w().h;
        }
        catch (NoSuchMethodError var7) {
            return;
        }
        while ((player = atv.w().h) == null) {
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException var6) {
                var6.printStackTrace();
            }
        }
        player.a(text);
    }

    private boolean hasUpdate() {
        return true;
    }
}

