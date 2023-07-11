package hrelics;

import hrelics.event.KeyInputHandler;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
import hrelics.networking.ModMessages;
import hrelics.particle.ModParticles;
import hrelics.particle.custom.NagaParticle;
import hrelics.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HrelicsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {




        ModMessages.registerS2CPackets();

        KeyInputHandler.register();
        ModModelPredicateProvider.registerModModels();

        ParticleFactoryRegistry.getInstance().register(ModParticles.NAGA_PARTICLE, NagaParticle.Factory::new);

        ClientPlayNetworking.registerGlobalReceiver(ModMessages.NAGAPARTICLE, ((client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int x = buf.readInt();
            int y = buf.readInt();
            int z = buf.readInt();

            client.execute(() -> {
                //client.world.addParticle(ModParticles.NAGA_PARTICLE, x, y, z, 0, -32d, 0);
                client.world.addParticle(ModParticles.NAGA_PARTICLE, true, x, y, z, 0, -32d, 0);
                //HeroesRelics.LOGGER.info("particle goes here");
            });
        }));

    }
}
