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

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.socket.commandStream.JClientGuildCommandOutput;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Guild_MEMBERS implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public Guild_MEMBERS(MineGuildConnectorPlugin plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
    }

    @Override
    public boolean runCmd(Command cmd, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageDB.NO_CONSOLE);
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission(permission)) {
            player.sendMessage(LanguageDB.NO_PERMISSIONS);
            return true;
        }
        String guildArg = "null";
        int page = 1;
        if (args.length > 1) {
            guildArg = args[1];
            if (args.length > 2) {
                try {
                    page = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    player.sendMessage(LanguageDB.NOT_A_NUMBER);
                    return true;
                }
            }
        }

        UUID actor = player.getUniqueId();
        JClientGuildCommandOutput.members_guild(actor, guildArg, page);
        return true;
    }
}
