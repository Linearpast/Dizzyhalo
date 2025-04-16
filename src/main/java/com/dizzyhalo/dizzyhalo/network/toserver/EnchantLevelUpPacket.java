package com.dizzyhalo.dizzyhalo.network.toserver;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class EnchantLevelUpPacket {
    private UUID uuid;
    private int slotId;
    public EnchantLevelUpPacket(UUID uuid, int slotId) {
        this.uuid = uuid;
        this.slotId = slotId;
    }
    public EnchantLevelUpPacket(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        this.slotId = buf.readInt();
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeInt(slotId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(uuid);
            if (player == null) return;
            ItemStack heldItem = player.containerMenu.getCarried();
            ItemStack levelUpStack = player.containerMenu.getSlot(slotId).getItem();
            if (levelUpStack.isEnchanted()) {
                heldItem.shrink(1);
                Map<Enchantment, Integer> allEnchantments = Map.copyOf(levelUpStack.getAllEnchantments());
                allEnchantments.forEach((enchantment, integer) -> {
                    levelUpStack.getAllEnchantments().remove(enchantment);
                    levelUpStack.enchant(enchantment, integer + 1);
                });
                player.containerMenu.sendAllDataToRemote();
                player.getInventory().setChanged();
            }
            ctx.setPacketHandled(true);
        });
    }
}
