package io.jerboathesandwing.config;

import net.minecraft.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class ConfigData {
	public String bookAuthor;
	public List<BookEnchantment> bookEnchantments;

	public ConfigData() {
		this.bookAuthor = "EnchantedSigning Mod";
		this.bookEnchantments = new ArrayList<>();

		for (Enchantment enchantment : Enchantment.ALL_ENCHANTMENTS) {
			if (enchantment != null) {
				this.bookEnchantments.add(new BookEnchantment(enchantment.id, (short) 0, (short) 0));
			}
		}
	}

	public static class BookEnchantment {
		public int id;
		public short level;
		public short multiplier;

		public BookEnchantment(int id, short level, short multiplier) {
			this.id = id;
			this.level = level;
			this.multiplier = multiplier;
		}
	}
}
