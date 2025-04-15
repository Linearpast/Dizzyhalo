package com.dizzyhalo.dizzyhalo.common.register.item;

import com.dizzyhalo.dizzyhalo.common.register.item.utils.WeaponUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AiglosTrident extends TridentItem implements Vanishable {
    public static final String withExplosion = "withExplosion";
    public AiglosTrident(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hurtEnemy(
            @NotNull ItemStack pStack,
            @NotNull LivingEntity pTarget,
            @NotNull LivingEntity pAttacker) {
        if(pAttacker instanceof Player player && !player.getCooldowns().isOnCooldown(this)) {
            Vec3 vec3 = pTarget.position();
            vec3.add(pTarget.getLookAngle().x * 4, 0, pTarget.getLookAngle().z * 4);
            Explosion explosion = WeaponUtils.createExplosion(
                    pTarget.level(), pAttacker,
                    pAttacker, vec3,
                    3.0f, false,
                    Explosion.BlockInteraction.KEEP
            );
            WeaponUtils.explosionApply(explosion, pTarget.level(), pAttacker, ParticleTypes.EXPLOSION);
            WeaponUtils.actuallyHurt(pTarget, pAttacker, 1.0f);
            player.getCooldowns().addCooldown(this, 120);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int releaseTime = this.getUseDuration(pStack) - pTimeLeft;
            if (releaseTime >= 10) {
                int level = EnchantmentHelper.getRiptide(pStack);
                if (level <= 0 || player.isInWaterOrRain()) {
                    if (!pLevel.isClientSide) {
                        pStack.hurtAndBreak(1, player, (player1) -> {
                            player1.broadcastBreakEvent(pEntityLiving.getUsedItemHand());
                        });
                        if (level == 0) {
                            ThrownTrident trident = new ThrownTrident(pLevel, player, pStack);
                            trident.addTag(withExplosion);
                            trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float) level * 0.5F, 1.0F);
                            if (player.getAbilities().instabuild) {
                                trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            pLevel.addFreshEntity(trident);
                            pLevel.playSound(null, trident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (!player.getAbilities().instabuild) {
                                player.getInventory().removeItem(pStack);
                            }
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (level > 0) {
                        float yRot = player.getYRot();
                        float xRot = player.getXRot();
                        float f = -Mth.sin(yRot * 0.017453292F) * Mth.cos(xRot * 0.017453292F);
                        float f1 = -Mth.sin(xRot * 0.017453292F);
                        float f2 = Mth.cos(yRot * 0.017453292F) * Mth.cos(xRot * 0.017453292F);
                        float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
                        float f4 = 3.0F * ((1.0F + (float) level) / 4.0F);
                        f *= f4 / f3;
                        f1 *= f4 / f3;
                        f2 *= f4 / f3;
                        player.push(f, f1, f2);
                        player.startAutoSpinAttack(20);
                        if (player.onGround()) {
                            float y = 1.2f;
                            player.move(MoverType.SELF, new Vec3(0.0, y, 0.0));
                        }

                        SoundEvent soundEvent;
                        if (level >= 3) {
                            soundEvent = SoundEvents.TRIDENT_RIPTIDE_3;
                        } else if (level == 2) {
                            soundEvent = SoundEvents.TRIDENT_RIPTIDE_2;
                        } else {
                            soundEvent = SoundEvents.TRIDENT_RIPTIDE_1;
                        }

                        pLevel.playSound(null, player, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }
}
