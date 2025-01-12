package net.theeffortfighter.lesseroccultarts.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<CovenantStoneBlockEntity> COVENANT_STONE;

    public static void registerBlockEntities() {
        COVENANT_STONE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(LesserOccultArts.MOD_ID, "covenant_stone"),
                BlockEntityType.Builder.create(CovenantStoneBlockEntity::new, ModBlocks.COVENANT_STONE).build(null)
        );
    }
}

