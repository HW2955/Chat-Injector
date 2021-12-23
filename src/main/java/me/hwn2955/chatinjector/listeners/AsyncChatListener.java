package me.hwn2955.chatinjector.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;

public class AsyncChatListener implements Listener {
    @EventHandler
    public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        Matcher matcher = PlaceholderAPI.getBracketPlaceholderPattern().matcher(msg);

        //TODO add permission check or debug command ;)
        if (matcher.find() && e.getPlayer().hasPermission("chatinjector.parsechat")) {
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
