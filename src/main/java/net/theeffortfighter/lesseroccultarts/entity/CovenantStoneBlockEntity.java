package net.theeffortfighter.lesseroccultarts.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.theeffortfighter.lesseroccultarts.block.custom.CovenantStone;

import java.util.UUID;

public class CovenantStoneBlockEntity extends BlockEntity {
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
}

