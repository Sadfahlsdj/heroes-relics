package hrelics.mixin;

import hrelics.HeroesRelics;
import hrelics.item.custom.ServerWorldInterface;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Random;

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
        if(nbt.contains("valflame_crypt_chest")) {
            this.nb = String.valueOf(nbt.getString("valflame_crypt_chest"));
            //this.p = ChestBlockEntity.posFromNbt(nbt);
            //HeroesRelics.LOGGER.info("nbt {} is being read", this.nb);
        }
        if(nbt.contains("naga_crypt_chest")) {
            this.nb = String.valueOf(nbt.getString("naga_crypt_chest"));
            //this.p = ChestBlockEntity.posFromNbt(nbt);
            //HeroesRelics.LOGGER.info("nbt {} is being read", this.nb);
        }
    }
    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void cryptChestsWrite(NbtCompound nbt, CallbackInfo ci){
        nbt.putString("forseti_crypt_chest", nb);
        nbt.putString("valflame_crypt_chest", nb);
        nbt.putString("naga_crypt_chest", nb);
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



            World w = player.getWorld();
            for(TntEntity t : tntEntities) {
                if (!w.isClient()) {
                    w.spawnEntity(t);
                }
            }
        }
        else if (this.nb.equals("naga_crypt_chest")){
            World w = player.getWorld();

            /*LightningEntity l1 = new LightningEntity(EntityType.LIGHTNING_BOLT, w);
            LightningEntity l2 = new LightningEntity(EntityType.LIGHTNING_BOLT, w);
            LightningEntity l3 = new LightningEntity(EntityType.LIGHTNING_BOLT, w);
            LightningEntity l4 = new LightningEntity(EntityType.LIGHTNING_BOLT, w);
            l1.setPos(x + 2, y, z);
            l2.setPos(x - 2, y, z);
            l3.setPos(x, y, z + 2);
            l3.setPos(x, y, z - 2);
            if (!w.isClient()){
                w.spawnEntity(l1);
                w.spawnEntity(l2);
                w.spawnEntity(l3);
                w.spawnEntity(l4);
            }*/

            Random r = new Random();

            // summons 10 lightning strikes each 0.4 seconds apart
            for(int i = 0; i < 80; i += 8) {
                int xmod = r.nextInt(8) - 4;
                int zmod = r.nextInt(8) - 4;
                Vec3d pos = new Vec3d(x + xmod, y, z + zmod);

                ((ServerWorldInterface) (ServerWorld) w).scheduleNagaLightning((long) i, pos, w);
            }
        }
    }
}
