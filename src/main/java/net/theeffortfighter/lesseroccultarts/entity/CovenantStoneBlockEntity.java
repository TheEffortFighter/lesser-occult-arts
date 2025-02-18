package net.theeffortfighter.lesseroccultarts.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.theeffortfighter.lesseroccultarts.block.custom.CovenantStone;
import net.theeffortfighter.lesseroccultarts.item.custom.CovenantDagger;
import net.theeffortfighter.lesseroccultarts.registry.CovenantPlayerRegistry;

import java.util.Set;
import java.util.UUID;

public class CovenantStoneBlockEntity extends BlockEntity implements TickablePacketListener {
    private UUID owner = Util.NIL_UUID;
    private boolean active = false;

    public CovenantStoneBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COVENANT_STONE, pos, state);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        this.markDirty();
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean state) {
        this.active = state;
        this.markDirty(); // Ensure the block entity updates properly
    }

    public void toggleActive() {
        this.active = !this.active;
        markDirty();
        if (world instanceof ServerWorld serverWorld) {
            BlockState state = world.getBlockState(pos);
            world.setBlockState(pos, state.with(CovenantStone.ACTIVE, this.active));
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (owner != null) {
            nbt.putUuid("Owner", owner);
        }
        nbt.putBoolean("Active", active);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("Owner")) {
            owner = nbt.getUuid("Owner");
        }
        active = nbt.getBoolean("Active");
    }

    public static void tick(World world, BlockPos pos, BlockState state, CovenantStoneBlockEntity blockEntity) {
        if (!world.isClient && blockEntity.isActive()) {
            MinecraftServer server = world.getServer();
            if (server == null) return;

            Set<UUID> allPlayers = CovenantPlayerRegistry.getInstance().getCovenantPlayers();
            for (UUID uuid : allPlayers) {
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);
                if (player != null) { // Ensure player is online
                    CovenantDagger.daggerEffect(player); // Apply effect from the Abyssal Chains class
                }
            }
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void onDisconnected(Text reason) {

    }

    @Override
    public ClientConnection getConnection() {
        return null;
    }
}

