package ryoryo.polishedlib.util.interfaces;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITooltipWiki
{
	public void addTooltipWiki(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag);
}