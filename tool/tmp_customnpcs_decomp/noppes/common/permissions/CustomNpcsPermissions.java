/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.permissions;

import noppes.common.permissions.PermissionsInterface;

public class CustomNpcsPermissions
implements PermissionsInterface {
    private static final String[] permissions = new String[]{"-customnpcs.*"};
    public static PermissionsInterface Instance = new CustomNpcsPermissions();

    @Override
    public boolean hasPermission(String username, String permission) {
        return true;
    }
}

