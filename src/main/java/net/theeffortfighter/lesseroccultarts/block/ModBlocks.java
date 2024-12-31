package net.theeffortfighter.lesseroccultarts.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.block.custom.CovenantStone;


public class ModBlocks {

    public static final Block COVENANT_STONE = registerBlock("covenant_stone", new CovenantStone(FabricBlockSettings.of(Material.STONE).collidable(true)), ItemGroup.MISC);

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(LesserOccultArts.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(LesserOccultArts.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerModBlocks() {

    }
}
