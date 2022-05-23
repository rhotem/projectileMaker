package me.justacat.projectilemaker.misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.util.*;

public class Chat {

    public static List<UUID> playerChatRequests = new ArrayList<>();
    public static HashMap<UUID, String> playerAndResult = new HashMap<>();

    public static String colorMessage(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String sendPlayerChatRequest(Player player) {

        player.closeInventory();
        player.sendMessage(" ");
        player.sendMessage(colorMessage("&6---------------------------------"));
        player.sendMessage(" ");
        player.sendMessage(colorMessage("&7  Please insert a value in chat!  "));
        player.sendMessage(" ");
        player.sendMessage(colorMessage("&6---------------------------------"));
        player.sendMessage(" ");
        playerAndResult.remove(player.getUniqueId());
        playerChatRequests.add(player.getUniqueId());

        Calendar time = Calendar.getInstance();
        while (!playerAndResult.containsKey(player.getUniqueId())) {

            if (time.compareTo(Calendar.getInstance()) /  60000 >= 30) {
                playerAndResult.put(player.getUniqueId(), "Error: time out");
                break;
            }

        }
        return playerAndResult.get(player.getUniqueId());

    }


}
