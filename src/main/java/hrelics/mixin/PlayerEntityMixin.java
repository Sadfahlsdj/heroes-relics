package hrelics.mixin;

import hrelics.item.custom.PlayerEntityInterface;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityInterface {
    int atrocityHits = 0;

    public void setAtrocityHits(int a){
        atrocityHits = a;
    }

    public int getAtrocityHits(){
        return atrocityHits;
    }

    public void decrementAtrocityHits(){
        atrocityHits--;
    }
}
