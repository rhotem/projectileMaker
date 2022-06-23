package me.justacat.ArcaneProjectiles.misc;

import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class NBT {


    public static ItemStack addNbt(ItemStack item, String key, PersistentDataType type, Object value) {


        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), key);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, type, value);
        item.setItemMeta(itemMeta);
        return item;

    }

    public static ItemStack putProjectile(ItemStack item, String clickType, String projectile) {

        return addNbt(item, clickType, PersistentDataType.STRING, projectile);

    }


}
