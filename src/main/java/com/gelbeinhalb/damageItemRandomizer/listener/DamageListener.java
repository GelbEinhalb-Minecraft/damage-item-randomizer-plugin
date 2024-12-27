package com.gelbeinhalb.damageItemRandomizer.listener;

import com.gelbeinhalb.damageItemRandomizer.utils.RandomItem;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (!player.getLocation().getWorld().getName().equals("void_world")) {
            return;
        }

        int itemCount = getItemCount(event);
        for (int i = 0; i < itemCount; i++) {
            ItemStack randomItem = new RandomItem().get();
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(randomItem);
            } else {
                player.getWorld().dropItem(player.getLocation(), randomItem);
            }
        }
    }

    private static int getItemCount(EntityDamageEvent event) {
        int itemCount = (int) (event.getDamage() / 2);
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            if (damager instanceof Player attacker) {
                if (attacker.getInventory().getItemInMainHand().getType() == Material.AIR && attacker.getInventory().getItemInOffHand().getType() == Material.AIR) {
                    itemCount = 1;
                }
            }
        }
        return itemCount;
    }

}
