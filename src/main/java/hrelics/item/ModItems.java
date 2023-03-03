package hrelics.item;

import hrelics.HeroesRelics;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item Areadbhar = registerItem("areadbhar", new SwordItem(ModToolMaterials.UMBRAL_STEEL,
            7, 1.3f, new FabricItemSettings().maxDamage(0).group(ModItemGroup.RELICWEAPON)));

    public static final Item Thunderbrand = registerItem("thunderbrand", new SwordItem(ModToolMaterials.UMBRAL_STEEL,
            4, 1.6f, new FabricItemSettings().maxDamage(0).group(ModItemGroup.RELICWEAPON)));

    //important

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(HeroesRelics.MOD_ID, name), item);
    }

    public static void registerModItems(){
        HeroesRelics.LOGGER.info("Registering items for" + HeroesRelics.MOD_ID);
    }
}
