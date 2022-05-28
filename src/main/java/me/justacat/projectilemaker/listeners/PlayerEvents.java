package me.justacat.projectilemaker.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.Projectile;
import me.justacat.projectilemaker.ProjectileMaker;
import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.UUID;

public class PlayerEvents implements Listener {

    @EventHandler
    public void OnPlayerChat(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (Chat.playerChatRequests.containsKey(uuid)) {

            if (Chat.playerChatRequests.get(uuid) == null) {return;}

            e.setCancelled(true);



            if (Chat.playerChatRequests.get(uuid).equals("newProjectile")) {

                Runnable runnable = () -> Projectile.createProjectile(e.getMessage(), player);

                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(ProjectileMaker.class), runnable);

            } else if (Chat.playerChatRequests.get(uuid).contains("EDIT:")) {

                Runnable runnable;
                String setting = Chat.playerChatRequests.get(uuid).replace("EDIT:", "");
                String name = ProjectileMenu.editMap.get(player.getUniqueId());
                Projectile projectile = FileManager.jsonToProjectile(FileManager.CreateFile(FileManager.projectilesFolder, name + ".json"));
                if (projectile.editSetting(setting, e.getMessage())) {
                    projectile.saveProjectile();
                    runnable = () -> ProjectileMenu.editProjectile(name, player);
                } else {
                    runnable = () -> Chat.sendPlayerChatRequest(player, Chat.playerChatRequests.get(uuid));
                    player.sendMessage(ChatColor.RED + "Invalid value, please try again!");
                }
                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(ProjectileMaker.class), runnable);

            }



            Chat.playerChatRequests.remove(uuid);
            Chat.playerAndResult.put(uuid, e.getMessage());
        }

    }
}
