package com.gitlab.aecsocket.movesmoother.mixin;

import com.gitlab.aecsocket.movesmoother.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = Mouse.class, priority = 1100)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @ModifyVariable(
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;client:Lnet/minecraft/client/MinecraftClient;", ordinal = 2),
            method = "updateMouse()V",
            ordinal = 3)
    private double sensitivity(double f) {
        double fovMult = 1 - MathHelper.clamp(((GameRendererAccessor) client.gameRenderer).getMovementFovMultiplier(), 0, 1);
        f *= 1 - (fovMult * Config.values().fovCoefficient);
        return f;
    }
}
