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

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.commands.ICommand;
import de.linzn.mineGuild.connector.socket.editStream.JClientGuildEditOutput;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Edit_GUILDHOME implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public Edit_GUILDHOME(MineGuildConnectorPlugin plugin, String permission) {
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

        if (args.length < 1) {
            player.sendMessage(LanguageDB.COMMAND_USAGE.replace("{command}", "/guild edit home"));
            return true;
        }


        UUID actor = player.getUniqueId();
        Location location = player.getLocation();

        JClientGuildEditOutput.set_guild_home(actor, location);


        return true;
    }
}
