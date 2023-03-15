package hrelics.networking.packet;

import hrelics.item.custom.PlayerEntityInterface;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PaviseAegisC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(((PlayerEntityInterface) player).getPaviseAegisTicks() == 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 2), player);
            ((PlayerEntityInterface) player).setPaviseAegisTicks(260);
        }
        else{
            double cooldownSeconds = (double)(((PlayerEntityInterface) player).getPaviseAegisTicks()) / 20.0;
            player.sendMessage(Text.of("Pavise/Aegis is on cooldown for " + cooldownSeconds + " seconds!"
                    ), true);
        }
    }
}
