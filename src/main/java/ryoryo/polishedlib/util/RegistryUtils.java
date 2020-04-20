package ryoryo.polishedlib.util;

import java.util.Arrays;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ryoryo.polishedlib.PolishedLib;

public class RegistryUtils
{
	private String modId = null;

	public RegistryUtils(String modId)
	{
		this.modId = modId;
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 * @param name
	 * @param output
	 * @param params
	 */
	public void addRecipe(String name, @Nonnull ItemStack output, Object... params)
	{
		addShapedRecipe(name, output, params);
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 * @param name
	 * @param output
	 * @param params
	 */
	public void addShapedRecipe(String name, @Nonnull ItemStack output, Object... params)
	{
		ResourceLocation location = Utils.makeModLocation(this.modId, name);
		ShapedOreRecipe ret = new ShapedOreRecipe(location, output, params);
		ret.setRegistryName(location);
		for(Ingredient ing : ret.getIngredients())
		{
			if(ing instanceof OreIngredient && ing.getMatchingStacks().length < 1)
				return;
		}
		ForgeRegistries.RECIPES.register(ret);
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるようにするやつのshapeless版
	 * @param name
	 * @param output
	 * @param params
	 */
	public void addShapelessRecipe(String name, @Nonnull ItemStack output, Object... params)
	{
		ResourceLocation location = Utils.makeModLocation(this.modId, name);
		ShapelessOreRecipe ret = new ShapelessOreRecipe(location, output, params);
		ret.setRegistryName(location);
		for(Ingredient ing : ret.getIngredients())
		{
			if(ing instanceof OreIngredient && ing.getMatchingStacks().length < 1)
				return;
		}
		ForgeRegistries.RECIPES.register(ret);
	}

	public void addRecipeAutoName(@Nonnull ItemStack output, Object... params)
	{
		addShapedRecipeAutoName(output, params);
	}

	public void addShapedRecipeAutoName(@Nonnull ItemStack output, Object... params)
	{
		addRecipe(new RecipeNameBuilder(output, params).build(), output, params);
	}

	public void addShapelessRecipeAutoName(@Nonnull ItemStack output, Object... params)
	{
		addShapelessRecipe(new RecipeNameBuilder(output, params).build(), output, params);
	}

	/**
	 * 斧レシピの登録
	 * 名前の前に"axe_"と足される
	 *
	 * @param output
	 * @param material
	 */
	public void addRecipeAxe(String name, ItemStack output, ItemStack material)
	{
		addRecipe("axe_" + name, output, "##", "#s", " s", 's', Items.STICK, '#', material);
	}

	/**
	 * つるはしレシピの登録
	 * 名前の前に"pickaxe_"と足される
	 *
	 * @param output
	 * @param material
	 */
	public void addRecipePickaxe(String name, ItemStack output, ItemStack material)
	{
		addRecipe("pickaxe_" + name, output, "###", " s ", " s ", 's', Items.STICK, '#', material);
	}

	/**
	 * ショベルレシピの登録
	 * 名前の前に"shovel_"と足される
	 *
	 * @param output
	 * @param material
	 */
	public void addRecipeShovel(String name, ItemStack output, ItemStack material)
	{
		addRecipe("shovel_" + name, output, "#", "s", "s", 's', Items.STICK, '#', material);
	}

	/**
	 * ツール3種レシピの一括登録（斧、つるはし、ショベル）
	 * 名前の前にそれぞれ"axe_"、"pickaxe_"、"shovel_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeTools(String name, ItemStack material, ItemStack... output)
	{
		assert(output.length == 3) : "完成品リストのサイズは3である必要があります";
		addRecipeAxe(name, material, output[0]);
		addRecipePickaxe(name, material, output[1]);
		addRecipeShovel(name, material, output[2]);
	}

	/**
	 * 剣レシピの登録
	 * 名前の前に"sword_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeSword(String name, ItemStack material, ItemStack output)
	{
		addRecipe("sword_" + name, output, "#", "#", "s", 's', Items.STICK, '#', material);
	}

	/**
	 * 防具4種レシピの一括登録（ヘルメット、チェストプレート、レギンス、ブーツ）
	 * 名前の前にそれぞれ"helmet_"、"chestplate_"、"leggings_"、"boots_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeArmor(String name, ItemStack material, ItemStack... output)
	{
		assert (output.length == 4) : "完成品リストのサイズは4である必要があります";
		addRecipe("helmet_" + name, output[0], "###", "# #", '#', material);
		addRecipe("chestplate_" + name, output[1], "# #", "###", "###", '#', material);
		addRecipe("leggings_" + name, output[2], "###", "# #", "# #", '#', material);
		addRecipe("boots_" + name, output[3], "# #", "# #", '#', material);
	}

	/**
	 * ツール3種、剣、防具4種レシピの一括登録
	 * 名前の前にそれぞれ"axe_"、"pickaxe_"、"shovel_"、"sword_"、"helmet_"、"chestplate_"、"leggings_"、"boots_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeAllToolsAndArmor(String name, ItemStack material, ItemStack... output)
	{
		assert (output.length == 8) : "完成品リストのサイズは8である必要があります";
		addRecipeTools(name, material, Arrays.copyOfRange(output, 0, 2));
		addRecipeSword(name, material, output[3]);
		addRecipeArmor(name, material, Arrays.copyOfRange(output, 4, 8));
	}

	/**
	 * 階段レシピ登録
	 * 名前の前に"stairs_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeStairs(String name, Block output, ItemStack material)
	{
		int quantity = 4;
		if(ModCompat.COMPAT_QUARK)
			quantity = 8;
		else
			PolishedLib.LOGGER.info("Quark isn't loaded.");
		addRecipe("stairs_" + name, new ItemStack(output, quantity), "#  ", "## ", "###", '#', material);
	}

	/**
	 * 階段を手元で作れるようにレシピ登録
	 * 名前の前に"stairs_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addMiniRecipeStairs(String name, Block output, ItemStack material)
	{
		int quantity = 2;
		if(ModCompat.COMPAT_QUARK)
			quantity = 4;
		else
			PolishedLib.LOGGER.info("Quark isn't loaded.");
		addRecipe("stairs_" + name, new ItemStack(output, quantity), "# ", "##", '#', material);
	}

	/**
	 * 壁のレシピ登録
	 * 名前の前に"wall_"と足される
	 * @param name
	 * @param output
	 * @param material
	 */
	public void addRecipeWall(String name, Block output, ItemStack material)
	{
		addRecipe("wall_" + name, new ItemStack(output, 6), "MMM", "MMM", 'M', material);
	}

	/**
	 * ブロック登録
	 *
	 * @param block
	 * @param name
	 */
	public void registerBlock(Block block, String name)
	{
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));

		if(Utils.isClient())
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	/**
	 * ブロック登録
	 * アイテムブロックを別に作成時用
	 *
	 * @param block
	 * @param itemBlock
	 * @param name
	 */
	public void registerBlock(Block block, ItemBlock itemBlock, String name)
	{
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(Utils.isClient())
			ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	/**
	 * ブロック登録
	 * meta違いで名前も変えたいとき用
	 *
	 * @param block
	 * @param itemBlock
	 * @param location
	 * @param names
	 */
	public void registerBlock(Block block, ItemBlock itemBlock, String name, String[] names)
	{
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(Utils.isClient())
		{
			for(int i = 0; i < names.length; i++)
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(block.getRegistryName().toString() + "_" + names[i], "inventory"));
		}
	}

	/**
	 * ブロック登録
	 * meta違いで数字で違う名前にする用
	 *
	 * @param block
	 * @param itemBlock
	 * @param location
	 * @param meta
	 */
	public void registerBlock(Block block, ItemBlock itemBlock, String name, int meta)
	{
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(Utils.isClient())
		{
			for(int i = 0; i < meta; i++)
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(block.getRegistryName().toString() + "_" + i, "inventory"));
		}
	}

	/**
	 * ノーマルアイテム登録
	 *
	 * @param item
	 * @param name
	 */
	public void registerItem(Item item, String name)
	{
		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));

		if(Utils.isClient())
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	/**
	 * アイテム登録
	 * meta違いでテクスチャを変える用
	 *
	 * @param item
	 * @param location
	 * @param names
	 */
	public void registerItem(Item item, String name, String[] names)
	{
		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));

		if(Utils.isClient())
		{
			for(int i = 0; i < names.length; i++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName().toString() + "_" + names[i], "inventory"));
		}
	}

	/**
	 * アイテム登録
	 * meta違いだけど同じテクスチャ用
	 *
	 * @param item
	 * @param name
	 * @param meta
	 */
	public void registerItem(Item item, String name, int meta)
	{
		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));

		if(Utils.isClient())
		{
			for(int i = 0; i < meta; i++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
		}
	}

	/**
	 * デフォルトの登録処理を簡略化
	 *
	 * @param entityClass
	 * @param entityName
	 * @param id
	 * @param mod
	 * @param trackingRange
	 * @param updateFrequency
	 * @param sendsVelocityUpdates
	 */
	public void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(Utils.makeModLocation(this.modId, entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}