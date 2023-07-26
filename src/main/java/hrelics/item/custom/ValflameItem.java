package hrelics.item.custom;

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

import java.util.List;
import java.util.Random;

import static hrelics.networking.ModMessages.FORSETIPARTICLE;

public class ValflameItem extends Item {
    public ValflameItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        //user.getItemCooldownManager().set(this, 100);

        return TypedActionResult.success(itemStack, world.isClient());

    }
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        //increment valflame ticks, which will damage user if have been holding for >5 seconds
        if(user instanceof PlayerEntity){
            ((PlayerEntityInterface) user).incrementValflameTicks();
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 200;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        //spawn projectile

        int useTime = (200 - remainingUseTicks) / 20; //in seconds
        if (!world.isClient && user instanceof PlayerEntity) {
            ((PlayerEntityInterface) user).setValflameTicks(0);

            ValflameProjectileEntity valEntity = new ValflameProjectileEntity(world, user);
            ((ThrownItemEntityInterface) valEntity).setValflameUseTime(useTime);
            //snowballEntity.setItem(itemStack);
            valEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, (1.5F + (float)useTime / 3.0F), 1.0F);
            valEntity.setNoGravity(true);
            world.spawnEntity(valEntity);

            ((PlayerEntity) user).getItemCooldownManager().set(this, 100);
        }
    }
}
