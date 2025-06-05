package me.eject.gui;

import me.eject.UltraRTP;
import me.eject.ConfigManager;
import me.eject.MaterialConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class GUIManager {
    private final UltraRTP plugin;
    private final Map<Integer, String> slotKeyMap = new HashMap<>();

    public GUIManager(UltraRTP plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        ConfigManager cfg = plugin.getConfigManager();
        String title = cfg.getGuiTitle();
        int rows = cfg.getRows();
        Inventory inv = Bukkit.createInventory(null, rows * 9, title);

        for (String key : new String[]{"overworld","nether","the_end"}) {
            MaterialConfig mc = cfg.getItemConfig(key);
            ItemStack item = new ItemStack(Material.valueOf(mc.getMaterial()));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(mc.getDisplayName());
            item.setItemMeta(meta);
            inv.setItem(mc.getSlot(), item);
            slotKeyMap.put(mc.getSlot(), key);
        }
        player.openInventory(inv);
    }

    public String getKey(int slot) {
        return slotKeyMap.get(slot);
    }
}
