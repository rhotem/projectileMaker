package me.justacat.ArcaneProjectiles.projectiles.hitevents;

import me.justacat.ArcaneProjectiles.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayParticle extends HitEvent {

    private Parameter<Particle> particleType = new Parameter<>("Particle Type", Particle.ELECTRIC_SPARK);
    private Parameter<Double> particleOffset = new Parameter<>("Particle Offset", 0.5);
    private Parameter<Double> particleOffsetY = new Parameter<>("Particle Offset Y", 0.5);
    private Parameter<Integer> particleAmount = new Parameter<>("Particle Amount", 15);
    private Parameter<Double> particleSpeed = new Parameter<>("Particle Speed", 0.15);


    public PlayParticle() {
        super("Play Particle");
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        location.getWorld().spawnParticle(particleType.getValue(), location,(int) particleAmount.getValue(),(double) particleOffset.getValue(),(double) particleOffsetY.getValue(),(double) particleOffset.getValue(), (double) particleSpeed.getValue());

    }

    @Override
    public List<Parameter<?>> getParameters() {
        List<Parameter<?>> parameters = new ArrayList<>();

        parameters.add(particleType);
        parameters.add(particleOffset);
        parameters.add(particleOffsetY);
        parameters.add(particleAmount);
        parameters.add(particleSpeed);

        return parameters;
    }
}
