package pcl.lc.base.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public abstract class FilteredInventory implements ISidedInventory {

	protected ItemStack[] items;
	protected FilterRule[] rules;

	public FilteredInventory(int size) {
		items = new ItemStack[size];
		rules = new FilterRule[size];
	}

	public void setFilterRule(int slot, FilterRule rule) {
		rules[slot] = rule;
	}

	public FilterRule getFilterRule(int slot) {
		return rules[slot];
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int take) {
		if (items[slot] == null)
			return null;
		if (items[slot].stackSize == 0)
			return null;
		return items[slot].splitStack(Math.min(take, items[slot].stackSize));
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		items[i] = itemstack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (rules[i] != null && !rules[i].test(itemstack))
			return false;
		return true;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

}
