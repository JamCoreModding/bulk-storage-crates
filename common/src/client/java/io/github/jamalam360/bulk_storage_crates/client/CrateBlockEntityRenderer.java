package io.github.jamalam360.bulk_storage_crates.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.jamalam360.bulk_storage_crates.content.CrateBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.RandomSupport;

public class CrateBlockEntityRenderer implements BlockEntityRenderer<CrateBlockEntity> {
	private static final int MAX_CAPACITY_ITEMS = 16;
	private static final float RANDOM_XZ_RANGE = 0.35F;
	private static final float RANDOM_ROTATION_DEGREE_RANGE = 18;
	private static final long RANDOM_SEED = RandomSupport.generateUniqueSeed();
	private static final RandomSource RANDOM = RandomSource.create(RANDOM_SEED);

	public CrateBlockEntityRenderer(@SuppressWarnings("unused") BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(CrateBlockEntity entity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		RANDOM.setSeed(RANDOM_SEED);
		int usedCapacity = entity.getUsedCapacity();

		if (usedCapacity == 0) {
			return;
		}

		float usedCapacityProportion = usedCapacity / (float) entity.getMaxCapacity();
		int count = Math.max(1, (int) (usedCapacityProportion * MAX_CAPACITY_ITEMS));
		ItemStack renderStack = entity.getItemTypeStored().getDefaultInstance();
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

		for (int i = 0; i < count; i++) {
			stack.pushPose();
			stack.translate(0.5F, (1 / 16F) + (i / (float)MAX_CAPACITY_ITEMS) * 0.85, 0.5F);
			stack.translate(RANDOM.nextFloat() * RANDOM_XZ_RANGE - (RANDOM_XZ_RANGE / 2), 0F, RANDOM.nextFloat() * RANDOM_XZ_RANGE - (RANDOM_XZ_RANGE / 2));
			stack.mulPose(Axis.YP.rotation((float) (RANDOM.nextFloat() * Math.PI)));
			stack.mulPose(Axis.XP.rotationDegrees(RANDOM.nextFloat() * RANDOM_ROTATION_DEGREE_RANGE - (RANDOM_ROTATION_DEGREE_RANGE * 0.5F)));
			stack.mulPose(Axis.ZP.rotationDegrees(RANDOM.nextFloat() * RANDOM_ROTATION_DEGREE_RANGE - (RANDOM_ROTATION_DEGREE_RANGE * 0.5F)));
			stack.scale(1.8F, 0.1F, 1.8F);

			itemRenderer.renderStatic(
					renderStack,
					ItemDisplayContext.GROUND,
					packedLight,
					packedOverlay,
					stack,
					bufferSource,
					Minecraft.getInstance().level,
					123456789
			);

			stack.popPose();
		}
	}
}
