package io.github.jamalam360.bulk_storage_crates.neoforge;

import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import io.github.jamalam360.bulk_storage_crates.client.BulkStorageCratesClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = BulkStorageCrates.MOD_ID, dist = Dist.CLIENT)
public class BulkStorageCratesClientNeoForge {
	public BulkStorageCratesClientNeoForge() {
		BulkStorageCratesClient.init();
	}
}
