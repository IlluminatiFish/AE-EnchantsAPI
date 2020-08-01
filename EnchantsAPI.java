package me.illuminatifish.enchantmentsapi.EnchantsAPI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

public class EnchantsAPI {

	public static boolean hasCustomEnchant(ItemStack item, String enchantment) {
		if(item == null || item.getType() == Material.AIR) {
			return false;
		} else {
			NBTItem nbtItem = new NBTItem(item);
			if(nbtItem.hasNBTData()) {
				for(String key : nbtItem.getKeys()) {
					if(key.startsWith("ae_enchantment")) {
						String enchant = key.split(";")[1];
						if(enchant.equalsIgnoreCase(enchantment)) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}
	
	public static int getEnchantLevel(ItemStack item, String enchantment) {
		if(item == null || item.getType() == Material.AIR) {
			return -1;
		} else {
			NBTItem nbtItem = new NBTItem(item);
			if(nbtItem.hasNBTData()) {
				for(String key : nbtItem.getKeys()) {
					if(key.startsWith("ae_enchantment")) {
						String enchant = key.split(";")[1];
						if(enchant.equalsIgnoreCase(enchantment)) {
							return nbtItem.getInteger(key);
						}
					}
				}
			}
			return 0;
		}	
	}
	
	public static int getLevelStack(Player player, String enchantment) {
		int levelStack = 0;
		for(ItemStack item : player.getInventory().getArmorContents()) {
			if(EnchantsAPI.hasCustomEnchant(item, enchantment)) {
				levelStack += EnchantsAPI.getEnchantLevel(item, enchantment);
			}
		}
		return levelStack;
	}
	
}
