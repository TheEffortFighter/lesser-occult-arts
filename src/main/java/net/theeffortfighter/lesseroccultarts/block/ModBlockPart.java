package net.theeffortfighter.lesseroccultarts.block;

import net.minecraft.util.StringIdentifiable;

public enum ModBlockPart implements StringIdentifiable {
    BASE("base"),
    MIDDLE("middle"),
    TOP("top");

    private final String name;

    ModBlockPart(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
