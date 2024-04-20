package net.meep.magicprogramming.item;

import io.wispforest.lavender.book.LavenderBookItem;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.OwoItemSettings;
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
    public static OwoItemGroup WAND_GROUP;
    public static Item WAND;
    static {
        WAND_GROUP = OwoItemGroup.builder(
                new Identifier(MagicalProgrammingMod.MOD_ID, "magical-programming"), () -> Icon.of(WAND)).build();
        WAND = registerItem("wand", new WandItem(
                new OwoItemSettings().maxCount(1).rarity(Rarity.EPIC).fireproof().group(WAND_GROUP)));
    }
    @SuppressWarnings("unused") //It has to be a variable for it to work.
    public static final Item SPELL_BOOK = LavenderBookItem.registerForBook(
            new Identifier(MagicalProgrammingMod.MOD_ID, "spellscript"),
            new Identifier(MagicalProgrammingMod.MOD_ID, "spellscript_book"),
            new OwoItemSettings().group(WAND_GROUP).maxCount(1));
    //To add more items, just add a new variable, and add it to the entries, and add assets for it.

    public static void registerModItems() {
        MagicalProgrammingMod.LOGGER.info("Registering mod items for " + MagicalProgrammingMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToCreativeTab);
    }
    @SuppressWarnings("all") //"Actual value of name is always wand"
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicalProgrammingMod.MOD_ID, name), item);
    }
    private static void addItemsToCreativeTab(FabricItemGroupEntries entries) {
        entries.add(WAND);
    }
}