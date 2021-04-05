package ryoryo.polishedlib.util;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;

public class RegistryUtils {

	//	/**
	//	 * ブロック登録
	//	 *
	//	 * @param block
	//	 * @param name
	//	 */
	//	public void registerBlock(Block block, String name) {
	//		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
	//		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	//
	//		if (Utils.isClient())
	//			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	//	}
	//
	//	/**
	//	 * ブロック登録
	//	 * アイテムブロックを別に作成時用
	//	 *
	//	 * @param block
	//	 * @param itemBlock
	//	 * @param name
	//	 */
	//	public void registerBlock(Block block, ItemBlock itemBlock, String name) {
	//		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
	//		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
	//
	//		if (Utils.isClient())
	//			ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	//	}
	//
	//	/**
	//	 * ブロック登録
	//	 * stateを無視する処理を入れるかどうか
	//	 *
	//	 * @param block
	//	 * @param itemBlock
	//	 * @param name
	//	 * @param meta
	//	 * @param namingFunc
	//	 * @param ignoreState
	//	 */
	//	public void registerBlock(Block block, ItemBlock itemBlock, String name, int meta, BiFunction<Integer, String, String> namingFunc, IStateMapper mapper) {
	//		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
	//		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
	//
	//		if (Utils.isClient()) {
	//			ModelLoader.setCustomStateMapper(block, mapper);
	//			for (int i = 0; i < meta; i ++)
	//				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(namingFunc.apply(i, block.getRegistryName().toString()), "inventory"));
	//		}
	//	}
	//	//
	//	//	/**
	//	//	 * ブロック登録
	//	//	 * Functionで添字iに対応する文字列を返す
	//	//	 *
	//	//	 * @param block
	//	//	 * @param itemBlock
	//	//	 * @param name
	//	//	 * @param meta
	//	//	 * @param namingFunc
	//	//	 */
	//	//	public void registerBlock(Block block, ItemBlock itemBlock, String name, int meta, BiFunction<Integer, String, String> namingFunc) {
	//	//		registerBlock(block, itemBlock, name, meta, namingFunc, new StateMap.Builder().build());
	//	//	}
	//
	//	public void registerBlock(Block block, ItemBlock itemBlock, String name, int meta, BiFunction<Integer, String, String> namingFunc) {
	//		ForgeRegistries.BLOCKS.register(block.setRegistryName(Utils.makeModLocation(this.modId, name)));
	//		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
	//
	//		if (Utils.isClient()) {
	//			for (int i = 0; i < meta; i ++)
	//				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(namingFunc.apply(i, block.getRegistryName().toString()), "inventory"));
	//		}
	//	}
	//
	//	/**
	//	 * ブロック登録
	//	 * meta違いで名前も変えたいとき用
	//	 *
	//	 * @param block
	//	 * @param itemBlock
	//	 * @param location
	//	 * @param names
	//	 */
	//	public void registerBlock(Block block, ItemBlock itemBlock, String name, String[] names) {
	//		registerBlock(block, itemBlock, name, names.length, (i, base) -> base + "_" + names[i]);
	//	}
	//
	//	/**
	//	 * ブロック登録
	//	 * meta違いで数字で違う名前にする用
	//	 *
	//	 * @param block
	//	 * @param itemBlock
	//	 * @param location
	//	 * @param meta
	//	 */
	//	public void registerBlock(Block block, ItemBlock itemBlock, String name, int meta) {
	//		registerBlock(block, itemBlock, name, meta, (i, base) -> base + "_" + i);
	//	}
	//
	//	/**
	//	 * ブロック登録
	//	 * stateごとにmodelを変えたくないやつよう
	//	 * 変えたくないpropをignoreする
	//	 *
	//	 * @param block
	//	 * @param itemBlock
	//	 * @param name
	//	 * @param meta
	//	 * @param ignoreState
	//	 */
	//	//	public void registerBlock(Block block, ItemBlock itemBlock, String name, int meta, IStateMapper mapper) {
	//	//		registerBlock(block, itemBlock, name, meta, (i, base) -> base, mapper);
	//	//	}
	//
	//	/**
	//	 * ノーマルアイテム登録
	//	 *
	//	 * @param item
	//	 * @param name
	//	 */
	//	public void registerItem(Item item, String name) {
	//		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));
	//
	//		if (Utils.isClient())
	//			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	//	}
	//
	//	/**
	//	 * アイテム登録
	//	 * Functionで添字iに対応する文字列を返す
	//	 *
	//	 * @param item
	//	 * @param name
	//	 * @param meta
	//	 * @param namingFunc
	//	 */
	//	public void registerItem(Item item, String name, int meta, BiFunction<Integer, String, String> namingFunc) {
	//		ForgeRegistries.ITEMS.register(item.setRegistryName(Utils.makeModLocation(this.modId, name)));
	//
	//		if (Utils.isClient()) {
	//			for (int i = 0; i < meta; i ++)
	//				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(namingFunc.apply(i, item.getRegistryName().toString()), "inventory"));
	//		}
	//	}
	//
	//	/**
	//	 * アイテム登録
	//	 * meta違いでテクスチャを変える用
	//	 *
	//	 * @param item
	//	 * @param location
	//	 * @param names
	//	 */
	//	public void registerItem(Item item, String name, String[] names) {
	//		registerItem(item, name, names.length, (i, base) -> base + "_" + names[i]);
	//	}
	//
	//	/**
	//	 * アイテム登録
	//	 *  meta違いだけど同じテクスチャ用
	//	 *
	//	 * @param item
	//	 * @param name
	//	 * @param meta
	//	 */
	//	public void registerItem(Item item, String name, int meta) {
	//		registerItem(item, name, meta, (i, base) -> base);
	//	}

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
	public static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(GameData.checkPrefix(entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
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
		if (tab == block.getCreativeTabToDisplayOn()) {
			for (int i = firstMeta; i < endMeta; i ++) {
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
		if (tab == item.getCreativeTab()) {
			for (int i = firstMeta; i < endMeta; i ++) {
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
	 * EntityRender登録
	 *
	 * @param entityClass
	 * @param renderFactory
	 */
	@SideOnly(Side.CLIENT)
	public static <T extends Entity> void registerEntityRendering(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
		if (Utils.isClient())
			RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}

	/**
	 * キー登録
	 *
	 * @param key
	 */
	@SideOnly(Side.CLIENT)
	public static void registerKeyBinding(KeyBinding key) {
		if (Utils.isClient())
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