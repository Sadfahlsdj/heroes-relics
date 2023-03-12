package hrelics.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class BlutgangItem extends SwordItem {
    public BlutgangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(((PlayerEntityInterface) attacker).getWitherHits() > 0){
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 4), attacker);
            ((LivingEntityInterface) target).setBoostedWitherTicks(100);
            ((PlayerEntityInterface) attacker).decrementWitherHits();
        }

        return super.postHit(stack, target, attacker);
    }
}
