package hrelics.item;

import hrelics.HeroesRelics;
import hrelics.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
//import net.minecraft.util.registry.Registry; deprecated in 1.19.3


public class ModItems {

    public static final Item Areadbhar = registerItem("areadbhar", new AreadbharItem(ModToolMaterials.UMBRAL_STEEL,
            10, -2.4f, new FabricItemSettings().maxDamage(0)));

    public static final Item Thunderbrand = registerItem("thunderbrand", new ThunderbrandItem(ModToolMaterials.UMBRAL_STEEL,
            3, -2.0f, new FabricItemSettings().maxDamage(0)));
    public static final Item Luin = registerItem("luin", new LuinItem(ModToolMaterials.UMBRAL_STEEL,
            5, -2.2f, new FabricItemSettings().maxDamage(0)));

    public static final Item CreatorSword = registerItem("creatorsword", new CreatorSwordItem(ModToolMaterials.UMBRAL_STEEL,
            6, -2.4f, new FabricItemSettings().maxDamage(0)));

    public static final Item Failnaught = registerItem("failnaught", new FailnaughtItem
            (new FabricItemSettings().maxDamage(0)));

    public static final Item LanceOfRuin = registerItem("lanceofruin", new LanceOfRuinItem(ModToolMaterials.UMBRAL_STEEL,
            5, -2.4f, new FabricItemSettings().maxDamage(0)));
    public static final Item Blutgang = registerItem("blutgang", new BlutgangItem(ModToolMaterials.UMBRAL_STEEL,
            5, -2.4f, new FabricItemSettings().maxDamage(0)));

    public static final Item AegisShield = registerItem("aegisshield",
            new AegisShieldItem(new FabricItemSettings().maxDamage(0)));

    public static final Item UmbralSteel = registerItem("umbralsteel",
            new Item(new FabricItemSettings()));

    //unique bloods for weapon crafting
    public static final Item AncientBlood = registerItem("ancientblood", new Item(new FabricItemSettings()));

    public static final Item BlaiddydBlood = registerItem("blaiddydblood", new BloodItem(new FabricItemSettings()));
    public static final Item BeastBlood = registerItem("beastblood", new BloodItem(new FabricItemSettings()));
    public static final Item HeavenlyBlood = registerItem("heavenlyblood", new BloodItem(new FabricItemSettings()));
    public static final Item RieganBlood = registerItem("rieganblood", new BloodItem(new FabricItemSettings()));
    public static final Item GautierBlood = registerItem("gautierblood", new BloodItem(new FabricItemSettings()));
    public static final Item GalateaBlood = registerItem("galateablood", new BloodItem(new FabricItemSettings()));
    public static final Item CharonBlood = registerItem("charonblood", new BloodItem(new FabricItemSettings()));


    //important

    private static Item registerItem(String name, Item item){

        return Registry.register(Registries.ITEM, new Identifier(HeroesRelics.MOD_ID, name), item);

    }

    public static void addItemsToItemGroup(){
        //each item needs to be manually added here
        addToItemGroup(ModItemGroup.RELICITEMS, Areadbhar);
        addToItemGroup(ModItemGroup.RELICITEMS, Thunderbrand);
        addToItemGroup(ModItemGroup.RELICITEMS, Luin);
        addToItemGroup(ModItemGroup.RELICITEMS, CreatorSword);
        addToItemGroup(ModItemGroup.RELICITEMS, LanceOfRuin);
        addToItemGroup(ModItemGroup.RELICITEMS, Failnaught);
        addToItemGroup(ModItemGroup.RELICITEMS, Blutgang);
        addToItemGroup(ModItemGroup.RELICITEMS, AegisShield);

        addToItemGroup(ModItemGroup.RELICITEMS, UmbralSteel);

        //bloods
        addToItemGroup(ModItemGroup.RELICITEMS, BlaiddydBlood);
        addToItemGroup(ModItemGroup.RELICITEMS, BeastBlood);
        addToItemGroup(ModItemGroup.RELICITEMS, HeavenlyBlood);
        addToItemGroup(ModItemGroup.RELICITEMS, RieganBlood);
        addToItemGroup(ModItemGroup.RELICITEMS, GautierBlood);
        addToItemGroup(ModItemGroup.RELICITEMS, GalateaBlood);
        addToItemGroup(ModItemGroup.RELICITEMS, CharonBlood);
        addToItemGroup(ModItemGroup.RELICITEMS, AncientBlood);
    }

    public static void addToItemGroup(ItemGroup group, Item item){
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));

    }

    public static void registerModItems(){

        HeroesRelics.LOGGER.info("Registering items for " + HeroesRelics.MOD_ID);
        addItemsToItemGroup();
    }
}
