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
import de.linzn.jSocket.core.ConnectionListener;
import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.mineGuild.connector.commands.GuildCommand;
import de.linzn.mineGuild.connector.commands.standalone.GChat;
import de.linzn.mineGuild.connector.listener.JobsRebornListener;
import de.linzn.mineGuild.connector.listener.LoginListener;
import de.linzn.mineGuild.connector.listener.McmmoListener;
import de.linzn.mineGuild.connector.socket.JClientConnectionListener;
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

import java.util.HashSet;

public class MineGuildConnectorPlugin extends JavaPlugin {
    private static MineGuildConnectorPlugin inst;
    private Economy econ = null;
    private Chat chat = null;
    private GuildUpdater guildUpdater = null;
    private HashSet<IncomingDataListener> listenerList;
    private HashSet<ConnectionListener> conListenerList;

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
        this.listenerList = new HashSet<>();
        this.conListenerList = new HashSet<>();
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
    }

    @Override
    public void onDisable() {
        this.unregisterListeners();
        this.listenerList.clear();
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

        GChat gChat = new GChat(this, "mineguild.chat");
        getCommand("gc").setExecutor(gChat);
    }

    private void registerListeners() {
        JClientConnectionListener jClientConnectionListener = new JClientConnectionListener();

        JClientGuildCommandListener jClientGuildCommandListener = new JClientGuildCommandListener();
        JClientGuildEditListener jClientGuildEditListener = new JClientGuildEditListener();
        JClientGuildRangListener jClientGuildRangListener = new JClientGuildRangListener();
        JClientGuildUpdateListener jClientGuildUpdateListener = new JClientGuildUpdateListener();
        JClientGuildControlListener jClientGuildControlListener = new JClientGuildControlListener();
        JClientGuildCheckListener jClientGuildCheckListener = new JClientGuildCheckListener();

        this.conListenerList.add(jClientConnectionListener);

        this.listenerList.add(jClientGuildCommandListener);
        this.listenerList.add(jClientGuildEditListener);
        this.listenerList.add(jClientGuildRangListener);
        this.listenerList.add(jClientGuildUpdateListener);
        this.listenerList.add(jClientGuildControlListener);
        this.listenerList.add(jClientGuildCheckListener);

        JClientConnection jClientConnection = MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1;
        jClientConnection.registerConnectionListener(jClientConnectionListener);

        jClientConnection.registerIncomingDataListener(JClientGuildCommandOutput.headerChannel, jClientGuildCommandListener);
        jClientConnection.registerIncomingDataListener(JClientGuildEditOutput.headerChannel, jClientGuildEditListener);
        jClientConnection.registerIncomingDataListener(JClientGuildRangOutput.headerChannel, jClientGuildRangListener);
        jClientConnection.registerIncomingDataListener(JClientGuildUpdateOutput.headerChannel, jClientGuildUpdateListener);
        jClientConnection.registerIncomingDataListener(JClientGuildControlOutput.headerChannel, jClientGuildControlListener);
        jClientConnection.registerIncomingDataListener(JClientGuildCheckOutput.headerChannel, jClientGuildCheckListener);

        this.getServer().getPluginManager().registerEvents(new LoginListener(), this);
        this.getServer().getPluginManager().registerEvents(new McmmoListener(), this);
        this.getServer().getPluginManager().registerEvents(new JobsRebornListener(), this);
    }

    private void unregisterListeners() {
        for (ConnectionListener connectionListener : this.conListenerList) {
            MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.unregisterConnectionListener(connectionListener);
        }
        for (IncomingDataListener incomingDataListener : this.listenerList) {
            MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.unregisterIncomingDataListener(incomingDataListener);
        }
    }

    public GuildUpdater getGuildUpdater() {
        return guildUpdater;
    }
}
