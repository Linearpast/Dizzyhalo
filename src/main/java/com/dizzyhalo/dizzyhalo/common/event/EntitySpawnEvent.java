package com.dizzyhalo.dizzyhalo.common.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySpawnEvent {

    public static float maxHealth = 800;

    @SubscribeEvent
    public static void onJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        Level level = event.getLevel();
        if(level.dimension() == Level.END) {
            if(entity instanceof EnderDragon dragon){
                if(dragon.getMaxHealth() != maxHealth){
                    Objects.requireNonNull(dragon.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(maxHealth);
                    dragon.setHealth(maxHealth);
                }
            }
        }
    }
}
