package vn.mcfk.coit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class InteractListener implements Listener {
    private final ItemUtil util;
    private final Map<UUID, Long> cooldown = new HashMap<>();

    public InteractListener(ItemUtil util) {
        this.util = util;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        Player player = event.getPlayer();
        ItemStack held = player.getInventory().getItemInMainHand();
        Weapons w = util.weaponOf(held);
        if (w == null) return;
        long now = System.currentTimeMillis();
        long last = cooldown.getOrDefault(player.getUniqueId(), 0L);
        if (now - last < 15000) return;
        int level = util.levelOf(held);
        boolean used = true;
        switch (w.skill) {
            case INVISIBILITY -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60 + level * 20, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 + level * 20, 0));
            }
            case SHIELD -> player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200 + level * 60, level));
            case SPEED -> player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100 + level * 40, 1 + level));
            default -> used = false;
        }
        if (used) cooldown.put(player.getUniqueId(), now);
    }
}
