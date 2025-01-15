package io.github.jamalam360.bulk_storage_crates.fabric;

import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import net.fabricmc.api.ModInitializer;

public class BulkStorageCratesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        BulkStorageCrates.init();
    }
}
