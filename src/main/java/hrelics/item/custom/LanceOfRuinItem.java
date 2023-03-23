package hrelics.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LanceOfRuinItem extends SwordItem {
    public LanceOfRuinItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
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
            tooltip.add(Text.translatable("item.hrelics.lanceofruin.tooltip.shift1"));
            tooltip.add(Text.translatable("item.hrelics.lanceofruin.tooltip.shift2"));
            tooltip.add(Text.translatable("item.hrelics.lanceofruin.tooltip.shift3"));
        }
        else{
            tooltip.add(Text.translatable("item.hrelics.lanceofruin.tooltip.flavor"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shiftnotheld"));
        }

    }
}
