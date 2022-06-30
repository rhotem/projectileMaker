package me.justacat.ArcaneProjectiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.justacat.ArcaneProjectiles.misc.RuntimeTypeAdapterFactory;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.Drill;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.Explosion;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.ExplosiveDrill;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.HitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {


    public static final File dataFolder = JavaPlugin.getPlugin(ArcaneProjectiles.class).getDataFolder();
    public static File projectilesFolder;

    public static RuntimeTypeAdapterFactory<HitEvent> adapter = RuntimeTypeAdapterFactory.of(HitEvent.class);


    public static void CreateAllFolders() {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        projectilesFolder = new File(dataFolder, "projectiles");
        if (!projectilesFolder.exists()) {
            projectilesFolder.mkdir();
            createDefaultProjectiles();
        }

    }

    public static void createDefaultProjectiles() {
        new Projectile("Firebolt");

        Projectile doom = new Projectile("DOOM");
        doom.getParameterByName("type").chatEdit("Spiral");
        doom.getParameterByName("branches").chatEdit("360");
        doom.getParameterByName("range").chatEdit("150");
        doom.getParameterByName("angle").chatEdit("-10");
        doom.getParameterByName("radius").chatEdit("1.5");
        doom.getParameterByName("Particle Amount").chatEdit("1");
        doom.getParameterByName("Display").chatEdit("TNT");
        doom.deleteHitEvent(0);
        Explosion explosion = new Explosion();
        explosion.getParameterByName("power").chatEdit("7");
        explosion.getParameterByName("safe").chatEdit("false");
        explosion.getParameterByName("fire").chatEdit("true");
        doom.getParameterByName("Cooldown").chatEdit("30");
        doom.addHitEvent(explosion);
        doom.saveProjectile();

        Projectile driller = new Projectile("Double_Driller");
        driller.getParameterByName("Particle").chatEdit("CAMPFIRE_COSY_SMOKE");
        driller.getParameterByName("Display").chatEdit("IRON_PICKAXE");
        driller.getParameterByName("Cooldown").chatEdit("3");
        driller.deleteHitEvent(0);
        driller.addHitEvent(new Drill());
        driller.addHitEvent(new ExplosiveDrill());
        driller.saveProjectile();

    }

    public static File CreateFile(File folder, String name) {

        File file = new File(folder, name);

        try {

            if (!file.exists()) {

                file.createNewFile();

            }

        }  catch (IOException e) {
            e.printStackTrace();
        }


        return file;




    }
    public static File createJSON(String name, File folder, Object data, boolean overRide) {


        File file = CreateFile(folder, name + ".json");

        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create();
            Writer writer;
            if (overRide) {
                writer = new FileWriter(file, false);
            } else {
                writer = new FileWriter(file, true);
            }

            gson.toJson(data, writer);
            writer.flush();
            writer.close();



        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("unable to write/create " + name + ".json!");
        }

        return file;

    }

    public static Projectile jsonToProjectile(File file) {

        try {


            Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();
            Reader reader = new FileReader(file);
            Projectile projectile = gson.fromJson(reader, Projectile.class);
            reader.close();

            return projectile;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }




    public static List<String> getProjectileList() {

        File[] files = projectilesFolder.listFiles();

        if (files == null) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();
        for (File file : files) {
            String name = file.getName();
            if (name.contains(".json")) {
                name = name.replace(".json", "");
                list.add(name);
            }

        }
        return list;

    }


}
