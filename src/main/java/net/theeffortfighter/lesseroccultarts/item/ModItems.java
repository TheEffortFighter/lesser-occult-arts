package net.theeffortfighter.lesseroccultarts.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.item.custom.AbbyssalEffigy;
import net.theeffortfighter.lesseroccultarts.item.custom.UnbreakableItem;

public class ModItems {

    public static final Item GOTD = registerItem("greatsword_of_the_damned", new UnbreakableItem(10,1, new FabricItemSettings().group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item DAGGER = registerItem("covenant_dagger", new Item(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item AMULET = registerItem("amulet_of_uncursing", new Item(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item EFFIGY = registerItem("abyssal_effigy", new AbbyssalEffigy(new FabricItemSettings().maxCount(1).maxDamage(600).group(ModItemGroup.LESSER_OCCULT_ARTS), 1, 1));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(LesserOccultArts.MOD_ID, name), item);
    }

    public static void registerModItems() {

    }
}
