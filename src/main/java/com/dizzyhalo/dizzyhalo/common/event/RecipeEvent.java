package com.dizzyhalo.dizzyhalo.common.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RecipeEvent {



    @SubscribeEvent
    public static void recipe(GrindstoneEvent event){
        ItemStack bottom = event.getBottomItem();
        ItemStack top = event.getTopItem();
        ModItems.REGISTER.getEntries().forEach(entry -> {
            if(entry.filter(item -> item == top.getItem()).isPresent()
                    || entry.filter(item -> item == bottom.getItem()).isPresent()){
                if(!getCanEnchant().contains(bottom.getItem())
                        && !getCanEnchant().contains(top.getItem())){
                    event.setCanceled(true);
                }
            }
        });
    }

    @SubscribeEvent
    public static void recipe(AnvilUpdateEvent event){
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        ModItems.REGISTER.getEntries().forEach(entry -> {
            if(entry.filter(item -> item == left.getItem()).isPresent()
                    || entry.filter(item -> item == right.getItem()).isPresent()){
                if(!getCanEnchant().contains(left.getItem())
                        && !getCanEnchant().contains(right.getItem())){
                    event.setCanceled(true);
                }
            }
        });
    }

    public static List<Item> getCanEnchant(){
        List<Item> canEnchant = new ArrayList<>();
        canEnchant.add(ModItems.CORRUPTED_CROSSBOW.get());
        return canEnchant;
    }
}
