package ryoryo.polishedlib.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBaseDoor extends BlockDoor {
	private boolean wooden;
	private boolean openable;

	public BlockBaseDoor(Material material, String name, CreativeTabs tab, SoundType sound, boolean wooden, boolean openable) {
		super(material);
		this.setUnlocalizedName(name + "_door");
		this.setCreativeTab(tab);
		this.setSoundType(sound);
		this.wooden = wooden;
		this.openable = openable;
	}

	public BlockBaseDoor(String name, CreativeTabs tab) {
		this(Material.WOOD, name, tab, SoundType.WOOD, true, true);
	}

	public BlockBaseDoor(Material material, String name, CreativeTabs tab, SoundType sound) {
		this(material, name, tab, sound, false, false);
	}

	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : Item.getItemFromBlock(this);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}

	protected int getCloseSound() {
		return this.wooden ? 1012 : 1011;
	}

	protected int getOpenSound() {
		return this.wooden ? 1006 : 1005;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!this.openable) {
			return false;
		}
		else {
			BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
			IBlockState iblockstate = pos.equals(blockpos) ? state : world.getBlockState(blockpos);

			if(iblockstate.getBlock() != this) {
				return false;
			}
			else {
				state = iblockstate.cycleProperty(OPEN);
				world.setBlockState(blockpos, state, 10);
				world.markBlockRangeForRenderUpdate(blockpos, pos);
				world.playEvent(player, state.getValue(OPEN).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
				return true;
			}
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			BlockPos posd = pos.down();
			IBlockState stated = world.getBlockState(posd);

			if(stated.getBlock() != this) {
				world.setBlockToAir(pos);
			}
			else if(block != this) {
				stated.neighborChanged(world, posd, block, fromPos);
			}
		}
		else {
			boolean flag1 = false;
			BlockPos posu = pos.up();
			IBlockState stateu = world.getBlockState(posu);

			if(stateu.getBlock() != this) {
				world.setBlockToAir(pos);
				flag1 = true;
			}

			if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP)) {
				world.setBlockToAir(pos);
				flag1 = true;

				if(stateu.getBlock() == this) {
					world.setBlockToAir(posu);
				}
			}

			if(flag1) {
				if(!world.isRemote) {
					this.dropBlockAsItem(world, pos, state, 0);
				}
			}
			else {
				boolean flag = world.isBlockPowered(pos) || world.isBlockPowered(posu);

				if(block != this && (flag || block.getDefaultState().canProvidePower()) && flag != stateu.getValue(POWERED).booleanValue()) {
					world.setBlockState(posu, stateu.withProperty(POWERED, Boolean.valueOf(flag)), 2);

					if(flag != state.getValue(OPEN).booleanValue()) {
						world.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(flag)), 2);
						world.markBlockRangeForRenderUpdate(pos, pos);
						world.playEvent((EntityPlayer) null, flag ? this.getOpenSound() : this.getCloseSound(), pos, 0);
					}
				}
			}
		}
	}
}