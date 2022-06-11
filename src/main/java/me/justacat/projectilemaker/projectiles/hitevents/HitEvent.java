package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

public abstract class HitEvent {


    public HashMap<String, Parameter<?>> parameters = new HashMap<>();
    public abstract void trigger(Location location, LivingEntity caster);

}
