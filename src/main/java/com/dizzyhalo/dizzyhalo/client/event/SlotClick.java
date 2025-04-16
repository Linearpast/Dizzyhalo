package com.dizzyhalo.dizzyhalo.client.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.client.event.create.SlotClickEvent;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SlotClick {
    @SubscribeEvent
    public static void slotClick(SlotClickEvent event) {
        ItemStack heldStack = event.getHeldStack();
        int button = event.getButton();
        if(heldStack.is(ModItems.EXPERT_SEAL.get()) && button == 1){
            int slotId = event.getSlotId();
            Player player = event.getPlayer();
            ItemStack levelUpStack = player.containerMenu.getSlot(slotId).getItem();
            if(!levelUpStack.isEnchanted()) return;
            heldStack.shrink(1);
//            Channel.sendServer(new EnchantLevelUpPacket(player.getUUID(), slotId));
            Map<Enchantment, Integer> allEnchantments = Map.copyOf(levelUpStack.getAllEnchantments());
            allEnchantments.forEach((enchantment, integer) -> {
                levelUpStack.getEnchantmentTags().removeIf(enchant ->
                        enchant.getAsString().contains(Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS
                                .getKey(enchantment)).toString())
                );
                levelUpStack.enchant(enchantment, integer + 1);
            });
            event.setCanceled(true);
        }
    }
}
