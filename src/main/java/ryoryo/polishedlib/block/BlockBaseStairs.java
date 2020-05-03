package ryoryo.polishedlib.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockBaseStairs extends BlockStairs {
	public BlockBaseStairs(IBlockState modelState) {
		super(modelState);
		this.setUnlocalizedName(modelState.getBlock().getUnlocalizedName().substring(5) + "_stairs");
	}

	public BlockBaseStairs(IBlockState modelState, String name) {
		super(modelState);
		this.setUnlocalizedName(name + "_stairs");
	}
}