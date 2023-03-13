package hrelics.item;

import hrelics.HeroesRelics;
import hrelics.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
//import net.minecraft.util.registry.Registry; deprecated in 1.19.3


public class ModItems {

    public static final Item Areadbhar = registerItem("areadbhar", new AreadbharItem(ModToolMaterials.UMBRAL_STEEL,
            10, 1.3f, new FabricItemSettings().maxDamage(0)));

    public static final Item Thunderbrand = registerItem("thunderbrand", new ThunderbrandItem(ModToolMaterials.UMBRAL_STEEL,
            3, 1.6f, new FabricItemSettings().maxDamage(0)));
    public static final Item Luin = registerItem("luin", new LuinItem(ModToolMaterials.UMBRAL_STEEL,
            5, 1.2f, new FabricItemSettings().maxDamage(0)));

    public static final Item CreatorSword = registerItem("creatorsword", new CreatorSwordItem(ModToolMaterials.UMBRAL_STEEL,
            6, 1.0f, new FabricItemSettings().maxDamage(0)));

    public static final Item Failnaught = registerItem("failnaught", new FailnaughtItem
            (new FabricItemSettings().maxDamage(0)));

    public static final Item LanceOfRuin = registerItem("lanceofruin", new LanceOfRuinItem(ModToolMaterials.UMBRAL_STEEL,
            5, 1.0f, new FabricItemSettings().maxDamage(0)));
    public static final Item Blutgang = registerItem("blutgang", new BlutgangItem(ModToolMaterials.UMBRAL_STEEL,
            5, 1.0f, new FabricItemSettings().maxDamage(0)));

    public static final Item AegisShield = registerItem("aegisshield",
            new AegisShieldItem(new FabricItemSettings().maxDamage(0)));


    //important

    private static Item registerItem(String name, Item item){

        return Registry.register(Registries.ITEM, new Identifier(HeroesRelics.MOD_ID, name), item);

    }

    public static void addItemsToItemGroup(){
        //each item needs to be manually added here
        addToItemGroup(ModItemGroup.RELICWEAPON, Areadbhar);
        addToItemGroup(ModItemGroup.RELICWEAPON, Thunderbrand);
        addToItemGroup(ModItemGroup.RELICWEAPON, Luin);
        addToItemGroup(ModItemGroup.RELICWEAPON, CreatorSword);
        addToItemGroup(ModItemGroup.RELICWEAPON, LanceOfRuin);
        addToItemGroup(ModItemGroup.RELICWEAPON, Failnaught);
        addToItemGroup(ModItemGroup.RELICWEAPON, Blutgang);
        addToItemGroup(ModItemGroup.RELICWEAPON, AegisShield);
    }

    public static void addToItemGroup(ItemGroup group, Item item){
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));

    }

    public static void registerModItems(){

        HeroesRelics.LOGGER.info("Registering items for " + HeroesRelics.MOD_ID);
        addItemsToItemGroup();
    }
}
