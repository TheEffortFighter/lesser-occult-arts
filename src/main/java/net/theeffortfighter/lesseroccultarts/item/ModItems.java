package net.theeffortfighter.lesseroccultarts.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.item.custom.UnbreakableItem;

public class ModItems {
    public static final Item UNBREAKABLE = new UnbreakableItem(new Item.Settings().maxCount(1));

    public static final Item GOTD = registerItem("greatsword_of_the_damned", new SwordItem(ToolMaterials.WOOD,11,1, new FabricItemSettings().group(ModItemGroup.LESSER_OCCULT_ARTS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(LesserOccultArts.MOD_ID, name), UNBREAKABLE);
    }

    public static void registerModItems() {

    }
}
