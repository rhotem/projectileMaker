package me.justacat.ArcaneProjectiles.projectiles;

import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.gui.ProjectileMenu;
import me.justacat.ArcaneProjectiles.misc.Chat;
import me.justacat.ArcaneProjectiles.misc.Parameter;
import me.justacat.ArcaneProjectiles.misc.VecMath;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.Delay;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.Explosion;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.HitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Projectile {

    //general settings
    private String name;
    private Parameter<String> type = new Parameter<>("Type", "Beam", Material.PAPER);

    private Parameter<Material> display = new Parameter<>("Display", Material.BLAZE_ROD, Material.ANVIL);

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

    private Parameter<Double> particleSpeed = new Parameter<>("Particle Speed", 0.0, Material.FEATHER);

    private Parameter<Double> homing = new Parameter<>("Homing Rate", 0.0, Material.BREWING_STAND);

    //spiral

    private Parameter<Double> radius = new Parameter<>("Radius", 1.0, Material.MAP);
    private Parameter<Integer> branches = new Parameter<>("Branches", 5, Material.STICK);
    private Parameter<Double> angle = new Parameter<>("Angle", 10.0, Material.IRON_INGOT);

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

    public String getType() {return type.getValue();}
    public List<Parameter<?>> getParameters() {

        if (type.getValue().equals("Beam")) {
            return getBeamParameters();
        } else if (type.getValue().equals("Spiral")) {
            return getSpiralParameters();
        }

        return new ArrayList<>();
    }

    private List<Parameter<?>> getBeamParameters() {
        List<Parameter<?>> parameters = new ArrayList<>();

        parameters.add(type);
        parameters.add(display);
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
        parameters.add(homing);

        return parameters;
    }

    private List<Parameter<?>> getSpiralParameters() {
        List<Parameter<?>> parameters = getBeamParameters();

        parameters.add(angle);
        parameters.add(radius);
        parameters.add(branches);

        parameters.remove(homing);

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



        if (type.getValue().equals("Beam")) {
            castAsBeam(location, caster, direction);
        } else if (type.getValue().equals("Spiral")) {
            castAsSpiral(location, caster, direction);
        }

    }





    public void castAsBeam(Location location, LivingEntity caster, Vector direction) {


        int looptimes = (int) (range.getValue() * 20 / velocity.getValue());
        direction.normalize().multiply(velocity.getValue() / 20);

            new BukkitRunnable() {
                @Override
                public void run() {

                    int ID = this.getTaskId();


                    location.add(direction);

                    //homing
                    if (homing.getValue() != 0) {
                        Collection<LivingEntity> livingEntities = location.getNearbyLivingEntities(10);
                        livingEntities.remove(caster);
                        if (!livingEntities.isEmpty()) {
                            Entity nearest = null;
                            double distance = 1000;
                            for (LivingEntity near : livingEntities) {

                                if (nearest == null | distance > location.distance(near.getLocation())) {
                                    nearest = near;
                                    distance = location.distance(near.getLocation());
                                }

                            }
                            Location nearestLocation = nearest.getLocation();
                            location.add(new Vector(nearestLocation.getX() - location.getX(), nearestLocation.getY() - location.getY(), nearestLocation.getZ() - location.getZ()).normalize().multiply(homing.getValue()));
                        }

                    }

                    try {
                        location.getWorld().spawnParticle(particle.getValue(), location, (int) particleAmount.getValue(), (double) particleOffset.getValue(), (double) particleOffsetY.getValue(), (double) particleOffset.getValue(),(double) particleSpeed.getValue());
                    } catch (Exception e) {
                        Bukkit.getLogger().warning("oops, seems like this particle is not supported by this version!");
                    }


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
            }.runTaskTimer(JavaPlugin.getPlugin(ArcaneProjectiles.class), castDelay.getValue(), delay.getValue());
    }


    public void castAsSpiral(Location location, LivingEntity caster, Vector direction) {

        int looptimes = (int) (range.getValue() * 20 / velocity.getValue());
        direction.normalize().multiply(velocity.getValue() / 20);
        Vector perpendicular = VecMath.perpendicular(direction);
        perpendicular.normalize();
        boolean[] hits = new boolean[branches.getValue()];
        for (int h = 0; h < branches.getValue(); h++) {hits[h] = false;}
        new BukkitRunnable() {

                Location loc;
                int cycles = 0;
                final int branches = Projectile.this.branches.getValue();
                @Override
                public void run() {

                    cycles++;
                    location.add(direction);
                    if (cycles >= looptimes) {

                        for (int b = 0; b < branches; b++) {
                            if (!hits[b]) {

                                loc = location.clone();
                                loc.add(perpendicular.clone().multiply((cycles * velocity.getValue() / 20) * Math.tan(Math.toRadians(angle.getValue())) + radius.getValue()));



                                hits[b] = true;
                                hit(loc, caster);




                            }
                            perpendicular.rotateAroundAxis(direction, Math.toRadians(360.0 / branches));
                        }
                        this.cancel();

                    }


                    if (particle != null) {
                        for (int b = 0; b < branches; b++) {
                            if (!hits[b]) {

                                loc = location.clone();
                                loc.add(perpendicular.clone().multiply((cycles * velocity.getValue() / 20) * Math.tan(Math.toRadians(angle.getValue())) + radius.getValue()));

                                try {
                                    loc.getWorld().spawnParticle(particle.getValue(), loc,(int) particleAmount.getValue(),(double) particleOffset.getValue(),(double) particleOffsetY.getValue(),(double) particleOffset.getValue(),(double) particleSpeed.getValue());
                                } catch (Exception e) {
                                    Bukkit.getLogger().warning("oops, seems like this particle is not supported by this version!");
                                }

                                Collection<Entity> hit = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                                hit.removeIf(entity -> !(entity instanceof LivingEntity));
                                hit.remove(caster);

                                if (!hit.isEmpty()) {
                                    hits[b] = true;
                                    hit(loc, caster);
                                    for (Entity entity : hit) {
                                        ((LivingEntity) entity).damage(damage.getValue(), caster);
                                    }
                                }
                                Block block = loc.getBlock();
                                if (block.getType() != Material.AIR) {
                                    hits[b] = true;
                                    hit(loc, caster);
                                }

                            }
                            perpendicular.rotateAroundAxis(direction, Math.toRadians(360.0 / branches));
                        }
                        perpendicular.rotateAroundAxis(direction, Math.toRadians(angle.getValue()));
                    }
                }
            }.runTaskTimer(JavaPlugin.getPlugin(ArcaneProjectiles.class), 0, delay.getValue());
    }



    public void hit(Location location, LivingEntity caster) {

        double delay = 0;
        for (HitEvent hitEvent : this.hitEventList) {

            if (hitEvent instanceof Delay) {
                delay = delay + ((Delay) hitEvent).getDelay();
            } else {
                Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(ArcaneProjectiles.class), () -> {
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
