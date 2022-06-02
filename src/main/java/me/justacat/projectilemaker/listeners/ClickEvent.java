package me.justacat.projectilemaker.listeners;

import me.justacat.projectilemaker.Projectile;
import me.justacat.projectilemaker.ProjectileMaker;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ClickEvent implements Listener {


    @EventHandler
    public void OnClick(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        NamespacedKey leftClick = new NamespacedKey(JavaPlugin.getPlugin(ProjectileMaker.class), "LeftClick");
        NamespacedKey rightClick = new NamespacedKey(JavaPlugin.getPlugin(ProjectileMaker.class), "RightClick");

        if (e.getHand() != EquipmentSlot.HAND) {return;}
        if (item.getItemMeta() == null) {return;}

        ItemMeta itemMeta = item.getItemMeta();


        if (itemMeta.getPersistentDataContainer().has(leftClick) && e.getAction().isLeftClick()) {

            String projectileName = itemMeta.getPersistentDataContainer().get(leftClick, PersistentDataType.STRING);

            Projectile projectile = Projectile.projectileFromName(projectileName, true);

            if (projectile != null) {
                projectile.cast(player.getEyeLocation(), player, player.getEyeLocation().getDirection());
            }


        }

        if (itemMeta.getPersistentDataContainer().has(rightClick) && e.getAction().isRightClick()) {

            String projectileName = itemMeta.getPersistentDataContainer().get(rightClick, PersistentDataType.STRING);

            Projectile projectile = Projectile.projectileFromName(projectileName, true);

            if (projectile != null) {
                projectile.cast(player.getEyeLocation(), player, player.getEyeLocation().getDirection());
            }




        }

    }
}
