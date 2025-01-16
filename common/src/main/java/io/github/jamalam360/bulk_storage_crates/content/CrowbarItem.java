package io.github.jamalam360.bulk_storage_crates.content;

import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import net.minecraft.world.item.Item;

public class CrowbarItem extends Item {
	public CrowbarItem() {
		super(new Item.Properties().stacksTo(1).arch$tab(BulkStorageCrates.CREATIVE_MODE_TAB));
	}
}
