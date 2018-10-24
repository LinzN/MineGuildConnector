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
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RANG_HELP implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public RANG_HELP(MineGuildConnectorPlugin plugin, String permission) {
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
        if (args.length >= 2) {

            if (args[1].equalsIgnoreCase("2")) {
                player.sendMessage(("§6§lAllgemeine Ranghilfe: "));
                player.sendMessage("§2 Mitgliederrang anzeigen: §e/guild rang show [Spieler]");
                player.sendMessage("§2 Rang zuweisen: §e/guild rang setplayer [Spieler] [Rang]");
                return true;
            }
        }
        player.sendMessage("§e§n§6§l-============[§2§lMineGuild Rangs§r§6§l]============-");
        player.sendMessage("§2 Rang Info: §e/guild rang info [Rang]");
        player.sendMessage("§2 Alle Ränge auflisten: §e/guild rang list <Seite>");
        player.sendMessage("§6§lÜbersicht der Rang Hilfebereiche:");
        player.sendMessage(" §2Allgemeine Ranghilfe §a/guild rang help 1 - 2");
        return true;


    }
}
