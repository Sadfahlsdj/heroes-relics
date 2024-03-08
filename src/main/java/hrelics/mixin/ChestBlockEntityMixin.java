package hrelics.mixin;

import hrelics.HeroesRelics;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin {

    @Unique NbtCompound n;
    @Unique String nb = "";
    @Inject(method = "readNbt", at = @At("HEAD"))
    public void cryptChestsRead(NbtCompound nbt, CallbackInfo ci){
        // HeroesRelics.LOGGER.info("nbt is being read");
        if(nbt.contains("forseti_crypt_chest")) {
            this.nb = String.valueOf(nbt.getString("forseti_crypt_chest"));
            HeroesRelics.LOGGER.info("nbt {} is being read", this.nb);
        }
    }
    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void cryptChestsWrite(NbtCompound nbt, CallbackInfo ci){
        nbt.putString("forseti_crypt_chest", nb);
        HeroesRelics.LOGGER.info("nbt {} is being written", this.nb);
    }

    @Inject(method = "onOpen", at = @At("HEAD"))
    public void chestLogic(PlayerEntity player, CallbackInfo ci){
        if (this.nb.equals("forseti_crypt_chest")) {
            HeroesRelics.LOGGER.info("if you see this message your dogshit worked");
            TntEntity tntEntity = new TntEntity(EntityType.TNT, player.getWorld());
            tntEntity.setPos(player.getX(), player.getY(), player.getZ());

            /*
            tnt relative positions to the chest:
            y-2
            y-1, x and z +- 1
            y + 1, x and z +- 2, 3, 4
            y + 2, x and z +- 2, 3, 4
            */

            World w = player.getWorld();
            w.spawnEntity(tntEntity);
        }
    }
}
