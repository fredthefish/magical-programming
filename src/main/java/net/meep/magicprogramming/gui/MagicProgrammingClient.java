package net.meep.magicprogramming.gui;

import net.fabricmc.api.ClientModInitializer;
import net.meep.magicprogramming.MagicalProgrammingMod;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MagicProgrammingClient implements ClientModInitializer {

    public void onInitializeClient() {
        HandledScreens.register(MagicalProgrammingMod.WAND_SCREEN_HANDLER, WandScreen::new);
    }
}
