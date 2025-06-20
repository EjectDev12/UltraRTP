package me.eject;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TeleportTask extends BukkitRunnable {
    private final Player player;
    private final String worldKey;
    private int secondsLeft;
    private final ConfigManager cfg;
    private final Random rand = new Random();
    private final Location startLoc;
    private static final int MAX_ATTEMPTS = 100;

    public TeleportTask(Player player, String worldKey) {
        this.player = player;
        this.worldKey = worldKey;
        this.cfg = UltraRTP.getInstance().getConfigManager();
        this.secondsLeft = cfg.getDelay();
        this.startLoc = player.getLocation().clone();
    }

    @Override
    public void run() {
        if (secondsLeft > 0) {
            if (!isStillAtStart()) {
                player.sendMessage("§cTeleport canceled due to movement.");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                cancel();
                return;
            }
            player.sendActionBar("§aTeleporting in §b" + secondsLeft + "§a...");
            secondsLeft--;
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                World world = getWorldByKey(worldKey);
                Location safeLoc = findSafeLocation(world, worldKey);
                if (safeLoc == null) {
                    safeLoc = world.getSpawnLocation();
                    player.sendMessage("§cCould not find a safe location, teleporting to world spawn.");
                }

                player.teleport(safeLoc);
                player.playSound(safeLoc, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                cancel();
            }
        }.runTask(UltraRTP.getInstance());
    }

    private World getWorldByKey(String key) {
        switch (key) {
            case "nether":
                return Bukkit.getWorld("world_nether");
            case "the_end":
                return Bukkit.getWorld("world_the_end");
            default:
                return Bukkit.getWorld("world");
        }
    }

    private Location findSafeLocation(World world, String key) {
        int radius = cfg.getRadius(key);
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int x = rand.nextInt(radius * 2 + 1) - radius;
            int z = rand.nextInt(radius * 2 + 1) - radius;
            Location loc;
            if (world.getEnvironment() == World.Environment.NETHER) {
                loc = findNetherLocation(world, x, z);
            } else {
                loc = findOverworldLocation(world, x, z);
            }
            if (loc != null) {
                loc.add(0.5, 0, 0.5);
                return loc;
            }
        }
        return null;
    }

    private Location findOverworldLocation(World world, int x, int z) {
        int y = world.getHighestBlockYAt(x, z) + 1;
        if (y <= 1) return null;
        Material below = world.getBlockAt(x, y - 1, z).getType();
        if (below == Material.WATER || below == Material.LAVA) return null;
        return new Location(world, x, y, z);
    }

    private Location findNetherLocation(World world, int x, int z) {
        final int roofLimit = 120;
        for (int y = roofLimit - 1; y > 1; y--) {
            Material blockBelow = world.getBlockAt(x, y - 1, z).getType();
            if (!blockBelow.isSolid()) continue;
            if (world.getBlockAt(x, y, z).getType() != Material.AIR) continue;
            return new Location(world, x, y, z);
        }
        return null;
    }

    private boolean isStillAtStart() {
        Location loc = player.getLocation();
        return loc.getWorld().equals(startLoc.getWorld()) &&
                loc.getBlockX() == startLoc.getBlockX() &&
                loc.getBlockY() == startLoc.getBlockY() &&
                loc.getBlockZ() == startLoc.getBlockZ();
    }
}