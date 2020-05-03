package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;
import ryoryo.polishedlib.util.enums.EnumArmorType;

public class ItemBaseArmor extends ItemArmor {
	public ItemBaseArmor(ArmorMaterial material, EnumArmorType type, String unlocalizeName, CreativeTabs tab) {
		super(material, type.getRenderIndex(), type.getSlot());
		this.setCreativeTab(tab);
		this.setUnlocalizedName(unlocalizeName + "_" + type.getName());
	}
}