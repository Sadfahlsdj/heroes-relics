package hrelics.mixin;

import hrelics.HeroesRelics;
import hrelics.item.custom.ServerWorldInterface;
import hrelics.networking.ModMessages;
import hrelics.particle.ModParticles;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.Pair;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;
import java.util.function.BooleanSupplier;

import static hrelics.networking.ModMessages.ATROCITY;
import static hrelics.networking.ModMessages.NAGAPARTICLE;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements ServerWorldInterface {

    public final Queue<Triple<Long, Vec3d, Entity>> nagaTomeQueue = new PriorityQueue<>();
    public final ArrayList<MutableTriple<Long, ServerPlayerEntity, Entity>> particleList = new ArrayList<>();


    public void scheduleDamageEvent(Entity attacker, Vec3d pos){
        //update the 30 to another value to change delay before attack
        nagaTomeQueue.add(Triple.of(attacker.getWorld().getTime() + 20L, pos, attacker));
    }

    public void scheduleNagaParticles(Entity target, ServerPlayerEntity player, World world){
        //update the 30 to another value to change delay before attack
        particleList.add(MutableTriple.of(world.getTime() + 30L, player, target));
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
            int radius = 7; //change for balance
            w.getOtherEntities(t.getRight(), Box.from(pos).expand(radius), (e) -> e.getPos().squaredDistanceTo(pos) <= Math.pow(radius, 2))
                    .forEach((e) -> e.damage(DamageSource.MAGIC, 20));
            nagaTomeQueue.poll();
        }

        //particles
        for(MutableTriple<Long, ServerPlayerEntity, Entity> p : particleList){
            if(p.getLeft() > w.getTime()){
                int x = p.getRight().getBlockPos().getX();
                int y = p.getRight().getBlockPos().getY() + 32;
                int z = p.getRight().getBlockPos().getZ();
                for(int j = x - 5; j < x + 5; j += 1){
                    for(int k = z - 5; k < z + 5; k += 1){
                        //w.addParticle(ModParticles.NAGA_PARTICLE, j, y, k, 0, -5d, 0);
                        ((ServerWorldInterface) (ServerWorld) w).nagaParticleHere(p.getRight().getUuid());
                        //networking stuff below
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeBlockPos(p.getRight().getBlockPos());
                        buf.writeInt(j);
                        buf.writeInt(y);
                        buf.writeInt(k);

                        ServerPlayNetworking.send((ServerPlayerEntity) p.getMiddle(), NAGAPARTICLE, buf);
                        //HeroesRelics.LOGGER.info("particle goes here");
                        //tick(); DONT DO THAT
                    }
                }
                p.setLeft(p.getLeft() - 1);
            }

            //particleList.remove(p);
        }

    }


}
