package ryoryo.polishedlib.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return this.getPickBlockImpl(state, target, world, pos, player);
	}

	protected abstract ItemStack getPickBlockImpl(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player);

	@Override
	public int damageDropped(IBlockState state) {
		return this.damageDroppedImpl(state);
	}

	protected abstract int damageDroppedImpl(IBlockState state);

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getStateFromMetaImpl(meta);
	}

	protected abstract IBlockState getStateFromMetaImpl(int meta);

	@Override
	public int getMetaFromState(IBlockState state) {
		return this.getMetaFromStateImpl(state);
	}

	protected abstract int getMetaFromStateImpl(IBlockState state);

	@Override
	protected BlockStateContainer createBlockState() {
		return this.createBlockStateImpl();
	}

	protected abstract BlockStateContainer createBlockStateImpl();

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		this.getSubBlocksImpl(tab, list);
	}

	@SideOnly(Side.CLIENT)
	protected abstract void getSubBlocksImpl(CreativeTabs tab, NonNullList<ItemStack> list);

	@Override
	public ItemBlock createItemBlock() {
		return this.createItemBlockImpl();
	}

	protected abstract ItemBlock createItemBlockImpl();

	@Override
	public void registerModels() {
		this.registerModelsImpl();
	}

	protected abstract void registerModelsImpl();
}