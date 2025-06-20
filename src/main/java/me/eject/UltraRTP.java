package me.eject;

import org.bukkit.plugin.java.JavaPlugin;
import me.eject.gui.GUIManager;
import me.eject.listeners.GUIListener;
import me.eject.commands.RTPCommand;
import org.bukkit.scheduler.BukkitRunnable;

public class UltraRTP extends JavaPlugin {
    private static UltraRTP instance;
    private ConfigManager configManager;
    private CooldownManager cooldownManager;
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        new BukkitRunnable() {
            @Override
            public void run() {
                configManager = new ConfigManager(UltraRTP.this);
                cooldownManager = new CooldownManager();
                guiManager = new GUIManager(UltraRTP.this);

                getCommand("rtp").setExecutor(new RTPCommand());
                getServer().getPluginManager().registerEvents(new GUIListener(), UltraRTP.this);
            }
        }.runTask(this);
    }

    public static UltraRTP getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }
}
