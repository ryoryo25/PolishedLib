package ryoryo.polishedlib.util.handlers;

import java.util.List;
import java.util.function.BiFunction;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ryoryo.polishedlib.util.References;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = References.MOD_ID)
public class ModelHandler {

	public static List<IModelRegister> modelRegisters = Lists.newArrayList();

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		Utils.measureTime("registering models", () -> {

			modelRegisters.forEach(mr -> mr.registerModels());

		});
	}

	// BLOCK
	/**
	 * ブロックの(アイテムの状態の)モデル登録
	 * metaなし
	 *
	 * @param block
	 */
	public static void registerBlockModel(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	/**
	 * ブロックの(アイテムの状態の)モデル登録
	 * metaなし
	 * ItemBlockが別途あるバージョン
	 *
	 * @param block
	 * @param itemBlock
	 */
	public static void registerBlockModel(Block block, ItemBlock itemBlock) {
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	/**
	 * ブロックの(アイテムの状態の)モデル登録
	 * metaあり
	 *
	 * @param block
	 * @param itemBlock
	 * @param meta metaの個数
	 * @param namingFunc meta番号とベースの文字列から各metaの文字列を返すFunction
	 */
	public static void registerBlockModel(Block block, ItemBlock itemBlock, int meta, BiFunction<Integer, String, String> namingFunc) {
		for (int i = 0; i < meta; i ++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(namingFunc.apply(i, block.getRegistryName().toString()), "inventory"));
		}
	}

	/**
	 * ブロックの(アイテムの状態の)モデル登録
	 * metaあり
	 * namesにそれぞれのmetaに対応した文字列を入れる
	 *
	 * @param block
	 * @param itemBlock
	 * @param names meta番号に対応した文字列の配列
	 */
	public static void registerBlockModel(Block block, ItemBlock itemBlock, String[] names) {
		registerBlockModel(block, itemBlock, names.length, (i, base) -> base + "_" + names[i]);
	}

	/**
	 * ブロックの(アイテムの状態の)モデル登録
	 * metaあり
	 * meta番号自体をベースの後ろに付ける
	 *
	 * @param block
	 * @param itemBlock
	 * @param meta metaの個数
	 */
	public static void registerBlockModel(Block block, ItemBlock itemBlock, int meta) {
		registerBlockModel(block, itemBlock, meta, (i, base) -> base + "_" + i);
	}

	// ITEM
	/**
	 * アイテムのモデル登録
	 * metaなし
	 *
	 * @param item
	 */
	public static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	/**
	 * アイテムのモデル登録
	 * metaあり
	 *
	 * @param item
	 * @param meta
	 * @param namingFunc meta番号とベースの文字列から各metaの文字列を返すFunction
	 */
	public static void registerItemModel(Item item, int meta, BiFunction<Integer, String, String> namingFunc) {
		for (int i = 0; i < meta; i ++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(namingFunc.apply(i, item.getRegistryName().toString()), "inventory"));
		}
	}

	/**
	 * アイテムのモデル登録
	 * metaあり
	 * namesにそれぞれのmetaに対応した文字列を入れる
	 *
	 * @param item
	 * @param names meta番号に対応した文字列の配列
	 */
	public static void registerItemModel(Item item, String[] names) {
		registerItemModel(item, names.length, (i, base) -> base + "_" + names[i]);
	}

	/**
	 * アイテムのモデル登録
	 * metaありだけどmetaで見た目を変えたくないとき
	 *
	 * @param item
	 * @param meta
	 */
	public static void registerItemModel(Item item, int meta) {
		registerItemModel(item, meta, (i, base) -> base);
	}
}