package ryoryo.polishedlib.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Multimap;

import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedlib.util.enums.EnumSimpleFacing;

public class Utils
{
	private static Logger logger = LogManager.getLogger(References.MOD_ID);
	public static String mod_id = "";

	//zero aabb
	public static final AxisAlignedBB ZERO_AABB = creatAABB(0, 0, 0, 0, 0, 0);

	//Properties
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool TOP = PropertyBool.create("top");
	public static final PropertyBool BOTTOM = PropertyBool.create("bottom");

	public static final PropertyDirection ALL_FACING = BlockDirectional.FACING;
	public static final PropertyDirection HORIZONTAL_FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<Axis> AXIS = PropertyEnum.<Axis> create("axis", Axis.class);
	public static final PropertyEnum<EnumSimpleFacing> SIMPLE_FACING = PropertyEnum.<EnumSimpleFacing> create("facing", EnumSimpleFacing.class);

	public static final PropertyEnum<EnumColor> COLOR = PropertyEnum.<EnumColor> create("color", EnumColor.class);

	/**
	 * クラスがロードされているかどうか調べる。
	 * isModLoadedでは判定できないjar導入系のMODの導入判定や、バージョンチェックなどに利用可能。
	 *
	 * @param classname
	 *            パッケージまでを含めた完全装飾名を指定する必要がある。例）"minecraftAPI.MCAPI"
	 * @since 1.4
	 */
	public static boolean isClassLoaded(String classname)
	{
		try
		{
			Class.forName(classname);
		}
		catch(ClassNotFoundException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * 10進数を2進数に変換
	 *
	 * @param baseDecimal
	 * @return
	 */
	public static String/* int */ transformDecimalToBinary(int baseDecimal)
	{
		return /* String bin = */"0b" + Integer.toBinaryString(baseDecimal);
		// return Integer.valueOf(bin);
	}

	// TODO
	/**
	 * 10進数を8進数に変換
	 *
	 * @param baseDecimal
	 * @return
	 */
	public static String/* int */ transformDecimalToOctal(int baseDecimal)
	{
		return /* String oct = */"0" + Integer.toOctalString(baseDecimal);
		// return Integer.valueOf(oct);
	}

	// TODO
	/**
	 * 10進数を16進数に変換
	 *
	 * @param baseDecimal
	 * @return
	 */
	public static String/* int */ transformDecimalToHexadecimal(int baseDecimal)
	{
		return /* String hex = */"0x" + Integer.toHexString(baseDecimal);
		// return Integer.valueOf(hex);
	}

	/**
	 * プレイヤーのチャット欄にメッセージを追加
	 *
	 * @param player
	 * @param message
	 */
	public static void addChat(EntityPlayer player, String message)
	{
		player.sendMessage(new TextComponentTranslation(message));
	}

	public static void addChat(String message)
	{
		addChat(getPlayer(), message);
	}

	/**
	 * フォーマット付きメッセージ
	 *
	 * @param player
	 * @param message
	 * @param args
	 */
	public static void addChatFormatted(EntityPlayer player, String message, Object... args)
	{
		player.sendMessage(new TextComponentTranslation(message, args));
	}

	public static void addChatFormatted(String message, Object... args)
	{
		addChatFormatted(getPlayer(), message, args);
	}

	public static void addChatModInfo(EntityPlayer player, String message)
	{
		player.sendMessage(new TextComponentString(TextFormatting.BLUE + "" + TextFormatting.BOLD + "[PolishedStone V2]:" + TextFormatting.RESET + " " + message));
	}

	public static void addChatModInfo(String message)
	{
		addChatModInfo(getPlayer(), message);
	}

	/**
	 * 翻訳
	 *
	 * @param key
	 * @return
	 */
	public static String translatableString(String key)
	{
		return I18n.translateToLocal(key);
	}

	/**
	 * フォーマット付き翻訳
	 *
	 * @param key
	 * @param formats
	 * @return
	 */
	public static String translatableStringFormatted(String key, Object... formats)
	{
		return I18n.translateToLocalFormatted(key, formats);
	}

	/**
	 * ロガー
	 *
	 * @param event
	 */
	public static void addLog(String log)
	{
		logger.log(Level.OFF, log);
	}

	public static void addLog(Level lvl, String log)
	{
		logger.log(lvl, log);
	}

	public static void addLog(Level lvl, String log, Object... params)
	{
		logger.log(lvl, log, params);
	}

	public static void addLog(String log, Object... params)
	{
		logger.log(Level.OFF, log, params);
	}

	public static void addLogThrowable(Throwable e, String log)
	{
		logger.log(Level.OFF, log, e);
	}

	public static void addInfo(String info)
	{
		logger.info(info);
	}

	public static void addInfo(String info, Object... params)
	{
		logger.info(info, params);
	}

	public static void addInfoThrowable(Throwable e, String log)
	{
		logger.info(log, e);
	}

	public static void addWarn(String warn)
	{
		logger.warn(warn);
	}

	public static void addWarn(String warn, Object... params)
	{
		logger.warn(warn, params);
	}

	public static void addWarnThrowable(Throwable e, String log)
	{
		logger.warn(log, e);
	}

	public static void addTrace(String trace)
	{
		logger.trace(trace);
	}

	public static void addTrace(String trace, Object... params)
	{
		logger.trace(trace, params);
	}

	public static void addTraceThrowable(Throwable e, String log)
	{
		logger.trace(log, e);
	}

	public static void addFatal(String fatal)
	{
		logger.fatal(fatal);
	}

	public static void addFatal(String fatal, Object... params)
	{
		logger.fatal(fatal, params);
	}

	public static void addFatalThrowable(Throwable e, String log)
	{
		logger.fatal(log, e);
	}

	public static void addDebug(String debag)
	{
		logger.debug(debag);
	}

	public static void addDebug(String debag, Object... params)
	{
		logger.debug(debag, params);
	}

	public static void addDebugThrowable(Throwable e, String log)
	{
		logger.debug(log, e);
	}

	public static void addError(String error)
	{
		logger.error(error);
	}

	public static void addError(String error, Object... params)
	{
		logger.error(error, params);
	}

	public static void addErrorThrowable(Throwable e, String log)
	{
		logger.error(log, e);
	}

	/**
	 * 小数をパーセントに変換
	 *
	 * @param base
	 * @return
	 */
	public static float decimalToPercent(float base)
	{
		return base * 100F;
	}

	/**
	 * 小数をパーミルに変換
	 *
	 * @param base
	 * @return
	 */
	public static float decimalToPermil(float base)
	{
		return base * 1000F;
	}

	/**
	 * パーセントを通常の小数に変換
	 *
	 * @param percent
	 * @return
	 */
	public static float percentToDecimal(float percent)
	{
		return percent / 100F;
	}

	/**
	 * パーミルを通常の小数に変換
	 *
	 * @param permil
	 * @return
	 */
	public static float permilToDecimal(float permil)
	{
		return permil / 1000F;
	}

	/**
	 * tickを秒に
	 *
	 * @param tick
	 * @return
	 */
	public static float tickToSecond(float tick)
	{
		return tick / 20F;
	}

	/**
	 * tickを分に
	 *
	 * @param tick
	 * @return
	 */
	public static float tickToMinute(float tick)
	{
		return tickToSecond(tick) / 60;
	}

	/**
	 * 秒をtickに
	 *
	 * @param second
	 * @return
	 */
	public static float secondToTick(float second)
	{
		return second * 20F;
	}

	public static float minuteToTick(float minute)
	{
		return secondToTick(minute * 60F);
	}

	/**
	 * ワールド時間の経過日数を得る（ワールド生成時を0日目6:00とし、24:00で1日経過として計算）
	 *
	 * @param world
	 * @return
	 */
	public static int getWorldTimeDay(World world)
	{
		return (int) ((world.getWorldInfo().getWorldTime() + 6000L) / 24000L);
	}

	/**
	 * ワールド時刻の時を得る（起床時間を6時として計算）
	 *
	 * @param world
	 * @return
	 */
	public static int getWorldTimeHour(World world)
	{
		return (int) (((world.getWorldInfo().getWorldTime() + 6000L) % 24000L) / 1000L);
	}

	/**
	 * ワールド時刻の分を得る（起床時間を6時として計算）
	 *
	 * @param world
	 * @return
	 */
	public static int getWorldTimeMinutes(World world)
	{
		return (int) (((world.getWorldInfo().getWorldTime() + 6000L) % 1000L) * 60L / 1000L);
	}

	// /**
	// * 指定された定型／不定形レシピを削除する。レシピの指定は厳密に判定される。
	// * 例）ダイヤつるはしのレシピを削除する場合
	// * {@code
	// * ItemStack dia = new ItemStack(Item.diamond);
	// * ItemStack stick = new ItemStack(Item.stick);
	// * removeRecipe(new ItemStack(Item.pickaxeDiamond), dia, dia, dia, null,
	// stick, null, null, stick, null);
	// * }
	// * return 削除されたアイテムがあればtrue
	// * @since 1.6, 1.7
	// */
	// public static boolean removeRecipe(ItemStack output, ItemStack... stacks)
	// {
	// assert (stacks.length == 9): "removeRecipe: stacksのサイズは9である必要があります。";
	// DummyInventoryCrafting inv = new DummyInventoryCrafting(stacks);
	// List recipes = CraftingManager.getInstance().getRecipeList();
	// for (int i = 0; i < recipes.size(); ++i)
	// {
	// IRecipe recipe = (IRecipe)recipes.get(i);
	// if (recipe.matches(inv) && recipe.getRecipeOutput().isStackEqual(output))
	// {
	// recipes.remove(i);
	// addLogFinest("removeRecipe: %s x %d のレシピを削除しました。",
	// output.getItem().getItemDisplayName(output), output.stackSize);
	// return true;
	// }
	// }
	// addLogWarning("removeRecipe error: %s x %d のレシピが存在しません。",
	// output.getItem().getItemDisplayName(output), output.stackSize);
	// return false;
	// }
	//
	// /**
	// * 指定されたアイテムを作るためのレシピを全て削除する。
	// * @since 1.7
	// */
	// public static boolean removeRecipe(ItemStack output)
	// {
	// Iterator itr = CraftingManager.getInstance().getRecipeList().iterator();
	// boolean result = false;
	// while(itr.hasNext())
	// {
	// Object obj = itr.next();
	// if(obj instanceof ShapedRecipes || obj instanceof ShapelessRecipes)
	// {
	// IRecipe recipe = (IRecipe)obj;
	// if(output.isItemEqual(recipe.getRecipeOutput()))
	// {
	// itr.remove();
	// result = true;
	// addLogFinest("removeRecipe: %s x %d のレシピを削除しました。",
	// output.getItem().getItemDisplayName(output), output.stackSize);
	// }
	// }
	// }
	// if(!result) addLogWarning("removeRecipe error: %s x %d のレシピが存在しません。",
	// output.getItem().getItemDisplayName(output), output.stackSize);
	// return result;
	// }
	//
	// /**
	// * 指定されたアイテムの精錬レシピを削除する。
	// * @since 1.12.150602
	// */
	// public static boolean removeSmelting(int id)
	// {
	// if(FurnaceRecipes.smelting().getSmeltingList().containsKey(Integer.valueOf(id)))
	// {
	// FurnaceRecipes.smelting().getSmeltingList().remove(id);
	// return true;
	// }
	// return false;
	// }
	//
	// /**
	// * 指定されたアイテムの精錬レシピを削除する。
	// * @since 1.12.150602
	// */
	// public static boolean removeSmelting(int id, int meta)
	// {
	// if(FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(id, 1,
	// meta)) != null)
	// {
	// try
	// {
	// Field forgeMetadataSmelting =
	// FurnaceRecipes.class.getDeclaredField("metaSmeltingList");
	// forgeMetadataSmelting.setAccessible(true);
	// Map recipes = (Map)forgeMetadataSmelting.get(FurnaceRecipes.smelting());
	// recipes.remove(Arrays.asList(id, meta));
	// return true;
	// }
	// catch(NoSuchFieldException e){}
	// catch(IllegalAccessException e){}
	// }
	// return false;
	// }

	/*
	 * Moddingメモ Entity.rotationYaw 南0° 西90° 北180° 東270° Entity.rotationPitch
	 * 正面0° 上-90° 下90°
	 */
	/**
	 * Entityの向いている方角を、東西南北で取得する。(South start) ForegeDirection使えると思う
	 *
	 * @return 0南 1西 2北 3東
	 * @since 1.19.160526
	 */
	public static int getEntityDirectionS(Entity entity)
	{
		return MathHelper.floor((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	}

	/**
	 * Entityの向いている方角を、東西南北で取得する。
	 *
	 * @return 0北 1東 2南 3西
	 * @since 1.4
	 */
	public static int getEntityDirection(Entity entity)
	{
		return MathHelper.floor((double) (entity.rotationYaw / 90.0F) + 2.5D) & 3;
	}

	/**
	 * Entityの向いている方角を、8方向で取得する。
	 *
	 * @return 0北 1北東 2東 3南東 4南 5南西 6西 7北西
	 * @since 1.20.170401
	 */
	public static int getEntityDirection8(Entity entity)
	{
		return MathHelper.floor((double) (entity.rotationYaw / 45.0F) + 4.5D) & 7;
	}

	/**
	 * Entityの向いている方角を取得する。南北のみの判定などに使用できる。 南北判定例 if(return == 0 || return ==
	 * 3){ 北向き }else{ 南向き }; 東西判定例 if(return == 0 || return == 1){ 東向き }else{
	 * 西向き };
	 *
	 * @return 0北東 1南東 2南西 3北西
	 * @since 1.4
	 */
	public static int getEntityCoordinate(Entity entity)
	{
		return MathHelper.floor((double) (entity.rotationYaw / 90.0F) + 2) & 3;
	}

	/**
	 * プレイヤー(クライアント)を得る
	 *
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public static EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().player;
	}

	/**
	 * ワールド(クライアント)を得る
	 *
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public static World getWorld()
	{
		return Minecraft.getMinecraft().world;
	}

	/**
	 * ディメンションIDを得る
	 *
	 * @param world
	 * @return
	 */
	public static int getDimensionId(World world)
	{
		return world.provider.getDimension();
	}

	@SideOnly(Side.CLIENT)
	public static int getDimensionId()
	{
		return getDimensionId(getWorld());
	}

	/**
	 * ディメンションの名前を得る
	 *
	 * @param world
	 * @return
	 */
	public static String getDimensionName(World world)
	{
		return world.provider.getDimensionType().getName();
	}

	@SideOnly(Side.CLIENT)
	public static String getDimensionName()
	{
		return getDimensionName(getWorld());
	}

	/**
	 * クリエイティブ判定
	 *
	 * @param player
	 * @return
	 */
	public static boolean isCreative(EntityPlayer player)
	{
		return player.capabilities.isCreativeMode;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isCreative()
	{
		return isCreative(getPlayer());
	}

	/**
	 * 飛行判定
	 *
	 * @param player
	 * @return
	 */
	public static final boolean isFlying(EntityPlayer player)
	{
		return player.capabilities.isFlying;
	}

	@SideOnly(Side.CLIENT)
	public static final boolean isFlying()
	{
		return isFlying(getPlayer());
	}

	/**
	 * たぶんサバイバル(アドベンチャー)判定
	 *
	 * @param player
	 * @return
	 */
	public static final boolean isSurvival(EntityPlayer player)
	{
		return !isCreative(player) && !player.isSpectator();
	}

	@SideOnly(Side.CLIENT)
	public static final boolean isSurvival()
	{
		return isSurvival(getPlayer());
	}

	/**
	 * プレイヤーがメインハンドに持ってるアイテムスタックを得る
	 *
	 * @param player
	 * @return
	 */
	public static ItemStack getHeldItemStackMainhand(EntityPlayer player)
	{
		return player.getHeldItemMainhand();
	}

	@SideOnly(Side.CLIENT)
	public static ItemStack getHeldItemStackMainhand()
	{
		return getHeldItemStackMainhand(getPlayer());
	}

	/**
	 * プレイヤーがオフハンドに持ってるアイテムスタックを得る
	 *
	 * @param player
	 * @return
	 */
	public static ItemStack getHeldItemStackOffhand(EntityPlayer player)
	{
		return player.getHeldItemOffhand();
	}

	@SideOnly(Side.CLIENT)
	public static ItemStack getHeldItemStackOffhand()
	{
		return getHeldItemStackOffhand(getPlayer());
	}

	/**
	 * 両方のアイテムスタック
	 *
	 * @param player
	 * @return
	 */
	public static ItemStack[] getHeldItemStacks(EntityPlayer player)
	{
		return new ItemStack[]
		{ getHeldItemStackMainhand(player), getHeldItemStackOffhand(player) };
	}

	@SideOnly(Side.CLIENT)
	public static ItemStack[] getHeldItemStacks()
	{
		return getHeldItemStacks(getPlayer());
	}

	/**
	 * プレイヤーがメインハンドに持ってるアイテムを得る
	 *
	 * @param player
	 * @return
	 */
	public static Item getHeldItemMainhand(EntityPlayer player)
	{
		return getHeldItemStackMainhand(player).getItem();
	}

	@SideOnly(Side.CLIENT)
	public static Item getHeldItemMainhand()
	{
		return getHeldItemMainhand(getPlayer());
	}

	/**
	 * プレイヤーがオフハンドに持ってるアイテムを得る
	 *
	 * @param player
	 * @return
	 */
	public static Item getHeldItemOffhand(EntityPlayer player)
	{
		return getHeldItemStackOffhand(player).getItem();
	}

	@SideOnly(Side.CLIENT)
	public static Item getHeldItemOffhand()
	{
		return getHeldItemOffhand(getPlayer());
	}

	/**
	 * 両方のアイテム
	 *
	 * @param player
	 * @return
	 */
	public static Item[] getHeldItems(EntityPlayer player)
	{
		return new Item[]
		{ getHeldItemMainhand(player), getHeldItemOffhand(player) };
	}

	public static Item[] getHeldItems()
	{
		return getHeldItems(getPlayer());
	}

	/**
	 * レシピ削除
	 *
	 * @param output
	 */
	public static void removeRecipe(ItemStack output)
	{
//	 List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> remover = ForgeRegistries.RECIPES.iterator();

		while(remover.hasNext())
		{
			ItemStack itemStack = remover.next().getRecipeOutput();

			if(itemStack != null && output.isItemEqual(itemStack))
			{
				remover.remove();
			}
		}
	}

	// TODO 精錬レシピ削除
	/**
	 * 精錬レシピの限定的な削除
	 *
	 * @param input
	 * @param output
	 */
	public static void removeSmeltingRecipe(ItemStack input, ItemStack output)
	{
		Map<ItemStack, ItemStack> remover = FurnaceRecipes.instance().getSmeltingList();
		remover.remove(input, output);
	}

	public static void removeSmeltingRecipe(ItemStack output)
	{
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
	public static void addFuel(int burnTime, ItemStack... fuels)
	{
		GameRegistry.registerFuelHandler(new IFuelHandler()
		{
			public int getBurnTime(ItemStack itemStack)
			{
				for(ItemStack add : fuels)
				{
					if(add != null && itemStack.isItemEqual(add))
					{
						return burnTime;
					}
				}
				return 0;
			}
		});
	}

	/**
	 * 指定した鉱石辞書名に登録されているアイテムがあるか調べる
	 *
	 * @param oreName
	 * @return
	 */
	public static boolean isOreDictLoaded(String oreName)
	{
		List<ItemStack> item = OreDictionary.getOres(oreName);

		if(item.size() > 0)
			return true;
		else
		{
			addInfo("The mod which have a " + oreName + " isn't installed.");
			return false;
		}
	}

	public static ItemStack getItemFromOreDict(String oreName)
	{
		List<ItemStack> item = OreDictionary.getOres(oreName);

		if(isOreDictLoaded(oreName))
		{
			return new ItemStack(item.get(0).getItem(), 1, item.get(0).getItemDamage());
		}
		else
			return ItemStack.EMPTY;
	}

	/**
	 * 鉱石辞書を探す
	 *
	 * @param stack
	 * @return
	 */
	public static List<ItemStack> findOreDict(ItemStack stack)
	{
		String[] oredicts = OreDictionary.getOreNames();
		List<ItemStack> result = new ArrayList<ItemStack>();
		try
		{
			for(int oreId : OreDictionary.getOreIDs(stack))
			{
				if(oreId >= 0)
				{
					String oreName = OreDictionary.getOreName(oreId);
					for(String regex : oredicts)
					{
						if(oreName.toLowerCase().matches(regex))
						{
							result = OreDictionary.getOres(oreName);
						}
					}
				}
			}

		}
		catch(IllegalArgumentException e)
		{
		}
		catch(NullPointerException e)
		{
		}
		return result;
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 * @param name
	 * @param output
	 * @param params
	 */
	public static void addRecipe(String name, ItemStack output, Object... params)
	{
		addShapedRecipe(name, output, params);
	}

	/**
	 * jsonでレシピ登録とかめんどいから前みたいにできるように
	 * @param name
	 * @param output
	 * @param params
	 */
	public static void addShapedRecipe(String name, @Nonnull ItemStack output, Object... params)
	{
		ResourceLocation location = makeModLocation(name);
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
	public static void addShapelessRecipe(String name, @Nonnull ItemStack output, Object... params)
	{
		ResourceLocation location = makeModLocation(name);
		ShapelessOreRecipe ret = new ShapelessOreRecipe(location, output, params);
		ret.setRegistryName(location);
		for(Ingredient ing : ret.getIngredients())
		{
			if(ing instanceof OreIngredient && ing.getMatchingStacks().length < 1)
				return;
		}
		ForgeRegistries.RECIPES.register(ret);
	}

	/**
	 * 斧レシピの登録
	 * 名前の前に"axe_"と足される
	 *
	 * @param output
	 * @param material
	 */
	public static void addRecipeAxe(String name, ItemStack output, ItemStack material)
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
	public static void addRecipePickaxe(String name, ItemStack output, ItemStack material)
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
	public static void addRecipeShovel(String name, ItemStack output, ItemStack material)
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
	public static void addRecipeTools(String name, ItemStack material, ItemStack... output)
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
	public static void addRecipeSword(String name, ItemStack material, ItemStack output)
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
	public static void addRecipeArmor(String name, ItemStack material, ItemStack... output)
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
	public static void addRecipeAllToolsAndArmor(String name, ItemStack material, ItemStack... output)
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
	public static void addRecipeStairs(String name, Block output, ItemStack material)
	{
		int quantity = 4;
		if(ModCompat.COMPAT_QUARK)
			quantity = 8;
		else
			addInfo("Quark isn't loaded.");
		addRecipe("stairs_" + name, new ItemStack(output, quantity), "#  ", "## ", "###", '#', material);
	}

	/**
	 * 階段を手元で作れるようにレシピ登録
	 * 名前の前に"stairs_"と足される
	 *
	 * @param material
	 * @param output
	 */
	public static void addMiniRecipeStairs(String name, Block output, ItemStack material)
	{
		int quantity = 2;
		if(ModCompat.COMPAT_QUARK)
			quantity = 4;
		else
			addInfo("Quark isn't loaded.");
		addRecipe("stairs_" + name, new ItemStack(output, quantity), "# ", "##", '#', material);
	}

	/**
	 * 壁のレシピ登録
	 * 名前の前に"wall_"と足される
	 * @param name
	 * @param output
	 * @param material
	 */
	public static void addRecipeWall(String name, Block output, ItemStack material)
	{
		addRecipe("wall_" + name, new ItemStack(output, 6), "MMM", "MMM", 'M', material);
	}

	/**
	 * 草からドロップするものを追加
	 * MinecraftForge.addGrassSeed(seed, weight)の備忘録用
	 *
	 * @param seed
	 * @param weight
	 */
	public static void addDropSeedFromGrass(ItemStack seed, int weight)
	{
		MinecraftForge.addGrassSeed(seed, weight);
	}

	/**
	 * スポーンエッグに新しくmobを追加
	 *
	 * @param entityId
	 * @param primaryColor
	 * @param secondaryColor
	 */
	public static void addSpawnEgg(Class<? extends EntityLiving> entity, int primaryColor, int secondaryColor)
	{
		EntityRegistry.registerEgg(EntityList.getKey(entity), primaryColor, secondaryColor);
	}

	/**
	 * スポーンエッグがあるかどうか
	 * @param entity
	 * @return
	 */
	public static boolean hasSpawnEgg(EntityLiving entity)
	{
		return EntityList.ENTITY_EGGS.containsKey(EntityList.getKey(entity));
	}

	/**
	 * 実績取得のトリガーを追加
	 *
	 * @param player
	 * @param achievementName
	 */
	public static void triggerAdvancement(EntityPlayer player, Advancement achievementName)
	{
	}

	/**
	 * mobのスポーンを追加
	 *
	 * @param entity
	 * @param weight
	 * @param minAmount
	 * @param maxAmount
	 * @param type
	 * @param biomes
	 */
	public static void addSpawnEntity(Class<? extends EntityLiving> entity, int weight, int minAmount, int maxAmount, EnumCreatureType type, Biome... biomes)
	{
		EntityRegistry.addSpawn(entity, weight, minAmount, maxAmount, type, biomes);
		// 大体同義
		//BiomeGenBase.hell.getSpawnableList(EnumCreatureType.monster).add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, weight, minAmount, maxAmount));
	}

	// /**
	// * 釣りで釣れるアイテムを追加
	// *
	// * @param item
	// */
	// public static void addFishableItemFish(ItemStack itemStack, int weight)
	// {
	// FishingHooks.addFish(new WeightedRandomFishable(itemStack, weight));
	// }
	//
	// public static void addFishableItemJunk(ItemStack itemStack, int weight)
	// {
	// FishingHooks.addJunk(new WeightedRandomFishable(itemStack, weight));
	// }
	//
	// public static void addFishableItemTreasure(ItemStack itemStack, int
	// weight)
	// {
	// FishingHooks.addTreasure(new WeightedRandomFishable(itemStack, weight));
	// }
	//
	// /**
	// * お宝チェストに入ってるものを追加
	// *
	// * @param category
	// * @param itemStack
	// * @param minAmount
	// * @param maxAmount
	// * @param weight
	// */
	// public static void addTreasureChest(String category, ItemStack itemStack,
	// int minAmount, int maxAmount, int weight)
	// {
	// ChestGenHooks.addItem(category, new WeightedRandomChestContent(itemStack,
	// minAmount, maxAmount, weight));
	// }

	/**
	 * スポナー追加
	 * @param entityName
	 * @param rarity
	 */
	public static void addMobSpawner(ResourceLocation entityName, int rarity)
	{
		DungeonHooks.addDungeonMob(entityName, rarity);
	}

	// TODO
	// public static void name()
	// {
	// ForgeHooks.onPlayerTossEvent(player, item, includeName);
	// }

	/**
	 * クライアントかサーバかサイドを得る
	 *
	 * @return
	 */
	public static Side getSide()
	{
		return FMLCommonHandler.instance().getSide();
	}

	/**
	 * サーバサイドかどうか
	 *
	 * @return
	 */
	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getSide().isServer();
	}

	/**
	 * クライアントサイドかどうか
	 *
	 * @return
	 */
	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	/**
	 * ブロック登録
	 *
	 * @param block
	 * @param location
	 */
	public static void registerBlock(Block block, String name)
	{
		ResourceLocation location = makeModLocation(name);
		ForgeRegistries.BLOCKS.register(block.setRegistryName(location));
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));

		if(isClient())
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(location, "inventory"));
		}
	}

	/**
	 * ブロック登録
	 * アイテムブロックを別に作成時用
	 *
	 * @param block
	 * @param itemBlock
	 * @param name
	 */
	public static void registerBlock(Block block, ItemBlock itemBlock, String name)
	{
		ResourceLocation location = makeModLocation(name);
		ForgeRegistries.BLOCKS.register(block.setRegistryName(location));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(isClient())
		{
			ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(location, "inventory"));
		}
	}

	/**
	 * ブロック登録
	 * meta違いで名前も変えたいとき用
	 *
	 * @param block
	 * @param itemBlock
	 * @param location
	 * @param meta
	 * @param locations
	 * @param names
	 */
	public static void registerBlock(Block block, ItemBlock itemBlock, ResourceLocation location, int meta, ResourceLocation[] locations, String... names)
	{
		ForgeRegistries.BLOCKS.register(block.setRegistryName(location));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(isClient() && names.length > 0)
		{
			ModelBakery.registerItemVariants(Item.getItemFromBlock(block), locations);
			for(int i = 0; i < meta; i++)
			{
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(location.toString() + "_" + names[i], "inventory"));
			}
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
	 * @param locations
	 */
	public static void registerBlock(Block block, ItemBlock itemBlock, ResourceLocation location, int meta, ResourceLocation[] locations)
	{
		ForgeRegistries.BLOCKS.register(block.setRegistryName(location));
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));

		if(isClient())
		{
			ModelBakery.registerItemVariants(Item.getItemFromBlock(block), locations);
			for(int i = 0; i < meta; i++)
			{
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(location.toString() + "_" + i, "inventory"));
			}
		}
	}

