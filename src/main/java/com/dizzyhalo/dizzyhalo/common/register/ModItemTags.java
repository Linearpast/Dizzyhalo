package com.dizzyhalo.dizzyhalo.common.register;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    private ModItemTags() {}

    public static final TagKey<Item> DIAMOND_ARMOR = create(new ResourceLocation(Dizzyhalo.MODID, "diamond_armor"));

    public static TagKey<Item> create(final ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }
}
