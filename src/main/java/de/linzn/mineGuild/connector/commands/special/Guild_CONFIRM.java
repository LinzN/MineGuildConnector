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

package de.linzn.mineGuild.connector.commands.special;

import de.linzn.mineGuild.connector.GuildDatabase;
import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.commands.ICommand;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import de.linzn.mineGuild.connector.socket.controlStream.JClientGuildControlOutput;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Guild_CONFIRM implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public Guild_CONFIRM(MineGuildConnectorPlugin plugin, String permission) {
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
        UUID actor = player.getUniqueId();
        GuildPlayer guildPlayer = GuildDatabase.getGuildPlayer(player.getUniqueId());
        if (guildPlayer == null) {
            return true;
        }
        if (!GuildDatabase.guildActionRequests.containsKey(guildPlayer.getUUID())) {
            return true;
        }

        GuildDatabase.guildActionRequests.remove(guildPlayer.getUUID());
        JClientGuildControlOutput.send_guild_action_confirm(actor, guildPlayer.getGuild().guildUUID);
        return true;
    }
}
