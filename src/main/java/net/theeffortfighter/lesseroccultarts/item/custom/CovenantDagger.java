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
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
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



    // Map to track players who used the dagger
    private final Map<UUID, Boolean> linkedToDagger = new HashMap<>();

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
                    UUID owner = covenantStoneBlockEntity.getOwner();

                    if (player.getUuid().equals(owner)) {
                        ownerEffect(player);
                    }
                    else if (!player.getUuid().equals(owner)) {
                        System.out.println("You are not the covenant owner: " + player.getUuid());
                        slowPlayer(player);
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
    private void slowPlayer(LivingEntity entity) {
        // Add slowness for 100 ticks (5 seconds) with an amplifier of 0
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 255));
    }

    private void ownerEffect(LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300, 0));
    }

    /**
     * Handles the generic use action when the dagger is right-clicked (not on a block).
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && hand == Hand.MAIN_HAND) {
            UUID uuid = user.getUuid();
            if (!CovenantPlayerRegistry.getInstance().isPlayerRegistered(uuid)) {
                CovenantPlayerRegistry.getInstance().addPlayer(uuid);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    /**
     * Checks if a player has used the dagger.
     *
     * @param player The player to check.
     * @return True if the player has used the dagger, otherwise false.
     */
    public void hasPlayerUsedDagger(PlayerEntity player) {
        UUID uuid = player.getUuid();
        if (CovenantPlayerRegistry.getInstance().isPlayerRegistered(uuid)); {

        }
    }
}
