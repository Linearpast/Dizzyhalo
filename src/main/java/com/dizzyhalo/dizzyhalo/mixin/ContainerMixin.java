package com.dizzyhalo.dizzyhalo.mixin;

import com.dizzyhalo.dizzyhalo.client.event.create.SlotClickEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class ContainerMixin {
    @Inject(
            method = "doClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onSlotClick(int pSlotId, int pButton, ClickType pClickType, Player pPlayer, CallbackInfo ci) {
        ItemStack heldItem = pPlayer.containerMenu.getCarried();
        SlotClickEvent event = new SlotClickEvent(pPlayer, pSlotId, heldItem, pClickType, pButton);
        boolean post = MinecraftForge.EVENT_BUS.post(event);
        if (post) ci.cancel();
    }
}