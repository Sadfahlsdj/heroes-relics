package hrelics.item.custom;

import hrelics.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WarpStaffItem extends Item {

    public WarpStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockPos playerPosition = user.getBlockPos();
        ItemStack itemStack = user.getStackInHand(hand);

        int teleportLength = 10; //length of teleport; change for balancing purposes
        Vec3d pos = user.getPos().add(user.getRotationVector().multiply(teleportLength));

        //user.teleport(pos.getX(), pos.getY(), pos.getZ(), false); this one teleports to the block underneath the intended position
        user.teleport(pos.getX(), pos.getY(), pos.getZ());
        user.getItemCooldownManager().set(this, 60);

        user.damage(DamageSource.OUT_OF_WORLD, 1);

        world.playSoundAtBlockCenter(user.getBlockPos(), ModSounds.WARP, SoundCategory.PLAYERS, 0.5f, 1f, true);

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
