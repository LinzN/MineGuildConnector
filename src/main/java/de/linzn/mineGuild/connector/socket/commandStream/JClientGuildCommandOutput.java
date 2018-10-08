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

package de.linzn.mineGuild.connector.socket.commandStream;

import de.linzn.mineSuite.core.MineSuiteCorePlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class JClientGuildCommandOutput {

    public static String headerChannel = "mineGuild_command";

    public static void create_guild(String guildName, UUID creator) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_create_guild");
            dataOutputStream.writeUTF(guildName);
            dataOutputStream.writeUTF(creator.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void remove_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_remove_guild");
            dataOutputStream.writeUTF(actor.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void info_guild(UUID actor, String guildArg) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_info_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(guildArg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void members_guild(UUID actor, String guildArg, int page) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_info_guild_members");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(guildArg);
            dataOutputStream.writeInt(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void invite_to_guild(UUID actor, String invitedName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_invite_to_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(invitedName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void accept_invite_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_accept_invite_guild");
            dataOutputStream.writeUTF(actor.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void deny_invite_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_deny_invite_guild");
            dataOutputStream.writeUTF(actor.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void kick_from_guild(UUID actor, String kickedPlayer) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_kick_from_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(kickedPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void leave_from_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_leave_from_guild");
            dataOutputStream.writeUTF(actor.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void guild_spawn_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_send_spawn_guild");
            dataOutputStream.writeUTF(actor.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void list_all_guilds(UUID actor, int page) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_list_all_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeInt(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void player_deposit_guild(UUID actor, double amount) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_deposit_to_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeDouble(amount);
            dataOutputStream.writeUTF(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void player_withdraw_guild(UUID actor, double amount) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_withdraw_from_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeDouble(amount);
            dataOutputStream.writeUTF(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }


}
