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

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.commands.ICommand;
import de.linzn.mineGuild.connector.socket.rangStream.JClientGuildRangOutput;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RANG_SHOW implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public RANG_SHOW(MineGuildConnectorPlugin plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
    }

    @Override
    public boolean runCmd(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageDB.NO_CONSOLE);
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission(permission)) {
            player.sendMessage(LanguageDB.NO_PERMISSIONS);
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(LanguageDB.COMMAND_USAGE.replace("{command}", "/guild rang show [Spielername]"));
            return true;
        }

        String playerName = args[1];
        UUID actor = player.getUniqueId();
        JClientGuildRangOutput.show_player_rang(actor, playerName);
        return true;
    }
}
