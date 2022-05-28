package me.justacat.projectilemaker;

import com.google.gson.Gson;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class FileManager {


    public static final File dataFolder = JavaPlugin.getPlugin(ProjectileMaker.class).getDataFolder();
    public static File projectilesFolder;
    public static File projectileList;


    public static void CreateAllFolders() {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        projectilesFolder = new File(dataFolder, "projectiles");
        if (!projectilesFolder.exists()) {
            projectilesFolder.mkdir();
        }
        projectileList = CreateFile(dataFolder, "ProjectileList.yml");


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

        if (overRide) {

            File file = new File(folder, name + ".json");

            if (file.exists()) {

                file.delete();

            }


        }

        File file = CreateFile(folder, name + ".json");

        try {

            Gson gson = new Gson();
            Writer writer = new FileWriter(file, true);
            gson.toJson(data, writer);
            writer.flush();
            writer.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;

    }

    public static Projectile jsonToProjectile(File file) {

        try {

            Gson gson = new Gson();
            Reader reader = new FileReader(file);
            return gson.fromJson(reader, Projectile.class);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;


    }



}
