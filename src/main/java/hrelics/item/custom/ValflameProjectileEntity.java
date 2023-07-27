package hrelics.item.custom;

import hrelics.item.ModItems;
import hrelics.particle.ModParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ValflameProjectileEntity extends ThrownItemEntity {
    public ValflameProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {

        super(entityType, world);
    }

    public ValflameProjectileEntity(World world, LivingEntity owner) {

        super(ModItems.ValflameProjectileEntityType, owner, world);
    }

    public ValflameProjectileEntity(World world, double x, double y, double z) {

        super(ModItems.ValflameProjectileEntityType, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ModItems.ValflameProjectileItem;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            int power = ((ThrownItemEntityInterface) this).getValflameUseTime();
            if(power <= 5){
                this.world.createExplosion(this.getOwner(), hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z, (float)(1 + 0.5 * power), false, World.ExplosionSourceType.MOB);
            }
            else{
                this.world.createExplosion(this.getOwner(), hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z, (float)(3.5 + (power - 5)), false, World.ExplosionSourceType.MOB);

            }
            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
        }

    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ModParticles.VALFLAME_WEAK_PARTICLE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }
}
