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

package de.linzn.mineGuild.connector.commands;

import com.google.common.collect.Maps;
import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.commands.edit.GuildEditCommand;
import de.linzn.mineGuild.connector.commands.rang.GuildRangCommand;
import de.linzn.mineGuild.connector.commands.special.Guild_DEPOSIT;
import de.linzn.mineGuild.connector.commands.special.Guild_WITHDRAW;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GuildCommand implements CommandExecutor {

    private ThreadPoolExecutor cmdThread;
    private MineGuildConnectorPlugin plugin;
    private boolean isLoaded = false;
    private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

    public GuildCommand(MineGuildConnectorPlugin plugin) {
        this.plugin = plugin;
        this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, String label, final String[] args) {
        cmdThread.submit(() -> {
            if (args.length == 0) {
                getCmdMap().get("help").runCmd(sender, args);
            } else if (getCmdMap().containsKey(args[0])) {
                String command = args[0];
                if (!getCmdMap().get(command).runCmd(sender, args)) {
                    sender.sendMessage(LanguageDB.NO_COMMAND.replace("{command}", "/guild help"));
                }

            } else {
                sender.sendMessage(LanguageDB.NO_COMMAND.replace("{command}", "/guild help"));
            }

        });
        return true;
    }

    private TreeMap<String, ICommand> getCmdMap() {
        return cmdMap;
    }

    public void loadCmd() {
        try {
            this.cmdMap.put("help", new Guild_HELP(this.plugin, "mineguild.help"));
            this.cmdMap.put("create", new Guild_CREATE(this.plugin, "mineguild.create"));
            this.cmdMap.put("disband", new Guild_DISBAND(this.plugin, "mineguild.disband"));
            this.cmdMap.put("info", new Guild_INFO(this.plugin, "mineguild.info"));
            this.cmdMap.put("members", new Guild_MEMBERS(this.plugin, "mineguild.members"));
            this.cmdMap.put("accept", new Guild_ACCEPT(this.plugin, "mineguild.accept"));
            this.cmdMap.put("deny", new Guild_DENY(this.plugin, "mineguild.deny"));
            this.cmdMap.put("invite", new Guild_INVITE(this.plugin, "mineguild.invite"));
            this.cmdMap.put("leave", new Guild_LEAVE(this.plugin, "mineguild.leave"));
            this.cmdMap.put("kick", new Guild_KICK(this.plugin, "mineguild.kick"));
            this.cmdMap.put("list", new Guild_LIST(this.plugin, "mineguild.list"));
            this.cmdMap.put("home", new Guild_HOME(this.plugin, "mineguild.home"));
            this.cmdMap.put("deposit", new Guild_DEPOSIT(this.plugin, "mineguild.deposit"));
            this.cmdMap.put("withdraw", new Guild_WITHDRAW(this.plugin, "mineguild.withdraw"));


            this.cmdMap.put("edit", new GuildEditCommand(this.plugin));
            this.cmdMap.put("rang", new GuildRangCommand(this.plugin));

            this.isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }
}
