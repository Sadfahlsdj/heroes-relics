package hrelics.item.custom;

import hrelics.HeroesRelics;
import hrelics.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

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
        Entity entity = entityHitResult.getEntity();
        int i = 1; //
        entity.damage(DamageSource.MAGIC, i);

        ((LivingEntityInterface) entity).setNagaWaitTicks(30);
        ((LivingEntityInterface) entity).setHitByNaga(true);
        HeroesRelics.LOGGER.info(Integer.toString(((LivingEntityInterface) entity).getNagaWaitTicks()));

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
