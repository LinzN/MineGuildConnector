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

package de.linzn.mineGuild.connector;

import de.linzn.jSocket.client.JClientConnection;
import de.linzn.mineGuild.connector.commands.GuildCommand;
import de.linzn.mineGuild.connector.listener.JobsRebornListener;
import de.linzn.mineGuild.connector.listener.McmmoListener;
import de.linzn.mineGuild.connector.socket.checkStream.JClientGuildCheckListener;
import de.linzn.mineGuild.connector.socket.checkStream.JClientGuildCheckOutput;
import de.linzn.mineGuild.connector.socket.commandStream.JClientGuildCommandListener;
import de.linzn.mineGuild.connector.socket.commandStream.JClientGuildCommandOutput;
import de.linzn.mineGuild.connector.socket.controlStream.JClientGuildControlListener;
import de.linzn.mineGuild.connector.socket.controlStream.JClientGuildControlOutput;
import de.linzn.mineGuild.connector.socket.editStream.JClientGuildEditListener;
import de.linzn.mineGuild.connector.socket.editStream.JClientGuildEditOutput;
import de.linzn.mineGuild.connector.socket.rangStream.JClientGuildRangListener;
import de.linzn.mineGuild.connector.socket.rangStream.JClientGuildRangOutput;
import de.linzn.mineGuild.connector.socket.updateStream.JClientGuildUpdateListener;
import de.linzn.mineGuild.connector.socket.updateStream.JClientGuildUpdateOutput;
import de.linzn.mineGuild.connector.utils.GuildUpdater;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MineGuildConnectorPlugin extends JavaPlugin {
    private static MineGuildConnectorPlugin inst;
    private Economy econ = null;
    private Chat chat = null;
    private GuildUpdater guildUpdater = null;

    public static Economy getEconomy() {
        return inst().econ;
    }

    public static Chat getChat() {
        return inst().chat;
    }

    public static MineGuildConnectorPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        if (!setupEconomy()) {
            this.getLogger().warning(
                    String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        loadCommands();
        registerListeners();
        setupChat();
        this.guildUpdater = new GuildUpdater();
        this.getServer().getScheduler().runTaskLaterAsynchronously(this, () -> JClientGuildControlOutput.request_all_guild_data(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME), 20);
    }

    @Override
    public void onDisable() {
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.econ = rsp.getProvider();
        return this.econ != null;
    }

    private void setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
            this.getLogger().info("Using Vault for Chatprovider!");
        }
    }

    private void loadCommands() {
        GuildCommand guildCommand = new GuildCommand(this);
        if (!guildCommand.isLoaded())
            guildCommand.loadCmd();
        getCommand("guild").setExecutor(guildCommand);
    }

    private void registerListeners() {
        JClientConnection jClientConnection = MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1;
        jClientConnection.registerIncomingDataListener(JClientGuildCommandOutput.headerChannel, new JClientGuildCommandListener());
        jClientConnection.registerIncomingDataListener(JClientGuildEditOutput.headerChannel, new JClientGuildEditListener());
        jClientConnection.registerIncomingDataListener(JClientGuildRangOutput.headerChannel, new JClientGuildRangListener());
        jClientConnection.registerIncomingDataListener(JClientGuildUpdateOutput.headerChannel, new JClientGuildUpdateListener());
        jClientConnection.registerIncomingDataListener(JClientGuildControlOutput.headerChannel, new JClientGuildControlListener());
        jClientConnection.registerIncomingDataListener(JClientGuildCheckOutput.headerChannel, new JClientGuildCheckListener());
        this.getServer().getPluginManager().registerEvents(new McmmoListener(), this);
        this.getServer().getPluginManager().registerEvents(new JobsRebornListener(), this);
    }

    public GuildUpdater getGuildUpdater() {
        return guildUpdater;
    }
}
