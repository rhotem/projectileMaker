package me.justacat.projectilemaker.listeners;

import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryEvents implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (e.getRawSlot() >= e.getInventory().getSize()) {return;}

        if (e.getInventory().getItem(e.getRawSlot()) == null) {return;}

        ItemStack item = e.getInventory().getItem(e.getRawSlot());

        String title = e.getView().getTitle();

        if (title.contains("Projectile Maker")) {

            e.setCancelled(true);
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLocalizedName()) {

                String localName = item.getItemMeta().getLocalizedName();
                if (localName.equals("CreateProjectile"))  {
                    Chat.sendPlayerChatRequest(player, "newProjectile");
                } else if (localName.equalsIgnoreCase("projectile")) {
                    ProjectileMenu.editProjectile(item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), ""), player);
                }


           }

        } else if (title.contains("Edit Projectile: ")) {
            e.setCancelled(true);
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLocalizedName()) {

                if (item.getItemMeta().getLocalizedName().equals("goBack")) {
                    ProjectileMenu.openProjectileMenu(player);

                } else if (item.getItemMeta().getLocalizedName().equals("hitEffects")) {

                    ProjectileMenu.editHitEffects(player);


                } else {
                    String projectileName = e.getView().getTitle().replace("Edit Projectile: ", "");
                    String settingType = item.getItemMeta().getLocalizedName();
                    String setting = item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), "");
                    Chat.sendPlayerChatRequest(player, "EDIT:" + setting);
                }




            }



        } else if (title.contains("Edit Hit Events: ")) {

            e.setCancelled(true);

            if (item.getItemMeta().getLocalizedName().equals("goBack")) {
                ProjectileMenu.editProjectile(ProjectileMenu.editMap.get(player.getUniqueId()), player);
            }
        }




    }


}
