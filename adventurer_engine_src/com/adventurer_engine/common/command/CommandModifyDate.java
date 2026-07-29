/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ad
 *  bkb
 *  jv
 *  z
 */
package com.adventurer_engine.common.command;

import com.adventurer_engine.common.data.globaldata.IGlobalData;

public class CommandModifyDate
extends z {
    public String c() {
        return "modifydate";
    }

    public int a() {
        return 2;
    }

    public String c(ad icommandsender) {
        return "commands.title.usage";
    }

    public void b(ad sender, String[] args) {
        if (args.length < 3) {
            this.sendError(sender);
        }
        String pattern = args[0];
        String cmd = args[1];
        int value = 0;
        try {
            value = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            this.sendError(sender);
        }
        if (value < 0) {
            this.sendError(sender);
            return;
        }
        if ("add".equals(cmd)) {
            if ("year".equals(pattern)) {
                IGlobalData.CALENDAR_DATA.addYear(value);
            } else if ("month".equals(pattern)) {
                IGlobalData.CALENDAR_DATA.addMonth(value);
            } else if ("day".equals(pattern)) {
                IGlobalData.CALENDAR_DATA.addDay(value);
            } else {
                this.sendError(sender);
            }
        } else if ("set".equals(cmd)) {
            if ("year".equals(pattern)) {
                IGlobalData.CALENDAR_DATA.setYear(value);
            } else if ("month".equals(pattern)) {
                IGlobalData.CALENDAR_DATA.setMonth(value);
            } else if ("day".equals(pattern)) {
                IGlobalData.CALENDAR_DATA.setDay(value);
            } else {
                this.sendError(sender);
            }
        }
    }

    private void sendError(ad sender) {
        if (sender instanceof jv) {
            jv player = (jv)sender;
            player.a(bkb.a((String)"command.modifydate.error"));
        }
    }
}

