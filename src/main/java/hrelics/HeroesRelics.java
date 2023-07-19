package hrelics;

import hrelics.configs.ModConfigs;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
import hrelics.item.custom.NagaProjectileEntity;
import hrelics.networking.ModMessages;
import hrelics.particle.ModParticles;
import hrelics.sound.ModSounds;
import hrelics.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeroesRelics implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("heroes-relics");

	public static final String MOD_ID = "hrelics";

	/*public static final EntityType<NagaProjectileEntity> NagaProjectileEntityType = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "nagaprojectileentitytype"),
			FabricEntityTypeBuilder.<NagaProjectileEntity>create(SpawnGroup.MISC, NagaProjectileEntity::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build()); // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS*/

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();

		ModItemGroup.registerModItemGroup();
		ModItems.registerModItems();
		//registerModItemGroup has to go on top of registerModItems

		//LOGGER.info("Hello Fabric world!");
		ModMessages.registerC2SPackets();
		ModLootTableModifiers.modifyLootTables();

		//sounds
		ModSounds.ATROCITY1 = ModSounds.registerSoundEvent("atrocity1");
		ModSounds.ATROCITY2 = ModSounds.registerSoundEvent("atrocity2");
		ModSounds.ATROCITY3 = ModSounds.registerSoundEvent("atrocity3");
		ModSounds.ATROCITY4 = ModSounds.registerSoundEvent("atrocity4");
		ModSounds.BQUAKE1 = ModSounds.registerSoundEvent("bquake1");
		ModSounds.BQUAKE2 = ModSounds.registerSoundEvent("bquake2");
		ModSounds.FALLENSTAR1 = ModSounds.registerSoundEvent("fallenstar1");
		ModSounds.FALLENSTAR2 = ModSounds.registerSoundEvent("fallenstar2");
		ModSounds.FOUDROYANT1 = ModSounds.registerSoundEvent("foudroyant1");
		ModSounds.FOUDROYANT2 = ModSounds.registerSoundEvent("foudroyant2");
		ModSounds.RUINEDSKY1 = ModSounds.registerSoundEvent("ruinedsky1");
		ModSounds.RUINEDSKY2 = ModSounds.registerSoundEvent("ruinedsky2");
		ModSounds.RHEAVEN1 = ModSounds.registerSoundEvent("rheaven1");
		ModSounds.RHEAVEN2 = ModSounds.registerSoundEvent("rheaven2");
		ModSounds.WARP = ModSounds.registerSoundEvent("warp");
		ModSounds.NAGAACTIVATION = ModSounds.registerSoundEvent("nagaactivation");
		ModSounds.NAGAHIT = ModSounds.registerSoundEvent("nagahit");

		ModParticles.registerParticles();



	}
}