package ryoryo.polishedlib.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import ryoryo.polishedlib.util.enums.ToolType;
import ryoryo.polishedlib.util.handlers.ModelHandler;
import ryoryo.polishedlib.util.handlers.RegistryHandler;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

public class ItemBasePaxel extends ItemTool implements IModelRegister {

	/**
	 * tab = TOOLS
	 */
	public ItemBasePaxel(String name, ToolMaterial material, float damage, float speed) {
		super(damage, speed, material, ToolType.PAXEL_EFFECTIVE);
		this.setHarvestLevel(ToolType.SHOVEL.getToolClass(), material.getHarvestLevel());
		this.setHarvestLevel(ToolType.PICKAXE.getToolClass(), material.getHarvestLevel());
		this.setHarvestLevel(ToolType.AXE.getToolClass(), material.getHarvestLevel());
		this.setUnlocalizedName(name);
		this.setRegistryName(name);

		RegistryHandler.register(this);
	}

	/**
	 * damage = 1.0F
	 * speed = -2.8F
	 * tab = TOOLS
	 */
	public ItemBasePaxel(String name, ToolMaterial toolMaterial) {
		this(name, toolMaterial, 1.0F, -2.8F);
	}

	public ItemBasePaxel(String name, ToolMaterial toolMaterial, float damage, float speed, CreativeTabs tab) {
		this(name, toolMaterial, damage, speed);
		this.setCreativeTab(tab);
	}

	@Override
	public boolean canHarvestBlock(IBlockState state) {
		Block block = state.getBlock();
		Material material = state.getMaterial();

		//shovel
		if (block == Blocks.SNOW_LAYER || block == Blocks.SNOW)
			return true;

		//pickaxe
		if (block == Blocks.OBSIDIAN)
			return this.toolMaterial.getHarvestLevel() == 3;

		if (block == Blocks.DIAMOND_BLOCK || block == Blocks.DIAMOND_ORE)
			return this.toolMaterial.getHarvestLevel() >= 2;

		if (block == Blocks.EMERALD_ORE || block == Blocks.EMERALD_BLOCK)
			return this.toolMaterial.getHarvestLevel() >= 2;

		if (block == Blocks.GOLD_BLOCK || block == Blocks.GOLD_ORE)
			return this.toolMaterial.getHarvestLevel() >= 2;

		if (block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE)
			return this.toolMaterial.getHarvestLevel() >= 2;

		if (block == Blocks.IRON_BLOCK || block == Blocks.IRON_ORE)
			return this.toolMaterial.getHarvestLevel() >= 1;

		if (block == Blocks.LAPIS_BLOCK || block == Blocks.LAPIS_ORE)
			return this.toolMaterial.getHarvestLevel() >= 1;

		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		Material material = state.getMaterial();

		//pickaxe
		if (material == Material.IRON || material == Material.ANVIL || material == Material.ROCK)
			return this.efficiency;

		//axe
		if (material == Material.WOOD || material == Material.PLANTS || material == Material.VINE)
			return this.efficiency;

		return super.getDestroySpeed(stack, state);
	}

	@Override
	public void registerModels() {
		ModelHandler.registerItemModel(this);
	}
}