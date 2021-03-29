package ryoryo.polishedlib.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ryoryo.polishedlib.client.handlers.CustomModelRegisterHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new CustomModelRegisterHandler());
	}
}