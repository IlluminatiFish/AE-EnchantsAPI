import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import de.tr7zw.nbtapi.NBTItem;
import me.illuminatifish.enchantmentsapi.EnchantsAPI.Utils.Util;

public class RemoveEnchantCmd implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] parameters) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(command.getName().equalsIgnoreCase("removeenchant")) {
				if(sender.hasPermission("impulseenchantments.admin.removeenchantcmd")) {
					if(parameters.length == 1) {
						String enchant = parameters[0];
						ItemStack is = p.getItemInHand();
						if(is == null || is.getType() == Material.AIR) {
							p.sendMessage(Util.Translate("&c&l(!) &cCannot remove enchantment from a null/air item!"));
							return true;
						}						
						if(is.hasItemMeta()) {
							ItemMeta itemMeta = is.getItemMeta();
							List<String> itemLore = itemMeta.getLore();	
							try {
								int line = 0;
								for(int i = 0; i < is.getItemMeta().getLore().size(); i++) {
									try {
										int delimeter = is.getItemMeta().getLore().get(i).lastIndexOf(" ") + 1;
										String enchantOnLore = is.getItemMeta().getLore().get(i).substring(0, delimeter - 1);
										
										if(ChatColor.stripColor(enchantOnLore).equalsIgnoreCase(enchant)) {
											/*
											 * - TODO: Remove all lines that have the enchant name on them instead of just one
											 * - prevents bugs from occuring that say there is no enchant nbt on that item.
											 */
											line = i;
											
											itemLore.remove(itemLore.get(line));											
											itemMeta.setLore(itemLore);
											is.setItemMeta(itemMeta);
											
											p.updateInventory();	
											break;
										}
									} catch(StringIndexOutOfBoundsException ex_string) {
										p.sendMessage(Util.Translate("&c&l(!) &cA fatal error occured while attempting to check enchant on lore with enchant entered"));
										return true;
									}
								}
								NBTItem nbtItem = new NBTItem(is);
								if(nbtItem.hasNBTData()) {
									
									String key = "ae_enchantment;"+String.valueOf(enchant).toLowerCase();
									if(nbtItem.hasKey(key)) {
										p.getInventory().removeItem(nbtItem.getItem());
										nbtItem.removeKey(key);			
										if(nbtItem.hasKey("slots")) {
											nbtItem.setInteger("slots", nbtItem.getInteger("slots") - 1);
										}
										p.getInventory().addItem(nbtItem.getItem());
										
										p.sendMessage(Util.Translate("&a&l(!) &aYou have removed the '"+String.valueOf(enchant).toUpperCase()+"' enchantment off of this item!"));
										return true;
									} else {
										p.sendMessage(Util.Translate("&c&l(!) &cThis item does not have the enchant '"+enchant+"' on it!"));
										return true;								
									}
								} else {
									p.sendMessage(Util.Translate("&c&l(!) &cThis item does not have any NBT data!"));
									return true;
								}								
							} catch(NullPointerException ex) {
								p.sendMessage(Util.Translate("&c&l(!) &cCould not find the enchant '"+enchant+"' in the item meta!"));
							}
						} else {
							p.sendMessage(Util.Translate("&c&l(!) &cThis item does not have any item metadata!"));
							return true;							
						}		
					} else {
						p.sendMessage(Util.Translate("&c&l(!) &cInvalid command usage, please use it like /removeenchant <enchantment>"));
						return true;
					}
				} else {
					p.sendMessage(Util.Translate("&c&l(!) &cYou do not have permissions for this administrative command!"));
					return true;
				}
			}
		}
		return false;
	}		
}
