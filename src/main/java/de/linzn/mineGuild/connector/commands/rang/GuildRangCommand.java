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

package de.linzn.mineGuild.connector.commands.rang;

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

public class GuildRangCommand implements ICommand {

    private ThreadPoolExecutor cmdThread;
    private MineGuildConnectorPlugin plugin;
    private boolean isLoaded = false;
    private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

    public GuildRangCommand(MineGuildConnectorPlugin plugin) {
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
                    sender.sendMessage(LanguageDB.NO_COMMAND.replace("{command}", "/guild rang help"));
                }

            } else {
                sender.sendMessage(LanguageDB.NO_COMMAND.replace("{command}", "/guild rang help"));
            }

        });
        return true;
    }

    private TreeMap<String, ICommand> getCmdMap() {
        return cmdMap;
    }

    public void loadCmd() {
        try {
            this.cmdMap.put("help", new RANG_HELP(this.plugin, "mineguild.rang.help"));
            this.cmdMap.put("setplayer", new RANG_SETPLAYER(this.plugin, "mineguild.rang.setplayer"));
            this.cmdMap.put("info", new RANG_INFO(this.plugin, "mineguild.rang.info"));
            this.cmdMap.put("show", new RANG_SHOW(this.plugin, "mineguild.rang.show"));
            this.cmdMap.put("list", new RANG_LIST(this.plugin, "mineguild.rang.list"));

            this.isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }
}
