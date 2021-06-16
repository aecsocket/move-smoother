package com.gitlab.aecsocket.movesmoother.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    float getYaw();
    @Accessor
    void setYaw(float yaw);

    @Accessor
    float getPitch();
    @Accessor
    void setPitch(float pitch);
}
