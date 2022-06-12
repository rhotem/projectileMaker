package me.justacat.projectilemaker.misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;




public class Chat {

    public static HashMap<UUID, String> playerChatRequests = new HashMap<>();
    public static HashMap<UUID, String> playerAndResult = new HashMap<>();

    public static String colorMessage(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendPlayerChatRequest(Player player, String ID) {

        player.closeInventory();
        player.sendMessage(" ");
        player.sendMessage(colorMessage("&6---------------------------------"));
        player.sendMessage(" ");
        player.sendMessage(colorMessage("&7  Please insert a value in chat!  "));
        player.sendMessage(" ");
        player.sendMessage(colorMessage("&6---------------------------------"));
        player.sendMessage(" ");
        playerAndResult.remove(player.getUniqueId());
        playerChatRequests.put(player.getUniqueId(), ID);


    }


}
