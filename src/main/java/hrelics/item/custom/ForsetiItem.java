package hrelics.item.custom;

import hrelics.HeroesRelics;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hrelics.networking.ModMessages.FORSETIPARTICLE;
import static hrelics.networking.ModMessages.NAGAPARTICLE;

public class ForsetiItem extends Item {
    public ForsetiItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        return TypedActionResult.success(itemStack, world.isClient());
    }
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 2), user);
        //HeroesRelics.LOGGER.info("forseti is being used");

        int forsetiRange = 15; //range of forseti; change for balance purposes
        Vec3d pos = user.getPos().add(user.getRotationVector().multiply(forsetiRange));

        //damaging logic is below
        List<Entity> entityList = world.getOtherEntities(user, Box.from(pos).expand(forsetiRange),
                (e) -> e.getPos().squaredDistanceTo(pos) <= Math.pow(forsetiRange, 2));

        for(Entity a : entityList){
            if(a.getPos().squaredDistanceTo(user.getPos()) < Math.pow(forsetiRange, 2)){
                a.damage(DamageSource.MAGIC, 4);
            }
        }

        PacketByteBuf ForsetiParticlePacket = PacketByteBufs.create();
        Random random = new Random();
        int coordModifier = random.nextInt(-5, 5);
        int velocityModifier = Math.abs(coordModifier);
        double x = user.getPos().add(user.getRotationVector().multiply((double) coordModifier)).getX();
        double y = user.getPos().getY();
        double z = user.getPos().add(user.getRotationVector().multiply((double) coordModifier)).getZ();
        double xV = user.getRotationVector().multiply((double) (velocityModifier)).getX();
        double zV = user.getRotationVector().multiply((double) (velocityModifier)).getZ(); //xV znd zV are x velocity and z velocity respectively

        ForsetiParticlePacket.writeDouble(x);
        ForsetiParticlePacket.writeDouble(y);
        ForsetiParticlePacket.writeDouble(z);
        ForsetiParticlePacket.writeDouble(xV);
        ForsetiParticlePacket.writeDouble(zV);

        if(user.getWorld() instanceof ServerWorld){
            ServerPlayNetworking.send((ServerPlayerEntity) user, FORSETIPARTICLE, ForsetiParticlePacket);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30, 2), user);
        //HeroesRelics.LOGGER.info("forseti is done being used");
    }
}
