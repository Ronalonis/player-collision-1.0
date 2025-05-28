package com.ronalonis.playercollision;

import com.ronalonis.playercollision.capabilities.PlayerCollision;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
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

