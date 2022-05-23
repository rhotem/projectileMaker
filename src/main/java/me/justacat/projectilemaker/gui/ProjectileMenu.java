package me.justacat.projectilemaker.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectileMenu {

    public void openProjectileMenu(Player player) {

        List<ItemStack> projectiles = new ArrayList<>(); //read from yaml projectile list
        projectiles.add(new ItemStack(Material.BLAZE_ROD));


        GuiBuilder builder = new GuiBuilder(player);
        //PagesGUI pagesGUI = new PagesGUI(45, projectiles);

        builder.setSize(54);
        builder.setTitle("&0Projectile Maker");

        int slot;
        for (slot = 0; slot < projectiles.size(); slot++) {

            builder.setItem(slot, projectiles.get(slot));

        }

        slot++;
        builder.setItem(
                slot,
                Material.ITEM_FRAME,
                1,
                "&7New Projectile",
                Arrays.asList("&0" ,"&7Click here to create new projectile!"),
                true,
                "CreateProjectile"
                );



    }



}
