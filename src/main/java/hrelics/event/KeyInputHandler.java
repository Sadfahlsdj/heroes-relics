package hrelics.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_HEROESRELICS = "key.category.hrelics.heroesrelics";
    public static final String KEY_COMBATART = "key.hrelics.combatart";

    public static KeyBinding combatArt;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(combatArt.wasPressed()){
                //combat art packets go under here
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
