package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class AbbyssalEffigy extends Item {

    private final int repairInterval; // Repair interval in ticks (20 ticks = 1 second)
    private final int repairAmount;   // Amount of durability repaired per interval
    private int repairTick;           // Timer for repair logic

    public AbbyssalEffigy(Settings settings, int seconds, int repairAmount) {
        super(settings);
        this.repairInterval = seconds * 20;
        this.repairAmount = repairAmount;
        this.repairTick = 0;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    public void applyRepair(ItemStack stack) {
        if (stack.isDamaged()) {
            int currentDamage = stack.getDamage();
            int newDamage = Math.max(0, currentDamage - repairAmount);

            // Only update if there's an actual change to prevent constant updates
            if (newDamage != currentDamage) {
                stack.setDamage(newDamage);
            }
        }
    }

    public void onTick(PlayerEntity player, ItemStack stack) {
        // Increment the timer
        repairTick++;

        // Only repair when the interval is reached
        if (repairTick >= repairInterval) {
            applyRepair(stack);
            repairTick = 0; // Reset the timer
        }
    }
}
