package com.dizzyhalo.dizzyhalo.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TotemRenderPacket {
    private ItemStack itemStack;
    public TotemRenderPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public TotemRenderPacket(FriendlyByteBuf buf) {
        itemStack = buf.readItem();
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(itemStack);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack);
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.playSound(SoundEvents.TOTEM_USE);
            }
            ctx.setPacketHandled(true);
        });
    }
}
