package hrelics.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class LuinItem extends SwordItem {
    public LuinItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return false;
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("item.hrelics.luin.tooltip.shift1"));
            tooltip.add(Text.translatable("item.hrelics.luin.tooltip.shift2"));
        }
        else{
            tooltip.add(Text.translatable("item.hrelics.luin.tooltip.flavor"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shiftnotheld"));
        }

    }
}
