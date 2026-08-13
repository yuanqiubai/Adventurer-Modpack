/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  we
 *  ye
 */
package noppes.common.containers;

import java.util.Iterator;
import noppes.common.roles.RoleFollower;

class SlotNpcMercenaryCurrency
extends we {
    RoleFollower role;

    public SlotNpcMercenaryCurrency(RoleFollower role, mo inv, int i, int j, int k) {
        super(inv, i, j, k);
        this.role = role;
    }

    public int a() {
        return 64;
    }

    public boolean a(ye itemstack) {
        ye is;
        int id = itemstack.d;
        Iterator<ye> i$ = this.role.inventory.items.values().iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            is = i$.next();
        } while (id != is.d || itemstack.h() && itemstack.k() != is.k());
        return true;
    }
}

