package net.theeffortfighter.lesseroccultarts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.mob.VexEntity;
import net.theeffortfighter.lesseroccultarts.block.ModBlocks;
import net.theeffortfighter.lesseroccultarts.entity.ModBlockEntities;
import net.theeffortfighter.lesseroccultarts.entity.ModEntities;
import net.theeffortfighter.lesseroccultarts.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class LesserOccultArts implements ModInitializer {
	public static final String MOD_ID = "loa";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModBlockEntities.registerBlockEntities();
		GeckoLib.initialize();
		FabricDefaultAttributeRegistry.register(ModEntities.DEMON, VexEntity.createVexAttributes());
		ModBlockEntities.initialize();

	}
}
