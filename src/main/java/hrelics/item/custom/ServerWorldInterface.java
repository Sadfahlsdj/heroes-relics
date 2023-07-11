package hrelics.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface ServerWorldInterface {

    public default void scheduleDamageEvent(Entity attacker, Vec3d pos){
        //called by NagaProjectileEntity when it hits something in order to schedule the AoE in 30 ticks
    }

    public default void scheduleNagaParticles(Entity target, World world){

    }
}
