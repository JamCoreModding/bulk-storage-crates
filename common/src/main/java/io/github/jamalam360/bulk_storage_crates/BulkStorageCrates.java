package io.github.jamalam360.bulk_storage_crates;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.jamalam360.bulk_storage_crates.content.CrateBlock;
import io.github.jamalam360.bulk_storage_crates.content.CrateBlockEntity;
import io.github.jamalam360.bulk_storage_crates.content.CrateLidItem;
import io.github.jamalam360.bulk_storage_crates.content.CrowbarItem;
import io.github.jamalam360.jamlib.JamLib;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class BulkStorageCrates {
	public static final String MOD_ID = "bulk_storage_crates";
	public static final String MOD_NAME = "Bulk Storage Crates";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
	private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);

	public static final RegistrySupplier<Item> CRATE_LID = ITEMS.register(id("crate_lid"), CrateLidItem::new);
	public static final RegistrySupplier<Item> CROWBAR = ITEMS.register(id("crowbar"), CrowbarItem::new);
	public static final RegistrySupplier<CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register(id("creative_mode_tab"), () -> CreativeTabRegistry.create(Component.literal("text.bulk_storage_crates.creative_mode_tab"), () -> CROWBAR.get().getDefaultInstance()));
	public static final RegistrySupplier<Block> WOODEN_CRATE = BLOCKS.register(id("wooden_crate"), () -> new CrateBlock(Block.Properties.of(), 256));
	public static final RegistrySupplier<Item> WOODEN_CRATE_ITEM = ITEMS.register(id("wooden_crate"), () -> new BlockItem(WOODEN_CRATE.get(), new Item.Properties().arch$tab(CREATIVE_MODE_TAB)));
	public static final RegistrySupplier<BlockEntityType<CrateBlockEntity>> CRATE_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register(id("crate"), () -> BlockEntityType.Builder.of(CrateBlockEntity::new, WOODEN_CRATE.get()).build(null));

	public static final TagKey<Item> CROWBARS = TagKey.create(Registries.ITEM, id("crowbars"));

	public static void init() {
		JamLib.checkForJarRenaming(BulkStorageCrates.class);
		LOGGER.info("Hello, crate world :)");

		BLOCKS.register();
		BLOCK_ENTITY_TYPES.register();
		ITEMS.register();
		CREATIVE_MODE_TABS.register();
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
