package ryoryo.polishedlib.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ryoryo.polishedlib.PolishedLib;

public class RegistryUtils {
	private String modId = null;

	public RegistryUtils(String modId) {
		this.modId = modId;
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 * 
	 * @param name
	 * @param output
	 * @param params
	 */
	public void addRecipe(String name, @Nonnull ItemStack output, Object... params) {
		addShapedRecipe(name, output, params);
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 * 
	 * @param name
	 * @param output
	 * @param params
	 */
	public void addShapedRecipe(String name, @Nonnull ItemStack output, Object... params) {
		ResourceLocation location = Utils.makeModLocation(this.modId, name);
		ShapedOreRecipe ret = new ShapedOreRecipe(location, output, params);
		ret.setRegistryName(location);
		for(Ingredient ing : ret.getIngredients()) {
			if(ing instanceof OreIngredient && ing.getMatchingStacks().length < 1)
				return;
		}
		ForgeRegistries.RECIPES.register(ret);
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるようにするやつのshapeless版
	 * 
	 * @param name
	 * @param output
	 * @param params
	 */
	public void addShapelessRecipe(String name, @Nonnull ItemStack output, Object... params) {
		ResourceLocation location = Utils.makeModLocation(this.modId, name);
		ShapelessOreRecipe ret = new ShapelessOreRecipe(location, output, params);
		ret.setRegistryName(location);
		for(Ingredient ing : ret.getIngredients()) {
			if(ing instanceof OreIngredient && ing.getMatchingStacks().length < 1)
				return;
		}
		ForgeRegistries.RECIPES.register(ret);
	}

	public void addRecipeAutoName(@Nonnull ItemStack output, Object... params) {
		addShapedRecipeAutoName(output, params);
	}

	public void addShapedRecipeAutoName(@Nonnull ItemStack output, Object... params) {
		addRecipe(new RecipeNameBuilder(output, params).build(), output, params);
	}

	public void addShapelessRecipeAutoName(@Nonnull ItemStack output, Object... params) {
		addShapelessRecipe(new RecipeNameBuilder(output, params).build(), output, params);
	}

	/**
	 * 斧レシピの登録 名前の前に"axe_"と足される
	 *
	 * @param output
	 * @param material
	 */
	public void addRecipeAxe(String name, ItemStack output, ItemStack material) {
		addRecipe("axe_" + name, output, "##", "#s", " s", 's', Items.STICK, '#', material);
	}

	/**
	 * つるはしレシピの登録 名前の前に"pickaxe_"と足される
	 *
	 * @param output
	 * @param material
	 */
	public void addRecipePickaxe(String name, ItemStack output, ItemStack material) {
		addRecipe("pickaxe_" + name, output, "###", " s ", " s ", 's', Items.STICK, '#', material);
	}

	/**
	 * ショベルレシピの登録 名前の前に"shovel_"と足される
	 *
	 * @param output
	 * @param material
	 */
	public void addRecipeShovel(String name, ItemStack output, ItemStack material) {
		addRecipe("shovel_" + name, output, "#", "s", "s", 's', Items.STICK, '#', material);
	}

	/**
	 * ツール3種レシピの一括登録（斧、つるはし、ショベル） 名前の前にそれぞれ"axe_"、"pickaxe_"、"shovel_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeTools(String name, ItemStack material, ItemStack... output) {
		assert (output.length == 3) : "完成品リストのサイズは3である必要があります";
		addRecipeAxe(name, material, output[0]);
		addRecipePickaxe(name, material, output[1]);
		addRecipeShovel(name, material, output[2]);
	}

	/**
	 * 剣レシピの登録 名前の前に"sword_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeSword(String name, ItemStack material, ItemStack output) {
		addRecipe("sword_" + name, output, "#", "#", "s", 's', Items.STICK, '#', material);
	}

	/**
	 * 防具4種レシピの一括登録（ヘルメット、チェストプレート、レギンス、ブーツ）
	 * 名前の前にそれぞれ"helmet_"、"chestplate_"、"leggings_"、"boots_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeArmor(String name, ItemStack material, ItemStack... output) {
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
	public void addRecipeAllToolsAndArmor(String name, ItemStack material, ItemStack... output) {
		assert (output.length == 8) : "完成品リストのサイズは8である必要があります";
		addRecipeTools(name, material, Arrays.copyOfRange(output, 0, 2));
		addRecipeSword(name, material, output[3]);
		addRecipeArmor(name, material, Arrays.copyOfRange(output, 4, 8));
	}

	/**
	 * 階段レシピ登録 名前の前に"stairs_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addRecipeStairs(String name, Block output, ItemStack material) {
		int quantity = 4;
		if(ModCompat.COMPAT_QUARK)
			quantity = 8;
		else
			PolishedLib.LOGGER.info("Quark isn't loaded.");
		addRecipe("stairs_" + name, new ItemStack(output, quantity), "#  ", "## ", "###", '#', material);
	}

	/**
	 * 階段を手元で作れるようにレシピ登録 名前の前に"stairs_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public void addMiniRecipeStairs(String name, Block output, ItemStack material) {
		int quantity = 2;
		if(ModCompat.COMPAT_QUARK)
			quantity = 4;
		else
			PolishedLib.LOGGER.info("Quark isn't loaded.");
		addRecipe("stairs_" + name, new ItemStack(output, quantity), "# ", "##", '#', material);
	}

	/**
	 * 壁のレシピ登録 名前の前に"wall_"と足される
	 * 
	 * @param name
	 * @param output
	 * @param material
	 */
	public void addRecipeWall(String name, Block output, ItemStack material) {
		addRecipe("wall_" + name, new ItemStack(output, 6), "MMM", "MMM", 'M', material);
	}

	/**
	 * レシピ削除
	 *
	 * @param output
	 */
	public static void removeRecipe(ItemStack output) {
		Iterator<IRecipe> remover = ForgeRegistries.RECIPES.iterator();

		while(remover.hasNext()) {
			ItemStack itemStack = remover.next().getRecipeOutput();

			if(itemStack != null && output.isItemEqual(itemStack))
				remover.remove();
		}
	}

	// TODO 精錬レシピ削除
	/**
	 * 精錬レシピの限定的な削除
	 *
	 * @param input
	 * @param output
	 */
	public static void removeSmeltingRecipe(ItemStack input, ItemStack output) {
		Map<ItemStack, ItemStack> remover = FurnaceRecipes.instance().getSmeltingList();
		remover.remove(input, output);
	}

	public static void removeSmeltingRecipe(ItemStack output) {
		Map<ItemStack, ItemStack> remover = FurnaceRecipes.instance().getSmeltingList();

		// for(Entry<ItemStack, ItemStack> entry : remover.entrySet())
		// {
		// if(output.isItemEqual((ItemStack) entry.getValue()))
		// {
		// remover.remove((ItemStack) entry.getKey(), (ItemStack)
		// entry.getValue());
		// }
		// }

		remover.entrySet().stream().filter(entry -> output.isItemEqual((ItemStack) entry.getValue()))
				.forEach(entry -> remover.remove((ItemStack) entry.getKey(), (ItemStack) entry.getValue()));
	}

	/**
	 * 燃料登録を簡単に見やすく
	 *
	 * @param burnTime
	 * @param fuels
	 */
	public static void addFuel(int burnTime, ItemStack... fuels) {
		GameRegistry.registerFuelHandler(fuel -> {
			for(ItemStack toAdd : fuels) {
				if(fuel.isItemEqual(toAdd))
					return burnTime;
			}

			return 0;
		});
	}

	/**
	 * ブロック登録
	 *
	 * @param block
	 * @param name
	 */
	public void registerBlock(Block block, String name) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));

		if(Utils.isClient())
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	/**
	 * ブロック登録 アイテムブロックを別に作成時用
	 *
	 * @param block
	 * @param itemBlock
	 * @param name
	 */
	public void registerBlock(Block block, ItemBlock itemBlock, String name) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(Utils.isClient())
			ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	/**
	 * ブロック登録 meta違いで名前も変えたいとき用
	 *
	 * @param block
	 * @param itemBlock
	 * @param location
	 * @param names
	 */
	public void registerBlock(Block block, ItemBlock itemBlock, String name, String[] names) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(Utils.isClient()) {
			for(int i = 0; i < names.length; i++)
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(block.getRegistryName().toString() + "_" + names[i], "inventory"));
		}
	}

	/**
	 * ブロック登録 meta違いで数字で違う名前にする用
	 *
	 * @param block
	 * @param itemBlock
	 * @param location
	 * @param meta
	 */
	public void registerBlock(Block block, ItemBlock itemBlock, String name, int meta) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(Utils.isClient()) {
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
	public void registerItem(Item item, String name) {
		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));

		if(Utils.isClient())
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	/**
	 * アイテム登録 meta違いでテクスチャを変える用
	 *
	 * @param item
	 * @param location
	 * @param names
	 */
	public void registerItem(Item item, String name, String[] names) {
		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));

		if(Utils.isClient()) {
			for(int i = 0; i < names.length; i++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName().toString() + "_" + names[i], "inventory"));
		}
	}

	/**
	 * アイテム登録 meta違いだけど同じテクスチャ用
	 *
	 * @param item
	 * @param name
	 * @param meta
	 */
	public void registerItem(Item item, String name, int meta) {
		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));

		if(Utils.isClient()) {
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
	public void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(Utils.makeModLocation(this.modId, entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	/**
	 * サブブロック登録を簡略化
	 * 
	 * @param block
	 * @param firstMeta
	 * @param endMeta
	 * @param tab
	 * @param list
	 */
	public static void registerSubBlocks(Block block, int firstMeta, int endMeta, CreativeTabs tab, NonNullList<ItemStack> list) {
		if(tab == block.getCreativeTabToDisplayOn()) {
			for(int i = firstMeta; i < endMeta; i++) {
				list.add(new ItemStack(block, 1, i));
			}
		}
	}

	/**
	 * サブブロック登録を簡略化 0から始める
	 * 
	 * @param block
	 * @param meta
	 * @param tab
	 * @param list
	 */
	public static void registerSubBlocks(Block block, int meta, CreativeTabs tab, NonNullList<ItemStack> list) {
		registerSubBlocks(block, 0, meta, tab, list);
	}

	/**
	 * サブアイテム登録を簡略化
	 * 
	 * @param item
	 * @param firstMeta
	 * @param endMeta
	 * @param tab
	 * @param list
	 */
	public static void registerSubItems(Item item, int firstMeta, int endMeta, CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == item.getCreativeTab()) {
			for(int i = firstMeta; i < endMeta; i++) {
				items.add(new ItemStack(item, 1, i));
			}
		}
	}

	/**
	 * サブアイテム登録を簡略化 0から始める
	 * 
	 * @param item
	 * @param meta
	 * @param tab
	 * @param list
	 */
	public static void registerSubItems(Item item, int meta, CreativeTabs tab, NonNullList<ItemStack> items) {
		registerSubItems(item, 0, meta, tab, items);
	}

	/**
	 * IBlockColorを実装しているブロック登録
	 *
	 * @param blockColor
	 * @param blocks
	 */
	@SideOnly(Side.CLIENT)
	public static void registerBlockColor(IBlockColor blockColor, Block... blocks) {
		if(Utils.isClient())
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blockColor, blocks);
	}

	/**
	 * IItemColorを実装しているアイテム登録
	 *
	 * @param itemColor
	 * @param items
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemColor(IItemColor itemColor, Item... items) {
		if(Utils.isClient())
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, items);
	}

	/**
	 * IItemColorを実装しているアイテムブロック登録
	 *
	 * @param itemColor
	 * @param blocks
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemBlockColor(IItemColor itemColor, Block... blocks) {
		if(Utils.isClient())
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, blocks);
	}

	/**
	 * EntityRender登録
	 *
	 * @param entityClass
	 * @param renderFactory
	 */
	@SideOnly(Side.CLIENT)
	public static <T extends Entity> void registerEntityRendering(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
		if(Utils.isClient())
			RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}

	/**
	 * キー登録
	 * 
	 * @param key
	 */
	@SideOnly(Side.CLIENT)
	public static void registerKeyBinding(KeyBinding key) {
		if(Utils.isClient())
			ClientRegistry.registerKeyBinding(key);
	}

	/**
	 * EnumParticleTypesへの登録を簡略化
	 *
	 * @param enumName
	 * @param particleName
	 * @param id
	 * @param shouldIgnoreRange
	 * @return
	 */
	public static EnumParticleTypes registerParticleType(String enumName, String particleName, int id, boolean shouldIgnoreRange) {
		Class<?>[] particleParams = { String.class, int.class, boolean.class };

		return EnumHelper.addEnum(EnumParticleTypes.class, enumName, particleParams, particleName, id, shouldIgnoreRange);
	}
}