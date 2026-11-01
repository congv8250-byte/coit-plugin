package vn.mcfk.coit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class GuideCommand implements CommandExecutor, Listener {
    private final ItemUtil util;
    private static final String TITLE = ChatColor.DARK_AQUA + "Coit - Admin Guide";

    public GuideCommand(ItemUtil util) {
        this.util = util;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("coit")) return false;
        if (!(sender instanceof Player player)) return true;
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "Chi admin moi dung duoc lenh nay.");
            return true;
        }
        Inventory inv = Bukkit.createInventory(null, 27, TITLE);
        int slot = 10;
        for (Weapons w : Weapons.values()) {
            inv.setItem(slot, util.create(w, 1));
            slot++;
        }
        player.openInventory(inv);
        return true;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!TITLE.equals(event.getView().getTitle())) return;
        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;
        Weapons w = util.weaponOf(clicked);
        if (w == null) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack give = util.create(w, 1);
        var leftover = player.getInventory().addItem(give);
        if (!leftover.isEmpty()) {
            for (ItemStack item : leftover.values()) player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }
}
