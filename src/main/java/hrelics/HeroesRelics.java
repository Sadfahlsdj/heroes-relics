package hrelics;

import hrelics.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeroesRelics implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("heroes-relics");

	public static final String MOD_ID = "hrelics";

	@Override
	public void onInitialize() {
		// asafasdfdfsadssfsadffdd
		ModItems.registerModItems();

		LOGGER.info("Hello Fabric world!");
	}
}