package me.justacat.projectilemaker;

import me.justacat.projectilemaker.commands.ProjectileMakerCommand;
import me.justacat.projectilemaker.commands.TabComplete;
import me.justacat.projectilemaker.commands.testCommand;
import me.justacat.projectilemaker.listeners.ChatEvent;
import me.justacat.projectilemaker.listeners.ClickEvent;
import me.justacat.projectilemaker.listeners.InventoryEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectileMaker extends JavaPlugin {

    @Override
    public void onEnable() {


        FileManager.CreateAllFolders();


        getCommand("test").setExecutor(new testCommand());
        getCommand("projectileMaker").setExecutor(new ProjectileMakerCommand());
        getCommand("projectileMaker").setTabCompleter(new TabComplete());

        Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
    }

    @Override
    public void onDisable() {
    }
}
