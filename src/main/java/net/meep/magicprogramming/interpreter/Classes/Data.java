package net.meep.magicprogramming.interpreter.Classes;

public class Data {
    DataType type;
    Object data;
    public Data(DataType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public DataType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return type + ": " + data;
    }
}
