package net.meep.magicprogramming.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.meep.magicprogramming.MagicalProgrammingMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class WandScreenHandler extends ScreenHandler {
    final PlayerInventory inventory;
    final PacketByteBuf buf;
    public WandScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        super(MagicalProgrammingMod.WAND_SCREEN_HANDLER, syncId);
        inventory = playerInventory;
        buf = packetByteBuf;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void changeText(String spell) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(syncId);
        buf.writeString(spell);
        ClientPlayNetworking.send(new NBTPacket(buf));
    }
}

