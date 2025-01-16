package io.github.jamalam360.bulk_storage_crates.content;

import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import net.minecraft.world.item.Item;

public class CrateLidItem extends Item {
	public CrateLidItem() {
		super(new Properties().stacksTo(16).arch$tab(BulkStorageCrates.CREATIVE_MODE_TAB));
	}
}
