package io.github.jamalam360.bulk_storage_crates.datagen;

import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import net.fabricmc.api.ModInitializer;

public class BulkStorageCratesBootstrap implements ModInitializer {
	@Override
	public void onInitialize() {
		BulkStorageCrates.init();
	}
}
