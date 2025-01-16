package io.github.jamalam360.bulk_storage_crates.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import io.github.jamalam360.bulk_storage_crates.content.CrateBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.ModelTemplates;

public class ModelProvider extends FabricModelProvider {
	public ModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators gen) {
		gen.modelOutput.accept(
				BulkStorageCrates.id("block/unlidded_wooden_crate"),
				() -> {
					JsonObject model = new JsonObject();
					model.addProperty("parent", "bulk_storage_crates:block/crate_unlidded");
					JsonObject textures = new JsonObject();
					textures.addProperty("side", "bulk_storage_crates:block/wooden_crate_side");
					textures.addProperty("bottom", "bulk_storage_crates:block/wooden_crate_bottom");
					textures.addProperty("particle", "bulk_storage_crates:block/wooden_crate_side");
					model.add("textures", textures);
					return model;
				}
		);

		gen.modelOutput.accept(
				BulkStorageCrates.id("block/lidded_wooden_crate"),
				() -> {
					JsonObject model = new JsonObject();
					model.addProperty("parent", "bulk_storage_crates:block/crate_lidded");
					JsonObject textures = new JsonObject();
					textures.addProperty("side", "bulk_storage_crates:block/wooden_crate_side");
					textures.addProperty("bottom", "bulk_storage_crates:block/wooden_crate_bottom");
					textures.addProperty("lid", "bulk_storage_crates:block/crate_lid");
					textures.addProperty("particle", "bulk_storage_crates:block/wooden_crate_side");
					model.add("textures", textures);
					return model;
				}
		);

		gen.blockStateOutput.accept(
				MultiVariantGenerator
						.multiVariant(BulkStorageCrates.WOODEN_CRATE.get())
						.with(BlockModelGenerators.createBooleanModelDispatch(CrateBlock.LIDDED, BulkStorageCrates.id("block/lidded_wooden_crate"), BulkStorageCrates.id("block/unlidded_wooden_crate")))
		);

		gen.delegateItemModel(BulkStorageCrates.WOODEN_CRATE.get(), BulkStorageCrates.id("block/unlidded_wooden_crate"));
	}

	@Override
	public void generateItemModels(ItemModelGenerators gen) {
		gen.generateFlatItem(BulkStorageCrates.CRATE_LID.get(), ModelTemplates.FLAT_ITEM);
		gen.generateFlatItem(BulkStorageCrates.CROWBAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
	}
}
