package io.jerboathesandwing.gui;

import io.jerboathesandwing.config.ConfigData;
import io.jerboathesandwing.config.ConfigManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.Language;

@SuppressWarnings("unchecked")
public class OptionScreen extends Screen {
	private final ConfigData config = ConfigManager.getConfig();
	private final Language language = Language.getInstance();
	private final Screen parentScreen;
	private EnchantmentListWidget enchantmentList;
	private TextFieldWidget bookAuthorWidget;
	private NumbericTextFieldWidget enchantmentLevelWidget;
	private NumbericTextFieldWidget enchantmentMultiplierWidget;

	public OptionScreen(Screen parent) {
		this.parentScreen = parent;
	}

	@Override
	public void init() {
		this.buttons.add(new ButtonWidget(200, this.width / 2 - 100, this.height - 32, language.translate("gui.done")));
		this.bookAuthorWidget = new TextFieldWidget(this.textRenderer, this.width / 2 - 90, this.height - 42 - 34 - 38, 180, 20);
		this.bookAuthorWidget.setText(config.bookAuthor);
		this.bookAuthorWidget.setMaxLength(256);
		this.enchantmentLevelWidget = new NumbericTextFieldWidget(this.textRenderer, this.width / 2 - 90, this.height - 42 - 34, 80, 20, true, this::onLevelSliderChange);
		this.enchantmentMultiplierWidget = new NumbericTextFieldWidget(this.textRenderer, this.width / 2 + 10, this.height - 42 - 34, 80, 20, false, this::onMultiplierSliderChange);
		this.enchantmentList = new EnchantmentListWidget(this.field_1229, this, this::onEnchantmentIndexChange);
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		if (button.active) {
			if (button.id == 200) {
				ConfigManager.saveConfig();
				this.field_1229.openScreen(parentScreen);
			}
			else {
				this.enchantmentList.buttonClicked(button);
			}
		}
	}

	@Override
	protected void keyPressed(char id, int code) {
		this.bookAuthorWidget.keyPressed(id, code);
		this.enchantmentLevelWidget.keyPressed(id, code);
		this.enchantmentMultiplierWidget.keyPressed(id, code);
		super.keyPressed(id, code);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		this.bookAuthorWidget.mouseClicked(mouseX, mouseY, button);
		this.enchantmentLevelWidget.mouseClicked(mouseX, mouseY, button);
		this.enchantmentMultiplierWidget.mouseClicked(mouseX, mouseY, button);
		super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void tick() {
		this.bookAuthorWidget.tick();
		this.enchantmentLevelWidget.tick();
		this.enchantmentMultiplierWidget.tick();
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		this.renderBackground();
		this.enchantmentList.render(mouseX, mouseY, tickDelta);
		this.drawCenteredString(this.textRenderer, "BookHack Settings", this.width / 2, 16, 16777215);
		this.drawWithShadow(this.textRenderer, "Book Author", this.width / 2 - 90, this.height - 42 - 34 - 38 - 12, 10526880);
		this.bookAuthorWidget.render();
		this.drawWithShadow(this.textRenderer, "Level", this.width / 2 - 90, this.height - 42 - 34 - 12, 10526880);
		this.enchantmentLevelWidget.render();
		this.drawWithShadow(this.textRenderer, "Multiplier", this.width / 2 + 10, this.height - 42 - 34 - 12, 10526880);
		this.enchantmentMultiplierWidget.render();
		this.drawWithShadow(this.textRenderer, "Min: -32768 | Max: 32767", this.width / 2 - 90, this.height - 42 - 4, 10526880);
		super.render(mouseX, mouseY, tickDelta);
	}

	@Override
	public void removed() {
		config.bookAuthor = this.bookAuthorWidget.getText();
	}

	private void onEnchantmentIndexChange(int index) {
		ConfigData.BookEnchantment bookEnchantment = this.config.bookEnchantments.get(index);
		this.enchantmentLevelWidget.setNumericValue(bookEnchantment.level);
		this.enchantmentLevelWidget.setCursor(0);
		this.enchantmentMultiplierWidget.setNumericValue(bookEnchantment.multiplier);
		this.enchantmentMultiplierWidget.setCursor(0);
	}

	private void onLevelSliderChange(int value) {
		ConfigData.BookEnchantment bookEnchantment = this.enchantmentList.getSelectedBookEnchantment();
		bookEnchantment.level = (short) value;
	}

	private void onMultiplierSliderChange(int value) {
		ConfigData.BookEnchantment bookEnchantment = this.enchantmentList.getSelectedBookEnchantment();
		bookEnchantment.multiplier = (short) value;
	}
}
