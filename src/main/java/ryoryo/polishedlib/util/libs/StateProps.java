package ryoryo.polishedlib.util.libs;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing.Axis;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedlib.util.enums.EnumSimpleFacing;

public class StateProps {
	// Properties
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
}