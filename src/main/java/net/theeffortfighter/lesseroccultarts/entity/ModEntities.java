package net.theeffortfighter.lesseroccultarts.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.theeffortfighter.lesseroccultarts.LesserOccultArts;

public class ModEntities {
    public static final EntityType<DemonEntity> DEMON = Registry.register(Registry.ENTITY_TYPE, new Identifier(LesserOccultArts.MOD_ID, "demon"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DemonEntity::new).dimensions(EntityDimensions.fixed(0.4f,0.4f)).build());
}
