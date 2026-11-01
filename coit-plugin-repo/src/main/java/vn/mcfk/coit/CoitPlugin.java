package vn.mcfk.coit;

import org.bukkit.plugin.java.JavaPlugin;

public final class CoitPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        ItemUtil util = new ItemUtil(this);
        CraftListener.registerAll(this, util);
        getServer().getPluginManager().registerEvents(new CombatListener(util), this);
        getServer().getPluginManager().registerEvents(new InteractListener(util), this);
        GuideCommand guide = new GuideCommand(util);
        getServer().getPluginManager().registerEvents(guide, this);
        RecipeCommand recipe = new RecipeCommand();
        getServer().getPluginManager().registerEvents(recipe, this);
        getCommand("coit").setExecutor(new CoitCommand());
        getCommand("guide").setExecutor(guide);
        getCommand("coitrecipe").setExecutor(recipe);
        getCommand("coitupgrade").setExecutor(new UpgradeCommand(util));
    }
}
