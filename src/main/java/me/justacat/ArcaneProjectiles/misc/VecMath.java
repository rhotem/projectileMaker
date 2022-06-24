package me.justacat.ArcaneProjectiles.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VecMath {

    public static float getPitch(Vector vector){

        Location location = new Location(Bukkit.getWorld("world"), 0D, 0D, 0D);
        location.setDirection(vector);
        return location.getPitch();
    }

    public static float getYaw(Vector vector){
        Location location = new Location(Bukkit.getWorld("world"), 0D, 0D, 0D);
        location.setDirection(vector);
        return location.getYaw();
    }
    public static Vector vecFromAng(float pitch, float yaw){ return new Location(Bukkit.getWorld("world"), 0D, 0D, 0D, yaw, pitch).getDirection(); }
    public static Vector angledVec(Vector vector, float pitch, float yaw) {return VecMath.vecFromAng( VecMath.getPitch(vector) + pitch, VecMath.getYaw(vector) + yaw);}
    public static Vector perpendicular(Vector vector) {return VecMath.angledVec(vector, 90, 0);}


    public static Vector vectorBetween(Location from, Location to) {return new Vector(to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ());}
}

