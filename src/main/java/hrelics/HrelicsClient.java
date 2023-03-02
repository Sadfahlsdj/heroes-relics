package hrelics;

import hrelics.event.KeyInputHandler;
import hrelics.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class HrelicsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();

        KeyInputHandler.register();
    }
}
