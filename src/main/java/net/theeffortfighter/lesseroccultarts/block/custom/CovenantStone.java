package net.theeffortfighter.lesseroccultarts.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.theeffortfighter.lesseroccultarts.block.ModBlockPart;
import net.theeffortfighter.lesseroccultarts.entity.CovenantStoneBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CovenantStone extends Block implements BlockEntityProvider {

    public static final EnumProperty<ModBlockPart> PART = EnumProperty.of("part", ModBlockPart.class);
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active"); // New Property

    public CovenantStone(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, ModBlockPart.BASE).with(ACTIVE, false)); // Default to inactive);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CovenantStoneBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART, ACTIVE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        // Check if there's enough space for the tall block
        if (pos.getY() + 2 >= world.getHeight() || !world.getBlockState(pos.up()).isAir() || !world.getBlockState(pos.up(2)).isAir()) {
            return null;
        }

        return this.getDefaultState().with(ACTIVE, false); // Set inactive on placement
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (!world.isClient && placer instanceof PlayerEntity player) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CovenantStoneBlockEntity covenantStoneBlockEntity) {
                covenantStoneBlockEntity.setOwner(player.getUuid());
                System.out.println("Covenant Owner UUID: " + player.getUuid());
            }
        }

        // Place the middle and top parts
        world.setBlockState(pos.up(), this.getDefaultState().with(PART, ModBlockPart.MIDDLE).with(ACTIVE, false));
        world.setBlockState(pos.up(2), this.getDefaultState().with(PART, ModBlockPart.TOP).with(ACTIVE, false));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);


        BlockPos basePos = pos;
        ModBlockPart part = state.get(PART);

        // Determine the base position of the tall block
        if (part == ModBlockPart.MIDDLE) {
            basePos = pos.down();
        } else if (part == ModBlockPart.TOP) {
            basePos = pos.down(2);
        }

        // Break all parts
        world.breakBlock(basePos, false);
        world.breakBlock(basePos.up(), false);
        world.breakBlock(basePos.up(2), false);
    }

}
