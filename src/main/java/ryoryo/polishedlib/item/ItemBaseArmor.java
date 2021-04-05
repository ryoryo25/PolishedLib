package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;
import ryoryo.polishedlib.util.enums.EnumArmorType;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class ItemBaseArmor extends ItemArmor implements IModelRegister {

	/**
	 * tab = COMBAT
	 */
	public ItemBaseArmor(String name, ArmorMaterial material, EnumArmorType type) {
		super(material, type.getRenderIndex(), type.getSlot());
		this.setUnlocalizedName(name + "_" + type.getName());
		this.setRegistryName(name + "_" + type.getName());

		RegistryHandler.register(this);
	}

	public ItemBaseArmor(String name, ArmorMaterial material, EnumArmorType type, CreativeTabs tab) {
		this(name, material, type);
		this.setCreativeTab(tab);
	}

	@Override
	public void registerModels() {
		ModelHandler.registerItemModel(this);
	}
}