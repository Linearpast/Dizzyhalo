package com.dizzyhalo.dizzyhalo.common.register.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DaredevilItem extends Item {

    public DaredevilItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level pLevel,
            @NotNull Player pPlayer,
            @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            itemstack.shrink(1);
            SkeletonHorse skeletonHorse = new SkeletonHorse(EntityType.SKELETON_HORSE, pLevel);
            skeletonHorse.setPos(
                    pPlayer.getX() + pPlayer.getLookAngle().x * 2,
                    pPlayer.getY() + 1,
                    pPlayer.getZ() + pPlayer.getLookAngle().z * 2
            );
            Optional.ofNullable(skeletonHorse.getAttribute(Attributes.MAX_HEALTH)).ifPresent(
                    attributeInstance -> attributeInstance.setBaseValue(50.0)
            );
            skeletonHorse.setHealth(skeletonHorse.getMaxHealth());
            skeletonHorse.setTamed(true);
            skeletonHorse.setOwnerUUID(pPlayer.getUUID());
            skeletonHorse.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.SADDLE, 1));
            skeletonHorse.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 13, false,false));
            pLevel.addFreshEntity(skeletonHorse);
            ((Saddleable)skeletonHorse).equipSaddle(SoundSource.NEUTRAL);
            skeletonHorse.level().gameEvent(skeletonHorse, GameEvent.EQUIP, skeletonHorse.position());
        }
        pPlayer.playSound(SoundEvents.SKELETON_HORSE_AMBIENT);
        return super.use(pLevel, pPlayer, pUsedHand);

    }
}
