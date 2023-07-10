package hrelics.mixin;

import hrelics.item.custom.ServerWorldInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements ServerWorldInterface {

    public final Queue<Triple<Long, Vec3d, Entity>> nagaTomeQueue = new PriorityQueue<>();
    public void scheduleDamageEvent(Entity attacker, Vec3d pos){
        //update the 30 to another value to change delay before attack
        nagaTomeQueue.add(Triple.of(attacker.getWorld().getTime() + 30L, pos, attacker));
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/EntityList;forEach(Ljava/util/function/Consumer;)V"))
    private void handleTomeDamage(BooleanSupplier shouldKeepTicking, CallbackInfo ci){
        ServerWorld world = (ServerWorld) (Object) this;
        Triple<Long, Vec3d, Entity> t;
        while((t = nagaTomeQueue.peek()) != null && t.getLeft() <= world.getTime()){
            //damages in an aoe once 30 ticks are up
            Vec3d pos = t.getMiddle();
            Entity owner = t.getRight();
            int radius = 5; //change for balance
            world.getOtherEntities(t.getRight(), Box.from(pos).expand(radius), (e) -> e.getPos().squaredDistanceTo(pos) <= Math.pow(radius, 2))
                    .forEach((e) -> e.damage(DamageSource.MAGIC, 20));
            nagaTomeQueue.poll();
        }

    }

}
