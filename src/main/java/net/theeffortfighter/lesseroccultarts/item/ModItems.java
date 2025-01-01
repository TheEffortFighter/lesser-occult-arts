package net.theeffortfighter.lesseroccultarts.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.item.custom.UnbreakableItem;

public class ModItems {
    public static final Item GOTD = registerItem("greatsword_of_the_damned", new SwordItem(ModToolMaterials.LOA,10,1, new FabricItemSettings().group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item DAGGER = registerItem("covenant_dagger", new SwordItem(ModToolMaterials.LOA,10,1, new FabricItemSettings().group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item AMULET = registerItem("amulet_of_uncursing", new Item(new FabricItemSettings().group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item EFFIGY = registerItem("abyssal_effigy", new Item(new FabricItemSettings().group(ModItemGroup.LESSER_OCCULT_ARTS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(LesserOccultArts.MOD_ID, name), item);
    }

    public static void registerModItems() {

    }
}
