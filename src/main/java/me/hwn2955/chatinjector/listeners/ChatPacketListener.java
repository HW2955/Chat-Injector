package me.hwn2955.chatinjector.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

public class ChatPacketListener extends PacketAdapter {
    public ChatPacketListener() {
        super(PlaceholderAPIPlugin.getInstance(), ListenerPriority.HIGHEST, PacketType.Play.Server.CHAT);
    }

    public void onPacketSending(PacketEvent e) {
        StructureModifier<WrappedChatComponent> chat = e.getPacket().getChatComponents();
        WrappedChatComponent chatComponent = chat.readSafely(0);
        if (chatComponent == null) { return; }

        String msg = chatComponent.getJson();
        if (msg == null) { return; }

        if (PlaceholderAPI.getPlaceholderPattern().matcher(msg).find()) {
            msg = PlaceholderAPI.setPlaceholders(e.getPlayer(), msg);
        }

        chat.writeSafely(0, WrappedChatComponent.fromJson(msg));
    }
}
