package io.github.jamalam360.bulk_storage_crates.fabric;

import io.github.jamalam360.bulk_storage_crates.client.BulkStorageCratesClient;
import net.fabricmc.api.ClientModInitializer;

public class BulkStorageCratesClientFabric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BulkStorageCratesClient.init();
	}
}
