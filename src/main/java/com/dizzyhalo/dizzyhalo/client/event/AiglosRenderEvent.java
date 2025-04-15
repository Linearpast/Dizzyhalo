package com.dizzyhalo.dizzyhalo.client.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.client.renderer.AiglosRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AiglosRenderEvent {
    public static final ResourceLocation aiglosModel = new ResourceLocation(Dizzyhalo.MODID, "item/aiglos/aiglos_projectile");

    @SubscribeEvent
    public static void tridentRenderRegister(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityType.TRIDENT, AiglosRenderer::new);
    }

    @SubscribeEvent
    public static void modelRegister(ModelEvent.RegisterAdditional event){
        event.register(aiglosModel);
    }
}
