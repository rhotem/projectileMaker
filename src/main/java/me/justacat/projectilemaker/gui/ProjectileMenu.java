package me.justacat.projectilemaker.gui;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.projectiles.HitManager;
import me.justacat.projectilemaker.projectiles.Projectile;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ProjectileMenu {


    public static HashMap<UUID, String> editMap = new HashMap<>();


    public static void openProjectileMenu(Player player) {

        List<String> list = FileManager.getProjectileList();




        List<ItemStack> projectiles = new ArrayList<>();
        for (String string : list) {
            ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Chat.colorMessage("&7" + string));
            itemMeta.setLocalizedName("Projectile");
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

    public static void editProjectile(String name, Player player) {


        editMap.put(player.getUniqueId(), name);
        GuiBuilder guiBuilder = new GuiBuilder(player);
        guiBuilder.setSize(54);

        guiBuilder.setTitle("Edit Projectile: " + name);
        List<String> lore = new ArrayList<>();
        lore.add("   ");
        lore.add(Chat.colorMessage("&eClick here to edit this value"));

        Projectile projectile = Projectile.projectileFromName(name, true);

        if (projectile == null) {
            System.out.println("error: this projectile seems to be null!");
            return;
        }

        lore.add(Chat.colorMessage("&eValue: &f" + projectile.getRange()));

        guiBuilder.setItem(0, Material.BOW, 1, Chat.colorMessage("&7Range"), lore, true, "double");

        lore.remove(Chat.colorMessage("&eValue: &f" + projectile.getRange()));
        lore.add(Chat.colorMessage("&eValue: &f" + projectile.getVelocity()));

        guiBuilder.setItem(1, Material.SUGAR, 1, Chat.colorMessage("&7Velocity"), lore, true, "double");

        lore.remove(Chat.colorMessage("&eValue: &f" + projectile.getVelocity()));
        lore.add(Chat.colorMessage("&eValue: &f" + projectile.getDelay()));

        guiBuilder.setItem(2, Material.CLOCK, 1, Chat.colorMessage("&7Delay"), lore, true, "int");

        lore.remove(Chat.colorMessage("&eValue: &f" + projectile.getDelay()));
        lore.add(Chat.colorMessage("&eValue: &f" + projectile.getDamage()));

        guiBuilder.setItem(3, Material.DIAMOND_SWORD, 1, Chat.colorMessage("&7Damage"), lore, true, "double");

        lore.remove(Chat.colorMessage("&eValue: &f" + projectile.getDamage()));
        lore.add(Chat.colorMessage("&eValue: &f" + projectile.getParticle()));

        guiBuilder.setItem(4, Material.REDSTONE, 1, Chat.colorMessage("&7Particle"), lore, true, "particle");


        guiBuilder.setItem(5, Material.TNT_MINECART, 1, Chat.colorMessage("&7Hit Effects"), null, true, "hitEffects");




        guiBuilder.setItem(49, Material.ARROW, 1, "&fGo Back", null, true, "goBack");

        guiBuilder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, " ", null, true);
        player.openInventory(guiBuilder.toInventory());
    }


    public static void editHitEffects(Player player) {

        String projectileName = editMap.get(player.getUniqueId());
        Projectile projectile = Projectile.projectileFromName(projectileName, true);

        List<HitManager> hitEffects = projectile.getHitEvents();

        GuiBuilder guiBuilder = new GuiBuilder(player);
        guiBuilder.setSize(54);

        guiBuilder.setTitle("Edit Hit Events: " + projectileName);


        int slot = 0;
        for (HitManager hit : hitEffects) {

            guiBuilder.setItem(slot, Material.TNT, 1, "Event " + (slot + 1) + ": " + hit.getType(), Arrays.asList("&0", "&7Click here to edit!"), true);


            slot++;

        }


        guiBuilder.setItem(49, Material.ARROW, 1, "&fGo Back", null, true, "goBack");


        player.openInventory(guiBuilder.toInventory());


    }



}
