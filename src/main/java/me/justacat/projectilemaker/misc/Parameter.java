package me.justacat.projectilemaker.misc;

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
