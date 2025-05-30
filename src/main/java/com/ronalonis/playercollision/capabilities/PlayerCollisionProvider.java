package com.ronalonis.playercollision.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class PlayerCollisionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<PlayerCollision> CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerCollision instance = new PlayerCollisionImpl();
    private final LazyOptional<PlayerCollision> optional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("collisionEnabled", instance.isCollisionEnabled());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("collisionEnabled")) {
            instance.setCollisionEnabled(nbt.getBoolean("collisionEnabled"));
        }
    }
}