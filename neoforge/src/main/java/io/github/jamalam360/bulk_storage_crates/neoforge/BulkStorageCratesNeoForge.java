package io.github.jamalam360.bulk_storage_crates.neoforge;

import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import net.neoforged.fml.common.Mod;

@Mod(BulkStorageCrates.MOD_ID)
public class BulkStorageCratesNeoForge {
    public BulkStorageCratesNeoForge() {
        BulkStorageCrates.init();
    }
}
