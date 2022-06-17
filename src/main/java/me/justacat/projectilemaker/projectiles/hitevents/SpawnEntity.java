package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntity extends HitEvent {


    Parameter<EntityType> type;
    Parameter<Integer> amount;
    Parameter<Double> spread;


    public SpawnEntity(EntityType type, int amount, double spread) {
        super(Material.CREEPER_SPAWN_EGG);
        this.type = new Parameter<>("Type", type);
        this.amount = new Parameter<>("Amount", amount);
        this.spread = new Parameter<>("Spread", spread);
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
