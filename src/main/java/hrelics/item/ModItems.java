package hrelics.item;

import hrelics.HeroesRelics;
import hrelics.item.custom.AreadbharItem;
import hrelics.item.custom.CreatorSwordItem;
import hrelics.item.custom.LuinItem;
import hrelics.item.custom.ThunderbrandItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item Areadbhar = registerItem("areadbhar", new AreadbharItem(ModToolMaterials.UMBRAL_STEEL,
            7, 1.3f, new FabricItemSettings().maxDamage(0).group(ModItemGroup.RELICWEAPON)));

    public static final Item Thunderbrand = registerItem("thunderbrand", new ThunderbrandItem(ModToolMaterials.UMBRAL_STEEL,
            2, 1.6f, new FabricItemSettings().maxDamage(0).group(ModItemGroup.RELICWEAPON)));
    public static final Item Luin = registerItem("luin", new LuinItem(ModToolMaterials.UMBRAL_STEEL,
            4, 1.2f, new FabricItemSettings().maxDamage(0).group(ModItemGroup.RELICWEAPON)));

    public static final Item CreatorSword = registerItem("creatorsword", new CreatorSwordItem(ModToolMaterials.UMBRAL_STEEL,
            5, 1.0f, new FabricItemSettings().maxDamage(0).group(ModItemGroup.RELICWEAPON)));


    //important

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(HeroesRelics.MOD_ID, name), item);
    }

    public static void registerModItems(){
        HeroesRelics.LOGGER.info("Registering items for" + HeroesRelics.MOD_ID);
    }
}
