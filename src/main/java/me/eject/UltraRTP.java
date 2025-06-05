package me.eject;

import org.bukkit.plugin.java.JavaPlugin;
import me.eject.gui.GUIManager;
import me.eject.listeners.GUIListener;
import me.eject.commands.RTPCommand;

public class UltraRTP extends JavaPlugin {
    private static UltraRTP instance;
    private ConfigManager   configManager;
    private CooldownManager cooldownManager;
    private GUIManager      guiManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        configManager    = new ConfigManager(this);
        cooldownManager  = new CooldownManager();
        guiManager       = new GUIManager(this);

        getCommand("rtp").setExecutor(new RTPCommand());
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    public static UltraRTP getInstance() {
        return instance;
    }

    public ConfigManager   getConfigManager()  { return configManager;  }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public GUIManager      getGUIManager()     { return guiManager;      }
}
