package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class UncursingAmulet extends Item {

    private final Map<StatusEffect, StatusEffectInstance> currentEffects = new HashMap<>();
    private static final Map<StatusEffect, StatusEffect> OPPOSITE_EFFECTS = Map.of(
            StatusEffects.WITHER, StatusEffects.REGENERATION,
            StatusEffects.POISON, StatusEffects.REGENERATION,
            StatusEffects.SLOWNESS, StatusEffects.SPEED,
            StatusEffects.MINING_FATIGUE, StatusEffects.HASTE,
            StatusEffects.WEAKNESS, StatusEffects.STRENGTH,
            StatusEffects.BLINDNESS, StatusEffects.NIGHT_VISION,
            StatusEffects.HUNGER, StatusEffects.SATURATION
    );

    public UncursingAmulet(Settings settings) {
        super(settings);
    }

    /**
     * Stores the current status effects of the given entity into a map.
     *
     * @param entity The entity whose status effects are being tracked.
     */
    private void trackStatusEffects(LivingEntity entity) {
        currentEffects.clear();
        entity.getStatusEffects().forEach(effectInstance ->
                currentEffects.put(effectInstance.getEffectType(), effectInstance));
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            activate(this.getDefaultStack(), user);
        }

        return super.use(world, user, hand);
    }

    /**
     * Checks if any of the entity's current effects are harmful.
     *
     * @return True if there are harmful effects, otherwise false.
     */
    private boolean hasNegativeEffect() {
        return currentEffects.keySet().stream()
                .anyMatch(effect -> effect.getCategory() == StatusEffectCategory.HARMFUL);
    }

    /**
     * Applies the opposite of all harmful effects to the given entity.
     *
     * @param entity The entity to apply the opposite effects to.
     */
    private void applyOppositeEffects(LivingEntity entity) {
        currentEffects.entrySet().stream()
                .filter(entry -> entry.getKey().getCategory() == StatusEffectCategory.HARMFUL)
                .map(Map.Entry::getKey)
                .forEach(effect -> {
                    StatusEffect oppositeEffect = getOppositeEffect(effect);
                    if (oppositeEffect != null) {
                        StatusEffectInstance instance = currentEffects.get(effect);
                        entity.removeStatusEffect(instance.getEffectType());
                        entity.addStatusEffect(new StatusEffectInstance(
                                oppositeEffect, instance.getDuration(), instance.getAmplifier()
                        ));
                    }
                });
    }

    /**
     * Maps harmful effects to their corresponding positive effects.
     *
     * @param effect The harmful effect to reverse.
     * @return The corresponding positive effect, or null if no opposite exists.
     */
    private StatusEffect getOppositeEffect(StatusEffect effect) {
        return OPPOSITE_EFFECTS.getOrDefault(effect, null);
    }

    /**
     * Activates the amulet's effect, reversing any harmful effects on the entity.
     *
     * @param stack  The item stack representing this item.
     * @param entity The entity using the item.
     */
    public void activate(ItemStack stack, LivingEntity entity) {
        trackStatusEffects(entity);
        if (hasNegativeEffect() & stack != null) {
            applyOppositeEffects(entity);
        }
    }
}
