package hrelics.mixin;

import hrelics.HeroesRelics;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
import hrelics.item.ModToolMaterials;
import hrelics.item.custom.LivingEntityInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityInterface {

    DamageSource source;
    LivingEntity target = (LivingEntity) (Object) this;
    LivingEntity user = (LivingEntity) (Object) this;
    @Inject(method = "damage", at = @At("HEAD"))
    protected void getSource(DamageSource source, float f, CallbackInfoReturnable cir){
        this.source = source;
    }

    //no callbackinfo on modifyvariable
    @ModifyVariable(method = "damage", at = @At("HEAD"))
    public float damageModifications(float f){
       if(source.isFire() && boostedFireTicks > 0){
            //burning quake logic
            f += 6;

       }
       if(source == DamageSource.WITHER && boostedWitherTicks > 0){
           //beast fang logic; disables target iframes so that wither values > 3 actually have an effect
           target.hurtTime = 0;
           target.timeUntilRegen = 1;
       }

       if(target instanceof PlayerEntity && target.getOffHandStack().isOf(ModItems.AegisShield)){
           //aegis shield passive DR
           f -= 2;
       }
       //testing
       //HeroesRelics.LOGGER.info("{} {} {}", source, source.isFire(), boostedFireTicks);
       return f;
    }

    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    protected void holdingAegisShield(CallbackInfoReturnable<Boolean> cir){
        if(user instanceof PlayerEntity && user.getOffHandStack().getItem().equals(ModItems.AegisShield)){
            cir.setReturnValue(false);
            //setReturnValue instantly forces the method to return, and sets the return value
        }
    }



    @Inject(method = "tick", at = @At("HEAD"))
    protected void decrementFireTicks(CallbackInfo ci){
        if(boostedFireTicks > 0){
            //makes boostedfireticks decrement as a tick would
            boostedFireTicks--;
        }
        if(boostedWitherTicks > 0){
            boostedWitherTicks--;
        }
        Item mainHandItem = user.getMainHandStack().getItem();
        if(mainHandItem instanceof ToolItem) {
            //if mainhand is of tool material UMBRAL_STEEL & offhand is aegis shield & shieldselfdamageticks < 20, start ticking up
            if (user instanceof PlayerEntity && ((ToolItem) mainHandItem).getMaterial().equals
                    (ModToolMaterials.UMBRAL_STEEL)
                    && user.getOffHandStack().isOf(ModItems.AegisShield) && shieldSelfDamageTicks < 20) {
                shieldSelfDamageTicks++;
                //HeroesRelics.LOGGER.info("Adding a tick to shieldselfdamageticks");
            }
            if (user instanceof PlayerEntity && ((ToolItem) mainHandItem).getMaterial().equals
                    (ModToolMaterials.UMBRAL_STEEL)
                    && user.getOffHandStack().isOf(ModItems.AegisShield) && shieldSelfDamageTicks == 20) {
                user.damage(DamageSource.OUT_OF_WORLD, 4);
                shieldSelfDamageTicks = 0;
                //HeroesRelics.LOGGER.info("shieldselfdamageticks hit 20, resetting it");
            }
            if (user instanceof PlayerEntity && !(((ToolItem) mainHandItem).getMaterial().equals
                    (ModToolMaterials.UMBRAL_STEEL))
                    || !(user.getOffHandStack().isOf(ModItems.AegisShield))) {
                shieldSelfDamageTicks = 0;
                //HeroesRelics.LOGGER.info("not holding 2 relic items, resetting shieldselfdamageticks");
            }
        }






    }


    public int boostedFireTicks = 0;
    public int boostedWitherTicks = 0;

    public int shieldSelfDamageTicks = 0;
    //used for the selfdamage when holding aegis shield + a relic weapon

    public void setBoostedFireTicks(int i){
        boostedFireTicks = i;

        //testing
        //HeroesRelics.LOGGER.info(String.valueOf(boostedFireTicks));
    }
    public void setBoostedWitherTicks(int i){
        boostedWitherTicks = i;
    }
}
