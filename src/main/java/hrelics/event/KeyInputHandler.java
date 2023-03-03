package hrelics.event;

import hrelics.item.ModItems;
import hrelics.networking.ModMessages;
import hrelics.networking.packet.AtrocityC2SPacket;
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
import org.lwjgl.glfw.GLFW;

import static hrelics.networking.ModMessages.ATROCITY;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_HEROESRELICS = "key.category.hrelics.heroesrelics";
    public static final String KEY_COMBATART = "key.hrelics.combatart";

    public static KeyBinding combatArt;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PlayerEntity p = client.player;
            if(combatArt.wasPressed()){
               if(p.getMainHandStack().isOf(ModItems.Areadbhar)){
                   ClientPlayNetworking.send(ATROCITY, PacketByteBufs.create());
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
