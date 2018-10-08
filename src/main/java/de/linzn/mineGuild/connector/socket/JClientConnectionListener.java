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

package de.linzn.mineGuild.connector.socket;

import de.linzn.jSocket.core.ConnectionListener;
import de.linzn.mineGuild.connector.GuildDatabase;
import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.socket.controlStream.JClientGuildControlOutput;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import org.bukkit.Bukkit;

import java.util.UUID;

public class JClientConnectionListener implements ConnectionListener {

    @Override
    public void onConnectEvent(UUID uuid) {
        MineGuildConnectorPlugin.inst().getServer().getScheduler().runTaskLaterAsynchronously(MineGuildConnectorPlugin.inst(), () -> JClientGuildControlOutput.request_all_guild_data(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME), 20);
    }

    @Override
    public void onDisconnectEvent(UUID uuid) {
        Bukkit.getScheduler().runTask(MineGuildConnectorPlugin.inst(), GuildDatabase::clearDatabase);
    }
}
