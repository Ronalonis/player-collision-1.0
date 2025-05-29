package com.ronalonis.playercollision;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CollisionMod.MOD_ID)
public class CollisionMod {
    public static final String MOD_ID = "playercollision";

    public CollisionMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(EventHandlers.class);
    }
}

