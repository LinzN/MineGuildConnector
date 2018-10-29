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
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EDIT_HELP implements ICommand {
    private MineGuildConnectorPlugin plugin;
    private String permission;


    public EDIT_HELP(MineGuildConnectorPlugin plugin, String permission) {
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
                player.sendMessage(("§6§lGuild Edit: "));
                player.sendMessage(" §2Gildennamen ändern: §e/guild edit name <Gildenname>");
                player.sendMessage(" §2Gildenhome ändern: §e/guild edit home");
                player.sendMessage(" §2Gildenmeister ändern: §e/guild edit master <Spielername>");
                return true;
            }
        }
        player.sendMessage("§e§n§6§l-============[§2§lMineGuild Edit§r§6§l]============-");
        player.sendMessage("§2 Edit Infos: §e/guild edit help");
        player.sendMessage("§6§lÜbersicht der Gilden-Edit Hilfebereiche:");
        player.sendMessage(" §2Allgemeine Edithilfe §a/guild edit help 1 - 2");
        return true;


    }
}
