package ryoryo.polishedlib.util;

import java.awt.Color;

import net.minecraft.util.math.MathHelper;

public class ColorHelper {

	/**
	 * Get Alpha from ARGB
	 *
	 * @param argb
	 * @return
	 */
	public static int getAlpha(int argb) {
		return (argb & 0xFF000000) >>> 24;
	}

	/**
	 * Get Red from ARGB
	 *
	 * @param argb
	 * @return
	 */
	public static int getRed(int argb) {
		return (argb & 0x00FF0000) >>> 16;
	}

	/**
	 * Get Green from ARGB
	 *
	 * @param argb
	 * @return
	 */
	public static int getGreen(int argb) {
		return (argb & 0x0000FF00) >> 8;
	}

	/**
	 * Get Blue from ARGB
	 *
	 * @param argb
	 * @return
	 */
	public static int getBlue(int argb) {
		return argb & 0x000000FF;
	}

	/**
	 * Get ARGB from each elements
	 *
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @return
	 */
	public static int getARGB(int r, int g, int b, int a) {
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}

	/**
	 * Get RGB from each elements
	 *
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static int getRGB(int r, int g, int b) {
		return getARGB(r, g, b, 0xFF);
	}

	/**
	 * Clamp num to 0 - 255
	 *
	 * @param num
	 * @return
	 */
	public static int clamp(int num) {
		return MathHelper.clamp(num, 0, 255);
	}

	/**
	 * Convert HSV to ARGB, Alpha=0xFF
	 *
	 * @param hue 0.0 - 1.0
	 * @param saturation 0.0 - 1.0
	 * @param brightness 0.0 - 1.0
	 * @return
	 */
	public static int HSVtoARGB(float hue, float saturation, float brightness) {
		return Color.HSBtoRGB(hue, saturation, brightness);
	}

	/**
	 * Convert HSV to RGB
	 *
	 * @param hue 0.0 - 1.0
	 * @param saturation 0.0 - 1.0
	 * @param brightness 0.0 - 1.0
	 * @return
	 */
	public static int HSVtoRGB(float hue, float saturation, float brightness) {
		return 0x00FFFFFF & HSVtoARGB(hue, saturation, brightness);
	}

	/**
	 * Convert HSL to ARGB, Alpha=0xFF
	 *
	 * @param hue
	 * @param saturation
	 * @param lightness
	 * @return
	 */
	public static int HSLtoARGB(float hue, float saturation, float lightness) {
		int r = 0, g = 0, b = 0;
		float max = lightness <= 0.49 ? lightness + lightness * saturation : lightness + (1F - lightness) * saturation;
		float min = lightness <= 0.49 ? lightness - lightness * saturation : lightness - (1F - lightness) * saturation;
		float diff = max - min;
		int i = (int) (hue * 6F);
		hue *= 360F;

		switch(i) {
			default:
			case 0:
				r = toHex(max);
				g = toHex(min + diff * (hue / 60F));
				b = toHex(min);
				break;
			case 1:
				r = (int) (min + diff * ((120F - hue) / 60F));
				g = toHex(max);
				b = toHex(min);
				break;
			case 2:
				r = toHex(min);
				g = toHex(max);
				b = toHex(min + diff * ((hue - 120F) / 60F));
				break;
			case 3:
				r = toHex(min);
				g = toHex(min + diff * ((240F - hue) / 60F));
				b = toHex(max);
				break;
			case 4:
				r = toHex(min + diff * ((hue - 240F) / 60F));
				g = toHex(min);
				b = toHex(max);
				break;
			case 5:
				r = toHex(max);
				g = toHex(min);
				b = toHex(min + diff * ((360F - hue) / 60F));
				break;
		}

		return 0xFF000000 | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}

	/**
	 * Convert HSL to RGB
	 *
	 * @param hue
	 * @param saturation
	 * @param lightness
	 * @return
	 */
	public static int HSLtoRGB(float hue, float saturation, float lightness) {
		return 0x00FFFFFF & HSLtoARGB(hue, saturation, lightness);
	}

	private static int toHex(float ratio) {
		if(ratio > 1F)
			ratio = 1F;
		if(ratio < 0F)
			ratio = 0F;

		return (int) (ratio * 255F + 0.5F);
	}
}