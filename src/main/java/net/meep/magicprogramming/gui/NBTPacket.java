package net.meep.magicprogramming.gui;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.meep.magicprogramming.MagicalProgrammingMod;
import net.minecraft.network.PacketByteBuf;

public record NBTPacket(int syncId, String spell) implements FabricPacket {
    public static final PacketType<NBTPacket> TYPE = PacketType.create(MagicalProgrammingMod.NBT_PACKET_ID, NBTPacket::new);
    public NBTPacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readString());
    }
    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.syncId);
        buf.writeString(this.spell);
    }
    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
