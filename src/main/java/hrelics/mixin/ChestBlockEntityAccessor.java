package hrelics.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockEntity.class)
public interface ChestBlockEntityAccessor {
    //needed to access ChestBlockEntity.getPos in ChestBlockEntityMixin
    @Invoker("getPos")
    public BlockPos invokeGetPos();

}
