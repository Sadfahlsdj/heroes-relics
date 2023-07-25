package hrelics.client.particle;

import hrelics.item.ModItems;
import hrelics.item.custom.NagaProjectileEntity;
import hrelics.item.custom.ValflameProjectileEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class ValflameProjectileEntityRenderer extends EntityRenderer<ValflameProjectileEntity> {
    public static final ItemStack STACK = new ItemStack(ModItems.ValflameProjectileItem);

    public ValflameProjectileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(ValflameProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light){
        matrices.push();
        //rotates da projectile
        //matrices.multiply(RotationAxis.POSITIVE_Y.rotation(entity.age));

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                STACK, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0
        );

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ValflameProjectileEntity entity) {
        return null;
    }
}
