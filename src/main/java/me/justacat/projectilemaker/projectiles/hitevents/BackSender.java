package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import me.justacat.projectilemaker.projectiles.Projectile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;

public class BackSender extends HitEvent {


    Parameter<Projectile> type;


    public BackSender() {
        super(Material.ALLIUM);
        Projectile[] array = Projectile.loadedProjectiles.values().toArray(new Projectile[]{});
        type = new Parameter<>("Type", array[0]);
        if (type.getValue() == null) {
            Projectile projectile = new Projectile("newProjectile");
            type.setValue(Projectile.projectileFromName("newProjectile", true));
        }
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        Location casterLoc = caster.getLocation();

        Vector vector = new Vector(casterLoc.getX() - location.getX(), casterLoc.getY() - location.getY(), casterLoc.getZ() - location.getZ());
        type.getValue().cast(location, caster, vector);

    }

    @Override
    public List<Parameter<?>> getParameters() {
        return Collections.singletonList(type);
    }
}
