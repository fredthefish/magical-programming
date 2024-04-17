package net.meep.magicprogramming;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.meep.magicprogramming.gui.WandScreenHandler;
import net.meep.magicprogramming.item.ModItemGroups;
import net.meep.magicprogramming.item.ModItems;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicalProgrammingMod implements ModInitializer {
	public static final String MOD_ID = "magical-programming";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ScreenHandlerType<WandScreenHandler> WAND_SCREEN_HANDLER =
			Registry.register(Registries.SCREEN_HANDLER, new Identifier(MOD_ID, "wand_screen"),
					new ExtendedScreenHandlerType<>(WandScreenHandler::new));
	public static final Identifier NBT_PACKET_ID = new Identifier(MOD_ID, "nbt_packet");

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		ServerPlayNetworking.registerGlobalReceiver(NBT_PACKET_ID, (server, player, handler, buf, responseSender) -> {
			int syncId = buf.readInt();
			if (player.currentScreenHandler instanceof WandScreenHandler screenHandler) {
				if (screenHandler.syncId != syncId) return;
			}
			String spell = buf.readString();
			player.getInventory().getMainHandStack().getOrCreateNbt().putString("spell", spell);
		});
	}
}