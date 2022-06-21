package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.ProjectileMaker;
import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import me.justacat.projectilemaker.misc.Parameter;
import me.justacat.projectilemaker.projectiles.hitevents.Delay;
import me.justacat.projectilemaker.projectiles.hitevents.Explosion;
import me.justacat.projectilemaker.projectiles.hitevents.HitEvent;
import org.bukkit.Bukkit;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Projectile {

    //general settings
    private String name;
    private String type = "Beam";



    private Parameter<Double> range = new Parameter<>("Range", 20.0, Material.BOW);
    private Parameter<Double> velocity = new Parameter<>("Velocity", 10.0, Material.SUGAR);
    private Parameter<Particle> particle = new Parameter<>("Particle", Particle.FLAME, Material.REDSTONE);

    private Parameter<Integer> delay = new Parameter<>("Tick Delay", 1, Material.CLOCK);
    private Parameter<Integer> castDelay = new Parameter<>("Cast Delay", 1, Material.CLOCK);
    private Parameter<Double> damage = new Parameter<>("Damage", 5.0, Material.IRON_SWORD);

    private Parameter<Double> knockback = new Parameter<>("Knockback", 0.2, Material.SLIME_BALL);

    private Parameter<Double> particleOffset = new Parameter<>("Particle Offset", 0D, Material.YELLOW_DYE);

    private Parameter<Double> particleOffsetY = new Parameter<>("Particle Offset Y", 0D, Material.GREEN_DYE);

    private Parameter<Integer> particleAmount = new Parameter<>("Particle Amount", 10, Material.REDSTONE_LAMP);

    private Parameter<Double> particleSpeed = new Parameter<>("Particle Speed", 0.05, Material.FEATHER);
    //spiral

    private int branches;
    private double angle;

    //cone

    //misc


    private List<HitEvent> hitEventList = new ArrayList<>();

    public static HashMap<Integer, Integer> cycles = new HashMap<>();


    public static HashMap<String, Projectile> loadedProjectiles = new HashMap<>();

    public Projectile(String name) {
        this.name = name;
        FileManager.createJSON(name, FileManager.projectilesFolder, this, true);
        loadedProjectiles.put(name, this);
        hitEventList.add(new Explosion());
    }


    public void saveProjectile() {FileManager.createJSON(name, FileManager.projectilesFolder, this, true);}


    public void deleteHitEvent(int index) {hitEventList.remove(index);}
    public void addHitEvent(HitEvent hitEvent) {hitEventList.add(hitEvent);}



    public List<HitEvent> getHitEventList() {return hitEventList;}


    public List<Parameter<?>> getParameters() {

        List<Parameter<?>> parameters = new ArrayList<>();

        parameters.add(range);
        parameters.add(velocity);
        parameters.add(delay);
        parameters.add(castDelay);
        parameters.add(damage);
        parameters.add(knockback);
        parameters.add(particle);
        parameters.add(particleAmount);
        parameters.add(particleOffset);
        parameters.add(particleOffsetY);
        parameters.add(particleSpeed);

        return parameters;

    }

    public Parameter<?> getParameterByName(String name) {

        for (Parameter<?> parameter : getParameters()) {

            if (parameter.getName().equalsIgnoreCase(name)) {

                return parameter;
            }

        }
        return null;

    }




    public void cast(Location location, LivingEntity caster, Vector direction) {



        if (type.equals("Beam")) {
            this.castAsBeam(location, caster, direction);
        }

    }



    public void castAsBeam(Location location, LivingEntity caster, Vector direction) {


        int looptimes = (int) (range.getValue() * 20 / velocity.getValue());
        direction.normalize().multiply(velocity.getValue() / 20);

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {

                    int ID = this.getTaskId();


                    location.add(direction);
                    location.getWorld().spawnParticle(particle.getValue(), location, (int) particleAmount.getValue(), (double) particleOffset.getValue(), (double) particleOffsetY.getValue(), (double) particleOffset.getValue(),(double) particleSpeed.getValue());

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

                            ((LivingEntity) entity).damage(damage.getValue(), caster);
                            entity.setVelocity(entity.getVelocity().add(direction.multiply(knockback.getValue())));

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
            }.runTaskTimer(JavaPlugin.getPlugin(ProjectileMaker.class), castDelay.getValue(), delay.getValue());
    }




    public void hit(Location location, LivingEntity caster) {

        double delay = 0;
        for (HitEvent hitEvent : this.hitEventList) {

            if (hitEvent instanceof Delay) {
                delay = delay + ((Delay) hitEvent).getDelay();
            } else {
                Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(ProjectileMaker.class), () -> {
                    hitEvent.trigger(location, caster);
                }, (long) delay);
            }

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
