package ryoryo.polishedlib.util;

import net.minecraft.world.World;

public class ArithmeticUtils {
	/**
	 * 小数をパーセントに変換
	 *
	 * @param base
	 * @return
	 */
	public static float decimalToPercent(float base) {
		return base * 100F;
	}

	/**
	 * 小数をパーミルに変換
	 *
	 * @param base
	 * @return
	 */
	public static float decimalToPermil(float base) {
		return base * 1000F;
	}

	/**
	 * パーセントを通常の小数に変換
	 *
	 * @param percent
	 * @return
	 */
	public static float percentToDecimal(float percent) {
		return percent / 100F;
	}

	/**
	 * パーミルを通常の小数に変換
	 *
	 * @param permil
	 * @return
	 */
	public static float permilToDecimal(float permil) {
		return permil / 1000F;
	}

	/**
	 * tickを秒に
	 *
	 * @param tick
	 * @return
	 */
	public static float tickToSecond(float tick) {
		return tick / 20F;
	}

	/**
	 * tickを分に
	 *
	 * @param tick
	 * @return
	 */
	public static float tickToMinute(float tick) {
		return tickToSecond(tick) / 60;
	}

	/**
	 * 秒をtickに
	 *
	 * @param second
	 * @return
	 */
	public static float secondToTick(float second) {
		return second * 20F;
	}

	public static float minuteToTick(float minute) {
		return secondToTick(minute * 60F);
	}

	/**
	 * ワールド時間の経過日数を得る（ワールド生成時を0日目6:00とし、24:00で1日経過として計算）
	 *
	 * @param world
	 * @return
	 */
	public static int getWorldTimeDay(World world) {
		return (int) ((world.getWorldInfo().getWorldTime() + 6000L) / 24000L);
	}

	/**
	 * ワールド時刻の時を得る（起床時間を6時として計算）
	 *
	 * @param world
	 * @return
	 */
	public static int getWorldTimeHour(World world) {
		return (int) (((world.getWorldInfo().getWorldTime() + 6000L) % 24000L) / 1000L);
	}

	/**
	 * ワールド時刻の分を得る（起床時間を6時として計算）
	 *
	 * @param world
	 * @return
	 */
	public static int getWorldTimeMinutes(World world) {
		return (int) (((world.getWorldInfo().getWorldTime() + 6000L) % 1000L) * 60L / 1000L);
	}
}