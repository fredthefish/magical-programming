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
                expr.token.value = new Data(DataType.NUMBER,
                        Math.pow((Double)arguments.get("radicand"), 1 / (Double)arguments.get("degree")));
                break;
            case "log":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "antilogarithm", 1d),
                        new Argument(DataType.NUMBER, "base", Math.E))));
                if ((Double)arguments.get("base") == 0) expr.token.value = nullData;
                if ((Double)arguments.get("antilogarithm") <= 0) expr.token.value = new Data(DataType.NULL, null);
                expr.token.value = new Data(DataType.NUMBER,
                        Math.log((Double)arguments.get("antilogarithm")) / Math.log((Double)arguments.get("base")));
                break;

            //Unary Operators
            case "abs":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.abs((Double)arguments.get("number")));
                break;
            case "negate":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, -((Double)arguments.get("number")));
                break;
            case "reciprocal":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, 1 / ((Double)arguments.get("number")));
                break;

            //Rounders
            case "round":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, (double)Math.round((Double)arguments.get("number")));
                break;
            case "floor":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.floor((Double)arguments.get("number")));
                break;
            case "ceil":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.ceil((Double)arguments.get("number")));
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

            //Trigonometry
            case "sin":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.sin((Double)arguments.get("number")));
                break;
            case "cos":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.cos((Double)arguments.get("number")));
                break;
            case "tan":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.tan((Double)arguments.get("number")));
                break;
            case "arcsin":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.asin((Double)arguments.get("number")));
                break;
            case "arccos":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.acos((Double)arguments.get("number")));
                break;
            case "arctan":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.NUMBER, "number", 0d))));
                expr.token.value = new Data(DataType.NUMBER, Math.atan((Double)arguments.get("number")));
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
            case "getx":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.VECTOR, "vector", 0d))));
                expr.token.value = new Data(DataType.NUMBER, ((Vec3d)arguments.get("vector")).getX());
                break;
            case "gety":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.VECTOR, "vector", 0d))));
                expr.token.value = new Data(DataType.NUMBER, ((Vec3d)arguments.get("vector")).getY());
                break;
            case "getz":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.VECTOR, "vector", 0d))));
                expr.token.value = new Data(DataType.NUMBER, ((Vec3d)arguments.get("vector")).getZ());
                break;
            case "direction":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.VECTOR, "vector", 0d))));
                expr.token.value = new Data(DataType.NUMBER, ((Vec3d)arguments.get("vector")).normalize());
                break;
            case "magnitude":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.VECTOR, "vector", 0d))));
                expr.token.value = new Data(DataType.NUMBER, ((Vec3d)arguments.get("vector")).length());
                break;
            case "setx":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector", 0d),
                        new Argument(DataType.NUMBER, "number", 0d))));
                Vec3d vectorSetX = ((Vec3d)arguments.get("vector"));
                Vec3d vx = new Vec3d((Double)arguments.get("number"), vectorSetX.y, vectorSetX.z);
                expr.token.value = new Data(DataType.NUMBER, vx);
                break;
            case "sety":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector", 0d),
                        new Argument(DataType.NUMBER, "number", 0d))));
                Vec3d vectorSetY = ((Vec3d)arguments.get("vector"));
                Vec3d vy = new Vec3d(vectorSetY.x, (Double)arguments.get("number"), vectorSetY.z);
                expr.token.value = new Data(DataType.NUMBER, vy);
                break;
            case "setz":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector", 0d),
                        new Argument(DataType.NUMBER, "number", 0d))));
                Vec3d vectorSetZ = ((Vec3d)arguments.get("vector"));
                Vec3d vz = new Vec3d(vectorSetZ.x, vectorSetZ.y, (Double)arguments.get("number"));
                expr.token.value = new Data(DataType.NUMBER, vz);
                break;

            //Vector operators
            case "multiplyvector": //Multiply vector by a number.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector", new Vec3d(0, 0, 0)),
                        new Argument(DataType.NUMBER, "multiplier", 0d))));
                expr.token.value = new Data(DataType.VECTOR,
                        ((Vec3d) arguments.get("vector")).multiply((Double) arguments.get("multiplier")));
                break;
            case "addvectors":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector1", new Vec3d(0,0,0)),
                        new Argument(DataType.VECTOR, "vector2", new Vec3d(0,0,0)))));
                expr.token.value = new Data(DataType.VECTOR,
                        ((Vec3d)arguments.get("vector1")).add((Vec3d)arguments.get("vector2")));
                break;
            case "subtractvectors":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector1", new Vec3d(0,0,0)),
                        new Argument(DataType.VECTOR, "vector2", new Vec3d(0,0,0)))));
                expr.token.value = new Data(DataType.VECTOR,
                        ((Vec3d)arguments.get("vector1")).subtract((Vec3d)arguments.get("vector2")));
                break;
            case "dotproduct":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector1", new Vec3d(0,0,0)),
                        new Argument(DataType.VECTOR, "vector2", new Vec3d(0,0,0)))));
                expr.token.value = new Data(DataType.VECTOR,
                        ((Vec3d)arguments.get("vector1")).dotProduct((Vec3d)arguments.get("vector2")));
                break;
            case "crossproduct":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector1", new Vec3d(0,0,0)),
                        new Argument(DataType.VECTOR, "vector2", new Vec3d(0,0,0)))));
                expr.token.value = new Data(DataType.VECTOR,
                        ((Vec3d)arguments.get("vector1")).crossProduct((Vec3d)arguments.get("vector2")));
                break;
            case "rotatevector":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector", new Vec3d(0,0,0)),
                        new Argument(DataType.VECTOR, "axis", new Vec3d(0,0,0)),
                        new Argument(DataType.NUMBER, "angle", 0d))));
                Vec3d rotatedVector = (Vec3d)arguments.get("vector");
                rotatedVector = rotatedVector.rotateX((float)(((Vec3d)arguments.get("axis")).normalize().getX()
                        * Math.PI / 180 * (double)arguments.get("angle")));
                rotatedVector = rotatedVector.rotateY((float)(((Vec3d)arguments.get("axis")).normalize().getY()
                        * Math.PI / 180 * (double)arguments.get("angle")));
                rotatedVector = rotatedVector.rotateZ((float)(((Vec3d)arguments.get("axis")).normalize().getZ()
                        * Math.PI / 180 * (double)arguments.get("angle")));
                expr.token.value = new Data(DataType.VECTOR, rotatedVector);
                break;

            //Equality Operators
            case "equals":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.ANY, "argument1", false),
                        new Argument(DataType.ANY, "argument2", false))));
                expr.token.value = new Data(DataType.BOOLEAN, arguments.get("argument1").equals(arguments.get("argument2")));
                break;
            case "notequals":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.ANY, "argument1", false),
                        new Argument(DataType.ANY, "argument2", false))));
                expr.token.value = new Data(DataType.BOOLEAN, !arguments.get("argument1").equals(arguments.get("argument2")));
                break;
            case "approxequals":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "number1", 0d),
                        new Argument(DataType.NUMBER, "number2", 0d),
                        new Argument(DataType.NUMBER, "tolerance", 0.0001d))));
                boolean approxEquals = Math.abs((double)arguments.get("argument1") - (double)arguments.get("argument2"))
                        <= (double)arguments.get("tolerance");
                expr.token.value = new Data(DataType.BOOLEAN, approxEquals);
                break;
            case "vectorapproxequals":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "vector1", 0d),
                        new Argument(DataType.VECTOR, "vector2", 0d),
                        new Argument(DataType.NUMBER, "tolerance", 0.0001d))));
                expr.token.value = new Data(DataType.BOOLEAN,
                        ((Vec3d)arguments.get("vector1")).subtract((Vec3d)arguments.get("vector2")).length()
                                <= (double)arguments.get("tolerance"));
                break;

            //Comparison Operators
            case "greater":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "number1", 0d),
                        new Argument(DataType.NUMBER, "number2", 0d))));
                expr.token.value = new Data(DataType.BOOLEAN, (double)arguments.get("number1") > (double)arguments.get("argument2"));
                break;
            case "lesser":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "number1", 0d),
                        new Argument(DataType.NUMBER, "number2", 0d))));
                expr.token.value = new Data(DataType.BOOLEAN, (double)arguments.get("number1") < (double)arguments.get("argument2"));
                break;
            case "greaterorequal":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "number1", 0d),
                        new Argument(DataType.NUMBER, "number2", 0d))));
                expr.token.value = new Data(DataType.BOOLEAN, (double)arguments.get("number1") >= (double)arguments.get("argument2"));
                break;
            case "lesserorequal":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.NUMBER, "number1", 0d),
                        new Argument(DataType.NUMBER, "number2", 0d))));
                expr.token.value = new Data(DataType.BOOLEAN, (double)arguments.get("number1") <= (double)arguments.get("argument2"));
                break;

            //Logic Gates
            case "not":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.BOOLEAN, "boolean", false))));
                expr.token.value = new Data(DataType.BOOLEAN, !(boolean)arguments.get("boolean"));
                break;
            case "and":
                argumentList = new ArrayList<>();
                for (int i = 1; i < 17; i++)
                    argumentList.add(new Argument(DataType.BOOLEAN, "boolean"+i, true));
                arguments = Interpreter.getArguments(expr.children, argumentList);
                boolean andResult = true;
                for (Object bool : arguments.values())
                    andResult &= (boolean)bool;
                expr.token.value = new Data(DataType.BOOLEAN, andResult);
                break;
            case "or":
                argumentList = new ArrayList<>();
                for (int i = 1; i < 17; i++)
                    argumentList.add(new Argument(DataType.BOOLEAN, "boolean"+i, false));
                arguments = Interpreter.getArguments(expr.children, argumentList);
                boolean orResult = false;
                for (Object bool : arguments.values())
                    orResult |= (boolean)bool;
                expr.token.value = new Data(DataType.BOOLEAN, orResult);
                break;
            case "xor":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.BOOLEAN, "boolean1", false),
                        new Argument(DataType.BOOLEAN, "boolean2", false))));
                expr.token.value = new Data(DataType.BOOLEAN,
                        (boolean)arguments.get("boolean1") ^ (boolean)arguments.get("boolean2"));
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