	/**
	 * 小階段登録
	 *
	 * @param block
	 * @param name
	 */
	public static void registerSmallStairs(Block block, String name)
	{
		ResourceLocation location = makeModLocation("small_stairs_" + name);
		ForgeRegistries.BLOCKS.register(block.setRegistryName(location));
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));

		if(isClient())
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(location, "inventory"));
		}
	}

	/**
	 * ノーマルアイテム登録
	 *
	 * @param item
	 * @param name
	 */
	public static void registerItem(Item item, String name)
	{
		ResourceLocation location = makeModLocation(name);
		ForgeRegistries.ITEMS.register(item.setRegistryName(location));

		if(isClient())
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(location, "inventory"));
		}
	}

	/**
	 * アイテム登録
	 * meta違いでテクスチャを変える用
	 *
	 * @param item
	 * @param location
	 * @param meta
	 * @param locations
	 * @param names
	 */
	public static void registerItem(Item item, ResourceLocation location, int meta, ResourceLocation[] locations, String... names)
	{
		ForgeRegistries.ITEMS.register(item.setRegistryName(location));

		if(isClient() && names.length > 0)
		{
			ModelBakery.registerItemVariants(item, locations);
			for(int i = 0; i < meta; i++)
			{
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(location.toString() + "_" + names[i], "inventory"));
			}
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
	public static void registerItem(Item item, String name, int meta)
	{
		ResourceLocation location = makeModLocation(name);
		ForgeRegistries.ITEMS.register(item.setRegistryName(location));

		if(isClient())
		{
			ModelBakery.registerItemVariants(item, location);
			for(int i = 0; i < meta; i++)
			{
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(location.toString(), "inventory"));
			}
		}
	}

	/**
	 * IBlockColorを実装しているブロック登録
	 *
	 * @param blockColor
	 * @param blocks
	 */
	@SideOnly(Side.CLIENT)
	public static void registerBlockColor(IBlockColor blockColor, Block... blocks)
	{
		if(isClient())
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blockColor, blocks);
	}

	/**
	 * IItemColorを実装しているアイテム登録
	 *
	 * @param itemColor
	 * @param items
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemColor(IItemColor itemColor, Item... items)
	{
		if(isClient())
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, items);
	}

	/**
	 * IItemColorを実装しているアイテムブロック登録
	 *
	 * @param itemColor
	 * @param blocks
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemBlockColor(IItemColor itemColor, Block... blocks)
	{
		if(isClient())
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, blocks);
	}

	/**
	 * EntityRender登録
	 *
	 * @param entityClass
	 * @param renderFactory
	 */
	@SideOnly(Side.CLIENT)
	public static <T extends Entity> void registerEntityRendering(Class<T> entityClass, IRenderFactory<? super T> renderFactory)
	{
		if(isClient())
			RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}

	/**
	 * キー登録
	 * @param key
	 */
	@SideOnly(Side.CLIENT)
	public static void registerKeyBinding(KeyBinding key)
	{
		if(isClient())
			ClientRegistry.registerKeyBinding(key);
	}

	/**
	 * ワールドの難易度取得
	 *
	 * @param world
	 * @return
	 */
	public static EnumDifficulty getWorldDifficulty(World world)
	{
		return world.getDifficulty();
	}

	/**
	 * ピースフルモードかどうか
	 *
	 * @param world
	 * @return
	 */
	public static boolean isPeacefulMode(World world)
	{
		return world.getDifficulty() == EnumDifficulty.PEACEFUL;
	}

	/**
	 * ハードモードかどうか
	 *
	 * @param world
	 * @return
	 */
	public static boolean isHardMode(World world)
	{
		return world.getDifficulty() == EnumDifficulty.HARD;
	}

	/**
	 * ある方角から見たときの右側に当たる方角のBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @return
	 */
	public static BlockPos getRightPos(EnumFacing facing, BlockPos pos)
	{
		switch(facing)
		{
		case NORTH:
		default:
			return pos.east();
		case SOUTH:
			return pos.west();
		case WEST:
			return pos.south();
		case EAST:
			return pos.north();
		}
	}

	/**
	 * ある方角から見たときの左側に当たる方角のBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @return
	 */
	public static BlockPos getLeftPos(EnumFacing facing, BlockPos pos)
	{
		switch(facing)
		{
		case NORTH:
		default:
			return pos.west();
		case SOUTH:
			return pos.east();
		case WEST:
			return pos.north();
		case EAST:
			return pos.south();
		}
	}

	/**
	 * offset分前にやったBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @param offset
	 * @return
	 */
	public static BlockPos getFront(EnumFacing facing, BlockPos pos, int offset)
	{
		switch(facing)
		{
		case NORTH:
		default:
			return pos.north(offset);
		case SOUTH:
			return pos.south(offset);
		case WEST:
			return pos.west(offset);
		case EAST:
			return pos.east(offset);
		}
	}

	/**
	 * offset分後ろにやったBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @param offset
	 * @return
	 */
	public static BlockPos getBack(EnumFacing facing, BlockPos pos, int offset)
	{
		switch(facing)
		{
		case NORTH:
		default:
			return pos.south(offset);
		case SOUTH:
			return pos.north(offset);
		case WEST:
			return pos.east(offset);
		case EAST:
			return pos.west(offset);
		}
	}

	/**
	 * ある方角から見た右側の方角を取得
	 * @param facing
	 * @return
	 */
	public static EnumFacing getRightFacing(EnumFacing facing)
	{
		switch(facing)
		{
		case NORTH:
		default:
			return EnumFacing.EAST;
		case SOUTH:
			return EnumFacing.WEST;
		case WEST:
			return EnumFacing.NORTH;
		case EAST:
			return EnumFacing.SOUTH;
		}
	}

	/**
	 * ある方角から見た左側の方角を取得
	 * @param facing
	 * @return
	 */
	public static EnumFacing getLeftFacing(EnumFacing facing)
	{
		switch(facing)
		{
		case NORTH:
		default:
			return EnumFacing.WEST;
		case SOUTH:
			return EnumFacing.EAST;
		case WEST:
			return EnumFacing.SOUTH;
		case EAST:
			return EnumFacing.NORTH;
		}
	}

	/**
	 * AIR Materialかどうか
	 *
	 * @param state
	 * @return
	 */
	public static boolean isAirMaterial(IBlockState state)
	{
		return state.getMaterial() == Material.AIR;
	}

	/**
	 * AIRブロックかどうか
	 *
	 * @param state
	 * @return
	 */
	public static boolean isAir(IBlockState state)
	{
		return state.getBlock() == Blocks.AIR;
	}

	/**
	 * AABBを見やすく？ 16で割ってるから0~16でok
	 *
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static AxisAlignedBB creatAABB(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return new AxisAlignedBB(x1 / 16.0D, y1 / 16.0D, z1 / 16.0D, x2 / 16.0D, y2 / 16.0D, z2 / 16.0D);
	}

	/**
	 * 北を向いている時のAABBを入れればほかの時のやつを返す。
	 *
	 * @param northAabb
	 * @return
	 */
	// TODO
	// public static AxisAlignedBB[] rotateAABB(AxisAlignedBB northAabb)
	// {
	// double minX = northAabb.minX;
	// double minY = northAabb.minY;
	// double minZ = northAabb.minZ;
	// double maxX = northAabb.maxX;
	// double maxY = northAabb.maxY;
	// double maxZ = northAabb.maxZ;
	//
	// return new AxisAlignedBB[]
	// {new AxisAlignedBB(minX, minY, minZ, maxX, maxY, 1D - minZ), new
	// AxisAlignedBB(minZ, minY, minZ, )};
	// }

	/**
	 * コンフィグの配列を見やすく
	 *
	 * @param config
	 * @param name
	 * @param category
	 * @param defaultValues
	 * @param comment
	 * @return
	 */
	public static List<String> getStringList(Configuration config, String name, String category, String[] defaultValues, String comment)
	{
		return Arrays.asList(config.getStringList(name, category, defaultValues, comment));
	}

	/**
	 * スポーンエッグ取得
	 *
	 * @param entityClass
	 * @param stacksize
	 * @return
	 */
	public static ItemStack getSpawnEggItemStack(Class<? extends EntityLiving> entity, int stacksize)
	{
		ItemStack spawn = new ItemStack(Items.SPAWN_EGG, stacksize);
		ItemMonsterPlacer.applyEntityIdToItemStack(spawn, EntityList.getKey(entity));
		return spawn;
	}

	/**
	 * プレイヤーの頭取得
	 *
	 * @param player
	 * @param stacksize
	 * @return
	 */
	public static ItemStack getPlayerHead(EntityPlayer player, int stacksize)
	{
		String name = player.getName();
		ItemStack head = new ItemStack(Items.SKULL, stacksize, 3);
		NBTTagCompound nbt = head.hasTagCompound() ? head.getTagCompound() : new NBTTagCompound();
		NBTTagCompound nbt1 = new NBTTagCompound();
		nbt1.setString("SkullOwner", name);
		nbt.setTag("SkullOwner", nbt1);
		head.setStackDisplayName(TextFormatting.RESET + I18n.translateToLocalFormatted("item.skull.player.name", name));

		return head;
	}

	public static float lightLevel(float level)
	{
		return level / 15F;
	}

	/**
	 * プレイヤーにアイテムを与える
	 * 備忘録用
	 *
	 * @param player
	 * @param stack
	 * @param preferredSlot
	 */
	public static void giveItemToPlayer(EntityPlayer player, ItemStack stack, int preferredSlot)
	{
		ItemHandlerHelper.giveItemToPlayer(player, stack, preferredSlot);
	}

	/**
	 * プレイヤーにアイテムを与える
	 * 備忘録用
	 * @param player
	 * @param stack
	 */
	public static void giveItemToPlayer(EntityPlayer player, ItemStack stack)
	{
		ItemHandlerHelper.giveItemToPlayer(player, stack);
	}

	/**
	 * NBTタグを取得
	 * @param nbt
	 * @param keyName
	 * @return
	 */
	public static NBTTagCompound getTagCompound(NBTTagCompound nbt, String keyName)
	{
		if(nbt == null || !nbt.hasKey(keyName))
			return new NBTTagCompound();

		return nbt.getCompoundTag(keyName);
	}

	/**
	 * iがaからbの(a,bを含む)範囲に入っているか
	 * a <= i <= b
	 *
	 * @param i
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isInAToB(int i, int a, int b)
	{
		return a <= i && i <= b;
	}

	/**
	 * プレイヤーが指定のアーマーを着けているかどうか
	 *
	 * @param player
	 * @param armor
	 * @return
	 */
	public static boolean isEquippedArmor(EntityPlayer player, ItemArmor armor)
	{
		for(ItemStack item : player.getArmorInventoryList())
		{
			if(item != null && item.getItem() == armor)
				return true;
		}

		return false;
	}

	/**
	 * 与えられたItemStackにダメージを1与えた新しいItemStackを返す
	 *
	 * @param stack
	 * @param amount
	 * @return
	 */
	public static ItemStack damageStack(ItemStack stack, int amount)
	{
		return new ItemStack(stack.getItem(), amount, stack.getItemDamage() + 1);
	}

	/**
	 * デフォルトの登録処理を簡略化
	 * @param entityClass
	 * @param entityName
	 * @param id
	 * @param mod
	 * @param trackingRange
	 * @param updateFrequency
	 * @param sendsVelocityUpdates
	 */
	public static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(makeModLocation(entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	/**
	 * サブブロック登録を簡略化
	 * @param block
	 * @param firstMeta
	 * @param endMeta
	 * @param tab
	 * @param list
	 */
	public static void registerSubBlocks(Block block, int firstMeta, int endMeta, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if(tab == block.getCreativeTabToDisplayOn())
		{
			for(int i = firstMeta; i < endMeta; i++)
			{
				list.add(new ItemStack(block, 1, i));
			}
		}
	}

	/**
	 * サブブロック登録を簡略化
	 * 0から始める
	 * @param block
	 * @param meta
	 * @param tab
	 * @param list
	 */
	public static void registerSubBlocks(Block block, int meta, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		registerSubBlocks(block, 0, meta, tab, list);
	}

	/**
	 * サブアイテム登録を簡略化
	 * @param item
	 * @param firstMeta
	 * @param endMeta
	 * @param tab
	 * @param list
	 */
	public static void registerSubItems(Item item, int firstMeta, int endMeta, CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(tab == item.getCreativeTab())
		{
			for(int i = firstMeta; i < endMeta; i++)
			{
				items.add(new ItemStack(item, 1, i));
			}
		}
	}

	/**
	 * サブアイテム登録を簡略化
	 * 0から始める
	 * @param item
	 * @param meta
	 * @param tab
	 * @param list
	 */
	public static void registerSubItems(Item item, int meta, CreativeTabs tab, NonNullList<ItemStack> items)
	{
		registerSubItems(item, 0, meta, tab, items);
	}

	/**
	 * 剣の速度とか変更
	 * @param amount
	 * @param modifierMultimap
	 * @param attribute
	 * @param id
	 * @param multiplier
	 */
	public static void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier)
	{
		// Get the modifiers for the specified attribute
		final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

		// Find the modifier with the specified ID, if any
		final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		if(modifierOptional.isPresent())
		{ // If it exists,
			final AttributeModifier modifier = modifierOptional.get();
			modifiers.remove(modifier); // Remove it
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation())); // Add the new modifier
		}
	}

	/**
	 * よく使うResourceLocationのやつ
	 * @param name
	 * @return
	 */
	public static ResourceLocation makeModLocation(String name)
	{
		return new ResourceLocation(mod_id, name);
	}

	public static void setModId(String id)
	{
		mod_id = id;
	}

	/**
	 * 色のRGBをぐるぐる
	 * @param pos
	 * @return
	 */
	public static float[] getWheelColor(float pos)
	{
		if(pos < 85.0f)
		{
			return new float[]
			{ pos * 3.0F, 255.0f - pos * 3.0f, 0.0f };
		}
		if(pos < 170.0f)
		{
			return new float[]
			{ 255.0f - (pos -= 85.0f) * 3.0f, 0.0f, pos * 3.0f };
		}
		return new float[]
		{ 0.0f, (pos -= 170.0f) * 3.0f, 255.0f - pos * 3.0f };
	}

	/**
	 * ワールドの時間で色がぐるぐるする耐久値バーのRGB
	 * @param player
	 * @return
	 */
	public static int getWheelColorDurabilityBar(EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			float[] color = Utils.getWheelColor(player.world.getTotalWorldTime() % 256);
			return MathHelper.rgb(color[0] / 255F, color[1] / 255F, color[2] / 255F);
		}

		return EnumColor.RED.getColorHex();
	}

	/**
	 * アイテムスタックの変動によるアイテムの再使用を抑制する
	 * @param gun
	 * @param player
	 */
	public static void setUncheckedItemStack(ItemStack gun, EntityPlayer player)
	{
		try
		{
			Class<?> clazz = ReflectionHelper.getClass(gun.getClass().getClassLoader(), "net.minecraft.entity.EntityLivingBase");
			Field field = ReflectionHelper.findField(clazz, "previousEquipment", "field_82180_bT");
			ItemStack[] equipments = (ItemStack[]) field.get(player);
			equipments[0] = gun.copy();
			field.set(player, equipments);
			//			ReflectionHelper.setPrivateValue(lclass, pPlayer, lequipments, "previousEquipment");
			if(player instanceof EntityPlayer)
			{
				Container lctr = player.openContainer;
				for(int i = 0; i < lctr.inventorySlots.size(); i++)
				{
					ItemStack lis = lctr.getSlot(i).getStack();
					if(lis == gun)
					{
						lctr.inventoryItemStacks.set(i, gun.copy());
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
	public static EnumParticleTypes registerParticleType(String enumName, String particleName, int id, boolean shouldIgnoreRange)
	{
		Class<?>[] particleParams =
		{ String.class, int.class, boolean.class };

		return EnumHelper.addEnum(EnumParticleTypes.class, enumName, particleParams, particleName, id, shouldIgnoreRange);
	}

	/**
	 * プレイヤーが動いてるか判定
	 *
	 * @param player
	 * @return
	 */
	public static boolean isPlayerMoving(EntityPlayer player)
	{
		return player.posX != player.lastTickPosX || player.posY != player.lastTickPosY || player.posZ != player.lastTickPosZ;
	}
}