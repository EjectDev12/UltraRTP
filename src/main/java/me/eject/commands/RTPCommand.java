package me.eject.commands;

import me.eject.UltraRTP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may use this.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("ultrartp.use")) {
            player.sendMessage("§cNo permission.");
            return true;
        }
        UltraRTP.getInstance().getGUIManager().open(player);
        return true;
    }
}