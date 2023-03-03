package hrelics.networking.packet;

import hrelics.item.custom.PlayerEntityInterface;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class FoudroyantC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){

        player.damage(DamageSource.OUT_OF_WORLD, 4);

        ((PlayerEntityInterface) player).setLightningHits(2);
    }
}
