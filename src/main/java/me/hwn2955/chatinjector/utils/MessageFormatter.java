package me.hwn2955.chatinjector.utils;

public class MessageFormatter {
    public static String format(String message) {
        return message
                .replace("&", "&amp;")
                .replace("&amp;&amp;", "&")
                .replace("&amp;", "§");
    }

    public static String stripColor(String message) {
        return message
                .replaceAll("((?:§\\w){1,7})","");
    }
}
