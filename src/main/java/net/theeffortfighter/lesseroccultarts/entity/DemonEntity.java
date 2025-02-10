package net.theeffortfighter.lesseroccultarts.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DemonEntity extends VexEntity implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private boolean isAttacking = false; // Track attack animation state

    public DemonEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    private <E extends IAnimatable> PlayState movementPredicate(AnimationEvent<E> event) {
        if (isAttacking) {
            return PlayState.STOP; // Stop movement animations when attacking
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Move", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("Idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (isAttacking && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Claws", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            isAttacking = false; // Reset after playing
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::movementPredicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof PlayerEntity || target instanceof LivingEntity) {
                isAttacking = true; // Set attack state when hitting a player
            }
            return true;
        }
        return false;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
