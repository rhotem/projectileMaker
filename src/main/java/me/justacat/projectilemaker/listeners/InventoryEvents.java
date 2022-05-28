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


                Chat.sendPlayerChatRequest(player, "newProjectile");

           }

        }


    }


}
