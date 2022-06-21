package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class HitEvent {

    protected Material display;
    protected String name;
    protected String description;


    public static HashMap<String, HitEvent> hitEvents = new HashMap<>();

    public HitEvent(Material display, String name, String description) {
        this.display = display;
        this.description = description;
        this.name = name;
    }


    public abstract void trigger(Location location, LivingEntity caster);

    public abstract List<Parameter<?>> getParameters();

    public Parameter<?> getParameterByName(String name) {

        for (Parameter<?> parameter : getParameters()) {

            if (parameter.getName().equals(name)) {

                return parameter;
            }

        }
        return null;

    }

    public Material getDisplay() {return display;}
    public String getDescription() {return description;}
    public String getName() {return name;}


    public static void registerHitEvent(HitEvent hitEvent) {
        hitEvents.put(hitEvent.getName(), hitEvent);
        FileManager.adapter.registerSubtype(hitEvent.getClass(), hitEvent.getName());
    }

    public static List<HitEvent> getHitEvents() {

        List<HitEvent> list = new ArrayList<>();

        Set<String> keys = hitEvents.keySet();

        for (String key : keys) {

            list.add(hitEvents.get(key));

        }

        return list;

    }
}
