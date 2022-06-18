package me.justacat.projectilemaker.misc;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.projectiles.Projectile;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

public class Parameter<T> {


    private T value;
    private final String name;

    public Parameter(String name, T value) {
        this.name = name;
        this.value = value;
    }



    public T getValue() {return value;}
    public void setValue(T value) {this.value = value;}

    public String getName() {return name;}


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
                        value = (T) chatValue;
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
                value = (T) EntityType.valueOf(chatValue);
                return true;
            } else if (value instanceof PotionEffectType) {
                if (PotionEffectType.getByName(chatValue) != null) {
                    value = (T) PotionEffectType.getByName(chatValue);
                    return true;
                } else if (PotionEffectType.getById(Integer.parseInt(chatValue)) != null) {
                    value = (T) PotionEffectType.getById(Integer.parseInt(chatValue));
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }



    }



}
