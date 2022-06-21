package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Explosion extends HitEvent {



    private Parameter<Float> power = new Parameter<>("Power", 3F);
    private Parameter<Boolean> fire = new Parameter<>("Fire", false);
    private Parameter<Boolean> safe = new Parameter<>("Safe", true);


    public Explosion() {
        super(Material.TNT, "Explosion", "Creates an explosion!");
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        if (power.getValue() > 100) {
            power.setValue(100F);
        }

        location.createExplosion(caster, power.getValue(), fire.getValue(), !safe.getValue());

    }

    @Override
    public List<Parameter<?>> getParameters() {
        List<Parameter<?>> parameterList = new ArrayList<>();

        parameterList.add(this.power);
        parameterList.add(this.fire);
        parameterList.add(this.safe);

        return parameterList;

    }





}