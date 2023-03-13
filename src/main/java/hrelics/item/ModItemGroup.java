package hrelics.item;

import hrelics.HeroesRelics;
//import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder; doesn't work in 1.19.3
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    /*
    public static final ItemGroup RELICWEAPON = FabricItemGroupBuilder.build(new Identifier(HeroesRelics.MOD_ID, "relicweapon"),
            () -> new ItemStack(ModItems.Areadbhar));

     */
    public static ItemGroup RELICWEAPON;

    public static void registerModItemGroup(){
        RELICWEAPON = FabricItemGroup.builder(new Identifier(HeroesRelics.MOD_ID, "relicweapon"))
                .displayName(Text.literal("Relic Items"))
                .icon(() -> new ItemStack(ModItems.Areadbhar)).build();
    }
}
