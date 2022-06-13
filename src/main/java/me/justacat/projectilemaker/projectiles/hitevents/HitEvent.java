package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class HitEvent {

    protected final UUID uuid = UUID.randomUUID();

    public static HashMap<UUID, List<Parameter<?>>> parameters = new HashMap<>();
    public abstract void trigger(Location location, LivingEntity caster);


    public UUID getUuid() {return uuid;}

    public abstract void load();

}
