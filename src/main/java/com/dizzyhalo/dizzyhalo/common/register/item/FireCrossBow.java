package com.dizzyhalo.dizzyhalo.common.register.item;

import com.dizzyhalo.dizzyhalo.common.register.item.utils.WeaponUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class FireCrossBow extends CrossbowItem implements Vanishable {
    public FireCrossBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if(isCharged(itemstack)){
            InteractionResultHolder<ItemStack> use = super.use(pLevel, pPlayer, pHand);
            WeaponUtils.onShootArrowDetect(pLevel, pPlayer);
            List<Entity> entities = WeaponUtils.onShootArrowDetect(pLevel, pPlayer);
            entities.forEach(entity -> {
                AbstractArrow arrow = (AbstractArrow) entity;
                Entity owner = arrow.getOwner();
                if(Optional.ofNullable(owner).map(Entity::getUUID).filter(uuid -> uuid.equals(pPlayer.getUUID())).isPresent()){
                    arrow.setSecondsOnFire(100);
                }
            });
            return use;
        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
