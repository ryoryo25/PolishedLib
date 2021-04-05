package ryoryo.polishedlib.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockBaseMeta extends BlockBase {
	public BlockBaseMeta(String name, Material material) {
		super(name, material);
	}

	public BlockBaseMeta(String name, Material material, CreativeTabs tab) {
		super(name, material, tab);
	}

	public BlockBaseMeta(String name, Material material, CreativeTabs tab, SoundType soundType) {
		super(name, material, tab, soundType);
	}

	//	@Override
	//	public abstract ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player);
	//
	//	@Override
	//	public abstract int damageDropped(IBlockState state);
	//
	//	@Override
	//	public abstract IBlockState getStateFromMeta(int meta);
	//
	//	@Override
	//	public abstract int getMetaFromState(IBlockState state);
	//
	//	@Override
	//	protected abstract BlockStateContainer createBlockState();
	//
	//	@Override
	//	@SideOnly(Side.CLIENT)
	//	public abstract void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list);
	//
	//	@Override
	//	public abstract ItemBlock createItemBlock();
	//
	//	@Override
	//	public abstract void registerModels();
}