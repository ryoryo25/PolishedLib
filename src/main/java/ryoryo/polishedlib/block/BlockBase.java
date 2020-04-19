package ryoryo.polishedlib.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBase extends Block
{
	public BlockBase(Material material, String name)
	{
		super(material);
		this.setUnlocalizedName(name);
	}

	public BlockBase(Material material, String name, CreativeTabs tab)
	{
		this(material, name);
		this.setCreativeTab(tab);
	}

	public BlockBase(Material material, String name, CreativeTabs tab, SoundType soundType)
	{
		this(material, name, tab);
		this.setSoundType(soundType);
	}
}