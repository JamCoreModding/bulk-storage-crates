package io.github.jamalam360.bulk_storage_crates;

import io.github.jamalam360.jamlib.JamLib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BulkStorageCrates {
	public static final String MOD_ID = "bulk_storage_crates";
	public static final String MOD_NAME = "Bulk Storage Crates";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static void init() {
		JamLib.checkForJarRenaming(BulkStorageCrates.class);
	}
}
