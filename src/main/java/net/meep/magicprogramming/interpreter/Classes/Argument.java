package net.meep.magicprogramming.interpreter.Classes;

public class Argument {
    DataType type;
    String name;
    Object defaultValue; //Null means it is a required value.
    public Argument(DataType type, String name, Object defaultValue) {
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
    }
    public DataType getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public Object getDefault() {
        return defaultValue;
    }
}
