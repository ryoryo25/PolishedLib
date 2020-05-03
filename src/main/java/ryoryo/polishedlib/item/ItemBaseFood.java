package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemBaseFood extends ItemFood {
	private int eatSpeed = 32;

	public ItemBaseFood(int hunger, float hiddenHunger, boolean wolfCanEat, String unlocalizeName, CreativeTabs tab, int eatSpeed) {
		super(hunger, hiddenHunger, wolfCanEat);
		this.setCreativeTab(tab);
		this.setUnlocalizedName(unlocalizeName);
		this.eatSpeed = eatSpeed;
	}

	public ItemBaseFood(int hunger, float hiddenHunger, boolean wolfCanEat, String unlocalizeName, CreativeTabs tab) {
		this(hunger, hiddenHunger, wolfCanEat, unlocalizeName, tab, 32);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemStack) {
		return eatSpeed;
	}
}
