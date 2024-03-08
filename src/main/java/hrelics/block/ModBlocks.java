package hrelics.block;

import hrelics.HeroesRelics;
import hrelics.block.custom.CryptChestBlock;
import hrelics.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block CRYPT_CHEST = registerBlock("crypt_chest",
            new CryptChestBlock(FabricBlockSettings.of(Material.WOOD), () -> BlockEntityType.CHEST), ModItemGroup.RELICITEMS);

    public static final Item CRYPT_CHEST_ITEM = registerBlockItem("crypt_chest",
            CRYPT_CHEST, ModItemGroup.RELICITEMS);

    private static Block registerBlock(String name, Block block, ItemGroup tab){
        // registerBlockItem(name, block, tab);
        HeroesRelics.LOGGER.info("registering a block" );
        return Registry.register(Registries.BLOCK, new Identifier(HeroesRelics.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab){
        HeroesRelics.LOGGER.info("registering a blockitem");
        return Registry.register(Registries.ITEM, new Identifier(HeroesRelics.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks(){
        HeroesRelics.LOGGER.info("registering hrelics mod blocks");
    }
}
