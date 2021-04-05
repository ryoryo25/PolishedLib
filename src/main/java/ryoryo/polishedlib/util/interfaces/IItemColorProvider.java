package ryoryo.polishedlib.util.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemColorProvider {

	int colorMultiplier(ItemStack stack, int tintIndex);
}