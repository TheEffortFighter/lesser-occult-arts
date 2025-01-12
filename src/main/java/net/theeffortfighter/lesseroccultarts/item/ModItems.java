package net.theeffortfighter.lesseroccultarts.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.item.custom.*;

public class ModItems {

    public static final Item GOTD = registerItem("greatsword_of_the_damned", new UnbreakableItem(10, 1, new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item DAGGER = registerItem("covenant_dagger", new CovenantDagger(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item AMULET = registerItem("amulet_of_uncursing", new UncursingAmulet(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item EFFIGY = registerItem("abyssal_effigy", new AbbyssalEffigy(new FabricItemSettings().maxCount(1).maxDamage(600).group(ModItemGroup.LESSER_OCCULT_ARTS), 1, 1));
    public static final Item CROWN = registerItem("monarch_crown", new MonarchCrown(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item MACE = registerItem("malice_mace", new MaliceMace(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item SEAL = registerItem("monarch_seal", new MonarchSeal(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(LesserOccultArts.MOD_ID, name), item);
    }

    public static void registerModItems() {

    }
}
