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

package de.linzn.mineGuild.connector.socket.controlStream;

import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.mineGuild.connector.manager.GuildConnectorManager;
import de.linzn.mineGuild.connector.manager.TransactionManager;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;


public class JClientGuildControlListener implements IncomingDataListener {


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
            if (subChannel.equalsIgnoreCase("guild_set_guild_packet")) {
                try {
                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(in.readUTF());
                    GuildConnectorManager.set_guild_object(jsonObject);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (subChannel.equalsIgnoreCase("request_confirm_guild_action")) {
                UUID playerUUID = UUID.fromString(in.readUTF());
                UUID guildUUID = UUID.fromString(in.readUTF());
                GuildConnectorManager.requestGuildConfirm(playerUUID, guildUUID);
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
