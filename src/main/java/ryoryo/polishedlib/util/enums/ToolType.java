package ryoryo.polishedlib.util.enums;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public enum ToolType {

	SHOVEL("shovel"),
	PICKAXE("pickaxe"),
	AXE("axe"),
	SWORD("sword"),
	PAXEL("paxel"),;

	private final String name;

	public static final Set<Block> SHOVEL_EFFECTIVE = ReflectionHelper.getPrivateValue(ItemSpade.class, null, "EFFECTIVE_ON");
	public static final Set<Block> PICKAXE_EFFECTIVE = ReflectionHelper.getPrivateValue(ItemPickaxe.class, null, "EFFECTIVE_ON");
	public static final Set<Block> AXE_EFFECTIVE = ReflectionHelper.getPrivateValue(ItemAxe.class, null, "EFFECTIVE_ON");
	public static final Set<Block> PAXEL_EFFECTIVE = Sets.newHashSet();

	private ToolType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getToolClass() {
		return this != ToolType.SWORD ? this.name : null;
	}

	static {
		PAXEL_EFFECTIVE.addAll(SHOVEL_EFFECTIVE);
		PAXEL_EFFECTIVE.addAll(PICKAXE_EFFECTIVE);
		PAXEL_EFFECTIVE.addAll(AXE_EFFECTIVE);
	}
}