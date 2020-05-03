package ryoryo.polishedlib.util.enums;

import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public enum EnumAxis implements IStringSerializable
{
	X("x"),
	Y("y"),
	Z("z"),
	NONE("none");

	private static final Map<String, EnumAxis> NAME_LOOKUP = Maps.newHashMap();
	private final String name;

	private EnumAxis(String name) {
		this.name = name;
	}

	/**
	 * Get the axis specified by the given name
	 */
	@Nullable
	public static EnumAxis byName(String name) {
		return name == null ? null : (EnumAxis) NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
	}

	public String toString() {
		return this.name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public EnumFacing.Axis toFacingAxis() {
		switch(this) {
			default:
			case X:
				return EnumFacing.Axis.X;
			case Y:
				return EnumFacing.Axis.Y;
			case Z:
				return EnumFacing.Axis.Z;
		}
	}

	public static EnumAxis fromFacingAxis(EnumFacing.Axis axis) {
		switch(axis) {
			case X:
				return EnumAxis.X;
			case Y:
				return EnumAxis.Y;
			case Z:
				return EnumAxis.Z;
			default:
				return EnumAxis.NONE;
		}
	}

	static {
		for(EnumAxis axis : values()) {
			NAME_LOOKUP.put(axis.getName().toLowerCase(Locale.ROOT), axis);
		}
	}
}