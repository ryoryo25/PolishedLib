package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;
import ryoryo.polishedlib.util.enums.EnumArmorType;
import ryoryo.polishedlib.util.interfaces.IModId;

public abstract class ItemBaseArmor extends ItemArmor implements IModId
{
	public ItemBaseArmor(ArmorMaterial material, EnumArmorType type, String unlocalizeName, CreativeTabs tab)
	{
		super(material, type.getRenderIndex(), type.getSlot());
		this.setCreativeTab(tab);
		this.setUnlocalizedName(unlocalizeName + "_" + type.getName());
	}
}