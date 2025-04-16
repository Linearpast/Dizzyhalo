package com.dizzyhalo.dizzyhalo.client.event.create;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class SlotClickEvent extends Event {
    private final Player player;
    private final int slotId;
    private final ItemStack heldStack;
    private final ClickType clickType;
    private final int button;

    public SlotClickEvent(Player player, int slotId, ItemStack heldStack, ClickType clickType, int button) {
        this.player = player;
        this.slotId = slotId;
        this.heldStack = heldStack;
        this.clickType = clickType;
        this.button = button;
    }
    public Player getPlayer() {
        return player;
    }
    public int getSlotId() {
        return slotId;
    }
    public ItemStack getHeldStack() {
        return heldStack;
    }
    public ClickType getClickType() {
        return clickType;
    }
    public int getButton() {
        return button;
    }
}
