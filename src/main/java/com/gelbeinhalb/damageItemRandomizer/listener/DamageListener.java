package com.gelbeinhalb.damageItemRandomizer.listener;

import com.gelbeinhalb.damageItemRandomizer.utils.RandomItem;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        int itemCount = (int) (event.getDamage() / 2);
        Player player = (Player) event.getEntity();
        for (int i = 0; i < itemCount; i++) {
            ItemStack randomItem = new RandomItem().get();
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(randomItem);
            } else {
                player.getWorld().dropItem(player.getLocation(), randomItem);
            }
        }
    }

}
