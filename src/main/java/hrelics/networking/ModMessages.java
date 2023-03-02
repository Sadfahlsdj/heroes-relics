package hrelics.networking;

import hrelics.HeroesRelics;
import hrelics.networking.packet.AtrocityC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ATROCITY = new Identifier(HeroesRelics.MOD_ID, "atrocity");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(ATROCITY, AtrocityC2SPacket::receive);
    }

    public static void registerS2CPackets(){

    }
}
