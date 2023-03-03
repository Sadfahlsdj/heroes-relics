package hrelics.mixin;

import hrelics.item.ModItems;
import hrelics.item.custom.PlayerEntityInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityInterface {
    int atrocityHits = 0;

    public void setAtrocityHits(int a){
        atrocityHits = a;
    }

    public int getAtrocityHits(){
        return atrocityHits;
    }

    public void decrementAtrocityHits(){
        atrocityHits--;
    }

    Entity t;

    @Inject(method = "attack", at = @At("HEAD"))
    protected void getTarget(Entity target, CallbackInfo cir){
        this.t = target;
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private float modifyDamage(float f){
        PlayerEntity user = (PlayerEntity) (Object) this;
        if(user.getMainHandStack().isOf(ModItems.Areadbhar) && ((PlayerEntityInterface) user).getAtrocityHits() > 0){
            f += 15;
            ((PlayerEntityInterface) user).decrementAtrocityHits();
        }
        return f;
    }
}
