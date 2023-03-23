package hrelics.item.custom;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_ATTACK_DAMAGE;

public class CreatorSwordItem extends SwordItem {
    public CreatorSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);


    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return false;
    }

    public static EntityAttributeModifier RupturedHeavenReach = new EntityAttributeModifier("rheavenreach",
            1.25, EntityAttributeModifier.Operation.ADDITION);
    public static EntityAttributeModifier RupturedHeavenAttackRange = new EntityAttributeModifier("rheavenrange",
            1.25, EntityAttributeModifier.Operation.ADDITION);
    public static EntityAttributeModifier RupturedHeavenDamage = new EntityAttributeModifier("rheavendamage",
            8, EntityAttributeModifier.Operation.ADDITION);

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        attacker.getAttributeInstance(ReachEntityAttributes.REACH).removeModifier(RupturedHeavenReach);
        attacker.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE).removeModifier(RupturedHeavenAttackRange);
        attacker.getAttributeInstance(GENERIC_ATTACK_DAMAGE).removeModifier(RupturedHeavenDamage);
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("item.hrelics.creatorsword.tooltip.shift1"));
            tooltip.add(Text.translatable("item.hrelics.creatorsword.tooltip.shift2"));
            tooltip.add(Text.translatable("item.hrelics.creatorsword.tooltip.shift3"));
        }
        else{
            tooltip.add(Text.translatable("item.hrelics.creatorsword.tooltip.flavor"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shiftnotheld"));
        }

    }
}
