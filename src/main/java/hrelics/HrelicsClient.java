package hrelics;

import hrelics.event.KeyInputHandler;
import hrelics.networking.ModMessages;
import hrelics.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class HrelicsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();

        KeyInputHandler.register();
        ModModelPredicateProvider.registerModModels();
    }
}
