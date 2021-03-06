package pcl.lc.module.power.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import pcl.lc.base.energy.IItemEnergyStore;
import pcl.lc.core.ResourceAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnergyCrystal extends Item implements IItemEnergyStore {

	public ItemEnergyCrystal() {
		super();
		setHasSubtypes(false);
		setMaxStackSize(1);
		setMaxDamage(21);
		setNoRepair();
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected String getIconString() {
		return ResourceAccess.formatResourceName("${ASSET_KEY}:%s_${TEX_QUALITY}", "energy_crystal");
	}

	@Override
	public double getMaximumEnergy() {
		return 128.0d;
	}

	@Override
	public double getMaximumIOPayload() {
		return 1.0d;
	}

	@Override
	public double receiveEnergy(ItemStack itemStack, double quantity, boolean isSimulated) {
		if (quantity > getMaximumIOPayload())
			quantity = getMaximumIOPayload();
		double actualPayload = Math.min(getMaximumEnergy() - getEnergyStored(itemStack), quantity);
		if (!isSimulated)
			setEnergyStored(itemStack, getEnergyStored(itemStack) + actualPayload);
		updateDisplay(itemStack);
		return actualPayload;
	}

	@Override
	public double extractEnergy(ItemStack itemStack, double quantity, boolean isSimulated) {
		if (quantity > getMaximumIOPayload())
			quantity = getMaximumIOPayload();
		double actualPayload = Math.min(getEnergyStored(itemStack), quantity);
		if (!isSimulated)
			setEnergyStored(itemStack, getEnergyStored(itemStack) - actualPayload);
		updateDisplay(itemStack);
		return actualPayload;
	}

	@Override
	public double getEnergyStored(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null)
			itemStack.setTagCompound(new NBTTagCompound());
		if (!itemStack.stackTagCompound.hasKey("stored-energy"))
			return 0.0d;
		return itemStack.stackTagCompound.getDouble("stored-energy");
	}

	@Override
	public void setEnergyStored(ItemStack itemStack, double value) {
		updateDisplay(itemStack);
		if (itemStack.stackTagCompound == null)
			itemStack.setTagCompound(new NBTTagCompound());
		itemStack.stackTagCompound.setDouble("stored-energy", value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		double energy = ((100 * getEnergyStored(par1ItemStack)) / getMaximumEnergy());
		par3List.add(I18n.format("energy.text", String.format("%.2f%%", energy)));
	}

	private void updateDisplay(ItemStack stack) {
		double ratio = getEnergyStored(stack) / getMaximumEnergy();
		stack.setItemDamage(21 - (int) Math.floor(20 * ratio));
	}
}
