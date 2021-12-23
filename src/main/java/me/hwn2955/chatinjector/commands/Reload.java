package me.hwn2955.chatinjector.commands;

import me.hwn2955.chatinjector.ChatInjector;
import me.hwn2955.chatinjector.utils.MessageFormatter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Reload implements CommandExecutor {
    public Reload() {
        super();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("chatinjector.reload")) {
            sender.sendMessage(MessageFormatter.format(ChatInjector.config.getString("messages.prefix") + ChatInjector.config.getString("messages.no-permission")));
            return false;
        }

        try {
            ChatInjector.reloadConfiguration();
            sender.sendMessage(MessageFormatter.format(ChatInjector.config.getString("messages.prefix") + ChatInjector.config.getString("messages.reload-success")));
        } catch (InvalidConfigurationException | IOException ex) {
            sender.sendMessage(MessageFormatter.format(ChatInjector.config.getString("messages.prefix") + ChatInjector.config.getString("messages.reload-failed")));
            ex.printStackTrace();
        }
        return false;
    }
}
