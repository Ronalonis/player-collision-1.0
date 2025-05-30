package com.ronalonis.playercollision.mixin;

import com.ronalonis.playercollision.capabilities.PlayerCollisionProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "push", at = @At("HEAD"), cancellable = true)
    private void onPush(Entity entity, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;

        if (self instanceof Player player1 && entity instanceof Player player2) {
            boolean collisionEnabled1 = player1.getCapability(PlayerCollisionProvider.CAPABILITY)
                    .map(cap -> cap.isCollisionEnabled())
                    .orElse(true);

            boolean collisionEnabled2 = player2.getCapability(PlayerCollisionProvider.CAPABILITY)
                    .map(cap -> cap.isCollisionEnabled())
                    .orElse(true);

            if (!collisionEnabled1 || !collisionEnabled2) {
                ci.cancel();
            }
        }
    }
}