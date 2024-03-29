package hrelics;

import hrelics.client.particle.NagaProjectileEntityRenderer;
import hrelics.client.particle.ValflameProjectileEntityRenderer;
import hrelics.event.KeyInputHandler;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
import hrelics.item.custom.LivingEntityInterface;
import hrelics.item.custom.NagaProjectileEntity;
import hrelics.networking.ModMessages;
import hrelics.particle.ModParticles;
import hrelics.particle.custom.ForsetiParticle;
import hrelics.particle.custom.NagaParticle;
import hrelics.particle.custom.ValflameStrongParticle;
import hrelics.particle.custom.ValflameWeakParticle;
import hrelics.sound.ModSounds;
import hrelics.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class HrelicsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModItems.NagaProjectileEntityType, (context) ->
                new NagaProjectileEntityRenderer(context));
        EntityRendererRegistry.register(ModItems.ValflameProjectileEntityType, (context) ->
                new ValflameProjectileEntityRenderer(context));


        ModMessages.registerS2CPackets();

        KeyInputHandler.register();
        ModModelPredicateProvider.registerModModels();

        ParticleFactoryRegistry.getInstance().register(ModParticles.NAGA_PARTICLE, NagaParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FORSETI_PARTICLE, ForsetiParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.VALFLAME_STRONG_PARTICLE, ValflameStrongParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.VALFLAME_WEAK_PARTICLE, ValflameWeakParticle.Factory::new);

        //naga particle packet is below
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

        //naga hit sound packet is below
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.NAGAHITSOUND, ((client, handler, buf, responseSender) -> {
            BlockPos soundLocation = buf.readBlockPos();

            client.execute(() -> {

                client.world.playSoundAtBlockCenter(soundLocation, ModSounds.NAGAHIT, SoundCategory.PLAYERS, 1f, 1f, true);
                //HeroesRelics.LOGGER.info("naga hit sound goes here");
            });
        }));

        //forseti particle packet is below
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.FORSETIPARTICLE, ((client, handler, buf, responseSender) -> {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            double xV = buf.readDouble();
            double zV = buf.readDouble();

            client.execute(() -> {
                client.world.addParticle(ModParticles.FORSETI_PARTICLE, true, x, y, z, xV, 0, zV);
                //HeroesRelics.LOGGER.info("forseti particle goes here");
            });
        }));

        ClientPlayNetworking.registerGlobalReceiver(ModMessages.VALFLAMEPARTICLE, ((client, handler, buf, responseSender) -> {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            boolean strong = buf.readBoolean();

            client.execute(() -> {
                if(strong){
                    client.world.addParticle(ModParticles.VALFLAME_STRONG_PARTICLE, true, x, y, z, 0, 30, 0);
                }
                else{
                    client.world.addParticle(ModParticles.VALFLAME_WEAK_PARTICLE, true, x, y, z, 0, 30, 0);
                }
            });
        }));

        ClientPlayNetworking.registerGlobalReceiver(ModMessages.TYRFINGTICKS, ((client, handler, buf, responseSender) -> {
            //Text t = buf.readText();
            UUID u = buf.readUuid();
            //double x = buf.readDouble();
            //double y = buf.readDouble();
            //double z = buf.readDouble();
            PlayerEntity p = client.world.getPlayerByUuid(u);


            client.execute(() -> {
                ((LivingEntityInterface) p).setTyrfingDamageTicks(100);
            });
        }));
    }


}
