package io.jerboathesandwing.gui;

import io.jerboathesandwing.config.ConfigData;
import io.jerboathesandwing.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ListWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.CommonI18n;

import java.util.function.Consumer;

public class EnchantmentListWidget extends ListWidget {
	private final ConfigData config = ConfigManager.getConfig();
	private final Screen parentScreen;
	private final Consumer<Integer> indexChangeConsumer;
	private int selectedIndex;

	public EnchantmentListWidget(Minecraft client, Screen parentScreen, Consumer<Integer> indexChangeConsumer) {
		super(client, parentScreen.width, parentScreen.height, 32, parentScreen.height - 130, 16);
		this.parentScreen = parentScreen;
		this.indexChangeConsumer = indexChangeConsumer;

		this.selectedIndex = 0;
		this.indexChangeConsumer.accept(this.selectedIndex);
	}

	@Override
	protected int getEntryCount() {
		return config.bookEnchantments.size();
	}

	@Override
	protected void method_1057(int index, boolean bl) {
		this.selectedIndex = index;
		this.indexChangeConsumer.accept(index);
	}

	@Override
	protected boolean isEntrySelected(int index) {
		return this.selectedIndex == index;
	}

	@Override
	protected void renderBackground() {
		this.parentScreen.renderBackground();
	}

	@Override
	protected void method_1055(int index, int x, int y, int l, Tessellator tessellator) {
		ConfigData.BookEnchantment bookEnchantment = config.bookEnchantments.get(index);
		Enchantment enchantment = Enchantment.ALL_ENCHANTMENTS[bookEnchantment.id];

		this.parentScreen.drawWithShadow(this.parentScreen.textRenderer, CommonI18n.translate(enchantment.getTranslationKey()), x + 2, y + 2, 16777215);
		this.parentScreen.drawWithShadow(this.parentScreen.textRenderer, "" + bookEnchantment.level, x + 130, y + 2, 16777215);
		this.parentScreen.drawWithShadow(this.parentScreen.textRenderer, "" + bookEnchantment.multiplier, x + 170, y + 2, 16777215);
	}

	public ConfigData.BookEnchantment getSelectedBookEnchantment() {
		return config.bookEnchantments.get(this.selectedIndex);
	}
}
