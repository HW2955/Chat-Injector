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
        Matcher matcher = PlaceholderAPI.getPlaceholderPattern().matcher(msg);
        if (matcher.find() && e.getPlayer().hasPermission("chatinjector.parsechat") && ChatInjector.config.getBoolean("allow_placeholders_in_messages")) {
            msg = PlaceholderAPI.setPlaceholders(e.getPlayer(), msg);
        }
        e.setMessage(msg);

        String format = e.getFormat();
        matcher = PlaceholderAPI.getPlaceholderPattern().matcher(format);

        if (matcher.find()) {
            format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
            e.setFormat(format);
        }
    }
}
