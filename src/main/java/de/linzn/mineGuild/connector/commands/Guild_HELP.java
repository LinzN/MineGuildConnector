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
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Guild_HELP implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public Guild_HELP(MineGuildConnectorPlugin plugin, String permission) {
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
                player.sendMessage(("§6§lAllgemeine Hilfe: "));
                player.sendMessage(" §2Gilde erstellen: §e/guild create <Gildenname>");
                player.sendMessage(" §2Gilde auflösen: §e/guild disband");
                player.sendMessage(" §2Mitglied einladen: §e/guild invite <Spielername>");
                player.sendMessage(" §2Mitglied entfernen: §e/guild kick <Spielername>");
                player.sendMessage(" §2Alle Gilden auflisten: §e/guild list <Seite>");
                return true;
            } else if (args[1].equalsIgnoreCase("3")) {
                player.sendMessage("§6§lAllgemeine Hilfe: ");
                player.sendMessage(" §2Gildeneinladung annehmen: §e/guild accept");
                player.sendMessage(" §2Gildeneinladung ablehnen: §e/guild deny");
                player.sendMessage(" §2Zum Gildenhome teleportieren: §e/guild home");
                player.sendMessage(" §2Mines auf Gildenkonto einzahlen: §e/guild deposit <Wert>");
                player.sendMessage(" §2Mines von Gildenkonto abheben: §e/guild withdraw <Wert>");
                return true;
            }
        }

        player.sendMessage("§e§n§6§l-===============[§2§lMineGuild§r§6§l]===============-");
        player.sendMessage("§2 Gildeninformationen: §e/guild info <Gilde>");
        player.sendMessage("§2 Gildenmitglieder anzeigen: §e/guild members <Gilde> <Seite>");
        player.sendMessage("§6§lÜbersicht der Gilden Hilfebereiche:");
        player.sendMessage(" §2Allgemeine Hilfe §a/guild help 1 - 3");
        player.sendMessage(" §2Gildeneinstellungen Hilfe §a/guild edit help");
        player.sendMessage(" §2Ränge Hilfe §4/guild rang help");
        return true;

    }
}
