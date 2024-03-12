package hrelics.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

public interface ServerWorldInterface {



    public default void scheduleDamageEvent(Entity attacker, Vec3d pos){
        //called by NagaProjectileEntity when it hits something in order to schedule the AoE in 30 ticks
    }

    public default void scheduleNagaParticles(Entity target, ServerPlayerEntity player, World world){

    }

    public default void scheduleNagaLightning(Long delay, Vec3d pos, World w){
        // called in ChestBlockEntityMixin to summon delayed lightning when opening the naga tome chest
    }

    public default void nagaParticleHere(UUID u){

    }
}
