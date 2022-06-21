package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class Teleport extends HitEvent {

    Parameter<Boolean> sound = new Parameter<>("Sound", true);

    public Teleport() {
        super(Material.ENDER_PEARL, "Teleport", "Teleports the caster to the hit location");
    }

    @Override
    public void trigger(Location location, LivingEntity caster) {
        caster.teleport(location);
        if (sound.getValue()) {
            caster.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
        }
    }

    @Override
    public List<Parameter<?>> getParameters() {
        return Collections.singletonList(sound);
    }
}
