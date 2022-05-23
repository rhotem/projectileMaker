package me.justacat.projectilemaker.listeners;

import me.justacat.projectilemaker.Projectile;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvents implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().contains("Projectile Maker")) {

            if (e.getInventory().getItem(e.getRawSlot()).getItemMeta().getLocalizedName().equals("CreateProjectile")) {

                String name = Chat.sendPlayerChatRequest(player);

                //check if the name exists
                //add the name to the list

                Projectile projectile = new Projectile(name);


            }

        }


    }


}
