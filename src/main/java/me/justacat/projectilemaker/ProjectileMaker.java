package me.justacat.projectilemaker;

import me.justacat.projectilemaker.commands.ProjectileMakerCommand;
import me.justacat.projectilemaker.commands.TabComplete;
import me.justacat.projectilemaker.listeners.ChatEvent;
import me.justacat.projectilemaker.listeners.ClickEvent;
import me.justacat.projectilemaker.listeners.InventoryEvents;
import me.justacat.projectilemaker.projectiles.Projectile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ProjectileMaker extends JavaPlugin {


    @Override
    public void onEnable() {


        FileManager.CreateAllFolders();

        for (String projectile : FileManager.getProjectileList()) {

            Projectile.loadedProjectiles.put(projectile, Projectile.projectileFromName(projectile, false));
        }


        getCommand("projectileMaker").setExecutor(new ProjectileMakerCommand());
        getCommand("projectileMaker").setTabCompleter(new TabComplete());

        Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);

    }

    @Override
    public void onDisable() {
    }


    public static boolean reload(boolean log) {
        if (log) {
            Bukkit.getLogger().log(Level.INFO, "reloading...");
        }


        try {
            Projectile.loadedProjectiles.clear();
            for (String projectile : FileManager.getProjectileList()) {
                Projectile.loadedProjectiles.put(projectile, Projectile.projectileFromName(projectile, false));
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
            if (log) {
                Bukkit.getLogger().log(Level.WARNING, "Reload failed :(");
            }

            return false;
        }

        if (log) {
            Bukkit.getLogger().log(Level.INFO, "Successfully reloaded ProjectileMaker!");
        }

        return true;
    }

    public static boolean reload() {
        return reload(true);
    }
}
