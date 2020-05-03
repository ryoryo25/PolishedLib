package ryoryo.polishedlib.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockBaseMeta extends BlockBase {
	public BlockBaseMeta(Material material, String name) {
		super(material, name);
	}

	public BlockBaseMeta(Material material, String name, CreativeTabs tab) {
		super(material, name, tab);
	}

	public BlockBaseMeta(Material material, String name, CreativeTabs tab, SoundType soundType) {
		super(material, name, tab, soundType);
	}

	@Override
	public abstract ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player);

	@Override
	public abstract int damageDropped(IBlockState state);

	@Override
	public abstract IBlockState getStateFromMeta(int meta);

	@Override
	public abstract int getMetaFromState(IBlockState state);

	@Override
	protected abstract BlockStateContainer createBlockState();

	@Override
	@SideOnly(Side.CLIENT)
	public abstract void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list);
}