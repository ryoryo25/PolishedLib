package ryoryo.polishedlib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.minecraft.client.Minecraft;

public class References
{
	public static final String MOD_ID = "polishedlib";
	public static final String MOD_NAME = "PolishedLib";

	public static final String MOD_VERSION_MAJOR = "GRADLE.VERSION_MAJOR";
	public static final String MOD_VERSION_MINOR = "GRADLE.VERSION_MINOR";
	public static final String MOD_VERSION_PATCH = "GRADLE.VERSION_PATCH";
	public static final String MOD_VERSION = MOD_VERSION_MAJOR + "." + MOD_VERSION_MINOR + "." + MOD_VERSION_PATCH;

	public static final String MOD_DEPENDENCIES = "required-after:forge@[1.12.2-14.23.4.2705,);";

	public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.12.2]";
	public static final String MOD_GUI_FACTORY = "ryoryo.polishedlib.config.GuiModConfigFactory";

	public static final String PROXY_CLIENT = "ryoryo.polishedlib.proxy.ClientProxy";
	public static final String PROXY_COMMON = "ryoryo.polishedlib.proxy.CommonProxy";

	public static final String PREFIX = MOD_ID + ":";
	public static final String TEXTURE_FOLDER = PREFIX + "textures/";
	public static final String GUI_FOLDER = TEXTURE_FOLDER + "gui/";
	public static final String HUD_FOLDER = TEXTURE_FOLDER + "hud/";
	public static final String VILLAGER_FOLDER = TEXTURE_FOLDER + "villager/";
	public static final String TILEENTITY_FOLDER = TEXTURE_FOLDER + "tileentity/";
	public static final String MOB_TEXTURE_FOLDER = TEXTURE_FOLDER + "mob/";
	public static final String MODEL_TEXTURE_FOLDER = TEXTURE_FOLDER + "itemmodels/";
	public static final String ARMOR_TEXTURE_FOLDER = TEXTURE_FOLDER + "armor/";
	public static final String MODEL_FOLDER = PREFIX + "models/";

	public static String getVersion()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		return Minecraft.getMinecraft().getVersion() + "-" + sdf.format(cal.getTime());
	}

	public static String getDependencies(String id)
	{
		return "after:" + id + ";";
	}

	//	static
	//	{
	//		MOD_VERSION = getVersion();
	//	}
}