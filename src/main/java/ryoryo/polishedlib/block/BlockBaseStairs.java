package ryoryo.polishedlib.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IGetItemBlock;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class BlockBaseStairs extends BlockStairs implements IModelRegister, IGetItemBlock {

	public BlockBaseStairs(String name, IBlockState modelState) {
		super(modelState);
		String stairName = name + "_stairs";
		this.setUnlocalizedName(stairName);
		this.setRegistryName(stairName);

		RegistryHandler.register(this);
		RegistryHandler.register(createItemBlock().setRegistryName(stairName));
	}

	public BlockBaseStairs(IBlockState modelState) {
		this(modelState.getBlock().getUnlocalizedName().substring("tile.".length()), modelState);
	}

	@Override
	public ItemBlock createItemBlock() {
		return new ItemBlock(this);
	}

	@Override
	public void registerModels() {
		ModelHandler.registerBlockModel(this);
	}
}