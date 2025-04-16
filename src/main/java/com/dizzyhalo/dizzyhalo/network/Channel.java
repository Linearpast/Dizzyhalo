package com.dizzyhalo.dizzyhalo.network;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.network.toclient.ParticlesPacket;
import com.dizzyhalo.dizzyhalo.network.toclient.TotemRenderPacket;
import com.dizzyhalo.dizzyhalo.network.toserver.EnchantLevelUpPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Channel {
    private static final String PROTOCOL_VERSION = "1";
    private static int cid = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Dizzyhalo.MODID, "dizzyhalo"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.messageBuilder(TotemRenderPacket.class, cid++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TotemRenderPacket::new)
                .encoder(TotemRenderPacket::encode)
                .consumerNetworkThread(TotemRenderPacket::handle)
                .add();
        INSTANCE.messageBuilder(ParticlesPacket.class, cid++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticlesPacket::new)
                .encoder(ParticlesPacket::encode)
                .consumerNetworkThread(ParticlesPacket::handle)
                .add();

        INSTANCE.messageBuilder(EnchantLevelUpPacket.class, cid++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(EnchantLevelUpPacket::new)
                .encoder(EnchantLevelUpPacket::encode)
                .consumerNetworkThread(EnchantLevelUpPacket::handle)
                .add();

    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendAllPlayer(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
    public static <MSG> void sendServer(MSG message){
        INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
    }
}
