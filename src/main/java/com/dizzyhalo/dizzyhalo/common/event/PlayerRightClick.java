package com.dizzyhalo.dizzyhalo.common.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerRightClick {
    @SubscribeEvent
    public static void itemUse(PlayerInteractEvent.RightClickItem event){
        if(event.getEntity() instanceof ServerPlayer serverPlayer){
            ItemStack itemInHand = serverPlayer.getItemInHand(event.getHand());
            if(itemInHand.getItem() == Items.PLAYER_HEAD){
                CompoundTag hasSkullOwner = Optional.ofNullable(itemInHand.getTag()).filter(tag -> tag.contains("SkullOwner")).orElse(null);
                if (hasSkullOwner != null){
                    itemInHand.shrink(1);
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 1));
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 1));
                    event.setCanceled(true);
                }
            }
            if(itemInHand.getItem() == ModItems.GOLDEN_HEAD.get()){
                itemInHand.shrink(1);
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 2));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 1));
                event.setCanceled(true);
            }
        }
    }
}
