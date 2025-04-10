package com.dizzyhalo.dizzyhalo.common.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityHurtEvent {
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity instanceof ServerPlayer serverPlayer) {
            for (ItemStack stack : serverPlayer.getArmorSlots()) {
                if (stack.is(ModItems.WARLOCK_PANTS.get())) {
                    float amount = event.getAmount();

                    float health = serverPlayer.getHealth();
                    float maxHealth = serverPlayer.getMaxHealth();
                    int percentage = (int) Math.floor((1.0f - health / maxHealth) * 5) * 5;
                    event.setAmount(amount * ((float) (100 - percentage) / 100));
                    return;
                }
            }
        }
    }
}
