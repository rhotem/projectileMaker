package me.justacat.projectilemaker.misc;

import me.justacat.projectilemaker.projectiles.hitevents.Explosion;
import org.checkerframework.checker.units.qual.K;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class Parameter<T> {


    private T value;
    private String name;

    public Parameter(String name, T value) {
        this.name = name;
        this.value = value;
    }




    public T getValue() {return value;}
    public void setValue(T value) {this.value = value;}

    public String getName() {return name;}


}
