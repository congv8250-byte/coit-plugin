package vn.mcfk.coit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public final class ItemUtil {
    private final CoitPlugin plugin;
    public final NamespacedKey keyId;
    public final NamespacedKey keyLevel;
    public final NamespacedKey keyDamage;

    public ItemUtil(CoitPlugin plugin) {
        this.plugin = plugin;
        this.keyId = new NamespacedKey(plugin, "coit_weapon_id");
        this.keyLevel = new NamespacedKey(plugin, "coit_weapon_level");
        this.keyDamage = new NamespacedKey(plugin, "coit_damage_mod");
    }

    public ItemStack create(Weapons w, int level) {
        ItemStack item = new ItemStack(w.base);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + w.title + ChatColor.GRAY + " +" + level);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + w.lore);
        lore.add(ChatColor.DARK_GRAY + "Cap do: " + level + "/3");
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(keyId, PersistentDataType.STRING, w.id);
        meta.getPersistentDataContainer().set(keyLevel, PersistentDataType.INTEGER, level);
        meta.addEnchant(org.bukkit.enchantments.Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        AttributeModifier mod = new AttributeModifier(keyDamage, level * 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, mod);
        if (w.skill == SkillType.UNBREAKABLE) meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isCoit(ItemStack item) {
        return item != null && item.hasItemMeta()
                && item.getItemMeta().getPersistentDataContainer().has(keyId, PersistentDataType.STRING);
    }

    public Weapons weaponOf(ItemStack item) {
        if (!isCoit(item)) return null;
        String id = item.getItemMeta().getPersistentDataContainer().get(keyId, PersistentDataType.STRING);
        return Weapons.byId(id);
    }

    public int levelOf(ItemStack item) {
        if (!isCoit(item)) return 0;
        Integer lv = item.getItemMeta().getPersistentDataContainer().get(keyLevel, PersistentDataType.INTEGER);
        return lv == null ? 1 : lv;
    }
}
