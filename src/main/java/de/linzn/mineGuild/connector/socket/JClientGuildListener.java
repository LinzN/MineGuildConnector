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

            subChannel = in.readUTF();

            if (subChannel.equalsIgnoreCase("test")) {
                UUID guildUUID = UUID.fromString(in.readUTF());
                String guildName = in.readUTF();
                String guildMaster = in.readUTF();
                return;
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
