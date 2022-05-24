package me.justacat.projectilemaker.gui;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectileMenu {

    public static void openProjectileMenu(Player player) {

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(FileManager.projectileList);
        List<String> list = new ArrayList<>();
        if (yamlConfiguration.get("List") != null) {
            list = yamlConfiguration.getStringList("List");
        }



        List<ItemStack> projectiles = new ArrayList<>();
        for (String string : list) {
            ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Chat.colorMessage("&7" + string));
            itemStack.setItemMeta(itemMeta);
            projectiles.add(itemStack);
        }



        GuiBuilder builder = new GuiBuilder(player);
        //PagesGUI pagesGUI = new PagesGUI(45, projectiles);

        builder.setSize(54);
        builder.setTitle("&0Projectile Maker");

        int slot;
        for (slot = 0; slot < projectiles.size(); slot++) {

            builder.setItem(slot, projectiles.get(slot));

        }

        builder.setItem(
                slot,
                Material.ITEM_FRAME,
                1,
                "&7New Projectile",
                Arrays.asList("&0" ,"&7Click here to create new projectile!"),
                true,
                "CreateProjectile"
                );
        player.openInventory(builder.toInventory());


    }



}
