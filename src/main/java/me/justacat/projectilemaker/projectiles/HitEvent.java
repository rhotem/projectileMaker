package me.justacat.projectilemaker.projectiles;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class HitEvent {


    public abstract void trigger(Location location, LivingEntity caster);

}
