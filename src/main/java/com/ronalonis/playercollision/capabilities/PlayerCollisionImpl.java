package com.ronalonis.playercollision.capabilities;

public class PlayerCollisionImpl implements PlayerCollision {
    private boolean collisionEnabled = true;

    @Override
    public boolean isCollisionEnabled() {
        return collisionEnabled;
    }

    @Override
    public void setCollisionEnabled(boolean enabled) {
        collisionEnabled = enabled;
    }
}
