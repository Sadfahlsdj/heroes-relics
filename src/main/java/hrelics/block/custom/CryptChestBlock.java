package hrelics.block.custom;

import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;

import java.util.function.Supplier;

public class CryptChestBlock extends ChestBlock {
    public CryptChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);
    }
}
