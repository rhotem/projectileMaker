package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.projectiles.hitevents.Drill;
import me.justacat.projectilemaker.projectiles.hitevents.Explosion;
import me.justacat.projectilemaker.projectiles.hitevents.HitEvent;

public class HitEventStorage {

    private Explosion explosion = null;
    private Drill drill = null;
    private String type;


    public static HitEventStorage newExplosion(float power, boolean fire, boolean safe) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setExplosion(new Explosion(power, fire, safe));
        hitEventStorage.setType("explosion");


        return hitEventStorage;
    }


    public static HitEventStorage newDrill(int length, boolean drops, int radius) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setDrill(new Drill(length, drops, radius));
        hitEventStorage.setType("drill");

        return hitEventStorage;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDrill(Drill drill) {this.drill = drill;}

    public void setExplosion(Explosion explosion) {this.explosion = explosion;}
    public HitEvent getHitEvent() {

        if (type.equals("explosion")) {
            return explosion;
        } else if (type.equals("drill")) {
            return drill;
        }

        return null;

    }
}
