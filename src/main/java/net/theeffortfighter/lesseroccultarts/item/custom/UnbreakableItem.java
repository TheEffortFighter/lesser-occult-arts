package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UnbreakableItem extends Item {
    public UnbreakableItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isDamageable() {
        return false; // Mark the item as non-damageable
    }

}

