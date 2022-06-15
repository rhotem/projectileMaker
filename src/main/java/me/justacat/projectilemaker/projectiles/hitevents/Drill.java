package me.justacat.projectilemaker.projectiles.hitevents;

import me.justacat.projectilemaker.misc.Parameter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Drill extends HitEvent {

    private Parameter<Integer> length;
    private Parameter<Boolean> drops;

    private Parameter<Integer> radius;



    public Drill(int length, boolean drops, int radius) {
        this.length = new Parameter<>("length", length);
        this.drops = new Parameter<>("drops", drops);
        this.radius = new Parameter<>("radius", radius);
        this.display = Material.IRON_PICKAXE;
    }


    @Override
    public void trigger(Location location, LivingEntity caster) {

        Vector vector = location.getDirection();

        for (int i = 0; i < length.getValue(); i++) {


            List<Block> blocks = getBlocksInRadius(radius.getValue(), location);

            for (Block block : blocks) {

                if (drops.getValue()) {
                    block.breakNaturally();
                } else {
                    block.setType(Material.AIR);
                }

            }



            location = location.add(vector.normalize());



        }

    }

    @Override
    public List<Parameter<?>> getParameters() {

        List<Parameter<?>> parameterList = new ArrayList<>();

        parameterList.add(length);
        parameterList.add(drops);
        parameterList.add(radius);

        return parameterList;
    }

    private List<Block> getBlocksInRadius(Integer radius, Location location) {


        int locX = location.getBlockX();
        int locY = location.getBlockY();
        int locZ = location.getBlockZ();

        List<Block> blocks = new ArrayList<>();

        for (int x = -radius; x <= radius; x++) {

            for (int y = -radius; y <= radius; y++) {

                for (int z = -radius; z < radius; z++) {

                    Location loc = new Location(location.getWorld(), locX + x, locY + y, locZ + z);
                    blocks.add(loc.getBlock());

                }


            }


        }
        return blocks;

    }
}
