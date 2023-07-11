package hrelics.mixin;

import hrelics.item.custom.ServerWorldInterface;
import hrelics.particle.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.Pair;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements ServerWorldInterface {

    public final Queue<Triple<Long, Vec3d, Entity>> nagaTomeQueue = new PriorityQueue<>();
    public final ArrayList<MutablePair<Long, Entity>> particleList = new ArrayList<>();
    public void scheduleDamageEvent(Entity attacker, Vec3d pos){
        //update the 30 to another value to change delay before attack
        nagaTomeQueue.add(Triple.of(attacker.getWorld().getTime() + 30L, pos, attacker));
    }

    public void scheduleNagaParticles(Entity target, World world){
        //update the 30 to another value to change delay before attack
        particleList.add(MutablePair.of(world.getTime() + 30L, target));
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/EntityList;forEach(Ljava/util/function/Consumer;)V"))
    private void handleTomeParticlesDamage(BooleanSupplier shouldKeepTicking, CallbackInfo ci){
        ServerWorld w = (ServerWorld) (Object) this;
        Triple<Long, Vec3d, Entity> t;
        //damage
        while((t = nagaTomeQueue.peek()) != null && t.getLeft() <= w.getTime()){
            //damages in an aoe once 30 ticks are up
            Vec3d pos = t.getMiddle();
            Entity owner = t.getRight();
            int radius = 5; //change for balance
            w.getOtherEntities(t.getRight(), Box.from(pos).expand(radius), (e) -> e.getPos().squaredDistanceTo(pos) <= Math.pow(radius, 2))
                    .forEach((e) -> e.damage(DamageSource.MAGIC, 20));
            nagaTomeQueue.poll();
        }

        //particles
        for(MutablePair<Long, Entity> p : particleList){
            if(p.getLeft() > w.getTime()){
                int x = p.getRight().getBlockPos().getX();
                int y = p.getRight().getBlockPos().getY() + 32;
                int z = p.getRight().getBlockPos().getZ();
                for(int j = x - 5; j < x + 5; j += 1){
                    for(int k = z - 5; k < z + 5; k += 1){
                        w.addParticle(ModParticles.NAGA_PARTICLE, j, y, k, 0, -5d, 0);
                        //tick(); DONT DO THAT
                    }
                }
                p.setLeft(p.getLeft() - 1);
            }

            //particleList.remove(p);
        }

    }


}
