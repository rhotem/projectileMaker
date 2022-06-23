package me.justacat.ArcaneProjectiles.projectiles.hitevents;

import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.misc.Parameter;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class BackSender extends HitEvent {


    Parameter<String> projectileType;


    public BackSender() {
        super("Back To The Sender!");
        projectileType = new Parameter<>("Projectile Type", FileManager.getProjectileList().get(0));

    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        Location casterLoc = caster.getLocation();

        Vector vector = new Vector(casterLoc.getX() - location.getX(), casterLoc.getY() - location.getY(), casterLoc.getZ() - location.getZ());


        if (vector.length() <= 4) {
            return;
        }

        Projectile projectile;
        if (Projectile.projectileFromName(projectileType.getValue(), true) == null) {
            Bukkit.getLogger().log(Level.WARNING, "Invalid projectile!");
            projectile = Projectile.projectileFromName(FileManager.getProjectileList().get(0), true);
        } else {
            projectile = Projectile.projectileFromName(projectileType.getValue(), true);
        }
        projectile.cast(location, caster, vector);

    }

    @Override
    public List<Parameter<?>> getParameters() {
        return Collections.singletonList(projectileType);
    }
}
