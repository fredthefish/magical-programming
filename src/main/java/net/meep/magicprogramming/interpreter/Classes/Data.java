package net.meep.magicprogramming.interpreter.Classes;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class Data {
    DataType type;
    Object data;
    public Data(DataType type, Object data) {
        this.type = type;
        this.data = data;
    }

    @SuppressWarnings("all") //Suppress warnings of unchecked casts.
    public Data(Data data) {
        this.type = data.getType();
        DataType type = TypeOf(data.getData());
        if (type == DataType.NULL || type == DataType.NUMBER || type == DataType.BOOLEAN || type == DataType.TYPE ||
        type == DataType.ENTITY || type == DataType.BLOCK || type == DataType.STRING) this.data = data.getData();
        else if (type == DataType.VECTOR) {
            Vec3d v = (Vec3d) data.getData();
            this.data = new Vec3d(v.x, v.y, v.z);
        } else if (type == DataType.LIST) {
            this.data = new ArrayList<>((ArrayList) data.getData());
        }
    }

    public static DataType TypeOf(Object object) {
        if (object == null) return DataType.NULL;
        else if (object instanceof Double) return DataType.NUMBER;
        else if (object instanceof Vec3d) return DataType.VECTOR;
        else if (object instanceof Boolean) return DataType.BOOLEAN;
        else if (object instanceof String) return DataType.STRING;
        else if (object instanceof ArrayList) return DataType.LIST;
        else if (object instanceof Block) return DataType.BLOCK;
        else if (object instanceof Entity) return DataType.ENTITY;
        else if (object instanceof DataType) return DataType.TYPE;
        System.out.println("Invalid type, somehow.");
        return DataType.NULL;
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
