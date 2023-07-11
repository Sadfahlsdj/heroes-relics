package hrelics.item.custom;

import hrelics.HeroesRelics;
import hrelics.item.ModItems;
import hrelics.particle.ModParticles;
import hrelics.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.entity.damage.DamageSource.*;

public class NagaProjectileEntity extends SnowballEntity {


    public NagaProjectileEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public NagaProjectileEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    public NagaProjectileEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected Item getDefaultItem() {
        return ModItems.NagaProjectileItem;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        this.getWorld().playSoundAtBlockCenter(this.getOwner().getBlockPos(), ModSounds.NAGAHIT, SoundCategory.PLAYERS, 1f, 1f, true);
        Entity target = entityHitResult.getEntity();
        int i = 1; //
        target.damage(DamageSource.MAGIC, i);

        //below is part of working but extremely inefficient code that is commented out in order to use birb's code instead

        /*((LivingEntityInterface) target).setNagaWaitTicks(30);
        ((LivingEntityInterface) target).setHitByNaga(true);
        HeroesRelics.LOGGER.info(Integer.toString(((LivingEntityInterface) target).getNagaWaitTicks()));*
        */
        if(this.getWorld() instanceof ServerWorld){
            ((ServerWorldInterface) (ServerWorld) this.getWorld()).scheduleDamageEvent(this.getOwner(), target.getPos());
            ((ServerWorldInterface) (ServerWorld) this.getWorld()).scheduleNagaParticles(target, (ServerPlayerEntity) this.getOwner(), this.getWorld());

            //stuff below is my (broken, do not uncomment) attempt at spawning the beam of light particles
            /*World w = this.getWorld();
            int x = target.getBlockPos().getX();
            int y = target.getBlockPos().getY() + 64;
            int z = target.getBlockPos().getZ();
            List<Long> particleList = new ArrayList<Long>();
            particleList.add(world.getTime() + 30);

            for(Long l : particleList){
                while(l > world.getTime()){
                    for(int j = x - 5; j < x + 5; j += 0.5d){
                        for(int k = z - 5; k < z + 5; k += 0.5d){
                            w.addParticle(ModParticles.NAGA_PARTICLE, j, y, z, 0, -5d, 0);
                            //tick(); DONT DO THAT
                        }
                    }
                    l--;
                }
                particleList.remove(l);
            }*/



        }

        //below is working but extremely inefficient code that is commented out in order to use birb's code instead

        /*while(true){
            if(((LivingEntityInterface) target).getNagaActivation() == true){
                Vec3d pos = target.getPos();
                int radius = 2; //radius of naga AoE
                target.getWorld().getOtherEntities(target, Box.from(pos).expand(radius), (entity) ->
                        pos.squaredDistanceTo(target.getPos()) < Math.pow(radius, 2)).forEach(
                        (entity) -> entity.damage(DamageSource.MAGIC, 20)
                );
            }
            ((LivingEntityInterface) target).setNagaActivation(false);
            break;
        }*/

        /*if(((LivingEntityInterface) entity).getNagaWaitTicks() == 0){ //filler comment to let me push
            HeroesRelics.LOGGER.info("Finished waiting 30 naga ticks");
        }*/
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
        }

    }
}
