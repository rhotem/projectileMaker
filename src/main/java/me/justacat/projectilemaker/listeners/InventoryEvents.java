package me.justacat.projectilemaker.listeners;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.Projectile;
import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.IOException;
import java.util.List;

public class InventoryEvents implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().contains("Projectile Maker")) {

            e.setCancelled(true);
            if (e.getInventory().getItem(e.getRawSlot()) != null && e.getInventory().getItem(e.getRawSlot()).getItemMeta() != null && e.getInventory().getItem(e.getRawSlot()).getItemMeta().getLocalizedName().equals("CreateProjectile")) {


                String name = Chat.sendPlayerChatRequest(player, "newProjectile");
                if (name.equals("new request 123456")) {return;}
                name = name.replace(":", "");
                name = name.replace("/", "");
                name = name.replace("\\", "");
                name = name.replace(">", "");
                name = name.replace("<", "");
                name = name.replace("*", "");
                name = name.replace("?", "");
                name = name.replace("\"", "");
                name = name.replace("|", "");
                name = name.replace(" ", "_");

                if (name.length() < 3) {
                    name = Chat.sendPlayerChatRequest(player, "newProjectile");
                    player.sendMessage(Chat.colorMessage("&cThis name is too short!"));
                    return;
                }
                if (name.length() > 16) {
                    name = Chat.sendPlayerChatRequest(player, "newProjectile");
                    player.sendMessage(Chat.colorMessage("&cThis name is too long!"));
                    return;
                }

                YamlConfiguration modifyProjectileList = YamlConfiguration.loadConfiguration(FileManager.projectileList);

                List<String> projectileList = modifyProjectileList.getStringList("List");

                if (projectileList.contains(name)) {

                    name = Chat.sendPlayerChatRequest(player, "newProjectile");
                    player.sendMessage(Chat.colorMessage("&cThere is already an existing projectile with this name!"));
                    return;
                } else {

                    projectileList.add(name);
                    modifyProjectileList.set("List", projectileList);

                }

                try {
                    modifyProjectileList.save(FileManager.projectileList);
                } catch (IOException ex) {
                    System.out.println("Failed to save the projectile list!");
                }

                Projectile projectile = new Projectile(name);
                ProjectileMenu.openProjectileMenu(player);


            }

        }


    }


}
