package net.meep.magicprogramming.interpreter;

import net.meep.magicprogramming.interpreter.Classes.Argument;
import net.meep.magicprogramming.interpreter.Classes.Data;
import net.meep.magicprogramming.interpreter.Classes.DataType;
import net.meep.magicprogramming.interpreter.Classes.ParserTreeNode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MathFunctions {
    GetterFunctions getterFunctions;
    public MathFunctions() {
        getterFunctions = new GetterFunctions();
    }

    public void TryFunctions(ParserTreeNode expr, World world, PlayerEntity user) {
        Map<String, Object> arguments;
        switch((String)expr.token.value) {
            //Arithmetic
            case "add": //Adds numbers.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "addend1", 0d),
                        new Argument(DataType.NUMBER, "addend2", 0d))));
                expr.token.value = new Data(DataType.NUMBER,
                        (Double) arguments.get("addend1") + (Double) arguments.get("addend2"));
                break;
            //Vectors
            case "vector": //Form vector from 3 components
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "x", 0d),
                        new Argument(DataType.NUMBER, "y", 0d),
                        new Argument(DataType.NUMBER, "z", 0d))));
                expr.token.value = new Data(DataType.VECTOR,
                        new Vec3d((Double) arguments.get("x"), (Double) arguments.get("y"), (Double) arguments.get("z")));
                break;
            case "multiplyvector": //Multiply vector by a number.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector", new Vec3d(0, 0, 0)),
                        new Argument(DataType.NUMBER, "multiplier", 0d))));
                expr.token.value = new Data(DataType.VECTOR,
                        ((Vec3d) arguments.get("vector")).multiply((Double) arguments.get("multiplier")));
                break;
            default:
                getterFunctions.TryFunctions(expr, world, user);
                break;
        }
    }
}
