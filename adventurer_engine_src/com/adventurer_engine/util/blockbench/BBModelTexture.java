/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.util.encoders.Base64
 */
package com.adventurer_engine.util.blockbench;

import org.bouncycastle.util.encoders.Base64;

public class BBModelTexture {
    public int sizeX;
    public int sizeY;
    public String uuid;
    public String source;
    public String name;

    public BBModelTexture(int sizeX, int sizeY, String uuid, String source, String name) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.uuid = uuid;
        this.source = source;
        this.name = name;
    }

    public void handleTexture() {
        String base64Image = this.source.split(",")[1];
        byte[] imageBytes = Base64.decode((String)base64Image);
    }
}

