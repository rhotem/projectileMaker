package me.justacat.ArcaneProjectiles;

import com.sk89q.worldguard.WorldGuard;
import me.justacat.ArcaneProjectiles.commands.MainCommand;
import me.justacat.ArcaneProjectiles.commands.TabComplete;
import me.justacat.ArcaneProjectiles.listeners.*;
import me.justacat.ArcaneProjectiles.misc.Parameter;
import me.justacat.ArcaneProjectiles.misc.WorldGuardManager;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public final class ArcaneProjectiles extends JavaPlugin {

    public static ArcaneProjectiles instance;

    public static boolean worldGuardEnabled;
    public static WorldGuardManager worldGuardManager;

    @Override
    public void onEnable() {

        instance = this;

        registerHitEvents();
        FileManager.adapter.registerSubtype(BackSender.class, "Back To The Sender!");
        FileManager.CreateAllFolders();

        HitEvent.registerWithOutAdaption(new BackSender(), "Shoots a projectile back to the caster!", Material.ALLIUM);

        for (String projectile : FileManager.getProjectileList()) {

            Projectile.loadedProjectiles.put(projectile, Projectile.projectileFromName(projectile, false));
        }


        getCommand("ArcaneProjectiles").setExecutor(new MainCommand());
        getCommand("ArcaneProjectiles").setTabCompleter(new TabComplete());

        Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnHit(), this);
        Bukkit.getPluginManager().registerEvents(new ShotEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Mana(), this);


        Bukkit.getLogger().info("Done!");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (getConfig().getBoolean("Auto-repair-on-start")) {
            repair();
        }


    }

    @Override
    public void onDisable() {
    }

    public static void registerHitEvents() {


        HitEvent.registerHitEvent(new Delay(), "Waits before the next hit event (time in ticks)!", Material.CLOCK);
        HitEvent.registerHitEvent(new Drill(), "Mines blocks at a straight line!", Material.IRON_PICKAXE);
        HitEvent.registerHitEvent(new Explosion(), "Creates an explosion!", Material.TNT);
        HitEvent.registerHitEvent(new ExplosiveDrill(), "Others may call this thing \"Ray of death\"!", Material.TNT_MINECART);
        HitEvent.registerHitEvent(new Potion(), "Gives nearby entities potion effect!", Material.SPLASH_POTION);
        HitEvent.registerHitEvent(new SpawnEntity(), "Spawns entities!", Material.CREEPER_SPAWN_EGG);
        HitEvent.registerHitEvent(new Teleport(), "Teleports the caster to the hit location", Material.ENDER_PEARL);
        HitEvent.registerHitEvent(new PlayParticle(), "Spawns particles!", Material.REDSTONE);

    }


    public static boolean reload(boolean log) {
        if (log) {
            Bukkit.getLogger().log(Level.INFO, "reloading...");
        }


        try {

            instance.getConfig().load(new File(instance.getDataFolder().getPath() + "\\config.yml"));

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
            Bukkit.getLogger().log(Level.INFO, "Successfully reloaded ArcaneProjectiles!");
        }

        return true;
    }

    public static boolean reload() {
        return reload(true);
    }


    public static void repair() {
        int fixed = 0;

        Bukkit.getLogger().info("Checking if the plugin needs to be repaired...");



        for (String name : FileManager.getProjectileList()) {


            Projectile projectile = Projectile.projectileFromName(name, true);





            boolean needFix = false;
            for (Parameter<?> parameter : projectile.getParameters()) {


                if (parameter == null || parameter.getValue() == null) {

                    needFix = true;
                    fixed++;
                    Bukkit.getLogger().info("Fixed!");

                }



            }

            if (needFix) {

                Bukkit.getLogger().info("Repairing projectile: " + name);

                List<Parameter<?>> parameters = projectile.getAllParameters();

                parameters.removeIf(Objects::isNull);
                parameters.removeIf(parameter -> parameter.getValue() == null);

                Projectile.loadedProjectiles.remove(name);

                new Projectile(name);

                projectile = Projectile.projectileFromName(name, true);

                for (Parameter<?> parameter : parameters) {


                    projectile.getParameterByNameFromAllParameters(parameter.getName()).chatEdit(parameter.getValue().toString());




                }
                projectile.saveProjectile();


            }



        }

        ArcaneProjectiles.reload();
        Bukkit.getLogger().info("Finished with total of " + fixed + " fixes.");


    }

    @Override
    public void onLoad() {

        try {
            WorldGuard worldGuard = WorldGuard.getInstance();
            worldGuardEnabled = true;
            worldGuardManager = new WorldGuardManager();
            worldGuardManager.registerFlags();
        } catch (NoClassDefFoundError e) {
            worldGuardEnabled = false;
        }

        System.out.println(worldGuardEnabled);




    }


}
