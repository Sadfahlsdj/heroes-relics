package hrelics.networking;

import hrelics.HeroesRelics;
import hrelics.networking.packet.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ATROCITY = new Identifier(HeroesRelics.MOD_ID, "atrocity");
    public static final Identifier FOUDROYANT = new Identifier(HeroesRelics.MOD_ID, "foudroyant");

    public static final Identifier BURNINGQUAKE = new Identifier(HeroesRelics.MOD_ID, "burningquake");

    public static final Identifier RUPTUREDHEAVEN = new Identifier(HeroesRelics.MOD_ID, "rupturedheaven");

    public static final Identifier FALLENSTAR = new Identifier(HeroesRelics.MOD_ID, "fallenstar");

    public static final Identifier RUINEDSKY = new Identifier(HeroesRelics.MOD_ID, "ruinedsky");

    public static final Identifier BEASTFANG = new Identifier(HeroesRelics.MOD_ID, "beastfang");

    public static final Identifier PAVISEAEGIS = new Identifier(HeroesRelics.MOD_ID, "paviseaegis");

    public static final Identifier NAGAPARTICLE = new Identifier(HeroesRelics.MOD_ID, "nagaparticle");

    public static final Identifier NAGAHITSOUND = new Identifier(HeroesRelics.MOD_ID, "nagahitsound");

    public static final Identifier FORSETIPARTICLE = new Identifier(HeroesRelics.MOD_ID, "forsetiparticle");




    public static final Identifier ANCIENTARMORY = new Identifier(HeroesRelics.MOD_ID, "ancientarmory");


    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(ATROCITY, AtrocityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FOUDROYANT, FoudroyantC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(BURNINGQUAKE, BurningQuakeC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(RUPTUREDHEAVEN, RupturedHeavenC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FALLENSTAR, FallenStarC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(RUINEDSKY, RuinedSkyC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(BEASTFANG, BeastFangC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PAVISEAEGIS, PaviseAegisC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ANCIENTARMORY, AncientArmoryC2SPacket::receive);
        //ServerPlayNetworking.registerGlobalReceiver(NAGAPARTICLE, AncientArmoryC2SPacket::receive);
    }



    public static void registerS2CPackets(){

    }
}
