package hrelics.mixin;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import hrelics.HeroesRelics;
import hrelics.item.ModItems;
import hrelics.item.custom.PlayerEntityInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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

    Entity t;
    PlayerEntity user = (PlayerEntity) (Object) this;

    @Inject(method = "attack", at = @At("HEAD"))
    protected void getTarget(Entity target, CallbackInfo cir){
        this.t = target;
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private float modifyDamage(float f){

        if(user.getMainHandStack().isOf(ModItems.Areadbhar) && ((PlayerEntityInterface) user).getAtrocityHits() > 0){
            f += 15;
            ((PlayerEntityInterface) user).decrementAtrocityHits();
        }

        return f;
    }

    EntityAttributeModifier CreatorSwordPermanentReach = new EntityAttributeModifier("cswordreach",
            1.25, EntityAttributeModifier.Operation.ADDITION);
    EntityAttributeModifier CreatorSwordPermanentAttackRange = new EntityAttributeModifier("cswordrange",
            1.25, EntityAttributeModifier.Operation.ADDITION);

    //double playeratkrange = user.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).getValue();
    //double playerreach = user.getAttributeInstance(ReachEntityAttributes.REACH).getValue();



    @Inject(method = "tick", at = @At("HEAD"))
    protected void applyCSwordRange(CallbackInfo ci){
        if(user.getMainHandStack().isOf(ModItems.CreatorSword) &&
                !user.getAttributeInstance(ReachEntityAttributes.REACH).hasModifier(CreatorSwordPermanentReach)){
            //adds reach+attack range if holding csword
            user.getAttributeInstance(ReachEntityAttributes.REACH).addTemporaryModifier(CreatorSwordPermanentReach);
            user.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).addTemporaryModifier(CreatorSwordPermanentAttackRange);
            //testing
            HeroesRelics.LOGGER.info("applied reach attributes");
            //HeroesRelics.LOGGER.info("{}", playerreach);
            //HeroesRelics.LOGGER.info("{}", playeratkrange);
        }
        //removes both modifiers if sword is not held
        else if(!user.getMainHandStack().isOf(ModItems.CreatorSword)) {
            user.getAttributeInstance(ReachEntityAttributes.REACH).removeModifier(CreatorSwordPermanentReach);
            user.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).removeModifier(CreatorSwordPermanentAttackRange);
        }
    }
}
