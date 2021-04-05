package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class ItemBaseAxe extends ItemAxe implements IModelRegister {

	/**
	 * damage = 8F
	 * speed = -3.2F
	 * tab = TOOLS
	 */
	public ItemBaseAxe(String name, ToolMaterial toolMaterial) {
		super(toolMaterial, 8F, -3.2F);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);

		RegistryHandler.register(this);
	}

	/**
	 * tab = TOOLS
	 */
	public ItemBaseAxe(String name, ToolMaterial toolMaterial, float damage, float speed) {
		this(name, toolMaterial);
		this.attackDamage = damage;
		this.attackSpeed = speed;
	}

	public ItemBaseAxe(String name, ToolMaterial toolMaterial, float damage, float speed, CreativeTabs tab) {
		this(name, toolMaterial, damage, speed);
		this.setCreativeTab(tab);
	}

	@Override
	public void registerModels() {
		ModelHandler.registerItemModel(this);
	}
}