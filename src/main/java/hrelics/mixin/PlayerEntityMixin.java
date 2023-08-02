package hrelics.mixin;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import hrelics.HeroesRelics;
import hrelics.item.ModItems;
import hrelics.item.custom.LivingEntityInterface;
import hrelics.item.custom.PlayerEntityInterface;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static hrelics.networking.ModMessages.*;
import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_ATTACK_DAMAGE;

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

    @Unique
    int valflameSelfDamageTicks = 0;

    int tyrfingDamageTicks = 0;
    public int tyrfingTicks;
    public int getValflameTicks(){
        return valflameSelfDamageTicks;
    }
    public void setValflameTicks(int a){
        valflameSelfDamageTicks = a;
    }

    public void incrementValflameTicks(){
        valflameSelfDamageTicks++;
    }

    public void setTyrfingDamageTicks(int i){
        tyrfingDamageTicks = i;
    }
    public int getTyrfingDamageTicks(){
        return tyrfingDamageTicks;
    }

    public void setTyrfingTicks(int i){
        tyrfingTicks = i;
    }


    Entity t;
    PlayerEntity user = (PlayerEntity) (Object) this;
    PlayerEntity target = (PlayerEntity) (Object) this;

    DamageSource source;
    EntityAttributeModifier tyrfingDamage = new EntityAttributeModifier("tyrfingdamage",
            1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    @Inject(method = "attack", at = @At("HEAD"))
    protected void getTarget(Entity target, CallbackInfo cir){
        this.t = target;
    }

    @Inject(method = "damage", at = @At("HEAD"))
    protected void getSource(DamageSource source, float f, CallbackInfoReturnable cir){
        this.source = source;
    }

    @Inject(method="attack", at = @At("HEAD"))
    protected void aegisShieldSlow(Entity target, CallbackInfo cir){
        LivingEntity targetEntity = (LivingEntity) target;
        if(user.getOffHandStack().isOf(ModItems.AegisShield) && targetEntity instanceof WardenEntity){
            targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 0), user);
        }
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"))
    public float damageModifications(float f){
        if(target instanceof PlayerEntity && tyrfingTicks > 0 && !source.isOutOfWorld()){
            f = 0;
            target.heal(3);

            //attribute for damage increase

            target.getAttributeInstance(GENERIC_ATTACK_DAMAGE).removeModifier(tyrfingDamage);
            target.getAttributeInstance(GENERIC_ATTACK_DAMAGE).addTemporaryModifier(tyrfingDamage);
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 0), target);
            setTyrfingDamageTicks(100);
            setTyrfingAwakened(true);

            PacketByteBuf TyrfingTicksPacket = PacketByteBufs.create();
            //TyrfingTicksPacket.writeText(target.getName());
            //TyrfingTicksPacket.write
            TyrfingTicksPacket.writeUuid(target.getUuid());
            //TyrfingTicksPacket.writeDouble(target.getX());
            //TyrfingTicksPacket.writeDouble(target.getY());
            //TyrfingTicksPacket.writeDouble(target.getZ());
            ServerPlayNetworking.send((ServerPlayerEntity) (PlayerEntity) target, TYRFINGTICKS, TyrfingTicksPacket);
            System.out.println("correct player was found: " + ((PlayerEntity) target).equals(target.getWorld().getClosestPlayer(target.getX(), target.getY(), target.getZ(), 10, false)));

        }
        return f;
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
        else if(user.getMainHandStack().isOf(ModItems.Blutgang) && ((PlayerEntityInterface) user).getWitherHits() > 0
        && t instanceof WitherEntity){
            f += 20;
            //decrementing happens in the BlutgangItem postHit
        }
        //HeroesRelics.LOGGER.info("{}", f);
        //HeroesRelics.LOGGER.info("tyrfing boosted damage ticks:" + ((LivingEntityInterface) user).getTyrfingDamageTicks());
        /*if(user.getWorld() instanceof ClientWorld){
            HeroesRelics.LOGGER.info("tyrfing predicate thing from playerentity clientworld:" + (user != null && ((LivingEntityInterface) user).getTyrfingDamageTicks() > 0));
            HeroesRelics.LOGGER.info("clientworld tyrfing ticks:" + ((LivingEntityInterface) user).getTyrfingDamageTicks());
        }
        if(user.getWorld() instanceof ServerWorld){
            HeroesRelics.LOGGER.info("tyrfing predicate thing from playerentity serverworld:" + (user != null && ((LivingEntityInterface) user).getTyrfingDamageTicks() > 0));
            HeroesRelics.LOGGER.info("serverworld tyrfing ticks:" + ((LivingEntityInterface) user).getTyrfingDamageTicks());
        }*/
        //HeroesRelics.LOGGER.info("tyrfing predicate thing from playerentity:" + (user != null && ((LivingEntityInterface) user).getTyrfingHits() > 0));
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

        //tyrfing tick stuff
        if (tyrfingTicks > 0) {
            tyrfingTicks--;
        }
        if (tyrfingDamageTicks > 0) {
            tyrfingDamageTicks--;
            HeroesRelics.LOGGER.info("decremented tyrfingDamageTick, value is now" + getTyrfingDamageTicks());
        }


        if(tyrfingDamageTicks == 0){
            target.getAttributeInstance(GENERIC_ATTACK_DAMAGE).removeModifier(tyrfingDamage);
        }

        //valflame self damage
        if(valflameSelfDamageTicks > 100 && valflameSelfDamageTicks % 20 == 0){
            user.damage(DamageSource.OUT_OF_WORLD, 1);
        }

        //valflame particles
        if(valflameSelfDamageTicks > 0){
            int x = user.getBlockPos().getX();
            int y = user.getBlockPos().getY();
            int z = user.getBlockPos().getZ();
            Random random = new Random();
            double d = 2 * random.nextDouble(-1, 1);

            PacketByteBuf ValflameParticlePacket = PacketByteBufs.create();
            ValflameParticlePacket.writeDouble(x + d);
            ValflameParticlePacket.writeDouble(y);
            ValflameParticlePacket.writeDouble(z + d);
            if(valflameSelfDamageTicks > 100){
                ValflameParticlePacket.writeBoolean(true);
            }
            else{
                ValflameParticlePacket.writeBoolean(false);
            }

            if(user.getWorld() instanceof ServerWorld){
                ServerPlayNetworking.send((ServerPlayerEntity) user, VALFLAMEPARTICLE, ValflameParticlePacket);
            }

        }
    }
}
