package io.github.jamalam360.bulk_storage_crates.content;

import io.github.jamalam360.bulk_storage_crates.BulkStorageCrates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class CrateBlockEntity extends BlockEntity {
	private int capacity;
	private NonNullList<ItemStack> items;

	public CrateBlockEntity(BlockPos pos, BlockState state) {
		this(pos, state, 0);
	}

	public CrateBlockEntity(BlockPos pos, BlockState state, int capacity) {
		super(BulkStorageCrates.CRATE_BLOCK_ENTITY_TYPE.get(), pos, state);
		this.capacity = capacity;
		this.items = NonNullList.withSize(this.capacity / 64, ItemStack.EMPTY);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (this.capacity != 0) {
			tag.putInt("Capacity", this.capacity);
		}

		ContainerHelper.saveAllItems(tag, this.items, registries);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("Capacity")) {
			this.capacity = tag.getInt("Capacity");
		}

		this.items = NonNullList.withSize(this.capacity / 64, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.items, registries);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithoutMetadata(registries);
	}

	// Returns what should be given back to the player
	protected ItemStack addStack(ItemStack stack) {
		Item itemTypeStored = this.getItemTypeStored();
		if (itemTypeStored != null && !stack.is(itemTypeStored)) {
			return stack;
		}

		int capacityRemaining = this.capacity - this.getUsedCapacity();
		if (capacityRemaining == 0) {
			return stack;
		}

		for (int i = 0; i < this.items.size(); i++) {
			ItemStack otherStack = this.items.get(i);
			if (!otherStack.isEmpty() && ItemStack.isSameItemSameComponents(stack, otherStack)) {
				int availableCount = otherStack.getMaxStackSize() - otherStack.getCount();

				if (availableCount > stack.getCount()) {
					otherStack.setCount(otherStack.getCount() + stack.getCount());
					stack.setCount(0);
				} else {
					otherStack.setCount(otherStack.getMaxStackSize());
					stack.setCount(stack.getCount() - availableCount);
				}
			} else if (otherStack.isEmpty()) {
				this.items.set(i, stack.copy());
				stack.setCount(0);
			}

			if (stack.isEmpty()) {
				break;
			}
		}

		this.saveAndSyncToClient();
		return stack;
	}

	protected ItemStack removeStack() {
		ItemStack last = this.items.reversed().stream().filter(Predicate.not(ItemStack::isEmpty)).findFirst().orElse(ItemStack.EMPTY);
		ItemStack returnStack = last.copy();
		last.setCount(0);
		this.saveAndSyncToClient();
		return returnStack;
	}

	private void saveAndSyncToClient() {
		this.setChanged();
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 0);
	}

	@Nullable
	public Item getItemTypeStored() {
		if (this.items.isEmpty() || this.items.stream().allMatch(ItemStack::isEmpty)) {
			return null;
		}

		return this.items.getFirst().getItem();
	}

	public int getUsedCapacity() {
		int capacity = 0;

		for (ItemStack stack : this.items) {
			capacity += stack.getCount();
		}

		return capacity;
	}

	public int getMaxCapacity() {
		return this.capacity;
	}

	public NonNullList<ItemStack> getItems() {
		return this.items;
	}
}
