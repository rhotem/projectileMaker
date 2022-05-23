package me.justacat.projectilemaker.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerEvents implements Listener {

    @EventHandler
    public void OnPlayerChat(AsyncChatEvent e) {

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (Chat.playerChatRequests.contains(uuid)) {
            e.setCancelled(true);
            Chat.playerChatRequests.remove(uuid);
            Chat.playerAndResult.put(uuid, e.message().toString());
        }

    }
}
