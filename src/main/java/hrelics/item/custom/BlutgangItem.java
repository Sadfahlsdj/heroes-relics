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

public class BlutgangItem extends SwordItem {
    public BlutgangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return false;
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("item.hrelics.blutgang.tooltip.shift1"));
            tooltip.add(Text.translatable("item.hrelics.blutgang.tooltip.shift2"));
            tooltip.add(Text.translatable("item.hrelics.blutgang.tooltip.shift3"));
        }
        else{
            tooltip.add(Text.translatable("item.hrelics.blutgang.tooltip.flavor"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shiftnotheld"));
        }

    }
}
