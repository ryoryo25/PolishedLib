package ryoryo.polishedlib.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IGetItemBlock;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class BlockBase extends Block implements IModelRegister, IGetItemBlock {
	public BlockBase(String name, Material material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);

		RegistryHandler.register(this);
		RegistryHandler.register(createItemBlock().setRegistryName(name));
	}

	public BlockBase(String name, Material material, CreativeTabs tab) {
		this(name, material);
		this.setCreativeTab(tab);
	}

	public BlockBase(String name, Material material, CreativeTabs tab, SoundType soundType) {
		this(name, material, tab);
		this.setSoundType(soundType);
	}

	@Override
	public ItemBlock createItemBlock() {
		return new ItemBlock(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelHandler.registerBlockModel(this);
	}
}