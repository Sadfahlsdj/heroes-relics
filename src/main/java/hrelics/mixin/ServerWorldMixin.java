package hrelics.mixin;

import hrelics.item.custom.ServerWorldInterface;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.Pair;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BooleanSupplier;

import static hrelics.networking.ModMessages.NAGAPARTICLE;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements ServerWorldInterface {

    public final Queue<Triple<Long, Vec3d, Entity>> nagaTomeQueue = new PriorityQueue<>();

    public final Queue<Triple<Long, Vec3d, World>> nagaCryptLightningQueue = new PriorityQueue<>();
    public final ArrayList<MutableTriple<Long, ServerPlayerEntity, Entity>> nagaParticleList = new ArrayList<>();




    public void scheduleDamageEvent(Entity attacker, Vec3d pos){
        //update the 30 to another value to change delay before attack
        nagaTomeQueue.add(Triple.of(attacker.getWorld().getTime() + 20L, pos, attacker));
    }

    public void scheduleNagaLightning(Long delay, Vec3d pos, World w){
        // used to spawn delayed lightning bolts
        nagaCryptLightningQueue.add(Triple.of(w.getTime() + delay, pos, w));
    }

    public void scheduleNagaParticles(Entity target, ServerPlayerEntity player, World world){
        //update the 30 to another value to change delay before attack
        nagaParticleList.add(MutableTriple.of(world.getTime() + 30L, player, target));
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

        Triple<Long, Vec3d, World> l;

        // lightning when opening the naga tome chest
        while((l = nagaCryptLightningQueue.peek()) != null && l.getLeft() <= w.getTime()){
            // summons lightning after delay
            // super similar code to the above loop for naga tome damage event
            Vec3d pos = l.getMiddle();
            LightningEntity l1 = new LightningEntity(EntityType.LIGHTNING_BOLT, w);
            l1.setPosition(pos);
            w.spawnEntity(l1);
            nagaCryptLightningQueue.poll();
        }



        //particles
        for(MutableTriple<Long, ServerPlayerEntity, Entity> p : nagaParticleList){
            if(p.getLeft() > w.getTime()){
                int x = p.getRight().getBlockPos().getX();
                int y = p.getRight().getBlockPos().getY() + 32;
                int z = p.getRight().getBlockPos().getZ();
                for(int j = x - 5; j < x + 5; j += 1){
                    for(int k = z - 5; k < z + 5; k += 1){
                        //w.addParticle(ModParticles.NAGA_PARTICLE, j, y, k, 0, -5d, 0);
                        ((ServerWorldInterface) (ServerWorld) w).nagaParticleHere(p.getRight().getUuid());
                        //networking stuff below
                        PacketByteBuf NagaParticlePacket = PacketByteBufs.create();
                        NagaParticlePacket.writeBlockPos(p.getRight().getBlockPos());
                        NagaParticlePacket.writeInt(j);
                        NagaParticlePacket.writeInt(y);
                        NagaParticlePacket.writeInt(k);

                        ServerPlayNetworking.send((ServerPlayerEntity) p.getMiddle(), NAGAPARTICLE, NagaParticlePacket);
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
