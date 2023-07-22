package fr.groupecraft.adminshop;

import fr.groupecraft.adminshop.bazard.BazardCommand;
import fr.groupecraft.adminshop.item.Items;
import fr.groupecraft.adminshop.utils.Config;
import fr.groupecraft.adminshop.utils.EventListener;
import fr.groupecraft.adminshop.utils.Lang;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

public final class Adminshop extends JavaPlugin {
    private static Adminshop instance;
    public Economy economy;

    @Override
    public void onEnable() {
        super.onEnable();
        if (!setupEconomy()){
            System.out.println("Error with vault");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        System.out.println("Bazard enable");
        saveDefaultConfig();
        instance = this;

        this.getCommand("Bazard").setExecutor(new BazardCommand());
        Items.getInstance().loadItems();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Bazard disable");
        super.onDisable();
        Items.getInstance().saveItems();
    }

    public static Adminshop getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();
        return economy != null;
    }
}