package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.projectiles.hitevents.Explosion;
import me.justacat.projectilemaker.projectiles.hitevents.HitEvent;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class HitEventStorage {

    private Explosion explosion;
    private String type;

    public HitEventStorage(float power, boolean fire, boolean safe) {
        this.explosion = new Explosion(power, fire, safe);
        this.type = "explosion";
    }

    public void trigger(Location location, LivingEntity caster) {
        if (type.equals("explosion")) {
            explosion.trigger(location, caster);
        }
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public HitEvent getHitEvent() {

        if (type.equals("explosion")) {
            return explosion;
        }

        return null;

    }
}
