package pcl.lc.items;

import pcl.lc.module.ModuleDecor.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemLanteaDecorGlass extends ItemBlock {

	public ItemLanteaDecorGlass(int id) {
		super(id);
		setHasSubtypes(true);
	}

	@Override
	public Icon getIconFromDamage(int i) {
		return Blocks.glassDecorBlock.getIcon(0, i);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile.lanteaGlassDecor." + stack.getItemDamage();
	}

}