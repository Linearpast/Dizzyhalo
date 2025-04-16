package com.dizzyhalo.dizzyhalo.common.register.item;

import com.dizzyhalo.dizzyhalo.common.register.item.utils.WeaponUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class PoisonCrossbow extends CrossbowItem implements Vanishable {
    public static final String poisonArrow = "poisonArrow";
    public static final MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.POISON, 60, 1);

    public PoisonCrossbow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if(isCharged(itemstack) && !pPlayer.getCooldowns().isOnCooldown(this)){
            InteractionResultHolder<ItemStack> use = super.use(pLevel, pPlayer, pHand);
            WeaponUtils.onShootArrowDetect(pLevel, pPlayer);
            List<Entity> entities = WeaponUtils.onShootArrowDetect(pLevel, pPlayer);
            entities.forEach(entity -> {
                AbstractArrow arrow = (AbstractArrow) entity;
                Entity owner = arrow.getOwner();
                if(Optional.ofNullable(owner).map(Entity::getUUID).filter(uuid -> uuid.equals(pPlayer.getUUID())).isPresent()){
                    arrow.addTag(poisonArrow);
                    pPlayer.getCooldowns().addCooldown(this, 300);
                }
            });
            return use;
        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
