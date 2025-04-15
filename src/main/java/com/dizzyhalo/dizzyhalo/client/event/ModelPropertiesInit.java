package com.dizzyhalo.dizzyhalo.client.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import com.dizzyhalo.dizzyhalo.common.register.item.FireCrossBow;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelPropertiesInit {
    @SubscribeEvent
    public static void clientInit(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (Item crossbowItem : getCrossbowItems()) {
                pullRegistry(crossbowItem);
                ItemProperties.register(
                        crossbowItem,
                        new ResourceLocation( "charged"),
                        ((itemStack, clientLevel, livingEntity, i) -> FireCrossBow.isCharged(itemStack) ? 1 : 0)
                );
            }

            pullRegistry(ModItems.AIGLOS.get());
        });
    }

    private static void pullRegistry(Item pullItem) {
        ItemProperties.register(
                pullItem,
                new ResourceLocation( "pull"),
                ((itemStack, clientLevel, livingEntity, i) -> (float) Optional.ofNullable(livingEntity)
                        .map(LivingEntity::getTicksUsingItem).orElse(0) / itemStack.getUseDuration())
        );
        ItemProperties.register(
                pullItem,
                new ResourceLocation( "pulling"),
                ((itemStack, clientLevel, livingEntity, i) -> {
                    if (livingEntity != null) {
                        return livingEntity.getUseItemRemainingTicks() > 0 ? 1 : 0;
                    }
                    return 0;
                })
        );
    }

    private static List<Item> getCrossbowItems(){
        List<Item> crossbowItems = new ArrayList<>();
        crossbowItems.add(ModItems.BLAZING_CROSSBOW.get());
        crossbowItems.add(ModItems.CORRUPTED_CROSSBOW.get());
        return crossbowItems;
    }
}
