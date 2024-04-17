package net.meep.magicprogramming.interpreter;

import net.meep.magicprogramming.interpreter.Classes.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
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
                expr.token.value = new Data(arguments.get("argument"));
                break;
            default:
                mathFunctions.TryFunctions(expr, world, user);
                break;
        }
    }
}
