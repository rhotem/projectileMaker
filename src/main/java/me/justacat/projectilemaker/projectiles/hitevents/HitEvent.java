package me.justacat.projectilemaker.projectiles.hitevents;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface HitEvent {


    void trigger(Location location, LivingEntity caster);

}
