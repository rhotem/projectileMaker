package me.justacat.ArcaneProjectiles.projectiles;

import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.gui.ProjectileMenu;
import me.justacat.ArcaneProjectiles.listeners.Mana;
import me.justacat.ArcaneProjectiles.misc.Chat;
import me.justacat.ArcaneProjectiles.misc.CooldownManager;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

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

    private Parameter<Double> cooldown = new Parameter<>("Cooldown", 0.0, Material.CLOCK);

    private Parameter<Double> manaCost = new Parameter<>("Mana Cost", 0.0, Material.WATER_BUCKET);

    //spiral

    private Parameter<Double> radius = new Parameter<>("Radius", 1.0, Material.MAP);
    private Parameter<Integer> branches = new Parameter<>("Branches", 5, Material.STICK);
    private Parameter<Double> angle = new Parameter<>("Angle", 10.0, Material.IRON_INGOT);


    //physical

    private Parameter<EntityType> entityType = new Parameter<>("Entity Type", EntityType.ARROW, Material.SHEEP_SPAWN_EGG);

    private Parameter<Boolean> gravity = new Parameter<>("Gravity", true, Material.IRON_BOOTS);

    private Parameter<Boolean> killOnHit = new Parameter<>("Kill On Hit", true, Material.DIAMOND_SWORD);

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
    public long getCooldown() {return cooldown.getValue().longValue();}
    public String getType() {return type.getValue();}
    public List<Parameter<?>> getParameters() {

        switch (type.getValue()) {
            case "Beam":
                return getBeamParameters();
            case "Spiral":
                return getSpiralParameters();
            case "Physical":
                return getPhysicalParameters();
        }

        return new ArrayList<>();
    }

    public List<Parameter<?>> getAllParameters() {

        List<Parameter<?>> parameters = new ArrayList<>(getBeamParameters());
        parameters.addAll(getSpiralParameters());
        parameters.addAll(getPhysicalParameters());

        List<Parameter<?>> parameterList = new ArrayList<>();

        for (Parameter<?> parameter : parameters) {

            if (!parameterList.contains(parameter)) {
                parameterList.add(parameter);
            }

        }

        return parameterList;

    }

    public Parameter<?> getParameterByNameFromAllParameters(String name) {
        List<Parameter<?>> parameters = getAllParameters();

        parameters.removeIf(Objects::isNull);

        for (Parameter<?> parameter : parameters) {

            if (parameter.getName().equalsIgnoreCase(name)) {

                return parameter;
            }

        }
        return null;
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
        parameters.add(cooldown);
        parameters.add(manaCost);

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

    private List<Parameter<?>> getPhysicalParameters() {

        List<Parameter<?>> parameters = getBeamParameters();

        parameters.add(entityType);
        parameters.add(gravity);
        parameters.add(killOnHit);

        parameters.remove(range);

        return parameters;

    }
    public Parameter<?> getParameterByName(String name) {

        List<Parameter<?>> parameters = getParameters();

        parameters.removeIf(Objects::isNull);

        for (Parameter<?> parameter : parameters) {

            if (parameter.getName().equalsIgnoreCase(name)) {

                return parameter;
            }

        }
        return null;

    }




    public void cast(Location location, LivingEntity caster, Vector direction) {


        if (ArcaneProjectiles.worldGuardEnabled) {

            if (!ArcaneProjectiles.worldGuardManager.checkFlag(location)) {
                caster.sendMessage(Chat.colorMessage("&cSeems like you can't use this here!"));
                return;
            }

        }


        FileConfiguration config = ArcaneProjectiles.instance.getConfig();


        if (config.getBoolean("Permissions.Enabled")) {

            if (config.get("Permissions.Permission") instanceof String) {
                if (caster.hasPermission(config.getString("Permissions.Permission").replace("{ProjName}", name))) {

                    caster.sendMessage(Chat.colorMessage(config.getString("Permissions.Message")));
                    return;

                }
            }



        }

        if (!CooldownManager.cooldownManagerMap.containsKey(name)) {
            new CooldownManager(name);
        }

        if (CooldownManager.cooldownManagerMap.get(name).getProjectileCooldown() != cooldown.getValue()) {
            new CooldownManager(name);
        }

        CooldownManager cooldownManager = CooldownManager.cooldownManagerMap.get(name);

        if (caster instanceof Player) {

            Player player = (Player) caster;

            if (cooldownManager.isInCooldown(player)) {

                if (config.getBoolean("Send-Cooldown-Message.Enabled")) {

                    if (config.getString("Send-Cooldown-Message.Message") == null) return;

                    String itemName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                    String itemType = player.getInventory().getItemInMainHand().getType().name().replace("_", " ").toLowerCase();

                    String message = config.getString("Send-Cooldown-Message.Message").replace("{Time}", String.valueOf(cooldownManager.getPlayerCooldown(player))).replace("{ItemName}", itemName.isEmpty() ? itemType : itemName).replace("{ProjName}", name);

                    caster.sendMessage(Chat.colorMessage(message));

                }
                return;
            }

            cooldownManager.putInCooldown(player);

            if (config.getBoolean("Mana.Enabled")) {

                if (Mana.getMana(player) > manaCost.getValue()) {

                    Mana.addMana(player, -manaCost.getValue());

                } else {
                    player.sendMessage(Chat.colorMessage(config.getString("Mana.NotEnoughManaMessage")));
                    return;
                }

            }
        }




        switch (type.getValue()) {
            case "Beam":
                castAsBeam(location, caster, direction);
                break;
            case "Spiral":
                castAsSpiral(location, caster, direction);
                break;
            case "Physical":
                castAsPhysical(location, caster, direction);
                break;
        }


    }



    public void castAsPhysical(Location location, LivingEntity caster, Vector direction) {


        final Entity entity = location.getWorld().spawnEntity(location, entityType.getValue());

        entity.setGravity(gravity.getValue());

        if (entity instanceof org.bukkit.entity.Projectile) {

            ((org.bukkit.entity.Projectile) entity).setShooter(caster);
            entity.setMetadata("ProjectileName." + name, new FixedMetadataValue(ArcaneProjectiles.instance, "true"));
        }

        new BukkitRunnable() {

            Location loc = entity.getLocation();
            final long time = System.currentTimeMillis();
            @Override
            public void run() {



                if (entity.isDead() || entity.isOnGround() || System.currentTimeMillis() - time > 15000) {

                    if (killOnHit.getValue()) entity.remove();

                    this.cancel();
                    hit(loc, caster);
                }


                if (homing.getValue() != 0) {
                    LivingEntity nearest = nearestEntity(entity.getLocation(), new Entity[]{caster, entity}, 15);
                    if (nearest != null) {

                        Vector vector = new Vector(nearest.getLocation().getX() - entity.getLocation().getX(), nearest.getLocation().getY() - entity.getLocation().getY(), nearest.getLocation().getZ() - entity.getLocation().getZ());
                        entity.setVelocity(entity.getVelocity().add(direction.normalize().multiply(velocity.getValue() / 10).add(vector.normalize().multiply(homing.getValue()))));

                    } else {
                        entity.setVelocity(entity.getVelocity().add(direction.normalize().multiply(velocity.getValue() / 10)));

                    }
                } else {
                    entity.setVelocity(entity.getVelocity().add(direction.normalize().multiply(velocity.getValue() / 10)));
                }

                entity.getWorld().spawnParticle(particle.getValue(), entity.getLocation(), (int) particleAmount.getValue(), (double) particleOffset.getValue(), (double) particleOffsetY.getValue(), (double) particleOffset.getValue(),(double) particleSpeed.getValue());
                loc = entity.getLocation();


            }
        }.runTaskTimer(ArcaneProjectiles.instance, castDelay.getValue(), delay.getValue());





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
                        Entity nearest = nearestEntity(location,new LivingEntity[]{caster}, 15);
                        Location nearestLocation = nearest.getLocation();
                        location.add(new Vector(nearestLocation.getX() - location.getX(), nearestLocation.getY() - location.getY(), nearestLocation.getZ() - location.getZ()).normalize().multiply(homing.getValue()));


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
            return loadedProjectiles.getOrDefault(name, null);
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

        loadedProjectiles.put(name, projectileFromName(name, false)); //reloads it to prevent hit effects running on the same instance

        ProjectileMenu.openProjectileMenu(player);



    }


    private LivingEntity nearestEntity(Location location, Entity[] doNotInclude, int radius) {

        Collection<LivingEntity> livingEntities = location.getNearbyLivingEntities(radius);

        for (Entity entity : doNotInclude) {

            if (entity instanceof LivingEntity) {
                livingEntities.remove(entity);
            }

        }

        if (!livingEntities.isEmpty()) {
            LivingEntity nearest = null;
            double distance = 1000;
            for (LivingEntity near : livingEntities) {

                if (nearest == null | distance > location.distance(near.getLocation())) {
                    nearest = near;
                    distance = location.distance(near.getLocation());
                }

            }
            return nearest;
        }
        return null;

    } 
}
