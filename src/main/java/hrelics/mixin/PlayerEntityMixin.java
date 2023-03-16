package hrelics.mixin;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import hrelics.item.ModItems;
import hrelics.item.custom.PlayerEntityInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityInterface {
    //atrocity
    @Unique
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

    //foudroyant strike
    @Unique
    int lightningHits = 0;
    public void setLightningHits(int a){
        lightningHits = a;
    }

    public int getLightningHits(){
        return lightningHits;
    }

    public void decrementLightningHits(){
        lightningHits--;
    }

    //burning quake
    @Unique
    int fireHits = 0;
    public void setFireHits(int a){
        fireHits = a;
    }
    public int getFireHits(){
        return fireHits;
    }

    public void decrementFireHits(){
        fireHits--;
    }

    //fallen star
    @Unique
    int fallenStarHits = 0;
    public void setFallenStarHits(int a){
        fallenStarHits = a;
    }
    public int getFallenStarHits(){
        return fallenStarHits;
    }

    public void decrementFallenStarHits(){
        fallenStarHits--;
    }

    //ruined sky
    @Unique
    int ruinedSkyHits = 0;
    public void setRuinedSkyHits(int a){
        ruinedSkyHits = a;
    }
    public int getRuinedSkyHits(){
        return ruinedSkyHits;
    }

    public void decrementRuinedSkyHits(){
        ruinedSkyHits--;
    }

    //beast fang
    @Unique
    int witherHits = 0;
    public void setWitherHits(int a) {
        witherHits = a;
    }

    public int getWitherHits(){
        return witherHits;
    }

    public void decrementWitherHits(){
        witherHits--;
    }


    Entity t;
    PlayerEntity user = (PlayerEntity) (Object) this;

    @Inject(method = "attack", at = @At("HEAD"))
    protected void getTarget(Entity target, CallbackInfo cir){
        this.t = target;
    }

    @Inject(method="attack", at = @At("HEAD"))
    protected void aegisShieldSlow(Entity target, CallbackInfo cir){
        LivingEntity targetEntity = (LivingEntity) target;
        if(user.getOffHandStack().isOf(ModItems.AegisShield) && targetEntity instanceof WardenEntity){
            targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 0), user);
        }
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private float modifyDamage(float f){
        //atrocity
        if(user.getMainHandStack().isOf(ModItems.Areadbhar) && ((PlayerEntityInterface) user).getAtrocityHits() > 0){
            f += 15;
            ((PlayerEntityInterface) user).decrementAtrocityHits();
            if(t instanceof EnderDragonPart || t instanceof WitherEntity){
                f =- 10;
            }
        }
        else if(user.getMainHandStack().isOf(ModItems.LanceOfRuin) && ((PlayerEntityInterface) user).getRuinedSkyHits() > 0){
            if(t instanceof BlazeEntity || t instanceof VexEntity || t instanceof PhantomEntity){
                f += 10;
                ((PlayerEntityInterface) user).decrementRuinedSkyHits();
            }
            else if(t instanceof EnderDragonPart){
                f += 25;
                ((PlayerEntityInterface) user).decrementRuinedSkyHits();
            }
            else{
                f += 5;
                ((PlayerEntityInterface) user).decrementRuinedSkyHits();
            }
        }
        else if(user.getMainHandStack().isOf(ModItems.Blutgang) && ((PlayerEntityInterface) user).getWitherHits() > 0){
            f += 20;
        }
        //HeroesRelics.LOGGER.info("{}", f);

        return f;
    }

    EntityAttributeModifier CreatorSwordPermanentReach = new EntityAttributeModifier("cswordreach",
            1.25, EntityAttributeModifier.Operation.ADDITION);
    EntityAttributeModifier CreatorSwordPermanentAttackRange = new EntityAttributeModifier("cswordrange",
            1.25, EntityAttributeModifier.Operation.ADDITION);

    //double playeratkrange = user.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).getValue();
    //double playerreach = user.getAttributeInstance(ReachEntityAttributes.REACH).getValue();

    int PaviseAegisTicks = 0;

    public int getPaviseAegisTicks(){
        return PaviseAegisTicks;
    }

    public void setPaviseAegisTicks(int a){
        PaviseAegisTicks = a;
    }



    @Inject(method = "tick", at = @At("HEAD"))
    protected void tickModifiers(CallbackInfo ci){
        //whole next section is for creatorsword permanent attributes
        if(user.getMainHandStack().isOf(ModItems.CreatorSword) &&
                !user.getAttributeInstance(ReachEntityAttributes.REACH).hasModifier(CreatorSwordPermanentReach)){
            //adds reach+attack range if holding csword
            user.getAttributeInstance(ReachEntityAttributes.REACH).addTemporaryModifier(CreatorSwordPermanentReach);
            user.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).addTemporaryModifier(CreatorSwordPermanentAttackRange);
            //testing
            //HeroesRelics.LOGGER.info("applied reach attributes");
            //HeroesRelics.LOGGER.info("{}", playerreach);
            //HeroesRelics.LOGGER.info("{}", playeratkrange);
        }
        //removes both modifiers if sword is not held
        else if(!user.getMainHandStack().isOf(ModItems.CreatorSword)) {
            user.getAttributeInstance(ReachEntityAttributes.REACH).removeModifier(CreatorSwordPermanentReach);
            user.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).removeModifier(CreatorSwordPermanentAttackRange);
        }
        if(PaviseAegisTicks > 0){
            PaviseAegisTicks--;
        }
    }
}
