package hrelics.mixin;

import hrelics.HeroesRelics;
import hrelics.item.ModItems;
import hrelics.item.custom.LivingEntityInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityInterface {

    DamageSource source;
    @Inject(method = "damage", at = @At("HEAD"))
    protected void getSource(DamageSource source, float f, CallbackInfoReturnable cir){
        this.source = source;
    }
    //no callbackinfo on modifyvariable
    @ModifyVariable(method = "damage", at = @At("HEAD"))
    public float boostFireDamage(float f){
       if(source.isFire() && boostedFireTicks > 0){
            //burning quake logic
            f += 6;

       }
       //testing
       //HeroesRelics.LOGGER.info("{} {} {}", source, source.isFire(), boostedFireTicks);
       return f;
    }



    @Inject(method = "tick", at = @At("HEAD"))
    protected void decrementFireTicks(CallbackInfo ci){
        if(boostedFireTicks > 0){
            //makes boostedfireticks decrement as a tick would
            boostedFireTicks--;
        }

    }


    public int boostedFireTicks = 0;

    public void setBoostedTicks(int i){
        boostedFireTicks = i;

        //testing
        //HeroesRelics.LOGGER.info(String.valueOf(boostedFireTicks));
    }
}
