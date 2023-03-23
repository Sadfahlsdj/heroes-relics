package hrelics.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AreadbharItem extends SwordItem {

    public AreadbharItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("item.hrelics.areadbhar.tooltip.shift1"));
            tooltip.add(Text.translatable("item.hrelics.areadbhar.tooltip.shift2"));
        }
        else{
            tooltip.add(Text.translatable("item.hrelics.areadbhar.tooltip.flavor"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shiftnotheld"));
        }

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
