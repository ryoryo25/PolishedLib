package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class ItemBasePickaxe extends ItemPickaxe implements IModelRegister {

	/**
	 * damage = 1.0F
	 * speed = -2.8F
	 * tab = TOOLS
	 */
	public ItemBasePickaxe(String name, ToolMaterial toolMaterial) {
		super(toolMaterial);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);

		RegistryHandler.register(this);
	}

	/**
	 * tab = TOOLS
	 */
	public ItemBasePickaxe(String name, ToolMaterial toolMaterial, float damage, float speed) {
		this(name, toolMaterial);
		this.attackDamage = damage;
		this.attackSpeed = speed;
	}

	public ItemBasePickaxe(String name, ToolMaterial toolMaterial, float damage, float speed, CreativeTabs tab) {
		this(name, toolMaterial, damage, speed);
		this.setCreativeTab(tab);
	}

	@Override
	public void registerModels() {
		ModelHandler.registerItemModel(this);
	}
}