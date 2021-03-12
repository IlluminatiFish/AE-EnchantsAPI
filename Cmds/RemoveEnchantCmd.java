package me.illuminatifish.enchantsapi.Cmds;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import de.tr7zw.nbtapi.NBTItem;
import me.illuminatifish.enchantsapi.Utils.Utils;

public class RemoveEnchantCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] parameters) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            if(player.hasPermission("enchantsapi.admin.removeenchantcmd")) {
                if(parameters.length == 1) {
                    String enchant = parameters[0];
                    ItemStack itemStack = player.getItemInHand();
                    if (Utils.isNull(itemStack)) {
                        player.sendMessage(Utils.colorizeString("&c&l(!) &cCannot remove enchantment from a null/air item!"));
                        return true;
                    }
                    if(itemStack.hasItemMeta()) {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        List<String> itemLore = itemMeta.getLore();
                        try {
                            int line = 0;
                            for(int i = 0; i < itemStack.getItemMeta().getLore().size(); i++) {
                                try {
                                    int delimeter = itemStack.getItemMeta().getLore().get(i).lastIndexOf(" ") + 1;
                                    String enchantOnLore = itemStack.getItemMeta().getLore().get(i).substring(0, delimeter - 1);
                                    if(ChatColor.stripColor(enchantOnLore).equalsIgnoreCase(enchant)) {
                                        /*
                                         *  TODO:
                                         *       - Remove all lines that have the enchant name on them instead of just one
                                         *       - prevents bugs from occuring that say there is no enchant nbt on that item.
                                         */
                                        line = i;

                                        itemLore.remove(itemLore.get(line));
                                        itemMeta.setLore(itemLore);
                                        itemStack.setItemMeta(itemMeta);

                                        player.updateInventory();
                                        break;
                                    }
                                } catch(StringIndexOutOfBoundsException ex_string) {
                                    player.sendMessage(Utils.colorizeString("&c&l(!) &cA fatal error occured while attempting to check enchant on lore with enchant entered"));
                                    return true;
                                }
                            }
                            NBTItem nbtItem = new NBTItem(itemStack);
                            if(nbtItem.hasNBTData()) {
                                String key = "ae_enchantment;"+String.valueOf(enchant).toLowerCase();
                                if(nbtItem.hasKey(key)) {
                                    player.getInventory().removeItem(nbtItem.getItem());
                                    nbtItem.removeKey(key);
                                    if(nbtItem.hasKey("slots")) {
                                        nbtItem.setInteger("slots", nbtItem.getInteger("slots") - 1);
                                    }
                                    player.getInventory().addItem(nbtItem.getItem());

                                    player.sendMessage(Utils.colorizeString("&a&l(!) &aYou have removed the '"+String.valueOf(enchant).toUpperCase()+"' enchantment off of this item!"));
                                    return true;
                                } else {
                                    player.sendMessage(Utils.colorizeString("&c&l(!) &cThis item does not have the enchant '"+enchant+"' on it!"));
                                    return true;
                                }
                            } else {
                                player.sendMessage(Utils.colorizeString("&c&l(!) &cThis item does not have any NBT data!"));
                                return true;
                            }
                        } catch(NullPointerException ex) {
                            player.sendMessage(Utils.colorizeString("&c&l(!) &cCould not find the enchant '"+enchant+"' in the item metadata!"));
                        }
                    } else {
                        player.sendMessage(Utils.colorizeString("&c&l(!) &cThis item does not have any item metadata!"));
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.colorizeString("&c&l(!) &cInvalid command usage, please use it like /removeenchant <enchantment>"));
                    return true;
                }
            } else {
                player.sendMessage(Utils.colorizeString("&c&l(!) &cYou do not have permissions for this administrative command!"));
                return true;
            }
        }
        return false;
    }
}

