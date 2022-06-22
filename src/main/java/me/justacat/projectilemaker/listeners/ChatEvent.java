package me.justacat.projectilemaker.listeners;

import me.justacat.projectilemaker.ProjectileMaker;
import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import me.justacat.projectilemaker.misc.Parameter;
import me.justacat.projectilemaker.projectiles.Projectile;
import me.justacat.projectilemaker.projectiles.hitevents.HitEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class ChatEvent implements Listener {

    @EventHandler
    public void OnPlayerChat(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();
        boolean skipRemoving = false;
        UUID uuid = player.getUniqueId();
        if (Chat.playerChatRequests.containsKey(uuid)) {

            if (Chat.playerChatRequests.get(uuid) == null) {return;}

            e.setCancelled(true);
            
            //cancel
            if (e.getMessage().equalsIgnoreCase("Cancel")) {
                Chat.playerChatRequests.remove(uuid);
                Runnable runnable = () -> ProjectileMenu.openProjectileMenu(player);
                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(ProjectileMaker.class), runnable);
                return;
            }




            if (Chat.playerChatRequests.get(uuid).equals("newProjectile")) {


                Runnable runnable = () -> Projectile.createProjectile(e.getMessage(), player);

                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(ProjectileMaker.class), runnable);

            } else if (Chat.playerChatRequests.get(uuid).contains("EDIT:")) {

                Runnable runnable;
                String setting = Chat.playerChatRequests.get(uuid).replace("EDIT:", "");
                String name = ProjectileMenu.projectileEditMap.get(player.getUniqueId());
                Projectile projectile = Projectile.loadedProjectiles.get(name);
                if (projectile.getParameterByName(setting).chatEdit(e.getMessage())) {
                    projectile.saveProjectile();
                    runnable = () -> ProjectileMenu.editProjectile(name, player);
                } else {
                    runnable = () -> Chat.sendPlayerChatRequest(player, Chat.playerChatRequests.get(uuid));
                    player.sendMessage(ChatColor.RED + "Invalid value, please try again!");
                    skipRemoving = true;
                }
                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(ProjectileMaker.class), runnable);

            } else if (Chat.playerChatRequests.get(uuid).contains("EditHitEvent:")) {

                Runnable runnable;

                String projectileName = ProjectileMenu.projectileEditMap.get(player.getUniqueId());
                Projectile projectile = Projectile.loadedProjectiles.get(projectileName);

                int hitIndex = ProjectileMenu.hitEventEditMap.get(uuid) - 1;
                HitEvent hitEvent = projectile.getHitEventList().get(hitIndex);

                String settingName = Chat.playerChatRequests.get(uuid).replace("EditHitEvent:", "");
                Parameter<?> parameter = hitEvent.getParameterByName(settingName);

                if (parameter.chatEdit(e.getMessage())) {
                    projectile.saveProjectile();
                    runnable = () -> ProjectileMenu.editHitEffect(player, hitIndex + 1);

                } else {
                    runnable = () -> Chat.sendPlayerChatRequest(player, Chat.playerChatRequests.get(uuid));
                    player.sendMessage(ChatColor.RED + "Invalid value, please try again!");
                    skipRemoving = true;
                }
                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(ProjectileMaker.class), runnable);




            }


            if (!skipRemoving) {
                Chat.playerChatRequests.remove(uuid);
            }

            Chat.playerAndResult.put(uuid, e.getMessage());
        }

    }
}
