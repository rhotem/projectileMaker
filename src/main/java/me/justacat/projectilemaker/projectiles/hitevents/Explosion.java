package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Explosion extends HitEvent {

    private Parameter<Float> power;
    private Parameter<Boolean> fire;
    private Parameter<Boolean> safe;


    public Explosion(float power, boolean fire, boolean safe) {
        this.power = new Parameter<>("Power", power);
        this.fire = new Parameter<>("Fire", fire);
        this.safe = new Parameter<>("Safe", safe);
        List<Parameter<?>> parameterList = new ArrayList<>();

        parameterList.add(this.power);
        parameterList.add(this.fire);
        parameterList.add(this.safe);

        parameters.put(this, parameterList);
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        location.createExplosion(caster, power.getValue(), fire.getValue(), !safe.getValue());

    }


    public Parameter<Boolean> getFire() {return fire;}
    public Parameter<Boolean> getSafe() {return safe;}

    public Parameter<Float> getPower() {return power;}
    public void setFire(Parameter<Boolean> fire) {this.fire = fire;}

    public void setPower(Parameter<Float> power) {this.power = power;}
    public void setSafe(Parameter<Boolean> safe) {this.safe = safe;}
}