package ryoryo.polishedlib.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.Multimap;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import ryoryo.polishedlib.PolishedLib;
import ryoryo.polishedlib.util.enums.EnumAxis;
import ryoryo.polishedlib.util.enums.EnumColor;

public class Utils {
	// zero aabb
	public static final AxisAlignedBB ZERO_AABB = creatAABB(0, 0, 0, 0, 0, 0);

	/**
	 * クラスがロードされているかどうか調べる。
	 * isModLoadedでは判定できないjar導入系のMODの導入判定や、バージョンチェックなどに利用可能。
	 *
	 * @param classname
	 *            パッケージまでを含めた完全装飾名を指定する必要がある。例）"minecraftAPI.MCAPI"
	 * @since 1.4
	 */
	public static boolean isClassLoaded(String classname) {
		try {
			Class.forName(classname);
		}
		catch(ClassNotFoundException e) {
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
	public static String/* int */ transformDecimalToBinary(int baseDecimal) {
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
	public static String/* int */ transformDecimalToOctal(int baseDecimal) {
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
	public static String/* int */ transformDecimalToHexadecimal(int baseDecimal) {
		return /* String hex = */"0x" + Integer.toHexString(baseDecimal);
		// return Integer.valueOf(hex);
	}

	/**
	 * プレイヤーのチャット欄にメッセージを追加
	 *
	 * @param player
	 * @param message
	 */
	public static void sendChat(EntityPlayer player, String message, Object... args) {
		player.sendMessage(new TextComponentTranslation(message, args));
	}

	public static void sendChat(String message, Object... args) {
		sendChat(getPlayer(), message, args);
	}

	public static void sendChat(EntityPlayer player, Object message, Object... args) {
		sendChat(player, String.valueOf(message), args);
	}

	public static void sendChat(Object message, Object... args) {
		sendChat(String.valueOf(message), args);
	}

	/**
	 * プレイヤーのホットバーの上にメッセージを表示(ベッドで寝れないときみたいに)
	 *
	 * @param player
	 * @param message
	 * @param args
	 */
	public static void sendPopUpMessage(EntityPlayer player, String message, Object... args) {
		player.sendStatusMessage(new TextComponentTranslation(message, args), true);
	}

	public static void sendPopUpMessage(String message, Object... args) {
		sendPopUpMessage(getPlayer(), message, args);
	}

	public static void sendPopUpMessage(EntityPlayer player, Object message, Object... args) {
		sendPopUpMessage(player, String.valueOf(message), args);
	}

	public static void sendPopUpMessage(Object message, Object... args) {
		sendPopUpMessage(String.valueOf(message), args);
	}

	/**
	 * 翻訳
	 *
	 * @param key
	 * @return
	 */
	public static String translatableString(String key, Object... formats) {
		return I18n.translateToLocalFormatted(key, formats);
	}

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
	public static int getEntityDirectionS(Entity entity) {
		return MathHelper.floor((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	}

	/**
	 * Entityの向いている方角を、東西南北で取得する。
	 *
	 * @return 0北 1東 2南 3西
	 * @since 1.4
	 */
	public static int getEntityDirection(Entity entity) {
		return MathHelper.floor((double) (entity.rotationYaw / 90.0F) + 2.5D) & 3;
	}

	/**
	 * Entityの向いている方角を、8方向で取得する。
	 *
	 * @return 0北 1北東 2東 3南東 4南 5南西 6西 7北西
	 * @since 1.20.170401
	 */
	public static int getEntityDirection8(Entity entity) {
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
	public static int getEntityCoordinate(Entity entity) {
		return MathHelper.floor((double) (entity.rotationYaw / 90.0F) + 2) & 3;
	}

	/**
	 * プレイヤー(クライアント)を得る
	 *
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public static EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}

	/**
	 * ワールド(クライアント)を得る
	 *
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public static World getWorld() {
		return Minecraft.getMinecraft().world;
	}

	/**
	 * ディメンションIDを得る
	 *
	 * @param world
	 * @return
	 */
	public static int getDimensionId(World world) {
		return world.provider.getDimension();
	}

	@SideOnly(Side.CLIENT)
	public static int getDimensionId() {
		return getDimensionId(getWorld());
	}

	/**
	 * ディメンションの名前を得る
	 *
	 * @param world
	 * @return
	 */
	public static String getDimensionName(World world) {
		return world.provider.getDimensionType().getName();
	}

	@SideOnly(Side.CLIENT)
	public static String getDimensionName() {
		return getDimensionName(getWorld());
	}

	/**
	 * クリエイティブ判定
	 *
	 * @param player
	 * @return
	 */
	public static boolean isCreative(EntityPlayer player) {
		return player.capabilities.isCreativeMode;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isCreative() {
		return isCreative(getPlayer());
	}

	/**
	 * 飛行判定
	 *
	 * @param player
	 * @return
	 */
	public static final boolean isFlying(EntityPlayer player) {
		return player.capabilities.isFlying;
	}

	@SideOnly(Side.CLIENT)
	public static final boolean isFlying() {
		return isFlying(getPlayer());
	}

	/**
	 * スニークのキーが押されているかどうか
	 *
	 * @return
	 */
	public static boolean isSneakKeyDown() {
		return isClient() ? Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown() : false;
	}

	/**
	 * ジャンプのキーが押されているかどうか
	 *
	 * @return
	 */
	public static boolean isJumpKeyDown() {
		return isClient() ? Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() : false;
	}

	/**
	 * たぶんサバイバル(アドベンチャー)判定
	 *
	 * @param player
	 * @return
	 */
	public static final boolean isSurvival(EntityPlayer player) {
		return !isCreative(player) && !player.isSpectator();
	}

	@SideOnly(Side.CLIENT)
	public static final boolean isSurvival() {
		return isSurvival(getPlayer());
	}

	/**
	 * プレイヤーがメインハンドに持ってるアイテムスタックを得る
	 *
	 * @param player
	 * @return
	 */
	public static ItemStack getHeldItemStackMainhand(EntityPlayer player) {
		return player.getHeldItemMainhand();
	}

	@SideOnly(Side.CLIENT)
	public static ItemStack getHeldItemStackMainhand() {
		return getHeldItemStackMainhand(getPlayer());
	}

	/**
	 * プレイヤーがオフハンドに持ってるアイテムスタックを得る
	 *
	 * @param player
	 * @return
	 */
	public static ItemStack getHeldItemStackOffhand(EntityPlayer player) {
		return player.getHeldItemOffhand();
	}

	@SideOnly(Side.CLIENT)
	public static ItemStack getHeldItemStackOffhand() {
		return getHeldItemStackOffhand(getPlayer());
	}

	/**
	 * 両方のアイテムスタック
	 *
	 * @param player
	 * @return
	 */
	public static List<ItemStack> getHeldItemStacks(EntityPlayer player) {
		return (List<ItemStack>) player.getHeldEquipment();
	}

	@SideOnly(Side.CLIENT)
	public static List<ItemStack> getHeldItemStacks() {
		return getHeldItemStacks(getPlayer());
	}

	/**
	 * プレイヤーがメインハンドに持ってるアイテムを得る
	 *
	 * @param player
	 * @return
	 */
	public static Item getHeldItemMainhand(EntityPlayer player) {
		return getHeldItemStackMainhand(player).getItem();
	}

	@SideOnly(Side.CLIENT)
	public static Item getHeldItemMainhand() {
		return getHeldItemMainhand(getPlayer());
	}

	/**
	 * プレイヤーがオフハンドに持ってるアイテムを得る
	 *
	 * @param player
	 * @return
	 */
	public static Item getHeldItemOffhand(EntityPlayer player) {
		return getHeldItemStackOffhand(player).getItem();
	}

	@SideOnly(Side.CLIENT)
	public static Item getHeldItemOffhand() {
		return getHeldItemOffhand(getPlayer());
	}

	/**
	 * 両方のアイテム
	 *
	 * @param player
	 * @return
	 */
	public static List<Item> getHeldItems(EntityPlayer player) {
		return ((List<ItemStack>) player.getHeldEquipment()).stream().map(stack -> stack.getItem()).collect(Collectors.toList());
	}

	public static List<Item> getHeldItems() {
		return getHeldItems(getPlayer());
	}

	/**
	 * 指定した鉱石辞書名に登録されているアイテムがあるか調べる
	 *
	 * @param oreName
	 * @return
	 */
	public static boolean isOreDictLoaded(String oreName) {
		List<ItemStack> item = OreDictionary.getOres(oreName);

		if(item.size() > 0)
			return true;
		else {
			PolishedLib.LOGGER.info("The mod which have a " + oreName + " isn't installed.");
			return false;
		}
	}

	/**
	 * 指定した鉱石辞書名のアイテムがあるか調べで，あればそのうちの1つを返す
	 *
	 * @param oreName
	 * @return
	 */
	public static ItemStack getItemFromOreDict(String oreName) {
		List<ItemStack> item = OreDictionary.getOres(oreName);

		if(isOreDictLoaded(oreName)) {
			return new ItemStack(item.get(0).getItem(), 1, item.get(0).getItemDamage());
		} else
			return ItemStack.EMPTY;
	}

	/**
	 * 鉱石辞書を探す
	 *
	 * @param stack
	 * @return
	 */
	public static List<ItemStack> findOreDict(ItemStack stack) {
		String[] oredicts = OreDictionary.getOreNames();
		List<ItemStack> result = new ArrayList<ItemStack>();

		try {
			for(int oreId : OreDictionary.getOreIDs(stack)) {
				if(oreId >= 0) {
					String oreName = OreDictionary.getOreName(oreId);
					for(String regex : oredicts) {
						if(oreName.toLowerCase().matches(regex)) {
							result = OreDictionary.getOres(oreName);
						}
					}
				}
			}

		}
		catch(NullPointerException e) {}

		return result;
	}

	/**
	 * 草からドロップするものを追加 MinecraftForge.addGrassSeed(seed, weight)の備忘録用
	 *
	 * @param seed
	 * @param weight
	 */
	public static void addDropSeedFromGrass(ItemStack seed, int weight) {
		MinecraftForge.addGrassSeed(seed, weight);
	}

	/**
	 * スポーンエッグに新しくmobを追加
	 *
	 * @param entityId
	 * @param primaryColor
	 * @param secondaryColor
	 */
	public static void addSpawnEgg(Class<? extends EntityLiving> entity, int primaryColor, int secondaryColor) {
		EntityRegistry.registerEgg(EntityList.getKey(entity), primaryColor, secondaryColor);
	}

	/**
	 * スポーンエッグがあるかどうか
	 *
	 * @param entity
	 * @return
	 */
	public static boolean hasSpawnEgg(EntityLiving entity) {
		return EntityList.ENTITY_EGGS.containsKey(EntityList.getKey(entity));
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
	public static void addSpawnEntity(Class<? extends EntityLiving> entity, int weight, int minAmount, int maxAmount, EnumCreatureType type, Biome... biomes) {
		EntityRegistry.addSpawn(entity, weight, minAmount, maxAmount, type, biomes);
		// 大体同義
		// BiomeGenBase.hell.getSpawnableList(EnumCreatureType.monster).add(new
		// BiomeGenBase.SpawnListEntry(EntityBlaze.class, weight, minAmount,
		// maxAmount));
	}

	/**
	 * スポナー追加
	 *
	 * @param entityName
	 * @param rarity
	 */
	public static void addMobSpawner(ResourceLocation entityName, int rarity) {
		DungeonHooks.addDungeonMob(entityName, rarity);
	}

	/**
	 * クライアントかサーバかサイドを得る
	 *
	 * @return
	 */
	public static Side getSide() {
		return FMLCommonHandler.instance().getSide();
	}

	/**
	 * サーバサイドかどうか
	 *
	 * @return
	 */
	public static boolean isServer() {
		return FMLCommonHandler.instance().getSide().isServer();
	}

	/**
	 * クライアントサイドかどうか
	 *
	 * @return
	 */
	public static boolean isClient() {
		return FMLCommonHandler.instance().getSide().isClient();
	}

	/**
	 * ワールドの難易度取得
	 *
	 * @param world
	 * @return
	 */
	public static EnumDifficulty getWorldDifficulty(World world) {
		return world.getDifficulty();
	}

	/**
	 * ピースフルモードかどうか
	 *
	 * @param world
	 * @return
	 */
	public static boolean isPeacefulMode(World world) {
		return world.getDifficulty() == EnumDifficulty.PEACEFUL;
	}

	/**
	 * ハードモードかどうか
	 *
	 * @param world
	 * @return
	 */
	public static boolean isHardMode(World world) {
		return world.getDifficulty() == EnumDifficulty.HARD;
	}

	/**
	 * AIR Materialかどうか
	 *
	 * @param state
	 * @return
	 */
	public static boolean isAirMaterial(IBlockState state) {
		return state.getMaterial() == Material.AIR;
	}

	/**
	 * AIRブロックかどうか
	 *
	 * @param state
	 * @return
	 */
	public static boolean isAir(IBlockState state) {
		return state.getBlock() == Blocks.AIR;
	}

	/**
	 * ある方角から見たときの右側に当たる方角のBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @return
	 */
	public static BlockPos getRightPos(BlockPos pos, EnumFacing facing, int offset) {
		return pos.offset(getRightFacing(facing), offset);
	}

	public static BlockPos getRightPos(BlockPos pos, EnumFacing facing) {
		return getRightPos(pos, facing, 1);
	}

	/**
	 * ある方角から見たときの左側に当たる方角のBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @return
	 */
	public static BlockPos getLeftPos(BlockPos pos, EnumFacing facing, int offset) {
		return pos.offset(getLeftFacing(facing), offset);
	}

	public static BlockPos getLeftPos(BlockPos pos, EnumFacing facing) {
		return getLeftPos(pos, facing, 1);
	}

	/**
	 * offset分前にやったBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @param offset
	 * @return
	 */
	public static BlockPos getFront(EnumFacing facing, BlockPos pos, int offset) {
		return pos.offset(facing, offset);
	}

	public static BlockPos getFront(EnumFacing facing, BlockPos pos) {
		return getFront(facing, pos, 1);
	}

	/**
	 * offset分後ろにやったBlockPos取得
	 *
	 * @param facing
	 * @param pos
	 * @param offset
	 * @return
	 */
	public static BlockPos getBack(EnumFacing facing, BlockPos pos, int offset) {
		return pos.offset(facing.getOpposite(), offset);
	}

	public static BlockPos getBack(EnumFacing facing, BlockPos pos) {
		return getBack(facing, pos, 1);
	}

	/**
	 * ある方角から見た右側の方角を取得
	 *
	 * @param facing
	 * @return
	 */
	public static EnumFacing getRightFacing(EnumFacing facing) {
		return facing.rotateY();
	}

	/**
	 * ある方角から見た左側の方角を取得
	 *
	 * @param facing
	 * @return
	 */
	public static EnumFacing getLeftFacing(EnumFacing facing) {
		return facing.rotateYCCW();
	}

	/**
	 * ターゲットのFacingをベースのFacingからのRotationで表す
	 *
	 * @param baseFacing
	 * @param facing
	 * @return
	 */
	public static Rotation getRotationFromBase(EnumFacing baseFacing, EnumFacing facing) {
		switch(baseFacing) {
			case NORTH:
				switch(facing) {
					default:
					case NORTH:
						return Rotation.NONE;
					case SOUTH:
						return Rotation.CLOCKWISE_180;
					case WEST:
						return Rotation.COUNTERCLOCKWISE_90;
					case EAST:
						return Rotation.CLOCKWISE_90;
				}
			case SOUTH:
				switch(facing) {
					case NORTH:
						return Rotation.CLOCKWISE_180;
					default:
					case SOUTH:
						return Rotation.NONE;
					case WEST:
						return Rotation.CLOCKWISE_90;
					case EAST:
						return Rotation.COUNTERCLOCKWISE_90;
				}
			case WEST:
				switch(facing) {
					case NORTH:
						return Rotation.CLOCKWISE_90;
					case SOUTH:
						return Rotation.COUNTERCLOCKWISE_90;
					default:
					case WEST:
						return Rotation.NONE;
					case EAST:
						return Rotation.CLOCKWISE_180;
				}
			case EAST:
				switch(facing) {
					case NORTH:
						return Rotation.COUNTERCLOCKWISE_90;
					case SOUTH:
						return Rotation.CLOCKWISE_90;
					case WEST:
						return Rotation.CLOCKWISE_180;
					default:
					case EAST:
						return Rotation.NONE;
				}
			default:
				return Rotation.NONE;
		}
	}

	public static Rotation getRotationFromNorth(EnumFacing facing) {
		switch(facing) {
			default:
			case NORTH:
				return Rotation.NONE;
			case SOUTH:
				return Rotation.CLOCKWISE_180;
			case WEST:
				return Rotation.COUNTERCLOCKWISE_90;
			case EAST:
				return Rotation.CLOCKWISE_90;
		}
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
	public static AxisAlignedBB creatAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
		return new AxisAlignedBB(x1 / 16.0D, y1 / 16.0D, z1 / 16.0D, x2 / 16.0D, y2 / 16.0D, z2 / 16.0D);
	}

	/**
	 * ベースのAABBをRotation分回転させる
	 *
	 * @param baseAabb
	 * @param facing
	 * @return
	 */
	public static AxisAlignedBB rotateAABB(AxisAlignedBB baseAabb, Rotation rotation) {
		double minX = baseAabb.minX;
		double minY = baseAabb.minY;
		double minZ = baseAabb.minZ;
		double maxX = baseAabb.maxX;
		double maxY = baseAabb.maxY;
		double maxZ = baseAabb.maxZ;

		switch(rotation) {
			default:
			case NONE:
				return baseAabb;
			case CLOCKWISE_90:
				return new AxisAlignedBB(1.0D - maxZ, minY, minX, 1.0D - minZ, maxY, maxX);
			case CLOCKWISE_180:
				return new AxisAlignedBB(1.0D - maxX, minY, 1.0D - maxZ, 1.0D - minX, maxY, 1.0D - minZ);
			case COUNTERCLOCKWISE_90:
				return new AxisAlignedBB(minZ, minY, 1.0D - maxX, maxZ, maxY, 1.0D - minX);
		}
	}

	/**
	 * Axisの進む向きを法線ベクトルとする面(Axis.X => yz平面)を対称として反転させる
	 *
	 * @param baseAabb
	 * @param axis
	 * @return
	 */
	public static AxisAlignedBB flipAABB(AxisAlignedBB baseAabb, EnumAxis axis) {
		double minX = baseAabb.minX;
		double minY = baseAabb.minY;
		double minZ = baseAabb.minZ;
		double maxX = baseAabb.maxX;
		double maxY = baseAabb.maxY;
		double maxZ = baseAabb.maxZ;

		switch(axis) {
			default:
			case NONE:
				return baseAabb;
			case X:
				return new AxisAlignedBB(1.0D - maxX, minY, minZ, 1.0D - minX, maxY, maxZ);
			case Y:
				return new AxisAlignedBB(minX, 1.0D - maxY, minZ, maxX, 1.0D - minY, maxZ);
			case Z:
				return new AxisAlignedBB(minX, minY, 1.0D - maxZ, maxX, maxY, 1.0D - minZ);
		}
	}

	/**
	 * doFlip==trueなら，上下を反転させる．
	 *
	 * @param baseAabb
	 * @param doFlip
	 * @return
	 */
	public static AxisAlignedBB flipYAABB(AxisAlignedBB baseAabb, boolean doFlip) {
		EnumAxis axis = doFlip ? EnumAxis.Y : EnumAxis.NONE;
		return flipAABB(baseAabb, axis);
	}

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
	public static List<String> getStringList(Configuration config, String name, String category, String[] defaultValues, String comment) {
		return Arrays.asList(config.getStringList(name, category, defaultValues, comment));
	}

	/**
	 * スポーンエッグ取得
	 *
	 * @param entityClass
	 * @param stacksize
	 * @return
	 */
	public static ItemStack getSpawnEggItemStack(Class<? extends EntityLiving> entity, int stacksize) {
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
	public static ItemStack getPlayerHead(EntityPlayer player, int stacksize) {
		String name = player.getName();
		ItemStack head = new ItemStack(Items.SKULL, stacksize, 3);
		NBTTagCompound nbt = head.hasTagCompound() ? head.getTagCompound() : new NBTTagCompound();
		NBTTagCompound nbt1 = new NBTTagCompound();
		nbt1.setString("SkullOwner", name);
		nbt.setTag("SkullOwner", nbt1);
		head.setStackDisplayName(TextFormatting.RESET + I18n.translateToLocalFormatted("item.skull.player.name", name));

		return head;
	}

	public static float lightLevel(float level) {
		return level / 15F;
	}

	/**
	 * プレイヤーにアイテムを与える 備忘録用
	 *
	 * @param player
	 * @param stack
	 * @param preferredSlot
	 */
	public static void giveItemToPlayer(EntityPlayer player, ItemStack stack, int preferredSlot) {
		ItemHandlerHelper.giveItemToPlayer(player, stack, preferredSlot);
	}

	/**
	 * プレイヤーにアイテムを与える 備忘録用
	 *
	 * @param player
	 * @param stack
	 */
	public static void giveItemToPlayer(EntityPlayer player, ItemStack stack) {
		ItemHandlerHelper.giveItemToPlayer(player, stack);
	}

	/**
	 * NBTタグを取得
	 *
	 * @param nbt
	 * @param keyName
	 * @return
	 */
	public static NBTTagCompound getTagCompound(NBTTagCompound nbt, String keyName) {
		if(nbt == null || !nbt.hasKey(keyName))
			return new NBTTagCompound();

		return nbt.getCompoundTag(keyName);
	}

	/**
	 * iがaからbの(a,bを含む)範囲に入っているか a <= i <= b
	 *
	 * @param i
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isInAToB(int i, int a, int b) {
		return a <= i && i <= b;
	}

	/**
	 * プレイヤーが指定のアーマーを着けているかどうか
	 *
	 * @param player
	 * @param armor
	 * @return
	 */
	public static boolean isEquippedArmor(EntityPlayer player, ItemArmor armor) {
		for(ItemStack item : player.getArmorInventoryList()) {
			if(!item.isEmpty() && item.getItem() == armor)
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
	public static ItemStack damageStack(ItemStack stack, int amount) {
		return new ItemStack(stack.getItem(), amount, stack.getItemDamage() + 1);
	}

	/**
	 * 剣の速度とか変更
	 *
	 * @param amount
	 * @param modifierMultimap
	 * @param attribute
	 * @param id
	 * @param multiplier
	 */
	public static void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier) {
		// Get the modifiers for the specified attribute
		final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

		// Find the modifier with the specified ID, if any
		final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		if(modifierOptional.isPresent()) { // If it exists,
			final AttributeModifier modifier = modifierOptional.get();
			modifiers.remove(modifier); // Remove it
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation())); // Add
																																					// the
																																					// new
																																					// modifier
		}
	}

	public static ResourceLocation makeModLocation(String modId, String name) {
		return new ResourceLocation(modId, name);
	}

	/**
	 * 色のRGBをぐるぐる
	 *
	 * @param pos
	 * @return
	 */
	public static float[] getWheelColor(float pos) {
		if(pos < 85.0f) {
			return new float[] { pos * 3.0F, 255.0f - pos * 3.0f, 0.0f };
		}
		if(pos < 170.0f) {
			return new float[] { 255.0f - (pos -= 85.0f) * 3.0f, 0.0f, pos * 3.0f };
		}
		return new float[] { 0.0f, (pos -= 170.0f) * 3.0f, 255.0f - pos * 3.0f };
	}

	/**
	 * ワールドの時間で色がぐるぐるする耐久値バーのRGB
	 *
	 * @param player
	 * @return
	 */
	public static int getWheelColorDurabilityBar(EntityPlayer player) {
		if(player != null && player.world != null) {
			float[] color = Utils.getWheelColor(player.world.getTotalWorldTime() % 256);
			return MathHelper.rgb(color[0] / 255F, color[1] / 255F, color[2] / 255F);
		}

		return EnumColor.RED.getColorValue();
	}

	/**
	 * アイテムスタックの変動によるアイテムの再使用を抑制する
	 *
	 * @param gun
	 * @param player
	 */
	public static void setUncheckedItemStack(ItemStack gun, EntityPlayer player) {
		try {
			Class<?> clazz = ReflectionHelper.getClass(gun.getClass().getClassLoader(), "net.minecraft.entity.EntityLivingBase");
			Field field = ReflectionHelper.findField(clazz, "previousEquipment", "field_82180_bT");
			ItemStack[] equipments = (ItemStack[]) field.get(player);
			equipments[0] = gun.copy();
			field.set(player, equipments);
			// ReflectionHelper.setPrivateValue(lclass, pPlayer, lequipments,
			// "previousEquipment");
			if(player instanceof EntityPlayer) {
				Container lctr = player.openContainer;
				for(int i = 0; i < lctr.inventorySlots.size(); i++) {
					ItemStack lis = lctr.getSlot(i).getStack();
					if(lis == gun) {
						lctr.inventoryItemStacks.set(i, gun.copy());
						break;
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * プレイヤーが動いてるか判定
	 *
	 * @param player
	 * @return
	 */
	public static boolean isPlayerMoving(EntityPlayer player) {
		return player.posX != player.lastTickPosX || player.posY != player.lastTickPosY || player.posZ != player.lastTickPosZ;
	}

	/**
	 * stackにenchantmentがついているかどうか
	 *
	 * @param stack
	 * @param enchantment
	 * @return
	 */
	public static boolean hasEnchant(ItemStack stack, Enchantment enchantment) {
		NBTTagList enchants = stack.getEnchantmentTagList();
		for(int i = 0; i < enchants.tagCount(); i++) {
			NBTTagCompound enchant = enchants.getCompoundTagAt(i);
			if(enchant.getInteger("id") == Enchantment.getEnchantmentID(enchantment))
				return true;
		}

		return false;
	}
}