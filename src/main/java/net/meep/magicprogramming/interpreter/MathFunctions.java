package net.meep.magicprogramming.interpreter;

import net.meep.magicprogramming.interpreter.Classes.Argument;
import net.meep.magicprogramming.interpreter.Classes.Data;
import net.meep.magicprogramming.interpreter.Classes.DataType;
import net.meep.magicprogramming.interpreter.Classes.ParserTreeNode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class MathFunctions {
    GetterFunctions getterFunctions;
    public MathFunctions() {
        getterFunctions = new GetterFunctions();
    }

    public void TryFunctions(ParserTreeNode expr, World world, PlayerEntity user) {
        Map<String, Object> arguments;
        List<Argument> argumentList = new ArrayList<>();
        Data nullData = new Data(DataType.NULL, null);
        switch((String)expr.token.value) {
            //Arithmetic
            case "add": //Adds up to 16 numbers.
                for (int i = 1; i < 17; i++)
                    argumentList.add(new Argument(DataType.NUMBER, "addend"+i, 0d));
                arguments = Interpreter.getArguments(expr.children, argumentList);
                double sum = 0;
                for (int i = 1; i < 17; i++)
                    sum += (double)arguments.get("addend"+i);
                expr.token.value = new Data(DataType.NUMBER, sum);
                break;
            case "subtract":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "minuend", 0d),
                        new Argument(DataType.NUMBER, "subtrahend", 0d))));
                expr.token.value = new Data(DataType.NUMBER, (Double)arguments.get("minuend") - (Double)arguments.get("subtrahend"));
                break;
            case "multiply": //Multiplies up to 16 numbers.
                for (int i = 1; i < 17; i++)
                    argumentList.add(new Argument(DataType.NUMBER, "multiplicand"+i, 1d));
                arguments = Interpreter.getArguments(expr.children, argumentList);
                double product = 1;
                for (int i = 1; i < 17; i++)
                    product *= (double)arguments.get("multiplicand"+i);
                expr.token.value = new Data(DataType.NUMBER, product);
                break;
            case "divide":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "dividend", 1d),
                        new Argument(DataType.NUMBER, "divisor", 1d))));
                if ((Double)arguments.get("divisor") == 0) expr.token.value = nullData;
                else expr.token.value = new Data(DataType.NUMBER, (Double)arguments.get("dividend") / (Double)arguments.get("divisor"));
                break;
            //Advanced Arithmetic
            case "modulo":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "dividend", 1d),
                        new Argument(DataType.NUMBER, "divisor", 1d))));
                if ((Double)arguments.get("divisor") == 0) expr.token.value = nullData;
                else expr.token.value = new Data(DataType.NUMBER, (Double)arguments.get("dividend") % (Double)arguments.get("divisor"));
                break;
            case "pow":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "base", Math.E),
                        new Argument(DataType.NUMBER, "exponent", 1d))));
                expr.token.value = new Data(DataType.NUMBER, Math.pow((Double)arguments.get("base"), (Double)arguments.get("exponent")));
                break;
            case "root":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "radicand", 1d),
                        new Argument(DataType.NUMBER, "degree", 2d))));
                if ((Double)arguments.get("degree") == 0) expr.token.value = nullData;
                expr.token.value = new Data(DataType.NUMBER, Math.pow((Double)arguments.get("radicand"), 1 / (Double)arguments.get("degree")));
                break;
            case "log":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "antilogarithm", 1d),
                        new Argument(DataType.NUMBER, "base", Math.E))));
                if ((Double)arguments.get("base") == 0) expr.token.value = nullData;
                if ((Double)arguments.get("antilogarithm") <= 0) expr.token.value = new Data(DataType.NULL, null);
                expr.token.value = new Data(DataType.NUMBER, Math.pow((Double)arguments.get("radicand"), 1 / (Double)arguments.get("degree")));
                break;
            //Unary Operators
            case "abs":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = Math.abs((Double)arguments.get("number"));
                break;
            case "negate":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = -((Double)arguments.get("number"));
                break;
            case "reciprocal":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = 1 / ((Double)arguments.get("number"));
                break;
            //Rounders
            case "round":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = Math.round((Double)arguments.get("number"));
                break;
            case "floor":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = Math.floor((Double)arguments.get("number"));
                break;
            case "ceil":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = Math.ceil((Double)arguments.get("number"));
                break;
            //Constants
            case "pi":
                expr.token.value = new Data(DataType.NUMBER, Math.PI);
                break;
            case "tau":
                expr.token.value = new Data(DataType.NUMBER, 2 * Math.PI);
                break;
            case "euler":
                expr.token.value = new Data(DataType.NUMBER, Math.E);
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

            //Miscellaneous
            case "random":
                expr.token.value = new Data(DataType.NUMBER, Math.random());
                break;

            default:
                getterFunctions.TryFunctions(expr, world, user);
                break;
        }
    }
}
