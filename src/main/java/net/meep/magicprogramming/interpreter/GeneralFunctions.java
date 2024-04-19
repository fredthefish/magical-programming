package net.meep.magicprogramming.interpreter;

import net.meep.magicprogramming.interpreter.Classes.*;
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

    public void TryFunctions(ParserTreeNode expr, World world, PlayerEntity user) {
        Map<String, Object> arguments;
        switch ((String)expr.token.value) {
            //General
            case "print": //Prints and returns data.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.ANY, "argument", null))));
                if (arguments.get("argument") == null) user.sendMessage(Text.literal("null"));
                user.sendMessage(Text.literal(arguments.get("argument").toString()));
                expr.token.value = new Data(Data.TypeOf(arguments.get("argument")), arguments.get("argument"));
                break;
            case "cast":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.SPELL, "spell", new ArrayList<ParserTreeNode>()))));
                @SuppressWarnings("unchecked")
                List<ParserTreeNode> spell = (ArrayList<ParserTreeNode>)arguments.get("spell");
                Interpreter.Interpret(spell, world, user);
                expr.token.value = new Data(DataType.NULL, null);
                break;
            case "type":
                if (!expr.children.isEmpty())
                    expr.token.value = new Data(DataType.TYPE, ((Data)expr.children.get(0).token.value).getType());
                break;

            //Control flow
            case "if":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.BOOLEAN, "condition", false),
                        new Argument(DataType.SPELL, "then", new ArrayList<ParserTreeNode>()),
                        new Argument(DataType.SPELL, "else", new ArrayList<ParserTreeNode>()))));
                @SuppressWarnings("unchecked")
                List<ParserTreeNode> thenSpell = (ArrayList<ParserTreeNode>) arguments.get("then");
                @SuppressWarnings("unchecked")
                List<ParserTreeNode> elseSpell = (ArrayList<ParserTreeNode>) arguments.get("else");
                if ((boolean)arguments.get("condition"))
                    Interpreter.Interpret(thenSpell, world, user);
                else
                    Interpreter.Interpret(elseSpell, world, user);
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
            default:
                mathFunctions.TryFunctions(expr, world, user);
                break;
        }
    }
}
