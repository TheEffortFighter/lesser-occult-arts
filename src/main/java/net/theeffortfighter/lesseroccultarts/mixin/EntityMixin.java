package net.theeffortfighter.lesseroccultarts.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.theeffortfighter.lesseroccultarts.entity.ModEntityStatus;
import net.theeffortfighter.lesseroccultarts.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "handleStatus", at = @At("HEAD"), cancellable = true)
    private void overrideTotemBehavior(byte status, CallbackInfo ci) {
        // Check if the status is for Totem activation (35)
        if ((Object) this instanceof LivingEntity livingEntity)
            if (status == ModEntityStatus.ABBYSAL_EFFIGY) {
                // Replace with a custom item (example: Nether Star)
                ItemStack customItem = new ItemStack(ModItems.EFFIGY); // Use your custom item here
                MinecraftClient.getInstance().gameRenderer.showFloatingItem(customItem);

                // Add custom particles
                for (int i = 0; i < 10; i++) {
                    livingEntity.world.addParticle(
                            ParticleTypes.END_ROD, // Replace with your custom particle if needed
                            livingEntity.getX() + (livingEntity.world.random.nextGaussian() * 0.5),
                            livingEntity.getY() + 1.0 + (livingEntity.world.random.nextGaussian() * 0.5),
                            livingEntity.getZ() + (livingEntity.world.random.nextGaussian() * 0.5),
                            0.0, 0.1, 0.0
                    );
                }

                livingEntity.world.playSound(
                        livingEntity.getX(),
                        livingEntity.getY(),
                        livingEntity.getZ(),
                        SoundEvents.ITEM_TOTEM_USE, // Replace with your sound
                        SoundCategory.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                );

                // Cancel the default behavior to prevent vanilla animation
                ci.cancel();
            }
    }
}
