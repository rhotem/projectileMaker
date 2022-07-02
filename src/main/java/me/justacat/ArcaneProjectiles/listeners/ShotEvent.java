package me.justacat.ArcaneProjectiles.listeners;

import com.jeff_media.morepersistentdatatypes.DataType;
import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ShotEvent implements Listener {

    @EventHandler
    public void onShot(EntityShootBowEvent e) {
        LivingEntity entity = e.getEntity();
        ItemStack item = e.getBow();
        NamespacedKey rightClick = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), "RightClick");

        if (item == null || item.getItemMeta() == null) {return;}

        ItemMeta itemMeta = item.getItemMeta();


        if (itemMeta.getPersistentDataContainer().has(rightClick)) {


            if (itemMeta.getPersistentDataContainer().has(new NamespacedKey(ArcaneProjectiles.instance, "cancel"))) {e.setCancelled(true);}

            List<String> projectileList = itemMeta.getPersistentDataContainer().get(rightClick, DataType.asList(DataType.STRING));

            if (projectileList == null) return;

            for (String projectileName : projectileList) {
                Projectile projectile = Projectile.projectileFromName(projectileName, true);

                if (projectile != null) {
                    projectile.cast(entity.getEyeLocation(), entity, entity.getEyeLocation().getDirection());
                }
            }




        }

    }


}
