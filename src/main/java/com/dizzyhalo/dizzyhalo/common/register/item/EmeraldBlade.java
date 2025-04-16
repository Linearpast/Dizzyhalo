package com.dizzyhalo.dizzyhalo.common.register.item;

import com.dizzyhalo.dizzyhalo.common.register.item.utils.WeaponUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmeraldBlade extends SwordItem {
    private final String damageAdd = "damageAdd";
    private final int baseCount;
    public EmeraldBlade(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, int baseCount) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.baseCount = baseCount;
    }

    @Override
    public void inventoryTick(
            @NotNull ItemStack pStack,
            @NotNull Level pLevel,
            @NotNull Entity pEntity,
            int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if(pEntity instanceof ServerPlayer pPlayer && pPlayer.tickCount % 100 == 0) {
            PlayerEnderChestContainer enderChestInventory = pPlayer.getEnderChestInventory();
            int countItem = enderChestInventory.countItem(Items.EMERALD);
            float damage = Math.floorDiv(countItem, baseCount) * 0.5f;
            pStack.getOrCreateTag().putFloat(damageAdd, damage);
        }
    }

    @Override
    public boolean hurtEnemy(
            @NotNull ItemStack pStack,
            @NotNull LivingEntity pTarget,
            @NotNull LivingEntity pAttacker) {
        if(pAttacker instanceof ServerPlayer pPlayer && !pPlayer.getCooldowns().isOnCooldown(this)){
            float attackStrengthScale = pPlayer.getAttackStrengthScale(0.5f);
            if(attackStrengthScale == 1.0f){
                float damage = pStack.getOrCreateTag().getFloat(damageAdd);
                WeaponUtils.actuallyHurt(pTarget, pAttacker, damage);
                pPlayer.getCooldowns().addCooldown(this, 15);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack pStack,
            @Nullable Level pLevel,
            @NotNull List<Component> pTooltipComponents,
            @NotNull TooltipFlag pIsAdvanced) {
        float damage = pStack.getOrCreateTag().getFloat(damageAdd);
        Component component = Component.literal("晶化附伤：+" + damage).withStyle(ChatFormatting.GREEN);
        pTooltipComponents.add(component);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
