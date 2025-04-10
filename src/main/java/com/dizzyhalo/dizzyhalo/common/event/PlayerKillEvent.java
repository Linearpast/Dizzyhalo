package com.dizzyhalo.dizzyhalo.common.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerKillEvent {

    @SubscribeEvent
    public static void onPlayerKill(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer targetPlayer) {
            if(event.getSource().getEntity() != null && event.getSource().getEntity() instanceof ServerPlayer fromPlayer) {
                ItemStack mainHandItem = fromPlayer.getMainHandItem();
                if(mainHandItem.is(ModItems.BLOODLUST.get())){
                    int killCount = mainHandItem.getOrCreateTag().getInt("KillCount");
                    mainHandItem.getOrCreateTag().putInt("KillCount", ++killCount);
                    int sharpness = switch (killCount){
                        case 0 -> 1;
                        case 1 -> 2;
                        case 3 -> 3;
                        case 6 -> 4;
                        case 10 -> 5;
                        default -> -1;
                    };
                    if(sharpness > 0){
                        int originLevel = mainHandItem.getEnchantmentLevel(Enchantments.SHARPNESS);
                        if(originLevel < sharpness){
                            mainHandItem.getEnchantmentTags().removeIf(tag -> tag.getAsString().contains("minecraft:sharpness"));
                            mainHandItem.enchant(Enchantments.SHARPNESS, sharpness);
                        }
                    }
                }
                fromPlayer.getInventory().add(new ItemStack(Items.GOLD_NUGGET, 32));
                ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);
                playerHead.getOrCreateTag().putString("SkullOwner", targetPlayer.getUUID().toString());
                fromPlayer.getInventory().add(playerHead);
                fromPlayer.getInventory().setChanged();
            }
        }
    }
}
