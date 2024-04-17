package net.meep.magicprogramming.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.meep.magicprogramming.MagicalProgrammingMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    private static final ItemGroup MP_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MagicalProgrammingMod.MOD_ID, "magical-programming"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.magical-programming"))
                    .icon(() -> new ItemStack(ModItems.WAND)).entries((displayContext, entries) ->
                            entries.add(ModItems.WAND)).build());

    public static void registerItemGroups() {
        MagicalProgrammingMod.LOGGER.info("Registering item groups for " + MagicalProgrammingMod.MOD_ID);
    }
}
