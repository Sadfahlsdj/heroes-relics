package hrelics.mixin;

import hrelics.item.custom.PlayerEntityInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)

public class PersistentProjectileEntityMixin {
    LivingEntity target;
    Entity user;


    @Inject(method = "onEntityHit", at = @At("HEAD"))
    public void getTarget(EntityHitResult entityHitResult, CallbackInfo ci){
        this.target = (LivingEntity) entityHitResult.getEntity();
    }
    @ModifyVariable(method = "onEntityHit", at = @At("HEAD"))
    public EntityHitResult applyWeakness(EntityHitResult ehr){
        user = ((PersistentProjectileEntity) (Object) this).getOwner();
        if(user instanceof PlayerEntity && ((PlayerEntityInterface) user).getFallenStarHits() > 0){
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 127), user);

            ((PlayerEntityInterface) user).decrementFallenStarHits();
        }
        return ehr;
    }

}
