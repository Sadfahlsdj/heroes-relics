package hrelics.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NagaParticle extends SpriteBillboardParticle {
    protected NagaParticle(ClientWorld level, double xCoord, double yCoord, double zCoord, SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.velocityMultiplier = 1.5F;
        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.scale = 0.75F;
        this.maxAge = 30;
        this.setSpriteForAge(spriteSet);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new NagaParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }






    /*public static void createParticle(World world, ParticleEffect particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed, boolean forceSend) {
        if (world instanceof ServerWorld) {
            ParticleS2CPacket packet = new ParticleS2CPacket(particle, true, x, y, z, (float) deltaX, (float) deltaY, (float) deltaZ, (float) speed, count);
            for (int j = 0; j < world.getPlayers().size(); ++j) {
                ServerPlayerEntity player = ((ServerWorld) world).getPlayers().get(j);
                BlockPos blockPos = player.getBlockPos();
                if (blockPos.isWithinDistance(new Vec3d(x, y, z), 128.0D))
                    player.networkHandler.sendPacket(packet);
            }
        }
    }*/
}
