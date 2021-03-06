package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFNaga;
import twilightforest.entity.boss.EntityTFNaga;

public class RenderTFNaga<T extends EntityTFNaga, M extends ModelTFNaga<T>> extends LivingRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("nagahead.png");

	public RenderTFNaga(EntityRendererManager manager, M modelbase, float shadowSize) {
		super(manager, modelbase, shadowSize);
		this.addLayer(new NagaEyelidsLayer(this));
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		super.render(entity, entityYaw, partialTicks, stack, buffer, light);
		if (!Minecraft.getInstance().isGamePaused() && entity.isDazed()) {
			Vec3d pos = new Vec3d(entity.getX(), entity.getY() + 3.15D, entity.getZ()).add(new Vec3d(1.5D, 0, 0).rotateYaw((float) Math.toRadians(entity.getRNG().nextInt(360))));
			Minecraft.getInstance().world.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, 0, 0, 0);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFNaga entity) {
		return textureLoc;
	}
}
