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

    protected String name;


    public static HashMap<String, HitEvent> hitEvents = new HashMap<>();
    public static HashMap<String, String> nameToDescription = new HashMap<>();
    public static HashMap<String, Material> nameToMaterial = new HashMap<>();

    public HitEvent(String name) {
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


    public String getName() {return name;}


    public static void registerHitEvent(HitEvent hitEvent, String description, Material display) {
        String name = hitEvent.getName();
        hitEvents.put(name, hitEvent);
        nameToDescription.put(name, description);
        nameToMaterial.put(name, display);
        FileManager.adapter.registerSubtype(hitEvent.getClass(), name);
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
