package ryoryo.polishedlib.util.handlers;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.References;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.interfaces.IBlockColorProvider;
import ryoryo.polishedlib.util.interfaces.IItemColorProvider;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = References.MOD_ID)
public class ColorHandler {

	public static Map<IBlockColorProvider, Block> blockMap = Maps.newHashMap();
	public static Map<IItemColorProvider, Item> itemMap = Maps.newHashMap();

	@SubscribeEvent
	public static void onBlockColorRegister(ColorHandlerEvent.Block event) {
		if (!blockMap.isEmpty()) {
			Utils.measureTime("registering IBlockColor(s)", () -> {

				blockMap.forEach((bcp, block) -> {
					event.getBlockColors().registerBlockColorHandler((s, w, p, t) -> bcp.colorMultiplier(s, w, p, t), block);
				});

			});
		}
	}

	@SubscribeEvent
	public static void onItemColorRegister(ColorHandlerEvent.Item event) {
		if (!itemMap.isEmpty()) {
			Utils.measureTime("registering IItemColor(s)", () -> {

				itemMap.forEach((icp, item) -> {
					event.getItemColors().registerItemColorHandler((s, t) -> icp.colorMultiplier(s, t), item);
				});

			});
		}
	}

	/**
	 * IBlockColor(これはBlockクラスなどには実装しない)とブロックの紐づけ登録
	 *
	 * @param blockColor
	 * @param blocks
	 */
	@SideOnly(Side.CLIENT)
	public static void registerBlockColor(IBlockColor blockColor, Block... blocks) {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blockColor, blocks);
	}

	/**
	 * IItemColorとアイテムブロックの紐づけ登録
	 *
	 * @param itemColor
	 * @param blocks
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemBlockColor(IItemColor itemColor, Block... blocks) {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, blocks);
	}

	/**
	 * IItemColorとアイテムの紐づけ登録
	 *
	 * @param itemColor
	 * @param items
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemColor(IItemColor itemColor, Item... items) {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, items);
	}
}