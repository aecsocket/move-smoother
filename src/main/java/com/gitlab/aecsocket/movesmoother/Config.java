package com.gitlab.aecsocket.movesmoother;

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
        public double fovCoefficient = 0.5;
    }

    private static Values values = new Values();
    private static final Gson gson = new Gson();

    public static Values values() { return values; }

    public static Path config() { return FabricLoader.getInstance().getConfigDir().resolve("move-smoother.json"); }

    public static void load() {
        try {
            BufferedReader reader = Files.newBufferedReader(config());
            values = gson.fromJson(reader, Values.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            values = null;
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
                    .startIntSlider(new TranslatableText("config.movesmoother.fov_coefficient"), (int) (values.fovCoefficient * 100), 0, 200)
                    .setDefaultValue(50)
                    .setTooltip(new TranslatableText("config.movesmoother.fov_coefficient.tooltip"))
                    .setSaveConsumer(v -> values.fovCoefficient = v / 100d)
                    .build()
            );
            return builder.build();
        };
    }
}
