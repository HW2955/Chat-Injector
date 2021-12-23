package me.hwn2955.chatinjector;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.hwn2955.chatinjector.commands.Reload;
import me.hwn2955.chatinjector.listeners.AsyncChatListener;
import me.hwn2955.chatinjector.listeners.ChatPacketListener;
import me.hwn2955.chatinjector.listeners.SpigotChatListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ChatInjector extends JavaPlugin {
    public static Configuration config = null;
    private PacketAdapter chat;

    @Override
    public void onEnable() {
        //Create data folder
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        //Create config file
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) { saveDefaultConfig(); }

        //Load configuration
        config = getConfig();

        if (PlaceholderAPIPlugin.getServerVersion().isSpigot()) {
            chat = new SpigotChatListener();
        } else {
            chat = new ChatPacketListener();
        }

        //Register listeners
        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(), this);

        //Register commands
        getCommand("reload").setExecutor(new Reload());
    }

    @Override
    public void onDisable() {
        if (chat != null) {
            ProtocolLibrary.getProtocolManager().removePacketListener(chat);
        }
    }

    public static void reloadConfiguration() throws IOException, InvalidConfigurationException {
        File file = new File(Bukkit.getPluginManager().getPlugin("ChatInjector").getDataFolder(), "config.yml");
        YamlConfiguration config = new YamlConfiguration();
        config.load(file); //Messy way of checking if config is valid ;-;
        ChatInjector.config = YamlConfiguration.loadConfiguration(file);
    }

}
