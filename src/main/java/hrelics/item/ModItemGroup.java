package hrelics.item;

import hrelics.HeroesRelics;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup RELICWEAPON = FabricItemGroupBuilder.build(new Identifier(HeroesRelics.MOD_ID, "relicweapon"),
            () -> new ItemStack(ModItems.Areadbhar));
}
