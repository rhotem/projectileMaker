package me.justacat.projectilemaker.projectiles.hitevents;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class Explosion {

    private float power = 3;
    private boolean fire = false;

    private boolean safe = true;


    public Explosion(float power, boolean fire, boolean safe) {
        this.power = power;
        this.fire = fire;
        this.safe = safe;

    }


    public void trigger(Location location, LivingEntity caster) {

        location.createExplosion(caster, power, fire, !safe);


    }


    public void setFire(boolean fire) {this.fire = fire;}
    public boolean isFire() {return fire;}
    public boolean isSafe() {return safe;}

    public float getPower() {return power;}

    public void setPower(float power) {this.power = power;}

    public void setSafe(boolean safe) {this.safe = safe;}
}
