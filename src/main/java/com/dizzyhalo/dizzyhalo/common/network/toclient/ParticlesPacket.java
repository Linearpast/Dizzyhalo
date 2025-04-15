package com.dizzyhalo.dizzyhalo.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ParticlesPacket {
    private final String particleType;
    private final double px;
    private final double py;
    private final double pz;
    private final double sx;
    private final double sy;
    private final double sz;
    public ParticlesPacket(String  particleType, double px, double py, double pz, double sx, double sy, double sz) {
        this.particleType = particleType;
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.sx = sx;
        this.sy = sy;
        this.sz = sz;
    }
    public ParticlesPacket(FriendlyByteBuf buf) {
        particleType = buf.readUtf();
        px = buf.readDouble();
        py = buf.readDouble();
        pz = buf.readDouble();
        sx = buf.readDouble();
        sy = buf.readDouble();
        sz = buf.readDouble();
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(particleType);
        buf.writeDouble(px);
        buf.writeDouble(py);
        buf.writeDouble(pz);
        buf.writeDouble(sx);
        buf.writeDouble(sy);
        buf.writeDouble(sz);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ParticleType<?> type = ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(particleType));
            ClientLevel level = Minecraft.getInstance().level;
            if(level != null){
                level.addParticle((ParticleOptions) type,px,py,pz,sx,sy,sz);
            }

            ctx.setPacketHandled(true);
        });
    }
}
