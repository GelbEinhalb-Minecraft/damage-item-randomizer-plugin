package com.gelbeinhalb.damageItemRandomizer.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WitherBar {

    public BossBar bossBar;
    private BukkitRunnable timerTask;

    public void run(World world) {
        bossBar = Bukkit.createBossBar("Wither spawning in 1 hour", BarColor.RED, BarStyle.SOLID);
        bossBar.setProgress(1.0);

        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }

        timerTask = new BukkitRunnable() {
            int timeLeft = 60; // 1 hour in seconds

            @Override
            public void run() {
                if (timeLeft <= 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("The wither has been spawned!");
                    }
                    bossBar.removeAll();
                    world.spawnEntity(world.getSpawnLocation(), EntityType.WITHER);
                    cancel();
                    return;
                }

                timeLeft--;
                double progress = (double) timeLeft / 3600;
                bossBar.setProgress(progress);

                int minutes = timeLeft / 60;
                int seconds = timeLeft % 60;
                bossBar.setTitle("Wither spawning in " + minutes + "m " + seconds + "s");
            }
        };
        timerTask.runTaskTimer(Bukkit.getPluginManager().getPlugin("damage-item-randomizer"), 20, 20);
    }

    public void stop() {
        if (bossBar != null) {
            bossBar.removeAll();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

}
