package com.ronalonis.playercollision;

import com.ronalonis.playercollision.capabilities.PlayerCollision;
import com.ronalonis.playercollision.capabilities.PlayerCollisionProvider;
import com.ronalonis.playercollision.commands.CollisionCommand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandlers {
    private static final ResourceLocation COLLISION_CAP_ID =
            new ResourceLocation("collisionmod", "player_collision");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(COLLISION_CAP_ID, new PlayerCollisionProvider());
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerCollision.class);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerCollisionProvider.CAPABILITY).ifPresent(oldCap -> {
                event.getPlayer().getCapability(PlayerCollisionProvider.CAPABILITY).ifPresent(newCap -> {
                    newCap.setCollisionEnabled(oldCap.isCollisionEnabled());
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CollisionCommand.register(event.getDispatcher());
    }
}