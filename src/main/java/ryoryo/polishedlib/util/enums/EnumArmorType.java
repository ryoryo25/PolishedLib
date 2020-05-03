package ryoryo.polishedlib.util.enums;

import net.minecraft.inventory.EntityEquipmentSlot;

public enum EnumArmorType
{
	HELMET("helmet", 1, EntityEquipmentSlot.HEAD, EntityEquipmentSlot.HEAD.getIndex()),
	CHESTPLATE("chestplate", 1, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.CHEST.getIndex()),
	LEGGINGS("leggings", 2, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.LEGS.getIndex()),
	BOOTS("boots", 1, EntityEquipmentSlot.FEET, EntityEquipmentSlot.FEET.getIndex());

	private final String name;
	private final int renderIndex;
	private final EntityEquipmentSlot slot;
	private final int slotIndex;

	private EnumArmorType(String name, int renderIndex, EntityEquipmentSlot equipmentSlot, int slotIndex) {
		this.name = name;
		this.renderIndex = renderIndex;
		this.slot = equipmentSlot;
		this.slotIndex = slotIndex;
	}

	public String getName() {
		return this.name;
	}

	public int getRenderIndex() {
		return this.renderIndex;
	}

	public EntityEquipmentSlot getSlot() {
		return this.slot;
	}

	public int getSlotIndex() {
		return this.slotIndex;
	}
}
