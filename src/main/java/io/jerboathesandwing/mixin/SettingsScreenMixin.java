package io.jerboathesandwing.mixin;

import io.jerboathesandwing.gui.OptionScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SettingsScreen.class)
public class SettingsScreenMixin extends Screen {
	@Inject(method = "init", at = @At("TAIL"))
	private void init(CallbackInfo ci) {
		this.buttons.add(new ButtonWidget(121, this.width / 2 - 152, this.height / 6 + 144 - 6, 150, 20, "BookHack Settings..."));
	}

	@Inject(method = "buttonClicked", at = @At("HEAD"))
	private void buttonClicked(ButtonWidget button, CallbackInfo ci) {
		if (button.active && button.id == 121) {
			this.field_1229.openScreen(new OptionScreen(this));
		}
	}
}
