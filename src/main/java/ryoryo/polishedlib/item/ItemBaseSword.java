package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class ItemBaseSword extends ItemSword implements IModelRegister {

	/**
	 * tab = COMBAT
	 */
	public ItemBaseSword(String name, ToolMaterial toolMaterial) {
		super(toolMaterial);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);

		RegistryHandler.register(this);
	}

	public ItemBaseSword(String name, ToolMaterial toolMaterial, CreativeTabs tab) {
		this(name, toolMaterial);
		this.setCreativeTab(tab);
	}

	@Override
	public void registerModels() {
		ModelHandler.registerItemModel(this);
	}
}