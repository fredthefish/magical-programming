package net.meep.magicprogramming.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.meep.magicprogramming.MagicalProgrammingMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item WAND = registerItem("wand", new WandItem(
            new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC).fireproof()
    ));
    //To add more items, just add a new variable, and add it to the entries, and add assets for it.

    public static void registerModItems() {
        MagicalProgrammingMod.LOGGER.info("Registering mod items for " + MagicalProgrammingMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToCreativeTab);
    }
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicalProgrammingMod.MOD_ID, name), item);
    }
    private static void addItemsToCreativeTab(FabricItemGroupEntries entries) {
        entries.add(WAND);
    }
}