package net.theeffortfighter.lesseroccultarts.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
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

import java.util.EnumSet;

public class DemonEntity extends VexEntity implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private boolean isAttacking = false; // Track attack animation state
    @Nullable
    MobEntity owner;

    public DemonEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new CustomChargeGoal());
        this.goalSelector.add(8, new CustomLookGoal());
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new CustomTrackOwnerGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public void AttackState(boolean attacking) {
        this.isAttacking = attacking;
    }

    class CustomChargeGoal
            extends Goal {
        public CustomChargeGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = DemonEntity.this.getTarget();
            if (livingEntity != null && livingEntity.isAlive() && !DemonEntity.this.getMoveControl().isMoving() && DemonEntity.this.random.nextInt(DemonEntity.CustomChargeGoal.toGoalTicks(7)) == 0) {
                return DemonEntity.this.squaredDistanceTo(livingEntity) > 4.0;
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return DemonEntity.this.getMoveControl().isMoving() && DemonEntity.this.isCharging() && DemonEntity.this.getTarget() != null && DemonEntity.this.getTarget().isAlive();
        }

        @Override
        public void start() {
            LivingEntity livingEntity = DemonEntity.this.getTarget();
            if (livingEntity != null) {
                Vec3d vec3d = livingEntity.getEyePos();
                DemonEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0);
            }
            DemonEntity.this.setCharging(true);
            DemonEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0f, 1.0f);
        }

        @Override
        public void stop() {
            DemonEntity.this.setCharging(false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = DemonEntity.this.getTarget();
            if (livingEntity == null) {
                return;
            }
            if (DemonEntity.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
                AttackState(true);
                System.out.println("Setting isAttacking to true");
                DemonEntity.this.tryAttack(livingEntity);
                DemonEntity.this.setCharging(false);
            } else {
                double d = DemonEntity.this.squaredDistanceTo(livingEntity);
                if (d < 9.0) {
                    Vec3d vec3d = livingEntity.getEyePos();
                    DemonEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0);
                }
            }
        }
    }

    class CustomLookGoal
            extends Goal {
        public CustomLookGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return !DemonEntity.this.getMoveControl().isMoving() && DemonEntity.this.random.nextInt(DemonEntity.CustomLookGoal.toGoalTicks(7)) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos blockPos = DemonEntity.this.getBounds();
            if (blockPos == null) {
                blockPos = DemonEntity.this.getBlockPos();
            }
            for (int i = 0; i < 3; ++i) {
                BlockPos blockPos2 = blockPos.add(DemonEntity.this.random.nextInt(15) - 7, DemonEntity.this.random.nextInt(11) - 5, DemonEntity.this.random.nextInt(15) - 7);
                if (!DemonEntity.this.world.isAir(blockPos2)) continue;
                DemonEntity.this.moveControl.moveTo((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 0.25);
                if (DemonEntity.this.getTarget() != null) break;
                DemonEntity.this.getLookControl().lookAt((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 180.0f, 20.0f);
                break;
            }
        }
    }

    class CustomTrackOwnerGoal
            extends TrackTargetGoal {
        private final TargetPredicate targetPredicate;

        public CustomTrackOwnerGoal(PathAwareEntity mob) {
            super(mob, false);
            this.targetPredicate = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();
        }

        @Override
        public boolean canStart() {
            return DemonEntity.this.owner != null && DemonEntity.this.owner.getTarget() != null && this.canTrack(DemonEntity.this.owner.getTarget(), this.targetPredicate);
        }

        @Override
        public void start() {
            DemonEntity.this.setTarget(DemonEntity.this.owner.getTarget());
            super.start();
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::movementPredicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    private <E extends IAnimatable> PlayState movementPredicate(AnimationEvent<E> event) {
        if (this.isAttacking) {
            System.out.println("Demon is attacking, stopping movement animation.");
            return PlayState.STOP;
        } else if (event.isMoving() && !this.isAttacking) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Move", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
    }


    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.isAttacking) {
            System.out.println("Playing Attack Animation");

            // Force update
            event.getController().markNeedsReload();

            // Set attack animation
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Claws", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            System.out.println("Attempting to play animation: Claws");

            AttackState(false);
            System.out.println("Attack animation finished, resetting isAttacking");
        }
        return PlayState.CONTINUE;
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
