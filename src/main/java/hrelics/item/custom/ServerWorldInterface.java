package hrelics.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public interface ServerWorldInterface {

    public default void scheduleDamageEvent(Entity attacker, Vec3d pos){

    }
}
