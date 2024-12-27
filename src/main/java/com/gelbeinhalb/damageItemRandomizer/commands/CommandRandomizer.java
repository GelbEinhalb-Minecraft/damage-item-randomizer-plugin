package com.gelbeinhalb.damageItemRandomizer.commands;

import com.gelbeinhalb.damageItemRandomizer.utils.WitherBar;
import com.gelbeinhalb.damageItemRandomizer.world.VoidWorld;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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

        VoidWorld voidworld = new VoidWorld();
        WitherBar witherbar = new WitherBar();

        if (args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("restart")) {
            witherbar.stop();
            voidworld.moveVoidToSpawn();
            voidworld.create();
            voidworld.moveSpawnToVoid();
            for (Player player : Bukkit.getOnlinePlayers()) {
                voidworld.resetPlayer(player);
            }
            witherbar.run(Bukkit.getWorld("void_world"));
        }

        else if (args[0].equalsIgnoreCase("stop")) {
            witherbar.stop();
            voidworld.moveVoidToSpawn();
            voidworld.delete(new java.io.File(Bukkit.getWorldContainer(), "void_world"));
        }

        else {
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

}
