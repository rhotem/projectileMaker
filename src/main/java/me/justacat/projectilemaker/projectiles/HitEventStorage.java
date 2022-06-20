package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.projectiles.hitevents.*;
import org.bukkit.entity.EntityType;

public class HitEventStorage {

    private Explosion explosion = null;
    private Drill drill = null;
    private Potion potion = null;
    private SpawnEntity spawnEntity = null;
    private ExplosiveDrill explosiveDrill = null;
    private BackSender backSender = null;
    private Delay delay = null;
    private String type;



    public static HitEventStorage newExplosion(float power, boolean fire, boolean safe) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setExplosion(new Explosion(power, fire, safe));
        hitEventStorage.setType("Explosion");


        return hitEventStorage;
    }


    public static HitEventStorage newDrill(int length, boolean drops, int radius, int delay) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setDrill(new Drill(length, drops, radius, delay));
        hitEventStorage.setType("Drill");

        return hitEventStorage;
    }

    public static HitEventStorage newSpawnEntity(EntityType type, int amount, double spread) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setType("Spawn");
        hitEventStorage.setSpawnEntity(new SpawnEntity(type, amount, spread));

        return hitEventStorage;
    }

    public static HitEventStorage newPotion() {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setType("Potion");
        hitEventStorage.setPotion(new Potion());

        return hitEventStorage;

    }

    public static HitEventStorage newExplosiveDrill() {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setType("Explosive Drill");
        hitEventStorage.setExplosiveDrill(new ExplosiveDrill());

        return hitEventStorage;

    }

    public static HitEventStorage newBackSender() {
        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setType("Back To The Sender");
        hitEventStorage.setBackSender(new BackSender());

        return hitEventStorage;
    }

    public static HitEventStorage newDelay() {
        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setType("Delay");
        hitEventStorage.setDelay(new Delay());

        return hitEventStorage;

    }

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public void setDrill(Drill drill) {this.drill = drill;}
    public void setExplosiveDrill(ExplosiveDrill explosiveDrill) {this.explosiveDrill = explosiveDrill;}

    public void setDelay(Delay delay) {this.delay = delay;}

    public void setSpawnEntity(SpawnEntity spawnEntity) {this.spawnEntity = spawnEntity;}

    public void setPotion(Potion potion) {this.potion = potion;}

    public void setBackSender(BackSender backSender) {this.backSender = backSender;}

    public void setExplosion(Explosion explosion) {this.explosion = explosion;}
    public HitEvent getHitEvent() {

        switch (type) {
            case "Explosion":
                return explosion;
            case "Drill":
                return drill;
            case "Spawn":
                return spawnEntity;
            case "Potion":
                return potion;
            case "Explosive Drill":
                return explosiveDrill;
            case "Back To The Sender":
                return backSender;
            case "Delay":
                return delay;
        }

        return null;

    }
}
