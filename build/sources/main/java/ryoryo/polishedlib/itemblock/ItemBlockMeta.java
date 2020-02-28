package ryoryo.polishedlib.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemBlockMeta extends ItemMultiTexture
{

	public ItemBlockMeta(Block block, Mapper mapper)
	{
		super(block, block, mapper);
	}

	public ItemBlockMeta(Block block, final String[] namesByMeta)
	{
		super(block, block, namesByMeta);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "_" + this.nameFunction.apply(stack);
	}
}