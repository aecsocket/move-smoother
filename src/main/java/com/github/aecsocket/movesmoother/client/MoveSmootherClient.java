package com.github.aecsocket.movesmoother.client;

import com.github.aecsocket.movesmoother.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MoveSmootherClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        Config.load();
    }
}
