package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Explosion extends HitEvent {



    private Parameter<Float> power;
    private Parameter<Boolean> fire;
    private Parameter<Boolean> safe;


    public Explosion(float power, boolean fire, boolean safe) {
        super(Material.TNT);
        this.power = new Parameter<>("Power", power);
        this.fire = new Parameter<>("Fire", fire);
        this.safe = new Parameter<>("Safe", safe);
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