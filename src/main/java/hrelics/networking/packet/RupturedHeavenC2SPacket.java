package hrelics.networking.packet;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import static hrelics.item.custom.CreatorSwordItem.*;
import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_ATTACK_DAMAGE;

public class RupturedHeavenC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){





        if(!player.getAttributeInstance(ReachEntityAttributes.REACH).hasModifier(RupturedHeavenReach) &&
                !player.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).hasModifier(RupturedHeavenAttackRange) &&
                !player.getAttributeInstance(GENERIC_ATTACK_DAMAGE).hasModifier(RupturedHeavenDamage)) {
            player.damage(DamageSource.OUT_OF_WORLD, 4);
            player.getAttributeInstance(ReachEntityAttributes.REACH).addTemporaryModifier(RupturedHeavenReach);
            player.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).addTemporaryModifier(RupturedHeavenAttackRange);
            player.getAttributeInstance(GENERIC_ATTACK_DAMAGE).addTemporaryModifier(RupturedHeavenDamage);
        }
    }
}
