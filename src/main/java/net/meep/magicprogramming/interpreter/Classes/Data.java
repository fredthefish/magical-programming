package net.meep.magicprogramming.interpreter.Classes;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Data {
    DataType type;
    Object data;
    public Data(DataType type, Object data) {
        this.type = type;
        this.data = data;
    }
    public Data(Object data) {
        this.data = data;
        if (data == null) type = DataType.NULL;
        else if (data instanceof String) type = DataType.STRING;
        else if (data instanceof Double) type = DataType.NUMBER;
        else if (data instanceof Vec3d) type = DataType.VECTOR;
        else if (data instanceof Entity) type = DataType.ENTITY;
        else if (data instanceof Boolean) type = DataType.BOOLEAN;
        else throw new Error();
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
