package me.eject;

import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {
    private final ConcurrentHashMap<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    public boolean isOnCooldown(Player player) {
        UUID id = player.getUniqueId();
        if (!cooldowns.containsKey(id)) return false;
        return System.currentTimeMillis() < cooldowns.get(id);
    }

    public long getTimeLeft(Player player) {
        UUID id = player.getUniqueId();
        long expire = cooldowns.getOrDefault(id, 0L);
        return Math.max(0, (expire - System.currentTimeMillis()) / 1000);
    }

    public void applyCooldown(Player player, int seconds) {
        long expire = System.currentTimeMillis() + seconds * 1000L;
        cooldowns.put(player.getUniqueId(), expire);
    }
}
