package io.jerboathesandwing.mixin;

import io.jerboathesandwing.config.ConfigData;
import io.jerboathesandwing.config.ConfigManager;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin {
	@Shadow
	@Final
	private ItemStack item;

	@Inject(method = "method_1137", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;writeItemStack(Lnet/minecraft/item/ItemStack;Ljava/io/DataOutputStream;)V"))
	private void changeBookData(boolean par1, CallbackInfo ci) {
		ConfigData configData = ConfigManager.getConfig();

		this.item.putSubNbt("author", new NbtString("author", configData.bookAuthor));

		for (ConfigData.BookEnchantment bookEnchantment : configData.bookEnchantments) {
			for (int i = 0; i < bookEnchantment.multiplier; i++) {
				this.addEnchantment(this.item, Enchantment.ALL_ENCHANTMENTS[bookEnchantment.id], bookEnchantment.level);
			}
		}
	}

	@Unique
	private void addEnchantment(ItemStack stack, Enchantment enchantment, int level) {
		if (stack == null || enchantment == null) {
			return;
		}

		if (stack.nbt == null) {
			stack.setNbt(new NbtCompound());
		}

		if (!stack.nbt.contains("ench")) {
			stack.nbt.put("ench", new NbtList("ench"));
		}

		NbtList enchListNbt = (NbtList) stack.nbt.get("ench");
		NbtCompound enchNbt = new NbtCompound();
		enchNbt.putShort("id", (short) enchantment.id);
		enchNbt.putShort("lvl", (short) level);
		enchListNbt.method_1217(enchNbt);
	}
}
