package me.hwn2955.chatinjector;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.hwn2955.chatinjector.listeners.AsyncChatListener;
import me.hwn2955.chatinjector.listeners.ChatPacketListener;
import me.hwn2955.chatinjector.listeners.SpigotChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

//TODO test ChatPacketListener on bukkit, and check working versions
//TODO add support for MVdW placeholders.
//1.17.1 Spigot(Paper) works fine
public final class ChatInjector extends JavaPlugin {
    private PacketAdapter chat;

    @Override
    public void onEnable() {
        if (PlaceholderAPIPlugin.getServerVersion().isSpigot()) {
            chat = new SpigotChatListener();
        } else {
            chat = new ChatPacketListener();
        }

        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(), this);
    }

    @Override
    public void onDisable() {
        if (chat != null) {
            ProtocolLibrary.getProtocolManager().removePacketListener(chat);
        }
    }
}
