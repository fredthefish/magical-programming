package net.meep.magicprogramming.interpreter.functions;

import net.meep.magicprogramming.interpreter.Classes.*;
import net.meep.magicprogramming.interpreter.Interpreter;
import net.meep.magicprogramming.interpreter.LexerParser;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GeneralFunctions {
    MathFunctions mathFunctions;
    public GeneralFunctions() {
        mathFunctions = new MathFunctions();
    }

    public void TryFunctions(ParserTreeNode expr, VirtualEnvironment env, int layers, World world, PlayerEntity user) {
        Map<String, Object> arguments;
        switch ((String)expr.token.value) {
            //General
            case "print": //Prints and returns data.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.ANY, "argument", new Data(DataType.NULL, null)))));
                if (arguments.get("argument") == null) {user.sendMessage(Text.literal("null")); break; }
                user.sendMessage(Text.literal(arguments.get("argument").toString()));
                expr.token.value = new Data(Data.TypeOf(arguments.get("argument")), arguments.get("argument"));
                break;
            case "type":
                if (!expr.children.isEmpty())
                    expr.token.value = new Data(DataType.TYPE, ((Data)expr.children.get(0).token.value).getType());
                break;

            //Spells
            case "cast":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.STRING, "spell", ""))));
                String spell = (String)arguments.get("spell");
                try {
                    Interpreter.Interpret(LexerParser.Spell(spell), env, world, user, layers+1);
                } catch (StackOverflowError ignored) {}
                expr.token.value = new Data(DataType.NULL, null);
                break;

            //Control flow
            case "if":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.BOOLEAN, "condition", false),
                        new Argument(DataType.STRING, "then", ""),
                        new Argument(DataType.STRING, "else", ""))));
                String thenSpell = (String) arguments.get("then");
                String elseSpell = (String) arguments.get("else");
                if ((boolean)arguments.get("condition"))
                    try {
                        Interpreter.Interpret(LexerParser.Spell(thenSpell), env, world, user, layers+1);
                    } catch (StackOverflowError ignored) {}
                else
                    try {
                        Interpreter.Interpret(LexerParser.Spell(elseSpell), env, world, user, layers+1);
                    } catch (StackOverflowError ignored) {}
                break;
            case "ternary":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.BOOLEAN, "condition", false),
                        new Argument(DataType.ANY, "then", new ArrayList<ParserTreeNode>()),
                        new Argument(DataType.ANY, "else", new ArrayList<ParserTreeNode>()))));

                if ((boolean)arguments.get("condition"))
                    expr.token.value = new Data(Data.TypeOf(arguments.get("then")), arguments.get("then"));
                else
                    expr.token.value = new Data(Data.TypeOf(arguments.get("else")), arguments.get("else"));
                break;

            //Variables
            case "var":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.STRING, "name", null),
                        new Argument(DataType.ANY, "value", new Data(DataType.NULL, null)))));
                if (arguments.get("name") == null) { expr.token.value = new Data(DataType.NULL, null); break; }
                String name = (String)arguments.get("name");
                System.out.println(arguments.get("value"));
                Data value = new Data(Data.TypeOf(arguments.get("value")), arguments.get("value"));
                if (env.variables.containsKey(name))
                    env.variables.replace(name, value);
                else env.variables.put(name, value);
                expr.token.value = value;
                break;

            default:
                mathFunctions.TryFunctions(expr, world, user);
                break;
        }
    }
}
