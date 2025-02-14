package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.theeffortfighter.lesseroccultarts.registry.CovenantPlayerRegistry;

import java.util.EnumSet;
import java.util.UUID;

public class AbyssalChains extends Item {

    public AbyssalChains(Settings settings) {
        super(settings);
    }

    public static void lockCamera(ServerPlayerEntity player) {
        // Send packet to lock camera
        player.networkHandler.sendPacket(new PlayerPositionLookS2CPacket(player.getX(), player.getY(), player.getZ(), 180.0f, 0.0f, EnumSet.noneOf(PlayerPositionLookS2CPacket.Flag.class), 0, true));
    }
}
