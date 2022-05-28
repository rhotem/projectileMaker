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
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

public class InventoryEvents implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getInventory().getItem(e.getRawSlot());

        if (e.getView().getTitle().contains("Projectile Maker")) {

            e.setCancelled(true);
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLocalizedName()) {

                String localName = e.getInventory().getItem(e.getRawSlot()).getItemMeta().getLocalizedName();
                if (localName.equals("CreateProjectile"))  {
                    Chat.sendPlayerChatRequest(player, "newProjectile");
                } else if (localName.equalsIgnoreCase("projectile")) {
                    ProjectileMenu.editProjectile(item.getItemMeta().getDisplayName().replace("&7", ""), player);
                }


           }

        } else if (e.getView().getTitle().contains("Edit Projectile: ")) {

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLocalizedName()) {
                String projectileName = e.getView().getTitle().replace("Edit Projectile: ", "");
                String settingType = item.getItemMeta().getLocalizedName();
                String setting = item.getItemMeta().getDisplayName().replace("&7", "");
                Chat.sendPlayerChatRequest(player, "EDIT:" + setting);

            }



        }




    }


}
