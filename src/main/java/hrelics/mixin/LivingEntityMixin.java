package hrelics.mixin;

import hrelics.HeroesRelics;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
import hrelics.item.ModToolMaterials;
import hrelics.item.custom.LivingEntityInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_ATTACK_DAMAGE;


@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityInterface {

    DamageSource source;
    LivingEntity target = (LivingEntity) (Object) this;
    LivingEntity user = (LivingEntity) (Object) this;

    EntityAttributeModifier tyrfingDamage = new EntityAttributeModifier("tyrfingdamage",
            1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
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
           f -= 1;
       }

       if(target instanceof PlayerEntity && tyrfingTicks > 0 && !source.isOutOfWorld()){
           f = 0;
           target.heal(3);

           //attribute for damage increase

           target.getAttributeInstance(GENERIC_ATTACK_DAMAGE).removeModifier(tyrfingDamage);
           target.getAttributeInstance(GENERIC_ATTACK_DAMAGE).addTemporaryModifier(tyrfingDamage);
           target.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0), target);
           setTyrfingDamageTicks(100);
       }
       //testing
       //HeroesRelics.LOGGER.info("{} {} {}", source, source.isFire(), boostedFireTicks);
       return f;
    }

    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    //this makes the aegis shield not able to be disabled
    protected void holdingAegisShield(CallbackInfoReturnable<Boolean> cir){
        if(user instanceof PlayerEntity && user.getOffHandStack().getItem().equals(ModItems.AegisShield)){
            cir.setReturnValue(false);
            //setReturnValue instantly forces the method to return, and sets the return value
        }
    }
    StatusEffectInstance instance;


    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"))
    protected void getStatusEffectInstance(StatusEffectInstance instance, Entity s, CallbackInfoReturnable<Boolean> cir){
        this.instance = instance;
    }

    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
    at = @At("HEAD"), cancellable = true)
    //this makes the aegis shield give darkness immunity when offhanded
    protected void aegisBlindnessImmunity(StatusEffectInstance sei, Entity s, CallbackInfoReturnable<Boolean> cir){
        if(user instanceof PlayerEntity && user.getOffHandStack().getItem().equals(ModItems.AegisShield) &&
                sei.getEffectType().equals(StatusEffects.DARKNESS)){
            cir.setReturnValue(false);
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
        if(nagaWaitTicks > 0){
            nagaWaitTicks--;
        }
        if (tyrfingTicks > 0) {
            tyrfingTicks--;
        }
        if (tyrfingDamageTicks > 0) {
            tyrfingDamageTicks--;
        }
        if(tyrfingDamageTicks == 0 && target instanceof PlayerEntity){
            target.getAttributeInstance(GENERIC_ATTACK_DAMAGE).removeModifier(tyrfingDamage);
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

        /*if(nagaWaitTicks == 0 && isHitByNaga == true){
            isHitByNaga = false;
            setNagaActivation(true);
            Vec3d pos = target.getPos();
            int radius = 5; //radius of naga AoE
            LivingEntity wielder = user;
            List<Entity> nearbyEntities = target.getWorld().getOtherEntities(target, Box.from(pos).expand(100));
            for (Entity nearbyEntity : nearbyEntities) {
                Iterable<ItemStack> itemStacks = nearbyEntity.getItemsEquipped();
                for (ItemStack i : itemStacks) {
                    if (ItemStack.areEqual(i, ModItems.NagaTome.getDefaultStack())) {
                        wielder = (LivingEntity) nearbyEntity;
                    }
                }
            }
            target.getWorld().getOtherEntities(wielder, Box.from(pos).expand(radius), (entity) ->
                    pos.squaredDistanceTo(entity.getPos()) < Math.pow(radius, 2)).forEach(
                    (entity) -> entity.damage(DamageSource.MAGIC, 20)
            );
            HeroesRelics.LOGGER.info("Finished waiting 30 naga ticks");
        } //filler comment to let me push*/
    }


    public int boostedFireTicks = 0;
    public int boostedWitherTicks = 0;

    public int shieldSelfDamageTicks = 0;
    //used for the selfdamage when holding aegis shield + a relic weapon

    public int nagaWaitTicks = 0;
    //set ticks to wait before naga tome AoE
    public boolean isHitByNaga = false;

    public boolean nagaActivation = false;

    public int tyrfingTicks;
    public int tyrfingDamageTicks;
    public void setBoostedFireTicks(int i){
        boostedFireTicks = i;

        //testing
        //HeroesRelics.LOGGER.info(String.valueOf(boostedFireTicks));
    }
    public void setBoostedWitherTicks(int i){
        boostedWitherTicks = i;
    }

    public void setNagaWaitTicks(int i){
        nagaWaitTicks = i;
    }

    public int getNagaWaitTicks(){
        return nagaWaitTicks;
    }
    public void setHitByNaga(boolean b){
        isHitByNaga = b;
    }

    public boolean getHitByNaga(){
        return isHitByNaga;
    }

    public void setNagaActivation(boolean b){
        nagaActivation = b;
    }

    public boolean getNagaActivation(){
        return nagaActivation;
    }

    public void setTyrfingTicks(int i){
        tyrfingTicks = i;
    }
    public void setTyrfingDamageTicks(int i){
        tyrfingDamageTicks = i;
    }
}
