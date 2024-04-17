package net.meep.magicprogramming.item;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.meep.magicprogramming.gui.WandScreenHandler;
import net.meep.magicprogramming.interpreter.Interpreter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WandItem extends Item implements ExtendedScreenHandlerFactory {
    public WandItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!user.isSneaking()) {
            if (!world.isClient) {
                if (itemStack.hasNbt()) {
                    assert itemStack.getNbt() != null;
                    String spell = itemStack.getNbt().getString("spell");
                    Interpreter interpreter = new Interpreter();
                    interpreter.CastSpell(spell, world, user);
                }
            }
        } else {
            if (!world.isClient) {
                user.openHandledScreen(this);
            }
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("magic-programming.wand.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        if (player.getInventory().getMainHandStack().getNbt() != null)
            if (player.getInventory().getMainHandStack().getNbt().getString("spell") != null)
                buf.writeString(player.getInventory().getMainHandStack().getNbt().getString("spell"));
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Wand Editor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        if (playerInventory.getMainHandStack().getNbt() != null)
            if (playerInventory.getMainHandStack().getNbt().contains("spell"))
                return new WandScreenHandler(syncId, playerInventory,
                    PacketByteBufs.create().writeString((playerInventory.getMainHandStack().getNbt().getString("spell"))));
            else return new WandScreenHandler(syncId, playerInventory, PacketByteBufs.create().writeString(""));
        else return new WandScreenHandler(syncId, playerInventory, PacketByteBufs.create().writeString(""));
    }
}