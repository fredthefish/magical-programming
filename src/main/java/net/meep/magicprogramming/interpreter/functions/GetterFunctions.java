package net.meep.magicprogramming.interpreter.functions;

import net.meep.magicprogramming.interpreter.Classes.Argument;
import net.meep.magicprogramming.interpreter.Classes.Data;
import net.meep.magicprogramming.interpreter.Classes.DataType;
import net.meep.magicprogramming.interpreter.Classes.ParserTreeNode;
import net.meep.magicprogramming.interpreter.Interpreter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetterFunctions {
    ActionFunctions actionFunctions;
    public GetterFunctions() {
        actionFunctions = new ActionFunctions();
    }

    public void TryFunctions(ParserTreeNode expr, World world, PlayerEntity user) {
        Map<String, Object> arguments;
        Data nullData = new Data(DataType.NULL, null);
        switch ((String) expr.token.value) {
            //Getters
            case "lookdirection": //Gets direction an entity is facing.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.ENTITY, "entity", null))));
                if (arguments.get("entity") == null) { expr.token.value = nullData; break; }
                Entity entity = (Entity) arguments.get("entity");
                double yaw = ((entity.getYaw() + 90) * Math.PI) / 180;
                double pitch = ((entity.getPitch() + 90) * Math.PI) / 180;
                expr.token.value = new Data(DataType.VECTOR, new Vec3d(
                        Math.sin(pitch) * Math.cos(yaw), Math.cos(pitch), Math.sin(pitch) * Math.sin(yaw)));
                break;
            case "velocity": //Gets velocity of an entity.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.ENTITY, "entity", null))));
                if (arguments.get("entity") == null) { expr.token.value = nullData; break; }
                expr.token.value = new Data(DataType.VECTOR, ((Entity) arguments.get("entity")).getVelocity());
                break;
            case "position": //Gets position of an entity.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.ENTITY, "entity", null))));
                if (arguments.get("entity") == null) { expr.token.value = nullData; break; }
                expr.token.value = new Data(DataType.VECTOR, ((Entity) arguments.get("entity")).getPos());
                break;
            case "eyeposition": //Gets eye position of an entity.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(List.of(
                        new Argument(DataType.ENTITY, "entity", null))));
                if (arguments.get("entity") == null) { expr.token.value = nullData; break; }
                expr.token.value = new Data(DataType.VECTOR, ((Entity) arguments.get("entity")).getEyePos());
                break;
//            case "raycastforentity":
//                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
//                        new Argument(DataType.VECTOR, "position", null),
//                        new Argument(DataType.VECTOR, "direction", null))));
//                if (arguments != null) {
//
//                } else expr.token.value = nullData; break;
            default: //Invalid argument.
                actionFunctions.TryFunctions(expr, world, user);
                break;
        }
    }
}
