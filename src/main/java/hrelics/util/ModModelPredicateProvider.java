package hrelics.util;

import com.google.common.collect.Maps;
import hrelics.item.ModItems;
import hrelics.item.custom.LivingEntityInterface;
import hrelics.item.custom.PlayerEntityInterface;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModModelPredicateProvider {
    public static void registerModModels(){
        //failnaught
        registerBow(ModItems.Failnaught);
        //aegis shield
        ModelPredicateProviderRegistry.register(ModItems.AegisShield, new Identifier("blocking"), (stack, world, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;
        });
        //tyrfing - currently not working
        ModelPredicateProviderRegistry.register(ModItems.Tyrfing, new Identifier("parried"), (stack, world, entity, seed) -> {
            if(entity != null && ((LivingEntityInterface) entity).getTyrfingHits() > 0 && entity.getActiveItem() == stack){
                return 1;
            }
            else{
                return 0;
            }
        });

    }

    private static void registerBow(Item bow) {
        FabricModelPredicateProviderRegistry.register(bow, new Identifier("pull"),
                (stack, world, entity, seed) -> {
                    if (entity == null) {
                        return 0.0f;
                    }
                    if (entity.getActiveItem() != stack) {
                        return 0.0f;
                    }
                    return (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
                });

        FabricModelPredicateProviderRegistry.register(bow, new Identifier("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem()
                        && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }

    private static final Map<Item, Map<Identifier, ModelPredicateProvider>> ITEM_SPECIFIC = Maps.newHashMap();
    private static final Map<Identifier, ModelPredicateProvider> GLOBAL = Maps.newHashMap();



}
