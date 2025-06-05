package me.eject;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final FileConfiguration cfg;

    public ConfigManager(UltraRTP plugin) {
        this.cfg = plugin.getConfig();
    }

    public int getDelay() {
        return cfg.getInt("delay");
    }

    public int getCooldown() {
        return cfg.getInt("cooldown");
    }

    public int getRadius(String worldKey) {
        return cfg.getConfigurationSection("radius").getInt(worldKey);
    }

    public String getGuiTitle() {
        return cfg.getString("gui-title");
    }

    public int getRows() {
        return cfg.getInt("rows");
    }

    public MaterialConfig getItemConfig(String key) {
        return new MaterialConfig(
                cfg.getString("items." + key + ".material"),
                cfg.getInt("items." + key + ".slot"),
                cfg.getString("items." + key + ".display-name")
        );
    }
}
