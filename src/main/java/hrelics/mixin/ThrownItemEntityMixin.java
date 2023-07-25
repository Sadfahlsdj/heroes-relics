package hrelics.mixin;

import hrelics.item.custom.ThrownItemEntityInterface;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ThrownItemEntity.class)
public class ThrownItemEntityMixin implements ThrownItemEntityInterface {

    int valflameUseTime;
    public void setValflameUseTime(int i){
        valflameUseTime = i;
    }

    public int getValflameUseTime(){
        return valflameUseTime;
    }
}
