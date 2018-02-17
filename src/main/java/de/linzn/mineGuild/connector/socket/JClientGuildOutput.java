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

import de.linzn.mineSuite.core.MineSuiteCorePlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class JClientGuildOutput {


    public static void test(UUID guildUUID, String guildName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("test");
            dataOutputStream.writeUTF(guildUUID.toString());
            dataOutputStream.writeUTF(guildName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

}
