package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class Explosion extends HitEvent{

    private float power = 3;
    private boolean fire = false;

    private boolean safe = true;


    public Explosion(float power, boolean fire, boolean safe) {
        this.power = power;
        this.fire = fire;
        this.safe = safe;
        updateParameters();
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        location.createExplosion(caster, power, fire, !safe);


    }

    private void updateParameters() {
        parameters.put("power", new Parameter<Float>("Power", power));
        parameters.put("fire", new Parameter<Boolean>("Fire", fire));
        parameters.put("safe", new Parameter<Boolean>("Safe", safe));
    }


    public void setFire(boolean fire) {this.fire = fire;}
    public boolean isFire() {return fire;}
    public boolean isSafe() {return safe;}

    public float getPower() {return power;}

    public void setPower(float power) {this.power = power;}

    public void setSafe(boolean safe) {this.safe = safe;}
}
