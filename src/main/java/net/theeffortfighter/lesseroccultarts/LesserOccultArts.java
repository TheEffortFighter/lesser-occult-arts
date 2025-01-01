package net.theeffortfighter.lesseroccultarts;

import net.fabricmc.api.ModInitializer;
import net.theeffortfighter.lesseroccultarts.block.ModBlocks;
import net.theeffortfighter.lesseroccultarts.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LesserOccultArts implements ModInitializer {
	public static final String MOD_ID = "loa";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();

	}
}
