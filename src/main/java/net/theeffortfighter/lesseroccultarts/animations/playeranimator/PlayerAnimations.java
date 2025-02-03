package net.theeffortfighter.lesseroccultarts.animations.playeranimator;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.theeffortfighter.lesseroccultarts.animations.PlayerAnimation;

public class PlayerAnimations {

    public void chainedAnimation(World world, PlayerEntity user) {
        if (world.isClient) {
            //If we want to play the animation, get the animation container
            var animationContainer = ((PlayerAnimation)user).LOA_getModAnimation();

            //Use setAnimation to set the current animation. It will be played automatically.
            KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier("loa", "chain"));

            assert anim != null;

            animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
        }
    }
}
