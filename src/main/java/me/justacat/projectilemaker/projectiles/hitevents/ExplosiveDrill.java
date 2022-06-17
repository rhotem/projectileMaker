package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.ProjectileMaker;
import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ExplosiveDrill extends HitEvent {


    private Parameter<Integer> length;

    private Parameter<Integer> power;

    private Parameter<Integer> delay;


    public ExplosiveDrill() {
        super(Material.TNT_MINECART);
        this.length = new Parameter<>("Length", 30);
        this.power = new Parameter<>("Power", 10);
        this.delay = new Parameter<>("Delay", 1);
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        Vector vector = location.getDirection();
        new BukkitRunnable() {

            private int cycles = 0;
            @Override
            public void run() {


                location.createExplosion(power.getValue());

                location.add(vector.normalize());


                if (cycles > length.getValue()) {
                    this.cancel();
                }
                cycles++;





            }
        }.runTaskTimer(JavaPlugin.getPlugin(ProjectileMaker.class), 0, delay.getValue());


    }

    @Override
    public List<Parameter<?>> getParameters() {

        List<Parameter<?>> parameterList = new ArrayList<>();

        parameterList.add(length);
        parameterList.add(power);
        parameterList.add(delay);

        return parameterList;


    }
}
