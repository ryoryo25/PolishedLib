package ryoryo.polishedlib.util.handlers;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;
import ryoryo.polishedlib.util.References;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.interfaces.IBlockColorProvider;
import ryoryo.polishedlib.util.interfaces.IItemColorProvider;
import ryoryo.polishedlib.util.interfaces.IModelRegister;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class RegistryHandler {

	// copied from https://github.com/Vazkii/AutoRegLib/blob/1.12/src/main/java/vazkii/arl/util/ProxyRegistry.java
	private static Multimap<Class<?>, IForgeRegistryEntry<?>> entries = MultimapBuilder.hashKeys().arrayListValues().build();

	/**
	 * IForgeRegistryEntryを実装しているクラスたちをMapに登録する
	 *
	 * @param obj
	 */
	public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistryEntry<T> obj) {
		if (obj == null)
			return;

		entries.put(obj.getRegistryType(), obj);

		// model
		if (obj instanceof IModelRegister) {
			ModelHandler.modelRegisters.add((IModelRegister) obj);
		}

		// coloring
		if (obj instanceof IBlockColorProvider && obj instanceof Block) {
			ColorHandler.blockMap.put((IBlockColorProvider) obj, (Block) obj);
		}

		if (obj instanceof IItemColorProvider) {
			Item item;
			if (obj instanceof Block) {
				item = Item.getItemFromBlock((Block) obj);
			} else if (obj instanceof Item) {
				item = (Item) obj;
			} else {
				return;
			}

			if (item == Items.AIR) {
				return;
			}

			ColorHandler.itemMap.put((IItemColorProvider) obj, item);
		}
	}

	// ここで実際にマインクラフトに登録する
	@SubscribeEvent
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void onRegistryEvent(RegistryEvent.Register event) {
		Class<?> type = event.getRegistry().getRegistrySuperType();

		if (entries.containsKey(type)) {
			Utils.measureTime("registering " + entries.get(type).size() + " " + type.getSimpleName() + "(s)", () -> {

				entries.get(type).forEach(event.getRegistry()::register);

			});
		}
	}
}