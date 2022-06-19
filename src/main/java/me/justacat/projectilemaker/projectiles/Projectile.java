package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.ProjectileMaker;
import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class Projectile {

    //general settings
    private String name;
    private String type = "Beam";

    private double range = 20.0;
    private double velocity = 10.0;
    private Particle particle = Particle.FLAME;
    private int delay = 1;
    private double damage = 5;

    private double knockback = 1;
    //spiral

    private int branches;
    private double angle;

    //cone

    //misc

    private List<HitEventStorage> hitEventList = new ArrayList<>();

    public static HashMap<Integer, Integer> cycles = new HashMap<>();


    public static HashMap<String, Projectile> loadedProjectiles = new HashMap<>();

    public Projectile(String name) {
        this.name = name;
        FileManager.createJSON(name, FileManager.projectilesFolder, this, true);
        loadedProjectiles.put(name, this);
        hitEventList.add(HitEventStorage.newExplosion(3, false, true));
    }


    public void saveProjectile() {FileManager.createJSON(name, FileManager.projectilesFolder, this, true);}


    public void deleteHitEvent(int index) {hitEventList.remove(index);}
    public void addHitEvent(HitEventStorage hitEventStorage) {hitEventList.add(hitEventStorage);}


    public int getDelay() {return delay;}
    public double getAngle() {return angle;}
    public double getRange() {return range;}
    public double getVelocity() {return velocity;}
    public int getBranches() {return branches;}
    public String getType() {return type;}
    public Particle getParticle() {return particle;}
    public double getDamage() {return damage;}

    public double getKnockback() {return knockback;}

    public List<HitEventStorage> getHitEventList() {return hitEventList;}





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
                    Particle particleValue = Particle.valueOf(value.toUpperCase().replace(" ", "_"));
                    particle = particleValue;
                    return true;
                default:
                    return false;



            }
        } catch (Exception e) {
            return false;
        }



    }


    public void cast(Location location, LivingEntity caster, Vector direction) {



        if (type.equals("Beam")) {
            this.castAsBeam(location, caster, direction);
        }

    }



    public void castAsBeam(Location location, LivingEntity caster, Vector direction) {


        int looptimes = (int) (range*20 / velocity);
        direction.normalize().multiply(velocity/20);

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {

                    int ID = this.getTaskId();


                    location.add(direction);
                    location.getWorld().spawnParticle(particle, location, 10, 0, 0, 0, 0.05);

                    if (!cycles.containsKey(ID)) {
                        cycles.put(ID, 0);
                    } else {
                        cycles.put(ID, cycles.get(ID) + 1);
                    }

                    if (!location.getBlock().getType().equals(Material.AIR)) {


                        hit(location, caster);

                        cycles.remove(ID);
                        this.cancel();
                        return;

                    }

                    Collection<Entity> hit = location.getWorld().getNearbyEntities(location, 1, 1, 1);
                    hit.removeIf(entity -> !(entity instanceof LivingEntity));
                    hit.remove(caster);
                    if (!hit.isEmpty()) {
                        for (Entity entity : hit) {

                            ((LivingEntity) entity).damage(damage, caster);
                            entity.setVelocity(entity.getVelocity().add(direction.multiply(knockback)));

                        }

                        hit(location, caster);

                        cycles.remove(ID);
                        this.cancel();
                        return;
                    }

                    if (cycles.get(ID) >= looptimes) {

                        hit(location, caster);
                        cycles.remove(ID);
                        this.cancel();

                    }

                }
            }.runTaskTimer(JavaPlugin.getPlugin(ProjectileMaker.class), 0, delay);
    }




    public void hit(Location location, LivingEntity caster) {

        for (HitEventStorage hitEventStorage : this.hitEventList) {
            hitEventStorage.getHitEvent().trigger(location, caster);
        }

    }




    public static Projectile projectileFromName(String name, boolean loaded) {
        if (loaded) {
            return loadedProjectiles.get(name);
        } else {
            return FileManager.jsonToProjectile(FileManager.CreateFile(FileManager.projectilesFolder, name + ".json"));
        }

    }


    public static void createProjectile(String name, Player player) {

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


        List<String> projectileList = FileManager.getProjectileList();

        if (projectileList.contains(name)) {

            Chat.sendPlayerChatRequest(player, "newProjectile");
            player.sendMessage(Chat.colorMessage("&cThere is already an existing projectile with this name!"));
            return;
        }

        Projectile projectile = new Projectile(name);
        projectile.saveProjectile();
        ProjectileMenu.openProjectileMenu(player);



    }

}
