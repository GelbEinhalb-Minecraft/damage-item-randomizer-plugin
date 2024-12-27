package com.gelbeinhalb.damageItemRandomizer.commands;

import com.gelbeinhalb.damageItemRandomizer.world.VoidGenerator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandRandomizer implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /randomizer <start/restart/stop>");
            return false;
        }

        if (args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("restart")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld().getName().equals("void_world")) {
                    player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                }
            }
            Bukkit.unloadWorld("void_world", false);
            createWorld();
            sender.sendMessage("Empty world has been created!");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld().getName().equals("world")) {
                    player.teleport(Bukkit.getWorld("void_world").getSpawnLocation());
                }
            }
        } else if (args[0].equalsIgnoreCase("stop")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld().getName().equals("void_world")) {
                    player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                }
            }
        } else {
            sender.sendMessage("Usage: /randomizer <start/restart/stop>");
            return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> options = List.of("start", "restart", "stop");
            for (String option : options) {
                if (option.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(option);
                }
            }
        }
        return completions;
    }

    public void createWorld() {
        World world = Bukkit.getWorld("void_world");
        File worldFolder = new File(Bukkit.getWorldContainer(), "void_world");

        if (world != null) {
            Bukkit.unloadWorld("void_world", false);
            deleteWorldFolder(worldFolder);
        }

        WorldCreator worldCreator = new WorldCreator("void_world");
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generator(new VoidGenerator());
        world = Bukkit.createWorld(worldCreator);
        if (world == null) {
            return;
        }

        world.setSpawnLocation(0, 64, 0);
        for (int i=-1; i<2; i++){
            for (int j=-1; j<2; j++){
                Block block = world.getBlockAt(i, 62, j);
                block.setType(Material.BEDROCK);
            }
        }
    }

    private void deleteWorldFolder(File folder) {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                deleteWorldFolder(file);
            }
        }
        folder.delete();
    }
}
