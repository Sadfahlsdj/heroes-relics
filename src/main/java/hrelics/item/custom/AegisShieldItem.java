package hrelics.item.custom;

import net.minecraft.block.DispenserBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
//import net.minecraft.tag.ItemTags; deprecated in 1.19.3
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AegisShieldItem extends ShieldItem {
    public static final int field_30918 = 5;
    public static final float field_30919 = 3.0f;
    public static final String BASE_KEY = "Base";



    public AegisShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if (BlockItem.getBlockEntityNbt(stack) != null) {
            return this.getTranslationKey() + "." + ShieldItem.getColor(stack).getName();
        }
        return super.getTranslationKey(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shift4"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shift1"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shift2"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shift3"));
        }
        else{
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.flavor"));
            tooltip.add(Text.translatable("item.hrelics.aegisshield.tooltip.shiftnotheld"));
        }

    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isIn(ItemTags.PLANKS) || super.canRepair(stack, ingredient);
    }

    public static DyeColor getColor(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        return nbtCompound != null ? DyeColor.byId(nbtCompound.getInt(BASE_KEY)) : DyeColor.WHITE;
    }
}
