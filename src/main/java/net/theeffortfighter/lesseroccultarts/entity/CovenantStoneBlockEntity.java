package net.theeffortfighter.lesseroccultarts.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class CovenantStoneBlockEntity extends BlockEntity {
    private UUID owner;

    public CovenantStoneBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COVENANT_STONE, pos, state);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getOwner() {
        return owner;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        if (owner != null) {
            tag.putUuid("Owner", owner);
        }
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("Owner")) {
            owner = tag.getUuid("Owner");
        }
    }
}

