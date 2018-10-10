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

package de.linzn.mineGuild.connector.commands.standalone;

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import de.linzn.mineSuite.chat.socket.JClientChatOutput;
import de.linzn.mineSuite.chat.utils.VaultAccess;
import de.linzn.mineSuite.core.configurations.YamlFiles.GeneralLanguage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GChat implements CommandExecutor {
    private MineGuildConnectorPlugin plugin;
    private String permission;
    private ThreadPoolExecutor cmdThread;


    public GChat(MineGuildConnectorPlugin plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
        this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageDB.NO_CONSOLE);
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission(permission)) {
            player.sendMessage(LanguageDB.NO_PERMISSIONS);
            return true;
        }
        cmdThread.submit(() -> {
            if (args.length == 0) {
                JClientChatOutput.channelSwitch(sender.getName(), "GUILD");
                sender.sendMessage(GeneralLanguage.chat_SWITCH.replace("{channel}", "GUILD"));
                return;
            }
            String text = "";
            for (String arg1 : args) {
                String arg = arg1 + " ";
                text = text + arg;
            }
            String prefix = VaultAccess.getPrefix(player).replace("&", "§");
            String suffix = VaultAccess.getSuffix(player).replace("&", "§");
            JClientChatOutput.channelChat(sender.getName(), text, prefix, suffix, "GUILD");
        });
        return true;
    }
}
