package ryoryo.polishedlib.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockBaseSlab extends BlockSlab {
	public BlockBaseSlab(Material material, String name, CreativeTabs tab, float hardness, float resistance, SoundType soundType) {
		super(material);
		this.setCreativeTab(tab);

		String newName = name;
		if(this.isDouble())
			newName = "double_" + name;

		this.setUnlocalizedName(newName);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(soundType);
	}
}