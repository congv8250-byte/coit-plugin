package vn.mcfk.coit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public final class UpgradeCommand implements CommandExecutor {
    private final ItemUtil util;

    public UpgradeCommand(ItemUtil util) {
        this.util = util;
    }

    private Map<Material, Integer> costFor(int level) {
        Map<Material, Integer> cost = new LinkedHashMap<>();
        if (level == 1) {
            cost.put(Material.NETHERITE_INGOT, 4);
            cost.put(Material.DIAMOND_BLOCK, 2);
        } else if (level == 2) {
            cost.put(Material.NETHERITE_INGOT, 8);
            cost.put(Material.DIAMOND_BLOCK, 4);
            cost.put(Material.NETHER_STAR, 1);
        }
        return cost;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        ItemStack held = player.getInventory().getItemInMainHand();
        Weapons w = util.weaponOf(held);
        if (w == null) {
            player.sendMessage(ChatColor.RED + "Ban can cam vu khi Coit tren tay.");
            return true;
        }
        int level = util.levelOf(held);
        if (level >= 3) {
            player.sendMessage(ChatColor.YELLOW + "Vu khi nay da dat cap toi da.");
            return true;
        }
        Map<Material, Integer> cost = costFor(level);
        for (var e : cost.entrySet()) {
            if (!player.getInventory().containsAtLeast(new ItemStack(e.getKey()), e.getValue())) {
                player.sendMessage(ChatColor.RED + "Thieu " + e.getValue() + " " + e.getKey().name());
                return true;
            }
        }
        for (var e : cost.entrySet()) {
            player.getInventory().removeItem(new ItemStack(e.getKey(), e.getValue()));
        }
        ItemStack upgraded = util.create(w, level + 1);
        player.getInventory().setItemInMainHand(upgraded);
        player.sendMessage(ChatColor.GREEN + "Da nang cap len cap " + (level + 1) + "!");
        return true;
    }
}
