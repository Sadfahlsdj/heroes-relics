package hrelics.item.custom;

import hrelics.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RescueStaffItem extends Item {
    public RescueStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        if(user.getPos().squaredDistanceTo(entity.getPos()) < 30 * 30){
            Vec3d pos = user.getPos();
            entity.teleport(pos.getX(), pos.getY(), pos.getZ());

            user.getItemCooldownManager().set(this, 200);

            user.damage(DamageSource.OUT_OF_WORLD, 1);

            //pretty sure rescue sfx is same as the warp one so this is legal I guess
            world.playSoundAtBlockCenter(user.getBlockPos(), ModSounds.WARP, SoundCategory.PLAYERS, 0.5f, 1f, true);
        }
        else{
            user.sendMessage(Text.of("Target out of range"), true);
        }
        return ActionResult.PASS;
    }
}
