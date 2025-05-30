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

public class EventHandlers {
    private static final ResourceLocation COLLISION_CAP_ID =
            new ResourceLocation("playercollision", "player_collision");

    // Убрано @EventBusSubscriber, регистрируем вручную

    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(COLLISION_CAP_ID, new PlayerCollisionProvider());
        }
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerCollision.class);
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player newPlayer = event.getEntity();

        // Всегда копируем состояние, не только при смерти
        original.reviveCaps();
        original.getCapability(PlayerCollisionProvider.CAPABILITY).ifPresent(oldCap -> {
            newPlayer.getCapability(PlayerCollisionProvider.CAPABILITY).ifPresent(newCap -> {
                newCap.setCollisionEnabled(oldCap.isCollisionEnabled());
            });
        });
        original.invalidateCaps();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CollisionCommand.register(event.getDispatcher());
    }
}