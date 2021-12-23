package me.hwn2955.chatinjector.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.hwn2955.chatinjector.ChatInjector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;

public class AsyncChatListener implements Listener {
    @EventHandler
    public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        Matcher matcher = PlaceholderAPI.getBracketPlaceholderPattern().matcher(msg);
        if (matcher.find() && e.getPlayer().hasPermission("chatinjector.parsechat") && ChatInjector.config.getBoolean("allow_placeholders_in_messages")) {
            msg = PlaceholderAPI.setBracketPlaceholders(e.getPlayer(), msg);
        }
        e.setMessage(msg);

        String format = e.getFormat();
        matcher = PlaceholderAPI.getBracketPlaceholderPattern().matcher(format);

        if (matcher.find()) {
            format = PlaceholderAPI.setBracketPlaceholders(e.getPlayer(), format);
            e.setFormat(format);
        }
    }
}
