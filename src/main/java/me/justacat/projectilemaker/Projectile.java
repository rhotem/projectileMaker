package me.justacat.projectilemaker;

import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class Projectile {

    //general settings
    private String name;
    private String type = "Beam";

    private double range = 20.0;
    private double velocity = 10.0;
    private Particle particle = Particle.FLAME;
    private int delay = 1;
    private double damage = 5;

    //spiral

    private int branches;
    private double angle;

    //cone

    //misc
    private BukkitTask task;
    private int cycles = 0;


    public Projectile(String name) {

        this.name = name;
        FileManager.createJSON(name, FileManager.projectilesFolder, this, true);

    }


    public void saveProjectile() {
        FileManager.createJSON(name, FileManager.projectilesFolder, this, true);
    }

    public void setType(String type) {this.type = type;}
    public void setVelocity(double velocity) {this.velocity = velocity;}
    public void setDelay(int delay) {this.delay = delay;}
    public void setParticle(Particle particle) {this.particle = particle;}
    public void setRange(double range) {this.range = range;}
    public void setAngle(double angle) {this.angle = angle;}
    public void setBranches(int branches) {this.branches = branches;}
    public void setDamage(double damage) {this.damage = damage;}

    public int getDelay() {return delay;}
    public double getAngle() {return angle;}
    public double getRange() {return range;}
    public double getVelocity() {return velocity;}
    public int getBranches() {return branches;}
    public String getType() {return type;}
    public Particle getParticle() {return particle;}
    public double getDamage() {return damage;}






    public boolean editSetting(String setting, String value) {





        try {
            double doubleValue;
            switch (setting) {
                case "Range":
                    doubleValue = Double.parseDouble(value);
                    range = doubleValue;
                    return true;
                case "Damage":
                    doubleValue = Double.parseDouble(value);
                    damage = doubleValue;
                    return true;
                case "Velocity":
                    doubleValue = Double.parseDouble(value);
                    velocity = doubleValue;
                    return true;
                case "Delay":
                    int intValue = Integer.parseInt(value);
                    delay = intValue;
                    return true;
                case "Particle":
                    Particle particleValue = Particle.valueOf(value.toUpperCase());
                    particle = particleValue;
                    return true;
                default:
                    return false;



            }
        } catch (Exception e) {
            return false;
        }



    }



    public void castAsBeam(Location location, LivingEntity caster, Vector direction) {


        int looptimes = (int) (range*20 / velocity);
        direction.normalize().multiply(velocity/20);

            task = new BukkitRunnable() {
                @Override
                public void run() {

                    location.add(direction);
                    location.getWorld().spawnParticle(particle, location, 10, 0, 0, 0, 0.05);
                    cycles++;

                    //hit here
                    if (!location.getBlock().getType().equals(Material.AIR)) {

                        //hit event
                        this.cancel();

                    }

                    Collection<Entity> hit = location.getWorld().getNearbyEntities(location, 1, 1, 1);
                    hit.removeIf(entity -> !(entity instanceof LivingEntity));
                    if (!hit.isEmpty()) {
                        for (Entity entity : hit) {

                            ((LivingEntity) entity).damage(damage, caster);

                        }
                        //hit event
                        this.cancel();
                    }

                    if (cycles >= looptimes) {
                        this.cancel();
                    }

                }
            }.runTaskTimer(JavaPlugin.getPlugin(ProjectileMaker.class), 0, delay);
    }



    public static Projectile projectileFromName(String name) {
        return FileManager.jsonToProjectile(FileManager.CreateFile(FileManager.projectilesFolder, name + ".json"));
    }


    public static void createProjectile(String name, Player player) {
        if (name.equals("new request 123456")) {return;}
                name = name.replace(":", "");
                name = name.replace("/", "");
                name = name.replace("\\", "");
                name = name.replace(">", "");
                name = name.replace("<", "");
                name = name.replace("*", "");
                name = name.replace("?", "");
                name = name.replace("\"", "");
                name = name.replace("|", "");
                name = name.replace(" ", "_");

                if (name.length() < 3) {
                    Chat.sendPlayerChatRequest(player, "newProjectile");
                    player.sendMessage(Chat.colorMessage("&cThis name is too short!"));
                    return;
                }
                if (name.length() > 16) {
                    Chat.sendPlayerChatRequest(player, "newProjectile");
                    player.sendMessage(Chat.colorMessage("&cThis name is too long!"));
                    return;
                }

                YamlConfiguration modifyProjectileList = YamlConfiguration.loadConfiguration(FileManager.projectileList);

                List<String> projectileList = modifyProjectileList.getStringList("List");

                if (projectileList.contains(name)) {

                    Chat.sendPlayerChatRequest(player, "newProjectile");
                    player.sendMessage(Chat.colorMessage("&cThere is already an existing projectile with this name!"));
                    return;
                } else {

                    projectileList.add(name);
                    modifyProjectileList.set("List", projectileList);

                }

                try {
                    modifyProjectileList.save(FileManager.projectileList);
                } catch (IOException ex) {
                    System.out.println("Failed to save the projectile list!");
                }

                Projectile projectile = new Projectile(name);
                ProjectileMenu.openProjectileMenu(player);
    }

}
