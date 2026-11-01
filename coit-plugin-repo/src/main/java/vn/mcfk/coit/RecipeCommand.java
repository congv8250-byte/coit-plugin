package vn.mcfk.coit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class RecipeCommand implements CommandExecutor, Listener {
    private static final String TITLE = ChatColor.DARK_GREEN + "Coit - Cong thuc";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        Inventory inv = Bukkit.createInventory(null, 27, TITLE);
        int slot = 10;
        for (Weapons w : Weapons.values()) {
            ItemStack icon = new ItemStack(w.base);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + w.title);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + w.lore);
            lore.add(ChatColor.DARK_GRAY + "Nguyen lieu che tao:");
            for (var e : w.ingredients.entrySet()) {
                lore.add(ChatColor.YELLOW + "- " + e.getKey().name() + " x" + e.getValue());
            }
            lore.add(ChatColor.DARK_GRAY + "Nang cap: /coitupgrade");
            meta.setLore(lore);
            icon.setItemMeta(meta);
            inv.setItem(slot, icon);
            slot++;
        }
        player.openInventory(inv);
        return true;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!TITLE.equals(event.getView().getTitle())) return;
        event.setCancelled(true);
    }
}
