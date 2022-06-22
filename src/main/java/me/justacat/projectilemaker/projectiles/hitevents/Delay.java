package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class Delay extends HitEvent {

    Parameter<Double> time;

    public Delay() {
        super("Delay");
        time = new Parameter<>("Time", 10D);
    }



    @Override
    public void trigger(Location location, LivingEntity caster) {

    }

    @Override
    public List<Parameter<?>> getParameters() {
        return Collections.singletonList(time);
    }

    public double getDelay() {return time.getValue();}
}
