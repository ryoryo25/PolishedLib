package ryoryo.polishedlib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBaseWall extends BlockWall {
	public BlockBaseWall(Block baseBlock) {
		super(baseBlock);
		this.setUnlocalizedName(baseBlock.getUnlocalizedName().substring(5) + "_wall");
	}

	public BlockBaseWall(Block baseBlock, String name) {
		super(baseBlock);
		this.setUnlocalizedName(name + "_wall");
	}

	private boolean canConnectTo(IBlockAccess world, BlockPos pos) {
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();
		return block == Blocks.BARRIER ? false : (block != this && !(block instanceof BlockFenceGate) && !(block instanceof BlockWall) ? (block.getMaterial(iblockstate).isOpaque() && iblockstate.isFullCube() ? block.getMaterial(iblockstate) != Material.GOURD : false) : true);
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		boolean flag = this.canConnectTo(world, pos.north());
		boolean flag1 = this.canConnectTo(world, pos.east());
		boolean flag2 = this.canConnectTo(world, pos.south());
		boolean flag3 = this.canConnectTo(world, pos.west());
		boolean flag4 = flag && !flag1 && flag2 && !flag3 || !flag && flag1 && !flag2 && flag3;
		return state.withProperty(UP, Boolean.valueOf(!flag4 || !world.isAirBlock(pos.up()))).withProperty(NORTH, Boolean.valueOf(flag)).withProperty(EAST, Boolean.valueOf(flag1)).withProperty(SOUTH, Boolean.valueOf(flag2)).withProperty(WEST, Boolean.valueOf(flag3));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
	}
}