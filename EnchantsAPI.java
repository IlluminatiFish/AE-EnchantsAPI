package me.illuminatifish.enchantsapi;

import me.illuminatifish.enchantsapi.Utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;


public class EnchantsAPI {

    public static boolean hasCustomEnchant(ItemStack item, String enchantment) {
        if(!Utils.isNull(item)) {
            NBTItem nbtItem = new NBTItem(item);
            if(nbtItem.hasNBTData()) {
                for(String key : nbtItem.getKeys()) {
                    if(key.startsWith("ae_enchantment")) {
                        return key.split(";")[1].equalsIgnoreCase(enchantment);
                    }
                }
            }
        }
        return false;
    }

    public static int getEnchantLevel(ItemStack item, String enchantment) {
        if(!Utils.isNull(item)) {
            NBTItem nbtItem = new NBTItem(item);
            if(nbtItem.hasNBTData()) {
                for(String key : nbtItem.getKeys()) {
                    if(key.startsWith("ae_enchantment") &&
                            key.split(";")[1].equalsIgnoreCase(enchantment)) {
                        //return nbtItem.getInteger(key.split(";")[1]); - Old method to get the level of the enchantment, due to NBT composition changing the code had to be adjusted too.
                        return nbtItem.getInteger(key).intValue();
                    }

                }
            }
        }
        return -1;
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
