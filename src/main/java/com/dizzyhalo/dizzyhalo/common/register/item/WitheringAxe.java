package com.dizzyhalo.dizzyhalo.common.register.item;

import com.dizzyhalo.dizzyhalo.common.network.Channel;
import com.dizzyhalo.dizzyhalo.common.network.toclient.ParticlesPacket;
import com.dizzyhalo.dizzyhalo.common.register.item.utils.WeaponUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WitheringAxe extends AxeItem {
    public WitheringAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(
            @NotNull ItemStack pStack,
            @NotNull LivingEntity pTarget,
            @NotNull LivingEntity pAttacker) {
        if(pAttacker instanceof Player player){
            if(!player.getCooldowns().isOnCooldown(this)){
                Explosion explosion = WeaponUtils.createExplosion(
                        pTarget.level(), pTarget,
                        pAttacker, pTarget.position(),
                        3.0f, false,
                        Explosion.BlockInteraction.DESTROY_WITH_DECAY
                );
                WeaponUtils.explosionApply(explosion, pTarget.level(), pTarget, ParticleTypes.EXPLOSION_EMITTER);
                WeaponUtils.actuallyHurt(pTarget, pAttacker, 4.0f);
                pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2));

                Vec3 vec3 = WeaponUtils.explosionKnockBackCal(3.0f, pTarget, pAttacker, explosion.getPosition());
                if (vec3 != null) {
                    player.addDeltaMovement(vec3);
                }
                player.getCooldowns().addCooldown(this, 240);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
