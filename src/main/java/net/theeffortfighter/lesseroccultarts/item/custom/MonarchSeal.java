package net.theeffortfighter.lesseroccultarts.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.theeffortfighter.lesseroccultarts.entity.DemonEntity;
import net.theeffortfighter.lesseroccultarts.entity.ModEntities;

public class MonarchSeal extends Item {

    public MonarchSeal(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            for (int i = 0; i < 3; i++) {
                summonDemon(world, player);
            }
            // Play summoning sound
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    private void summonDemon(World world, PlayerEntity player) {
        DemonEntity demon = new DemonEntity(ModEntities.DEMON, world);
        Vec3d pos = player.getPos().add((world.random.nextDouble() - 0.5) * 2, 1, (world.random.nextDouble() - 0.5) * 2);

        demon.refreshPositionAndAngles(pos.x, pos.y, pos.z, world.random.nextFloat() * 360F, 0F);

        world.spawnEntity(demon);
    }
}
