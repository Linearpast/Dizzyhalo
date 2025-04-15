package com.dizzyhalo.dizzyhalo.common.register.item.utils;

import com.dizzyhalo.dizzyhalo.common.network.Channel;
import com.dizzyhalo.dizzyhalo.common.network.toclient.ParticlesPacket;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeaponUtils {
    public static Vec3 explosionKnockBackCal(float radius, LivingEntity pTarget, LivingEntity pAttacker, Vec3 pos){
        float f1 = radius * 2.0F;
        double d1 = Math.sqrt(pTarget.distanceToSqr(pos)) / (double)f1;
        double d2 = Explosion.getSeenPercent(pos, pTarget);
        double d3 = (1.0 - d1) * d2;

        double d4 = pTarget.getX() - pos.x + pAttacker.getLookAngle().x * 3;
        double d5 = pTarget.getEyeY() - pos.y + 0.5;
        double d6 = pTarget.getZ() - pos.z + pAttacker.getLookAngle().z * 3;
        double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        if(d7 != 0.0){
            d4 /= d7;
            d5 /= d7;
            d6 /= d7;

            d4 *= d3;
            d5 *= d3;
            d6 *= d3;
            return new Vec3(d4, d5, d6);
        }
        return null;
    }

    public static List<Entity> onShootArrowDetect(@NotNull Level pLevel, @NotNull Player pPlayer) {
        double radius = 0.5;
        AABB area = new AABB(
                pPlayer.getX() - radius, pPlayer.getEyeY() - radius, pPlayer.getZ() - radius,
                pPlayer.getX() + radius, pPlayer.getEyeY() + radius, pPlayer.getZ() + radius
        );
        return  ((pLevel.getEntitiesOfClass(Entity.class, area))
                .stream().filter(entity -> entity instanceof AbstractArrow).toList());
    }

    public static void actuallyHurt(LivingEntity pTarget, LivingEntity pAttacker, float damage){
        DamageSource damageSource = new DamageSource(pTarget.level().registryAccess()
                .lookupOrThrow(Registries.DAMAGE_TYPE)
                .getOrThrow(DamageTypes.MOB_ATTACK), pAttacker, pAttacker);
        ForgeHooks.onLivingAttack(pTarget, damageSource, damage);
        ForgeHooks.onLivingHurt(pTarget, damageSource, damage);
        ForgeHooks.onLivingDamage(pTarget, damageSource, damage);
        if (!pTarget.isDeadOrDying() && damage != 0.0F) {
            pTarget.getCombatTracker().recordDamage(damageSource, damage);
            pTarget.setHealth(pTarget.getHealth() - damage);
            pTarget.setAbsorptionAmount(pTarget.getAbsorptionAmount() - damage);
            pTarget.gameEvent(GameEvent.ENTITY_DAMAGE);
            if(pTarget.isDeadOrDying()){
                pTarget.die(damageSource);
            }
        }
    }

    public static Explosion createExplosion(
            Level pLevel,
            Entity directEntity,
            Entity causingEntity,
            Vec3 pos,
            float radius,
            boolean fire,
            Explosion.BlockInteraction interaction){
        DamageSource damageSource = new DamageSource(pLevel.registryAccess()
                .lookupOrThrow(Registries.DAMAGE_TYPE)
                .getOrThrow(DamageTypes.EXPLOSION), directEntity, causingEntity
        );
        return new Explosion(pLevel, directEntity, damageSource, null,
                pos.x, pos.y, pos.z,
                radius, fire, interaction
        );
    }

    public static void explosionApply(Explosion explosion, Level plevel, Entity directEntity, ParticleType<SimpleParticleType> particle){
        plevel.playSound(null, directEntity.getOnPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL);
        if(particle instanceof SimpleParticleType particleType && !plevel.isClientSide){
            Channel.sendAllPlayer(new ParticlesPacket(particleType.getType().writeToString(),
                    directEntity.getX(), directEntity.getY(), directEntity.getZ(),
                    1.0F, 0, 0));
        }
        explosion.explode();
        explosion.finalizeExplosion(true);
    }
}
