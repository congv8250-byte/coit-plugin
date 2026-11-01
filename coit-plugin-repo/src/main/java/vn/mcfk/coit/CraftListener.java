package vn.mcfk.coit;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public final class CraftListener {
    public static void registerAll(CoitPlugin plugin, ItemUtil util) {
        for (Weapons w : Weapons.values()) {
            NamespacedKey key = new NamespacedKey((Plugin) plugin, "coit_recipe_" + w.id);
            ItemStack result = util.create(w, 1);
            ShapelessRecipe recipe = new ShapelessRecipe(key, result);
            for (var e : w.ingredients.entrySet()) {
                Material mat = e.getKey();
                int count = e.getValue();
                recipe.addIngredient(count, mat);
            }
            plugin.getServer().addRecipe(recipe);
        }
    }
}
