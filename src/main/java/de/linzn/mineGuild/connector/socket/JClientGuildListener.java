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

import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.mineGuild.connector.manager.GuildConnectorManager;
import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;


public class JClientGuildListener implements IncomingDataListener {


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

            if (subChannel.equalsIgnoreCase("guild_set_guild_data")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                String guildName = in.readUTF();
                int level = in.readInt();
                Guild guild = new Guild(guildName, guildUUID);
                guild.setLevel(level);
                GuildConnectorManager.set_guild_data(guild);
                return;
            }

            if (subChannel.equalsIgnoreCase("guild_set_guildplayer_data")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                UUID playerUUID = UUID.fromString(in.readUTF());
                GuildPlayer guildPlayer = new GuildPlayer(playerUUID);

                GuildConnectorManager.set_guildplayer_data(guildUUID, guildPlayer);
                return;
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
