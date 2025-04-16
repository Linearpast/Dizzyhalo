package com.dizzyhalo.dizzyhalo;

import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import com.dizzyhalo.dizzyhalo.network.Channel;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Dizzyhalo.MODID)
public class Dizzyhalo {
    public static final String MODID = "dizzyhalo";

    public Dizzyhalo() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.REGISTER.register(bus);

        Channel.register();
    }

}
