package io.jerboathesandwing.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class ConfigManager {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private static ConfigData config;

	public static ConfigData getConfig() {
		return config;
	}

	public static void loadConfig() {
		File configFile = getConfigFile();

		if (!configFile.exists()) {
			config = new ConfigData();
			saveConfig();
		}
		else {
			try (Reader reader = new FileReader(configFile)) {
				config = GSON.fromJson(reader, ConfigData.class);
			} catch (IOException e) {
				System.err.println("Couldn't save config file, reverting to defaults");
				config = new ConfigData();
				saveConfig();
			}
		}
	}

	public static void saveConfig() {
		try (Writer writer = new FileWriter(getConfigFile())) {
			GSON.toJson(config, writer);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't save config file", e);
		}
	}

	private static File getConfigFile() {
		return new File(FabricLoader.getInstance().getConfigDir().toString(), "enchantedsigning.json");
	}
}
