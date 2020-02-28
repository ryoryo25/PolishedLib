package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
	public ItemBase(String name, CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		this.setUnlocalizedName(name);
	}
}