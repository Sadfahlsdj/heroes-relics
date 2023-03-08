package hrelics.networking;

import hrelics.HeroesRelics;
import hrelics.networking.packet.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ATROCITY = new Identifier(HeroesRelics.MOD_ID, "atrocity");
    public static final Identifier FOUDROYANT = new Identifier(HeroesRelics.MOD_ID, "foudroyant");

    public static final Identifier BURNINGQUAKE = new Identifier(HeroesRelics.MOD_ID, "burningquake");

    public static final Identifier RUPTUREDHEAVEN = new Identifier(HeroesRelics.MOD_ID, "rupturedheaven");

    public static final Identifier FALLENSTAR = new Identifier(HeroesRelics.MOD_ID, "fallenstar");

    public static final Identifier RUINEDSKY = new Identifier(HeroesRelics.MOD_ID, "ruinedsky");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(ATROCITY, AtrocityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FOUDROYANT, FoudroyantC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(BURNINGQUAKE, BurningQuakeC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(RUPTUREDHEAVEN, RupturedHeavenC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FALLENSTAR, FallenStarC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(RUINEDSKY, RuinedSkyC2SPacket::receive);
    }

    public static void registerS2CPackets(){

    }
}
