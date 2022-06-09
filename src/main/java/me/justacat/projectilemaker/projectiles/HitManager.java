package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.projectiles.hitevents.Explosion;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class HitManager {

    private Explosion explosion;
    private String type;

    public HitManager(float power, boolean fire, boolean safe) {
        this.explosion = new Explosion(power, fire, safe);
        this.type = "boom";
    }

    public void trigger(Location location, LivingEntity caster) {
        if (type.equals("boom")) {
            explosion.trigger(location, caster);
        }
    }

}
