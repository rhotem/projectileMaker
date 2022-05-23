package me.justacat.projectilemaker;

import me.justacat.projectilemaker.commands.ProjectileMakerCommand;
import me.justacat.projectilemaker.commands.testCommand;
import me.justacat.projectilemaker.listeners.InventoryEvents;
import me.justacat.projectilemaker.listeners.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectileMaker extends JavaPlugin {

    @Override
    public void onEnable() {


        FileManager.CreateAllFolders();


        getCommand("test").setExecutor(new testCommand());
        getCommand("projectileMaker").setExecutor(new ProjectileMakerCommand());

        Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    @Override
    public void onDisable() {
    }
}
