package ryoryo.polishedlib.util.handlers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import ryoryo.polishedlib.PolishedLib;
import ryoryo.polishedlib.util.ModCompat;
import ryoryo.polishedlib.util.RecipeNameBuilder;

public class RecipeHandler {

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 *
	 * @param name
	 * @param output
	 * @param params
	 */
	public static void addRecipe(String name, @Nonnull ItemStack output, Object... params) {
		addShapedRecipe(name, output, params);
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 *
	 * @param name
	 * @param output
	 * @param params
	 */
	public static void addShapedRecipe(String name, @Nonnull ItemStack output, Object... params) {
		ResourceLocation location = GameData.checkPrefix(name);
		ShapedOreRecipe ret = new ShapedOreRecipe(location, output, params);
		ret.setRegistryName(location);
		for (Ingredient ing : ret.getIngredients()) {
			if (ing instanceof OreIngredient && ing.getMatchingStacks().length < 1)
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
	public static void addShapelessRecipe(String name, @Nonnull ItemStack output, Object... params) {
		ResourceLocation location = GameData.checkPrefix(name);
		ShapelessOreRecipe ret = new ShapelessOreRecipe(location, output, params);
		ret.setRegistryName(location);
		for (Ingredient ing : ret.getIngredients()) {
			if (ing instanceof OreIngredient && ing.getMatchingStacks().length < 1)
				return;
		}
		ForgeRegistries.RECIPES.register(ret);
	}

	public static void addRecipeAutoName(@Nonnull ItemStack output, Object... params) {
		addShapedRecipeAutoName(output, params);
	}

	public static void addShapedRecipeAutoName(@Nonnull ItemStack output, Object... params) {
		addRecipe(new RecipeNameBuilder(output, params).build(), output, params);
	}

	public static void addShapelessRecipeAutoName(@Nonnull ItemStack output, Object... params) {
		addShapelessRecipe(new RecipeNameBuilder(output, params).build(), output, params);
	}

	/**
	 * 斧レシピの登録 名前の前に"axe_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param output
	 * @param material
	 */
	public static void addRecipeAxe(String name, ItemStack output, Object material) {
		addRecipe("axe_" + name, output, "##", "#s", " s", 's', Items.STICK, '#', material);
	}

	/**
	 * つるはしレシピの登録 名前の前に"pickaxe_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param output
	 * @param material
	 */
	public static void addRecipePickaxe(String name, ItemStack output, Object material) {
		addRecipe("pickaxe_" + name, output, "###", " s ", " s ", 's', Items.STICK, '#', material);
	}

	/**
	 * ショベルレシピの登録 名前の前に"shovel_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param output
	 * @param material
	 */
	public static void addRecipeShovel(String name, ItemStack output, Object material) {
		addRecipe("shovel_" + name, output, "#", "s", "s", 's', Items.STICK, '#', material);
	}

	/**
	 * ツール3種レシピの一括登録（斧、つるはし、ショベル） 名前の前にそれぞれ"axe_"、"pickaxe_"、"shovel_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param material
	 * @param output
	 */
	public static void addRecipeTools(String name, Object material, ItemStack... output) {
		assert (output.length == 3) : "完成品リストのサイズは3である必要があります";
		addRecipeAxe(name, output[0], material);
		addRecipePickaxe(name, output[1], material);
		addRecipeShovel(name, output[2], material);
	}

	/**
	 * 剣レシピの登録 名前の前に"sword_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param name
	 * @param output
	 * @param material
	 */
	public static void addRecipeSword(String name, ItemStack output, Object material) {
		addRecipe("sword_" + name, output, "#", "#", "s", 's', Items.STICK, '#', material);
	}

	/**
	 * 防具4種レシピの一括登録（ヘルメット、チェストプレート、レギンス、ブーツ）
	 * 名前の前にそれぞれ"helmet_"、"chestplate_"、"leggings_"、"boots_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param material
	 * @param output
	 */
	public static void addRecipeArmor(String name, Object material, ItemStack... output) {
		assert (output.length == 4) : "完成品リストのサイズは4である必要があります";
		addRecipe("helmet_" + name, output[0], "###", "# #", '#', material);
		addRecipe("chestplate_" + name, output[1], "# #", "###", "###", '#', material);
		addRecipe("leggings_" + name, output[2], "###", "# #", "# #", '#', material);
		addRecipe("boots_" + name, output[3], "# #", "# #", '#', material);
	}

	/**
	 * ツール3種、剣、防具4種レシピの一括登録
	 * 名前の前にそれぞれ"axe_"、"pickaxe_"、"shovel_"、"sword_"、"helmet_"、"chestplate_"、"leggings_"、"boots_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param material
	 * @param output
	 */
	public static void addRecipeAllToolsAndArmor(String name, Object material, ItemStack... output) {
		assert (output.length == 8) : "完成品リストのサイズは8である必要があります";
		addRecipeTools(name, material, Arrays.copyOfRange(output, 0, 2));
		addRecipeSword(name, output[3], material);
		addRecipeArmor(name, material, Arrays.copyOfRange(output, 4, 8));
	}

	/**
	 * 階段レシピ登録 名前の前に"stairs_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param material
	 * @param output
	 */
	public static void addRecipeStairs(String name, Block output, Object material) {
		int quantity = 4;
		if (ModCompat.COMPAT_QUARK) {
			quantity = 8;
			PolishedLib.LOGGER.info("Quark is loaded.");
		}

		addRecipe("stairs_" + name, new ItemStack(output, quantity), "#  ", "## ", "###", '#', material);
	}

	/**
	 * 階段を手元で作れるようにレシピ登録 名前の前に"stairs_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param material
	 * @param output
	 */
	public static void addMiniRecipeStairs(String name, Block output, Object material) {
		int quantity = 2;
		if (ModCompat.COMPAT_QUARK) {
			quantity = 4;
			PolishedLib.LOGGER.info("Quark is loaded.");
		}

		addRecipe("stairs_" + name, new ItemStack(output, quantity), "# ", "##", '#', material);
	}

	/**
	 * 壁のレシピ登録 名前の前に"wall_"と足される
	 * 鉱石辞書名にも対応
	 *
	 * @param name
	 * @param output
	 * @param material
	 */
	public static void addRecipeWall(String name, Block output, Object material) {
		addRecipe("wall_" + name, new ItemStack(output, 6), "MMM", "MMM", 'M', material);
	}

	/**
	 * レシピ削除
	 *
	 * @param output
	 */
	public static void removeRecipe(ItemStack output) {
		Iterator<IRecipe> remover = ForgeRegistries.RECIPES.iterator();

		while (remover.hasNext()) {
			ItemStack stack = remover.next().getRecipeOutput();

			if (!stack.isEmpty() && output.isItemEqual(stack))
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
				.forEach(entry -> remover.remove(entry.getKey(), entry.getValue()));
	}

	/**
	 * 燃料登録を簡単に見やすく
	 *
	 * @param burnTime
	 * @param fuels
	 */
	public static void addFuel(int burnTime, ItemStack... fuels) {
		GameRegistry.registerFuelHandler(stack -> {
			for (ItemStack fuel : fuels) {
				if (isItemEqual(stack, fuel)) {
					return burnTime;
				}
			}

			return 0;
		});
	}

	private static boolean isItemEqual(ItemStack self, ItemStack other) {
		if (self.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| other.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			return self.getItem() == other.getItem();
		}

		return self.isItemEqual(other);
	}
}