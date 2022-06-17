package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public abstract class HitEvent {

    protected Material display;



    public HitEvent(Material display) {this.display = display;}


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
}
