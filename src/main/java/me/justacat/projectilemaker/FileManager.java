package me.justacat.projectilemaker;

import com.google.gson.Gson;
import me.justacat.projectilemaker.projectiles.Projectile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {


    public static final File dataFolder = JavaPlugin.getPlugin(ProjectileMaker.class).getDataFolder();
    public static File projectilesFolder;


    public static void CreateAllFolders() {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        projectilesFolder = new File(dataFolder, "projectiles");
        if (!projectilesFolder.exists()) {
            projectilesFolder.mkdir();
            Projectile firebolt = new Projectile("Firebolt");
            firebolt.saveProjectile();
        }

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

            Gson gson = new Gson();
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

            Gson gson = new Gson();
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
