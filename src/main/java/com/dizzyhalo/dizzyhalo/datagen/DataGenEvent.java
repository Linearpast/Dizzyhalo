package com.dizzyhalo.dizzyhalo.datagen;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.datagen.provider.ModItemModelGen;
import com.dizzyhalo.dizzyhalo.datagen.provider.ModItemTagGen;
import com.dizzyhalo.dizzyhalo.datagen.provider.ModLangGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider=event.getLookupProvider();
        ExistingFileHelper helper=event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModItemModelGen(packOutput, helper));
        generator.addProvider(event.includeClient(), new ModLangGen(packOutput, ModLangGen.Lang.EN_US));
        generator.addProvider(event.includeClient(), new ModLangGen(packOutput, ModLangGen.Lang.ZH_CN));
        generator.addProvider(event.includeServer(), new ModItemTagGen(packOutput, lookupProvider, helper));
    }
}
