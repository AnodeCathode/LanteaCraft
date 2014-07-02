package pcl.lc.items;

import net.minecraft.item.Item;
import pcl.lc.LanteaCraft;

public class ItemGDO extends Item {

	public ItemGDO(int id) {
		super(id);
	}

	@Override
	protected String getIconString() {
		return LanteaCraft.getAssetKey() + ":gdo_iris_controller_" + LanteaCraft.getProxy().getRenderMode();
	}
}