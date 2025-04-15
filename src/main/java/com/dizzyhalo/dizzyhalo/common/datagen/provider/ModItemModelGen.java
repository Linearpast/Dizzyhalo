package com.dizzyhalo.dizzyhalo.common.datagen.provider;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ModItemModelGen extends ItemModelProvider {

    public static final String GENERATED="item/generated";
    public static final String HANDHELD="item/handheld";

    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Dizzyhalo.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemGenerateModel(ModItems.WARLOCK_PANTS.get(), resourceItem(itemName(ModItems.WARLOCK_PANTS.get())));
        itemHandHeldGenerateModel(ModItems.BLOODLUST.get(), resourceItem(itemName(ModItems.BLOODLUST.get())));
        itemGenerateModel(ModItems.GOLDEN_HEAD.get(), resourceItem(itemName(ModItems.GOLDEN_HEAD.get())));
        itemGenerateModel(ModItems.REVIVAL_STAR.get(), resourceItem(itemName(ModItems.REVIVAL_STAR.get())));
        itemGenerateModel(ModItems.DAREDEVIL.get(), resourceItem(itemName(ModItems.DAREDEVIL.get())));
        itemGenerateModel(ModItems.FUSION_HELMET.get(), resourceItem(itemName(ModItems.FUSION_HELMET.get())));
        itemGenerateModel(ModItems.FUSION_CHESTPLATE.get(), resourceItem(itemName(ModItems.FUSION_CHESTPLATE.get())));
        itemGenerateModel(ModItems.FUSION_LEGGINGS.get(), resourceItem(itemName(ModItems.FUSION_LEGGINGS.get())));
        itemGenerateModel(ModItems.FUSION_BOOTS.get(), resourceItem(itemName(ModItems.FUSION_BOOTS.get())));
        itemGenerateModel(ModItems.FUSION_ARMORS.get(), resourceItem(itemName(ModItems.FUSION_ARMORS.get())));
        itemHandHeldGenerateModel(ModItems.WITHERING_AXE.get(), resourceItem(itemName(ModItems.WITHERING_AXE.get())));
    }

    public void itemGenerateModel(Item item, ResourceLocation location){
        withExistingParent(itemName(item), GENERATED).texture("layer0", location);
    }
    public void itemHandHeldGenerateModel(Item item, ResourceLocation location){
        withExistingParent(itemName(item), HANDHELD).texture("layer0", location);
    }

    public String itemName(Item item){
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    public ResourceLocation resourceItem(String path){
        return new ResourceLocation(Dizzyhalo.MODID, "item/" + path);
    }
}
