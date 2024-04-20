package net.meep.magicprogramming.interpreter;

import net.meep.magicprogramming.interpreter.Classes.*;
import net.meep.magicprogramming.interpreter.functions.GeneralFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.*;

public class Interpreter {
    static GeneralFunctions generalFunctions;

    public Interpreter() {
        generalFunctions = new GeneralFunctions();
    }

    public void CastSpell(String spell, World world, PlayerEntity user) {
        Interpret(LexerParser.Spell(spell), new VirtualEnvironment(), world, user, 0);
    }
    public static List<ParserTreeNode> Interpret(List<ParserTreeNode> parsed, VirtualEnvironment env, World world, PlayerEntity user, int layers) {
        if (parsed == null) return null;
        if (layers > 128) return null;
        for (ParserTreeNode expr : parsed) {
            if (expr.token.type == TokenType.FUNCTION) {
                expr.token.type = TokenType.LITERAL;
                expr.children = Interpret(expr.children, env, world, user, layers+1);
                generalFunctions.TryFunctions(expr, env, layers, world, user);
                expr.children = new ArrayList<>();
            } else {
                if (!(expr.token.value instanceof Data)) //Data needs no interpreting.
                    try {
                        double numValue = Double.parseDouble((String) expr.token.value);
                        if (Double.isNaN(numValue) || Double.isInfinite(numValue)) expr.token.value = new Data(DataType.NULL, null);
                        else expr.token.value = new Data(DataType.NUMBER, numValue);
                    } catch (Exception ignored) {
                        if (expr.token.value == null) expr.token.value = new Data(DataType.NULL, null);
                        switch (expr.token.value.toString()) {
                            case "self":
                                expr.token.value = new Data(DataType.ENTITY, user);
                                break;
                            case "true":
                                expr.token.value = new Data(DataType.BOOLEAN, true);
                                break;
                            case "false":
                                expr.token.value = new Data(DataType.BOOLEAN, false);
                                break;
                            case "null":
                                expr.token.value = new Data(DataType.NULL, null);
                                break;
                            case "NUMBER":
                                expr.token.value = new Data(DataType.TYPE, DataType.NUMBER);
                                break;
                            case "VECTOR":
                                expr.token.value = new Data(DataType.TYPE, DataType.VECTOR);
                                break;
                            case "BOOLEAN":
                                expr.token.value = new Data(DataType.TYPE, DataType.BOOLEAN);
                                break;
                            case "STRING":
                                expr.token.value = new Data(DataType.TYPE, DataType.STRING);
                                break;
                            case "ENTITY":
                                expr.token.value = new Data(DataType.TYPE, DataType.ENTITY);
                                break;
                            case "BLOCK":
                                expr.token.value = new Data(DataType.TYPE, DataType.BLOCK);
                                break;
                            case "NULL":
                                expr.token.value = new Data(DataType.TYPE, DataType.NULL);
                                break;
                            case "TYPE":
                                expr.token.value = new Data(DataType.TYPE, DataType.TYPE);
                                break;
                            case "LIST":
                                expr.token.value = new Data(DataType.TYPE, DataType.LIST);
                                break;
                            case "ANY":
                                expr.token.value = new Data(DataType.TYPE, DataType.ANY);
                                break;
                            default:
                                if (env.variables.containsKey(expr.token.value.toString())) {
                                    expr.token.value = new Data((Data)env.variables.get(expr.token.value.toString()));
                                } else expr.token.value = new Data(DataType.NULL, null);
                                break;
                        }
                    }
            }
        }
        return parsed;
    }
    public static void NoNativeFunction(ParserTreeNode expr) {
        expr.token.value = new Data(DataType.NULL, null);
    }
    public static Map<String, Object> getArguments(List<ParserTreeNode> children, List<Argument> arguments) {
        if (children.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            for (Argument argument : arguments)
                map.put(argument.getName(), argument.getDefault());
            return map;
        }
        Map<String, Object> map = new HashMap<>();
        if (!children.get(0).argumentName.isEmpty()) { //If named arguments are used
            for (Argument argument : arguments) {
                for (ParserTreeNode child : children) {
                    if (child.argumentName.equals(argument.getName())) {
                        if (compatibleTypes(((Data)child.token.value).getType(), argument.getType()))
                            map.put(argument.getName(), ((Data)child.token.value).getData());
                        else map.put(argument.getName(), argument.getDefault()); //Invalid type, use default.
                    }
                }
                if (!map.containsKey(argument.getName()))
                    map.put(argument.getName(), argument.getDefault()); //Argument not found, use default.
            }
        } else {
            List<Data> dataList = convertToData(children);
            for (int i = 0; i < arguments.size(); i++) {
                try {
                    if (compatibleTypes(dataList.get(i).getType(), arguments.get(i).getType()))
                        map.put(arguments.get(i).getName(), dataList.get(i).getData());
                    else map.put(arguments.get(i).getName(), arguments.get(i).getDefault()); //Invalid type, use default.
                } catch (Exception ignored) { //Argument not found, use default.
                    map.put(arguments.get(i).getName(), arguments.get(i).getDefault());
                }
            }
        }
        return map;
    }
    static List<Data> convertToData(List<ParserTreeNode> tree) {
        List<Data> dataList = new ArrayList<>();
        for (ParserTreeNode node : tree) {
            if (node.token.value instanceof Data) {
                dataList.add((Data)node.token.value);
            } else {
                //Shouldn't ever happen.
                System.out.println("NOT DATA");
            }
        }
        return dataList;
    }
    public static boolean compatibleTypes(DataType type, DataType argumentType) {
        if (type == argumentType) return true;
        else return argumentType == DataType.ANY;
    }
}
