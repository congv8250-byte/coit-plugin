package vn.mcfk.coit;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public final class CombatListener implements Listener {
    private final ItemUtil util;
    private final Random rng = new Random();

    public CombatListener(ItemUtil util) {
        this.util = util;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof LivingEntity target)) return;
        ItemStack held = player.getInventory().getItemInMainHand();
        Weapons w = util.weaponOf(held);
        if (w == null) return;
        int level = util.levelOf(held);
        switch (w.skill) {
            case LIGHTNING -> {
                if (rng.nextInt(100) < 15 + level * 5) {
                    target.getWorld().strikeLightningEffect(target.getLocation());
                    target.damage(2 + level, player);
                }
            }
            case FREEZE -> {
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60 + level * 20, 2));
                target.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 60 + level * 20, 1));
            }
            case LIFESTEAL -> {
                double heal = event.getFinalDamage() * (0.2 + level * 0.1);
                double newHealth = Math.min(player.getMaxHealth(), player.getHealth() + heal);
                player.setHealth(newHealth);
            }
            case EXPLOSION -> {
                for (org.bukkit.entity.Entity nearby : target.getNearbyEntities(3, 3, 3)) {
                    if (nearby instanceof LivingEntity le && nearby != player) {
                        le.damage(3 + level, player);
                    }
                }
                target.getWorld().spawnParticle(org.bukkit.Particle.EXPLOSION, target.getLocation(), 1);
            }
            case PULL -> {
                Location from = target.getLocation();
                Location to = player.getLocation();
                Vector dir = to.toVector().subtract(from.toVector()).normalize().multiply(0.6 + level * 0.15);
                dir.setY(0.25);
                target.setVelocity(dir);
            }
            case STUN -> {
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40 + level * 10, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60 + level * 10, 4));
            }
            default -> {}
        }
    }
}
