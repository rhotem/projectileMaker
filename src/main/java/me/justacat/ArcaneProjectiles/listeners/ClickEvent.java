package me.justacat.ArcaneProjectiles.listeners;

import com.jeff_media.morepersistentdatatypes.DataType;
import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ClickEvent implements Listener {


    @EventHandler
    public void OnClick(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        NamespacedKey leftClick = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), "LeftClick");
        NamespacedKey rightClick = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), "RightClick");

        if (e.getHand() != EquipmentSlot.HAND) {return;}
        if (item.getItemMeta() == null) {return;}

        ItemMeta itemMeta = item.getItemMeta();


        if (itemMeta.getPersistentDataContainer().has(leftClick) && e.getAction().isLeftClick()) {

            List<String> projectileList = itemMeta.getPersistentDataContainer().get(leftClick, DataType.asList(DataType.STRING));

            if (projectileList == null) return;


            for (String projectileName : projectileList) {
                Projectile projectile = Projectile.projectileFromName(projectileName, true);

                if (projectile != null) {
                    projectile.cast(player.getEyeLocation(), player, player.getEyeLocation().getDirection());
                }
            }



        }

        if (itemMeta.getPersistentDataContainer().has(rightClick) && e.getAction().isRightClick()) {

            if (item.getType() == Material.BOW | item.getType() == Material.CROSSBOW) return;

            List<String> projectileList = itemMeta.getPersistentDataContainer().get(rightClick, DataType.asList(DataType.STRING));

            if (projectileList == null) return;

            for (String projectileName : projectileList) {
                Projectile projectile = Projectile.projectileFromName(projectileName, true);

                if (projectile != null) {
                    projectile.cast(player.getEyeLocation(), player, player.getEyeLocation().getDirection());
                }
            }




        }

    }
}
