package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class ItemBaseHoe extends ItemHoe implements IModelRegister {

	/**
	 * tab = TOOLS
	 */
	public ItemBaseHoe(String name, ToolMaterial toolMaterial) {
		super(toolMaterial);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);

		RegistryHandler.register(this);
	}

	public ItemBaseHoe(String name, ToolMaterial toolMaterial, CreativeTabs tab) {
		this(name, toolMaterial);
		this.setCreativeTab(tab);
	}

	@Override
	public void registerModels() {
		ModelHandler.registerItemModel(this);
	}
}