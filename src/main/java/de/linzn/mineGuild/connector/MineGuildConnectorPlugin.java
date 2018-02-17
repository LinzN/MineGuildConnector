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

import de.linzn.mineGuild.connector.commands.GuildCommand;
import de.linzn.mineGuild.connector.socket.JClientGuildListener;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MineGuildConnectorPlugin extends JavaPlugin {
    private static MineGuildConnectorPlugin inst;
    private Economy econ = null;
    private Chat chat = null;

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
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.registerIncomingDataListener("mineGuild", new JClientGuildListener());
    }

}
