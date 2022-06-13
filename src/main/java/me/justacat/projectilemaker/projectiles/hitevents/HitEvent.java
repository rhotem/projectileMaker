package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public abstract class HitEvent {

    public abstract void trigger(Location location, LivingEntity caster);

    public abstract List<Parameter<?>> getParameters();
}
