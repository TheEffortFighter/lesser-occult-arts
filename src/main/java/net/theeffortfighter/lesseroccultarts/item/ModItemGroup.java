package net.theeffortfighter.lesseroccultarts.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;

public class ModItemGroup {
    public static final ItemGroup LESSER_OCCULT_ARTS = FabricItemGroupBuilder.build(new Identifier(LesserOccultArts.MOD_ID, "lesser_occult_arts"), () -> new ItemStack(ModItems.GOTD));
}
