package io.github.jamalam360.bulk_storage_crates.client;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;

public class BulkStorageCratesClient {
	public static void init() {
		BlockEntityRendererRegistry.register(BulkStorageCrates.CRATE_BLOCK_ENTITY_TYPE.get(), CrateBlockEntityRenderer::new);
	}
}
