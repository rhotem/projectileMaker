package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntity extends HitEvent {


    Parameter<EntityType> type = new Parameter<>("Type", EntityType.ZOMBIE);
    Parameter<Integer> amount = new Parameter<>("Amount", 3);
    Parameter<Double> spread = new Parameter<>("Spread", 5.0);


    public SpawnEntity() {
        super(Material.CREEPER_SPAWN_EGG, "Spawn Entity", "Spawns entities!");
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        for (int i = 0; i < amount.getValue(); i++) {

            double x = Math.random() * spread.getValue() * 2 - spread.getValue();
            double y = Math.random() * spread.getValue() * 2 - spread.getValue();
            double z = Math.random() * spread.getValue() * 2 - spread.getValue();

            Location spawnLoc = new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z);
            spawnLoc.getWorld().spawnEntity(spawnLoc, type.getValue(), true);
        }



    }

    @Override
    public List<Parameter<?>> getParameters() {

        List<Parameter<?>> parameterList = new ArrayList<>();

        parameterList.add(type);
        parameterList.add(amount);
        parameterList.add(spread);

        return parameterList;
    }
}
