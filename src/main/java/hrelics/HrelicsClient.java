package hrelics;

import hrelics.event.KeyInputHandler;
import hrelics.item.ModItemGroup;
import hrelics.item.ModItems;
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

        //registerModItemGroup needs to be registered first
        ModItemGroup.registerModItemGroup();
        ModItems.registerModItems();
    }
}
