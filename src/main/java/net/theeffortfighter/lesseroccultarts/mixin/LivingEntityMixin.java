package net.theeffortfighter.lesseroccultarts.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.theeffortfighter.lesseroccultarts.entity.ModEntityStatus;
import net.theeffortfighter.lesseroccultarts.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
	private void overrideTryUseTotem(CallbackInfoReturnable<Boolean> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;

		// Look for a custom totem instead of the vanilla one
		ItemStack totemStack = null;
		for (Hand hand : Hand.values()) {
			ItemStack stack = entity.getStackInHand(hand);
			if (stack.isOf(ModItems.EFFIGY)) {
				totemStack = stack;
				break;
			}
		}

		if (totemStack != null && !totemStack.isDamaged()) {
			// Apply custom effects instead of vanilla
			entity.setHealth(1.0F); // Restore health to 1
			entity.clearStatusEffects(); // Clear all effects
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1)); // Regeneration II
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1)); // Absorption I
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 1)); // Resistance I'

			if (!entity.world.isClient) {
				// Trigger the Totem animation and particles
				totemStack.setDamage(600);
				entity.world.sendEntityStatus(entity, ModEntityStatus.ABBYSAL_EFFIGY);
			}

			cir.setReturnValue(true); // Cancel further processing and return success
		}
	}
}

