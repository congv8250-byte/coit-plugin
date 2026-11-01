package vn.mcfk.coit;

import org.bukkit.Material;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Weapons {
    THUNDER("thunder", "Loi Kiem", Material.NETHERITE_SWORD, SkillType.LIGHTNING,
            "Danh trung co co hoi trieu set danh muc tieu",
            ingr(Material.NETHERITE_SWORD, 1, Material.NETHER_STAR, 1, Material.LIGHTNING_ROD, 2, Material.DIAMOND_BLOCK, 2, Material.COPPER_BLOCK, 2)),
    ICE("ice", "Bang Kiem", Material.NETHERITE_SWORD, SkillType.FREEZE,
            "Danh trung lam cham va gay ngat quang muc tieu",
            ingr(Material.NETHERITE_SWORD, 1, Material.BLUE_ICE, 3, Material.PACKED_ICE, 3, Material.DIAMOND_BLOCK, 2)),
    BLOOD("blood", "Huyet Kiem", Material.NETHERITE_SWORD, SkillType.LIFESTEAL,
            "Danh trung hut mau hoi lai cho nguoi dung",
            ingr(Material.NETHERITE_SWORD, 1, Material.TOTEM_OF_UNDYING, 1, Material.REDSTONE_BLOCK, 3, Material.NETHER_WART_BLOCK, 3)),
    BLAST("blast", "Cuong Phong Riu", Material.NETHERITE_AXE, SkillType.EXPLOSION,
            "Danh trung gay sat thuong lan sang xung quanh",
            ingr(Material.NETHERITE_AXE, 1, Material.TNT, 2, Material.NETHER_STAR, 1, Material.ANCIENT_DEBRIS, 3, Material.BLAZE_POWDER, 2)),
    WIND("wind", "Phong Linh Kiem", Material.NETHERITE_SWORD, SkillType.PULL,
            "Danh trung keo muc tieu ve phia nguoi dung",
            ingr(Material.NETHERITE_SWORD, 1, Material.FEATHER, 4, Material.PHANTOM_MEMBRANE, 3, Material.DIAMOND_BLOCK, 1)),
    SHADOW("shadow", "Anh Kiem", Material.NETHERITE_SWORD, SkillType.INVISIBILITY,
            "Chuot phai de an than trong giay lat",
            ingr(Material.NETHERITE_SWORD, 1, Material.ENDER_EYE, 3, Material.OBSIDIAN, 3, Material.SHULKER_SHELL, 2)),
    RAGE("rage", "Thinh No Riu", Material.NETHERITE_AXE, SkillType.STUN,
            "Danh trung lam muc tieu choang vang",
            ingr(Material.NETHERITE_AXE, 1, Material.WITHER_SKELETON_SKULL, 1, Material.GOLD_BLOCK, 3, Material.SOUL_SAND, 4)),
    GUARD("guard", "Ho Ve Kiem", Material.NETHERITE_SWORD, SkillType.SHIELD,
            "Chuot phai de nhan giap hap thu tam thoi",
            ingr(Material.NETHERITE_SWORD, 1, Material.SHIELD, 1, Material.DIAMOND_BLOCK, 3, Material.AMETHYST_BLOCK, 4)),
    HASTE("haste", "Toc Phong Cung", Material.BOW, SkillType.SPEED,
            "Chuot phai de tang toc do di chuyen",
            ingr(Material.BOW, 1, Material.SUGAR, 4, Material.RABBIT_FOOT, 3, Material.DIAMOND_BLOCK, 1)),
    ETERNAL("eternal", "Bat Diet Dinh Ba", Material.TRIDENT, SkillType.UNBREAKABLE,
            "Khong bao gio hong theo thoi gian",
            ingr(Material.TRIDENT, 1, Material.NETHERITE_INGOT, 4, Material.ANCIENT_DEBRIS, 2, Material.DIAMOND_BLOCK, 2));

    public final String id;
    public final String title;
    public final Material base;
    public final SkillType skill;
    public final String lore;
    public final Map<Material, Integer> ingredients;

    Weapons(String id, String title, Material base, SkillType skill, String lore, Map<Material, Integer> ingredients) {
        this.id = id;
        this.title = title;
        this.base = base;
        this.skill = skill;
        this.lore = lore;
        this.ingredients = ingredients;
    }

    public static Weapons byId(String id) {
        for (Weapons w : values()) if (w.id.equalsIgnoreCase(id)) return w;
        return null;
    }

    private static Map<Material, Integer> ingr(Object... kv) {
        Map<Material, Integer> m = new LinkedHashMap<>();
        for (int i = 0; i < kv.length; i += 2) m.put((Material) kv[i], (Integer) kv[i + 1]);
        return m;
    }
}
