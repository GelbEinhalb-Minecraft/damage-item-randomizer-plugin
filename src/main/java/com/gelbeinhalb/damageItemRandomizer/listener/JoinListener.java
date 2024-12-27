package com.gelbeinhalb.damageItemRandomizer.listener;

import com.gelbeinhalb.damageItemRandomizer.utils.WitherBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("void_world")) {
            WitherBar.bossBar.addPlayer(player);
        }
    }

}