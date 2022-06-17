package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Potion extends HitEvent {


    private Parameter<PotionEffectType> effect;
    private Parameter<Double> radius;
    private Parameter<Integer> duration;
    private Parameter<Integer> level;
    private Parameter<Boolean> particles;
    private Parameter<Boolean> showIcon;

    public Potion() {
        super(Material.SPLASH_POTION);
        this.effect = new Parameter<>("Effect", PotionEffectType.POISON);
        this.radius = new Parameter<>("Radius", 5D);
        this.duration = new Parameter<>("Duration", 30);
        this.level = new Parameter<>("Level", 2);
        this.particles = new Parameter<>("Show Particles", true);
        this.showIcon = new Parameter<>("Show Icon", true);
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        Collection<LivingEntity> livingEntities = location.getNearbyLivingEntities(radius.getValue(), radius.getValue(), radius.getValue());

        for (LivingEntity livingEntity : livingEntities) {

            livingEntity.addPotionEffect(new PotionEffect(effect.getValue(), duration.getValue(), level.getValue(), particles.getValue(), particles.getValue(), showIcon.getValue()));

        }



    }

    @Override
    public List<Parameter<?>> getParameters() {

        List<Parameter<?>> parameterList = new ArrayList<>();

        parameterList.add(effect);
        parameterList.add(radius);
        parameterList.add(duration);
        parameterList.add(level);
        parameterList.add(particles);
        parameterList.add(showIcon);


        return parameterList;
    }
}
