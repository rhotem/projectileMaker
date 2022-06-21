package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

public class Potion extends HitEvent {


    private Parameter<String> effect;
    private Parameter<Double> radius;
    private Parameter<Integer> duration;
    private Parameter<Integer> level;
    private Parameter<Boolean> particles;
    private Parameter<Boolean> showIcon;


    public Potion() {
        super(Material.SPLASH_POTION, "Potion Effect", "Gives nearby entities potion effect!");
        this.effect = new Parameter<>("Potion Effect", "POISON");
        this.radius = new Parameter<>("Radius", 5D);
        this.duration = new Parameter<>("Duration", 30);
        this.level = new Parameter<>("Level", 2);
        this.particles = new Parameter<>("Show Particles", true);
        this.showIcon = new Parameter<>("Show Icon", true);
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {

        Collection<LivingEntity> livingEntities = location.getNearbyLivingEntities(radius.getValue(), radius.getValue(), radius.getValue());

        PotionEffectType potionEffectType;
        if (PotionEffectType.getByName(effect.getValue()) == null) {
            potionEffectType = PotionEffectType.POISON;
            Bukkit.getLogger().log(Level.WARNING, "Invalid potion effect!");
        } else {
            potionEffectType = PotionEffectType.getByName(effect.getValue());
        }

        for (LivingEntity livingEntity : livingEntities) {

            livingEntity.addPotionEffect(new PotionEffect(potionEffectType, duration.getValue(), level.getValue(), particles.getValue(), particles.getValue(), showIcon.getValue()));

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
