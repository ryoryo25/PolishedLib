package ryoryo.polishedlib.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum EnumPlanks implements IStringSerializable
{
	OAK(0, "oak"),
	SPRUCE(1, "spruce"),
	BIRCH(2, "birch"),
	JUNGLE(3, "jungle"),
	ACACIA(4, 0, "acacia"),
	DARK_OAK(5, 1, "dark_oak", "big_oak"),;

	private static final EnumPlanks[] META_LOOKUP = new EnumPlanks[values().length];
	private final int meta;
	private final int logMeta;
	private final String name;
	private final String unlocalizedName;

	//名前がそのまま && Plankのmeta=Logのmeta
	private EnumPlanks(int meta, String name)
	{
		this(meta, meta, name, name);
	}

	//名前がそのまま && Plankのmeta!=Logのmeta
	private EnumPlanks(int meta, int logMeta, String name)
	{
		this(meta, logMeta, name, name);
	}

	//全部
	private EnumPlanks(int meta, int logMeta, String name, String unlocalizedName)
	{
		this.meta = meta;
		this.logMeta = logMeta;
		this.name = name;
		this.unlocalizedName = unlocalizedName;
	}

	/**
	 * Planksのmetaを返す
	 * @return
	 */
	public int getMeta()
	{
		return this.meta;
	}

	/**
	 * Logのmetaを返す
	 * @return
	 */
	public int getLogMeta()
	{
		return this.logMeta;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * big_oakとかの方を返す
	 * @return
	 */
	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}

	/**
	 * enumのlength
	 * @return
	 */
	public static int getLength()
	{
		return EnumPlanks.values().length;
	}

	/**
	 * Logが途中で変わってるからPlanksのmetaに合わせて変えたスタックを返す
	 * @param amount
	 * @param meta
	 * @return
	 */
	public static ItemStack getLogStackByPlanksMeta(int amount, int meta)
	{
		return new ItemStack(getLogBlockByPlanksMeta(meta), amount, byMeta(meta).getLogMeta());
	}

	/**
	 * Leavesも途中で変わってるからPlanksのmetaに合わせて変えたスタックを返す
	 * @param amount
	 * @param meta
	 * @return
	 */
	public static ItemStack getLeavesStackByPlanksMeta(int amount, int meta)
	{
		return new ItemStack(getLeavesBlockByPlanksMeta(meta), amount, byMeta(meta).getLogMeta());
	}

	/**
	 * Logが途中で変わってるからPlanksのmetaに合わせて変えたブロックを返す
	 * @param meta
	 * @return
	 */
	public static Block getLogBlockByPlanksMeta(int meta)
	{
		return meta > 3 ? Blocks.LOG2 : Blocks.LOG;
	}

	/**
	 * Leavesも途中で変わってるからPlanksのmetaに合わせて変えたブロックを返す
	 * @param meta
	 * @return
	 */
	public static Block getLeavesBlockByPlanksMeta(int meta)
	{
		return meta > 3 ? Blocks.LEAVES2 : Blocks.LEAVES;
	}

	public static ItemStack getPlanksStack(int amount, EnumPlanks planks)
	{
		return new ItemStack(Blocks.PLANKS, amount, planks.getMeta());
	}

	/**
	 * vanillaのPlanksのenumに変換
	 * @param planks
	 * @return
	 */
	public static BlockPlanks.EnumType convertToVanillaEnum(EnumPlanks planks)
	{
		switch(planks)
		{
		case OAK:
		default:
			return BlockPlanks.EnumType.OAK;
		case SPRUCE:
			return BlockPlanks.EnumType.SPRUCE;
		case BIRCH:
			return BlockPlanks.EnumType.BIRCH;
		case JUNGLE:
			return BlockPlanks.EnumType.JUNGLE;
		case ACACIA:
			return BlockPlanks.EnumType.ACACIA;
		case DARK_OAK:
			return BlockPlanks.EnumType.DARK_OAK;
		}
	}

	/**
	 * metaに対応したPlanksを返す
	 * @param meta
	 * @return
	 */
	public static EnumPlanks byMeta(int meta)
	{
		if(meta < 0 || meta >= META_LOOKUP.length)
		{
			meta = 0;
		}

		return META_LOOKUP[meta];
	}

	static
	{
		for(EnumPlanks enumplanks : values())
		{
			META_LOOKUP[enumplanks.getMeta()] = enumplanks;
		}
	}
}