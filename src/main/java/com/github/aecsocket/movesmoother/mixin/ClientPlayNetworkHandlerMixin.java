package com.github.aecsocket.movesmoother.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Final @Shadow private MinecraftClient client;

    @Inject(method = "onPlayerPositionLook", at = @At("HEAD"), cancellable = true)
    public void onPlayerPositionLook(PlayerPositionLookS2CPacket packet, CallbackInfo callback) {
        Set<PlayerPositionLookS2CPacket.Flag> flags = packet.getFlags();
        if (
            flags.contains(PlayerPositionLookS2CPacket.Flag.X) && packet.getX() == 0
            && flags.contains(PlayerPositionLookS2CPacket.Flag.Y) && packet.getY() == 0
            && flags.contains(PlayerPositionLookS2CPacket.Flag.Z) && packet.getZ() == 0
        ) {
            NetworkThreadUtils.forceMainThread(packet, (ClientPlayNetworkHandler) (Object) this, client);
            PlayerEntity player = client.player;

            EntityAccessor aPlayer = (EntityAccessor) player;
            aPlayer.setYaw(aPlayer.getYaw() + packet.getYaw());
            aPlayer.setPitch(MathHelper.clamp(aPlayer.getPitch() + packet.getPitch(), -90, 90));
            player.prevYaw = packet.getYaw();
            player.prevPitch = packet.getPitch();
            callback.cancel();
        }
    }
}
