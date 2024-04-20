package net.meep.magicprogramming.interpreter.functions;

import net.meep.magicprogramming.interpreter.Classes.*;
import net.meep.magicprogramming.interpreter.Interpreter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ActionFunctions {
    public ActionFunctions() {}

    public void TryFunctions(ParserTreeNode expr, World world, PlayerEntity user) {
        Map<String, Object> arguments;
        Data nullData = new Data(DataType.NULL, null);
        switch ((String) expr.token.value) {
            //Actions
            case "addforce": //Adds a force to an entity.
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.ENTITY, "entity", null),
                        new Argument(DataType.VECTOR, "vector", new Vec3d(0, 0, 0)))));
                if (arguments.get("entity") == null) { expr.token.value = nullData; break; }
                Vec3d direction = ((Vec3d)arguments.get("vector")).normalize();
                System.out.println(direction);
                double force = ((Vec3d)arguments.get("vector")).length();
                System.out.println(force);
                if (force > 256) force = 256; //Clamp force at 256.
                else if (force < -256) force = -256;
                ((Entity) arguments.get("entity")).addVelocity(direction.multiply(force));
                ((Entity) arguments.get("entity")).velocityModified = true;
                expr.token.value = nullData;
                break;
            case "summonarrow":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "position", null),
                        new Argument(DataType.BOOLEAN, "critical", false),
                        new Argument(DataType.NUMBER, "power", 0d),
                        new Argument(DataType.NUMBER, "punch", 0d),
                        new Argument(DataType.NUMBER, "pierce", 0d))));
                if (arguments.get("position") == null) { expr.token.value = nullData; break; }
                Vec3d vector = (Vec3d) arguments.get("position");
                ArrowEntity arrow = new ArrowEntity(world, vector.x, vector.y, vector.z, new ItemStack(Items.ARROW));
                world.spawnEntity(arrow);
                arrow.setOwner(user);
                arrow.setCritical((Boolean) arguments.get("critical"));
                if (((Double) arguments.get("power")).intValue() > 0)
                    arrow.writeNbt(new NbtCompound()).putDouble("damage", 2.5 + (Double) arguments.get("power") / 2);
                arrow.setPierceLevel(((Double) arguments.get("pierce")).byteValue());
                arrow.setPunch(((Double) arguments.get("punch")).intValue());
                expr.token.value = new Data(DataType.ENTITY, arrow);
                break;
            case "summonfireball":
                arguments = Interpreter.getArguments(expr.children, new ArrayList<>(Arrays.asList(
                        new Argument(DataType.VECTOR, "position", null),
                        new Argument(DataType.NUMBER, "power", 0.0))));
                if (arguments.get("position") == null) { expr.token.value = nullData; break; }
                double power = Math.min(16.0, (Double)arguments.get("power")); //Clamp power at 16.
                FireballEntity fireball = new FireballEntity(world, user, 0, 0, 0, (int)power);
                Vec3d p = (Vec3d)arguments.get("position");
                fireball.setPos(p.x, p.y, p.z);
                world.spawnEntity(fireball);
                expr.token.value = new Data(DataType.ENTITY, fireball);
                break;
            default:
                //NO NATIVE FUNCTION
                Interpreter.NoNativeFunction(expr);
                break;
        }
    }
}
