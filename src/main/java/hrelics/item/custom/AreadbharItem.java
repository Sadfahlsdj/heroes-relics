package hrelics.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class AreadbharItem extends SwordItem {

    public AreadbharItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //postHit example code; will prob come in handy for thunderbrand
    /*

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 0), attacker);
        return super.postHit(stack, target, attacker);
    }

     */

}
