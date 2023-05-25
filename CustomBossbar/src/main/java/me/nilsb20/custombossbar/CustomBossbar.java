package me.nilsb20.custombossbar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomBossbar extends JavaPlugin {

    private static CustomBossbar instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("playerbar").setExecutor(new PlayerBarManager());
        Bukkit.getPluginManager().registerEvents(new PlayerBarManager(), instance);

        Bukkit.getLogger().info("Custom Bossbar enabled!");
    }

    @Override
    public void onDisable() {
        PlayerBarManager.serverStops();
        Bukkit.getLogger().info("Custom Bossbar disabled!");
    }

    public static CustomBossbar getInstance() {
        return instance;
    }
}
