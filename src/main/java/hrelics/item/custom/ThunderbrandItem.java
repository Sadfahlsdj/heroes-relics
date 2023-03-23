package hrelics.item.custom;

import hrelics.HeroesRelics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThunderbrandItem extends SwordItem {
    public ThunderbrandItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return false;
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        //testing again
        //HeroesRelics.LOGGER.info("{}",((PlayerEntityInterface) attacker).getLightningHits());
       //lightning spawning goes here
        if(((PlayerEntityInterface) attacker).getLightningHits() > 0){
            //testing
            //HeroesRelics.LOGGER.info("if you see this message the if statement in ThunderbrandItem suceeded");

            //lightning bolt spawning & shit
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, target.getWorld());
            lightningEntity.setPos(target.getX(), target.getY(), target.getZ());

            World w = target.getWorld();
            w.spawnEntity(lightningEntity);

            //removes iframes
            target.hurtTime = 0;
            target.timeUntilRegen = 1;

            ((PlayerEntityInterface) attacker).decrementLightningHits();
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("item.hrelics.thunderbrand.tooltip.shift1"));
            tooltip.add(Text.translatable("item.hrelics.thunderbrand.tooltip.shift2"));
        }
        else{
            tooltip.add(Text.translatable("item.hrelics.thunderbrand.tooltip.flavor"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shiftnotheld"));
        }

    }

}
