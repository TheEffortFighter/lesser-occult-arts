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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
            lockCamera(serverPlayer);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    private void lockCamera(ServerPlayerEntity player) {
        float yaw = player.getYaw();
        float pitch = player.getPitch();

        // Send packet to lock camera
        player.networkHandler.sendPacket(new PlayerPositionLookS2CPacket(player.getX(), player.getY(), player.getZ(), yaw, pitch, Set.of(PlayerPositionLookS2CPacket.Flag.X_ROT, PlayerPositionLookS2CPacket.Flag.Y_ROT), 0, true));
    }
}
