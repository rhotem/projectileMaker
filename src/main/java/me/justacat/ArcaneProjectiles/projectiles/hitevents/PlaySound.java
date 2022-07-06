package me.justacat.ArcaneProjectiles.projectiles.hitevents;

import me.justacat.ArcaneProjectiles.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class PlaySound extends HitEvent {

    private Parameter<Sound> sound = new Parameter<>("Sound", Sound.ENTITY_GENERIC_EXPLODE);
    private Parameter<Float> volume = new Parameter<>("Volume", 1F);
    private Parameter<Float> pitch = new Parameter<>("Pitch", 0.5F);


    public PlaySound() {
        super("Play Sound");
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {
        location.getWorld().playSound(location, sound.getValue(), volume.getValue(), pitch.getValue());
    }

    @Override
    public List<Parameter<?>> getParameters() {

        List<Parameter<?>> parameters = new ArrayList<>();

        parameters.add(sound);
        parameters.add(volume);
        parameters.add(pitch);

        return parameters;
    }
}
