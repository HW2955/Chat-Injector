package me.hwn2955.chatinjector.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class SpigotChatListener extends PacketAdapter {
    public SpigotChatListener() {
        super(PlaceholderAPIPlugin.getInstance(), ListenerPriority.HIGHEST, PacketType.Play.Server.CHAT);
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    public void onPacketSending(PacketEvent e) {
        if (e.getPlayer() == null) { return; }

        StructureModifier<WrappedChatComponent> chat = e.getPacket().getChatComponents();
        WrappedChatComponent chatComponent = chat.read(0);

        if (chatComponent != null) {
            String msg = chatComponent.getJson();

            if (msg == null) { return; }
            if (!PlaceholderAPI.getBracketPlaceholderPattern().matcher(msg).find()) { return; }

            msg = PlaceholderAPI.setPlaceholders(e.getPlayer(), msg);
            chat.write(0, WrappedChatComponent.fromJson(msg));
            return;
        }

        StructureModifier<BaseComponent[]> modifier = e.getPacket().getSpecificModifier(BaseComponent[].class);
        BaseComponent[] component = modifier.readSafely(0);
        if (component == null) { return; }

        String msg = ComponentSerializer.toString(component);
        if (msg == null) { return; }
        if (!PlaceholderAPI.getBracketPlaceholderPattern().matcher(msg).find()) { return; }

        msg = PlaceholderAPI.setBracketPlaceholders(e.getPlayer(), msg);
        modifier.write(0, ComponentSerializer.parse(msg));
    }
}