package hrelics.mixin;

import hrelics.HeroesRelics;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin {

    @Unique NbtCompound n;
    @Unique String nb = "";
    @Unique BlockPos p = ((ChestBlockEntityAccessor) this).invokeGetPos();
    //check ChestBlockEntityAccessor interface

    @Inject(method = "readNbt", at = @At("HEAD"))
    public void cryptChestsRead(NbtCompound nbt, CallbackInfo ci){
        // HeroesRelics.LOGGER.info("nbt is being read");
        if(nbt.contains("forseti_crypt_chest")) {
            this.nb = String.valueOf(nbt.getString("forseti_crypt_chest"));
            //this.p = ChestBlockEntity.posFromNbt(nbt);
            //HeroesRelics.LOGGER.info("nbt {} is being read", this.nb);
        }
    }
    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void cryptChestsWrite(NbtCompound nbt, CallbackInfo ci){
        nbt.putString("forseti_crypt_chest", nb);
        //this.p = ChestBlockEntity.posFromNbt(nbt);
        // HeroesRelics.LOGGER.info("nbt {} is being written", this.nb);
    }

    @Inject(method = "onOpen", at = @At("HEAD"))
    public void chestLogic(PlayerEntity player, CallbackInfo ci){
        int x = this.p.getX();
        int y = this.p.getY();
        int z = this.p.getZ();

        if (this.nb.equals("forseti_crypt_chest")) {
            ArrayList<TntEntity> tntEntities = new ArrayList<TntEntity>();

            TntEntity tt = new TntEntity(player.getWorld(), x, y - 2, z, player);

            tntEntities.add(tt);
            int[] relativeCoordList1 = {-1, 1};
            int[] relativeCoordList2 = {-2, 2};
            int[] relativeCoordList3 = {2, 3, 4, -2, -3, -4};
            int[] yRelativeCoordList = {-1, -2};
            for(int xtemp : relativeCoordList1){
                for(int ztemp : relativeCoordList1) {
                    TntEntity t = new TntEntity(player.getWorld(), x + xtemp, y - 1, z + ztemp,
                            player);
                    tntEntities.add(t);
                }
            }

            /*for(int xtemp : relativeCoordList2){
                for(int ztemp : relativeCoordList3) {
                    int ytemp = -1;
                    TntEntity t = new TntEntity(player.getWorld(), x + xtemp, y + ytemp, z + ztemp,
                            player);
                    tntEntities.add(t);
                }
            }

            for(int xtemp : relativeCoordList3){
                for(int ztemp : relativeCoordList2) {
                    int ytemp = -1;
                    TntEntity t = new TntEntity(player.getWorld(), x + xtemp, y + ytemp, z + ztemp,
                            player);
                    tntEntities.add(t);
                }
            }*/
            TntEntity t1 = new TntEntity(player.getWorld(), x + 2, y - 1, z + 2,
                    player);
            TntEntity t2 = new TntEntity(player.getWorld(), x + 2, y - 1, z - 2,
                    player);
            TntEntity t3 = new TntEntity(player.getWorld(), x - 2, y - 1, z + 2,
                    player);
            TntEntity t4 = new TntEntity(player.getWorld(), x - 2, y - 1, z - 2,
                    player);
            tntEntities.add(t1);
            tntEntities.add(t2);
            tntEntities.add(t3);
            tntEntities.add(t4);



            /*
            tnt relative positions to the chest:
            y-2
            y-1, x and z +- 1
            y + 1, x and z +- 2, 3, 4
            y + 2, x and z +- 2, 3, 4
            */

            World w = player.getWorld();
            for(TntEntity t : tntEntities) {
                if (!w.isClient()) {
                    w.spawnEntity(t);
                    // w.emitGameEvent((Entity) player, GameEvent.PRIME_FUSE, this.p);
                }
            }
            // BlockPos a = this.pos;
            //BlockPos a = (ChestBlockEntity)(Object) this.pos;
        }
    }
}
