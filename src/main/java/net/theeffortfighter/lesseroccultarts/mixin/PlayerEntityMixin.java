package net.theeffortfighter.lesseroccultarts.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.theeffortfighter.lesseroccultarts.item.custom.AbbyssalEffigy;
import net.theeffortfighter.lesseroccultarts.item.custom.MonarchSeal;
import net.theeffortfighter.lesseroccultarts.item.custom.UncursingAmulet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract Iterable<ItemStack> getHandItems();

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
