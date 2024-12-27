package com.gelbeinhalb.damageItemRandomizer.world;

import com.gelbeinhalb.damageItemRandomizer.utils.WitherBar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;

public class VoidWorld {

    public void create() {
        World world = Bukkit.getWorld("void_world");
        File worldFolder = new File(Bukkit.getWorldContainer(), "void_world");

        if (world != null) {
            new WitherBar().stop();
            moveVoidToSpawn();
            Bukkit.unloadWorld("void_world", false);
            delete(worldFolder);
        }

        WorldCreator worldCreator = new WorldCreator("void_world");
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generator(new VoidGenerator());
        world = Bukkit.createWorld(worldCreator);
        buildSpawn(world);
    }

    public void buildSpawn(World world){
        world.setSpawnLocation(0, 64, 0);
        for (int i=-1; i<2; i++){
            for (int j=-1; j<2; j++){
                Block block = world.getBlockAt(i, 62, j);
                block.setType(Material.BEDROCK);
            }
        }
    }

    public void delete(File folder) {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                delete(file);
            }
        }
        folder.delete();
    }

    public void moveVoidToSpawn(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getName().equals("void_world")) {
                player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            }
        }
    }

    public void moveSpawnToVoid(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getName().equals("world")) {
                player.teleport(Bukkit.getWorld("void_world").getSpawnLocation());
            }
        }
    }
}
