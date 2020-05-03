package ryoryo.polishedlib.item;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.interfaces.ITooltipWiki;

public class ItemBase extends Item {
	public ItemBase(String name, CreativeTabs tab) {
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		if(this instanceof ITooltipWiki) {
			if(GuiScreen.isShiftKeyDown()) {
				((ITooltipWiki) this).addTooltipWiki(stack, world, tooltip, flag);
			}
			else {
				tooltip.add("Hold " + TextFormatting.BLUE + TextFormatting.ITALIC + "Shift" + TextFormatting.RESET + TextFormatting.GRAY + " for detail.");
			}
		}
	}
}