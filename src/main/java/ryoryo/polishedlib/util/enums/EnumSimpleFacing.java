package ryoryo.polishedlib.util.enums;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.IStringSerializable;

public enum EnumSimpleFacing implements IStringSerializable
{
	NORTH(0, "north", EnumFacing.NORTH, Axis.Z),
	WEST(1, "west", EnumFacing.WEST, Axis.X),;

	private static final EnumSimpleFacing[] META_LOOKUP = new EnumSimpleFacing[values().length];
	private final int index;
	private final String name;
	private final EnumFacing facing;
	private final Axis axis;

	private EnumSimpleFacing(int index, String name, EnumFacing facing, Axis axis) {
		this.index = index;
		this.name = name;
		this.facing = facing;
		this.axis = axis;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getIndex() {
		return this.index;
	}

	public static int getLength() {
		return EnumSimpleFacing.values().length;
	}

	public static EnumSimpleFacing convertToNormalFacing(EnumFacing facing) {
		switch(facing) {
			case NORTH:
			case SOUTH:
			default:
				return EnumSimpleFacing.NORTH;
			case WEST:
			case EAST:
				return EnumSimpleFacing.WEST;
		}
	}

	public EnumFacing getNormalFacing() {
		return this.facing;
	}

	public Axis getAxis() {
		return this.axis;
	}

	public static EnumSimpleFacing byMeta(int metadata) {
		if(metadata < 0 || metadata >= META_LOOKUP.length) {
			metadata = 0;
		}

		return META_LOOKUP[metadata];
	}

	static {
		for(EnumSimpleFacing facing : values()) {
			META_LOOKUP[facing.getIndex()] = facing;
		}
	}
}