package ryoryo.polishedlib.util.libs;

import javax.annotation.Nullable;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Potions {
	/** 移動速度上昇 */
	public static final Potion SPEED = getRegisteredPotion("speed");
	/** 移動速度低下 */
	public static final Potion SLOWNESS = getRegisteredPotion("slowness");
	/** 採掘速度上昇 */
	public static final Potion HASTE = getRegisteredPotion("haste");
	/** 採掘速度低下 */
	public static final Potion MINING_FATIGUE = getRegisteredPotion("mining_fatigue");
	/** 攻撃力上昇 */
	public static final Potion STRENGTH = getRegisteredPotion("strength");
	/** 即時回復 */
	public static final Potion INSTANT_HEALTH = getRegisteredPotion("instant_health");
	/** 負傷 */
	public static final Potion INSTANT_DAMAGE = getRegisteredPotion("instant_damage");
	/** ジャンプ力上昇 */
	public static final Potion JUMP_BOOST = getRegisteredPotion("jump_boost");
	/** 吐き気 */
	public static final Potion NAUSEA = getRegisteredPotion("nausea");
	/** 再生 */
	public static final Potion REGENERATION = getRegisteredPotion("regeneration");
	/** 耐性 */
	public static final Potion RESISTANCE = getRegisteredPotion("resistance");
	/** 火炎耐性 */
	public static final Potion FIRE_RESISTANCE = getRegisteredPotion("fire_resistance");
	/** 水中呼吸 */
	public static final Potion WATER_BREATHING = getRegisteredPotion("water_breathing");
	/** 透明化 */
	public static final Potion INVISIBILITY = getRegisteredPotion("invisibility");
	/** 盲目 */
	public static final Potion BLINDNESS = getRegisteredPotion("blindness");
	/** 暗視 */
	public static final Potion NIGHT_VISION = getRegisteredPotion("night_vision");
	/** 空腹 */
	public static final Potion HUNGER = getRegisteredPotion("hunger");
	/** 弱体化 */
	public static final Potion WEAKNESS = getRegisteredPotion("weakness");
	/** 毒 */
	public static final Potion POISON = getRegisteredPotion("poison");
	/** ウィザー */
	public static final Potion WITHER = getRegisteredPotion("wither");
	/** 体力増強 */
	public static final Potion HEALTH_BOOST = getRegisteredPotion("health_boost");
	/** 衝撃吸収 */
	public static final Potion ABSORPTION = getRegisteredPotion("absorption");
	/** 満腹度回復 */
	public static final Potion SATURATION = getRegisteredPotion("saturation");
	/** 発光 */
	public static final Potion GLOWING = getRegisteredPotion("glowing");
	/** 浮遊 */
	public static final Potion LEVITATION = getRegisteredPotion("levitation");
	/** 幸運 */
	public static final Potion LUCK = getRegisteredPotion("luck");
	/** 不運 */
	public static final Potion UNLUCK = getRegisteredPotion("unluck");

	@Nullable
	private static Potion getRegisteredPotion(String id) {
		Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

		if(potion == null)
			throw new IllegalStateException("Invalid Enchantment requested: " + id);
		else
			return potion;
	}
}