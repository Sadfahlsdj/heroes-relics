package hrelics.networking.packet;

import hrelics.item.custom.PlayerEntityInterface;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RuinedSkyC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){

        //damages player more heavily if they're within 750 blocks of 0,0,0 in end(on main island essentially)
        if(player.getWorld().getRegistryKey() == World.END &&
                player.getPos().squaredDistanceTo(Vec3d.ZERO) < 750 * 750){
            player.damage(DamageSource.OUT_OF_WORLD, 7);
        }
        else{
            player.damage(DamageSource.OUT_OF_WORLD, 3);
        }



        ((PlayerEntityInterface) player).setRuinedSkyHits(1);


    }
}
