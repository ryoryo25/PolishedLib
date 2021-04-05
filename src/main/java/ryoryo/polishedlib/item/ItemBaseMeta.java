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
	public abstract String getUnlocalizedName(ItemStack stack);

	@Override
	@SideOnly(Side.CLIENT)
	public abstract void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items);

	@Override
	@SideOnly(Side.CLIENT)
	public abstract void registerModels();
}