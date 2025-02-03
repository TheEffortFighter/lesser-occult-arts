package net.theeffortfighter.lesseroccultarts;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.theeffortfighter.lesseroccultarts.entity.ModEntities;
import net.theeffortfighter.lesseroccultarts.entity.renderer.DemonRenderer;

public class LOAClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.DEMON, DemonRenderer::new);

    }
}
