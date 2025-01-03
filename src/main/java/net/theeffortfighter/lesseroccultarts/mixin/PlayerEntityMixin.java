package net.theeffortfighter.lesseroccultarts.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.theeffortfighter.lesseroccultarts.item.custom.AbbyssalEffigy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Loop through the player's inventory to find the repairing item
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() instanceof AbbyssalEffigy) {
                AbbyssalEffigy item = (AbbyssalEffigy) stack.getItem();
                item.onTick(player, stack); // Call the repair method
            }
        }

        // Check the offhand slot
        ItemStack offhandStack = player.getOffHandStack();
        if (offhandStack.getItem() instanceof AbbyssalEffigy) {
            AbbyssalEffigy item = (AbbyssalEffigy) offhandStack.getItem();
            item.onTick(player, offhandStack);  // Call the repair method
        }

    }
}
