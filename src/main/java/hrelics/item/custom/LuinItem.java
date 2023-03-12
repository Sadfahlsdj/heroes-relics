package hrelics.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;



public class LuinItem extends SwordItem {
    public LuinItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(((PlayerEntityInterface) attacker).getFireHits() > 0){
            target.setFireTicks(100);
            ((LivingEntityInterface) target).setBoostedFireTicks(100);
            ((PlayerEntityInterface) attacker).decrementFireHits();
        }

        return super.postHit(stack, target, attacker);
    }
}
