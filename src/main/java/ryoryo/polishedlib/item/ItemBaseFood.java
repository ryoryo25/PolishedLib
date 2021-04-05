package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class ItemBaseFood extends ItemFood implements IModelRegister {

	private int eatSpeed;

	/**
	 * tab = FOOD
	 */
	public ItemBaseFood(String name, int hunger, float hiddenHunger, boolean wolfCanEat, int eatSpeed) {
		super(hunger, hiddenHunger, wolfCanEat);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.eatSpeed = eatSpeed;

		RegistryHandler.register(this);
	}

	public ItemBaseFood(String name, int hunger, float hiddenHunger, boolean wolfCanEat, int eatSpeed, CreativeTabs tab) {
		this(name, hunger, hiddenHunger, wolfCanEat, eatSpeed);
		this.setCreativeTab(tab);
	}

	/**
	 * eatSpeed = 32
	 */
	public ItemBaseFood(String name, int hunger, float hiddenHunger, boolean wolfCanEat, CreativeTabs tab) {
		this(name, hunger, hiddenHunger, wolfCanEat, 32, tab);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemStack) {
		return eatSpeed;
	}

	@Override
	public void registerModels() {
		ModelHandler.registerItemModel(this);
	}
}
