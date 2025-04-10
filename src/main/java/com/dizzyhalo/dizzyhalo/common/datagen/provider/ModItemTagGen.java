package com.dizzyhalo.dizzyhalo.common.datagen.provider;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGen extends ItemTagsProvider {
    public ModItemTagGen(
            PackOutput packOutput,
            CompletableFuture<HolderLookup.Provider> holderProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, holderProvider, CompletableFuture.completedFuture(TagLookup.empty()), Dizzyhalo.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModItemTags.DIAMOND_ARMOR)
                .add(Items.DIAMOND_HELMET)
                .add(Items.DIAMOND_CHESTPLATE)
                .add(Items.DIAMOND_LEGGINGS)
                .add(Items.DIAMOND_BOOTS);
    }
}
