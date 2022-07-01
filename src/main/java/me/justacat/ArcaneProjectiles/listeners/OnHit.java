package me.justacat.ArcaneProjectiles.listeners;

import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class OnHit implements Listener {


    @EventHandler
    public void OnProjectileHit(ProjectileHitEvent e) {

        String meta = null;

        for (String name : FileManager.getProjectileList()) {


            if (e.getEntity().hasMetadata("ProjectileName." + name)) {
                meta = name;
                break;

            }

        }



        Projectile projectile = Projectile.projectileFromName(meta, true);



        if (projectile == null) return;

        if (e.getHitEntity() == null) {
//            projectile.hit(e.getHitBlock().getLocation(), (LivingEntity) e.getEntity().getShooter());
            return;
        }


        if (e.getHitEntity() instanceof  LivingEntity) {
            LivingEntity entity = (LivingEntity) e.getHitEntity();


            e.setCancelled(true);
            entity.damage((double) projectile.getParameterByName("Damage").getValue(), (Entity) e.getEntity().getShooter());
            entity.setVelocity(entity.getVelocity().add(e.getEntity().getVelocity().normalize().multiply((double) projectile.getParameterByName("knockBack").getValue())));
            e.getEntity().remove();
        }





    }
}
