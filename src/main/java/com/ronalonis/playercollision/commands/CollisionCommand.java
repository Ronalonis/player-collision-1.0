package com.ronalonis.playercollision.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.ronalonis.playercollision.capabilities.PlayerCollisionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CollisionCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("collision")
                .then(Commands.literal("on")
                        .executes(ctx -> setCollision(ctx, true))
                )
                .then(Commands.literal("off")
                        .executes(ctx -> setCollision(ctx, false))
                )
        );
    }

    private static int setCollision(CommandContext<CommandSourceStack> ctx, boolean enabled) {
        Player player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Command only for players"));
            return 0;
        }

        player.getCapability(PlayerCollisionProvider.CAPABILITY).ifPresent(cap -> {
            cap.setCollisionEnabled(enabled);
        });

        String status = enabled ? "enabled" : "disabled";
        ctx.getSource().sendSuccess(
                () -> Component.literal("Player collision " + status),
                true
        );
        return 1;
    }
}
