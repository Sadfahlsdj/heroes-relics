package hrelics.particle;

import hrelics.HeroesRelics;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType NAGA_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType FORSETI_PARTICLE = FabricParticleTypes.simple();

    public static final DefaultParticleType VALFLAME_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles(){
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(HeroesRelics.MOD_ID, "naga_particle"), NAGA_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(HeroesRelics.MOD_ID, "forseti_particle"), FORSETI_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(HeroesRelics.MOD_ID, "valflame_particle"), VALFLAME_PARTICLE);
    }
}
