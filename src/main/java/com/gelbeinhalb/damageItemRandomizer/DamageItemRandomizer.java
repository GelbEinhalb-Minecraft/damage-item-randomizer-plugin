package com.gelbeinhalb.damageItemRandomizer;

import com.gelbeinhalb.damageItemRandomizer.commands.CommandRandomizer;
import com.gelbeinhalb.damageItemRandomizer.listener.DamageListener;
import com.gelbeinhalb.damageItemRandomizer.listener.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DamageItemRandomizer extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("randomizer").setExecutor(new CommandRandomizer());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
