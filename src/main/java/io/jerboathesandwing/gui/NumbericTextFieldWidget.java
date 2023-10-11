package io.jerboathesandwing.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.SharedConstants;

import java.util.function.Consumer;

public class NumbericTextFieldWidget extends TextFieldWidget {
	private final boolean allowNegativNumbers;
	private final Consumer<Integer> valueChangeConsumer;

	public NumbericTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, boolean allowNegativNumbers, Consumer<Integer> valueChangeConsumer) {
		super(textRenderer, x, y, width, height);
		this.allowNegativNumbers = allowNegativNumbers;
		this.valueChangeConsumer = valueChangeConsumer;
		this.setMaxLength(6);
	}

	@Override
	public void write(String text) {
		super.write(this.stripInvalidChars(text));
		this.valueChangeConsumer.accept(this.getNumericValue());
	}

	private String stripInvalidChars(String text) {
		StringBuilder stringBuilder = new StringBuilder();
		char[] chars = text.toCharArray();

		for (int i = 0; i < chars.length; ++i) {
			char chari = chars[i];
			if (SharedConstants.isValidChar(chari) && (Character.isDigit(chari) || (this.allowNegativNumbers && (this.selectionStart == 0 && i == 0 && chari == '-')))) {
				stringBuilder.append(chari);
			}
		}

		return stringBuilder.toString();
	}

	public void setNumericValue(int value) {
		this.text = Integer.toString(value);
	}

	public int getNumericValue() {
		if (this.text.length() > 0) {
			return Integer.parseInt(this.text);
		}
		else {
			return 0;
		}
	}
}
