package me.illuminatifish.enchantsapi;

import me.illuminatifish.enchantsapi.Cmds.RemoveEnchantCmd;
import me.illuminatifish.enchantsapi.Cmds.CheckEnchantCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class Launch extends JavaPlugin {
    public void onEnable() {
        getCommand("checkenchant").setExecutor(new CheckEnchantCmd());
        getCommand("removeenchant").setExecutor(new RemoveEnchantCmd());
        System.out.println("[EnchantmentsAPI] EnchantmentsAPI v0.3-DEV by IlluminatiFish#0753 has been enabled!");
    }
}
