package me.justacat.ArcaneProjectiles.projectiles.hitevents;

import me.justacat.ArcaneProjectiles.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntity extends HitEvent {


    Parameter<EntityType> entityType = new Parameter<>("Type", EntityType.ZOMBIE);
    Parameter<Integer> amount = new Parameter<>("Amount", 3);
    Parameter<Double> spread = new Parameter<>("Spread", 5.0);


    public SpawnEntity() {
        super("Spawn Entity");
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        for (int i = 0; i < amount.getValue(); i++) {

            double x = Math.random() * spread.getValue() * 2 - spread.getValue();
            double y = Math.random() * spread.getValue() * 2 - spread.getValue();
            double z = Math.random() * spread.getValue() * 2 - spread.getValue();

            Location spawnLoc = new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z);
            spawnLoc.getWorld().spawnEntity(spawnLoc, entityType.getValue(), true);
        }



    }

    @Override
    public List<Parameter<?>> getParameters() {

        List<Parameter<?>> parameterList = new ArrayList<>();

        parameterList.add(entityType);
        parameterList.add(amount);
        parameterList.add(spread);

        return parameterList;
    }
}
