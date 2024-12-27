package com.gelbeinhalb.damageItemRandomizer.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RandomItem{

    public ItemStack get(){
        Random random = new Random();
        Material[] materials = Material.values();
        Material randomMaterial;
        do {
            randomMaterial = materials[random.nextInt(materials.length)];
        } while (!randomMaterial.isItem() || randomMaterial.getMaxStackSize() <= 1);
        int amount = 1 + random.nextInt(randomMaterial.getMaxStackSize());
        return new ItemStack(randomMaterial, amount);
    }

}
