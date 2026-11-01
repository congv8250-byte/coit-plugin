package vn.mcfk.coit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class CoitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        ItemStack held = player.getInventory().getItemInMainHand();
        if (held == null || held.getType().isAir()) return true;
        ItemStack copy = held.clone();
        var leftover = player.getInventory().addItem(copy);
        if (!leftover.isEmpty()) {
            for (ItemStack item : leftover.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
        return true;
    }
}
