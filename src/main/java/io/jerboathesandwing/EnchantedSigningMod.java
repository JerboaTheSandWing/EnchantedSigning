package io.jerboathesandwing;

import io.jerboathesandwing.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EnchantedSigningMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ConfigManager.loadConfig();
	}
}
