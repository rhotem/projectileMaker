package me.justacat.ArcaneProjectiles.misc;

import com.jeff_media.morepersistentdatatypes.DataType;
import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NBT {


    public static ItemStack addNbt(ItemStack item, String key, PersistentDataType type, Object value) {


        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), key);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, type, value);
        item.setItemMeta(itemMeta);
        return item;

    }

    public static ItemStack removeNbt(ItemStack item, String key) {

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.getPersistentDataContainer().remove(new NamespacedKey(ArcaneProjectiles.instance, key));

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack addProjectile(ItemStack item, String clickType, String projectile) {

        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), clickType);
        List<String> strings = item.getItemMeta().getPersistentDataContainer().get(namespacedKey, DataType.asList(DataType.STRING));

        if (strings == null) {
            strings = new ArrayList<>();
        }

        strings.add(projectile);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, DataType.asList(DataType.STRING), strings);
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack removeProjectile(ItemStack item, int index, String clickType) {

        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), clickType);
        List<String> projectiles = item.getItemMeta().getPersistentDataContainer().get(namespacedKey, DataType.asList(DataType.STRING));

        if (projectiles == null) {
            return item;
        }

        if (projectiles.size() > index) {

            projectiles.remove(index);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.getPersistentDataContainer().set(namespacedKey, DataType.asList(DataType.STRING), projectiles);
            item.setItemMeta(itemMeta);
        }
        return item;

    }




}
