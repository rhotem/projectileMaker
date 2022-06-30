package me.justacat.ArcaneProjectiles.misc;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {


    public static HashMap<String, CooldownManager> cooldownManagerMap = new HashMap<>();

    private Cache<UUID, Long> cooldown;


    private long projectileCooldown;

    public CooldownManager(String projectileName) {
        projectileCooldown = Projectile.projectileFromName(projectileName, true).getCooldown();
        cooldown = CacheBuilder.newBuilder().expireAfterWrite(projectileCooldown, TimeUnit.SECONDS).build();
        cooldownManagerMap.put(projectileName, this);
    }

    public long getPlayerCooldown(Player player) {
        return TimeUnit.MILLISECONDS.toSeconds(cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis());
    }

    public boolean isInCooldown(Player player) {
        if (cooldown.asMap().containsKey(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }

    public void putInCooldown(Player player) {
        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + projectileCooldown * 1000);
    }

    public long getProjectileCooldown() {
        return projectileCooldown;
    }
}
