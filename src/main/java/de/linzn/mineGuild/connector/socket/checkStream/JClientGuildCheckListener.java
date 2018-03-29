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

package de.linzn.mineGuild.connector.socket.checkStream;

import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;


public class JClientGuildCheckListener implements IncomingDataListener {


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

            if (subChannel.equalsIgnoreCase("test1")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                return;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
