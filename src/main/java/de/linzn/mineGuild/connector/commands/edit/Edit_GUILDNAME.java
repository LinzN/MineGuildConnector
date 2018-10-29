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
import de.linzn.mineGuild.connector.utils.PluginUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Edit_GUILDNAME implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public Edit_GUILDNAME(MineGuildConnectorPlugin plugin, String permission) {
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
            player.sendMessage(LanguageDB.COMMAND_USAGE.replace("{command}", "/guild edit name <Gildenname>"));
            return true;
        }

        String guildName = args[1];
        UUID actor = player.getUniqueId();

        if (!PluginUtil.is_valid_guild_name(guildName)) {
            sender.sendMessage(LanguageDB.INVALID_GUILD_NAME);
            return true;
        }

        JClientGuildEditOutput.set_guild_name(actor, guildName);


        return true;
    }
}
