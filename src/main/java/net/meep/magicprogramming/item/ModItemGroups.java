package net.meep.magicprogramming.item;

import io.wispforest.owo.itemgroup.Icon;

public class ModItemGroups {

    public static void registerItemGroups() {
        ModItems.WAND_GROUP.addTab(Icon.of(ModItems.WAND), "Magical Programming", null, false);
        ModItems.WAND_GROUP.initialize();
    }
}
