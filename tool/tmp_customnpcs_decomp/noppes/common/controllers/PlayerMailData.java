/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 */
package noppes.common.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import noppes.common.controllers.IPlayerData;
import noppes.common.controllers.PlayerMail;

public class PlayerMailData
implements IPlayerData {
    public ArrayList<PlayerMail> playermail = new ArrayList();

    public void readNBT(by compound) {
        ArrayList<PlayerMail> newmail = new ArrayList<PlayerMail>();
        cg list = compound.m("MailData");
        for (int i = 0; i < list.c(); ++i) {
            PlayerMail mail = new PlayerMail();
            mail.readNBT((by)list.b(i));
            newmail.add(mail);
        }
        this.playermail = newmail;
    }

    @Override
    public by writeNBT(by compound) {
        cg list = new cg();
        for (PlayerMail mail : this.playermail) {
            list.a((cl)mail.writeNBT());
        }
        compound.a("MailData", (cl)list);
        return compound;
    }

    public boolean hasMail() {
        PlayerMail mail;
        Iterator<PlayerMail> i$ = this.playermail.iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            mail = i$.next();
        } while (mail.beenRead);
        return true;
    }
}

