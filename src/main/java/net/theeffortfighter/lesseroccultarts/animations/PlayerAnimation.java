package net.theeffortfighter.lesseroccultarts.animations;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;

public interface PlayerAnimation {
    /**
     * Use your mod ID in the method name to avoid collisions with other mods
     *
     * @return Mod animation container
     */
    ModifierLayer<IAnimation> LOA_getModAnimation();
}
