package com.gitlab.aecsocket.movesmoother.client;

import com.gitlab.aecsocket.movesmoother.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

@Environment(EnvType.CLIENT)
public class MoveSmootherClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        Config.load();
    }
}
