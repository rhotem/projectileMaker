package me.justacat.projectilemaker.misc;

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
                value = (T) chatValue;
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


            } else if (value instanceof Integer) {
                value = (T) Integer.valueOf(chatValue);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }



    }



}
