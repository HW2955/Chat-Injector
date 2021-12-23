package me.hwn2955.chatinjector.listeners;

import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

public class ChatPacketListener extends PacketAdapter {
    public ChatPacketListener() {
        super(PlaceholderAPIPlugin.getInstance(), ListenerPriority.HIGHEST, com.comphenix.protocol.PacketType.Play.Server.CHAT);
    }

    public void onPacketSending(PacketEvent e) {
        if (e.getPlayer() == null) { return; }

        StructureModifier<WrappedChatComponent> chat = e.getPacket().getChatComponents();
        WrappedChatComponent chatComponent = chat.readSafely(0);
        if (chatComponent == null) { return; }

        String msg = chatComponent.getJson();
        if (msg == null) { return; }

        if (!PlaceholderAPI.getBracketPlaceholderPattern().matcher(msg).find()) { return; }

        msg = PlaceholderAPI.setBracketPlaceholders(e.getPlayer(), msg);
        chat.write(0, WrappedChatComponent.fromJson(msg));
    }
}
