/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.mineGuild.connector.commands.edit;

import com.google.common.collect.Maps;
import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.commands.ICommand;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GuildEditCommand implements ICommand {

    private ThreadPoolExecutor cmdThread;
    private MineGuildConnectorPlugin plugin;
    private boolean isLoaded = false;
    private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

    public GuildEditCommand(MineGuildConnectorPlugin plugin) {
        this.plugin = plugin;
        this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        this.loadCmd();
    }

    @Override
    public boolean runCmd(CommandSender sender, String[] args_old) {
        String[] args = Arrays.copyOfRange(args_old, 1, args_old.length);
        cmdThread.submit(() -> {
            if (args.length == 0) {
                getCmdMap().get("help").runCmd(sender, args);
            } else if (getCmdMap().containsKey(args[0])) {
                String command = args[0];
                if (!getCmdMap().get(command).runCmd(sender, args)) {
                    sender.sendMessage(LanguageDB.NO_COMMAND.replace("{command}", "/guild edit help"));
                }

            } else {
                sender.sendMessage(LanguageDB.NO_COMMAND.replace("{command}", "/guild edit help"));
            }

        });
        return true;
    }

    private TreeMap<String, ICommand> getCmdMap() {
        return cmdMap;
    }

    private void loadCmd() {
        try {
            this.cmdMap.put("help", new EDIT_HELP(this.plugin, "mineguild.edit.help"));
            this.cmdMap.put("guildname", new Edit_GUILDNAME(this.plugin, "mineguild.edit.guildname"));
            this.cmdMap.put("guildmaster", new Edit_GUILDMASTER(this.plugin, "mineguild.edit.guildmaster"));
            this.cmdMap.put("guildhome", new Edit_GUILDHOME(this.plugin, "mineguild.edit.guildhome"));
            this.isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }
}
