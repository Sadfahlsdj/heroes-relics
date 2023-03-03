package hrelics.item.custom;

import hrelics.HeroesRelics;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class ThunderbrandItem extends SwordItem {
    public ThunderbrandItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        //testing again
        //HeroesRelics.LOGGER.info("{}",
                ((PlayerEntityInterface) attacker).getLightningHits());
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

}
