package ryoryo.polishedlib.util.enums;

import java.util.stream.Stream;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum EnumColor implements IStringSerializable
{
	// めんどくさい？
	BLACK("black", "Black", 0, 15), // 0x1e1b1b
	RED("red", "Red", 1, 14), // 0xb3312c
	GREEN("green", "Green", 2, 13), // 0x3b511a
	BROWN("brown", "Brown", 3, 12), // 0x51301a
	BLUE("blue", "Blue", 4, 11), // 0x253192
	PURPLE("purple", "Purple", 5, 10), // 0x7b2fbe
	CYAN("cyan", "Cyan", 6, 9), // 0x287697
	LIGHT_GRAY("light_gray", "silver", "LightGray", "Silver", 7, 8), // 0xa0a0af
	GRAY("gray", "Gray", 8, 7), // 0x434343
	PINK("pink", "Pink", 9, 6), // 0xd88198
	LIME("lime", "Lime", 10, 5), // 0x41cd34
	YELLOW("yellow", "Yellow", 11, 4), // 0xdecf2a
	LIGHT_BLUE("light_blue", "LightBlue", 12, 3), // 0x6689d3
	MAGENTA("magenta", "Magenta", 13, 2), // 0xc354cd
	ORANGE("orange", "Orange", 14, 1), // 0xeb8844
	WHITE("white", "White", 15, 0); // 0xf0f0f0

	private final String name;
	private final String vanillaName;
	private final String nameCamelCase;
	private final String vanillaNameCamelCase;
	private final int dyeNum;
	private final int woolNum;
	private static final EnumColor[] META_LOOKUP = new EnumColor[values().length];
	private static final EnumColor[] DYE_DMG_LOOKUP = new EnumColor[values().length];
	public static final String[] COLOR_WOOL = new String[values().length];
	public static final String[] COLOR_DYE = new String[values().length];

	// 薄灰色用
	private EnumColor(String name, String nameCamelCase, int dyeNum, int woolNum) {
		this(name, name, nameCamelCase, nameCamelCase, dyeNum, woolNum);
	}

	private EnumColor(String name, String vanillaName, String nameCamelCase, String vanillaNameCamelCase, int dyeNum, int woolNum) {
		this.name = name;
		this.vanillaName = vanillaName;
		this.nameCamelCase = nameCamelCase;
		this.vanillaNameCamelCase = vanillaNameCamelCase;
		this.dyeNum = dyeNum;
		this.woolNum = woolNum;
	}

	/**
	 * 羊毛と染料で対応するmetaが違うから 染料のmetaを返す
	 * 
	 * @return
	 */
	public int getDyeNumber() {
		return this.dyeNum;
	}

	/**
	 * 羊毛と染料で対応するmetaが違うから 羊毛のmetaを返す
	 * 
	 * @return
	 */
	public int getWoolNumber() {
		return this.woolNum;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * 薄灰色がsilverになる
	 * 
	 * @return
	 */
	public String getVanillaName() {
		return this.vanillaName;
	}

	/**
	 * 一文字目が大文字の名前を返す
	 * 
	 * @return
	 */
	public String getNameCamelCase() {
		return this.nameCamelCase;
	}

	/**
	 * 薄灰色がsilverになる
	 * 
	 * @return
	 */
	public String getVanillNameCamelCase() {
		return this.vanillaNameCamelCase;
	}

	/**
	 * 先頭にblockWoolをつけたものを返す
	 * 
	 * @return
	 */
	public String getWoolOreName() {
		return "blockWool" + this.nameCamelCase;
	}

	/**
	 * 先頭にdyeをつけたものを返す
	 * 
	 * @return
	 */
	public String getDyeOreName() {
		return "dye" + this.nameCamelCase;
	}

	/**
	 * dyeのスタックを返す
	 * 
	 * @param amount
	 * @return
	 */
	public ItemStack getDyeStack(int amount) {
		return new ItemStack(Items.DYE, amount, this.dyeNum);
	}

	/**
	 * woolのスタックを返す
	 * 
	 * @param amount
	 * @return
	 */
	public ItemStack getWoolStack(int amount) {
		return new ItemStack(Blocks.WOOL, amount, this.woolNum);
	}

	/**
	 * 16進数にしたのを返す
	 * 
	 * @return
	 */
	public int getColorValue() {
		return EnumColor.convertToVanillaEnum(this).getColorValue();
	}

	/**
	 * enumのlengthを返すD
	 * 
	 * @return
	 */
	public static int getLength() {
		return EnumColor.values().length;
	}

	/**
	 * EnumColorのstream
	 * 
	 * @return
	 */
	public static Stream<EnumColor> stream() {
		return Stream.of(values());
	}

	/**
	 * vanillaのEnumDyeColorに変換する
	 * 
	 * @param color
	 * @return
	 */
	public static EnumDyeColor convertToVanillaEnum(EnumColor color) {
		switch(color) {
			case BLACK:
			default:
				return EnumDyeColor.BLACK;
			case RED:
				return EnumDyeColor.RED;
			case GREEN:
				return EnumDyeColor.GREEN;
			case BROWN:
				return EnumDyeColor.BROWN;
			case BLUE:
				return EnumDyeColor.BLUE;
			case PURPLE:
				return EnumDyeColor.PURPLE;
			case CYAN:
				return EnumDyeColor.CYAN;
			case LIGHT_GRAY:
				return EnumDyeColor.SILVER;
			case GRAY:
				return EnumDyeColor.GRAY;
			case PINK:
				return EnumDyeColor.PINK;
			case LIME:
				return EnumDyeColor.LIME;
			case YELLOW:
				return EnumDyeColor.YELLOW;
			case LIGHT_BLUE:
				return EnumDyeColor.LIGHT_BLUE;
			case MAGENTA:
				return EnumDyeColor.MAGENTA;
			case ORANGE:
				return EnumDyeColor.ORANGE;
			case WHITE:
				return EnumDyeColor.WHITE;
		}
	}

	/**
	 * 染料のmetaに対応する物を返す
	 * 
	 * @param damage
	 * @return
	 */
	public static EnumColor byDyeDamage(int damage) {
		if(damage < 0 || damage >= DYE_DMG_LOOKUP.length) {
			damage = 0;
		}

		return DYE_DMG_LOOKUP[damage];
	}

	/**
	 * 羊毛のmetaに対応するものを返す
	 * 
	 * @param meta
	 * @return
	 */
	public static EnumColor byMeta(int meta) {
		if(meta < 0 || meta >= META_LOOKUP.length) {
			meta = 0;
		}

		return META_LOOKUP[meta];
	}

	static {
		for(EnumColor color : values()) {
			META_LOOKUP[color.getWoolNumber()] = color;
			DYE_DMG_LOOKUP[color.getDyeNumber()] = color;

			COLOR_WOOL[color.getWoolNumber()] = color.getName();
			COLOR_DYE[color.getDyeNumber()] = color.getName();
		}
	}
}
