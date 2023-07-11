package hrelics;

import hrelics.event.KeyInputHandler;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
import hrelics.networking.ModMessages;
import hrelics.particle.ModParticles;
import hrelics.particle.custom.NagaParticle;
import hrelics.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class HrelicsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {




        ModMessages.registerS2CPackets();

        KeyInputHandler.register();
        ModModelPredicateProvider.registerModModels();

        ParticleFactoryRegistry.getInstance().register(ModParticles.NAGA_PARTICLE, NagaParticle.Factory::new);

    }
}
