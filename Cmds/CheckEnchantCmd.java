package me.illuminatifish.enchantsapi.Cmds;

import me.illuminatifish.enchantsapi.EnchantsAPI;
import me.illuminatifish.enchantsapi.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckEnchantCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] parameters) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (sender.hasPermission("enchantsapi.admin.checkenchantcmd")) {
                if (parameters.length == 1) {
                    String enchant = parameters[0];
                    ItemStack itemStack = player.getItemInHand();
                    if (Utils.isNull(itemStack)) {
                        player.sendMessage(Utils.colorizeString("&c&l(!) &cCannot get enchantment information from a null/air item!"));
                        return true;
                    }
                    if (EnchantsAPI.hasCustomEnchant(itemStack, enchant)) {
                        int level = EnchantsAPI.getEnchantLevel(itemStack, enchant);
                        player.sendMessage(Utils.colorizeString("&a&l(!) &aThe item in your hand does have the enchant '" + enchant + "' at level " + level));
                        return true;
                    }
                    player.sendMessage(Utils.colorizeString("&c&l(!) &cThe item in your hand does not have the enchant '" + enchant + "'"));
                    return true;
                }
            } else {
                player.sendMessage(Utils.colorizeString("&8[&cEnchantments&fAPI&8] &cYou do not have permissions for this administrative command!"));
                return true;
            }
        }
        return false;
    }
}

