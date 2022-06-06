package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.projectiles.HitEvent;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Explosion implements HitEvent {

    private float power;
    private boolean fire;

    private boolean safe;


    public Explosion(){}
    public Explosion(float power, boolean fire, boolean safe) {
        this.power = power;
        this.fire = fire;
        this.safe = safe;
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        location.createExplosion(caster, power, fire, !safe);

    }
}
