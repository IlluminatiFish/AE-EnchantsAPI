package me.illuminatifish.enchantsapi.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static boolean isNull(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public static String colorizeString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
