package net.theeffortfighter.lesseroccultarts.entity.model;

import net.minecraft.util.Identifier;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;
import net.theeffortfighter.lesseroccultarts.entity.DemonEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DemonModel extends AnimatedGeoModel<DemonEntity> {
    @Override
    public Identifier getModelResource(DemonEntity object) {
        return new Identifier(LesserOccultArts.MOD_ID, "geo/demon.geo.json");
    }

    @Override
    public Identifier getTextureResource(DemonEntity object) {
        return new Identifier(LesserOccultArts.MOD_ID, "textures/entity/demon.png");
    }

    @Override
    public Identifier getAnimationResource(DemonEntity animatable) {
        return new Identifier(LesserOccultArts.MOD_ID, "animations/demon.animation.json");
    }
}
