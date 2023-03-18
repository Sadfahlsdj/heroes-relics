package hrelics.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BloodItem extends Item {
    public BloodItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack){
        return true;
    }
}
