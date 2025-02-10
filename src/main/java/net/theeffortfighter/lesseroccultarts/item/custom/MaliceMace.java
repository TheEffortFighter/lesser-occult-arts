package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Set;

public class MaliceMace extends Item {

    public MaliceMace(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && hand == Hand.MAIN_HAND && user instanceof ServerPlayerEntity serverPlayer) {
            lockCamera(serverPlayer); // Track the player
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    private void lockCamera(ServerPlayerEntity player) {
        // Send packet to lock camera
        player.networkHandler.sendPacket(new PlayerPositionLookS2CPacket(player.getX(), player.getY(), player.getZ(), 0.0f, 0.0f, Set.of(PlayerPositionLookS2CPacket.Flag.X, PlayerPositionLookS2CPacket.Flag.Y), 0, false));
    }
}
