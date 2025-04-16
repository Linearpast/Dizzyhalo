package com.dizzyhalo.dizzyhalo.common.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import com.dizzyhalo.dizzyhalo.network.Channel;
import com.dizzyhalo.dizzyhalo.network.toclient.TotemRenderPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerDeathEvent {
    @SubscribeEvent
    public static void revivalStarTrigger(LivingHurtEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.getHealth() - event.getAmount() > 0) return;
            List<ItemStack> itemStacks = serverPlayer.getInventory().items.stream().filter(itemStack -> itemStack.is(ModItems.REVIVAL_STAR.get())).toList();
            if(!itemStacks.isEmpty()){
                Channel.sendToPlayer(new TotemRenderPacket(itemStacks.get(0).copy()), serverPlayer);
                itemStacks.get(0).shrink(1);
                serverPlayer.setHealth(0.5f);
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 2));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 4));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                event.setCanceled(true);
            }
        }
    }
}
