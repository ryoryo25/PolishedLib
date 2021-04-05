package ryoryo.polishedlib.util.interfaces;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockColorProvider {

	int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex);
}