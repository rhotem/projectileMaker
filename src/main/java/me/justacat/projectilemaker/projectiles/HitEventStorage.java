package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.projectiles.hitevents.*;
import org.bukkit.entity.EntityType;

public class HitEventStorage {

    private Explosion explosion = null;
    private Drill drill = null;
    private Potion potion = null;
    private SpawnEntity spawnEntity = null;
    private String type;


    public static HitEventStorage newExplosion(float power, boolean fire, boolean safe) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setExplosion(new Explosion(power, fire, safe));
        hitEventStorage.setType("explosion");


        return hitEventStorage;
    }


    public static HitEventStorage newDrill(int length, boolean drops, int radius, int delay) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setDrill(new Drill(length, drops, radius, delay));
        hitEventStorage.setType("drill");

        return hitEventStorage;
    }

    public static HitEventStorage newSpawnEntity(EntityType type, int amount, double spread) {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setType("spawn");
        hitEventStorage.setSpawnEntity(new SpawnEntity(type, amount, spread));

        return hitEventStorage;
    }

    public static HitEventStorage newPotion() {

        HitEventStorage hitEventStorage = new HitEventStorage();

        hitEventStorage.setType("potion");
        hitEventStorage.setPotion(new Potion());

        return hitEventStorage;

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDrill(Drill drill) {this.drill = drill;}

    public void setSpawnEntity(SpawnEntity spawnEntity) {this.spawnEntity = spawnEntity;}

    public void setPotion(Potion potion) {this.potion = potion;}

    public void setExplosion(Explosion explosion) {this.explosion = explosion;}
    public HitEvent getHitEvent() {

        switch (type) {
            case "explosion":
                return explosion;
            case "drill":
                return drill;
            case "spawn":
                return spawnEntity;
            case "potion":
                return potion;
        }

        return null;

    }
}
