package me.justacat.ArcaneProjectiles.misc;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;

public class WorldGuardManager {

    public StateFlag flag;

    public void registerFlags() {

        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        try {

            StateFlag flag = new StateFlag("Shoot-Arcane-Projectiles", true);
            registry.register(flag);
            this.flag = flag;

        } catch (FlagConflictException ignored) {

        }



    }

    public boolean checkFlag(Location location) {
        try {
            RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = regionContainer.createQuery();
            com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
            ApplicableRegionSet set = query.getApplicableRegions(loc);

            return set.testState(null, flag);
        } catch (Exception e) {
            return true;
        }

    }


}
