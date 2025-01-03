package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class AbbyssalEffigy extends Item {
    public AbbyssalEffigy(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        // Adds the enchantment glint to the item
        return true;
    }

    @Override
    public boolean isDamageable() {
        return false; // Prevent the item from being damaged
    }

}
