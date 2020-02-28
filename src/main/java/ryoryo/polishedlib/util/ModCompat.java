package ryoryo.polishedlib.util;

import net.minecraftforge.fml.common.Loader;

public class ModCompat
{
	//Mod Ids
	public static final String MOD_ID_QUARK = "quark";

	//Bool of the mod loaded
	public static final boolean COMPAT_QUARK = Loader.isModLoaded(MOD_ID_QUARK);
}