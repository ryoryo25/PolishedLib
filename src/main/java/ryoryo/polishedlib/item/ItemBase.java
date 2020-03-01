package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ryoryo.polishedlib.util.interfaces.IModId;

public abstract class ItemBase extends Item implements IModId
{
	public ItemBase(String name, CreativeTabs tab)
	{
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}