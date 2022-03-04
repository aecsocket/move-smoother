package com.github.aecsocket.movesmoother.mixin;

import com.github.aecsocket.movesmoother.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = Mouse.class, priority = 1100)
public class MouseMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    private static long next;

    @ModifyVariable(
        at = @At("STORE"),
        method = "updateMouse()V",
        ordinal = 3)
    private double sensitivity(double g) {
        double fovMult = ((GameRendererAccessor) client.gameRenderer).getFovMultiplier();
        return MathHelper.lerp(Config.values().sensLerp, g, g * MathHelper.clamp(fovMult, 0, 1));
    }
}
        //double fovMult = ((GameRendererAccessor) client.gameRenderer).getFovMultiplier();
        /*double fovMult = 1 - MathHelper.clamp(rawFovMult, 0, 1);
        if (System.currentTimeMillis() > next) {
            System.out.printf("%.5f / %.5f / %.3f\n", rawFovMult, fovMult, Config.values().fovCoefficient);
            next = System.currentTimeMillis() + 1000;
        }
        return f * MathHelper.clamp(fovMult, 0, 1);
        //f *= 1 - MathHelper.clamp(fovMult * Config.values().fovCoefficient, 0, 1);
        //return f;
    }
}*/
