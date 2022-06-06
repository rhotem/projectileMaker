package me.justacat.projectilemaker.projectiles;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface HitEvent {

    void trigger(Location location, LivingEntity caster);

}
