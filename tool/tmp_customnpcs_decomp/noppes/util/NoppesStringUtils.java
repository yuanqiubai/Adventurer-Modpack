/*
 * Decompiled with CFR 0.152.
 */
package noppes.util;

public class NoppesStringUtils {
    public static String formatText(String text, String username) {
        text = text.replaceAll("\\{player\\}", username);
        text = text.replaceAll("@p", username);
        text = text.replaceAll("&", String.valueOf(Character.toChars(167)[0]));
        return text;
    }
}

