package com.dizzyhalo.dizzyhalo.common.event;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.item.AiglosTrident;
import com.dizzyhalo.dizzyhalo.common.register.item.PoisonCrossbow;
import com.dizzyhalo.dizzyhalo.common.register.item.utils.WeaponUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Dizzyhalo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpecialArrowEvent {
    private static final float poisonAreaRadius = 4.0f;
    private static final int poisonDuration = 15 * 20;

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent event) {
        if(event.getProjectile() instanceof AbstractArrow abstractArrow) {
            if (abstractArrow.getOwner() instanceof ServerPlayer serverPlayer) {
                Entity entity = event.getEntity();
                if (abstractArrow.getTags().stream().anyMatch(string -> string.equals(PoisonCrossbow.poisonArrow))) {
                    Potion potion = new Potion(PoisonCrossbow.poisonEffect);
                    makeEffectCloudPoisonCrossbow(entity.level(), potion, abstractArrow.position(), serverPlayer);
                }
            }
        }
        if(event.getProjectile() instanceof ThrownTrident thrownTrident){
            if(thrownTrident.getTags().contains(AiglosTrident.withExplosion) &&
                    thrownTrident.getOwner() instanceof ServerPlayer serverPlayer){
                Explosion explosion = WeaponUtils.createExplosion(
                        thrownTrident.level(), serverPlayer,
                        serverPlayer, thrownTrident.position(),
                        3.0f, false,
                        Explosion.BlockInteraction.KEEP
                );
                WeaponUtils.explosionApply(explosion, thrownTrident.level(), thrownTrident, ParticleTypes.EXPLOSION);
                explosion.getHitPlayers().forEach(((player, vec3) -> {
                    WeaponUtils.actuallyHurt(player, serverPlayer, 1.0f);
                    player.invulnerableTime = 0;
                }));
            }
        }
    }

    private static void makeEffectCloudPoisonCrossbow(Level pLevel, Potion pPotion, Vec3 pPos, LivingEntity pShooter) {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(pLevel, pPos.x, pPos.y, pPos.z);
        areaEffectCloud.setOwner(pShooter);

        areaEffectCloud.setRadius(poisonAreaRadius);
        areaEffectCloud.setRadiusOnUse(-0.5F);
        areaEffectCloud.setWaitTime(10);
        areaEffectCloud.setDuration(poisonDuration);
        areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float) areaEffectCloud.getDuration());
        areaEffectCloud.setPotion(pPotion);
        areaEffectCloud.addEffect(PoisonCrossbow.poisonEffect);

        pLevel.addFreshEntity(areaEffectCloud);
    }
}
