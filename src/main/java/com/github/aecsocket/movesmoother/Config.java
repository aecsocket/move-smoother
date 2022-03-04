package com.github.aecsocket.movesmoother;

import com.google.gson.Gson;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.TranslatableText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config implements ModMenuApi {
    public static final class Values {
        public double sensLerp = 0.9;
    }

    private static Values values = new Values();
    private static final Gson gson = new Gson();

    public static Values values() { return values; }

    public static Path config() { return FabricLoader.getInstance().getConfigDir().resolve("move-smoother.json"); }

    public static void load() {
        if (config().toFile().exists()) {
            try {
                BufferedReader reader = Files.newBufferedReader(config());
                values = gson.fromJson(reader, Values.class);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                values = null;
            }
        }
        if (values == null) {
            values = new Values();
        }
    }

    public static void save() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(config());
            gson.toJson(values, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        load();
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("config.movesmoother.title"))
                .setSavingRunnable(Config::save);
            ConfigCategory category = builder.getOrCreateCategory(new TranslatableText(""));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            category.addEntry(entryBuilder
                .startIntSlider(new TranslatableText("config.movesmoother.sens_lerp"), (int) (values.sensLerp * 100), 0, 100)
                .setDefaultValue(90)
                .setTooltip(new TranslatableText("config.movesmoother.sens_lerp.tooltip"))
                .setSaveConsumer(v -> values.sensLerp = v / 100d)
                .build());
            return builder.build();
        };
    }
}
