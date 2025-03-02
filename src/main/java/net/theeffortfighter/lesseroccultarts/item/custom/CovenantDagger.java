package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.theeffortfighter.lesseroccultarts.block.custom.CovenantStone;
import net.theeffortfighter.lesseroccultarts.entity.CovenantStoneBlockEntity;
import net.theeffortfighter.lesseroccultarts.registry.CovenantPlayerRegistry;

import java.util.*;

public class CovenantDagger extends Item {
    private boolean isActive = false;
    private UUID owner;

    public CovenantDagger(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (!world.isClient && blockState.getBlock() instanceof CovenantStone) {
            PlayerEntity player = context.getPlayer();

            if (player != null) {
                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                if (blockEntity instanceof CovenantStoneBlockEntity covenantStoneBlockEntity) {
                    UUID dagOwner = covenantStoneBlockEntity.getOwner();
                    setOwner(dagOwner); // Sets owner for dagger

                    if (player.getUuid().equals(dagOwner) && isActive) { // ✅ Check if the player is the owner
                        boolean currentState = covenantStoneBlockEntity.isActive();
                        covenantStoneBlockEntity.setActive(!currentState); // ✅ Toggle state
                        ownerEffect(player);

                        player.sendMessage(Text.of("Covenant Stone is now " + (currentState ? "inactive" : "active")), true);
                    } else if (player.getUuid().equals(dagOwner) && !isActive){
                        boolean currentDagState = isDagActive();
                        activeState(!currentDagState);
                        System.out.println("Dagger Activated: " + isActive);
                    } else {
                        slowPlayer(player); // ❌ Not the owner? Apply slowness
                    }
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    /**
     * Applies a slowness effect to the player who used the dagger.
     *
     * @param entity The entity to apply the effect to.
     */
    private static void slowPlayer(LivingEntity entity) {
        // Add slowness for 100 ticks (5 seconds) with an amplifier of 0
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 255));
    }

    private static void chainedPlayer(LivingEntity entity) {
        // Add slowness for 100 ticks (5 seconds) with an amplifier of 0
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, 255));
    }

    private void ownerEffect(LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300, 0));
    }

    /**
     * Handles the generic use action when the dagger is right-clicked (not on a block).
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && hand == Hand.MAIN_HAND && isActive) {
            UUID uuid = user.getUuid();
            if (!CovenantPlayerRegistry.getInstance().isPlayerRegistered(uuid) && !user.getUuid().equals(owner)) {
                CovenantPlayerRegistry.getInstance().addPlayer(uuid);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    public void setOwner (UUID uuid) {
        this.owner = uuid;
    }

    public void activeState (boolean state) {
        this.isActive = state;
    }

    public boolean isDagActive () {
        return this.isActive;
    }

    public static void daggerEffect(ServerPlayerEntity player) {
        AbyssalChains.lockCamera(player);
        slowPlayer(player);
    }
}
