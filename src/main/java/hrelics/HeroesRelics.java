package hrelics;

import hrelics.configs.ModConfigs;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
import hrelics.networking.ModMessages;
import hrelics.sound.ModSounds;
import hrelics.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;

import net.minecraft.sound.SoundEvent;
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
		ModConfigs.registerConfigs();

		ModItemGroup.registerModItemGroup();
		ModItems.registerModItems();
		//registerModItemGroup has to go on top of registerModItems

		//LOGGER.info("Hello Fabric world!");
		ModMessages.registerC2SPackets();
		ModLootTableModifiers.modifyLootTables();

		ModSounds.ATROCITY1 = ModSounds.registerSoundEvent("atrocity1");
		ModSounds.ATROCITY2 = ModSounds.registerSoundEvent("atrocity2");
		ModSounds.ATROCITY3 = ModSounds.registerSoundEvent("atrocity3");
		ModSounds.ATROCITY4 = ModSounds.registerSoundEvent("atrocity4");

	}
}