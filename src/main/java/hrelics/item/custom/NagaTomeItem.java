package hrelics.item.custom;

import hrelics.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class NagaTomeItem extends Item {
    public NagaTomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            world.playSoundAtBlockCenter(user.getBlockPos(), ModSounds.NAGAACTIVATION, SoundCategory.PLAYERS, 1f, 1f, true);
            NagaProjectileEntity nagaEntity = new NagaProjectileEntity(world, user);
            //snowballEntity.setItem(itemStack);
            nagaEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            nagaEntity.setNoGravity(true);
            world.spawnEntity(nagaEntity);
            user.getItemCooldownManager().set(this, 120);

        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
