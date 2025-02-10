package net.theeffortfighter.lesseroccultarts.item;

import com.eliotlash.mclib.math.functions.classic.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.item.custom.*;
import net.theeffortfighter.lesseroccultarts.item.functions.ModdedArmorItem;

public class ModItems {

    public static final Item GOTD = registerItem("greatsword_of_the_damned", new UnbreakableItem(10, -3, new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item DAGGER = registerItem("covenant_dagger", new CovenantDagger(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item AMULET = registerItem("amulet_of_uncursing", new UncursingAmulet(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item EFFIGY = registerItem("abyssal_effigy", new AbbyssalEffigy(new FabricItemSettings().maxCount(1).maxDamage(600).group(ModItemGroup.LESSER_OCCULT_ARTS), 1, 1));
    public static final Item CROWN = registerItem("abyssal_crown", new ModdedArmorItem(5,5,20, EquipmentSlot.HEAD, new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item MACE = registerItem("malice_mace", new UnbreakableItem(8, -2.4F, new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item SEAL = registerItem("monarchs_seal", new MonarchSeal(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));
    public static final Item CHAIN = registerItem("abyssal_chains", new AbyssalChains(new FabricItemSettings().maxCount(1).group(ModItemGroup.LESSER_OCCULT_ARTS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(LesserOccultArts.MOD_ID, name), item);
    }

    public static void registerModItems() {

    }
}
