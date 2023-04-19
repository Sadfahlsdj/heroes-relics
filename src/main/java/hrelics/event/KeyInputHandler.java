package hrelics.event;

import hrelics.HeroesRelics;
import hrelics.configs.ModConfigs;
import hrelics.item.ModItems;
import hrelics.networking.ModMessages;
import hrelics.networking.packet.AtrocityC2SPacket;
import hrelics.sound.ModSounds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

import static hrelics.networking.ModMessages.*;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_HEROESRELICS = "key.category.hrelics.heroesrelics";
    public static final String KEY_COMBATART = "key.hrelics.combatart";

    public static KeyBinding combatArt;

    static Random rand = new Random();

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PlayerEntity p = client.player;
            if(combatArt.wasPressed()){
               if(p.getMainHandStack().isOf(ModItems.Areadbhar)){
                   HeroesRelics.LOGGER.info(String.valueOf(ModConfigs.VOICE_LINES_ON));
                   ClientPlayNetworking.send(ATROCITY, PacketByteBufs.create());
                   if(ModConfigs.VOICE_LINES_ON) {
                       int lastAtrocityRNG = 999;
                       int atrocityRNG = rand.nextInt(4);
                       if (lastAtrocityRNG == atrocityRNG) {
                           atrocityRNG = (atrocityRNG + 1) % 4;
                       }
                       lastAtrocityRNG = atrocityRNG;
                       World w = p.getWorld();
                       if (atrocityRNG == 0) {
                           w.playSoundAtBlockCenter(p.getBlockPos(), ModSounds.ATROCITY1, SoundCategory.PLAYERS, 1f, 1f, true);
                       } else if (atrocityRNG == 1) {
                           w.playSoundAtBlockCenter(p.getBlockPos(), ModSounds.ATROCITY2, SoundCategory.PLAYERS, 1f, 1f, true);
                       } else if (atrocityRNG == 2) {
                           w.playSoundAtBlockCenter(p.getBlockPos(), ModSounds.ATROCITY3, SoundCategory.PLAYERS, 1f, 1f, true);
                       } else if (atrocityRNG == 3) {
                           w.playSoundAtBlockCenter(p.getBlockPos(), ModSounds.ATROCITY4, SoundCategory.PLAYERS, 1f, 1f, true);
                       }
                   }
               }
               else if(p.getMainHandStack().isOf(ModItems.Thunderbrand)){
                   ClientPlayNetworking.send(FOUDROYANT, PacketByteBufs.create());
               }
               else if(p.getMainHandStack().isOf(ModItems.Luin)){
                   ClientPlayNetworking.send(BURNINGQUAKE, PacketByteBufs.create());
               }
               else if(p.getMainHandStack().isOf(ModItems.CreatorSword)){
                   ClientPlayNetworking.send(RUPTUREDHEAVEN, PacketByteBufs.create());
               }
               else if(p.getMainHandStack().isOf(ModItems.Failnaught)){
                   ClientPlayNetworking.send(FALLENSTAR, PacketByteBufs.create());
               }
               else if(p.getMainHandStack().isOf(ModItems.LanceOfRuin)){
                   ClientPlayNetworking.send(RUINEDSKY, PacketByteBufs.create());
               }
               else if(p.getMainHandStack().isOf(ModItems.Blutgang)){
                   ClientPlayNetworking.send(BEASTFANG, PacketByteBufs.create());
               }
               else if(p.getOffHandStack().isOf(ModItems.AegisShield)){
                   ClientPlayNetworking.send(PAVISEAEGIS, PacketByteBufs.create());
               }
               else if(p.getOffHandStack().isOf(ModItems.AncientArmory)){
                   ClientPlayNetworking.send(ANCIENTARMORY, PacketByteBufs.create());
               }
            }
        });
    }

    public static void register(){
        combatArt = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_COMBATART, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_HEROESRELICS
        ));

        registerKeyInputs();
    }
}
