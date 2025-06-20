package me.eject.listeners;

import me.eject.ConfigManager;
import me.eject.CooldownManager;
import me.eject.UltraRTP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class GUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ConfigManager cfg = UltraRTP.getInstance().getConfigManager();
        if (!e.getView().getTitle().equals(cfg.getGuiTitle())) return;
        e.setCancelled(true);
        if (e.getClickedInventory() == null || e.getClickedInventory().getType() != InventoryType.CHEST) return;

        int slot = e.getRawSlot();
        String key = UltraRTP.getInstance().getGUIManager().getKey(slot);
        if (key == null) return;

        Player player = (Player) e.getWhoClicked();
        CooldownManager cd = UltraRTP.getInstance().getCooldownManager();
        if (cd.isOnCooldown(player)) {
            player.sendMessage("§cPlease wait §e" + cd.getTimeLeft(player) + "§c seconds.");
            player.closeInventory();
            return;
        }

        player.closeInventory();
        new me.eject.TeleportTask(player, key).runTaskTimer(UltraRTP.getInstance(), 0L, 20L);
        cd.applyCooldown(player, cfg.getCooldown());
    }
}
