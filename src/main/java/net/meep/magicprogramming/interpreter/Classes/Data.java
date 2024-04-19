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

    public static DataType TypeOf(Object object) {
        if (object == null) return DataType.NULL;
        if (object instanceof Double) return DataType.NUMBER;
        if (object instanceof Vec3d) return DataType.VECTOR;
        if (object instanceof Boolean) return DataType.BOOLEAN;
        if (object instanceof String) return DataType.STRING;
        if (object instanceof ArrayList) {
            if (object.getClass().getComponentType() == ParserTreeNode.class) return DataType.SPELL;
            else return DataType.LIST;
        }
        if (object instanceof Block) return DataType.BLOCK;
        if (object instanceof Entity) return DataType.ENTITY;
        if (object instanceof DataType) return DataType.TYPE;
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
