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

package de.linzn.mineGuild.connector.socket.updateStream;

import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.mineGuild.connector.manager.GuildConnectorManager;
import de.linzn.mineGuild.connector.manager.TransactionManager;
import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;


public class JClientGuildUpdateListener implements IncomingDataListener {


    @Override
    public void onEvent(String s, UUID clientUUID, byte[] bytes) {
        // TODO Auto-generated method stub
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        String subChannel;
        try {
            String serverName = in.readUTF();

            if (!serverName.equalsIgnoreCase(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME) && !serverName.equalsIgnoreCase("all")) {
                return;
            }
            subChannel = in.readUTF();

            if (subChannel.equalsIgnoreCase("guild_update_guild")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                int level = in.readInt();
                GuildConnectorManager.update_guild(guildUUID, level);
            }

            if (subChannel.equalsIgnoreCase("guild_remove_guild")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                GuildConnectorManager.remove_guild(guildUUID);
            }

            if (subChannel.equalsIgnoreCase("guild_add_guild")) {
                UUID actorUUID = UUID.fromString(in.readUTF());
                UUID guildUUID = UUID.fromString(in.readUTF());
                int level = in.readInt();
                Guild guild = new Guild(guildUUID);
                guild.setLevel(level);
                GuildConnectorManager.add_guild(guild, actorUUID);
            }

            if (subChannel.equalsIgnoreCase("guild_remove_guildplayer")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                UUID playerUUID = UUID.fromString(in.readUTF());
                GuildConnectorManager.remove_guildplayer(guildUUID, playerUUID);
            }
            if (subChannel.equalsIgnoreCase("guild_add_guildplayer")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                UUID playerUUID = UUID.fromString(in.readUTF());
                GuildConnectorManager.add_guildplayer(guildUUID, playerUUID);
            }

            if (subChannel.equalsIgnoreCase("guild_accept_withdraw")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                UUID playerUUID = UUID.fromString(in.readUTF());
                double amount = in.readDouble();
                TransactionManager.player_withdraw_transaction(guildUUID, playerUUID, amount);
            }

            if (subChannel.equalsIgnoreCase("guild_accept_deposit")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                UUID playerUUID = UUID.fromString(in.readUTF());
                double amount = in.readDouble();
                TransactionManager.player_deposit_transaction(guildUUID, playerUUID, amount);
            }

            if (subChannel.equalsIgnoreCase("plugin_migrate_data")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                String guildName = in.readUTF();
                TransactionManager.migrate_guild_to_uuid(guildUUID, guildName);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
