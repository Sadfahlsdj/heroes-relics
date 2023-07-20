package hrelics.client.particle;

import hrelics.item.ModItems;
import hrelics.item.custom.NagaProjectileEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.util.math.*;
import net.minecraft.util.math.Vec2f;

public class NagaProjectileEntityRenderer extends EntityRenderer<NagaProjectileEntity> {
    public static final ItemStack STACK = new ItemStack(ModItems.NagaProjectileItem);

    public NagaProjectileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(NagaProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light){
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(entity.age));

        //logic to rotate the projectile goes here probably - commented shit is testing
        //matrices.multiply(Direction.fromHorizontal(0).getRotationQuaternion());
        //Quaternionf q = new Quaternionf(entity.age % 20, 0, 0, 0);
        //matrices.multiply(q);

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                STACK, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0
        );

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(NagaProjectileEntity entity) {
        return null;
    }
}
