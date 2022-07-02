package me.justacat.ArcaneProjectiles.misc;

import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

public class Parameter<T> {


    private T value;
    private final String name;

    private Material display = null;

    public Parameter(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public Parameter(String name, T value, Material display) {
        this.name = name;
        this.value = value;
        this.display = display;
    }



    public T getValue() {return value;}
    public void setValue(T value) {this.value = value;}

    public String getName() {return name;}

    public Material getDisplay() {return display;}

    public boolean chatEdit(String chatValue) {

        try {

            if (value instanceof String) {

                if (name.equals("Projectile Type")) {

                    if (Projectile.projectileFromName(chatValue, true) != null) {
                        value = (T) chatValue;
                    } else {

                        for (String name : FileManager.getProjectileList()) {

                            if (name.toUpperCase().contains(chatValue.toUpperCase())) {

                                value = (T) name;
                                return true;

                            } else if (chatValue.toUpperCase().contains(name.toUpperCase())) {
                                value = (T) name;
                                return true;
                            }

                        }

                        return false;


                    }


                } else if (name.equals("Potion Effect")) {

                    if (PotionEffectType.getByName(chatValue.toUpperCase().replace(" ", "_")) != null) {
                        value = (T) chatValue.toUpperCase().replace(" ", "_");
                        return true;
                    } else {
                        return false;
                    }


                } else {
                    value = (T) chatValue;
                    return true;
                }

                return true;
            } else if (value instanceof Float) {
                value = (T) Float.valueOf(chatValue);
                return true;
            } else if (value instanceof Boolean) {

                if (chatValue.equalsIgnoreCase("true") | chatValue.equalsIgnoreCase("yes")) {
                    value = (T) Boolean.TRUE;
                    return true;
                } else if (chatValue.equalsIgnoreCase("false") | chatValue.equalsIgnoreCase("no")) {
                    value = (T) Boolean.FALSE;
                    return true;
                } else {
                    return false;
                }


            } else if (value instanceof Double) {
                value = (T) Double.valueOf(chatValue);
                return true;
            } else if (value instanceof Integer) {
                value = (T) Integer.valueOf(chatValue);
                return true;
            } else if (value instanceof EntityType) {
                value = (T) EntityType.valueOf(chatValue.toUpperCase().replace(" ", "_"));
                return true;

            } else if (value instanceof Particle) {

                Particle particle = Particle.valueOf(chatValue.replace(" ", "_").toUpperCase().replace("MINECRAFT:", ""));

                value = (T) particle;
                return true;

            } else if (value instanceof Material) {

                value = (T) Material.valueOf(chatValue.replace(" ", "_").toUpperCase());
                return true;


            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }



    }



}
