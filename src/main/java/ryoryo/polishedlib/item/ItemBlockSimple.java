package ryoryo.polishedlib.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockSimple extends ItemBlock {

	public ItemBlockSimple(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}

	// ItemStackのdamage値からmetadataの値を返す。
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}