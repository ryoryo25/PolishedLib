package ryoryo.polishedlib.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemBaseMeta extends ItemBase {

	public ItemBaseMeta(String name) {
		super(name);
		this.setHasSubtypes(true);
	}

	public ItemBaseMeta(String name, CreativeTabs tab) {
		this(name);
		this.setCreativeTab(tab);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedNameImpl(stack);
	}

	protected abstract String getUnlocalizedNameImpl(ItemStack stack);

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		this.getSubItemsImpl(tab, items);
	}

	@SideOnly(Side.CLIENT)
	protected abstract void getSubItemsImpl(CreativeTabs tab, NonNullList<ItemStack> items);

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		this.registerModelsImpl();
	}

	@SideOnly(Side.CLIENT)
	protected abstract void registerModelsImpl();
}